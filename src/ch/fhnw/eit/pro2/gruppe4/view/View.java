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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	public LeftPanel leftPanel;
	public RightPanel rightPanel;
	private JFrame frame;

	/**
	 * Setzt das GridBagLayout. Erzeugt das leftPanel und das rightPanel.
	 * Platziert diese auf der View.
	 * 
	 * @param guiController
	 */
	public View(GUIController guiController) {
		super(new GridBagLayout());

		// Construct the main left and main right panel
		leftPanel = new LeftPanel(guiController);
		add(leftPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(2, 0, 0, 0), 0, 0));

		rightPanel = new RightPanel(guiController);
		add(rightPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_END,
				GridBagConstraints.BOTH, new Insets(2, 0, 0, 0), 0, 0));
	}

	/**
	 * Verbindet das übergeordnete JFrame mit dem lokalen Frame.
	 * 
	 * @param frame
	 */
	public void setJFrame(JFrame frame) {
		this.frame = frame;
	}

	/**
	 * Setzt die Sichtbarkeit des richtPanel, die grösse des frames und ein
	 * Flag, ob das Frame in der Grösse veränderbar ist.
	 * 
	 * @param flag
	 * @param dimension
	 */
	public void setVisibility(boolean flag, Dimension dimension) {
		rightPanel.setVisible(flag);
		frame.setSize(dimension);
		frame.setResizable(flag);
	}

	/**
	 * Übergibt obs und obj an das leftPanel und rightPanel.
	 * 
	 * @param obs
	 * @param obj
	 */
	public void update(Observable obs, Object obj) {
		leftPanel.update(obs, obj);
		rightPanel.update(obs, obj);
	}

}
