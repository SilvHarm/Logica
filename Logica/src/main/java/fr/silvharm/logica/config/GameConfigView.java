package fr.silvharm.logica.config;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.silvharm.logica.MainWindow;
import fr.silvharm.logica.game.Game;
import fr.silvharm.logica.game.GamesView;

public class GameConfigView extends JPanel {
	
	private Boolean hasChanged = false;
	private Game game;
	private JComboBox<Byte> masC;
	private JComboBox<Character> caseBox, trieBox01, trieBox02;
	private JComboBox<GameMode> modeBox;
	private Properties properties;
	
	
	public GameConfigView(Game gameG) {
		game = gameG;
		properties = PropertiesHandler.getProperties();
		
		BorderLayout bLayout = new BorderLayout();
		bLayout.setVgap(30);
		this.setLayout(bLayout);
		
		JPanel startPanel = new JPanel();
		JLabel gameLabel = new JLabel(game.getName());
		gameLabel.setAlignmentX(CENTER_ALIGNMENT);
		startPanel.add(gameLabel);
		this.add(startPanel, BorderLayout.PAGE_START);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
		
		centerPanel.add(addModes());
		
		centerPanel.add(addOptions());
		
		this.add(centerPanel, BorderLayout.CENTER);
		
		
		this.add(addButtons(), BorderLayout.PAGE_END);
	}
	
	
	private JPanel addButtons() {
		JPanel buttons = new JPanel();
		
		JButton start = new JButton("Commencer � jouer");
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
		
		JLabel modeLabel = new JLabel("Mode de jeu:");
		modePanel.add(modeLabel);
		
		GameMode[] gameMode = GameMode.values();
		modeBox = new JComboBox<GameMode>(gameMode);
		
		String propMode = properties.getProperty(PropertiesEnum.GAMEMODE.getKeyName());
		for (GameMode mode : gameMode) {
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
		
		JLabel masL = new JLabel("Nombre de couleurs:");
		subMastermind.add(masL);
		
		masC = new JComboBox<Byte>();
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
		
		JLabel squareSecretL = new JLabel("Longueur de la solution:");
		squareSecretP.add(squareSecretL);
		
		caseBox = new JComboBox<Character>(new Character[] { '1', '2', '3', '4', '5', '6', '7', '8', '9' });
		caseBox.setSelectedIndex(Integer.valueOf(properties.getProperty(PropertiesEnum.SQUARESECRET.getKeyName())) - 1);
		caseBox.addActionListener(new CaseListener());
		
		squareSecretP.add(caseBox);
		
		options.add(squareSecretP);
		
		
		JPanel triesP = new JPanel();
		
		JLabel triesL = new JLabel("Nombre d'essais:");
		triesP.add(triesL);
		
		String[] trieStr = properties.getProperty(PropertiesEnum.TRIESNUMBER.getKeyName()).split("");
		
		trieBox01 = new JComboBox<Character>(new Character[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' });
		trieBox01.setSelectedIndex(Integer.valueOf(trieStr[0]));
		trieBox01.addActionListener(new TrieListener());
		
		trieBox02 = new JComboBox<Character>(new Character[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' });
		trieBox02.setSelectedIndex(Integer.valueOf(trieStr[1]));
		trieBox02.addActionListener(new TrieListener());
		
		triesP.add(trieBox01);
		triesP.add(trieBox02);
		
		options.add(triesP);
		
		
		if (game.getName().equals("Mastermind")) {
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
			MainWindow.getMainWindow().setView(new GamesView());
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
			for (GameMode mode : GameMode.values()) {
				if (mode.getId().equals(defaultMode)) {
					modeBox.setSelectedItem(mode);
				}
			}
			
			caseBox.setSelectedIndex(Integer.valueOf(PropertiesEnum.SQUARESECRET.getDefaultValue()) - 1);
			
			String[] trieStr = PropertiesEnum.TRIESNUMBER.getDefaultValue().split("");
			trieBox01.setSelectedIndex(Integer.valueOf(trieStr[0]));
			trieBox02.setSelectedIndex(Integer.valueOf(trieStr[1]));
			
			if (game.getName().equals("Mastermind")) {
				masC.setSelectedIndex(Byte.valueOf(PropertiesEnum.COLORNUMBER.getDefaultValue()) - 4);
			}
		}
	}
	
	
	class ModeListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			updateProperties(PropertiesEnum.GAMEMODE.getKeyName(), ((GameMode) modeBox.getSelectedItem()).getId());
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
				PropertiesHandler.updateProperties(properties);
				
				hasChanged = false;
			}
			
			if (properties.getProperty(PropertiesEnum.GAMEMODE.getKeyName()).equals(GameMode.DEFENSEUR.getId())) {
				new AskPlayerSecretDialog(game, properties);
			}
			else {
				Game.launchGame(game, properties);
			}
		}
	}
}