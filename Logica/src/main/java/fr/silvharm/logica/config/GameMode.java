package fr.silvharm.logica.config;


public enum GameMode {
	CHALLENGER("0", "Challenger"),
	DEFENSEUR("1", "Defenseur"),
	DUEL("2", "Duel");
	
	private String id, name;
	
	
	GameMode(String id, String nameG) {
		this.id = id;
		this.name = nameG;
	}
	
	
	/*******************************
	 * Getters
	 *******************************/
	public String getId() {
		return id;
	}
	
	
	public String getName() {
		return name;
	}
}