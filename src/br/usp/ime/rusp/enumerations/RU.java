package br.usp.ime.rusp.enumerations;

public enum RU {

	QUIMICA("quimica","Química"), 
	PROFESSORES("professores","Professores"), 
	DIREITO("direito","Direito"), 
	FISICA("fisica","Física"), 
	SAUDE_PUBLICA("saude_publica","Saúde Pública"), 
	ENFERMAGEM("enfermagem","Enfermagem"), 
	COCESP("cocesp","COCESP"),
	CENTRAL("central","Central");
	
	private final String name;
	private final String friendlyName;
	
	private RU(String name, String friendlyName) {
		this.name = name;
		this.friendlyName = friendlyName;
	}
	
	public String getFriendlyName() {
		return friendlyName;
	}
	
	public String getName() {
		return name;
	}
	
	public static RU getRUByName(String name) {
		
		RU result = null;
		
		for ( RU ru : RU.values() ) {
			
			String tmp = ru.getName().toLowerCase(); 
			String lname = name.toLowerCase();
			if ( tmp.equals(lname) ) {
				result = ru;
			}
		}
		
		return result;

	}
	
	public static RU getRUByFriendlyName(String name) {
		
		for ( RU ru : RU.values() ) {
			if ( ru.getFriendlyName().toUpperCase().equals(name.toUpperCase()) ) {
				return ru;
			}
		}
		
		return null;

	}
	
	@Override
	public String toString() {
		return this.friendlyName.toString();
	}
	
}
