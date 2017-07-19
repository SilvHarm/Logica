package fr.silvharm.logica.game;

import javax.swing.JPanel;

public abstract class Game extends JPanel {
	
	private String name;
	private int secretLength, triesNumber;
	
	
	protected Game(String nameG) {
		this.name = nameG;
	}
	
	
	public void endGame() {
		
	}
	
	
	public void startGame() {
		
	}
	
	
	/*
	 * Getters
	 */
	public String getName() {
		return this.name;
	}
	
	
	public int getSecretLength() {
		return this.secretLength;
	}
	
	
	public int getTriesNumber() {
		return this.triesNumber;
	}
	
	
	/*
	 * Setters
	 */
	public void setSecretLength(int i) {
		this.secretLength = i;
	}
	
	
	public void setTriesNumber(int i) {
		this.triesNumber = i;
	}
}
