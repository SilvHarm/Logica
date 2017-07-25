package fr.silvharm.logica.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandler {
	
	
	private static void createProperties() {
		Properties properties = new Properties();
		
		for (PropertiesEnum prop : PropertiesEnum.values())	{
			properties.setProperty(prop.getKeyName(), prop.getDefaultValue());
		}
		
		updateProperties(properties);
	}
	
	
	public static Properties getProperties() {
		Properties properties = new Properties();
		
		try (InputStream is = PropertiesHandler.class.getResourceAsStream("/config/config.properties")) {
			if (is != null) {
				properties.load(is);
				
				testProperties(properties);
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
	
	
	private static void testProperties(Properties properties) {
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
			updateProperties(properties);
		}
	}
	
	
	public static void updateProperties(Properties properties) {
		File file = new File("bin/config/config.properties");
		
		try (FileWriter writer = new FileWriter(file)) {
			properties.store(writer, "Options");
		}
		catch (IOException e) {
			e.getMessage();
		}
	}
}
