/*
 * Copyright (C) 2011 Wglxy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.usp.ime.mapa;

import br.ime.usp.mapa.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
	/*	case R.id.home_btn_feature2 :
			//startActivity (new Intent(getApplicationContext(), F2Activity.class));
			break;*/
		default: 
			break;
		}
	}
	
	public void setTitleFromActivityLabel (int textViewId) {
		TextView tv = (TextView) findViewById (textViewId);
		if (tv != null) tv.setText (getTitle ());
	}

}