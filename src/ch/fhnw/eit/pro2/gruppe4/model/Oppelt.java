package ch.fhnw.eit.pro2.gruppe4.model;

public class Oppelt extends Controller {
	
	public static final int CALCULATIONTYP = 2;

	protected void calculate() {
		double[] inputValues = path.getInputValues();
		double Ks = inputValues[Path.KsPOS];
		double Tg = inputValues[Path.TgPOS];
		double Tu = inputValues[Path.TuPOS];
		
		switch (controllerTyp) {
		// PI Regler
		case 2:
			Kr=(0.8/Ks)*(Tg/Tu);
		    Tn=3*Tu;
		    Tv=0;
			break;
		// PID Regler
		case 3:
			Kr=(1.2/Ks)*(Tg/Tu);
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
