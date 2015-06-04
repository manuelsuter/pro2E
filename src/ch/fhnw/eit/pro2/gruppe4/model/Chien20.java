package ch.fhnw.eit.pro2.gruppe4.model;

/*

 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter

 * Authors: Anita Rosenberger

 * 

 * */

public class Chien20 extends Controller {

	public Chien20() {
		CALCULATIONTYP = 4;
	}

	/**
	 * Berechnet Reglerwerte mittels Faustformel Chien/Hrones/Reswick 20%. Setzt
	 * die UTF.
	 */
	protected void calculate() {
		double[] inputValues = path.getInputValues();
		double Ks = inputValues[Path.KsPOS];
		double Tg = inputValues[Path.TgPOS];
		double Tu = inputValues[Path.TuPOS];

		switch (controllerTyp) {
		// PI Regler
		case Controller.PI:
			Kr = 0.7 * Tg / (Ks * Tu);
			Tn = 2.3 * Tu;
			Tv = 0;
			break;
		// PID Regler
		case Controller.PID:
			Kr = 1.2 * Tg / (Ks * Tu);
			Tn = 2 * Tu;
			Tv = 0.42 * Tu;
			break;
		default:
			System.out.println("!!!! Reglertyp nicht berechenbar !!!!");
			break;
		}
		setUTFcontrollerConf();
	}
}
