package fr.silvharm.logica.game;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.LinkedHashMap;
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
	
	protected int colorNumber;
	protected int[] colorCounter, presentCounter;
	protected ColorEnum[] colorTab;
	protected Map<String, Integer> boxMap;
	
	
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
	class ColorButListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			MainWindow.getMainWindow().requestFocus();
			
			nextColor((ColorButton) arg0.getSource(), 1);
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
