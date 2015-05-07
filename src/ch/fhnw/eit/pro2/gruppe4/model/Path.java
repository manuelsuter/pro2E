package ch.fhnw.eit.pro2.gruppe4.model;
import ch.fhnw.eit.pro2.gruppe4.utilities.Calc;


public class Path {
	
	public static final int KsPOS = 0, TuPOS = 1, TgPOS = 2;
	private double Ks, Tu, Tg;
	private UTF utf = new UTF();
	private double[] t;
	
	public Path(){		
	}
	
	/**
	 * 
	 * @param Ks
	 * @param Tu
	 * @param Tg
	 */
	
	public void setData(double Ks, double Tu, double Tg){
		this.Ks = Ks;
		this.Tu = Tu;
		this.Tg = Tg;
		
		calculate();
	}
	/**
	 * 
	 * @return
	 */
	
	public double[] getInputValues(){
		double[] inputValues = new double[3];
		inputValues[0] = Ks;
		inputValues[1] = Tu;
		inputValues[2] = Tg;
		
		return inputValues;		
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
//TODO: name für getT rename.
	/**
	 * 
	 * @return
	 */
	public double[] getT(){
	
		return t;
	}
	
	private void calculate(){
		double[] KsArray = new double[1];
		KsArray[0] = Ks;
		utf.setZahPoly(KsArray);
		t = Calc.sani(Tu, Tg);
		utf.setNenPoly(Calc.poly(t));
	}
}
