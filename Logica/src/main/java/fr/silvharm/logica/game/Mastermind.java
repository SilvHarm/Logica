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

public class Mastermind extends Game {
	
	private int colorNumber;
	private int[] colorCounter, colorPossibleTab, presentCounter;
	private Map<String, Integer> boxMap;
	
	
	public Mastermind() {
		this.name = "Mastermind";
	}
	
	
	protected void aiTurn() {
		
	}
	
	
	public JPanel askPlayerSecret() {
		colorNumber = Byte
				.valueOf(PropertiesHandler.getProperties().getProperty(PropertiesEnum.COLORNUMBER.getKeyName()));
		
		squareSecret = Byte
				.valueOf(PropertiesHandler.getProperties().getProperty(PropertiesEnum.SQUARESECRET.getKeyName()));
		
		this.initColorPossibleTab();
		
		return createBoxPanel();
	}
	
	
	protected void calculSolution() {
		this.initColorPossibleTab();
		
		Random random = new Random();
		
		int temp;
		for (int i = 0; i < squareSecret; i++) {
			temp = random.nextInt(colorNumber);
			
			solution += colorPossibleTab[temp];
			
			solutionTab[i] = colorPossibleTab[temp];
		}
		
		colorCounter = colorCounter(solutionTab);
	}
	
	
	protected void colorComparison(String answer) {
		presentCounter[0] = 0;
		presentCounter[1] = 0;
		
		
		int[] answerIntTab = new int[answer.length()];
		
		for (int i = 0; i < answerIntTab.length; i++) {
			answerIntTab[i] = answer.charAt(i) - '0';
		}
		
		
		// number of time colors in answer are present
		int[] tempCounter = colorCounter(answerIntTab);
		
		for (int i = 0; i < tempCounter.length; i++) {
			if (colorCounter[i] == 0) {
				tempCounter[i] = 0;
			}
			
			else if (tempCounter[i] > colorCounter[i]) {
				tempCounter[i] = colorCounter[i];
			}
			
			presentCounter[1] += tempCounter[i];
		}
		
		
		// count the number of colors well placed
		for (int i = 0; i < solutionTab.length; i++) {
			if (answerIntTab[i] == solutionTab[i]) {
				presentCounter[0]++;
			}
		}
		
		
		ansResult = "";
		
		if (presentCounter[1] == 0) {
			ansResult += "Aucune n'est présente";
		}
		else if (presentCounter[1] == 1) {
			ansResult += presentCounter[1] + " présente";
		}
		else if (presentCounter[1] > 1) {
			ansResult += presentCounter[1] + " présentes";
		}
		
		if (presentCounter[1] != 0 && presentCounter[0] == 0) {
			ansResult += ", mais aucune n'est bien placée";
		}
		else if (presentCounter[0] == 1) {
			ansResult += ", dont " + presentCounter[0] + " bien placée";
		}
		else if (presentCounter[0] > 1) {
			ansResult += ", dont " + presentCounter[0] + " bien placées";
		}
	}
	
	
	// number of time the colors in iTab are in the solution
	protected int[] colorCounter(int[] iTab) {
		int[] temp = new int[colorNumber];
		
		for (int i = 0; i < colorNumber; i++) {
			temp[i] = 0;
			
			for (int j : iTab) {
				if (j == colorPossibleTab[i]) {
					temp[i]++;
				}
			}
		}
		
		return temp;
	}
	
	
	protected JPanel createAnsSolPanel(String str) {
		JPanel panel = new JPanel();
		
		JLabel label = new JLabel(str);
		panel.add(label);
		
		return panel;
	}
	
	
	protected JPanel createBoxPanel() {
		JPanel boxPanel = new JPanel();
		
		boxMap = new LinkedHashMap<String, Integer>();
		BoxListener boxListener = new BoxListener();
		
		
		Integer[] tempInt = new Integer[colorPossibleTab.length];
		for (int i = 0; i < colorPossibleTab.length; i++) {
			tempInt[i] = colorPossibleTab[i];
		}
		
		
		Dimension dim = new Dimension(20, 0);
		for (int i = 0; i < squareSecret; i++) {
			// add space to separate box in group of 3
			if (i != 0 && ((squareSecret - i) % 3) == 0) {
				boxPanel.add(Box.createRigidArea(dim));
			}
			
			
			JComboBox<Integer> box = new MyJComboBox<Integer>(tempInt);
			
			box.setName(Integer.toString(i));
			box.addActionListener(boxListener);
			
			boxMap.put(box.getName(), box.getItemAt(0));
			
			boxPanel.add(box);
		}
		
		return boxPanel;
	}
	
	
	protected void initGamePanel() {
		JPanel boxPanel = createBoxPanel();
		gamePanel.add(boxPanel);
	}
	
	
	// determine the colors who will be used for the game
	protected void initColorPossibleTab() {
		colorPossibleTab = new int[colorNumber];
		
		for (int i = 0; i < colorPossibleTab.length; i++) {
			colorPossibleTab[i] = 0;
		}
		
		
		boolean alreadyPresent = false;
		int temp;
		
		Random random = new Random();
		
		for (int i = 0; i < colorNumber; i++) {
			do {
				temp = random.nextInt(10);
				
				for (int j = 0; j < colorPossibleTab.length; j++) {
					if (colorPossibleTab[j] == temp) {
						alreadyPresent = true;
					}
				}
				
				if (!alreadyPresent) {
					colorPossibleTab[i] = temp;
					break;
				}
				
				alreadyPresent = false;
			} while (true);
		}
	}
	
	
	protected void updateGameConfig() {
		super.updateGameConfig();
		
		colorNumber = Byte
				.valueOf(PropertiesHandler.getProperties().getProperty(PropertiesEnum.COLORNUMBER.getKeyName()));
		
		colorCounter = new int[colorNumber];
		presentCounter = new int[2];
	}
	
	
	protected void verifyListenerEcho() {
		// if gameMode is "DUEL"
		if (PropertiesHandler.getProperties().getProperty(PropertiesEnum.GAMEMODE.getKeyName())
				.equals(GameModeEnum.DUEL.getId())) {
			this.aiTurn();
		}
		
		
		playerAnswer = "";
		
		int value;
		for (int i = 0; i < squareSecret; i++) {
			value = boxMap.get(Integer.toString(i));
			
			playerAnswer += value;
		}
		
		this.colorComparison(playerAnswer);
		
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
