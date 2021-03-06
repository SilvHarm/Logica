package fr.silvharm.logica.config;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.silvharm.logica.components.MainWindow;
import fr.silvharm.logica.components.MyJComboBox;
import fr.silvharm.logica.game.AskPlayerSecretDialog;
import fr.silvharm.logica.game.Game;
import fr.silvharm.logica.game.GamesPanel;

public class GameConfigPanel extends JPanel {
	
	private Boolean hasChanged = false;
	private Dimension dim = new Dimension(Short.MAX_VALUE, 40), dim2 = new Dimension(Short.MAX_VALUE, 50);
	private JComboBox<Byte> masC;
	private JComboBox<Character> caseBox, trieBox01, trieBox02;
	private JComboBox<GameModeEnum> modeBox;
	private Properties properties;
	
	
	public GameConfigPanel() {
		properties = PropertiesHandler.getProperties();
		
		
		BorderLayout bLayout = new BorderLayout();
		bLayout.setVgap(30);
		this.setLayout(bLayout);
		
		
		JPanel startPanel = new JPanel();
		
		JLabel gameLabel = new JLabel(Game.getGame().getName());
		startPanel.add(gameLabel);
		
		this.add(startPanel, BorderLayout.PAGE_START);
		
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
		
		centerPanel.add(new Box.Filler(new Dimension(0, 5), new Dimension(0, 60), null));
		
		centerPanel.add(addModes());
		centerPanel.add(addOptions());
		
		centerPanel.add(new Box.Filler(new Dimension(0, 5), new Dimension(0, 60), null));
		
		this.add(centerPanel, BorderLayout.CENTER);
		
		
		this.add(addButtons(), BorderLayout.PAGE_END);
	}
	
	
	private JPanel addButtons() {
		JPanel buttons = new JPanel();
		
		JButton start = new JButton("Commencer à jouer");
		start.addActionListener(new LaunchGameListener());
		buttons.add(start);
		
		JButton defaultConfig = new JButton("Reset");
		defaultConfig.addActionListener(new DefaultConfigListener());
		buttons.add(defaultConfig);
		
		JButton retour = new JButton("Retour");
		retour.addActionListener(new BackListener());
		buttons.add(retour);
		
		return buttons;
	}
	
	
	private JPanel addModes() {
		JPanel modePanel = new JPanel();
		modePanel.setMaximumSize(dim);
		
		JLabel modeLabel = new JLabel("Mode de jeu:");
		modePanel.add(modeLabel);
		
		GameModeEnum[] gameMode = GameModeEnum.values();
		modeBox = new MyJComboBox<GameModeEnum>(gameMode);
		
		String propMode = properties.getProperty(PropertiesEnum.GAMEMODE.getKeyName());
		for (GameModeEnum mode : gameMode) {
			if (mode.getId().equals(propMode)) {
				modeBox.setSelectedItem(mode);
			}
		}
		
		modeBox.addActionListener(new ModeListener());
		
		modePanel.add(modeBox);
		
		return modePanel;
	}
	
	
	private JPanel addMastermind() {
		JPanel subMastermind = new JPanel();
		subMastermind.setMaximumSize(dim);
		
		JLabel masL = new JLabel("Nombre de couleurs:");
		subMastermind.add(masL);
		
		masC = new MyJComboBox<Byte>();
		for (Byte i = 4; i <= 10; i++) {
			masC.addItem(i);
		}
		
		Byte tempMas = Byte.valueOf(properties.getProperty(PropertiesEnum.COLORNUMBER.getKeyName()));
		if (tempMas < 4 || 10 < tempMas) {
			masC.setSelectedIndex(0);
		}
		else {
			masC.setSelectedIndex(tempMas - 4);
		}
		
		masC.addActionListener(new ColorListener());
		
		subMastermind.add(masC);
		
		return subMastermind;
	}
	
	
	private JPanel addOptions() {
		JPanel options = new JPanel();
		options.setLayout(new BoxLayout(options, BoxLayout.PAGE_AXIS));
		
		
		JPanel squareSecretP = new JPanel();
		squareSecretP.setMaximumSize(dim);
		
		JLabel squareSecretL = new JLabel("Longueur de la solution:");
		squareSecretP.add(squareSecretL);
		
		caseBox = new MyJComboBox<Character>(new Character[] { '1', '2', '3', '4', '5', '6', '7', '8', '9' });
		caseBox.setSelectedIndex(Integer.valueOf(properties.getProperty(PropertiesEnum.SQUARESECRET.getKeyName())) - 1);
		caseBox.addActionListener(new CaseListener());
		
		squareSecretP.add(caseBox);
		
		options.add(Box.createRigidArea(dim2));
		options.add(squareSecretP);
		
		
		JPanel triesP = new JPanel();
		triesP.setMaximumSize(dim);
		
		JLabel triesL = new JLabel("Nombre d'essais:");
		triesP.add(triesL);
		
		String[] trieStr = properties.getProperty(PropertiesEnum.TRIESNUMBER.getKeyName()).split("");
		
		trieBox01 = new MyJComboBox<Character>(new Character[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' });
		trieBox01.addActionListener(new TrieListener());
		
		trieBox02 = new MyJComboBox<Character>(new Character[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' });
		trieBox02.addActionListener(new TrieListener());
		
		if (trieStr.length == 1) {
			trieBox01.setSelectedIndex(0);
			trieBox02.setSelectedIndex(Integer.valueOf(trieStr[0]));
		}
		else {
			trieBox01.setSelectedIndex(Integer.valueOf(trieStr[0]));
			trieBox02.setSelectedIndex(Integer.valueOf(trieStr[1]));
		}
		
		triesP.add(trieBox01);
		triesP.add(trieBox02);
		
		options.add(Box.createRigidArea(dim2));
		options.add(triesP);
		
		
		if (Game.getGame().getName().equals("Mastermind")) {
			options.add(Box.createRigidArea(dim2));
			options.add(addMastermind());
		}
		
		
		return options;
	}
	
	
	private void updateProperties(String propNam, String propValue) {
		hasChanged = true;
		
		properties.setProperty(propNam, propValue);
	}
	
	
	/*****************************
	 * Listeners
	 *****************************/
	class BackListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			MainWindow.getMainWindow().setView(new GamesPanel());
		}
	}
	
	
	class CaseListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			updateProperties(PropertiesEnum.SQUARESECRET.getKeyName(), ((Character) caseBox.getSelectedItem()).toString());
		}
	}
	
	
	class ColorListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			updateProperties(PropertiesEnum.COLORNUMBER.getKeyName(), masC.getSelectedItem().toString());
		}
	}
	
	
	class DefaultConfigListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			String defaultMode = PropertiesEnum.GAMEMODE.getDefaultValue();
			for (GameModeEnum mode : GameModeEnum.values()) {
				if (mode.getId().equals(defaultMode)) {
					modeBox.setSelectedItem(mode);
				}
			}
			
			caseBox.setSelectedIndex(Integer.valueOf(PropertiesEnum.SQUARESECRET.getDefaultValue()) - 1);
			
			String[] trieStr = PropertiesEnum.TRIESNUMBER.getDefaultValue().split("");
			if (trieStr.length == 1) {
				trieBox01.setSelectedIndex(0);
				trieBox02.setSelectedIndex(Integer.valueOf(trieStr[0]));
			}
			else {
				trieBox01.setSelectedIndex(Integer.valueOf(trieStr[0]));
				trieBox02.setSelectedIndex(Integer.valueOf(trieStr[1]));
			}
			
			if (Game.getGame().getName().equals("Mastermind")) {
				masC.setSelectedIndex(Byte.valueOf(PropertiesEnum.COLORNUMBER.getDefaultValue()) - 4);
			}
		}
	}
	
	
	class ModeListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			updateProperties(PropertiesEnum.GAMEMODE.getKeyName(), ((GameModeEnum) modeBox.getSelectedItem()).getId());
		}
	}
	
	
	class TrieListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			String str = ((Character) trieBox01.getSelectedItem()).toString()
					+ ((Character) trieBox02.getSelectedItem()).toString();
			
			updateProperties(PropertiesEnum.TRIESNUMBER.getKeyName(), str);
		}
	}
	
	
	class LaunchGameListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (hasChanged) {
				PropertiesHandler.updateProperties();
				
				hasChanged = false;
			}
			
			// if game mode isn't challenger
			if (!properties.getProperty(PropertiesEnum.GAMEMODE.getKeyName()).equals(GameModeEnum.CHALLENGER.getId())) {
				new AskPlayerSecretDialog();
			}
			else {
				Game.launchGame();
			}
		}
	}
}