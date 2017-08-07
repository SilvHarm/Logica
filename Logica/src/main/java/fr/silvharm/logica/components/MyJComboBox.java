package fr.silvharm.logica.components;

import java.awt.Component;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

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
	
	
	/***************************************
	 * Listeners
	 ***************************************/
	class MyMouseWheelListener implements MouseWheelListener {
		
		public void mouseWheelMoved(MouseWheelEvent e) {
			@SuppressWarnings("rawtypes")
			JComboBox box = (JComboBox) e.getSource();
			
			int i = box.getSelectedIndex();
			if (e.getWheelRotation() > 0) {
				if (i > 0) {
					box.setSelectedIndex(i - 1);
				}
			}
			else {
				if (i < box.getItemCount() - 1) {
					box.setSelectedIndex(i + 1);
				}
			}
		}
	}
}
