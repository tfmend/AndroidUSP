package br.usp.ime.rusp.core.actions;

import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;
import br.ime.usp.R;
import br.usp.ime.rusp.core.CallBackListener;
import br.usp.ime.rusp.core.DataRecover;
import br.usp.ime.rusp.core.RUConnection;
import br.usp.ime.rusp.core.RUConnectionFactory;
import br.usp.ime.rusp.core.RemoteRecommendation;

public class GetStatusAction extends BasicAction {

	private DataRecover<RemoteRecommendation> dataRecover;
	
	public GetStatusAction(Context context,
			DialogInterface.OnCancelListener cancelListener, 
			CallBackListener backListener) {
		
		super(context.getString(R.string.app_name1), 
				context.getString(R.string.get_comment_wait),
				context, cancelListener, backListener);
		
	}
	
	public void getRecommendation(DataRecover<RemoteRecommendation> dataRecover) {
		this.dataRecover = dataRecover;
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
			
			List<RemoteRecommendation> data = connection.getStatus(this.backListener);
		
			if ( this.backListener != null ) {
				this.backListener.sucess();
			}
			
			if ( this.dataRecover != null ) {
				this.dataRecover.afterRecovery(data);
			}
		} catch (Exception e) {
			Toast.makeText(context, "Falha ao recuperar coment�rios.", Toast.LENGTH_LONG);
			Log.e("send_comment", e.getMessage());
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
