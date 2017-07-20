package fr.silvharm.logica.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
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
	JTextField caseSecretF, essaiF;
	private List<String[]> list;
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
		defaultConfig.addActionListener(new defaultConfigListener());
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
		
		
		JPanel essaiP = new JPanel();
		
		JLabel essaiL = new JLabel("Nombre d'essais:");
		essaiP.add(essaiL);
		
		essaiF = new JTextField();
		essaiF.setColumns(3);
		essaiF.setText(properties.getProperty("triesNumber"));
		essaiP.add(essaiF);
		
		
		options.add(essaiP);
		
		
		// Mastermind
		if (game.getName().equals("Mastermind")) {
			JPanel subMastermind = new JPanel();
			
			JLabel masL = new JLabel("Nombre de couleurs:");
			subMastermind.add(masL);
			
			masC = new JComboBox<Byte>();
			for (Byte i = 4; i <= 10; i++) {
				masC.addItem(i);
			}
			
			Byte tempMas = Byte.valueOf(properties.getProperty("colorNumber"));
			if (tempMas < 4 || 10 < tempMas) {
				masC.setSelectedIndex(0);
			}
			else {
				masC.setSelectedIndex(tempMas - 4);
			}
			
			subMastermind.add(masC);
			
			options.add(subMastermind);
		}
		
		
		return options;
	}
	
	
	private void addToList(String[] str) {
		if (list == null) {
			list = new ArrayList<String[]>();
		}
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i)[0].equals(str[0])) {
				list.get(i)[1] = str[1];
				break;
			}
			
			if (i == (list.size() - 1)) {
				list.add(str);
				break;
			}
		}
	}
	
	
	class defaultConfigListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			caseSecretF.setText("5");
			
			essaiF.setText("8");
			
			if (game.getName().equals("Mastermind")) {
				masC.setSelectedIndex(1);
			}
			
			hasChanged = true;
		}
	}
	
	
	class startGameListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (hasChanged) {
				PropertiesHandler.updateProperties(properties, list);
			}
			
			game.setSecretLength(Byte.valueOf(properties.getProperty("secretCase")));
			game.setTriesNumber(Byte.valueOf(properties.getProperty("triesNumber")));
			
			if (game.getName().equals("Mastermind")) {
				
			}
			
			game.startGame();
		}
	}
}