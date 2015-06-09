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
import java.awt.Dimension;

import ch.fhnw.eit.pro2.gruppe4.model.Controller;
import ch.fhnw.eit.pro2.gruppe4.model.ControllerException;
import ch.fhnw.eit.pro2.gruppe4.model.Model;
import ch.fhnw.eit.pro2.gruppe4.model.SaniException;

public class GUIController {
	private Model model;
	private View view;
	private boolean controllerCalculated = false;

	/**
	 * Setzt Attribut model gleich model.
	 * 
	 * @param model
	 */
	public GUIController(Model model) {
		this.model = model;
	}

	/**
	 * Überprüft die Benutzer-Eingaben auf Korrektheit. Löst setData() des
	 * models auf.
	 */
	public void calculate() {
		// Bringt die View in einen Default-Zustand
		view.leftPanel.controllerValuePanel.phaseResponsePanel.setInitialValues();
		setExceptionLabel(" ", Color.black);
		view.leftPanel.controllerChooserPanel.btPID.setEnabled(true);

		try {
			double[] tpValues = new double[3];
			if ((view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[0].getText()).equals("")
					|| (view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[1].getText()).equals("")
					|| (view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[2].getText()).equals("")) {
				tpValues[0] = 0.0;
				tpValues[1] = 0.0;
				tpValues[2] = 0.0;
			} else {
				tpValues[0] = Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[0]
						.getText());
				tpValues[1] = Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[1]
						.getText());
				tpValues[2] = Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[2]
						.getText());
			}

			double Ks = Double.parseDouble(view.leftPanel.inputPanel.tfKs.getText());
			double Tu = Double.parseDouble(view.leftPanel.inputPanel.tfTu.getText());
			double Tg = Double.parseDouble(view.leftPanel.inputPanel.tfTg.getText());

			double phaseMarginOffset = view.rightPanel.optimizationPanel.getPhaseMargin();
			double overShoot = view.rightPanel.optimizationPanel.getOverShoot();

			if ((Double.parseDouble(view.leftPanel.inputPanel.tfKs.getText()) == 0.0)
					|| (Double.parseDouble(view.leftPanel.inputPanel.tfTu.getText()) == 0.0)
					|| (Double.parseDouble(view.leftPanel.inputPanel.tfTg.getText()) == 0.0)) {
				setExceptionLabel("Werte dürfen nicht 0 sein!", Color.RED);
			} else if ((Double.parseDouble(view.leftPanel.inputPanel.tfKs.getText()) < 1e-9)
					|| (Double.parseDouble(view.leftPanel.inputPanel.tfTu.getText()) < 1e-9)
					|| (Double.parseDouble(view.leftPanel.inputPanel.tfTg.getText()) < 1e-9)) {
				setExceptionLabel("Eingabewerte zu klein (Minimum: 1e-9)", Color.RED);
			} else if ((Double.parseDouble(view.leftPanel.inputPanel.tfKs.getText()) > 1e6)
					|| (Double.parseDouble(view.leftPanel.inputPanel.tfTu.getText()) > 1e6)
					|| (Double.parseDouble(view.leftPanel.inputPanel.tfTg.getText()) > 1e6)) {
				setExceptionLabel("Eingabewerte zu gross (Maximum: 1e6)", Color.RED);
			} else {
				int controllerTyp;

				if (view.leftPanel.controllerChooserPanel.btPI.isSelected() == true) {
					controllerTyp = Controller.PI;
				} else {
					controllerTyp = Controller.PID;
				}
				// Überprüft die Streckenordnung und deaktiviert den PID Regler,
				// wenn Ordnung < 3 ist.
				if (Tu / Tg <= 0.103638) {
					controllerTyp = Controller.PI;
					setExceptionLabel("Für Strecken-Ordnung < 3 nur PI möglich!", Color.MAGENTA);
					view.leftPanel.controllerChooserPanel.btPID.setEnabled(false);
					view.leftPanel.controllerChooserPanel.btPI.setSelected(true);
				}
				model.setData(Ks, Tu, Tg, controllerTyp, tpValues, overShoot, phaseMarginOffset);
				controllerCalculated = true;
			}

			view.leftPanel.inputPanel.lbOrder.setText("        Strecken-Ordnung: " + model.getPath().getT().length);

		} catch (NumberFormatException e) {
			setExceptionLabel("Eigabefeld darf nicht leer sein.", Color.RED);
		} catch (SaniException e) {
			setExceptionLabel(e.getLocalizedMessage(), Color.RED);
		} catch (ControllerException e) {
			setExceptionLabel(e.getLocalizedMessage(), Color.RED);
		}
	}

	/**
	 * Löscht alle Benutzereingaben und die Plots und setzt das GUI zurück.
	 */
	public void clear() {
		view.leftPanel.inputPanel.tfKs.setText("");
		view.leftPanel.inputPanel.tfTu.setText("");
		view.leftPanel.inputPanel.tfTg.setText("");
		view.leftPanel.controllerValuePanel.phaseResponsePanel.setInitialValues();
		view.leftPanel.controllerValuePanel.rulesOfThumbPanel.setInitialValues();
		view.leftPanel.controllerChooserPanel.btPID.setEnabled(true);
		view.rightPanel.stepResponsePanel.deleteDatasets();
		view.leftPanel.inputPanel.lbOrder.setText("        Strecken-Ordnung:   ");
		view.leftPanel.inputPanel.lbMessage.setText(" ");

		controllerCalculated = false;
	}

	/**
	 * Löst setVisibility() der view aus und übergibt dich Sichtbarkeit des
	 * RightPanels mittels flag.
	 * 
	 * @param flag
	 */
	public void setVisibility(boolean flag) {
		if (flag == false) {
			view.setVisibility(flag, view.leftPanel.getSize());
		}
		if (flag == true) {
			Dimension dimension = new Dimension(1200, 768);
			view.setVisibility(flag, dimension);
		}
	}

	/**
	 * Überprüft die Benutzereingaben auf Richtigkeit und löst setTp() des
	 * models auf.
	 * 
	 * @param index
	 */
	public void setTp(int index) {

		setExceptionLabel(" ", Color.red);

		try {
			double[] tpValues = new double[3];
			tpValues[0] = Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[0].getText());
			tpValues[1] = Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[1].getText());
			tpValues[2] = Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[2].getText());

			if ((Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[0].getText()) == 0.0)
					|| (Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[0].getText()) == 0.0)
					|| (Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[0].getText()) == 0.0)
					|| (Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[1].getText()) == 0.0)
					|| (Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[1].getText()) == 0.0)
					|| (Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[1].getText()) == 0.0)
					|| (Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[2].getText()) == 0.0)
					|| (Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[2].getText()) == 0.0)
					|| (Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[2].getText()) == 0.0)) {
				setExceptionLabel("Werte dürfen nicht 0 sein!", Color.red);
			} else {
				model.setTp(tpValues, index);
			}

		} catch (NumberFormatException e) {
			setExceptionLabel("Eingabefeld darf nicht leer sein.", Color.red);
		}
	}

	/**
	 * Übergibt einen String und Farbe an das JLabel lbMessage des inputPanels.
	 * 
	 * @param exception
	 * @param color
	 */
	public void setExceptionLabel(String exception, Color color) {
		view.leftPanel.inputPanel.lbMessage.setForeground(color);
		view.leftPanel.inputPanel.lbMessage.setText(exception);
	}

	/**
	 * Übergibt den Phasenrand an setPhaseMargin() des models.
	 * 
	 * @param phaseMargin
	 */
	public void setPhaseMargin(double phaseMargin) {
		if (controllerCalculated == true) {
			model.setPhaseMargin(phaseMargin);
		}
	}

	/**
	 * Übergibt das Überschwingen an setOverShoot() des models.
	 * 
	 * @param overShoot
	 */
	public void setOverShoot(double overShoot) {
		if (controllerCalculated == true) {
			model.setOverShoot(overShoot);
		}
	}

	/**
	 * Setzt Attribut view gleich view.
	 * 
	 * @param view
	 */
	public void setView(View view) {
		this.view = view;
	}
}
