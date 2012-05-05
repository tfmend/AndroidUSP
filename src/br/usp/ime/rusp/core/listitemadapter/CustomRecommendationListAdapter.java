package br.usp.ime.rusp.core.listitemadapter;

import java.util.List;

import br.ime.usp.R;
import br.usp.ime.rusp.core.RemoteRecommendation;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomRecommendationListAdapter extends ArrayAdapter<RemoteRecommendation> {

	private Context context;
	private int resourceId;
	private List<RemoteRecommendation> itens;
	private LayoutInflater inflater;
	
	public CustomRecommendationListAdapter(Context context,
			int textViewResourceId, List<RemoteRecommendation> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.resourceId = textViewResourceId;
		this.itens = objects;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if ( !itens.isEmpty() ) {
			//get the person from position
	        RemoteRecommendation item = itens.get(position);
	 
	        // get a new View no matter recycling or ViewHolder FIXME
	        convertView = inflater.inflate(resourceId, parent, false);
	 
	        TextView title = (TextView) convertView.findViewById(R.id.line_item_title);
	        TextView description = (TextView) convertView.findViewById(R.id.line_item_description);
	 
	        String stitle = item.ru.getFriendlyName();
	        String sdescription = item.fila.getFriendlyName(); 
	        
	        title.setText( stitle  );
	        description.setText( sdescription );
	        
		}
		
        return convertView;
		
	}

}
