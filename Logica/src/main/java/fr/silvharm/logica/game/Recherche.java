package fr.silvharm.logica.game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.silvharm.logica.MainWindow;
import fr.silvharm.logica.config.GameConfigPanel;
import fr.silvharm.logica.config.GameModeEnum;
import fr.silvharm.logica.config.PropertiesEnum;
import fr.silvharm.logica.config.PropertiesHandler;

public class Recherche extends Game {
	
	private JLabel ansLabel;
	private Map<String, Character> boxMap;
	
	
	public Recherche() {
		this.setGame();
		
		this.name = "Recherche";
	}
	
	
	public JPanel askPlayerSecret() {
		this.squareSecret = Byte
				.valueOf(PropertiesHandler.getProperties().getProperty(PropertiesEnum.SQUARESECRET.getKeyName()));
		
		return createBoxPanel();
	}
	
	
	protected void calculSolution() {
		solution = Integer.toString(new Random().nextInt((int) Math.pow(10, squareSecret)));
		
		while (solution.length() < squareSecret) {
			solution = "0" + solution;
		}
		
		solutionTab = solution.toCharArray();
	}
	
	
	protected JPanel createBoxPanel() {
		JPanel boxPanel = new JPanel();
		
		boxMap = new LinkedHashMap<String, Character>();
		BoxListener boxListener = new BoxListener();
		
		Character[] c = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		for (int i = 0; i < squareSecret; i++) {
			JComboBox<Character> box = new JComboBox<Character>(c);
			
			box.setName(Integer.toString(i));
			box.addActionListener(boxListener);
			
			boxMap.put(box.getName(), (Character) box.getSelectedItem());
			
			boxPanel.add(box);
		}
		
		return boxPanel;
	}
	
	
	protected void initGamePanel() {
		BorderLayout layout = new BorderLayout();
		layout.setVgap(20);
		gamePanel.setLayout(layout);
		
		
		JPanel centerPanel = new JPanel();
		
		BorderLayout centLayout = new BorderLayout();
		centLayout.setVgap(20);
		centerPanel.setLayout(centLayout);
		
		centerPanel.add(createBoxPanel(), BorderLayout.CENTER);
		
		JPanel ansPanel = new JPanel();
		
		ansLabel = new JLabel();
		ansPanel.add(ansLabel);
		
		centerPanel.add(ansPanel, BorderLayout.SOUTH);
		
		gamePanel.add(centerPanel, BorderLayout.CENTER);
		
		
		JPanel butPanel = new JPanel();
		
		JButton verBut = new JButton("Vérifier");
		verBut.addActionListener(new VerifListener());
		butPanel.add(verBut);
		
		JButton backBut = new JButton("Retour");
		backBut.addActionListener(new BackListener());
		butPanel.add(backBut);
		
		gamePanel.add(butPanel, BorderLayout.PAGE_END);
	}
	
	
	protected void updateGameConfig() {
		super.updateGameConfig();
		
		// if gameMode is "DEFENSEUR"
		if (PropertiesHandler.getProperties().getProperty(PropertiesEnum.GAMEMODE.getKeyName())
				.equals(GameModeEnum.DEFENSEUR.getId())) {
			for (int i = 0; i < squareSecret; i++) {
				solution += boxMap.get(Integer.toString(i));
				
				solutionTab = solution.toCharArray();
			}
		}
	}
	
	
	/*******************************
	 * Listeners
	 *******************************/
	class BackListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			MainWindow.getMainWindow().setView(new GameConfigPanel());
		}
	}
	
	
	class BoxListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			@SuppressWarnings("rawtypes")
			JComboBox box = (JComboBox) arg0.getSource();
			
			boxMap.put(box.getName(), (Character) box.getSelectedItem());
		}
	}
	
	
	class VerifListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			Game.updateTriesRemaining();
			
			String ansCompare = "";
			
			Character c;
			for (int i = 0; i < squareSecret; i++) {
				c = boxMap.get(Integer.toString(i));
				
				answer += c;
				
				if (Integer.valueOf(c) == solutionTab[i]) {
					ansCompare += "=";
				}
				else if (Integer.valueOf(c) < solutionTab[i]) {
					ansCompare += "+";
				}
				else if (Integer.valueOf(c) > solutionTab[i]) {
					ansCompare += "-";
				}
			}
			
			// if win
			if (answer.equals(solution)) {
				Game.getGame().endGame(0);
			}
			// if lose
			else if (triesRemaining == 0) {
				Game.getGame().endGame(1);
			}
			else {
				ansLabel.setText(ansCompare);
			}
			
			answer = "";
		}
	}
}
