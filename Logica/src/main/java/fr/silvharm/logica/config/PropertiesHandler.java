package fr.silvharm.logica.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandler {
	
	private static Properties properties;
	
	private static String configPath = "config.properties";
	
	
	private static void createProperties() {
		Properties properties = new Properties();
		
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
	
	
	// load the config stored in resources but who is not writable
	private static void loadBackuptProperties() {
		try (InputStream is = PropertiesHandler.class.getResourceAsStream("/config/config.properties")) {
			properties.load(is);
			
			testProperties();
		}
		catch (IOException e) {
			e.getMessage();
		}
	}
	
	
	// try loading the config created by the program
	private static void loadProperties(boolean alreadyTried) {
		properties = new Properties();
		
		if (!new File(configPath).exists()) {
			// in case the program don't have writing access
			if (alreadyTried) {
				loadBackuptProperties();
				
				return;
			}
			
			createProperties();
			
			loadProperties(true);
		}
		
		
		try (InputStream is = new FileInputStream(configPath)) {
			properties.load(is);
			
			testProperties();
		}
		catch (IOException e) {
			e.getMessage();
		}
	}
	
	
	private static void testProperties() {
		Boolean hasChanged = false;
		
		String testStr;
		for (PropertiesEnum prop : PropertiesEnum.values()) {
			testStr = properties.getProperty(prop.getKeyName());
			
			if (testStr == null || testStr.length() != prop.getDefaultValue().length()) {
				properties.setProperty(prop.getKeyName(), prop.getDefaultValue());
				
				hasChanged = true;
			}
			else {
				try {
					Integer.valueOf(testStr);
				}
				catch (NumberFormatException e) {
					properties.setProperty(prop.getKeyName(), prop.getDefaultValue());
					
					hasChanged = true;
				}
			}
		}
		
		
		if (hasChanged) {
			updateProperties();
		}
	}
	
	
	public static void updateProperties() {
		try (FileWriter writer = new FileWriter(configPath)) {
			properties.store(writer, null);
		}
		catch (IOException e) {
			e.getMessage();
		}
	}
}
