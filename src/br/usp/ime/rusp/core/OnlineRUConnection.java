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

		this.sendComment(configuration.getURlSendComments(), comment);

	}

	@Override
	public List<RemoteComment> getComments(CallBackListener callBackListener)
			throws Exception {

		JSONMapper<RemoteComment> mapper = new RemoteCommentJSONMapper();
		this.writeToMapper(configuration.getURlGetComments()+currentRU.getName(), mapper);
		return mapper.getObjects();

	}

	@Override
	public List<RemoteRecommendation> getRURecommendations(
			CallBackListener callBackListener) throws Exception {
		JSONMapper<RemoteRecommendation> mapper = new RemoteRecommendationJSONMapper();
		this.writeToMapper(configuration.getURlRURecommendation(), mapper);
		List<RemoteRecommendation> result =  mapper.getObjects();
		OfflineRUConnection.getInstance(context).rurecommendations = result;
		return result;
	}

	@Override
	public List<RemoteRecommendation> getTimeRecommendation(
			CallBackListener callBackListener) throws Exception {
		JSONMapper<RemoteRecommendation> mapper = new RemoteRecommendationJSONMapper();
		this.writeToMapper(configuration.getURlTimeRecommendation(), mapper);
		List<RemoteRecommendation> result =  mapper.getObjects();
		OfflineRUConnection.getInstance(context).timerecommendations = result;
		return result;
	}

	private void writeToMapper(String url, JSONMapper<?> mapper)
			throws Exception {
		String result = this.readWebService(url);
		mapper.read(result);
	}

	private synchronized String readWebService(String serverAdress)
			throws Exception {

		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		URI uri = URI.create(serverAdress);
		HttpGet httpGet = new HttpGet(uri);

		HttpResponse response = client.execute(httpGet);
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if (statusCode == 200) {
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
		} else {
			Log.e(OnlineRUConnection.class.toString(),
					"Failed to server connection");
			throw new IllegalStateException("Failed to server connection");
		}

		return builder.toString();
	}

	private void sendComment(String serverAdress, RemoteComment comment)
			throws Exception {

		HttpClient client = new DefaultHttpClient();
		URI uri = URI.create(serverAdress + comment.ru.getName());
		HttpPost post = new HttpPost(uri);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("comentario.texto",
				comment.texto));
		nameValuePairs.add(new BasicNameValuePair("comentario.fila",
				comment.fila.getName()));

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);
		// entity.setContentType("application/x-www-form-urlencoded");
		post.setEntity(entity);

		HttpResponse response = client.execute(post);
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if (statusCode != 200) {
			Log.e(OnlineRUConnection.class.toString(),
					"Falha ao enviar mensagem.");
			throw new IllegalStateException("Falha ao enviar comentário.");
		}

	}

	@Override
	public List<RemoteRecommendation> getStatus(CallBackListener backListener)
			throws Exception {
		JSONMapper<RemoteRecommendation> mapper = new RemoteRecommendationJSONMapper();
		this.writeToMapper(configuration.getURlStatus(), mapper);
		List<RemoteRecommendation> result =  mapper.getObjects();
		OfflineRUConnection.getInstance(context).statusrecommendations = result;
		return result;
	}

	@Override
	public void setCurrentRU(RU ru) {
		currentRU = ru;
		
	}

}
