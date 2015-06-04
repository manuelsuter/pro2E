package ch.fhnw.eit.pro2.gruppe4.model;

/*

 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter

 * Authors: Manuel Suter, Benjamin Müller

 * 

 * */

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
