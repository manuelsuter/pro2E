package ch.fhnw.eit.pro2.gruppe4.utilities;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.ComplexType;

/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

public class Calc {
	
	/**
	 * Berechnet linspace mittels startValue, endValue und length.
	 * Gibt ein Array mit den entsprechenden Weten zur�ck.
	 * @param startValue
	 * @param endValue
	 * @param length
	 * @return
	 */

	public static double[] linspace(double startValue, double endValue, int length) {
		double delta = (endValue - startValue) / (length - 1);
		double[] array = new double[length];

		for (int i = 0; i < array.length; i++) {

			array[i] = startValue + delta * i;
		}
		return array;
	}
	
	
	/**
	 * Berechnet die Sani-Methode
	 * @param Tu
	 * @param Tg
	 * @return
	 */
	public static double[] sani(double Tu, double Tg){
		
		
		return null;
	}
	
	/**
	 *Ausmultiplizieren von Polynomen der Form: (1+x_1)*(1+x_2)*...
	 * @param x double[] 
	 * @return ausmultiplikation
	 */
	public double[] poly(double[] x) {//wirklich returnvalue ein double[]?
		double[] ausmulti = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			
		}
		return ausmulti;
	}
	
	/**
	 *Berechnet die diskrete Faltung
	 * @param b = Nenner
	 * @param a = Zähler
	 * @return c = die Faltung
	 */
	public double[] diskConv(double[] b, double[] a) {
		double[] c = new double[a.length +b.length -1];
		
		for (int n = 0; n < c.length; n++) {
			for (int i = Math.max(0, n-b.length +1); i <= Math.min(a.length-1, n); i++) {
				c[i] += a[i]*b[n-i];
			}
		}
		return c;
	}
	
	/**
	 *Berechnet die Inverse step fast furious transformation 
	 * @param zah = Zähler 
	 * @param nen = Nenner
	 * @param fs = Frequenz
	 * @param n = Länge Arrey
	 * @return c = die Faltung
	 */
	public double[][] schrittIfft(double[] zah, double[] nen, double[]fs, double n) {
		double[] T = new double[fs.length];//Periode
		double[] w = new double[fs.length];//Kreisfrequenz
		
		for (int i = 0; i < fs.length; i++) {
			T[i] = (1/fs[i]);
			w[i] = logspace(0,fs[i]*Math.PI,n/2);
		}
		
		
		return null;
	}
}
