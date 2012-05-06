package br.usp.ime.rusp.enumerations;

public enum OnlineServices {

	GET_STATUS("status"), 
	GET_COMMENTS("bandejao/"), 
	GET_RU_RECOMMENDATIONS("rurecommender"), 
	GET_TIME_RECOMMENDATIONS("timerecommender"), 
	SEND_COMMENT("bandejao/");
	
	private final String name;
	
	private OnlineServices(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static OnlineServices getRUByName(String name) {
		
		OnlineServices result = null;
		
		for ( OnlineServices ru : OnlineServices.values() ) {
			
			String tmp = ru.getName().toLowerCase(); 
			String lname = name.toLowerCase();
			if ( tmp.equals(lname) ) {
				result = ru;
			}
		}
		
		return result;

	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public String getUrlService(String serverAddress) {
		return serverAddress+this.name;
	}
	
}
