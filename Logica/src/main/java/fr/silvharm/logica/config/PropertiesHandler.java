package fr.silvharm.logica.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandler {
	
	private static Properties properties;
	
	private static Boolean internConfig = false;
	private static String configPath = "config.properties";
	
	
	private static void createProperties() {
		properties = new Properties();
		
		for (PropertiesEnum prop : PropertiesEnum.values()) {
			properties.setProperty(prop.getKeyName(), prop.getDefaultValue());
		}
		
		updateProperties();
	}
	
	
	public static Properties getProperties() {
		if (properties == null) {
			loadProperties(false);
		}
		
		return properties;
	}
	
	
	// try loading the config file created by the program else just go with the
	// default properties
	private static void loadProperties(boolean alreadyTried) {
		if (internConfig) {
			try (InputStream is = PropertiesHandler.class.getResourceAsStream("/config/config.properties")) {
				properties = new Properties();
				
				properties.load(is);
				
				PropertiesTester.testProperties();
			}
			catch (IOException e) {
				e.getMessage();
			}
		}
		else if (!new File(configPath).exists()) {
			// in case the program don't have writing access
			if (alreadyTried) {
				return;
			}
			
			createProperties();
			
			loadProperties(true);
		}
		else {
			try (InputStream is = new FileInputStream(configPath)) {
				properties = new Properties();
				
				properties.load(is);
				
				PropertiesTester.testProperties();
			}
			catch (IOException e) {
				e.getMessage();
			}
		}
	}
	
	
	public static void updateProperties() {
		if (!internConfig) {
			try (FileWriter writer = new FileWriter(configPath)) {
				properties.store(writer, null);
			}
			catch (IOException e) {
				e.getMessage();
			}
		}
	}
	
	
	/*******************************
	 * Getters
	 *******************************/
	public static void setInternConfig(Boolean b) {
		internConfig = b;
	}
}
