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

package ch.fhnw.eit.pro2.gruppe4.model;

import ch.fhnw.eit.pro2.gruppe4.utilities.Calc;

public class Path {

	public static final int KsPOS = 0, TuPOS = 1, TgPOS = 2;
	private double Ks, Tu, Tg;
	private UTF utf = new UTF();
	private double[] t;

	public Path() {
	}

	public void setData(double Ks, double Tu, double Tg) throws SaniException {
		this.Ks = Ks;
		this.Tu = Tu;
		this.Tg = Tg;

		calculate();
	}

	public double[] getInputValues() {
		double[] inputValues = new double[3];
		inputValues[0] = Ks;
		inputValues[1] = Tu;
		inputValues[2] = Tg;

		return inputValues;
	}

	/**
	 * Gibt Zähler der UTF in einem Double-Array als Polynom zurück.
	 * 
	 * @return
	 */
	public double[] getUTFZahPoly() {
		return utf.getZahPoly();
	}

	/**
	 * Gibt Nenner der UTF in einem Double-Array als Polynom zurück.
	 * 
	 * @return
	 */
	public double[] getUTFNenPoly() {
		return utf.getNenPoly();
	}

	/**
	 * Gibt die Zeitkonstante t der Regel-Strecke zurück.
	 * 
	 * @return
	 */
	public double[] getT() {
		return t;
	}

	/**
	 * Setzt Ks in ein KsArray. Setzt Ks Array als Zaehler der UTF in
	 * Polynomform. Berechnet t mittels Tu, Tg und sani(). Setzt t als Nenner
	 * der UTF.
	 * 
	 * @throws SaniException
	 */
	private void calculate() throws SaniException {
		double[] KsArray = new double[1];
		KsArray[0] = Ks;
		utf.setZahPoly(KsArray);

		t = Calc.sani(Tu, Tg);

		utf.setNenPoly(Calc.poly(t));
	}
}
