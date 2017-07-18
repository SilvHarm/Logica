package fr.silvharm.logica.game;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import fr.silvharm.logica.Main;
import fr.silvharm.logica.config.GameConfig;

public class GamesView extends JPanel {
	
	public GamesView() {
		FlowLayout layout = new FlowLayout();
		this.setLayout(layout);
		
		
		GamesListener gamesList = new GamesListener();
		
		JButton mastermind = new JButton("Mastermind");
		mastermind.setName("mastermind");
		mastermind.addActionListener(gamesList);
		
		JButton recherche = new JButton("Recherche");
		recherche.setName("recherche");
		recherche.addActionListener(gamesList);
		
		JButton exit = new JButton("Quitter");
		exit.addActionListener(new ExitListener());
		
		
		this.add(mastermind);
		this.add(recherche);
		this.add(exit);
	}
	
	
	class GamesListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			Main.mainWindow.setView(new GameConfig(new TestGame("Test")));
		}
	}
	
	
	class ExitListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}
}
