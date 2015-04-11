
public abstract class Regulator {

	private double Kp, Kr=0, Tn=0, Tv=0, Tp=0;
	private double Krk=0, Tnk=0, Tvk=0;
	private double Ks=0, Tu=0, Tg=0;
	
	//TODO: regulatorTyp string oder int??
	private int regulatorTyp;
	private String calculationTyp;
	
	public Regulator(){
		
	}
	
	/**
	 * Setzt die Werte für den Reglertyp, Ks, Tu und Tg
	 * @param regulatorTyp
	 * @param Ks
	 * @param Tu
	 * @param Tg
	 */
	//TODO: Die Methode setStepResponseMeasured muss noch geleert werden.
	public void setStepResponseMeasured(int regulatorTyp, double Ks, double Tu, double Tg) {
		this.Ks = Ks;
		this.Tu = Tu;
		this.Tg = Tg;
		this.regulatorTyp = regulatorTyp;
		
		calculateRegulator();
		
	}
	/**
	 * Berechnet Einstellwerte des Reglers.
	 */
	private void calculateRegulator(){
		
	}
	
	/**
	 * Gibt die Reglerwerte reglerkonform in einem Array zurück.
	 * @return
	 */
	public Object[] getDataRegulator(){

		
		
		return null;
	}
	
	/**
	 * Gibt die Reglerwerte bodekonform in einem Array zurück.
	 * @return
	 */
	public Object[] getDataBode(){
		
		
		return null;
	}
	
	/**
	 * Gibt die Berechnungsart zurück.
	 * @return
	 */
	public String getCalculationTyp(){
		
		
		
		return calculationTyp;
	}
		
	//TODO: sollte hier noch eine Abfrage für den Reglertyp eingefügt werden, um für spätere Anwendungen zur Verfügung zu stehen.
	//TODO: sollte hier noch eine Setter-Methode für den Reglertyp eingefügt werden, um für spÃ¤tere Anwendungen zur Verfüung zu stehen.
		
}

	class Rosenberg extends Regulator {
		
		private double Kp = 0, Kr=0, Tn=0, Tv=0, Tp=0;
		private double Krk=0, Tnk=0, Tvk=0;
		private double Ks=0, Tu=0, Tg=0;
		
		private void calculateRegulator(int regulatorTyp, double Ks, double Tu, double Tg){
			
			if (regulatorTyp < 2) {
				System.out.println("!!!! Nicht implementiert zum jetzigen Zeitpunkt !!!!");
				
			} else if (regulatorTyp == 2){
				Kp = (0.91/Ks)*(Tg/Tu);
				Tn = 3.3*Tu;
				Tv = 0;
			} else if (regulatorTyp == 3){
				Kp = (1.2/Ks) * (Tg/Tu);
			    Tn = 2 * Tu;
			    Tv = 0.44 * Tu;
			} else {
				System.out.println("!!!! Reglertyp nicht berechenbar !!!!");
			}
		    
			
		}
		
	}