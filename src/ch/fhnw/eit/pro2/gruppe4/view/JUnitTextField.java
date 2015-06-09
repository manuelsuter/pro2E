/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * Authors: Manuel Suter, Benjamin Müller
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

import java.text.DecimalFormat;

public class JUnitTextField extends JDoubleTextField {

	public static final String[] unitSize = { "a", "f", "p", "n", "u", "m", "", "k", "M", "G", "T", "P", "E" };
	public static final String[] size = { "e-18", "e-15", "e-12", "e-9", "e-6", "e-3", "", "e3", "e6", "e9", "e12",
			"e15", "e18" };

	private DecimalFormat f = new DecimalFormat("#0.00");

	private static final long serialVersionUID = 1L;

	/**
	 * Erzeugt Textfeld.
	 * @param text
	 * @param columns
	 * @param allowNegativeValues
	 */
	public JUnitTextField(String text, int columns, boolean allowNegativeValues) {
		super(text, columns, allowNegativeValues);
	}

	/**
	 * Setzt Text im Textfeld.
	 * @param value
	 */
	public void setText(double value) {
		int unitIndex = 0;
		if (Math.abs(value) < 1e-18) {
			value = 0.0;
			unitIndex = 0;
		} else if (Math.abs(value) < 1.00) {
			while (Math.abs(value) < 1.00) {
				value = value * 1000.0;
				unitIndex++;
			}
		} else if (Math.abs(value) >= 1000.0) {
			while (Math.abs(value) >= 1000.0) {
				value = value / 1000.0;
				unitIndex--;
			}
		}

		String valueRounded = f.format(Math.round(value * 100.0) / 100.0);

		super.setText(valueRounded + size[-unitIndex + 6]);
	}
	
	/**
	 * Setzt Text und Einheit in das Textfeld.
	 * @param value
	 * @param unit
	 */
	public void setText(double value, String unit) {
		int unitIndex = 0;
		if (Math.abs(value) < 1e-18) {
			value = 0.0;
			unitIndex = 0;
		} else if (value < 1.00) {
			while (value < 1.00) {
				value = value * 1000.0;
				unitIndex++;
			}
		} else if (value >= 1000.0) {
			while (value >= 1000.0) {
				value = value / 1000.0;
				unitIndex--;
			}
		}

		String valueRounded = f.format(Math.round(value * 100.0) / 100.0);

		super.setText(valueRounded + unitSize[-unitIndex + 6] + unit);
	}
}
