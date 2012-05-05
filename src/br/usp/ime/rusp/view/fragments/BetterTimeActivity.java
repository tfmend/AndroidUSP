package br.usp.ime.rusp.view.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import br.ime.usp.R;
import br.usp.ime.rusp.core.DataRecover;
import br.usp.ime.rusp.core.RemoteRecommendation;
import br.usp.ime.rusp.core.actions.SugestTimeCommentsAction;
import br.usp.ime.rusp.core.listitemadapter.CustomTimeRecommendationListAdapter;
import br.usp.ime.rusp.view.activities.RUSPBasicActivity;

public class BetterTimeActivity extends RUSPBasicActivity {
	
	private ListView listView;
	private List<RemoteRecommendation> remoteRecommendations = new ArrayList<RemoteRecommendation>();
	private CustomTimeRecommendationListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_better_time);

        listView = (ListView) this.findViewById(R.id.list_better_time);
        
		adapter = new CustomTimeRecommendationListAdapter(this,
				R.layout.fragment_line_item, remoteRecommendations);

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new RUOnClickListener(remoteRecommendations, this));
		this.update();
		
	}

	@Override
	public void update() {
		
		SugestTimeCommentsAction action = new SugestTimeCommentsAction(this, null, null);
		action.getRecommendation(new LocalDataRecovery(this));
		
	}
	
	private class LocalDataRecovery implements DataRecover<RemoteRecommendation>  {

		private final Activity activity;
		
		public LocalDataRecovery(Activity activity) {
			this.activity = activity;
		}
		
		@Override
		public void afterRecovery(List<RemoteRecommendation> data) {
			
			BetterTimeActivity.this.remoteRecommendations.clear();
			BetterTimeActivity.this.remoteRecommendations.addAll(data);
			BetterTimeActivity.this.adapter.notifyDataSetChanged();
			
		}
		
	}	
	
}

