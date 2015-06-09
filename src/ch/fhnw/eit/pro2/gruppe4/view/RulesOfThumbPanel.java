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

import ch.fhnw.eit.pro2.gruppe4.model.ClosedLoop;
import ch.fhnw.eit.pro2.gruppe4.model.Controller;
import ch.fhnw.eit.pro2.gruppe4.model.Model;

public class RulesOfThumbPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JUnitLabel[] lbMethod;
	private JUnitLabel[] lbKr;
	private JUnitLabel[] lbTn;
	private JUnitLabel[] lbTv;
	public static final String[] unitName = { "a", "f", "p", "n", "u", "m", "", "k", "M", "G", "T", "P", "E" };

	/**
	 * Setzt GridBagLayout. Baut das rulesOfThumbPanel zur Ausgabe der
	 * Reglerwerte. Setzt mittels setIntialValues() Initialisierungswerte.
	 * 
	 * @param controller
	 */
	public RulesOfThumbPanel(GUIController controller) {
		super(new GridBagLayout());
		setBorder(MyBorderFactory.createMyBorder(" Faustformeln "));

		lbMethod = new JUnitLabel[Controller.calculationTypName.length];
		lbKr = new JUnitLabel[lbMethod.length];
		lbTn = new JUnitLabel[lbMethod.length];
		lbTv = new JUnitLabel[lbMethod.length];

		for (int i = 0; i < lbMethod.length; i++) {
			lbMethod[i] = new JUnitLabel();
			add(lbMethod[i], new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
					GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));

			lbKr[i] = new JUnitLabel();
			add(lbKr[i], new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
					GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));

			lbTn[i] = new JUnitLabel();
			add(lbTn[i], new GridBagConstraints(2, i, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
					GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));

			lbTv[i] = new JUnitLabel();
			add(lbTv[i], new GridBagConstraints(3, i, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
					GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));
		}

		add(new JUnitLabel(), new GridBagConstraints(4, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		for (int i = 0; i < StepResponsePanel.plotColor.length - 3; i++) {
			lbMethod[i + 1].setForeground(StepResponsePanel.plotColor[i + 3]);
			lbKr[i + 1].setForeground(StepResponsePanel.plotColor[i + 3]);
			lbTn[i + 1].setForeground(StepResponsePanel.plotColor[i + 3]);
			lbTv[i + 1].setForeground(StepResponsePanel.plotColor[i + 3]);
		}

		// Erzeugt die erste Zeile für Kr, Tn, Tv.
		lbKr[0].setText("<html><i>K<sub>r</sub></html></i>");
		lbTn[0].setText("<html><i>T<sub>n</sub></html></i>");
		lbTv[0].setText("<html><i>T<sub>v</sub></html></i>");

		setInitialValues();
	}

	/**
	 * Setzt Initialisierungs-Werte (Null-Werte).
	 */
	public void setInitialValues() {
		// Initialisierungswerte der Reglerausgabe.
		lbMethod[0].setText("                                                                     ");

		for (int i = 0; i < Controller.calculationTypName.length - 1; i++) {
			lbMethod[i + 1].setText(Controller.calculationTypName[i + 1]);
			lbKr[i + 1].setText(" ");
			lbTn[i + 1].setText(" ");
			lbTv[i + 1].setText(" ");
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

		for (int i = 0; i < closedLoop.length - 3; i++) {
			double[] controllerValues = closedLoop[i + 3].getController().getControllerValues();
			lbMethod[i + 1].setText(""
					+ Controller.calculationTypName[(int) controllerValues[Controller.CALCULATIONTYPPOS]]);

			lbKr[i + 1].setText(controllerValues[Controller.KrPOS]);
			lbTn[i + 1].setText(controllerValues[Controller.TnPOS], "s");
			lbTv[i + 1].setText(controllerValues[Controller.TvPOS], "s");
		}

		if (model.getClosedLoop()[0].getController().getControllerTyp() != Controller.PID) {
			for (int i = 0; i < lbTv.length; i++) {
				lbTv[i].setText("           ");
			}
		} else {
			lbTv[0].setText("<html><i>T<sub>v</sub></html></i>");

		}
	}

}
