package ch.fhnw.eit.pro2.gruppe4.model;

/*

 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter

 * Authors: Anita Rosenberger

 * 

 * */

class Rosenberg extends Controller {

	/**
	 * Setzt Calculationtyp.
	 */
	public Rosenberg() {
		CALCULATIONTYP = 1;
	}

	/**
	 * Berechnet Reglerwerte mittels Faustformel Rosenberg und l�st setUTFControllerConf() aus.
	 * @throws ControllerException 
	 */
	protected void calculate() throws ControllerException {
		double[] inputValues = path.getInputValues();
		double Ks = inputValues[Path.KsPOS];
		double Tg = inputValues[Path.TgPOS];
		double Tu = inputValues[Path.TuPOS];

		switch (controllerTyp) {
		// PI Regler
		case Controller.PI:
			Kr = (0.91 / Ks) * (Tg / Tu);
			Tn = 3.3 * Tu;
			Tv = 0;
			break;
		// PID Regler
		case Controller.PID:
			Kr = (1.2 / Ks) * (Tg / Tu);
			Tn = 2 * Tu;
			Tv = 0.44 * Tu;
			break;

		default:
			throw new ControllerException();
		}
		setUTFcontrollerConf();
	}
}