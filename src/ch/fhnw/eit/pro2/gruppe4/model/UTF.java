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
	 * @return
	 */
	public double[] getZahPoly(){
		return zahPoly;
	}
	
	/**
	 * Gibt Nenner als Polynom zurück.
	 * @return
	 */
	public double[] getNenPoly(){
		return nenPoly;
	}
	
	/**
	 * Setzt die UTF mittels Zähler- und Nennerpolynome.
	 * @return
	 */
	public void setUTFPoly(double[] zahPoly, double[] nenPoly){
		this.zahPoly = zahPoly;
		this.nenPoly = nenPoly;
	}
	
	/**
	 * Setzt den Zähler als Polynom aus einem Double-Array.
	 * @return
	 */
	public void setZahPoly(double[] zahPoly){
		this.zahPoly = zahPoly;
	}
	
	
	/**
	 * Setzt den Zähler als Polynom aus einem Double-Array.
	 * @return
	 */
	public void setNenPoly(double[] nenPoly){
		this.nenPoly = nenPoly;
	}
	
	
	
	
	/**
	 * Gibt Zähler faktorisiert zurück.
	 * @return
	 */
	public double[] getZahFac(){
		return zahFac;
	}
	
	/**
	 * Gibt Nenner faktorisiert zurück.
	 * @return
	 */
	public double[] getNenFac(){
		return nenFac;
	}
	
	/**
	 * Setzt die UTF mittels faktorisiertem Zähler und Nenner.
	 * @return
	 */
	public void setUTFPFac(double[] zahFac, double[] nenFac){
		this.zahFac = zahFac;
		this.nenFac = nenFac;
	}
	
	/**
	 * Setzt den faktorisierten Zähler aus einem Double-Array.
	 * @return
	 */
	public void setZahFac(double[] zahFac){
		this.zahFac = zahFac;
	}
	
	/**
	 * Setzt den faktorisierten Nenner aus einem Double-Array.
	 * @return
	 */
	public void setNenFac(double[] nenFac){
		this.nenFac = nenPoly;
	}
}
