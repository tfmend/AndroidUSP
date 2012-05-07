package br.usp.ime.rusp.view.activities;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import br.ime.usp.R;
import br.usp.ime.rusp.core.RUConnectionFactory;
import br.usp.ime.rusp.core.RemoteRecommendation;
import br.usp.ime.rusp.view.fragments.BetterRuActivity;
import br.usp.ime.rusp.view.fragments.BetterTimeActivity;
import br.usp.ime.rusp.view.fragments.ConfigActivity;
import br.usp.ime.rusp.view.fragments.SendCommentActivity;
import br.usp.ime.rusp.view.fragments.ViewCommentsActivity;
import br.usp.ime.rusp.view.fragments.ViewStatusActivity;

public abstract class RUSPBasicActivity extends Activity {

	public static volatile boolean firstTime = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* na primeira execucao avisa sobre a conexao com a internet */
		if (firstTime) {

			firstTime = false;

			boolean isOnline = RUConnectionFactory.isOnline(this);

			if (!isOnline) {

				new AlertDialog.Builder(this)
						.setTitle("Alerta RUSP!")
						.setMessage(
								"O aplicativo utilizar� os dados em cache porque"
										+ " n�o foi identificada a conex�o com a internet.")
						.setNeutralButton("Ok",null).show();

			}

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		OnClickListener listener = null;
		switch (item.getItemId()) {

		case R.id.item_menu_better_ru:
			listener = new ButtonGetRURecommenderListener(this);
			listener.onClick(getCurrentFocus());
			return true;
			
		case R.id.item_menu_comments_view:
			listener = new ButtonGetCommentsListener(this);
			listener.onClick(getCurrentFocus());
			return true;
			
		case R.id.item_menu_configurations:
			listener = new ButtonConfigListener(this);
			listener.onClick(getCurrentFocus());
			return true;
		
		case R.id.item_menu_status:
			listener = new ButtonGetStatusListener(this);
			listener.onClick(getCurrentFocus());
			return true;
			
		case R.id.item_menu_send_comment:
			listener = new ButtonCommentSenderListener(this);
			listener.onClick(getCurrentFocus());
			return true;

		case R.id.item_menu_time_recommender:
			listener = new ButtonGetTimeRecommenderListener(this);
			listener.onClick(getCurrentFocus());
			return true;
		case R.id.item_menu_update:
			this.update();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected class ButtonCommentSenderListener implements View.OnClickListener {

		private final RUSPBasicActivity activity;

		public ButtonCommentSenderListener(RUSPBasicActivity activity) {
			this.activity = activity;
		}

		@Override
		public void onClick(View arg0) {
			Intent i = new Intent(activity, SendCommentActivity.class);
			startActivity(i);
		}

	}

	protected class ButtonGetRURecommenderListener implements
			View.OnClickListener {

		private final RUSPBasicActivity activity;

		public ButtonGetRURecommenderListener(RUSPBasicActivity activity) {
			this.activity = activity;
		}

		@Override
		public void onClick(View arg0) {
			Intent i = new Intent(activity, BetterRuActivity.class);
			startActivity(i);
		}

	}

	protected class ButtonGetTimeRecommenderListener implements
			View.OnClickListener {

		private final RUSPBasicActivity activity;

		public ButtonGetTimeRecommenderListener(RUSPBasicActivity activity) {
			this.activity = activity;
		}

		@Override
		public void onClick(View arg0) {
			Intent i = new Intent(activity, BetterTimeActivity.class);
			startActivity(i);
		}

	}

	protected class ButtonGetCommentsListener implements View.OnClickListener {

		private final RUSPBasicActivity activity;

		public ButtonGetCommentsListener(RUSPBasicActivity activity) {
			this.activity = activity;
		}

		@Override
		public void onClick(View arg0) {
			Intent i = new Intent(activity, ViewCommentsActivity.class);
			startActivity(i);
		}

	}
	
	protected class ButtonGetStatusListener implements View.OnClickListener {

		private final RUSPBasicActivity activity;

		public ButtonGetStatusListener(RUSPBasicActivity activity) {
			this.activity = activity;
		}

		@Override
		public void onClick(View arg0) {
			Intent i = new Intent(activity, ViewStatusActivity.class);
			startActivity(i);
		}

	}

	protected class ButtonConfigListener implements View.OnClickListener {

		private final RUSPBasicActivity activity;

		public ButtonConfigListener(RUSPBasicActivity activity) {
			this.activity = activity;
		}

		@Override
		public void onClick(View arg0) {
			Intent i = new Intent(activity, ConfigActivity.class);
			startActivity(i);
		}

	}
	
	
	protected class RUOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			RemoteRecommendation rr = this.recommendations.get( (int) arg3);
			RUConnectionFactory.getConnection(activity).setCurrentRU(rr.ru);
			
			Intent i = new Intent(activity, ViewCommentsActivity.class);
			startActivity(i);
			
		}

		private final List<RemoteRecommendation> recommendations;
		private final Activity activity;
		public RUOnClickListener(List<RemoteRecommendation> recommendations,
				Activity activity) {
			super();
			this.recommendations = recommendations;
			this.activity = activity;
		}
		
	}
	
	public abstract void update();

}
