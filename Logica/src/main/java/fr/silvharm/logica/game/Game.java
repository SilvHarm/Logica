package fr.silvharm.logica.game;

import java.awt.BorderLayout;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.silvharm.logica.MainWindow;
import fr.silvharm.logica.config.GameModeEnum;
import fr.silvharm.logica.config.PropertiesEnum;
import fr.silvharm.logica.game.components.EndGamePanel;

public abstract class Game extends JPanel {
	
	protected Byte squareSecret, triesNumber, triesRemaining;
	protected char[] solutionTab;
	protected JLabel triesRemLabel;
	protected JPanel gamePanel, infoPanel;
	protected String answer = "", name, solution = "";
	
	
	protected Game() {
		BorderLayout layout = new BorderLayout();
		layout.setVgap(30);
		this.setLayout(layout);
		
		this.infoPanel = new JPanel();
		this.add(infoPanel, BorderLayout.PAGE_START);
		
		this.gamePanel = new JPanel();
		this.add(gamePanel, BorderLayout.CENTER);
	}
	
	
	protected void endGame() {
		MainWindow.getMainWindow().setView(new EndGamePanel(this, null));
	}
	
	
	public static void launchGame(Game game, Properties properties) {
		game.updateGameConfig(properties);
		
		if (!(properties.getProperty(PropertiesEnum.GAMEMODE.getKeyName()).equals(GameModeEnum.DEFENSEUR.getId()))) {
			game.calculSolution();
		}
		
		game.infoPanel.removeAll();
		game.updateInfoPanel();
		
		game.gamePanel.removeAll();
		game.updateGamePanel();
		
		game.startGame();
	}
	
	
	protected void startGame() {
		MainWindow.getMainWindow().setView(this);
		
		System.out.println(solution);
	}
	
	
	protected void updateGameConfig(Properties properties) {
		squareSecret = Byte.valueOf(properties.getProperty(PropertiesEnum.SQUARESECRET.getKeyName()));
		
		triesNumber = Byte.valueOf(properties.getProperty(PropertiesEnum.TRIESNUMBER.getKeyName()));
		
		if (triesNumber == 0) {
			triesRemaining = -1;
		}
		else {
			triesRemaining = triesNumber;
		}
	}
	
	
	protected void updateInfoPanel() {
		triesRemLabel = new JLabel();
		triesRemLabel.setText("Essais restants: " + Byte.toString(triesRemaining));
		infoPanel.add(triesRemLabel);
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
	
	
	/**********************
	 * Setters
	 **********************/
	protected void setTriesRemLabel() {
		triesRemLabel.setText("Essais restants: " + Byte.toString(triesRemaining));
	}
	
}
