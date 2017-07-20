package fr.silvharm.logica.game;

import javax.swing.JPanel;

public abstract class Game extends JPanel {
	
	private Byte secretLength, triesNumber;
	private String name;
	
	
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
	
	
	public Byte getSecretLength() {
		return this.secretLength;
	}
	
	
	public Byte getTriesNumber() {
		return this.triesNumber;
	}
	
	
	/*
	 * Setters
	 */
	public void setSecretLength(Byte b) {
		this.secretLength = b;
	}
	
	
	public void setTriesNumber(Byte b) {
		this.triesNumber = b;
	}
}
