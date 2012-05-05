package br.usp.ime.rusp.core.actions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import br.ime.usp.R;
import br.usp.ime.rusp.core.CallBackListener;

public abstract class BasicAction extends Thread {

	protected final String waitMessage;
	protected final String waitTitle;
	protected final Context context;
	protected final DialogInterface.OnCancelListener cancelListener;
	protected final CallBackListener backListener;
	private ProgressDialog dialog;

	public BasicAction(String waitTitle, String waitMessage, Context context,
			DialogInterface.OnCancelListener cancelListener,
			CallBackListener backListener) {
		this.waitMessage = waitMessage;
		this.waitTitle = waitTitle;
		this.context = context;
		this.cancelListener = cancelListener;
		this.backListener = backListener;
		
	}

	public void showWaitDialog() {
		
		if (this.cancelListener != null) {
			
			dialog = ProgressDialog.show(context, this.waitTitle,
					this.waitMessage, false, true, this.cancelListener);
			
		} else {
			
			dialog = ProgressDialog.show(context, this.waitTitle,
					this.waitMessage, false, false);
			
		}

		dialog.setIcon(R.drawable.ic_launcher);		
		dialog.show();
		
	}

	public void closeWaitDialog() {
		if (dialog != null) {
			dialog.dismiss();
		}

	}

}
