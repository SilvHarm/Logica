package fr.silvharm.logica.game;

import java.util.Properties;

import javax.swing.JPanel;

public abstract class Game extends JPanel {
	
	private String name;
	
	
	protected Game(String nameG) {
		this.name = nameG;
	}
	
	
	protected void endGame() {
		
	}
	
	
	public void launchGame(Properties properties) {
		
	}
	
	
	protected void startGame() {
		
	}
	
	
	public void updateGame(Properties properties) {
		
	}
	
	
	/*
	 * Getters
	 */
	public String getName() {
		return this.name;
	}
}
