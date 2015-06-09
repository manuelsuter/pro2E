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

public abstract class Controller {

	protected double Kr = 0, Tn = 0, Tv = 0, Tp = 0;
	protected double Krk = 0, Tnk = 0, Tvk = 0;

	public static final int KrPOS = 0, TnPOS = 1, TvPOS = 2, TpPOS = 3, CONTROLLERTYPPOS = 4, CALCULATIONTYPPOS = 5,
			KrkPOS = 6, TnkPOS = 7, TvkPOS = 8;
	public static final int P = 0, I = 1, PI = 2, PID = 3;
	public static final String[] calculationTypName = { "Phasengang", "Rosenberg", "Oppelt",
			"Chien/Hrones/Reswick (20%)", "Chien/Hrones/Reswick (aperiod.)" };

	protected int controllerTyp; // PI/PID
	protected int CALCULATIONTYP; // Rosenberg, CHN...
	protected Path path;
	protected UTF utf = new UTF();

	protected double phaseMarginOffset, overShoot;

	public Controller() {

	}

	/**
	 * Setzt die Input-Wert für die Berechnung ohne Phasenrandverschiebung. Löst
	 * calculate() aus.
	 * 
	 * @param controllerTyp
	 * @param path
	 * @throws ControllerException
	 */
	public void setData(int controllerTyp, Path path) throws ControllerException {
		this.controllerTyp = controllerTyp;
		this.path = path;

		calculate();
	}

	/**
	 * Setzt die Input-Wert für die Berechnung inklusive Phasenrandverschiebung.
	 * Löst calculate() aus.
	 * 
	 * @param controllerTyp
	 * @param path
	 * @param Tp
	 * @param overShoot
	 * @param phaseMarginOffset
	 * @throws ControllerException
	 */
	public void setData(int controllerTyp, Path path, double Tp, double overShoot, double phaseMarginOffset)
			throws ControllerException {
		this.controllerTyp = controllerTyp;
		this.path = path;
		this.Tp = Tp;
		this.phaseMarginOffset = phaseMarginOffset;
		this.overShoot = overShoot;

		calculate();
	}

	/**
	 * Setzt Überschwingen.
	 * 
	 * @param overShoot
	 */
	public void setOverShoot(double overShoot) {

	}

	/**
	 * Setzt Tp.
	 * 
	 * @param Tp
	 */
	public void setTp(double Tp) {

	}

	/**
	 * Setzt die Phasenverschiebung.
	 * 
	 * @param phaseMargin
	 */
	public void setPhaseMargin(double phaseMargin) throws ControllerException {

	}

	/**
	 * Setzte die UTF mit reglerkonformen Werten.
	 */
	protected void setUTFcontrollerConf() {
		Tp = Tv / 10;

		utf.setUTFPoly(Calc.utfControllerConf(controllerTyp, Kr, Tn, Tv, Tp));
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
	 * Gibt Reglertyp (PI/PID) als int zurück.
	 * 
	 * @return
	 */
	public int getControllerTyp() {
		return controllerTyp;
	}

	/**
	 * Gibt die Phasenverschiebung zurück.
	 * 
	 * @return
	 */
	public double getPhaseMargin() {
		return phaseMarginOffset;
	}

	/**
	 * Gibt die Reglerwerte reglerkonform in einem Double-Array zurück.
	 * 
	 * @return
	 */
	public double[] getControllerValues() {
		double[] values = new double[9];
		values[0] = Kr;
		values[1] = Tn;
		values[2] = Tv;
		values[3] = Tp;
		values[4] = controllerTyp;
		values[5] = CALCULATIONTYP;
		values[6] = Krk;
		values[7] = Tnk;
		values[8] = Tvk;

		return values;
	}

	/**
	 * Berechnet Einstellwerte des jeweiligen Reglers.
	 * 
	 * @throws ControllerException
	 */
	protected abstract void calculate() throws ControllerException;
}
