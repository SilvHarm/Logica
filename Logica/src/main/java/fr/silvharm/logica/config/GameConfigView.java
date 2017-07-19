package fr.silvharm.logica.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	private int tempMastermind = 7, tempSecretLength = 5, tempTriesNumber = 5;
	private Game game;
	
	
	public GameConfigView(Game gameG) {
		game = gameG;
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
		JPanel mode = new JPanel();
		
		JLabel modeLabel = new JLabel("Mode de jeu:");
		mode.add(modeLabel);
		
		GameMode[] gameMode = GameMode.values();
		JComboBox<GameMode> modeBox = new JComboBox<GameMode>(gameMode);
		mode.add(modeBox);
		
		return mode;
	}
	
	
	private JPanel addOptions() {
		JPanel options = new JPanel();
		options.setLayout(new BoxLayout(options, BoxLayout.PAGE_AXIS));
		
		
		JLabel optGeLabel = new JLabel("Options:");
		optGeLabel.setAlignmentX(CENTER_ALIGNMENT);
		options.add(optGeLabel);
		
		
		JPanel caseSecretP = new JPanel();
		
		JLabel caseSecretL = new JLabel("Longueur de la solution:");
		caseSecretP.add(caseSecretL);
		
		JTextField caseSecretF = new JTextField();
		caseSecretF.setColumns(3);
		caseSecretF.setText(Integer.toString(tempSecretLength));
		caseSecretP.add(caseSecretF);
		
		options.add(caseSecretP);
		
		
		JPanel essaiP = new JPanel();
		
		JLabel essaiL = new JLabel("Nombre d'essais:");
		essaiP.add(essaiL);
		
		JTextField essaiF = new JTextField();
		essaiF.setColumns(3);
		essaiF.setText(Integer.toString(tempTriesNumber));
		essaiP.add(essaiF);
		
		
		options.add(essaiP);
		
		
		// Mastermind specific
		if (game.getName().equals("Mastermind")) {
			JLabel master = new JLabel("Option Mastermind:");
			master.setAlignmentX(CENTER_ALIGNMENT);
			options.add(master);
			
			
			JPanel subMastermind = new JPanel();
			
			JLabel masL = new JLabel("Nombre de couleurs:");
			subMastermind.add(masL);
			
			JComboBox<Byte> masC = new JComboBox<Byte>();
			for (Byte i = 4; i <= 10; i++) {
				masC.addItem(i);
			}
			
			Byte tempMas = (byte) tempMastermind;
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
	
	
	class startGameListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			game.setSecretLength(tempSecretLength);
			game.setTriesNumber(tempTriesNumber);
			
			game.startGame();
		}
		
	}
}