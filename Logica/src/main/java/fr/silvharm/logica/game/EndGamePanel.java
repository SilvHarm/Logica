package fr.silvharm.logica.game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.silvharm.logica.MainWindow;
import fr.silvharm.logica.config.AskPlayerSecretDialog;
import fr.silvharm.logica.config.GameModeEnum;
import fr.silvharm.logica.config.PropertiesEnum;
import fr.silvharm.logica.config.PropertiesHandler;


public class EndGamePanel extends JPanel {
	
	
	public EndGamePanel(int endCode) {
		BorderLayout layout = new BorderLayout();
		layout.setVgap(30);
		this.setLayout(layout);
		
		
		this.add(getInfoPanel(endCode), BorderLayout.CENTER);
		
		
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
	
	
	private JPanel getInfoPanel(int endCode) {
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
		
		
		String str = "<html><br><br><br><br><br><br>";
		String str2 = "<html>";
		switch (endCode) {
			// Player win
			case 0:
				str += "Vous avez gagné.";
				break;
			// Player lose
			case 1:
				str += "Vous avez perdu !";
				break;
			// AI win
			case 2:
				str += "L'IA a gagné !";
				str2 += "Ce résultat était une évidence !";
				break;
			// AI lose
			case 3:
				str += "L'IA a perdu.";
				str2 += "Cessez de lui faire des grimaces !";
				break;
			// Player and AI wins
			case 4:
				str += "Vous et l'IA avez tout deux gagnés !";
				str2 += "C'est normal venant d'elle, mais vous ?!";
				break;
			// Player and AI loses
			case 5:
				str += "Vous et l'IA avez tout deux perdus !";
				str2 += "Arrêtez de la déconcentrer !";
				break;
		}
		str += "</html>";
		str2 += "<br><br><br></html>";
		
		JLabel winLoseLabel = new JLabel(str);
		winLoseLabel.setHorizontalAlignment(JLabel.CENTER);
		pan.add(winLoseLabel);
		
		JLabel commentLabel = new JLabel(str2);
		commentLabel.setHorizontalAlignment(JLabel.CENTER);
		pan.add(commentLabel);
		
		
		pan.add(getTriesLabel(endCode));
		
		
		return pan;
	}
	
	
	private JLabel getTriesLabel(int endCode) {
		String str = "<html>";
		Game game = Game.getGame();
		
		// if win
		if (endCode % 2 == 0) {
			if (game.triesRemaining == -2) {
				str += "1 essai aura été nécessaire !";
			}
			else if (game.triesRemaining < 0) {
				str += (-game.triesRemaining - 1) + " essais auront été nécessaires.";
			}
			else if (game.triesRemaining >= 0) {
				str += "Il restait " + game.triesRemaining + "/" + game.triesNumber + " essais.";
			}
		}
		else {
			if (game.triesNumber == 1) {
				str += "Un seul essai était permis";
			}
			else {
				str += game.triesNumber + " essais étaient permis";
			}
		}
		
		str += "</html>";
		
		JLabel triesLabel = new JLabel(str);
		triesLabel.setHorizontalAlignment(JLabel.CENTER);
		
		return triesLabel;
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
