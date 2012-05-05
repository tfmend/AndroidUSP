package br.usp.ime.rusp.core.actions;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;
import br.ime.usp.R;
import br.usp.ime.rusp.core.CallBackListener;
import br.usp.ime.rusp.core.RUConnection;
import br.usp.ime.rusp.core.RUConnectionFactory;
import br.usp.ime.rusp.core.RemoteComment;

public class SendCommentAction extends BasicAction {

	private RemoteComment comment = null;
	
	public SendCommentAction(Context context,
			DialogInterface.OnCancelListener cancelListener, 
			CallBackListener backListener) {
		
		super(context.getString(R.string.app_name1), 
				context.getString(R.string.send_commen_wait),
				context, cancelListener, backListener);
		
	}
	
	public void send(RemoteComment comment) {
		this.comment = comment;
		this.start();
	}
	
	@Override
	public synchronized void start() {
		super.start();
		this.showWaitDialog();
		if ( this.backListener != null ) {
			this.backListener.beforeStart();
		}
		try {
			RUConnection connection = RUConnectionFactory.getConnection(this.context);
			connection.sendComment(comment, this.backListener);
			if ( this.backListener != null ) {
				this.backListener.sucess();
			}
		} catch (Exception e) {
			Toast.makeText(context, "Falha ao enviar coment�rio.", Toast.LENGTH_LONG);
//			Log.e("send_comment", e.getMessage());
			e.printStackTrace();
			if ( this.backListener != null ) {
				this.backListener.fail(e);
			}
		}
		finally {
			this.closeWaitDialog();	
		}
		
		if ( this.backListener != null ) {
			this.backListener.afterEnd();
		}
		
	}	

}
