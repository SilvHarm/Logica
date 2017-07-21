package fr.silvharm.logica.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.silvharm.logica.MainWindow;
import fr.silvharm.logica.game.Game;
import fr.silvharm.logica.game.GamesView;

public class GameConfigView extends JPanel {
	
	private Boolean hasChanged = false;
	private Game game;
	private JComboBox<Byte> masC;
	private JComboBox<GameMode> modeBox;
	JTextField squareSecretF, triesF;
	private Properties properties;
	
	
	public GameConfigView(Game gameG) {
		game = gameG;
		properties = PropertiesHandler.getProperties();
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JLabel gameLabel = new JLabel(game.getName());
		gameLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.add(gameLabel);
		
		this.add(addModes());
		
		this.add(addOptions());
		
		this.add(addButtons());
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
		retour.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				MainWindow.getMainWindow().setView(new GamesView());
			}
		});
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
		
		squareSecretF = new JTextField();
		squareSecretF.setColumns(3);
		squareSecretF.setText(properties.getProperty(PropertiesEnum.SQUARESECRET.getKeyName()));
		squareSecretP.add(squareSecretF);
		
		options.add(squareSecretP);
		
		
		JPanel triesP = new JPanel();
		
		JLabel triesL = new JLabel("Nombre d'essais:");
		triesP.add(triesL);
		
		triesF = new JTextField();
		triesF.setColumns(3);
		triesF.setText(properties.getProperty(PropertiesEnum.TRIESNUMBER.getKeyName()));
		
		triesP.add(triesF);
		
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
	class ColorListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			updateProperties(PropertiesEnum.COLORNUMBER.getKeyName(), masC.getSelectedItem().toString());
		}
	}
	
	
	class DefaultConfigListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			squareSecretF.setText(PropertiesEnum.SQUARESECRET.getDefaultValue());
			
			triesF.setText(PropertiesEnum.TRIESNUMBER.getDefaultValue());
			
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
	
	
	class LaunchGameListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (hasChanged) {
				PropertiesHandler.updateProperties(properties);
				
				hasChanged = false;
			}
			
			game.launchGame(properties);
		}
	}
}