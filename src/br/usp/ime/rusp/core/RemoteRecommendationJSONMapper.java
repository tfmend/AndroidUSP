package br.usp.ime.rusp.core;

import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;
import br.usp.ime.rusp.enumerations.QueueSize;
import br.usp.ime.rusp.enumerations.RU;

public class RemoteRecommendationJSONMapper implements
		JSONMapper<RemoteRecommendation> {

	private String json;

	@Override
	public void read(String jsonString) {

		this.json = jsonString;

	}

	@Override
	public List<RemoteRecommendation> getObjects() throws Exception {

		List<RemoteRecommendation> list = new LinkedList<RemoteRecommendation>();

		if (this.json.length() < 1) {
			return list;
		}

		JSONArray jsonArray = new JSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {

			JSONObject jsonObject = jsonArray.getJSONObject(i);
			RemoteRecommendation recommendation = new RemoteRecommendation();
			recommendation.index = jsonObject.getDouble("index");
			recommendation.ru = RU
					.getRUByName(jsonObject.getString("bandejao"));
			recommendation.time = jsonObject.getString("hora");
			recommendation.fila = QueueSize.getQueueByName(jsonObject
					.getString("fila"));

			if (recommendation.ru != null) {
				list.add(recommendation);
			}

		}

		return list;

	}

}
