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

public class UTF {

	private double[] zahPoly;
	private double[] nenPoly;
	private double[] zahFac;
	private double[] nenFac;

	public UTF() {
	}

	/**
	 * Gibt Zähler als Polynom zurück.
	 * 
	 * @return
	 */
	public double[] getZahPoly() {
		return zahPoly;
	}

	/**
	 * Gibt Nenner als Polynom zurück.
	 * 
	 * @return
	 */
	public double[] getNenPoly() {
		return nenPoly;
	}

	/**
	 * Setzt die UTF mittels Zähler- und Nennerpolynome.
	 * @param zahPoly
	 * @param nenPoly
	 */
	public void setUTFPoly(double[] zahPoly, double[] nenPoly) {
		this.zahPoly = zahPoly;
		this.nenPoly = nenPoly;
	}

	/**
	 * Setzt die UTF mittels Zähler- und Nennerpolynome in einem Array.
	 * 
	 * @param polyArray
	 */
	public void setUTFPoly(double[][] polyArray) {
		this.zahPoly = polyArray[0];
		this.nenPoly = polyArray[1];
	}

	/**
	 * Setzt den Zähler als Polynom aus einem Double-Array.
	 * 
	 * @param zahPoly
	 */
	public void setZahPoly(double[] zahPoly) {
		this.zahPoly = zahPoly;
	}

	/**
	 * Setzt den Zähler als Polynom aus einem Double-Array.
	 * 
	 * @param nenPoly
	 */
	public void setNenPoly(double[] nenPoly) {
		this.nenPoly = nenPoly;
	}

	/**
	 * Gibt Zähler faktorisiert zurück.
	 * 
	 * @return zahFac
	 */
	public double[] getZahFac() {
		return zahFac;
	}

	/**
	 * Gibt Nenner faktorisiert zurück.
	 * 
	 * @return nenFac
	 */
	public double[] getNenFac() {
		return nenFac;
	}

	/**
	 * Setzt die UTF mittels faktorisiertem Zähler und Nenner.
	 * @param zahFac
	 * @param nenFac
	 */
	public void setUTFFac(double[] zahFac, double[] nenFac) {
		this.zahFac = zahFac;
		this.nenFac = nenFac;
	}

	/**
	 * Setzt den faktorisierten Zähler aus einem Double-Array.
	 * @param zahFac
	 */
	public void setZahFac(double[] zahFac) {
		this.zahFac = zahFac;
	}

	/**
	 * Setzt den faktorisierten Nenner aus einem Double-Array.
	 * @param nenFac
	 */
	public void setNenFac(double[] nenFac) {
		this.nenFac = nenPoly;
	}
}
