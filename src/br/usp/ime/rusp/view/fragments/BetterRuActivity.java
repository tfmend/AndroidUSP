package br.usp.ime.rusp.view.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import br.ime.usp.R;
import br.usp.ime.rusp.core.DataRecover;
import br.usp.ime.rusp.core.RemoteRecommendation;
import br.usp.ime.rusp.core.actions.SugestRUCommentsAction;
import br.usp.ime.rusp.core.listitemadapter.CustomRecommendationListAdapter;
import br.usp.ime.rusp.view.activities.RUSPBasicActivity;

public class BetterRuActivity extends RUSPBasicActivity {

	private ListView listView;
	private List<RemoteRecommendation> remoteRecommendations = new ArrayList<RemoteRecommendation>();
	private CustomRecommendationListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_better_ru);

        listView = (ListView) this.findViewById(R.id.list_better_ru);
        
		adapter = new CustomRecommendationListAdapter(this,
				R.layout.fragment_line_item, remoteRecommendations);

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new RUOnClickListener(remoteRecommendations, this));
        
		this.update();
		
	}

	@Override
	public void update() {
		
		SugestRUCommentsAction action = new SugestRUCommentsAction(this, null, null);
		action.getRecommendation(new LocalDataRecovery(this));
		
	}
	
	private class LocalDataRecovery implements DataRecover<RemoteRecommendation>  {

		private final Activity activity;
		
		public LocalDataRecovery(Activity activity) {
			this.activity = activity;
		}
		
		@Override
		public void afterRecovery(List<RemoteRecommendation> data) {
			
			BetterRuActivity.this.remoteRecommendations.clear();
			BetterRuActivity.this.remoteRecommendations.addAll(data);
			BetterRuActivity.this.adapter.notifyDataSetChanged();

		}
		
	}
	
}
