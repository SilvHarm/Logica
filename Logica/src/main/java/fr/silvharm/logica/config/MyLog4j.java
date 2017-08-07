package fr.silvharm.logica.config;

import org.apache.log4j.Logger;

public class MyLog4j {
	
	private static Logger logger = Logger.getLogger(MyLog4j.class);
	
	
	public static Logger getLogger()	{
		return logger;
	}
}
