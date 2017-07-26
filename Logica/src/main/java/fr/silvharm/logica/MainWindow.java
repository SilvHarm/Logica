package fr.silvharm.logica;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.silvharm.logica.game.GamesPanel;

public class MainWindow extends JFrame {
	
	private static MainWindow window = new MainWindow();
	
	
	private MainWindow() {
		this.setTitle("Logica");
		this.setSize(600, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.setView(new GamesPanel());
		
		this.setVisible(true);
	}
	
	
	public static MainWindow getMainWindow() {
		return window;
	}
	
	
	public void setView(JPanel pan) {
		this.setContentPane(pan);
		this.revalidate();
	}
}
