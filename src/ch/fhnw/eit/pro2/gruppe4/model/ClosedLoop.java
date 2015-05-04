package ch.fhnw.eit.pro2.gruppe4.model;

import ch.fhnw.eit.pro2.gruppe4.utilities.Calc;

public abstract class ClosedLoop {

	protected void simClosedLoop(double[] zah_s,double[] nen_s,double[] zah_r, double[]nen_r, double Tg) {
		double[] zah; //Zähler
		double[] nen; //Nenner
		int n = 8*1024;
		double[][] yt; 
		
		double fs = (1/Tg)*500;
		
		zah = Calc.diskConv(zah_s,zah_r);
		nen = Calc.diskConv(nen_s,nen_r);
		
		nen = Calc.colonColon(nen,nen.length-zah.length+1,1,nen.length);
		nen = add(nen,zah);
		
		yt = Calc.schrittIfft(zah, nen, fs, n);
		//TODO: figure; plot(t,y);
	}
	
	protected void simClosedLoop(double[] zah_s,double[] nen_s,double[] zah_r, double[]nen_r) {
		double[] zah; //Zähler
		double[] nen; //Nenner
		int n = 8*1024;
		double[][] yt; 
		
		double fs = 100;//TODO: Wahl abhängig von fg
		
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

}
