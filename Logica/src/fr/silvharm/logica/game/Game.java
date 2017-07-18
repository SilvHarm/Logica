package fr.silvharm.logica.game;

import javax.swing.JPanel;

public abstract class Game extends JPanel {
	
	private String name;
	
	
	protected Game(String nameG) {
		this.name = nameG;
	}
	
	
	public void endGame() {
		
	}
	
	
	public void startGame() {
		
	}
	
	
	public String getName() {
		return this.name;
	}
}
