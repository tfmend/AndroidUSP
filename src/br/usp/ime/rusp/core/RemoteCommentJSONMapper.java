package br.usp.ime.rusp.core;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import br.usp.ime.rusp.enumerations.*;

public class RemoteCommentJSONMapper implements JSONMapper<RemoteComment> {

	private String json;

	@Override
	public void read(String jsonString) {

		this.json = jsonString;

	}

	@Override
	public List<RemoteComment> getObjects() throws Exception {
		List<RemoteComment> comments = new LinkedList<RemoteComment>();

		if (this.json.length() < 1) {
			return comments;
		}
		JSONArray jsonArray = new JSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {

			JSONObject jsonObject = jsonArray.getJSONObject(i);
			RemoteComment comment = new RemoteComment();
			comment.ru = RU.getRUByName(jsonObject.getString("bandejao"));
			comment.fila = QueueSize.getQueueByName(jsonObject
					.getString("fila"));
			comment.texto = jsonObject.getString("texto");
			comment.time = jsonObject.getString("hora");

			comments.add(comment);

		}

		return comments;
	}

}
