package fr.silvharm.logica.game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.silvharm.logica.components.MainWindow;
import fr.silvharm.logica.config.GameModeEnum;
import fr.silvharm.logica.config.MyLog4j;
import fr.silvharm.logica.config.PropertiesEnum;
import fr.silvharm.logica.config.PropertiesHandler;


public class EndGamePanel extends JPanel {
	
	private Dimension dima = new Dimension(0, 5), dimi = new Dimension(0, 30), dimo = new Dimension(0, 60),
			dimu = new Dimension(Short.MAX_VALUE, 40);
	private Game game;
	
	
	public EndGamePanel() {
		game = Game.getGame();
		
		MyLog4j.getLogger().info("Game ended");
		MyLog4j.getLogger().debug("Ending code is: " + game.endCode);
		
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		
		this.add(getInfoPanel(), BorderLayout.CENTER);
		
		
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
	
	
	private JPanel getInfoPanel() {
		JPanel pan = new JPanel();
		
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		
		
		String str = "";
		String str2 = "";
		switch (game.endCode) {
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
		
		// top white-space
		pan.add(new Box.Filler(dima, dimi, null));
		
		
		JLabel winLoseLabel = new JLabel(str);
		winLoseLabel.setAlignmentX(CENTER_ALIGNMENT);
		pan.add(winLoseLabel);
		
		JLabel commentLabel = new JLabel(str2);
		commentLabel.setAlignmentX(CENTER_ALIGNMENT);
		pan.add(commentLabel);
		
		
		// separator
		pan.add(new Box.Filler(dima, dimi, dimi));
		
		
		JLabel triesLabel = getTriesLabel();
		triesLabel.setAlignmentX(CENTER_ALIGNMENT);
		pan.add(triesLabel);
		
		
		if (!game.solution.equals(" ")) {
			// separator
			pan.add(new Box.Filler(dima, dimo, dimo));
			
			
			JLabel solLabel = new JLabel("Solution :");
			solLabel.setAlignmentX(CENTER_ALIGNMENT);
			pan.add(solLabel);
			
			JPanel solPanel = game.createAnsSolPanel(game.solution);
			solPanel.setAlignmentX(CENTER_ALIGNMENT);
			solPanel.setMaximumSize(dimu);
			pan.add(solPanel);
		}
		
		
		if (!game.solutionPlayer.equals(" ")) {
			// separator
			if (!game.solution.equals(" ")) {
				pan.add(new Box.Filler(dima, dimi, dimi));
			}
			else {
				pan.add(new Box.Filler(dima, dimo, dimo));
			}
			
			
			JLabel solPlayLabel = new JLabel("Solution du Joueur :");
			solPlayLabel.setAlignmentX(CENTER_ALIGNMENT);
			pan.add(solPlayLabel);
			
			JPanel solPlayPanel = game.createAnsSolPanel(game.solutionPlayer);
			solPlayPanel.setAlignmentX(CENTER_ALIGNMENT);
			solPlayPanel.setMaximumSize(dimu);
			pan.add(solPlayPanel);
		}
		
		
		// bottom white-space
		pan.add(new Box.Filler(dima, dimi, null));
		
		
		return pan;
	}
	
	
	private JLabel getTriesLabel() {
		String str = "";
		
		// if win
		if (game.endCode % 2 == 0) {
			if (game.triesRemaining == -2) {
				str += "1 essai aura été nécessaire !";
			}
			else if (game.triesRemaining < 0) {
				str += (-game.triesRemaining - 1) + " essais auront été nécessaires.";
			}
			else if (game.triesRemaining == 0) {
				str += "C'était le denier essai sur " + game.triesNumber + ".";
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
		
		JLabel triesLabel = new JLabel(str);
		
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
