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
	 * Gibt ein Array mit den entsprechenden Weten zurück.
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
}
