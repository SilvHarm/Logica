package fr.silvharm.logica.game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.silvharm.logica.MainWindow;
import fr.silvharm.logica.config.AskPlayerSecretDialog;
import fr.silvharm.logica.config.GameModeEnum;
import fr.silvharm.logica.config.PropertiesEnum;
import fr.silvharm.logica.config.PropertiesHandler;


public class EndGamePanel extends JPanel {
	
	
	public EndGamePanel() {
		BorderLayout layout = new BorderLayout();
		layout.setVgap(30);
		this.setLayout(layout);
		
		
		JLabel infoDisplayerLabel = new JLabel("Test");
		this.add(infoDisplayerLabel, BorderLayout.CENTER);
		
		
		JPanel butPanel = new JPanel();
		
		JButton replayBut = new JButton("Rejouer");
		replayBut.addActionListener(new ReplayListener());
		butPanel.add(replayBut);
		
		JButton changeBut = new JButton("Changer de jeu");
		changeBut.addActionListener(new ChangeGameListener());
		butPanel.add(changeBut);
		
		JButton quitBut = new JButton("Quitter");
		quitBut.addActionListener(new QuitListener());
		butPanel.add(quitBut);
		
		this.add(butPanel, BorderLayout.PAGE_END);
	}
	
	
	/***********************
	 * Listeners
	 ***********************/
	class ChangeGameListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			MainWindow.getMainWindow().setView(new GamesPanel());
		}
	}
	
	
	class QuitListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}
	
	
	class ReplayListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			// if gameMode is "DEFENSEUR"
			if (PropertiesHandler.getProperties().getProperty(PropertiesEnum.GAMEMODE.getKeyName())
					.equals(GameModeEnum.DEFENSEUR.getId())) {
				new AskPlayerSecretDialog();
			}
			else {
				Game.launchGame();
			}
		}
	}
}
