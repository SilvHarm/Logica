package fr.silvharm.logica;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame {
	
	public MainWindow() {
		this.setTitle("Logica");
		this.setSize(600, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		gameChooseView();
		
		this.setVisible(true);
	}
	
	
	public void gameChooseView() {
		JPanel view = new JPanel();
		FlowLayout layout = new FlowLayout();
		
		view.setLayout(layout);
		
		
		GamesListener gamesList = new GamesListener();
		
		JButton mastermind = new JButton("Mastermind");
		mastermind.setName("mastermind");
		mastermind.addActionListener(gamesList);
		
		JButton recherche = new JButton("Recherche");
		recherche.setName("recherche");
		recherche.addActionListener(gamesList);
		
		JButton exit = new JButton("Quitter");
		exit.addActionListener(new ExitListener());
		
		
		view.add(mastermind);
		view.add(recherche);
		view.add(exit);
		
		this.setContentPane(view);
	}
	
	
	class GamesListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	
	class ExitListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}
}
