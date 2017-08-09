package fr.silvharm.logica.components;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

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
