package ch.fhnw.eit.pro2.gruppe4.model;

public class Chien20 extends Controller {
	
	

	
	public Chien20(){	
		CALCULATIONTYP = 4;
	}

	protected void calculate() {
		double[] inputValues = path.getInputValues();
		double Ks = inputValues[Path.KsPOS];
		double Tg = inputValues[Path.TgPOS];
		double Tu = inputValues[Path.TuPOS];
		
		switch (controllerTyp) {
		//PI Regler
		case 2:
			Kr=0.7*Tg/(Ks*Tu);
            Tn=2.3*Tu;
            Tv=0;
			break;
		// PID Regler
		case 3:
		     Kr=1.2*Tg/(Ks*Tu);
	         Tn=2*Tu;
	         Tv=0.42*Tu;
			break;
		default:
			System.out.println("!!!! Reglertyp nicht berechenbar !!!!");
			break;
		}
		setUTFcontrollerConf();
	}
}


