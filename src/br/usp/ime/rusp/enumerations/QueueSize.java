package br.usp.ime.rusp.enumerations;

public enum QueueSize {

	SEM_FILA("SEM_FILA", "Sem fila"), PEQUENA("PEQUENA", "Fila pequena"), MEDIA(
			"MEDIA", "Fila média"), GRANDE("GRANDE", "Fila grande"), MUITO_GRANDE(
			"MUITO_GRANDE", "Fila muito grande");

	private final String friendlyName;
	private final String name;

	private QueueSize(String name, String friendlyName) {
		this.friendlyName = friendlyName;
		this.name = name;
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	public String getName() {
		return name;
	}

	public static QueueSize getQueueByName(String name) {

		QueueSize result = MUITO_GRANDE;
		
		for (QueueSize qs : QueueSize.values()) {
			
			if (qs.getName().toLowerCase().equals(name.toLowerCase())) {
				result = qs;
			}
		}

		return result;

	}
	
	public static QueueSize getQueueByFriendlyName(String name) {

		for (QueueSize qs : QueueSize.values()) {
			if (qs.getFriendlyName().toUpperCase().equals(name.toUpperCase())) {
				return qs;
			}
		}

		return MUITO_GRANDE;

	}
	
	
	@Override
	public String toString() {
		return this.friendlyName.toString();
	}


}
