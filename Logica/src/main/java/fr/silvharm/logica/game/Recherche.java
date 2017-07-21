package fr.silvharm.logica.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class Recherche extends Game {
	
	public Recherche(String name) {
		super(name);
	}
	
	
	protected void calculSolution() {
		this.solution = Integer.toString(new Random().nextInt((int) Math.pow(10, squareSecret)));
		
		while (solution.length() < squareSecret) {
			solution = "0" + solution;
		}
		
		this.solutionTab = solution.toCharArray();
	}
	
	
	protected void updateGamePanel() {
		List<JComboBox<char[]>> boxList = new ArrayList<JComboBox<char[]>>();
		JPanel boxPanel = new JPanel();
		
		Character[] c = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		for (int i = 0; i < squareSecret; i++) {
			JComboBox<Character> box = new JComboBox<Character>(c);
			
			//boxList.add(box.);
			boxPanel.add(box);
		}
		
		gamePanel.add(boxPanel);
	}
	
	
}
