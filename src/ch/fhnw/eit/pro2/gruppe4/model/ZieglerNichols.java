package ch.fhnw.eit.pro2.gruppe4.model;

/*

 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter

 * Authors: Anita Rosenberger

 * 

 * */

public class ZieglerNichols extends Controller {

	public ZieglerNichols() {
		CALCULATIONTYP = 3;
	}

	/**
	 * Berechnet Reglerwerte mittels Faustformel Ziegler/Nichols. Setzt die UTF.
	 */
	protected void calculate() {
		double[] inputValues = path.getInputValues();
		double Ks = inputValues[Path.KsPOS];
		double Tg = inputValues[Path.TgPOS];
		double Tu = inputValues[Path.TuPOS];

		switch (controllerTyp) {
		// PI Regler
		case Controller.PI:
			Kr = (0.9 / Ks) * (Tg / Tu);
			Tn = 3.33 * Tu;
			Tv = 0;
			break;
		// PID Regler
		case Controller.PID:
			Kr = (1.2 / Ks) * (Tg / Tu);
			Tn = 2 * Tu;
			Tv = 0.5 * Tu;
			break;

		default:
			System.out.println("!!!! Reglertyp nicht berechenbar !!!!");
			break;
		}
		setUTFcontrollerConf();
	}
}
