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
import br.usp.ime.rusp.core.actions.GetStatusAction;
import br.usp.ime.rusp.core.listitemadapter.CustomRecommendationListAdapter;
import br.usp.ime.rusp.view.activities.RUSPBasicActivity;

public class ViewStatusActivity extends RUSPBasicActivity {

	private ListView listView;
	private List<RemoteRecommendation> remoteRecommendations = new ArrayList<RemoteRecommendation>();
	private CustomRecommendationListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_ru_status);

		listView = (ListView) this.findViewById(R.id.list_ru_status);
		adapter = new CustomRecommendationListAdapter(this,
				R.layout.fragment_line_item, remoteRecommendations);

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new RUOnClickListener(remoteRecommendations, this));
		this.update();
		
	}

	@Override
	public synchronized void update() {

		GetStatusAction action = new GetStatusAction(this, null, null);
		action.getRecommendation(new LocalDataRecovery(this));

	}

	private class LocalDataRecovery implements
			DataRecover<RemoteRecommendation> {

		private final Activity activity;

		public LocalDataRecovery(Activity activity) {
			this.activity = activity;
		}

		@Override
		public void afterRecovery(List<RemoteRecommendation> data) {

			ViewStatusActivity.this.remoteRecommendations.clear();
			ViewStatusActivity.this.remoteRecommendations.addAll(data);
			ViewStatusActivity.this.adapter.notifyDataSetChanged();

		}

	}

}
