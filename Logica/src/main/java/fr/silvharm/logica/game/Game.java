package fr.silvharm.logica.game;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
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
	protected int endCode;
	protected JLabel triesRemLabel;
	protected JPanel gamePanel, infoPanel;
	protected String aiAnswer, name, playerAnswer, solution;
	
	
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
		MainWindow.getMainWindow().setView(new EndGamePanel());
	}
	
	
	public static Game getGame() {
		return game;
	}
	
	
	protected void initInfoPanel() {
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
		
		triesRemLabel = new JLabel();
		
		if (triesRemaining == -1) {
			triesRemLabel.setText("Essais restants: ∞");
		}
		else {
			triesRemLabel.setText("Essais restants: " + Byte.toString(triesRemaining));
		}
		
		infoPanel.add(triesRemLabel);
		
		
		// separator
		infoPanel.add(new Box.Filler(null, null, new Dimension(Integer.MAX_VALUE, 0)));
		
		
		JLabel solLabel = new JLabel("Solution: ");
		infoPanel.add(solLabel);
		
		infoPanel.add(game.createSolutionPanel());
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
	
	
	protected void isFinish() {
		// if Player and AI has win
		if (playerAnswer.equals(solution) && aiAnswer.equals(solution)) {
			endCode = 4;
		}
		
		// if AI has win
		if (aiAnswer.equals(solution)) {
			endCode = 2;
		}
		
		// if Player has win
		if (playerAnswer.equals(solution)) {
			endCode = 0;
		}
		
		
		// if lose
		if (triesRemaining == 0) {
			// if Player and AI has lost
			if (PropertiesHandler.getProperties().getProperty(PropertiesEnum.GAMEMODE.getKeyName())
					.equals(GameModeEnum.DUEL.getId())) {
				endCode = 5;
			}
			// if AI has lost
			else if (PropertiesHandler.getProperties().getProperty(PropertiesEnum.GAMEMODE.getKeyName())
					.equals(GameModeEnum.DEFENSEUR.getId())) {
				endCode = 3;
			}
			// if Player has lost
			else {
				endCode = 1;
			}
		}
		
		
		if (endCode != -1) {
			this.endGame();
		}
	}
	
	
	protected void startGame() {
		MainWindow.getMainWindow().setView(this);
	}
	
	
	protected void updateGameConfig() {
		Game.getGame().endCode = -1;
		
		aiAnswer = "";
		playerAnswer = "";
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
		
		if (0 <= game.triesRemaining) {
			Game.getGame().setTriesRemLabel();
		}
	}
	
	
	/***************************
	 * Abstract
	 ***************************/
	public abstract JPanel askPlayerSecret();
	
	
	protected abstract void calculSolution();
	
	
	protected abstract JPanel createSolutionPanel();
	
	
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
		triesRemLabel.setText("Essais restants: " + triesRemaining);
	}
	
}
