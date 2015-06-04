package ch.fhnw.eit.pro2.gruppe4.view;

/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

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

	private static final long serialVersionUID = 1L;
	JMenu menu, menu2;
	JMenuItem menuItemOnTop;
	JMenuItem menuItemPrint;
	JMenuItem menuItemPlot;
	JFrame frame;

	/**
	 * Baut Menubar.
	 * @param controller
	 * @param frame
	 */
	public MenuBar(GUIController controller, JFrame frame) {
		this.frame = frame;
		menu = new JMenu("Ansicht");
		menu.setMnemonic(KeyEvent.VK_D);
		add(menu);

		menuItemOnTop = new JMenuItem("Immer im Vordergrund", KeyEvent.VK_A);
		menuItemOnTop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		menuItemOnTop.setActionCommand("OnTop");
		menuItemOnTop.addActionListener(this);
		menu.add(menuItemOnTop);

		add(menu);
	}

	/**
	 * Keine Verwendung bis dato.
	 */
	public void update(Observable o, Object arg) {
	}

	/**
	 * Setzt Zustand JFrames und Text des menuItemOnTop.
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("OnTop")) {
			if (((JFrame) this.getTopLevelAncestor()).isAlwaysOnTop()) {
				((JFrame) this.getTopLevelAncestor()).setAlwaysOnTop(false);
				menuItemOnTop.setText("Immer im Vordergrund");
			} else {
				((JFrame) this.getTopLevelAncestor()).setAlwaysOnTop(true);
				menuItemOnTop.setText("Nicht immer im Vordergrund");
			}
		}
	}
}
