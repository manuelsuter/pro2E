package ch.fhnw.eit.pro2.gruppe4.model;

import ch.fhnw.eit.pro2.gruppe4.utilities.Calc;

class Rosenberg extends Controller {

	public Rosenberg() {
		CALCULATIONTYP = 1;
	}

	protected void calculate() {
		double[] inputValues = path.getInputValues();
		double Ks = inputValues[Path.KsPOS];
		double Tg = inputValues[Path.TgPOS];
		double Tu = inputValues[Path.TuPOS];

		switch (controllerTyp) {
		// PI Regler
		// TODO: Bei allen so mit Controller.PI ergänzen.
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
			System.out.println("!!!! Reglertyp nicht berechenbar !!!!");
			break;
		}
		setUTFcontrollerConf();
	}
}