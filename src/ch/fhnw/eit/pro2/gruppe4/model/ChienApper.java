package ch.fhnw.eit.pro2.gruppe4.model;

/*

 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter

 * Authors: Anita Rosenberger

 * 

 * */

public class ChienApper extends Controller {

	/**
	 * Setzt Calculationtyp.
	 */
	public ChienApper() {
		CALCULATIONTYP = 4;
	}

	/**
	 * Berechnet Reglerwerte mittels Faustformel Chien/Hrones/Reswick und löst setUTFControllerConf() aus.
	 * @throws ControllerException 
	 */
	protected void calculate() throws ControllerException {
		double[] inputValues = path.getInputValues();
		double Ks = inputValues[Path.KsPOS];
		double Tg = inputValues[Path.TgPOS];
		double Tu = inputValues[Path.TuPOS];

		switch (controllerTyp) {
		// PI Regler:
		case Controller.PI:
			Kr = 0.35 * Tg / (Ks * Tu);
			Tn = 1.2 * Tg;
			Tv = 0;
			break;
			
		// PID Regler:	
		case Controller.PID:
			Kr = 0.6 * Tg / (Ks * Tu);
			Tn = Tg;
			Tv = 0.5 * Tu;
			break;
			
		default:
			throw new ControllerException();	
		}
		setUTFcontrollerConf();
	}
}
