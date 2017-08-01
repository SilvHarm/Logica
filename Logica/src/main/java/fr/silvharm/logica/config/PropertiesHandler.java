package fr.silvharm.logica.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandler {
	
	private static Properties properties;
	
	
	private static void createProperties() {
		Properties properties = new Properties();
		
		for (PropertiesEnum prop : PropertiesEnum.values()) {
			properties.setProperty(prop.getKeyName(), prop.getDefaultValue());
		}
		
		updateProperties();
	}
	
	
	public static Properties getProperties() {
		if (properties == null) {
			loadProperties();
		}
		
		return properties;
	}
	
	
	private static void loadProperties() {
		properties = new Properties();
		
		try (InputStream is = PropertiesHandler.class.getResourceAsStream("/config/Logica_config.properties")) {
			if (is != null) {
				properties.load(is);
				
				testProperties();
			}
			else {
				createProperties();
				
				loadProperties();
			}
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
		File file = new File("bin/config/Logica_config.properties");
		
		try (FileWriter writer = new FileWriter(file)) {
			properties.store(writer, null);
		}
		catch (IOException e) {
			e.getMessage();
		}
	}
}
