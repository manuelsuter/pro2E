package ch.fhnw.eit.pro2.gruppe4.model;

import ch.fhnw.eit.pro2.gruppe4.utilities.Calc;

public abstract class ClosedLoop{
	
	private double[][] yt;
	private Controller controller;

	protected void simClosedLoop(double[] zah_s,double[] nen_s,double[] zah_r, double[]nen_r, double Tg) {
		double[] zah; //Z�hler
		double[] nen; //Nenner
		int n = 8*1024;
		
		double fs = (1/Tg)*500;
		
		zah = Calc.diskConv(zah_s,zah_r);
		nen = Calc.diskConv(nen_s,nen_r);
		
		nen = Calc.colonColon(nen,nen.length-zah.length+1,1,nen.length);
		nen = add(nen,zah);
		
		yt = Calc.schrittIfft(zah, nen, fs, n);
		//TODO: figure; plot(t,y);
	}
	
	protected void simClosedLoop(double[] zah_s,double[] nen_s,double[] zah_r, double[]nen_r) {
		double[] zah; //Z�hler
		double[] nen; //Nenner
		int n = 8*1024; 
		
		double fs = 100;//TODO: Wahl abh�ngig von fg
		
		zah = Calc.diskConv(zah_s,zah_r);
		nen = Calc.diskConv(nen_s,nen_r);
		
		nen = Calc.colonColon(nen,nen.length-zah.length+1,1,nen.length);
		nen = add(nen,zah);
		
		yt = Calc.schrittIfft(zah, nen, fs, n);
		//TODO: figure; plot(t,y);
	}
	
	protected double[] add(double[] b, double[] a) {
		double[] res = new double[a.length + b.length];
		//TODO: Vereinfachen mit Referenzen
		if (b.length>a.length) {
			res = b.clone();
			for (int i = 0; i > (res.length -a.length); i++) {
				res[i] = b[b.length-1-i]+a[i];
			}
		}
		else
			if (b.length<a.length) {
				res = a.clone();
				for (int i = 0; i > (res.length -b.length); i++) {
					res[i] = b[b.length-1-i]+a[i];
				}
			}
		return res;
	}
	
	
	/**
	 * Nimmt die InputValues entgegen und gibt sie den Unterklassen weiter
	 * @param input (int ControllerCalculationTyp, int ControllerTyp, Path path, double Tp, double "phiR", double/int overshoot)
	 * 
	 */
					
	public void setData(Object[] input) {
		int calculationTyp = (int)input[0];
		int controllerTyp = (int)input[1];
		Path path = (Path)input[2];
		double Tp = (double)input[3];
		
		controller.setData(controllerTyp, calculationTyp, path);
		controller.setTp(Tp);
	}
	
	/**
	 * Gibt Controller zur�ck
	 * @return controller
	 */
	public Controller getControllerTyp() {
		controller.getControllerTyp();
		return controller;
	}
	/**
	 * Gibt Path zur�ck
	 * @return path
	 */
	public Path getPath() {
		path.getInputValues();
		return path;
	}
	/**
	 * Gibt Schrittantwort des geschlossenen Regelkreises zur�ck
	 * @return
	 */
	public double[][] getStepResponse() {
		return yt;
	}


}
