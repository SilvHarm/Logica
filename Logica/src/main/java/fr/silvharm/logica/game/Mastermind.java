package fr.silvharm.logica.game;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.Box;
import javax.swing.JPanel;

import fr.silvharm.logica.MainWindow;
import fr.silvharm.logica.components.ColorButton;
import fr.silvharm.logica.config.ColorEnum;
import fr.silvharm.logica.config.GameModeEnum;
import fr.silvharm.logica.config.PropertiesEnum;
import fr.silvharm.logica.config.PropertiesHandler;

public class Mastermind extends Game {
	
	protected ColorEnum[] colorTab;
	protected int colorNumber;
	protected int[] presentCounter, solColorCounter;
	protected Map<String, Integer> boxMap;
	
	protected int aiColorIndex, aiTabIndex;
	protected int[] aiActualCounter, aiAnswerTab, aiPrevCounter;
	protected List<Integer> aiPossiColor;
	
	
	public Mastermind() {
		this.name = "Mastermind";
	}
	
	
	protected void aiConfigUpdate() {
		aiAnswerTab = new int[squareSecret];
		
		int tempId = colorTab[colorNumber - 1].getId();
		for (int i = 0; i < aiAnswerTab.length; i++) {
			aiAnswerTab[i] = tempId;
		}
		
		
		aiPossiColor = new ArrayList<Integer>();
		for (ColorEnum color : colorTab) {
			aiPossiColor.add(color.getId());
		}
	}
	
	
	protected void aiTurn() {
		// if it's the first turn
		if (triesRemaining == triesNumber) {
			this.aiConfigUpdate();
		}
		// else if it's not the second turn
		else if (triesRemaining != triesNumber - 1) {
			// if there is one less well placed color compared to before
			if (aiActualCounter[0] < aiPrevCounter[0]) {
				if (aiColorIndex - 2 < 0) {
					aiAnswerTab[aiTabIndex] = aiPossiColor.get(aiPossiColor.size() - 1);
				}
				else {
					aiAnswerTab[aiTabIndex] = aiPossiColor.get(aiColorIndex - 2);
				}
				
				aiActualCounter[0]++;
				aiActualCounter[1]++;
				
				aiColorIndex = 0;
				aiTabIndex++;
			}
			// if there is one more well placed color compared to before
			else if (aiActualCounter[0] > aiPrevCounter[0]) {
				aiColorIndex = 0;
				aiTabIndex++;
			}
		}
		
		
		aiAnswerTab[aiTabIndex] = aiPossiColor.get(aiColorIndex);
		
		aiColorIndex++;
		
		
		aiAnswer = "";
		for (int i : aiAnswerTab) {
			aiAnswer += Integer.toString(i);
		}
		
		this.answerVerification(aiAnswer);
		
		aiPrevCounter[0] = aiActualCounter[0];
		aiPrevCounter[1] = aiActualCounter[1];
		
		aiActualCounter[0] = presentCounter[0];
		aiActualCounter[1] = presentCounter[1];
		
		this.addToHistoric(1);
	}
	
	
	protected void answerVerification(String answer) {
		presentCounter[0] = 0;
		presentCounter[1] = 0;
		
		
		int[] answerIntTab = new int[answer.length()];
		
		for (int i = 0; i < answerIntTab.length; i++) {
			answerIntTab[i] = answer.charAt(i) - '0';
		}
		
		
		// number of time colors in answer are present
		int[] tempCounter = colorCounter(answerIntTab);
		
		for (int i = 0; i < tempCounter.length; i++) {
			if (solColorCounter[i] == 0) {
				tempCounter[i] = 0;
			}
			
			else if (tempCounter[i] > solColorCounter[i]) {
				tempCounter[i] = solColorCounter[i];
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
	
	
	public JPanel askPlayerSecret() {
		colorNumber = Byte
				.valueOf(PropertiesHandler.getProperties().getProperty(PropertiesEnum.COLORNUMBER.getKeyName()));
		
		squareSecret = Byte
				.valueOf(PropertiesHandler.getProperties().getProperty(PropertiesEnum.SQUARESECRET.getKeyName()));
		
		this.initColorPossibleTab();
		
		return createButPanel();
	}
	
	
	protected void calculSolution() {
		this.initColorPossibleTab();
		
		Random random = new Random();
		
		int temp;
		for (int i = 0; i < squareSecret; i++) {
			temp = random.nextInt(colorNumber);
			
			solution += colorTab[temp].getId();
			
			solutionTab[i] = colorTab[temp].getId();
		}
		
		solColorCounter = colorCounter(solutionTab);
	}
	
	
	// number of time the colors in iTab are in the solution
	protected int[] colorCounter(int[] iTab) {
		int[] temp = new int[colorNumber];
		
		for (int i = 0; i < colorNumber; i++) {
			temp[i] = 0;
			
			for (int j : iTab) {
				if (j == colorTab[i].getId()) {
					temp[i]++;
				}
			}
		}
		
		return temp;
	}
	
	
	protected JPanel createAnsSolPanel(String str) {
		char[] cTab = str.toCharArray();
		
		
		JPanel panel = new JPanel();
		
		Dimension dim = new Dimension(20, 0);
		for (int i = 0; i < squareSecret; i++) {
			// add space to separate box in group of 3
			if (i != 0 && ((squareSecret - i) % 3) == 0) {
				panel.add(Box.createRigidArea(dim));
			}
			
			ColorButton button = new ColorButton();
			
			for (ColorEnum col : colorTab) {
				if (col.getId() == (cTab[i] - '0')) {
					button.setBackground(col.getColor());
					
					button.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent arg0) {
							MainWindow.getMainWindow().requestFocus();
						}
					});
				}
			}
			
			panel.add(button);
		}
		
		return panel;
	}
	
	
	protected JPanel createButPanel() {
		JPanel butPanel = new JPanel();
		
		boxMap = new LinkedHashMap<String, Integer>();
		ColorButListener colorListener = new ColorButListener();
		ColorWheelListener colorWheelListener = new ColorWheelListener();
		
		
		Dimension dim = new Dimension(20, 0);
		for (int i = 0; i < squareSecret; i++) {
			// add space to separate box in group of 3
			if (i != 0 && ((squareSecret - i) % 3) == 0) {
				butPanel.add(Box.createRigidArea(dim));
			}
			
			ColorButton button = new ColorButton(colorTab[0].getId(), 0);
			
			button.setName(Integer.toString(i));
			button.setBackground(colorTab[0].getColor());
			
			button.addActionListener(colorListener);
			button.addMouseWheelListener(colorWheelListener);
			
			boxMap.put(button.getName(), button.getColorId());
			
			butPanel.add(button);
		}
		
		return butPanel;
	}
	
	
	protected void initGamePanel() {
		JPanel boxPanel = createButPanel();
		gamePanel.add(boxPanel);
	}
	
	
	// determine the colors who will be used for the game
	protected void initColorPossibleTab() {
		colorTab = new ColorEnum[colorNumber];
		
		boolean alreadyPresent = false;
		ColorEnum[] tempColor = ColorEnum.values();
		int temp;
		
		Random random = new Random();
		
		for (int i = 0; i < colorNumber; i++) {
			do {
				temp = random.nextInt(10);
				
				for (int j = 0; j < colorTab.length; j++) {
					if (colorTab[j] != null && colorTab[j] == tempColor[temp]) {
						alreadyPresent = true;
					}
				}
				
				if (!alreadyPresent) {
					colorTab[i] = tempColor[temp];
					break;
				}
				
				alreadyPresent = false;
			} while (true);
		}
	}
	
	
	protected void nextColor(ColorButton button, int modifier) {
		int i = button.getTabId();
		
		if (i + modifier >= colorTab.length) {
			i = 0;
		}
		else if (i + modifier < 0) {
			i = colorTab.length - 1;
		}
		else {
			i += modifier;
		}
		
		button.setTabId(i);
		
		button.setBackground(colorTab[i].getColor());
		
		boxMap.put(button.getName(), colorTab[i].getId());
	}
	
	
	protected void updateGameConfig() {
		super.updateGameConfig();
		
		colorNumber = Byte
				.valueOf(PropertiesHandler.getProperties().getProperty(PropertiesEnum.COLORNUMBER.getKeyName()));
		
		solColorCounter = new int[colorNumber];
		presentCounter = new int[2];
		
		
		aiActualCounter = new int[] { 0, 0 };
		aiColorIndex = 0;
		aiPrevCounter = new int[] { 0, 0 };
		aiTabIndex = 0;
		
		
		// if gameMode is "DEFENSEUR"
		if (PropertiesHandler.getProperties().getProperty(PropertiesEnum.GAMEMODE.getKeyName())
				.equals(GameModeEnum.DEFENSEUR.getId())) {
			for (int i = 0; i < squareSecret; i++) {
				solutionTab[i] = boxMap.get(Integer.toString(i));
				
				solution += solutionTab[i];
				
				solColorCounter = colorCounter(solutionTab);
			}
		}
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
		
		
		this.answerVerification(playerAnswer);
		
		this.addToHistoric(0);
		
		this.updateTriesRemaining();
		
		this.isFinish();
	}
	
	
	/*******************************
	 * Listeners
	 *******************************/
	class ColorButListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			ColorButton but = (ColorButton) arg0.getSource();
			
			but.getParent().requestFocus();
			
			nextColor(but, 1);
		}
	}
	
	
	class ColorWheelListener implements MouseWheelListener {
		
		public void mouseWheelMoved(MouseWheelEvent arg0) {
			if (arg0.getWheelRotation() > 0) {
				nextColor((ColorButton) arg0.getSource(), -1);
			}
			else {
				nextColor((ColorButton) arg0.getSource(), 1);
			}
		}
	}
}
