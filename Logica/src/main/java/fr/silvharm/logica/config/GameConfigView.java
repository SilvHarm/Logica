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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import fr.silvharm.logica.MainWindow;
import fr.silvharm.logica.game.Game;
import fr.silvharm.logica.game.GamesView;

public class GameConfigView extends JPanel {
	
	private Boolean hasChanged = false;
	private Game game;
	private JComboBox<Byte> masC;
	JTextField caseSecretF, triesF;
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
		start.addActionListener(new startGameListener());
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
		
		String[] gameString = new String[gameMode.length];
		for (int i = 0; i < gameMode.length; i++) {
			gameString[i] = gameMode[i].toString();
		}
		
		JComboBox<String> modeBox = new JComboBox<String>(gameString);
		modePanel.add(modeBox);
		
		return modePanel;
	}
	
	
	private JPanel addOptions() {
		JPanel options = new JPanel();
		options.setLayout(new BoxLayout(options, BoxLayout.PAGE_AXIS));
		
		
		JPanel caseSecretP = new JPanel();
		
		JLabel caseSecretL = new JLabel("Longueur de la solution:");
		caseSecretP.add(caseSecretL);
		
		caseSecretF = new JTextField();
		caseSecretF.setColumns(3);
		caseSecretF.setText(properties.getProperty("secretCase"));
		caseSecretP.add(caseSecretF);
		
		options.add(caseSecretP);
		
		
		JPanel triesP = new JPanel();
		
		JLabel triesL = new JLabel("Nombre d'essais:");
		triesP.add(triesL);
		
		triesF = new JTextField();
		triesF.setColumns(3);
		triesF.setText(properties.getProperty("triesNumber"));
		triesF.getDocument().addDocumentListener(new TriesListener());
		
		triesP.add(triesF);
		
		
		options.add(triesP);
		
		
		// Mastermind
		if (game.getName().equals("Mastermind")) {
			JPanel subMastermind = new JPanel();
			
			JLabel masL = new JLabel("Nombre de couleurs:");
			subMastermind.add(masL);
			
			masC = new JComboBox<Byte>();
			for (Byte i = 4; i <= 10; i++) {
				masC.addItem(i);
			}
			
			Byte tempMas;
			try {
				tempMas = Byte.valueOf(properties.getProperty("colorNumber"));
			}
			catch (NumberFormatException e) {
				tempMas = 4;
			}
			
			if (tempMas < 4 || 10 < tempMas) {
				masC.setSelectedIndex(0);
			}
			else {
				masC.setSelectedIndex(tempMas - 4);
			}
			
			masC.addActionListener(new ModeListener());
			
			subMastermind.add(masC);
			
			options.add(subMastermind);
		}
		
		
		return options;
	}
	
	
	private void updateProperties(String propNam, String propValue) {
		hasChanged = true;
		
		properties.setProperty(propNam, propValue);
	}
	
	
	/*****************************
	 * Listeners
	 ****************************/
	class DefaultConfigListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			caseSecretF.setText(PropertiesHandler.defCaseSecret);
			
			triesF.setText(PropertiesHandler.defTriesNumber);
			
			if (game.getName().equals("Mastermind")) {
				masC.setSelectedIndex(Byte.valueOf(PropertiesHandler.defColorNumber) - 4);
			}
		}
	}
	
	
	class ModeListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			updateProperties("colorNumber", masC.getSelectedItem().toString());
		}
	}
	
	
	class TriesListener implements DocumentListener {
		
		public void changedUpdate(DocumentEvent doc) {
			
		}
		
		
		public void insertUpdate(DocumentEvent doc) {
			
		}
		
		
		public void removeUpdate(DocumentEvent doc) {
			
		}
		
	}
	
	
	class startGameListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (hasChanged) {
				PropertiesHandler.updateProperties(properties);
				
				hasChanged = false;
			}
			
			game.setSecretLength(Byte.valueOf(properties.getProperty("secretCase")));
			game.setTriesNumber(Byte.valueOf(properties.getProperty("triesNumber")));
			
			if (game.getName().equals("Mastermind")) {
				
			}
			
			game.startGame();
		}
	}
}