package br.usp.ime.rusp.core;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ConfigurationDataHelper {

	private static final String DATABASE_NAME = "config.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "configuration";
	private static final String FIELD_NAMES = " id, name, value ";
	private static final String[] FIELDS = { "id", "name", "value" };

	private static final String SQL_INSERT = "insert into " + TABLE_NAME
			+ "(name, value) values " + "(?,?)";

	private static ConfigurationDataHelper INSTANCE;

	public static synchronized ConfigurationDataHelper getInstance(
			Context context) {

		if (INSTANCE == null) {
			INSTANCE = new ConfigurationDataHelper(context);
		}

		return INSTANCE;

	}

	/**
	 * classe auxiliar para acesso ao banco de dados
	 * 
	 * @author jteodoro
	 * 
	 */
	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL("CREATE TABLE " + TABLE_NAME
					+ "(id INTEGER primary key autoincrement, "
					+ "name TEXT, value TEXT)");

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

	private ConfigurationDataHelper(Context context) {
		super();
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
	}

	public synchronized void saveConfig(Configuration config) throws Exception {

		
		this.clearTable();
		for (String key : config.getConfigurationKeys() ) {
			this.insertConfiguration(key, config.getConfigurationValue(key) );
		}

	}

	public synchronized void readConfiguration(Configuration config)
			throws Exception {

		Cursor cursor = this.db.rawQuery("select " + FIELD_NAMES + " from "
				+ TABLE_NAME, null);
		if (cursor != null && cursor.moveToFirst()) {

			do {

				String key = cursor.getString(0);
				String value = cursor.getString(1);
				
				config.putConfigurationValue(key, value);

			} while (cursor.moveToNext());

		}

		if (cursor != null && !cursor.isClosed()) {
	         cursor.close();
		}

	}

	private void insertConfiguration(String name, String value) {
		String[] args = {name,value};
		this.db.execSQL(SQL_INSERT, args);
	}
	
	private void clearTable() throws Exception {

		this.db.execSQL("delete from " + TABLE_NAME);

	}

}
