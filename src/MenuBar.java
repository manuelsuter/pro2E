/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuBar extends JMenuBar implements Observer, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenu menu, menu2;
	JMenuItem menuItemOnTop;
	JMenuItem menuItemPrint;
	JMenuItem menuItemPlot;
	JFrame frame;

	public MenuBar(Controller controller, JFrame frame) {
		this.frame = frame;
		menu = new JMenu("Ansicht");
		menu.setMnemonic(KeyEvent.VK_D);
		add(menu);
		
		menuItemOnTop = new JMenuItem("Immer im Vordergrund", KeyEvent.VK_A);
		menuItemOnTop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		menuItemOnTop.setActionCommand("OnTop");
		menuItemOnTop.addActionListener(this);
		menu.add(menuItemOnTop);
		

		/**
		menuItemPrint = new JMenuItem("Print", KeyEvent.VK_P);
		menuItemPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		menuItemOnTop.setActionCommand("Print");
		menuItemOnTop.addActionListener(this);
		menu.add(menuItemPrint);
		
		JMenuItem menuItemResizable = new JMenuItem("Resizable", KeyEvent.VK_R);
		menuItemResizable.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		menuItemResizable.setActionCommand("Resizable");
		menuItemResizable.addActionListener(this);
		menu.add(menuItemResizable);
		
		JMenuItem menuItemNotResizable = new JMenuItem("Not Resizable", KeyEvent.VK_N);
		menuItemNotResizable.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
		menuItemNotResizable.setActionCommand("NotResizable");
		menuItemNotResizable.addActionListener(this);
		menu.add(menuItemNotResizable);
		*/
		add(menu);
	}

	public void update(Observable o, Object arg) {}

	public void actionPerformed(ActionEvent e) {
		/**if (e.getActionCommand().equals("Resizable")) {
		 * 
		 
			frame.setResizable(true);			
			Dimension dim = frame.getSize();
			dim.width -=100;
			frame.setSize(dim);
		}
		if (e.getActionCommand().equals("NotResizable")) {
			frame.setResizable(false);			
			Dimension dim = frame.getSize();
			dim.width +=100;
			frame.setSize(dim);
		}
		*/
		
		
		
		if (e.getActionCommand().equals("OnTop")) {
			if (((JFrame) this.getTopLevelAncestor()).isAlwaysOnTop()) {
				((JFrame) this.getTopLevelAncestor()).setAlwaysOnTop(false);
				menuItemOnTop.setText("Immer im Vordergrund");
			} else{
				((JFrame) this.getTopLevelAncestor()).setAlwaysOnTop(true);
				menuItemOnTop.setText("Nicht immer im Vordergrund");
			} 
		}
	}
}
