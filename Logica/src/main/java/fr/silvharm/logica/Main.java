package fr.silvharm.logica;

import fr.silvharm.logica.components.MainWindow;
import fr.silvharm.logica.config.MyLog4j;
import fr.silvharm.logica.config.PropertiesHandler;
import fr.silvharm.logica.game.Game;

public class Main {
	
	public static void main(String[] args) {
		if (args.length > 0) {
			for (String str : args) {
				switch (str) {
					case "-cheat":
						MyLog4j.getLogger().info("Cheat mode activated");
						
						Game.setCheat(true);
						break;
					case "-internconfig":
						MyLog4j.getLogger().info("Usage of the intern config");
						
						PropertiesHandler.setInternConfig(true);
						break;
				}
			}
		}
		
		MainWindow.getMainWindow();
	}
	
}
