package ch.fhnw.eit.pro2.gruppe4.model;

public class ChienApper extends Controller {
	
	public ChienApper(){	
		CALCULATIONTYP = 5;
	}
	
	protected void calculate() {
		double[] inputValues = path.getInputValues();
		double Ks = inputValues[Path.KsPOS];
		double Tg = inputValues[Path.TgPOS];
		double Tu = inputValues[Path.TuPOS];
		
		switch (controllerTyp) {
		// PI Regler
		case 2:
			 Kr=0.35*Tg/(Ks*Tu);
	         Tn=1.2*Tu;
	         Tv=0;
			break;
		// PID Regler
		case 3:
			Kr=0.95*Tg/(Ks*Tu);
            Tn=2.4*Tu;
            Tv=0.42*Tu;
			break;

		default:
			System.out.println("!!!! Reglertyp nicht berechenbar !!!!");
			break;
		}
		setUTFcontrollerConf();

	}
}


