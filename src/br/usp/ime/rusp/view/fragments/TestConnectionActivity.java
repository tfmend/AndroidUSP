package br.usp.ime.rusp.view.fragments;

import android.os.Bundle;
import android.widget.Button;
import br.ime.usp.R;
import br.usp.ime.rusp.view.activities.RUSPBasicActivity;

public class TestConnectionActivity extends RUSPBasicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_test_dashboard);
        
        Button button = (Button) findViewById(R.id.test_comment_sender_button);
        button.setOnClickListener(new ButtonCommentSenderListener(this));
        button = (Button) findViewById(R.id.test_ru_recommender_button);
        button.setOnClickListener(new ButtonGetRURecommenderListener(this));
        button = (Button) findViewById(R.id.test_time_recommender_button);
        button.setOnClickListener(new ButtonGetTimeRecommenderListener(this));
        button = (Button) findViewById(R.id.test_get_status_button);
        button.setOnClickListener(new ButtonGetStatusListener(this));
        
        button = (Button) findViewById(R.id.test_config_button);
        button.setOnClickListener(new ButtonConfigListener(this));
        
	}

	@Override
	public void update() {}
		
}
