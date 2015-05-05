package ch.fhnw.eit.pro2.gruppe4.model;

import java.lang.invoke.SwitchPoint;

import org.apache.commons.math3.geometry.euclidean.twod.DiskGenerator;

import ch.fhnw.eit.pro2.gruppe4.utilities.Calc;

public class ClosedLoop {
	
	private double[][] yt;
	private Controller controller;
	private Path path;
	private UTF utf = new UTF();
	
	public ClosedLoop(int calculationTyp) {
		switch (calculationTyp) {
		case 0:
			controller = new PhaseResponseMethod();
			break;
			
		case 1:
			controller = new Rosenberg();
			break;
			
		case 2:
			controller = new Oppelt();
			break;	
			
		case 3:
			controller = new ZieglerNichols();
			break;

		case 4:
			controller = new Chien20();
			break;
	
		case 5:
			controller = new ChienApper();
			break;
	
		default:
			//TODO: Exception!
			break;
		}
	}
	
	
	

	/**
	 * Berechnet den geschlossenen Regelkreis.
	 */
	
	protected void calculate() {
		double[] zah_c = controller.getUTFZahPoly();
		double[] nen_c = controller.getUTFNenPoly();
		double[] zah_p = path.getUTFZahPoly();
		double[] nen_p = path.getUTFNenPoly();
		System.out.println(nen_c+"nenc");
		System.out.println(nen_p+"nen_p");
		
		double[] nen = Calc.diskConv(nen_c, nen_p);
		double[] zah = Calc.diskConv(zah_c, zah_p);
		
	
		nen = Calc.colonColon(nen,nen.length-zah.length+1,1,nen.length-1);
		nen = add(nen,zah);
	
		
		utf.setUTFFac(zah, nen);
		
		//TODO: noch optimieren, gibt Bereich an, welcher berechnet wird:
		//double fs = 1.0/(path.getInputValues()[Path.TgPOS]*500); // Muss Potenz von 2 sein.
		double fs = 128;
		int n = 8*1024;
		yt = Calc.schrittIfft(zah, nen, fs, n);
		System.out.println(yt[1][1]+"yt");
	}
		
	/**
	 * Addiert zwei Arrays zusammen.
	 * @param b
	 * @param a
	 * @return
	 */
	
	protected double[] add(double[] b, double[] a) {
		double[] res = new double[a.length + b.length];
		//TODO: Vereinfachen mit Referenzen
		if (b.length > a.length) {
			res = b.clone();
			for (int i = 0; i > (res.length -a.length); i++) {
				res[i] = b[b.length-1-i]+a[i];
			}
		}
		else
			if (b.length < a.length) {
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
					
	public void setData(int controllerTyp, Path path, double Tp) {
		this.path = path;
	
		controller.setData(controllerTyp, path);
		controller.setTp(Tp);
		
		calculate();
	}
	
	/**
	 * Gibt Controller zur�ck
	 * @return controller
	 */
	public Controller getController() {
		return controller;
	}
	/**
	 * Gibt Path zur�ck.
	 * @return path
	 */
	public Path getPath() {
		return path;
	}
	/**
	 * Gibt Schrittantwort des geschlossenen Regelkreises zur�ck.
	 * @return
	 */
	public double[][] getStepResponse() {
		return yt;
	}

	
	/**
	 * Gibt UTF des geschlossenen Regelkreises zur�ck.
	 * @return
	 */
	public UTF getUTF() {
		return utf;
	}

}
