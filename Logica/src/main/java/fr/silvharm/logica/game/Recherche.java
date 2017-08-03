package fr.silvharm.logica.game;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.silvharm.logica.components.MyJComboBox;
import fr.silvharm.logica.config.GameModeEnum;
import fr.silvharm.logica.config.PropertiesEnum;
import fr.silvharm.logica.config.PropertiesHandler;

public class Recherche extends Game {
	
	protected Boolean luckyAI;
	protected int[][] aiMemory;
	protected Map<String, Integer> boxMap;
	
	
	public Recherche() {
		this.name = "Recherche";
	}
	
	
	protected void aiTurn() {
		aiAnswer = "";
		ansResult = "";
		
		int aiProp;
		for (int i = 0; i < squareSecret; i++) {
			
			if (aiMemory[0][i] == aiMemory[1][i]) {
				aiAnswer += Integer.toString(aiMemory[0][i]);
				
				ansResult += "=";
				
				continue;
			}
			
			
			// if AI feel lucky
			if (luckyAI) {
				aiProp = new Random().nextInt(aiMemory[1][i] - aiMemory[0][i] + 1) + aiMemory[0][i];
			}
			// else it's will play logically
			else {
				aiProp = (int) Math.round((aiMemory[1][i] + aiMemory[0][i] + 0.1) / 2);
			}
			
			
			aiAnswer += aiProp;
			
			if (aiProp == solutionTab[i]) {
				aiMemory[0][i] = aiProp;
				aiMemory[1][i] = aiProp;
				
				ansResult += "=";
			}
			else if (aiProp < solutionTab[i]) {
				aiMemory[0][i] = aiProp + 1;
				
				ansResult += "+";
			}
			else if (aiProp > solutionTab[i]) {
				aiMemory[1][i] = aiProp - 1;
				
				ansResult += "-";
			}
		}
		
		ansResult = moreSpaceResult(ansResult);
		
		
		this.addToHistoric(1);
		
		this.isFinish();
	}
	
	
	public JPanel askPlayerSecret() {
		squareSecret = Byte
				.valueOf(PropertiesHandler.getProperties().getProperty(PropertiesEnum.SQUARESECRET.getKeyName()));
		
		return createBoxPanel();
	}
	
	
	protected void calculSolution() {
		solution = Integer.toString(new Random().nextInt((int) Math.pow(10, squareSecret)));
		
		while (solution.length() < squareSecret) {
			solution = "0" + solution;
		}
		
		
		for (int i = 0; i < solution.length(); i++) {
			solutionTab[i] = solution.charAt(i) - '0';
		}
	}
	
	
	protected JPanel createBoxPanel() {
		JPanel boxPanel = new JPanel();
		
		boxMap = new LinkedHashMap<String, Integer>();
		BoxListener boxListener = new BoxListener();
		
		Dimension dim = new Dimension(20, 0);
		Integer[] iTab = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		for (int i = 0; i < squareSecret; i++) {
			// add space to separate box in group of 3
			if (i != 0 && ((squareSecret - i) % 3) == 0) {
				boxPanel.add(Box.createRigidArea(dim));
			}
			
			
			JComboBox<Integer> box = new MyJComboBox<Integer>(iTab);
			
			box.setName(Integer.toString(i));
			box.addActionListener(boxListener);
			
			boxMap.put(box.getName(), 0);
			
			boxPanel.add(box);
		}
		
		return boxPanel;
	}
	
	
	protected JPanel createAnsSolPanel(String str) {
		str = moreSpace(str);
		
		JPanel panel = new JPanel();
		
		JLabel label = new JLabel(str);
		
		
		panel.add(label);
		
		return panel;
	}
	
	
	protected void initGamePanel() {
		JPanel boxPanel = createBoxPanel();
		boxPanel.setAlignmentX(CENTER_ALIGNMENT);
		gamePanel.add(boxPanel);
	}
	
	
	// put a space all 3 characters
	protected String moreSpace(String str) {
		if (str.length() > 3) {
			String[] strTab = str.split("");
			str = "";
			
			for (int i = strTab.length - 1, j = 0; i >= 0; i--, j++) {
				if (j == 3) {
					str = " " + str;
					
					j = 0;
				}
				
				str = strTab[i] + str;
			}
		}
		
		return str;
	}
	
	
	// put 5 spaces all 3 characters
	protected String moreSpaceResult(String str) {
		String[] strTab = str.split("");
		str = "";
		
		for (int i = strTab.length - 1, j = 0; i >= 0; i--, j++) {
			if (j == 3) {
				str = strTab[i] + "     " + str;
				
				j = 0;
			}
			else {
				str = strTab[i] + "  " + str;
			}
		}
		
		return str;
	}
	
	
	protected void updateGameConfig() {
		super.updateGameConfig();
		
		aiMemory = new int[2][squareSecret];
		
		// if gameMode isn't "CHALLENGER"
		if (!PropertiesHandler.getProperties().getProperty(PropertiesEnum.GAMEMODE.getKeyName())
				.equals(GameModeEnum.CHALLENGER.getId())) {
			// initialization of AI memory
			for (int i = 0; i < squareSecret; i++) {
				aiMemory[0][i] = 0;
				aiMemory[1][i] = 9;
			}
			
			
			// AI will play logically except if it's feel lucky
			luckyAI = false;
			if (new Random().nextInt(10) < 4) {
				luckyAI = true;
			}
		}
		
		
		// if gameMode is "DEFENSEUR"
		if (PropertiesHandler.getProperties().getProperty(PropertiesEnum.GAMEMODE.getKeyName())
				.equals(GameModeEnum.DEFENSEUR.getId())) {
			for (int i = 0; i < squareSecret; i++) {
				solutionTab[i] = boxMap.get(Integer.toString(i));
				
				solution += solutionTab[i];
			}
		}
	}
	
	
	protected void verifyListenerEcho() {
		// if gameMode is "DUEL"
		if (PropertiesHandler.getProperties().getProperty(PropertiesEnum.GAMEMODE.getKeyName())
				.equals(GameModeEnum.DUEL.getId())) {
			this.aiTurn();
		}
		
		
		ansResult = "";
		playerAnswer = "";
		
		int value;
		for (int i = 0; i < squareSecret; i++) {
			value = boxMap.get(Integer.toString(i));
			
			playerAnswer += value;
			
			if (value == solutionTab[i]) {
				ansResult += "=";
				
				if (aiMemory[0][i] != value || aiMemory[1][i] != value) {
					aiMemory[0][i] = value;
					aiMemory[1][i] = value;
				}
			}
			else if (value < solutionTab[i]) {
				ansResult += "+";
				
				if (aiMemory[0][i] <= value) {
					aiMemory[0][i] = value + 1;
				}
			}
			else if (value > solutionTab[i]) {
				ansResult += "-";
				
				if (aiMemory[1][i] >= value) {
					aiMemory[1][i] = value - 1;
				}
			}
		}
		
		ansResult = moreSpaceResult(ansResult);
		
		
		this.addToHistoric(0);
		
		this.updateTriesRemaining();
		
		this.isFinish();
	}
	
	
	/*******************************
	 * Listeners
	 *******************************/
	class BoxListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			@SuppressWarnings("rawtypes")
			JComboBox box = (JComboBox) arg0.getSource();
			
			boxMap.put(box.getName(), (Integer) box.getSelectedItem());
		}
	}
}
