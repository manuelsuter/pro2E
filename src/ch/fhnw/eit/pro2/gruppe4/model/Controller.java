package ch.fhnw.eit.pro2.gruppe4.model;


public abstract class Controller {

	public double Kr=0, Tn=0, Tv=0, Tp=0;
	private double Krk=0, Tnk=0, Tvk=0;
	
	public static final int KrPOS = 0, TnPOS = 1, TvPOS = 2, TpPOS = 3; 
	public static final int P = 0, I = 1, PI = 2, PID = 3;
	
	private int controllerTyp; // PI/PID
	private String calculationTyp; // Rosenberg, CHN...
	private Path path;
	private UTF utf;
	
	public Controller(){
		
	}
	
	
	/**
	 * Gibt Z�hler der UTF in einem Double-Array als Polynom zur�ck.
	 * @return
	 */
	public double[] getUTFZahPoly(){
		return utf.getZahPoly();
	}
	
	/**
	 * Gibt Nenner der UTF in einem Double-Array als Polynom zur�ck.
	 * @return
	 */
	public double[] getUTFNenPoly(){
		return utf.getNenPoly();
	}
	
	/**
	 * Gibt Reglertyp (PI/PID) als int zur�ck.
	 * @return
	 */
	public int getControllerTyp(){
		return controllerTyp;
	}
	
	/**
	 * Gibt die Berechnungsart (Rosenberg, CHN) als String zur�ck.
	 * @return
	 */
	public String getCalculationTyp(){
		return calculationTyp;
	}
	
	/**
	 * Gibt die Reglerwerte reglerkonform in einem Double-Array zur�ck.
	 * @return
	 */
	public double[] getControllerValues(){
		double[] values = new double[4];
		values[0] = Kr;
		values[1] = Tn;
		values[2] = Tv;
		values[3] = Tp;

		return values;
	}
	
	/** 
	 * Setzt die Input-Wert f�r die Berechnung.
	 * L�st calculate() aus.
	 * @param path
	 */
	public void setData(int controllerTyp, String calculationTyp, Path path){
		this.controllerTyp = controllerTyp;
		this.calculationTyp = calculationTyp;
		this.path = path;

		calculate(controllerTyp, path);
	}		
	
	//TODO: K�nnen abstrakte Klassen private sein?
	/**
	 * Berechnet Einstellwerte des jeweiligen Reglers.
	 */
	protected abstract void calculate(int controllerTyp, Path path);
	
}

	