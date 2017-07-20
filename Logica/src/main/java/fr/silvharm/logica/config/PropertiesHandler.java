package fr.silvharm.logica.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class PropertiesHandler {
	
	
	private static void createProperties() {
		Properties properties = new Properties();
		
		File file = new File("bin/config/config.properties");
		
		try (FileWriter writer = new FileWriter(file)) {
			properties.setProperty("triesNumber", "8");
			properties.setProperty("secretCase", "5");
			properties.store(writer, "General");
			
			properties.clear();
			
			properties.setProperty("colorNumber", "4");
			properties.store(writer, "\n" + "Mastermind");
		}
		catch (IOException e) {
			e.getMessage();
		}
	}
	
	
	public static Properties getProperties() {
		Properties properties = new Properties();
		
		try (InputStream is = PropertiesHandler.class.getResourceAsStream("/config/config.properties")) {
			if (is != null) {
				properties.load(is);
			}
			else {
				createProperties();
				
				properties = getProperties();
			}
		}
		catch (IOException e) {
			e.getMessage();
		}
		
		return properties;
	}
	
	
	public static void updateProperties(Properties properties, List<String[]> list) {
		for (String[] str : list) {
			properties.setProperty(str[0], str[1]);
		}
	}
	
}
