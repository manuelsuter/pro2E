
public abstract class Path {
	
	public static final int KsPOS = 0, TuPOS = 1, TgPOS = 2;
	private double Ks, Tu, Tg;
	private UTF utf;
	
	public Path(){		
	}
	
	public void setData(double Ks, double Tu, double Tg){
		this.Ks = Ks;
		this.Tu = Tu;
		this.Tg = Tg;
		
		calculate();
	}
	
	
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

	
	private void calculate(){
		double[] KsArray = new double[1];
		KsArray[0] = Ks;
		utf.setZahPoly(KsArray);
		double[] t = MiniMatlab.sani(Tu, Tg);
		
	}
}
