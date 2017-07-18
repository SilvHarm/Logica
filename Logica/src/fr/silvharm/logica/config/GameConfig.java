package fr.silvharm.logica.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.silvharm.logica.Main;
import fr.silvharm.logica.MainWindow;
import fr.silvharm.logica.game.Game;
import fr.silvharm.logica.game.GamesView;

public class GameConfig extends JPanel {
	
	public GameConfig(Game game) {
		JLabel gameName = new JLabel(game.getName());
		gameName.setLayout(new BoxLayout(gameName, BoxLayout.Y_AXIS));
		this.add(gameName);
		
		
		JButton start = new JButton("Commencer à jouer");
		start.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				game.startGame();
			}
		});
		start.setLayout(new BoxLayout(start, BoxLayout.Y_AXIS));
		this.add(start);
		
		JButton retour = new JButton("Retour");
		retour.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				Main.mainWindow.setView(new GamesView());
			}
		});
		retour.setLayout(new BoxLayout(retour, BoxLayout.Y_AXIS));
		this.add(retour);
	}
}