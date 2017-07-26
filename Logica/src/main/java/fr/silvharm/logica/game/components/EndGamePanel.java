package fr.silvharm.logica.game.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.silvharm.logica.MainWindow;
import fr.silvharm.logica.config.AskPlayerSecretDialog;
import fr.silvharm.logica.config.GameModeEnum;
import fr.silvharm.logica.config.PropertiesEnum;
import fr.silvharm.logica.game.Game;
import fr.silvharm.logica.game.GamesView;


public class EndGamePanel extends JPanel {
	
	private Game game;
	private Properties properties;
	
	
	public EndGamePanel(Game game, Properties properties) {
		this.game = game;
		this.properties = properties;
		
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
			MainWindow.getMainWindow().setView(new GamesView());
		}
	}
	
	
	class QuitListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}
	
	
	class ReplayListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			if (properties.getProperty(PropertiesEnum.GAMEMODE.getKeyName()).equals(GameModeEnum.DEFENSEUR.getId())) {
				new AskPlayerSecretDialog(game, properties);
			}
			else	{
				Game.launchGame(game, properties);
			}	
		}
	}
}
