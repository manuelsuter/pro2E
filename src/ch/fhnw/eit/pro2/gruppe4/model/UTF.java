package ch.fhnw.eit.pro2.gruppe4.model;

public class UTF {
	//TODO: Doku schreiben
	
	private double[] zahPoly;
	private double[] nenPoly;
	private double[] zahFac;
	private double[] nenFac;

	public UTF(){		
	}
	
	/**
	 * Gibt Zähler als Polynom zurück.
	 * @return zahPoly
	 */
	public double[] getZahPoly(){
		return zahPoly;
	}
	
	/**
	 * Gibt Nenner als Polynom zurück.
	 * @return nenPoly
	 */
	public double[] getNenPoly(){
		return nenPoly;
	}
	
	/**
	 * Setzt die UTF mittels Zähler- und Nennerpolynome.
	 * @param zahPoly
	 * @param nenPoly
	 */
	public void setUTFPoly(double[] zahPoly, double[] nenPoly){
		this.zahPoly = zahPoly;
		this.nenPoly = nenPoly;
	}
	
	/**
	 * Setzt die UTF mittels Zähler- und Nennerpolynome in einem Array.
	 * @param polyArray
	 */
	public void setUTFPoly(double[][] polyArray){
		this.zahPoly = polyArray[0];
		this.nenPoly = polyArray[1];
	}
	
	/**
	 * Setzt den Zähler als Polynom aus einem Double-Array.
	 * @param zahPoly
	 */
	public void setZahPoly(double[] zahPoly){
		this.zahPoly = zahPoly;
	}
	
	
	/**
	 * Setzt den Zähler als Polynom aus einem Double-Array.
	 * @param nenPoly
	 */
	public void setNenPoly(double[] nenPoly){
		this.nenPoly = nenPoly;
	}
	
	
	/**
	 * Gibt Zähler faktorisiert zurück.
	 * @return zahFac
	 */
	public double[] getZahFac(){
		return zahFac;
	}
	
	/**
	 * Gibt Nenner faktorisiert zurück.
	 * @return nenFac
	 */
	public double[] getNenFac(){
		return nenFac;
	}
	
	/**
	 * Setzt die UTF mittels faktorisiertem Zähler und Nenner.
	 * @param zahFac
	 * @param nenFac
	 */
	public void setUTFFac(double[] zahFac, double[] nenFac){
		this.zahFac = zahFac;
		this.nenFac = nenFac;
	}
	
	/**
	 * Setzt den faktorisierten Zähler aus einem Double-Array.
	 * @param zahFac
	 */
	
	public void setZahFac(double[] zahFac){
		this.zahFac = zahFac;
	}
	
	/**
	 * Setzt den faktorisierten Nenner aus einem Double-Array.
	 * @param nenFac
	 */
	public void setNenFac(double[] nenFac){
		this.nenFac = nenPoly;
	}
}
