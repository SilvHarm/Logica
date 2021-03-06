package fr.silvharm.logica.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import fr.silvharm.logica.components.MainWindow;
import fr.silvharm.logica.config.GameConfigPanel;
import fr.silvharm.logica.config.GameModeEnum;
import fr.silvharm.logica.config.MyLog4j;
import fr.silvharm.logica.config.PropertiesEnum;
import fr.silvharm.logica.config.PropertiesHandler;

public abstract class Game extends JPanel {
	
	private static Boolean cheat = false;
	private static Game game;
	
	protected Byte squareSecret, triesNumber, triesRemaining;
	protected Dimension dim = new Dimension(0, 20);
	protected int endCode;
	protected int[] solutionTab, solutionPlayerTab;
	protected JButton verifyBut;
	protected JLabel triesRemLabel;
	protected JPanel gamePanel, historyPanel, infoPanel;
	protected String aiAnswer, ansResult = "", name, playerAnswer, solution, solutionPlayer;
	protected Timer timer;
	
	
	protected Game() {
		this.setGame();
		
		
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		this.infoPanel = new JPanel();
		this.add(infoPanel, BorderLayout.PAGE_START);
		
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		gamePanel = new JPanel();
		gamePanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 70));
		centerPanel.add(gamePanel);
		
		centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		
		historyPanel = new JPanel();
		historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(historyPanel);
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		centerPanel.add(scrollPane);
		
		this.add(centerPanel, BorderLayout.CENTER);
		
		
		JPanel butPanel = new JPanel();
		
		verifyBut = new JButton("Vérifier");
		verifyBut.addActionListener(new VerifyListener());
		butPanel.add(verifyBut);
		
		JButton backBut = new JButton("Retour");
		backBut.addActionListener(new BackListener());
		butPanel.add(backBut);
		
		this.add(butPanel, BorderLayout.PAGE_END);
	}
	
	
	protected void addToHistoric(int who) {
		JLabel label = new JLabel(), resultLabel = new JLabel();
		JPanel ansSolPanel = new JPanel(), pan = new JPanel();
		
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		pan.setMaximumSize(new Dimension(Short.MAX_VALUE, 70));
		
		if (who == 0) {
			label.setText("Vous avez proposé:");
			label.setForeground(Color.GREEN);
			
			ansSolPanel = this.createAnsSolPanel(playerAnswer);
		}
		else {
			label.setText("L'IA a proposé:");
			label.setForeground(Color.RED);
			
			ansSolPanel = this.createAnsSolPanel(aiAnswer);
		}
		
		resultLabel.setText("Résultat:   " + ansResult);
		
		label.setAlignmentX(CENTER_ALIGNMENT);
		ansSolPanel.setAlignmentX(CENTER_ALIGNMENT);
		resultLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		pan.add(label);
		pan.add(ansSolPanel);
		pan.add(resultLabel);
		
		historyPanel.add(pan, 0);
		historyPanel.add(Box.createRigidArea(dim), 0);
	}
	
	
	protected void endGame() {
		MainWindow.getMainWindow().setView(new EndGamePanel());
	}
	
	
	protected void initInfoPanel() {
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
		
		// left white-space
		infoPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
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
		
		if (cheat) {
			// if game mode isn't "DEFENSEUR"
			if (!PropertiesHandler.getProperties().getProperty(PropertiesEnum.GAMEMODE.getKeyName())
					.equals(GameModeEnum.DEFENSEUR.getId())) {
				
				JLabel solLabel = new JLabel("Solution: ");
				infoPanel.add(solLabel);
				
				infoPanel.add(this.createAnsSolPanel(solution));
			}
			
			
			// if game mode is "DUEL"
			if (PropertiesHandler.getProperties().getProperty(PropertiesEnum.GAMEMODE.getKeyName())
					.equals(GameModeEnum.DUEL.getId())) {
				// separator
				infoPanel.add(Box.createRigidArea(new Dimension(5, 0)));
			}
			
			
			// if game mode isn't "CHALLENGER"
			if (!PropertiesHandler.getProperties().getProperty(PropertiesEnum.GAMEMODE.getKeyName())
					.equals(GameModeEnum.CHALLENGER.getId())) {
				JLabel solLabel = new JLabel("Solution du Joueur: ");
				infoPanel.add(solLabel);
				
				infoPanel.add(this.createAnsSolPanel(solutionPlayer));
			}
			
			
			// right white-space
			infoPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		}
	}
	
	
	public static void launchGame() {
		MyLog4j.getLogger().info("Launching game: " + game.name);
		
		game.updateGameConfig();
		
		game.gamePanel.removeAll();
		
		// if gameMode isn't "DEFENSEUR"
		if (!PropertiesHandler.getProperties().getProperty(PropertiesEnum.GAMEMODE.getKeyName())
				.equals(GameModeEnum.DEFENSEUR.getId())) {
			game.calculSolution();
			
			game.initGamePanel();
		}
		
		game.infoPanel.removeAll();
		game.initInfoPanel();
		
		game.historyPanel.removeAll();
		
		game.startGame();
	}
	
	
	protected void isFinish() {
		// if Player and AI has win
		if (playerAnswer.equals(solution) && aiAnswer.equals(solutionPlayer)) {
			endCode = 4;
		}
		
		// if AI has win
		else if (aiAnswer.equals(solutionPlayer)) {
			endCode = 2;
		}
		
		// if Player has win
		else if (playerAnswer.equals(solution)) {
			endCode = 0;
		}
		else {
			
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
		}
		
		
		if (endCode != -1) {
			this.endGame();
		}
	}
	
	
	protected void startGame() {
		MyLog4j.getLogger().info("Starting game, solution is: " + solution);
		
		
		MainWindow.getMainWindow().setView(this);
		
		
		// if gameMode is "DEFENSEUR"
		if (PropertiesHandler.getProperties().getProperty(PropertiesEnum.GAMEMODE.getKeyName())
				.equals(GameModeEnum.DEFENSEUR.getId())) {
			game.whileDefenseur();
		}
	}
	
	
	protected void updateGameConfig() {
		Game.getGame().endCode = -1;
		
		aiAnswer = "";
		ansResult = "";
		playerAnswer = "";
		solution = "";
		solutionPlayer = "";
		
		
		// if gameMode is "CHALLENGER"
		if (PropertiesHandler.getProperties().getProperty(PropertiesEnum.GAMEMODE.getKeyName())
				.equals(GameModeEnum.CHALLENGER.getId())) {
			solutionPlayer = " ";
		}
		
		
		// if gameMode is "DEFENSEUR"
		if (PropertiesHandler.getProperties().getProperty(PropertiesEnum.GAMEMODE.getKeyName())
				.equals(GameModeEnum.DEFENSEUR.getId())) {
			solution = " ";
		}
		
		
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
		
		
		// if cheatMode is activated in the config
		if (Integer.valueOf(PropertiesHandler.getProperties().getProperty(PropertiesEnum.CHEATMODE.getKeyName())) == 1) {
			cheat = true;
		}
		
		
		solutionTab = new int[squareSecret];
		solutionPlayerTab = new int[squareSecret];
	}
	
	
	protected void updateTriesRemaining() {
		triesRemaining--;
		
		if (0 <= triesRemaining) {
			this.setTriesRemLabel();
		}
	}
	
	
	protected void whileDefenseur() {
		verifyBut.setVisible(false);
		
		
		timer = new Timer(2000, new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				if (endCode == -1) {
					game.aiTurn();
					
					game.updateTriesRemaining();
					
					game.isFinish();
				}
				else {
					timer.stop();
				}
				
			}
			
		});
		
		
		game.aiTurn();
		
		game.updateTriesRemaining();
		
		
		if (endCode == -1) {
			timer.start();
		}
	}
	
	
	/***************************
	 * Abstract
	 ***************************/
	protected abstract void aiTurn();
	
	
	public abstract JPanel askPlayerSecret();
	
	
	protected abstract void calculSolution();
	
	
	protected abstract JPanel createAnsSolPanel(String str);
	
	
	protected abstract void initGamePanel();
	
	
	protected abstract void verifyListenerEcho();
	
	
	/*******************************
	 * Listeners
	 *******************************/
	class BackListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			if (timer != null && timer.isRunning()) {
				timer.stop();
			}
			
			verifyBut.setVisible(true);
			
			MainWindow.getMainWindow().setView(new GameConfigPanel());
		}
	}
	
	
	class VerifyListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			game.verifyListenerEcho();
		}
	}
	
	
	/*********************
	 * Getters
	 *********************/
	public static Game getGame() {
		return game;
	}
	
	
	public String getName() {
		return name;
	}
	
	
	/**********************
	 * Setters
	 **********************/
	public static void setCheat(Boolean b) {
		cheat = b;
	}
	
	
	public void setGame() {
		game = this;
	}
	
	
	public void setTriesRemLabel() {
		triesRemLabel.setText("Essais restants: " + triesRemaining);
	}
	
}
