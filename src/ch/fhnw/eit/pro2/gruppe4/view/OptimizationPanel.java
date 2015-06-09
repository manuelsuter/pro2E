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
import java.util.Hashtable;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class OptimizationPanel extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 1L;
	private GUIController guiController;
	public JSlider jsPhaseMargin;
	public JSlider jsOverShoot;
	private JLabel lbPhaseMargin, lbOverShoot;
	private double phaseMarginOffset;
	private double overShootValue;
	private int phaseMarginSliderValue;
	private int overShootSliderValue;
	public static final String[] phaseMarginValues = { "0.1", "1", "2", "5", "10", "15", "20", "25", "30" };
	int x = 0;

	/**
	 * Setzt GridBagLayout. Besitzt die Slider und Labels für das
	 * Optimierungspanel.
	 * 
	 * @param guiController
	 */
	public OptimizationPanel(GUIController guiController) {
		super(new GridBagLayout());
		this.guiController = guiController;
		setBorder(MyBorderFactory.createMyBorder(" Optimierungen "));

		lbOverShoot = new JLabel("Überschwingen in %");
		add(lbOverShoot, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(5, 10, 5, 10), 0, 0));

		// Baut und added Slider für Überschwingen.
		jsOverShoot = new JSlider(0, 8, 6);

		Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
		table.put(0, new JLabel("0.1"));
		table.put(1, new JLabel("1"));
		table.put(2, new JLabel("2"));
		table.put(3, new JLabel("5"));
		table.put(4, new JLabel("10"));
		table.put(5, new JLabel("15"));
		table.put(6, new JLabel("20"));
		table.put(7, new JLabel("25"));
		table.put(8, new JLabel("30"));
		jsOverShoot.setLabelTable(table);

		jsOverShoot.setSnapToTicks(true);
		jsOverShoot.setMajorTickSpacing(1);
		jsOverShoot.setPaintTicks(true);
		jsOverShoot.setPaintLabels(true);
		add(jsOverShoot, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(5, 10, 5, 10), 0, 0));
		jsOverShoot.addChangeListener(this);

		// Baut und added Slider für Pasenrandverschiebung.
		lbPhaseMargin = new JLabel("Optimierung");
		add(lbPhaseMargin, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(5, 10, 5, 10), 0, 0));

		jsPhaseMargin = new JSlider(0, 9, 2);
		Hashtable<Integer, JLabel> tablePhaseMargin = new Hashtable<Integer, JLabel>();
		tablePhaseMargin.put(0, new JLabel(" "));
		tablePhaseMargin.put(1, new JLabel(" "));
		tablePhaseMargin.put(2, new JLabel(" "));
		tablePhaseMargin.put(3, new JLabel(" "));
		tablePhaseMargin.put(4, new JLabel(" "));
		tablePhaseMargin.put(5, new JLabel(" "));
		tablePhaseMargin.put(6, new JLabel(" "));
		tablePhaseMargin.put(7, new JLabel(" "));
		tablePhaseMargin.put(8, new JLabel(" "));
		tablePhaseMargin.put(9, new JLabel(" "));

		jsPhaseMargin.setLabelTable(tablePhaseMargin);
		jsPhaseMargin.setMajorTickSpacing(1);
		jsPhaseMargin.setPaintTicks(true);
		jsPhaseMargin.setPaintLabels(true);

		add(jsPhaseMargin, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(5, 10, 5, 10), 0, 0));
		jsPhaseMargin.addChangeListener(this);
	}

	/**
	 * Gibt das Überschwingen in Abhängigkeit der Slider-Position zurück.
	 * 
	 * @return overShootValue
	 */
	public double getOverShoot() {
		overShootSliderValue = jsOverShoot.getValue();

		switch (overShootSliderValue) {
		case 0:
			overShootValue = 0.1;
			break;

		case 1:
			overShootValue = 1.0;
			break;

		case 2:
			overShootValue = 2.0;
			break;

		case 3:
			overShootValue = 5.0;
			break;

		case 4:
			overShootValue = 10.0;
			break;

		case 5:
			overShootValue = 15.0;
			break;

		case 6:
			overShootValue = 20.0;
			break;

		case 7:
			overShootValue = 25.0;
			break;

		default:
			overShootValue = 30.0;
			break;
		}
		return overShootValue;
	}

	/**
	 * Gibt den Phasenrand in Abhängikeit der Slider-Position zurück.
	 * 
	 * @return phaseMarginOffset
	 */
	public double getPhaseMargin() {
		phaseMarginSliderValue = jsPhaseMargin.getValue();

		switch (phaseMarginSliderValue) {
		case 0:
			phaseMarginOffset = 0.0;
			break;

		case 2:
			phaseMarginOffset = 10.0;
			break;

		case 3:
			phaseMarginOffset = 15.0;
			break;

		case 4:
			phaseMarginOffset = 20.0;
			break;

		case 5:
			phaseMarginOffset = 25.0;
			break;

		case 6:
			phaseMarginOffset = 30.0;
			break;

		case 7:
			phaseMarginOffset = 35.0;
			break;

		case 8:
			phaseMarginOffset = 40.0;
			break;

		case 9:
			phaseMarginOffset = 45.0;
			break;

		default:
			phaseMarginOffset = 5.0;
			break;
		}
		return phaseMarginOffset;
	}

	/**
	 * Erkennt Sliderbewegung und übergibt die Werte an setOverShoot() und
	 * setPhaseMargin() des guiControllers.
	 * @param e
	 */
	public void stateChanged(ChangeEvent e) {

		if (e.getSource() == jsOverShoot) {
			if (overShootSliderValue == jsOverShoot.getValue()) {

			} else {
				overShootSliderValue = jsOverShoot.getValue();
				switch (overShootSliderValue) {
				case 0:
					overShootValue = 0.1;
					break;

				case 1:
					overShootValue = 1.0;
					break;

				case 2:
					overShootValue = 2.0;
					break;

				case 3:
					overShootValue = 5.0;
					break;

				case 4:
					overShootValue = 10.0;
					break;

				case 5:
					overShootValue = 15.0;
					break;

				case 6:
					overShootValue = 20.0;
					break;

				case 7:
					overShootValue = 25.0;
					break;

				default:
					overShootValue = 30.0;
					break;
				}
				guiController.setOverShoot(overShootValue);
			}
		}
		if (e.getSource() == jsPhaseMargin) {
			if (phaseMarginSliderValue == jsPhaseMargin.getValue()) {

			} else {
				phaseMarginSliderValue = jsPhaseMargin.getValue();
				switch (phaseMarginSliderValue) {
				case 0:
					phaseMarginOffset = 0.0;
					break;

				case 2:
					phaseMarginOffset = 10.0;
					break;

				case 3:
					phaseMarginOffset = 15.0;
					break;

				case 4:
					phaseMarginOffset = 20.0;
					break;

				case 5:
					phaseMarginOffset = 25.0;
					break;

				case 6:
					phaseMarginOffset = 30.0;
					break;

				case 7:
					phaseMarginOffset = 35.0;
					break;

				case 8:
					phaseMarginOffset = 40.0;
					break;

				case 9:
					phaseMarginOffset = 45.0;
					break;

				default:
					phaseMarginOffset = 5.0;
					break;
				}
				guiController.setPhaseMargin(phaseMarginOffset);
			}
		}
		// Prüft die Position des Optimierungs-Sliders und deaktiviert den
		// Slider fürs Überschwingen, wenn die Optimierung gross ist.
		if (jsPhaseMargin.getValue() >= 8) {
			jsOverShoot.setEnabled(false);
		} else {
			jsOverShoot.setEnabled(true);
		}
	}

	/**
	 * Keine Verwendung bis dato.
	 * 
	 * @param obs
	 * @param obj
	 */
	public void update(Observable obs, Object obj) {
	}

}
