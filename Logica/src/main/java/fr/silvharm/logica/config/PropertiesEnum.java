package fr.silvharm.logica.config;


public enum PropertiesEnum {
	COLORNUMBER("colorNumber", "4"),
	GAMEMODE("gameMode", GameModeEnum.CHALLENGER.getId()),
	SQUARESECRET("squareSecret", "5"),
	TRIESNUMBER("triesNumber", "08");
	
	private String defaultValue, keyName;
	
	
	PropertiesEnum(String keyName, String defaultValue) {
		this.keyName = keyName;
		this.defaultValue = defaultValue;
	}
	
	
	/********************************
	 * Getters
	 ********************************/
	public String getDefaultValue() {
		return defaultValue;
	}
	
	
	public String getKeyName() {
		return keyName;
	}
}
