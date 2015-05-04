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
	 * @return res
	 */
	public double[] poly(double[] x) {
		double[] res = new double[x.length];
		for (int i = 2; i < res.length; i++) {
			res = diskConv(x, res);
		}
		return res;
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
	 * @param n = Länge
	 * @return c = die Faltung
	 */
	public double[][] schrittIfft(double[] zah, double[] nen, double fs, int n) {
		
		double T = (1/fs);//Periode
		double[] w = new double[(int)fs];//Kreisfrequenz
		Complex[] H = new Complex[];
		
		//Frequenzachse berechnen
		w = linspace(0.0, fs*Math.PI, n/2);
		
		//Frequenzgang berechnen
		H = freqs(zah, nen, w);
		
		//Sym. Array für Ifft() erstelllen
		for (int i = 1; i < n/2; i++) {
			H[i] = i;
		}
		H[n/2+1] = 0;
		for (int i = n/2; i > 2; i--) {
			H[i]= i;//Was machen mit conj?
		}
		
		//Impulsantwort berechen
		double[] h = new double[H.length];//welche Länge
		for (int i = 0; i < h.length; i++) {
			h[i]=ifft(H[i]);
		}
		//Schrittantwort berechnen
		double[] zwres = new double[2];
		zwres[0]=1.0;
		zwres[1] = n+1.0;
		double[] y = new double[h.length];
		y = diskConv(h, zwres);
		//Resultate ausschneiden
		for (int i = 0; i < y.length/2; i++) {
			
  
		}
		double[] t = new double[];//Länge?
		t = linspace(0.0, (y.length-1)*T, y.length);//' wie?
		
		return null;
	}
}
