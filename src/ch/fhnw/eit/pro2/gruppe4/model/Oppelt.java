package ch.fhnw.eit.pro2.gruppe4.model;

public class Oppelt extends Controller {

	@Override
	protected void calculate(int controllerTyp, Path path) {
		double[] inputValues = path.getInputValues();
		double Ks = inputValues[Path.KsPOS];
		double Tg = inputValues[Path.TgPOS];
		double Tu = inputValues[Path.TuPOS];
		
		if (controllerTyp < 2) {
			System.out.println("!!!! Nicht implementiert zum jetzigen Zeitpunkt !!!!");
		}
		
		if (controllerTyp == 2) {//PI Regler
			Kr=(0.8/Ks)*(Tg/Tu);
		    Tn=3*Tu;
		    Tv=0;
		}
		if (controllerTyp == 3) {//PID Regler
		    Kr=(1.2/Ks)*(Tg/Tu);
		    Tn=2*Tu;
		    Tv=0.42*Tu;
		}
		else {
			System.out.println("!!!! Reglertyp nicht berechenbar !!!!");
		}
	}
}
