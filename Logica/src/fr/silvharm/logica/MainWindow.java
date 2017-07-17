package fr.silvharm.logica;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
	
	public MainWindow() {
		this.setTitle("Logica");
		this.setSize(600, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		// this.setContentPane(mainPanel);
		this.setVisible(true);
	}
}
