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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class LeftPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public InputPanel inputPanel;
	public ControllerChooserPanel controllerChooserPanel;
	public ControllerValuePanel controllerValuePanel;

	/**
	 * Konstruiert das LeftPanel.
	 * @param controller
	 */
	public LeftPanel(GUIController controller) {
		super(new GridBagLayout());

		UIManager.put("ToggleButton.select", Color.ORANGE);

		// Construct the inputPanel, regulatorChooserPanel and
		// regulatorValuePanel
		inputPanel = new InputPanel(controller);
		add(inputPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));

		controllerChooserPanel = new ControllerChooserPanel(controller);
		add(controllerChooserPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));

		controllerValuePanel = new ControllerValuePanel(controller);
		add(controllerValuePanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
	}
	
	/**
	 * Aktualisiert das Panel.
	 * @param obs
	 * @param obj
	 */
	public void update(Observable obs, Object obj) {
		inputPanel.update(obs, obj);
		controllerChooserPanel.update(obs, obj);
		controllerValuePanel.update(obs, obj);
	}
}