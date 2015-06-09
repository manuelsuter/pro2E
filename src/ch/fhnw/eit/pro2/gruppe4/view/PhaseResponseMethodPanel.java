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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

import javax.swing.JPanel;

import ch.fhnw.eit.pro2.gruppe4.model.ClosedLoop;
import ch.fhnw.eit.pro2.gruppe4.model.Controller;
import ch.fhnw.eit.pro2.gruppe4.model.Model;

public class PhaseResponseMethodPanel extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;
	private GUIController guiController;

	public JUnitLabel[] lbMethod = new JUnitLabel[4];
	private JUnitLabel[] lbKr = new JUnitLabel[lbMethod.length];
	private JUnitLabel[] lbTn = new JUnitLabel[lbMethod.length];
	private JUnitLabel[] lbTv = new JUnitLabel[lbMethod.length];
	public JUnitTextField[] tfTp = new JUnitTextField[3];
	private JUnitLabel lbTp;
	private JUnitLabel[] lbS = new JUnitLabel[3];
	public static final String[] unitName = { "a", "f", "p", "n", "u", "m", "", "k", "M", "G", "T", "P", "E" };

	/**
	 * Setzt das GridBagLayout. Baut das phaseRepsonseMethodPanel zur Ausgabe
	 * der Reglerwerte. Setzt mittels setIntialValues() Initialisierungswerte.
	 * 
	 * @param controller
	 */
	public PhaseResponseMethodPanel(GUIController controller) {
		super(new GridBagLayout());
		this.guiController = controller;
		setBorder(MyBorderFactory.createMyBorder(" Phasengang-Methode "));

		// Baut die Ausgabe-Panel der Phasengangmethode:
		lbMethod[0] = new JUnitLabel("Optimierung");
		lbMethod[1] = new JUnitLabel("Positiv");
		lbMethod[2] = new JUnitLabel("Standard");
		lbMethod[3] = new JUnitLabel("Negativ");

		for (int i = 0; i < lbMethod.length; i++) {

			add(lbMethod[i], new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
					GridBagConstraints.NONE, new Insets(5, 10, 9, 10), 0, 0));

			lbKr[i] = new JUnitLabel();
			add(lbKr[i], new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
					GridBagConstraints.NONE, new Insets(5, 10, 9, 10), 0, 0));

			lbTn[i] = new JUnitLabel();
			add(lbTn[i], new GridBagConstraints(2, i, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
					GridBagConstraints.NONE, new Insets(5, 10, 9, 10), 0, 0));

			lbTv[i] = new JUnitLabel();
			add(lbTv[i], new GridBagConstraints(3, i, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
					GridBagConstraints.NONE, new Insets(5, 10, 9, 10), 0, 0));
		}
		for (int i = 0; i < tfTp.length; i++) {
			tfTp[i] = new JUnitTextField("", 5, false);
			tfTp[i].addKeyListener(this);
			add(tfTp[i], new GridBagConstraints(4, i + 1, 1, 1, 1.0, 0.0, GridBagConstraints.LINE_START,
					GridBagConstraints.HORIZONTAL, new Insets(5, 10, 5, 10), 0, 0));

			lbS[i] = new JUnitLabel("s");
			add(lbS[i], new GridBagConstraints(6, i + 1, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
					GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		}

		lbKr[0].setText("<html><i>K<sub>r</sub></html></i>");
		lbTn[0].setText("<html><i>T<sub>n</sub></html></i>");
		lbTv[0].setText("<html><i>T<sub>v</sub></html></i>");

		add(lbTp = new JUnitLabel("<html><i>T<sub>p</sub></i></html>"), new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5, 10, 9, 10), 0, 0));

		add(new JUnitLabel(), new GridBagConstraints(6, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		// Initialisierungswerte der Reglerausgabe.
		setInitialValues();
	}

	/**
	 * Setzt Initialisierungs-Werte (Null-Werte).
	 */
	public void setInitialValues() {
		for (int i = 0; i < lbMethod.length - 1; i++) {
			lbKr[i + 1].setText("             ");
			lbTn[i + 1].setText("             ");
			lbTv[i + 1].setText("             ");
			tfTp[i].setText("");
		}

		for (int i = 0; i < lbMethod.length - 1; i++) {
			lbMethod[i + 1].setForeground(StepResponsePanel.plotColor[i]);
			lbKr[i + 1].setForeground(StepResponsePanel.plotColor[i]);
			lbTn[i + 1].setForeground(StepResponsePanel.plotColor[i]);
			lbTv[i + 1].setForeground(StepResponsePanel.plotColor[i]);
		}
	}

	/**
	 * Castet obs zu einem Model. Setzt die aktuellen Regler-Werte auf die
	 * Labels und regelt die Sichtbarkeit der einzelnen Komponenten.
	 * 
	 * @param obs
	 * @param obj
	 */
	public void update(Observable obs, Object obj) {
		Model model = (Model) obs;

		ClosedLoop[] closedLoop = model.getClosedLoop();

		// Holt die jeweiligen ClosedLoops in die Methode.
		// Setzt die aktuellen Werte auf die Labels und Textfelder.
		for (int i = 0; i < lbMethod.length - 1; i++) {
			double[] controllerValues = closedLoop[i].getController().getControllerValues();

			lbKr[i + 1].setText(controllerValues[Controller.KrPOS]);
			lbTn[i + 1].setText(controllerValues[Controller.TnPOS], "s");
			lbTv[i + 1].setText(controllerValues[Controller.TvPOS], "s");
		}

		for (int i = 0; i < lbMethod.length - 1; i++) {
			tfTp[i].setText(closedLoop[i].getController().getControllerValues()[Controller.TpPOS]);
		}

		if (model.getClosedLoop()[0].getController().getControllerTyp() != Controller.PID) {
			for (int i = 0; i < lbTv.length; i++) {
				lbTv[i].setText("                 ");
			}
			for (int i = 0; i < lbS.length; i++) {
				tfTp[i].setVisible(false);
				lbS[i].setVisible(false);
			}
			lbTp.setForeground(getBackground());
		} else {
			lbTv[0].setText("<html><i>T<sub>v</sub></html></i>");
			for (int i = 0; i < lbS.length; i++) {
				tfTp[i].setVisible(true);
				lbS[i].setVisible(true);
			}
			lbTp.setForeground(Color.BLACK);
		}
	}

	/**
	 * Setzt die Werte für Tp mittels setTp() des guiController, sobald mit
	 * Enter bestätigt.
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) {
			if (e.getSource() == tfTp[0]) {
				guiController.setTp(0);
			} else if (e.getSource() == tfTp[1]) {
				guiController.setTp(1);
			} else if (e.getSource() == tfTp[2]) {
				guiController.setTp(2);
			}
		}

	}

	/**
	 * Keine Verwenung bis dato.
	 */
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * Keine Verwenung bis dato.
	 */
	public void keyTyped(KeyEvent e) {
	}

}
