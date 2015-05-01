package ch.fhnw.eit.pro2.gruppe4.utilities;
/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

public class Calc {
	
	/**
	 * Berechnet linspace mittels startValue, endValue und length.
	 * Gibt ein Array mit den entsprechenden Werten zurück.
	 * @param startValue
	 * @param endValue
	 * @param count
	 * @return
	 */

	public static double[] linspace(double startValue, double endValue, int count) {
		double delta = (endValue - startValue) / (count - 1);
		double[] array = new double[count];

		for (int i = 0; i < array.length; i++) {

			array[i] = startValue + delta * i;
		}
		return array;
	}
	
	
	/**
	 * Berechnet logspace mittels startValue, endValue und count.
	 * Generiert ein Array mit "count" Punkten logarithmisch zwischen 10^startValue und 10^endValue aufgespannt.
	 * Gibt ein Array mit den entsprechenden Werten zurück.
	 * @param startValue
	 * @param endValue
	 * @param length
	 * @return
	 */
	public static double[] logspace(double startValue, double endValue, int count) {
		
		double logarithmicBase = 10;
		double delta = (endValue - startValue) / (count);
		double accDelta = 0.0;
		double[] array = new double[count+1];
		for (int i = 0; i < array.length; i++) {
			array[i] = Math.pow(logarithmicBase, startValue + accDelta);
			accDelta += delta;
		}
		return array;
	}
	
	/**
	 * Berechnet den Frequenzgang aufgrund von Zähler- und Nennerpolynom b resp.
	 * a sowie der Frequenzachse f.
	 * 
	 * @param b
	 *            Zählerpolynom
	 * @param a
	 *            Nennerpolynom
	 * @param f
	 *            Frequenzachse
	 * @return Komplexwertiger Frequenzgang.
	 */
	public static final Complex[] freqs(double[] b, double[] a, double[] f) {
		Complex[] res = new Complex[f.length];

		for (int k = 0; k < res.length; k++) {
			Complex jw = new Complex(0, 2.0 * Math.PI * f[k]);

			Complex zaehler = new Complex(0, 0);
			for (int i = 0; i < b.length; i++) {
				zaehler = zaehler.add(Complex.pow(jw, b.length - i - 1).mul(
						b[i]));
			}

			Complex nenner = new Complex(0, 0);
			for (int i = 0; i < a.length; i++) {
				nenner = nenner
						.add(Complex.pow(jw, a.length - i - 1).mul(a[i]));
			}
			res[k] = zaehler.div(nenner);
		}
		return res;
	}
	/**
	 * Sucht den Höchstwert von x und gibt dessen Index zurück.
	 * Kommt die der Höchstwert mehrfach vor, wird der Index des ersten Wertes ausgegeben.
	 * @param array
	 * @return
	 */
	
	public static int diskMax(double[] array) {
		double largest = array[0];
		int index = 0;
		for (int i = 1; i < array.length; i++) {
		  if ( array[i] > largest ) {
		      largest = array[i];
		      index = i;
		   }
		}
	return index;
	}	
	
	
	/**
	 * Berechnet die Sani-Methode gemäss m-File.
	 * @param Tu
	 * @param Tg
	 * @return
	 */
	public static double[] sani(double Tu, double Tg){
		
		
		
		
		return null;
	}
}
