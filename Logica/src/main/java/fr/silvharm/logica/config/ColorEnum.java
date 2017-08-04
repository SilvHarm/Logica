package fr.silvharm.logica.config;

import java.awt.Color;

public enum ColorEnum {
	BLACK(0, Color.BLACK),
	BLUE(1, Color.BLUE),
	CYAN(2, Color.CYAN),
	GRAY(3, Color.GRAY),
	GREEN(4, Color.GREEN),
	MAGENTA(5, Color.MAGENTA),
	ORANGE(6, Color.ORANGE),
	PINK(7, Color.PINK),
	RED(8, Color.RED),
	YELLOW(9, Color.YELLOW);
	
	private Color color;
	private int id;
	
	
	ColorEnum(int id, Color color) {
		this.id = id;
		this.color = color;
	}
	
	
	/*******************************
	 * Getters
	 *******************************/
	public Color getColor() {
		return color;
	}
	
	
	public int getId() {
		return id;
	}
}
