package br.usp.ime.rusp.core;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import br.usp.ime.rusp.enumerations.QueueSize;
import br.usp.ime.rusp.enumerations.RU;

public class OffLineDataHelper {

	private static final String DATABASE_NAME = "offline.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "comments";
	private static final String FIELD_NAMES = " id, ru, queue, comment, time ";
	private static final String[] FIELDS = { "id", "ru", "queue", "comment", "time" };

	private static final String SQL_INSERT = "insert into " + TABLE_NAME
			+ "(ru, queue, comment, time) values "
			+ "(?,?,?,?)";	
	
	private static OffLineDataHelper INSTANCE;

	public static synchronized OffLineDataHelper getInstance(Context context) {

		if (INSTANCE == null) {
			INSTANCE = new OffLineDataHelper(context);
		}

		return INSTANCE;

	}

	/**
	 * classe auxiliar para acesso ao banco de dados
	 * @author jteodoro
	 *
	 */
	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL("CREATE TABLE "
					+ TABLE_NAME
					+ "(id INTEGER primary key autoincrement, "
					+ "ru TEXT, queue TEXT, comment TEXT, time TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("database",
					"Upgrading database, this will drop tables and recreate.");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}

	}

	/*
	 * variaveis e metodos da instancia
	 */
	
	private Context context;
	private volatile SQLiteDatabase db;
	
	private OffLineDataHelper(Context context) {
		super();
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
	}

	public synchronized void saveComment(RemoteComment comment) throws Exception {

		if (!existsComment(comment)) {
			
			SQLiteStatement insertStmt = this.db.compileStatement(SQL_INSERT);
			insertStmt.bindString(1, comment.ru.getName() );
			insertStmt.bindString(2, comment.fila.getName() );
			insertStmt.bindString(3, comment.texto );
			insertStmt.bindString( 4, comment.time );
		    long result = insertStmt.executeInsert();
		    
		}

	}

	public List<RemoteComment> getCommentsAfter(Date date) throws Exception {
		
		String[] args = {String.valueOf( date.getTime() )};
		Cursor cursor = this.db.rawQuery("select "+FIELD_NAMES+" from "+TABLE_NAME+ " where time > ?",args);
		return this.getComments(cursor);
		
	}
		
	public void clearTable() throws Exception {
		
		this.db.execSQL("delete from "+TABLE_NAME);

	}

	private boolean existsComment(RemoteComment comment) throws Exception {

		String selection = "ru = ? and queue = ? " +
				"and comment = ? and " +
				"time = ?";
		String[] args = {comment.ru.getName(), comment.fila.getName(), comment.texto, String.valueOf( comment.time )};
		List<RemoteComment> comments = this.executeQuery(selection, args, null, null, null);
		return !comments.isEmpty();

	}
	
	/*metodos para facilitar a manipulacao dos dados*/
	
	private List<RemoteComment> executeQuery(String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) throws Exception {

		Cursor cursor = this.db.query(TABLE_NAME, FIELDS, selection,
				selectionArgs, groupBy, having, orderBy);
		return this.getComments(cursor);

	}

	/**
	 * mapper ORM
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private List<RemoteComment> getComments(Cursor cursor) throws SQLException {

		List<RemoteComment> comments = new LinkedList<RemoteComment>();

		if (cursor != null && cursor.moveToFirst()) {
			
			do {

				RemoteComment comment = new RemoteComment();
				comment.ru = RU.getRUByName( cursor.getString(1) );
				comment.fila = QueueSize.getQueueByName( cursor.getString(2) );
				comment.texto = cursor.getString(3);
				comment.time = cursor.getString(4);
				comments.add(comment);

			} while (cursor.moveToNext());
			
		}

		if (cursor != null && !cursor.isClosed()) {
	         cursor.close();
	     }
		
		return comments;

	}
}
