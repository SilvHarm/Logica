package fr.silvharm.logica.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import fr.silvharm.logica.MainWindow;
import fr.silvharm.logica.config.GameConfigView;

public class Recherche extends Game {
	
	private Map<String, Character> boxMap;
	
	
	public Recherche() {
		this.name = "Recherche";
	}
	
	
	protected void calculSolution() {
		this.solution = Integer.toString(new Random().nextInt((int) Math.pow(10, squareSecret)));
		
		while (solution.length() < squareSecret) {
			solution = "0" + solution;
		}
		
		this.solutionTab = solution.toCharArray();
		
		System.out.println(solution);
	}
	
	
	protected void updateGamePanel() {
		gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.PAGE_AXIS));
		
		JPanel boxPanel = new JPanel();
		
		boxMap = new LinkedHashMap<String, Character>();
		BoxListener boxListener = new BoxListener();
		
		Character[] c = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		for (int i = 0; i < squareSecret; i++) {
			JComboBox<Character> box = new JComboBox<Character>(c);
			
			box.setName(Integer.toString(i));
			box.addActionListener(boxListener);
			
			boxMap.put(box.getName(), (Character) box.getSelectedItem());
			
			boxPanel.add(box);
		}
		
		gamePanel.add(boxPanel);
		
		
		JPanel butPanel = new JPanel();
		
		JButton verBut = new JButton("Vérifier");
		verBut.addActionListener(new VerifListener());
		butPanel.add(verBut);
		
		JButton backBut = new JButton("Retour");
		backBut.addActionListener(new BackListener());
		butPanel.add(backBut);
		
		gamePanel.add(butPanel);
	}
	
	
	/*******************************
	 * Listeners
	 *******************************/
	class BackListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			MainWindow.getMainWindow().setView(new GameConfigView(new Recherche()));
		}
	}
	
	
	class BoxListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			@SuppressWarnings("rawtypes")
			JComboBox box = (JComboBox) arg0.getSource();
			
			boxMap.put(box.getName(), (Character) box.getSelectedItem());
		}
	}
	
	
	class VerifListener implements ActionListener	{
		
		public void actionPerformed(ActionEvent arg0) {
			for (int i = 0; i < squareSecret; i++)	{
				answer += boxMap.get(Integer.toString(i));
			}
			
			if (answer.equals(solution))	{
				System.out.println("Gagnez !");
			}
			else	{
				System.out.println("Try again !");
			}
			
			answer = "";
		}
	}
}
