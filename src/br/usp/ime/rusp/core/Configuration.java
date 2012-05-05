package br.usp.ime.rusp.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.util.Log;

public class Configuration {

	private static Configuration INSTANCE = null;
	
	private static final String GET_STATUS = "status";
	private static final String GET_COMMENTS = "bandejao/";
	private static final String GET_RU_RECOMMENDATIONS = "rurecommender";
	private static final String GET_TIME_RECOMMENDATIONS = "timerecommender";
	private static final String SEND_COMMENT = "bandejao/";
	
	public static final String SERVER_ADDRESS = "SERVER_ADDRESS";
	
	private static final String SERVER_DATE_PATTERN = "ss-mm-HH-dd-MM-yy";
	
	public static synchronized Configuration getInstance(Context context) {
		
		if (INSTANCE == null) {
			INSTANCE = new Configuration(context);
			INSTANCE.load();
		}
		
		return INSTANCE;
		
	}
	
	private final Context context;
	private final Map<String, String> configs;
	
	private Configuration(Context context) {
		
		this.context = context;
		this.configs = new HashMap<String, String>();
		this.configs.put(SERVER_ADDRESS, "http://192.168.1.3:8084/usp-mobile/");
		
	}
	
	public String[] getConfigurationKeys() {
		Set<String> set = this.configs.keySet(); 
		return set.toArray(new String[set.size()]);
	}
	
	public String getConfigurationValue(String key) {
		return this.configs.get(key).toString();
	}
	
	public void putConfigurationValue(String key, String value) {
		this.configs.put(key, value);
	}
	
	public void load() {
		
		
		try {
			ConfigurationDataHelper.getInstance(context).readConfiguration(this);
		} catch (Exception e) {

			Log.e("DBConfiguration", e.getMessage());
			
		}
		
	}
	
	public void save() {
		
		try {
			ConfigurationDataHelper.getInstance(context).readConfiguration(this);
		} catch (Exception e) {
			Log.e("DBConfiguration", e.getMessage());
		}
		
	}
	
	public String getURlStatus() {
		return this.getConfigurationValue(SERVER_ADDRESS)+GET_STATUS;
	}
	
	public String getURlGetComments() {
		return this.getConfigurationValue(SERVER_ADDRESS)+GET_COMMENTS;
	}
	
	public String getURlSendComments() {
		return this.getConfigurationValue(SERVER_ADDRESS)+SEND_COMMENT;
	}
	
	public String getURlTimeRecommendation() {
		return this.getConfigurationValue(SERVER_ADDRESS)+GET_TIME_RECOMMENDATIONS;
	}
	
	public String getURlRURecommendation() {
		return this.getConfigurationValue(SERVER_ADDRESS)+GET_RU_RECOMMENDATIONS;
	}	
	
}
