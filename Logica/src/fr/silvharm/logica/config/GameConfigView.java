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
	
	private int tempSecretLength = 5, tempTriesNumber = 5;
	private Game game;
	
	
	public GameConfigView(Game gameG) {
		game = gameG;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Nom du jeu
		JLabel gameLabel = new JLabel(game.getName());
		this.add(gameLabel);
		
		
		// Mode de jeu
		JPanel modePanel = new JPanel();
		
		JLabel modeLabel = new JLabel("Mode de jeu:");
		modePanel.add(modeLabel);
		
		GameMode[] gameMode = GameMode.values();
		JComboBox<GameMode> modeBox = new JComboBox<GameMode>(gameMode);
		modePanel.add(modeBox);
		
		this.add(modePanel);
		
		
		// Options générales
		JLabel optGeLabel = new JLabel("Options générales");
		this.add(optGeLabel);
		
		JPanel caseSecretP = new JPanel();
		
		JLabel caseSecretL = new JLabel("Longueur de la solution:");
		caseSecretP.add(caseSecretL);
		JTextField caseSecretF = new JTextField();
		caseSecretF.setColumns(3);
		caseSecretP.add(caseSecretF);
		
		this.add(caseSecretP);
		
		JPanel essaiP = new JPanel();
		
		JLabel essaiL = new JLabel("Nombre d'essais:");
		essaiP.add(essaiL);
		JTextField essaiF = new JTextField();
		essaiF.setColumns(3);
		essaiP.add(essaiF);
		
		this.add(essaiP);
		
		
		// Boutons
		JPanel bouPanel = new JPanel();
		
		JButton start = new JButton("Commencer à jouer");
		start.addActionListener(new startGameListener());
		bouPanel.add(start);
		
		JButton retour = new JButton("Retour");
		retour.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				MainWindow.getMainWindow().setView(new GamesView());
			}
		});
		bouPanel.add(retour);
		
		this.add(bouPanel);
	}
	
	
	class startGameListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			game.setSecretLength(tempSecretLength);
			game.setTriesNumber(tempTriesNumber);
			
			game.startGame();
		}
		
	}
}