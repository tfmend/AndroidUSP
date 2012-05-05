package br.usp.ime.rusp.core.listitemadapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.ime.usp.R;
import br.usp.ime.rusp.core.RemoteComment;

public class CustomCommentListAdapter extends ArrayAdapter<RemoteComment> {

	private Context context;
	private int resourceId;
	private List<RemoteComment> itens;
	private LayoutInflater inflater;
	
	public CustomCommentListAdapter(Context context,
			int textViewResourceId, List<RemoteComment> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.resourceId = textViewResourceId;
		this.itens = objects;
		this.inflater = LayoutInflater.from(context);
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		RemoteComment item = getItem(position);
 
        convertView = inflater.inflate(resourceId, parent, false);
 
        TextView title = (TextView) convertView.findViewById(R.id.line_item_title);
        TextView description = (TextView) convertView.findViewById(R.id.line_item_description);
 
        title.setText( item.fila.getFriendlyName() +" - "+ item.time );
        description.setText( item.texto );
 
        return convertView;
		
	}

}
