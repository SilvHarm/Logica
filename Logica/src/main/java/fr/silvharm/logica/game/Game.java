package fr.silvharm.logica.game;

import java.util.Properties;

import javax.swing.JPanel;

import fr.silvharm.logica.MainWindow;
import fr.silvharm.logica.config.GameMode;
import fr.silvharm.logica.config.PropertiesEnum;

public abstract class Game extends JPanel {
	
	protected Byte squareSecret, triesNumber, triesRemaining;
	protected char[] solutionTab;
	protected JPanel gamePanel;
	protected String answer = "", name, solution;
	
	
	protected Game() {
		this.gamePanel = new JPanel();
		this.add(gamePanel);
	}
	
	
	protected void endGame() {
		
	}
	
	
	public static void launchGame(Game game, Properties properties) {
		game.updateGameConfig(properties);
		
		if (!(properties.getProperty(PropertiesEnum.GAMEMODE.getKeyName()).equals(GameMode.DEFENSEUR.getId())))	{
			game.calculSolution();
		}		
		
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
	
	
	/***************************
	 * Abstract
	 ***************************/
	public abstract JPanel askPlayerSecret(Properties properties);
	
	
	protected abstract void calculSolution();
	
	
	protected abstract void updateGamePanel();
	
	
	/*********************
	 * Getters
	 *********************/
	public String getName() {
		return name;
	}
}
