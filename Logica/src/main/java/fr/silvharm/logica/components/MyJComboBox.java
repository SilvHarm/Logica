package fr.silvharm.logica.components;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JComboBox;


public class MyJComboBox<E> extends JComboBox<E> {
	
	public MyJComboBox() {
		alterBox();
	}
	
	
	public MyJComboBox(E[] items) {
		super(items);
		
		alterBox();
	}
	
	
	protected void alterBox() {
		Component[] comp = this.getComponents();
		Component removeComponent = null;
		
		for (int i = 0; i < comp.length; i++) {
			if (comp[i] instanceof JButton) {
				removeComponent = comp[i];
			}
		}
		if (removeComponent != null) {
			this.remove(removeComponent);
		}
		
		
		this.addMouseWheelListener(new MyMouseWheelListener());
	}
}
