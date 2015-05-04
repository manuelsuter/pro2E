package ch.fhnw.eit.pro2.gruppe4.model;


public abstract class Controller {

	protected double Kr=0, Tn=0, Tv=0, Tp=0;
	protected double Krk=0, Tnk=0, Tvk=0;
	
	public static final int KrPOS = 0, TnPOS = 1, TvPOS = 2, TpPOS = 3; 
	public static final int P = 0, I = 1, PI = 2, PID = 3;
	public static final String[] calculationTypName = {"Phasengang", "Rosenberg", "Oppelt", "Ziegler/Nichols", "Chien/Hrones/Reswick (20%)", "Chien/Hrones/Reswick (aperiod.)"};
	
	protected int controllerTyp; // PI/PID
	protected int CALCULATIONTYP; // Rosenberg, CHN...
	protected Path path;
	protected UTF utf = new UTF();
	
	public Controller(){
		
	}
	
	
	/**
	 * Gibt Zähler der UTF in einem Double-Array als Polynom zurück.
	 * @return
	 */
	public double[] getUTFZahPoly(){
		return utf.getZahPoly();
	}
	
	/**
	 * Gibt Nenner der UTF in einem Double-Array als Polynom zurück.
	 * @return
	 */
	public double[] getUTFNenPoly(){
		return utf.getNenPoly();
	}
	
	/**
	 * Gibt Reglertyp (PI/PID) als int zurück.
	 * @return
	 */
	public int getControllerTyp(){
		return controllerTyp;
	}

	
	/**
	 * Gibt die Reglerwerte reglerkonform in einem Double-Array zurück.
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
	 * Setzt die Input-Wert für die Berechnung.
	 * Löst calculate() aus.
	 * @param path
	 */
	public void setData(int controllerTyp, Path path){
		this.controllerTyp = controllerTyp;
		this.path = path;

		calculate();
	}		
	
	/** 
	 * Setzt den Input-Wert für Tp.
	 * Löst calculate() aus.
	 * @param path
	 */
	public void setTp(double Tp){
		this.Tp = Tp;
		calculate();
	}		

	/**
	 * Berechnet Einstellwerte des jeweiligen Reglers.
	 */
	protected abstract void calculate();
	
}

	