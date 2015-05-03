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
	public double[] concat (double[] x,double[] y, double[] z){
		double [] combined = ArrayUtils.addAll(x, y, z);
		return combined;
	}
	
	public double[] controllerconform (Krk, Tnk, Tvk, Tp, controllerTyp){
		if (controllerTyp=3) {	
		Kr= Krk*(1+Tvk/Tnk);
		Tn=	Tnk+Tvk-Tp;
		Tv=((Tnk*Tvk)/(Tnk+Tvk-Tp))-Tp;
		}else{		
		}	
		
		
		return test;
	}
	public double[] bodeconform (Kr, Tn, Tv, Tp, controllerTyp){
		if (controllerTyp=3) {
			e=Math.sqrt(1-((4*Tn*(Tv-Tp))/((Tn+Tp)^2)));
			
		Krk=0.5*Kr*(1+(Tp/Tnk)*(1+e));
		Tnk=0.5*(Tn+Tp)*(1+e);
		Tvk=0.5*(Tn+Tp)*(1-e);
		}else{
			
		}
		
		return test;
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
