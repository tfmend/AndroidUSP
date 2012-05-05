package br.usp.ime.rusp.view.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import br.ime.usp.R;
import br.usp.ime.rusp.core.DataRecover;
import br.usp.ime.rusp.core.RemoteComment;
import br.usp.ime.rusp.core.actions.GetCommentsAction;
import br.usp.ime.rusp.core.listitemadapter.CustomCommentListAdapter;
import br.usp.ime.rusp.view.activities.RUSPBasicActivity;

public class ViewCommentsActivity extends RUSPBasicActivity {

	private ListView listView;
	private List<RemoteComment> remoteComments = new ArrayList<RemoteComment>();
	private CustomCommentListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_comments);

        listView = (ListView) this.findViewById(R.id.list_view_comments);
        
		adapter = new CustomCommentListAdapter(this,
				R.layout.fragment_line_item, remoteComments);

		listView.setAdapter(adapter);
		this.update();
		
	}

	@Override
	public void update() {
		
		GetCommentsAction action = new GetCommentsAction(this, null, null);
		action.getComments(new LocalDataRecovery(this));
		
	}
	
	private class LocalDataRecovery implements DataRecover<RemoteComment>  {

		private final Activity activity;
		
		public LocalDataRecovery(Activity activity) {
			this.activity = activity;
		}
		
		@Override
		public void afterRecovery(List<RemoteComment> data) {
			
			ViewCommentsActivity.this.remoteComments.clear();
			ViewCommentsActivity.this.remoteComments.addAll(data);
			ViewCommentsActivity.this.adapter.notifyDataSetChanged();
			
		}
		
	}	
	
	
}
