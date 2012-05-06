package br.usp.ime.rusp.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import android.content.Context;
import android.util.Log;
import br.usp.ime.rusp.enumerations.OnlineServices;
import br.usp.ime.rusp.enumerations.*;

public class Configuration {

	private static Configuration INSTANCE = null;
	public static final String SERVER_ADDRESS = "SERVER_ADDRESS";
	
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
		this.configs.put(SERVER_ADDRESS, "http://192.168.1.3:8084/");
		
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
		return OnlineServices.GET_STATUS.getUrlService(this.getConfigurationValue(SERVER_ADDRESS));
	}
	
	public String getURlGetComments(RU ru) {
		return OnlineServices.GET_COMMENTS.getUrlService(this.getConfigurationValue(SERVER_ADDRESS))+ru.getName();
	}
	
	public String getURlSendComments(RU ru) {
		return OnlineServices.SEND_COMMENT.getUrlService(this.getConfigurationValue(SERVER_ADDRESS))+ru.getName();
	}
	
	public String getURlTimeRecommendation() {
		return OnlineServices.GET_TIME_RECOMMENDATIONS.getUrlService(this.getConfigurationValue(SERVER_ADDRESS));
	}
	
	public String getURlRURecommendation() {
		return OnlineServices.GET_RU_RECOMMENDATIONS.getUrlService(this.getConfigurationValue(SERVER_ADDRESS));
	}	
	
}
