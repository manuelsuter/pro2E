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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;

import javax.swing.JPanel;

public class RightPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public OptimizationPanel optimizationPanel;
	public StepResponsePanel stepResponsePanel;

	/**
	 * Setzt GridBagLayout.
	 * Erzugt und platziert optimizationPanel und stepResponsePanel.
	 * @param controller
	 */
	public RightPanel(GUIController controller) {
		super(new GridBagLayout());

		// Construct the upperPlotPanel and the stepResponsePanel
		optimizationPanel = new OptimizationPanel(controller);
		add(optimizationPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.1, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

		stepResponsePanel = new StepResponsePanel(controller);
		add(stepResponsePanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.9, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

	}
	
	/**
	 * Übergibt obs und obj an optimizationPanel und stepResponsePanel weiter.
	 * @param obs
	 * @param obj
	 */
	public void update(Observable obs, Object obj) {
		optimizationPanel.update(obs, obj);
		stepResponsePanel.update(obs, obj);
	}
}