package fr.silvharm.logica.config;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import fr.silvharm.logica.game.Game;


public class AskPlayerSecretDialog extends JDialog {
	
	private static AskPlayerSecretDialog dialog;
	
	
	public AskPlayerSecretDialog() {
		dialog = this;
		
		this.setTitle("Quel est votre combinaison ?");
		this.setSize(400, 120);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		
		JPanel mainPanel = new JPanel();
		
		BorderLayout bLayout = new BorderLayout();
		mainPanel.setLayout(bLayout);
		
		
		mainPanel.add(Game.getGame().askPlayerSecret(), BorderLayout.CENTER);
		
		
		JPanel butPanel = new JPanel();
		
		JButton confirmB = new JButton("Confirmer combinaison");
		confirmB.addActionListener(new ConfirmListener());
		butPanel.add(confirmB);
		
		JButton cancelB = new JButton("Annuler");
		cancelB.addActionListener(new CancelListener());
		butPanel.add(cancelB);
		
		mainPanel.add(butPanel, BorderLayout.PAGE_END);
		
		
		this.setContentPane(mainPanel);
		
		
		this.setVisible(true);
	}
	
	
	/*****************************
	 * Listeners
	 *****************************/
	class CancelListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			dialog.setVisible(false);
			
			dialog = null;
		}
	}
	
	
	class ConfirmListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			dialog.setVisible(false);
			
			Game.launchGame();
		}
	}
}
