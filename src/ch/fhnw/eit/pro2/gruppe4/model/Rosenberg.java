package ch.fhnw.eit.pro2.gruppe4.model;

class Rosenberg extends Controller {
	
	public Rosenberg(){		
	}
		
	protected void calculate(){
		double[] inputValues = path.getInputValues();
		double Ks = inputValues[Path.KsPOS];
		double Tg = inputValues[Path.TgPOS];
		double Tu = inputValues[Path.TuPOS];
			
		if (controllerTyp < 2) {
			System.out.println("!!!! Nicht implementiert zum jetzigen Zeitpunkt !!!!");
				
		} else if (controllerTyp == 2){ //PI Regler
			Kr = (0.91/Ks)*(Tg/Tu);
			Tn = 3.3*Tu;
			Tv = 0;
		} else if (controllerTyp == 3){//PID Regler
			Kr = (1.2/Ks) * (Tg/Tu);
		    Tn = 2 * Tu;
			Tv = 0.44 * Tu;
		} else {
			System.out.println("!!!! Reglertyp nicht berechenbar !!!!");
		}
	}
}