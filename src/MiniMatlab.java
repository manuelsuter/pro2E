/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

public class MiniMatlab {

	public static double[] linspace(double startValue, double endValue, int lenght) {
		double delta = (endValue - startValue) / (lenght - 1);
		double[] array = new double[lenght];

		for (int i = 0; i < array.length; i++) {

			array[i] = startValue + delta * i;
		}
		return array;
	}
}
