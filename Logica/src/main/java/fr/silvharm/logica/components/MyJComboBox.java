package fr.silvharm.logica.components;

import javax.swing.JComboBox;


public class MyJComboBox<E> extends JComboBox<E> {
	
	public MyJComboBox() {
		alterBox();
	}
	
	
	public MyJComboBox(E[] items) {
		super(items);
		
		alterBox();
	}
	
	
	private void alterBox() {
		/*
		 * this.setUI(new BasicComboBoxUI() {
		 * 
		 * protected JButton createArrowButton() { return new JButton() {
		 * 
		 * public int getWidth() { return 0; } }; } });
		 */
		
		
		this.addMouseWheelListener(new MyMouseWheelListener());
	}
}
