package fr.silvharm.logica.game;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import fr.silvharm.logica.MainWindow;
import fr.silvharm.logica.config.GameConfigPanel;

public class GamesPanel extends JPanel {
	
	private Dimension dMin = new Dimension(0, 5), dPref = new Dimension(0, 30);
	
	
	public GamesPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		GamesListener gamesList = new GamesListener();
		
		
		this.add(new Box.Filler(null, null, null));
		
		
		JButton mastermind = new JButton("Mastermind");
		mastermind.setName("mastermind");
		mastermind.setAlignmentX(CENTER_ALIGNMENT);
		mastermind.addActionListener(gamesList);
		this.add(mastermind);
		
		
		this.add(new Box.Filler(dMin, dPref, dPref));
		
		
		JButton recherche = new JButton("Recherche");
		recherche.setName("recherche");
		recherche.setAlignmentX(CENTER_ALIGNMENT);
		recherche.addActionListener(gamesList);
		this.add(recherche);
		
		
		this.add(new Box.Filler(dMin, dPref, dPref));
		
		
		JButton exit = new JButton("Quitter");
		exit.setAlignmentX(CENTER_ALIGNMENT);
		exit.addActionListener(new ExitListener());
		this.add(exit);
		
		
		this.add(new Box.Filler(null, null, null));
	}
	
	
	class GamesListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			switch (((JButton) e.getSource()).getName()) {
				case "mastermind":
					new Mastermind();
					break;
				case "recherche":
					new Recherche();
					break;
			}
			
			MainWindow.getMainWindow().setView(new GameConfigPanel());
		}
	}
	
	
	class ExitListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}
}
