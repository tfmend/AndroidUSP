package br.usp.ime.rusp.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.util.Log;
import br.usp.ime.rusp.enumerations.RU;

public class OnlineRUConnection implements RUConnection {

	private static OnlineRUConnection INSTANCE;

	public static synchronized OnlineRUConnection getInstance(Context context) {

		if (INSTANCE == null) {
			INSTANCE = new OnlineRUConnection(context);
		}

		return INSTANCE;

	}

	private Configuration configuration;
	private Context context;
	private RU currentRU;

	private OnlineRUConnection(Context context) {
		this.configuration = Configuration.getInstance(context);
		this.context = context;
	}

	@Override
	public void sendComment(RemoteComment comment,
			CallBackListener callBackListener) throws Exception {

		WebserviceClient client = new WebserviceClient();
		String url = configuration.getURlSendComments(currentRU);
		client.sendComment( url , comment);
		
	}

	@Override
	public List<RemoteComment> getComments(CallBackListener callBackListener)
			throws Exception {

		JSONMapper<RemoteComment> mapper = new RemoteCommentJSONMapper();
		WebserviceClient client = new WebserviceClient();
		client.writeToMapper(configuration.getURlGetComments(currentRU), mapper);
		return mapper.getObjects();

	}

	@Override
	public List<RemoteRecommendation> getRURecommendations(
			CallBackListener callBackListener) throws Exception {
		JSONMapper<RemoteRecommendation> mapper = new RemoteRecommendationJSONMapper();
		WebserviceClient client = new WebserviceClient();
		client.writeToMapper(configuration.getURlRURecommendation(), mapper);
		List<RemoteRecommendation> result =  mapper.getObjects();
		OfflineRUConnection.getInstance(context).rurecommendations = result;
		return result;
	}

	@Override
	public List<RemoteRecommendation> getTimeRecommendation(
			CallBackListener callBackListener) throws Exception {
		JSONMapper<RemoteRecommendation> mapper = new RemoteRecommendationJSONMapper();
		WebserviceClient client = new WebserviceClient();
		client.writeToMapper(configuration.getURlTimeRecommendation(), mapper);
		List<RemoteRecommendation> result =  mapper.getObjects();
		OfflineRUConnection.getInstance(context).timerecommendations = result;
		return result;
	}

	@Override
	public List<RemoteRecommendation> getStatus(CallBackListener backListener)
			throws Exception {
		JSONMapper<RemoteRecommendation> mapper = new RemoteRecommendationJSONMapper();
		WebserviceClient client = new WebserviceClient();
		client.writeToMapper(configuration.getURlStatus(), mapper);
		List<RemoteRecommendation> result =  mapper.getObjects();
		OfflineRUConnection.getInstance(context).statusrecommendations = result;
		return result;
	}

	@Override
	public void setCurrentRU(RU ru) {
		currentRU = ru;
		
	}

}
