package fr.silvharm.logica.config;


public enum GameMode {
	CHALLENGER("Challenger"),
	DEFENSEUR("Defenseur"),
	DUEL("Duel");
	
	private String name;
	
	
	GameMode(String nameG) {
		this.name = nameG;
	}
	
	
	public String toString() {
		return this.name;
	}
}
