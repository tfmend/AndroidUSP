package br.usp.ime.rusp.core.listitemadapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.widget.SimpleAdapter;
import br.ime.usp.R;
import br.usp.ime.rusp.core.RemoteComment;
import br.usp.ime.rusp.core.RemoteRecommendation;

public class ListItemAdapterFactory {

	public static SimpleAdapter createAdapter( Activity context, List<Map<String, String>> itens, String title, String description ) {
		
		String[] from = { title, description };
        int[] to = { R.id.line_item_title, R.id.line_item_description };
 
        SimpleAdapter ad = new SimpleAdapter(context, itens , R.layout.fragment_line_item, from, to);
        return ad;
        
	}

	
	public static List<Map<String, String>> createMapList( RemoteRecommendation[] recommendations ) {
	
		List<Map<String, String>> target = new LinkedList<Map<String,String>>();
		return createMapList(recommendations, target);
        
	}	
	
	public static List<Map<String, String>> createMapList( RemoteComment[] comments ) {
		
		List<Map<String, String>> target = new LinkedList<Map<String,String>>();
		return createMapList(comments, target);
        
	}
	
	public static List<Map<String, String>> createMapList( RemoteRecommendation[] recommendations, List<Map<String, String>> target ) {
		
		target.clear();
		
		for ( RemoteRecommendation r : recommendations ) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("texto", String.valueOf(r.index));
			map.put("fila", r.fila.getFriendlyName());
			map.put("ru", r.ru.getFriendlyName());
			map.put("fila", r.time);
			target.add(map);
		}
		
		return target;
        
	}	
	
	public static List<Map<String, String>> createMapList( RemoteComment[] comments, List<Map<String, String>> target ) {
		
		target.clear();
		
		
		for ( RemoteComment r : comments ) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("texto", r.texto);
			map.put("fila", r.fila.getFriendlyName());
			map.put("ru", r.ru.getFriendlyName());
			map.put("fila", r.time);
			target.add(map);
		}
		
		return target;
        
	}
	
}
