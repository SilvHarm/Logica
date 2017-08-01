package fr.silvharm.logica.components;

import javax.swing.JButton;


public class ColorButton extends JButton {
	
	protected int colorId, tabId;
	
	
	public ColorButton() {
		this.setText(" ");
	}
	
	
	public ColorButton(int colorId, int tabId) {
		this.colorId = colorId;
		this.tabId = tabId;
		
		this.setText(" ");
	}
	
	
	/***********************
	 * Getters
	 ***********************/
	public int getColorId() {
		return colorId;
	}
	
	
	public int getTabId() {
		return tabId;
	}
	
	
	/***********************
	 * Setters
	 ***********************/
	public void setTabId(int tabId) {
		this.tabId = tabId;
	}
}