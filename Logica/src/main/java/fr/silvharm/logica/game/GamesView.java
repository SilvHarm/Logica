package fr.silvharm.logica.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import fr.silvharm.logica.MainWindow;
import fr.silvharm.logica.config.GameConfigView;

public class GamesView extends JPanel {
	
	public GamesView() {
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		
		
		GamesListener gamesList = new GamesListener();
		
		JButton mastermind = new JButton("Mastermind");
		mastermind.setName("mastermind");
		mastermind.addActionListener(gamesList);
		pan.add(mastermind);
		
		JButton recherche = new JButton("Recherche");
		recherche.setName("recherche");
		recherche.addActionListener(gamesList);
		pan.add(recherche);
		
		JButton exit = new JButton("Quitter");
		exit.addActionListener(new ExitListener());
		pan.add(exit);
		
		
		this.add(pan);
	}
	
	
	class GamesListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			Game game = null;
			
			switch (((JButton) e.getSource()).getName()) {
				case "mastermind":
					game = new Mastermind("Mastermind");
					break;
				case "recherche":
					game = new Recherche("Recherche");
					break;
			}
			
			MainWindow.getMainWindow().setView(new GameConfigView(game));
		}
	}
	
	
	class ExitListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}
}
