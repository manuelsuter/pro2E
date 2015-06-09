/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * Authors: Manuel Suter
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */

package ch.fhnw.eit.pro2.gruppe4.view;

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
	 * 
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
