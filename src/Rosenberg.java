class Rosenberg extends Regulator {
		
		private double Kr=0, Tn=0, Tv=0, Tp=0;
		private double Krk=0, Tnk=0, Tvk=0;
		private double Ks=0, Tu=0, Tg=0;
		
		private void calculateRegulator(int regulatorTyp, double Ks, double Tu, double Tg){
			
			if (regulatorTyp < 2) {
				System.out.println("!!!! Nicht implementiert zum jetzigen Zeitpunkt !!!!");
				
			} else if (regulatorTyp == 2){
				Kr = (0.91/Ks)*(Tg/Tu);
				Tn = 3.3*Tu;
				Tv = 0;
			} else if (regulatorTyp == 3){
				Kr = (1.2/Ks) * (Tg/Tu);
			    Tn = 2 * Tu;
			    Tv = 0.44 * Tu;
			} else {
				System.out.println("!!!! Reglertyp nicht berechenbar !!!!");
			}
		}
	}