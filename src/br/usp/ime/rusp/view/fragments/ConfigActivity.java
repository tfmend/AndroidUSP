package br.usp.ime.rusp.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import br.ime.usp.R;
import br.usp.ime.rusp.core.Configuration;
import br.usp.ime.rusp.view.activities.RUSPBasicActivity;

public class ConfigActivity extends RUSPBasicActivity {

	EditText editServerAddress = null;
	Button cancelButton = null;
	Button saveButton = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_config);

		editServerAddress = (EditText) findViewById(R.id.edittext_config_server_address);
		cancelButton = (Button) findViewById(R.id.config_button_cancel);
		cancelButton.setOnClickListener(new ButtonCancelListener(this));
		saveButton = (Button) findViewById(R.id.config_button_save);
		saveButton.setOnClickListener(new ButtonSaveListener(this));
        
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		this.update();
	}

	@Override
	public void update() {
		
		Configuration config = Configuration.getInstance(this);
		config.load();
		editServerAddress.setText(config.getConfigurationValue(config.SERVER_ADDRESS));
		
	}
	
	protected class ButtonSaveListener implements View.OnClickListener {

		private final RUSPBasicActivity activity;

		public ButtonSaveListener(RUSPBasicActivity activity) {
			this.activity = activity;
		}

		@Override
		public void onClick(View arg0) {
			
			Configuration config = Configuration.getInstance(activity);
			config.load();
			
			config.putConfigurationValue(config.SERVER_ADDRESS, editServerAddress.getText().toString()) ;
			
			config.save();
			this.activity.finish();
			
		}

	}
	
	protected class ButtonCancelListener implements View.OnClickListener {

		private final RUSPBasicActivity activity;

		public ButtonCancelListener(RUSPBasicActivity activity) {
			this.activity = activity;
		}

		@Override
		public void onClick(View arg0) {
			activity.update();
			activity.finish();
		}

	}
	
}
