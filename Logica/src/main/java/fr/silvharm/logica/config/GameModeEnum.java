package fr.silvharm.logica.config;


public enum GameModeEnum {
	CHALLENGER("0", "Challenger"),
	DEFENSEUR("1", "Defenseur"),
	DUEL("2", "Duel");
	
	private String id, name;
	
	
	GameModeEnum(String id, String nameG) {
		this.id = id;
		this.name = nameG;
	}
	
	
	public String toString() {
		return this.name;
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