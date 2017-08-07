package fr.silvharm.logica.config;

import java.util.Properties;

public class PropertiesTester {
	
	private static Boolean hasChanged;
	private static Properties properties;
	private static PropertiesEnum prop;
	private static String value;
	
	
	public static void testProperties() {
		hasChanged = false;
		
		properties = PropertiesHandler.getProperties();
		
		
		cheatModeTester();
		
		colorNumberTester();
		
		gameModeTester();
		
		squareSecretTester();
		
		triesNumberTester();
		
		
		if (hasChanged) {
			PropertiesHandler.updateProperties();
		}
	}
	
	
	private static void getValue() {
		value = properties.getProperty(prop.getKeyName());
	}
	
	
	private static Boolean isInteger() {
		try {
			Integer.valueOf(value);
			
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
	
	private static Boolean isNull() {
		return (value == null);
	}
	
	
	private static void changeToDefault() {
		properties.setProperty(prop.getKeyName(), prop.getDefaultValue());
		
		hasChanged = true;
	}
	
	
	/**************************
	 * Testers
	 ***************************/
	private static void cheatModeTester() {
		prop = PropertiesEnum.CHEATMODE;
		
		getValue();
		
		if (!isNull()) {
			if (isInteger()) {
				int i = Integer.valueOf(value);
				
				if (i == 0 || i == 1) {
					return;
				}
			}
		}
		
		changeToDefault();
	}
	
	
	private static void colorNumberTester() {
		prop = PropertiesEnum.COLORNUMBER;
		
		getValue();
		
		if (!isNull()) {
			if (isInteger()) {
				int i = Integer.valueOf(value);
				
				if (4 <= i && i <= 10) {
					return;
				}
			}
		}
		
		changeToDefault();
	}
	
	
	private static void gameModeTester() {
		prop = PropertiesEnum.GAMEMODE;
		
		getValue();
		
		if (!isNull()) {
			if (isInteger()) {
				int i = Integer.valueOf(value);
				
				if (0 <= i && i <= 2) {
					return;
				}
			}
		}
		
		changeToDefault();
	}
	
	
	private static void squareSecretTester() {
		prop = PropertiesEnum.SQUARESECRET;
		
		getValue();
		
		if (!isNull()) {
			if (isInteger()) {
				int i = Integer.valueOf(value);
				
				if (1 <= i && i <= 9) {
					return;
				}
			}
		}
		
		changeToDefault();
	}
	
	
	private static void triesNumberTester() {
		prop = PropertiesEnum.TRIESNUMBER;
		
		getValue();
		
		if (!isNull()) {
			if (isInteger()) {
				int i = Integer.valueOf(value);
				
				if (0 <= i && i <= 99) {
					return;
				}
			}
		}
		
		changeToDefault();
	}
}