package fr.silvharm.logica;

import fr.silvharm.logica.components.MainWindow;
import fr.silvharm.logica.game.Game;

public class Main {
	
	public static void main(String[] args) {
		if (args.length > 0) {
			for (String str : args) {
				if (str.equals("-cheat")) {
					Game.setCheat(true);
					break;
				}
			}
		}
		
		MainWindow.getMainWindow();
	}
	
}
