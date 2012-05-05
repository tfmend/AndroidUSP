package br.usp.ime.uspApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import br.ime.usp.R;
import br.usp.ime.mapa.MapaDaUSP;
import br.usp.ime.rusp.view.fragments.TestConnectionActivity;

public abstract class DashboardActivity extends Activity 
{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void onDestroy () {
		super.onDestroy ();
	}

	protected void onPause () {
		super.onPause ();
	}

	protected void onRestart () {
		super.onRestart ();
	}

	protected void onResume () {
		super.onResume ();
	}

	protected void onStart () {
		super.onStart ();
	}

	protected void onStop () {
		super.onStop ();
	}

	public void onClickFeature (View v) {
		int id = v.getId ();
		switch (id) {
		case R.id.home_btn_feature1 :
			startActivity (new Intent(getApplicationContext(), MapaDaUSP.class));
			break;
		case R.id.home_btn_feature2 :
			startActivity (new Intent(getApplicationContext(), TestConnectionActivity.class));
			break;
		default: 
			break;
		}
	}
	
	public void setTitleFromActivityLabel (int textViewId) {
		TextView tv = (TextView) findViewById (textViewId);
		if (tv != null) tv.setText (getTitle ());
	}

}