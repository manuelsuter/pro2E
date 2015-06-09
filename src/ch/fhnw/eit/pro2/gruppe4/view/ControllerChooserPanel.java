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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import ch.fhnw.eit.pro2.gruppe4.utilities.Utility;

public class ControllerChooserPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private GUIController guiController;
	public JToggleButton btPI, btPID;
	private ButtonGroup bGroup;

	/**
	 * Setzt GridBagLayout. Baut eine ButtonGroup mit den Button zur Wahl von PI
	 * und PID.
	 * 
	 * @param guiController
	 */
	public ControllerChooserPanel(GUIController guiController) {
		super(new GridBagLayout());
		this.guiController = guiController;
		setBorder(MyBorderFactory.createMyBorder(" PI / PID-T1 Regler "));

		ImageIcon iconPI = new ImageIcon(Utility.loadResourceIcon("PI.png").getImage()
				.getScaledInstance(50, 25, java.awt.Image.SCALE_SMOOTH));
		btPI = new JToggleButton("PI", iconPI);
		btPI.setHorizontalTextPosition(AbstractButton.CENTER);
		btPI.setVerticalTextPosition(AbstractButton.TOP);
		btPI.addActionListener(this);

		ImageIcon iconPID = new ImageIcon(Utility.loadResourceIcon("PID.png").getImage()
				.getScaledInstance(50, 25, java.awt.Image.SCALE_SMOOTH));
		btPID = new JToggleButton("PID-T1", iconPID, true);
		btPID.addActionListener(this);
		btPID.setHorizontalTextPosition(AbstractButton.CENTER);
		btPID.setVerticalTextPosition(AbstractButton.TOP);

		bGroup = new ButtonGroup();
		bGroup.add(btPI);
		bGroup.add(btPID);

		add(btPI, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 51, 0));

		add(btPID, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 0, 10, 10), 51, 0));

		add(new JLabel(), new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
	}

	public void update(Observable obs, Object obj) {

	}

	/**
	 * Löst calculate() des guiControllers auf.
	 */
	public void actionPerformed(ActionEvent e) {
		guiController.calculate();
	}
}
