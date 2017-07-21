package fr.silvharm.logica.game;

import java.util.Properties;

import javax.swing.JPanel;

import fr.silvharm.logica.MainWindow;
import fr.silvharm.logica.config.PropertiesEnum;

public abstract class Game extends JPanel {
	
	protected Byte squareSecret, triesNumber, triesRemaining;
	protected char[] solutionTab;
	protected JPanel gamePanel;
	protected String name, solution;
	
	
	protected Game() {
		this.gamePanel = new JPanel();
		this.add(gamePanel);
	}
	
	
	protected void calculSolution()	{
		
	}
	
	
	protected void endGame() {
		
	}
	
	
	public static void launchGame(Game game, Properties properties) {
		game.updateGameConfig(properties);
		
		game.calculSolution();
		
		game.updateGamePanel();
		
		game.startGame();
	}
	
	
	protected void startGame() {
		MainWindow.getMainWindow().setView(this);
	}
	
	
	protected void updateGameConfig(Properties properties) {
		this.squareSecret = Byte.valueOf(properties.getProperty(PropertiesEnum.SQUARESECRET.getKeyName()));
		
		this.triesNumber = Byte.valueOf(properties.getProperty(PropertiesEnum.SQUARESECRET.getKeyName()));
		this.triesRemaining = triesNumber;
	}
	
	
	protected void updateGamePanel() {
		
	}
	
	
	/*
	 * Getters
	 */
	public String getName() {
		return name;
	}
}
