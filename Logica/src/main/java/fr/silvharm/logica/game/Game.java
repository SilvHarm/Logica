package fr.silvharm.logica.game;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.silvharm.logica.MainWindow;
import fr.silvharm.logica.config.GameModeEnum;
import fr.silvharm.logica.config.PropertiesEnum;
import fr.silvharm.logica.config.PropertiesHandler;

public abstract class Game extends JPanel {
	
	private static Game game;
	
	protected Byte squareSecret, triesNumber, triesRemaining;
	protected char[] solutionTab;
	protected JLabel triesRemLabel;
	protected JPanel gamePanel, infoPanel;
	protected String answer, name, solution;
	
	
	protected Game() {
		BorderLayout layout = new BorderLayout();
		layout.setVgap(30);
		this.setLayout(layout);
		
		this.infoPanel = new JPanel();
		this.add(infoPanel, BorderLayout.PAGE_START);
		
		this.gamePanel = new JPanel();
		this.add(gamePanel, BorderLayout.CENTER);
	}
	
	
	protected void endGame(int endCode) {
		MainWindow.getMainWindow().setView(new EndGamePanel(endCode));
	}
	
	
	public static Game getGame() {
		return game;
	}
	
	
	protected void initInfoPanel() {
		triesRemLabel = new JLabel();
		
		if (triesRemaining == -1) {
			triesRemLabel.setText("Essais restants: ∞");
		}
		else {
			triesRemLabel.setText("Essais restants: " + Byte.toString(triesRemaining));
		}
		
		infoPanel.add(triesRemLabel);
	}
	
	
	public static void launchGame() {
		game.updateGameConfig();
		
		// if gameMode isn't "DEFENSEUR"
		if (!(PropertiesHandler.getProperties().getProperty(PropertiesEnum.GAMEMODE.getKeyName())
				.equals(GameModeEnum.DEFENSEUR.getId()))) {
			game.calculSolution();
		}
		
		game.infoPanel.removeAll();
		game.initInfoPanel();
		
		game.gamePanel.removeAll();
		game.initGamePanel();
		
		game.startGame();
	}
	
	
	protected void startGame() {
		MainWindow.getMainWindow().setView(this);
		
		System.out.println(solution);
	}
	
	
	protected void updateGameConfig() {
		answer = "";
		solution = "";
		
		squareSecret = Byte
				.valueOf(PropertiesHandler.getProperties().getProperty(PropertiesEnum.SQUARESECRET.getKeyName()));
		
		triesNumber = Byte
				.valueOf(PropertiesHandler.getProperties().getProperty(PropertiesEnum.TRIESNUMBER.getKeyName()));
		
		if (triesNumber == 0) {
			triesRemaining = -1;
		}
		else {
			triesRemaining = triesNumber;
		}
	}
	
	
	protected static void updateTriesRemaining() {
		game.triesRemaining--;
		
		if (0 < game.triesRemaining) {
			Game.getGame().setTriesRemLabel();
		}
	}
	
	
	/***************************
	 * Abstract
	 ***************************/
	public abstract JPanel askPlayerSecret();
	
	
	protected abstract void calculSolution();
	
	
	protected abstract void initGamePanel();
	
	
	/*********************
	 * Getters
	 *********************/
	public String getName() {
		return name;
	}
	
	
	/**********************
	 * Setters
	 **********************/
	protected void setGame() {
		game = this;
	}
	
	
	protected void setTriesRemLabel() {
		triesRemLabel.setText("Essais restants: " + Byte.toString(triesRemaining));
	}
	
}
