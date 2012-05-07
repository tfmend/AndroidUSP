package br.usp.ime.rusp.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class WebserviceClient {

	public void writeToMapper(String url, JSONMapper<?> mapper)
			throws Exception {
		String result = this.readWebService(url);
		mapper.read(result);
	}

	public synchronized String readWebService(String serverAdress)
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
			throw new HttpException("Failed to server connection. Host: "+serverAdress+". Response code: "+statusCode);
		}

		return builder.toString();
	}

	public void sendComment(String serverAdress, RemoteComment comment)
			throws Exception {

		HttpClient client = new DefaultHttpClient();
		URI uri = URI.create(serverAdress);
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
			throw new HttpException("Failed to server connection. Host: "+serverAdress+". Response code: "+statusCode);
		}

	}
	
}
