package fr.silvharm.logica;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.silvharm.logica.game.GamesView;

public class MainWindow extends JFrame {
	
	public MainWindow() {
		this.setTitle("Logica");
		this.setSize(600, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.setView(new GamesView());
		
		this.setVisible(true);
	}
	
	
	public void setView(JPanel pan) {
		this.setContentPane(pan);
		this.revalidate();
	}
}
