package ch.fhnw.eit.pro2.gruppe4.model;

import org.apache.commons.math3.complex.Complex;

import ch.fhnw.eit.pro2.gruppe4.utilities.Calc;


public class PhaseResponseMethod extends Controller {

	public static final double OVERSHOOT0 = -1.8099064, OVERSHOOT4_6 = -2.0001473, OVERSHOOT16_3 = -2.2427481, OVERSHOOT23_3 = -2.3561945;
	private double phiU = OVERSHOOT0;
	public static final double PHASEMARGINPI = -1.5707963, PHASEMARGINPID = -2.3561802;
	private double phaseMargin;
	
	private int omegaControllerIndex;
	private double[] phiO, phiS, phiR;
	private Complex[] Ho, Hs, Hr;
	int pointNumber;
	double[] omega;

	
	public PhaseResponseMethod(){	
		CALCULATIONTYP = 0;
	}

	/**
	 * Setzt die Input-Wert für die Berechnung.
	 * Löst calculate() aus.
	 * @param path
	 * @throws ControllerException 
	 */
	public void setData(int controllerTyp, Path path){
		System.out.println("setData ohne Phasenrand von Phasengangmethode ausgelöst");
		this.controllerTyp = controllerTyp;
		this.path = path;
		Tp = 0.0;
		calculatePhaseMargin();
		calculate();
	}
	
	/**
	 * Setzt die Input-Wert für die Berechnung.
	 * Löst calculate() aus.
	 * @param path
	 * @throws ControllerException 
	 */
	public void setData(int controllerTyp, Path path, double Tp){
		System.out.println("setData ohne Phasenrand mit Tp von Phasengangmethode ausgelöst");
		this.controllerTyp = controllerTyp;
		this.path = path;
		this.Tp = Tp;
		calculatePhaseMargin();
		calculate();
	}
	
	public void setData(int controllerTyp, Path path, double Tp, double overShoot, double phaseMargin){
		System.out.println("setData mit Phasenrand und Tp und Overshoot von Phasengangmethode ausgelöst");
		this.controllerTyp = controllerTyp;
		this.path = path;
		this.Tp = Tp;
		this.phaseMargin = phaseMargin;
		this.overShoot = overShoot;
		calculateOverShoot();
		calculate();
	}
	

	public void setOverShoot(double overShoot) {
		System.out.println("setOverShoot(overShootValue) von Phasengangmethode ausgelöst");
		this.overShoot = overShoot;
		calculateOverShoot();
		calculateKrk();
	}

	//Phasenrand manuell ändern
	public void setPhaseMargin(double phaseMargin) {
		System.out.println("set phaseMargin(phaseMargin) von Phasengang ausgelöst");
		this.phaseMargin = phaseMargin;
		//Berechnung Tnk und Tvk
		calculateTnkTvk();
	}
	
	public double getPhaseMargin(){
		return phaseMargin;
	}
	
	public void setKrk(double Krk) {
		System.out.println("setKrk(Krk) von Phasengangmethode ausgelöst");
		this.Krk = Krk;
		calculatecontrollerConf();
		setUTF();
	}
	
	/** 
	 * Setzt den Input-Wert für Tp.
	 * Löst calculate() aus.
	 * @param path
	 */
	public void setTp(double Tp){
		System.out.println("setTp(Tp) von Phasengangmethode ausgelöst");
		this.Tp = Tp;
		calculatecontrollerConf();
		setUTF();
	}		

	protected void calculate() {
		System.out.println("Phasengangmethode calculate() ausgelöst");
		// UTF Strecke aus Strecke holen 
		double Ks = path.getUTFZahPoly()[0];
		double[] Ts = path.getT();
		
		
		// Omega-Achse erstellen
		omega = createOmegaAxis(Ts);
		pointNumber = omega.length;
		
		
		// Hs und phiS berechnen
		Hs = new Complex[pointNumber];
		phiS = new double[pointNumber];
		for (int i = 0; i < pointNumber; i++) {
			Hs[i] = new Complex(Ks);
			phiS[i] = 0;
		}
		for (int i = 0; i < Hs.length; i++) {
			for (int n = 0; n < Ts.length; n++) {
				Hs[i] = Hs[i].multiply(new Complex(1).divide(new Complex(1, Ts[n] * omega[i])));
				
				phiS[i] = phiS[i] - Math.atan(omega[i] * Ts[n]);
			}
		}
		//Berechnung Tnk und Tvk
		calculateTnkTvk();
	}
		
	private void calculateTnkTvk(){
		System.out.println("calculateTnkTvk() von Phasengangmethode ausgelöst");
		// Bestimmung der Frequenz im Phasenrand alpha
		omegaControllerIndex = Calc.diskFind(phiS, phaseMargin);
		double omegaController = omega[omegaControllerIndex];
		
		
		// Reglerspezifische Berechnungen
		Hr = new Complex[pointNumber];
		Ho = new Complex[pointNumber];
		phiR = new double[pointNumber];
		phiO = new double[pointNumber];
		
		switch (controllerTyp) {
		case 2:
			System.out.println("Berechnung PI");
			Tnk = 1 / omegaController;
			Tvk = 0;
			Tp = 0;

			for (int i = 0; i < pointNumber; i++) {
				Hr[i] = new Complex(0, omega[i] * Tnk).pow(-1).add(1);
				Ho[i] = Hs[i].multiply(Hr[i]);
				phiR[i] = Math.atan(omega[i] * Tnk) - Math.PI/2;
			}
			break;
		case 3:
			// Steigung Strecke
			double dPhiS = Calc.diskDiff(omega, phiS, omegaControllerIndex);
			double beta_u = Math.pow(10, -12);
			double beta_o = 1;
			double beta;
			double dPhiR, dPhiO;

			for (int iteration = 0; iteration < 20; iteration++) {
				beta = (beta_o - beta_u) / 2 + beta_u;
				Tvk = 1 / (omegaController / beta);
				Tnk = 1 / (omegaController * beta);

				for (int i = 0; i < pointNumber; i++) {
					Hr[i] = new Complex(1, omega[i] * Tnk).multiply(
							new Complex(1, omega[i] * Tvk))
							.divide(new Complex(0, omega[i] * Tnk));
					Ho[i] = Hs[i].multiply(Hr[i]);

					phiR[i] = Math.atan(omega[i] * Tnk) + Math.atan(omega[i] *Tvk) - Math.PI/2;
				}

				dPhiR = Calc.diskDiff(omega, phiR, omegaControllerIndex);
				dPhiO = dPhiS + dPhiR;

				if (dPhiO * omegaController < -0.5) {
					beta_u = beta;
				}
				else {
					beta_o = beta;
				}
			}
			break;
		default:
			// TODO: Controller Exception erstellen
			break;
		}	

		//TODO: TP!
		if (Tp == 0.0){
			Tp = Tvk/10;
		}
		
		//Berechnung Krk
		calculateKrk();
		
	}
	
	private void calculateKrk(){
		//Krk berechnen
		//Phasengang des offenen Regelkreises berechnen
	for (int i = 0; i < phiO.length; i++) {
		phiO[i] = phiR[i] + phiS[i];
	}
		// Bestimmung omegaD für die gewünschte Überschwingung
		int omegaDIndex;
		if (phiU == -2.3561945) {
			omegaDIndex = omegaControllerIndex;
		}
		else {
			omegaDIndex = Calc.diskFind(phiO, phiU);
		}

			// Berechnung von Krk
		Krk = 1 / Ho[omegaDIndex].abs();
		
		// Umrechnung Reglerkonform
		calculatecontrollerConf();
		//UTF setzen
		setUTF();
	}

	private void setUTF(){
		utf.setUTFPoly(Calc.utfController(controllerTyp, Krk, Tnk, Tvk, Tp));
	}
	private void calculatecontrollerConf(){
		double[] controllerConf = Calc.controllerConform(Krk, Tnk, Tvk, Tp, controllerTyp);
		Kr = controllerConf[0];
		Tn = controllerConf[1];
		Tv = controllerConf[2];
	}
	
	
	//Phasenrand bestimmen je nach Reglertyp
	private void calculatePhaseMargin() {

		// alpha/Phasenrand bestimmen je nach Reglertyp
		switch (controllerTyp) {
		case 2:
			phaseMargin = PHASEMARGINPI;
			break;
		case 3:
			phaseMargin = PHASEMARGINPID;
			break;
		default:
			// TODO: Controller Exception erstellen
			break;
		}
	}
	
	private void calculateOverShoot(){
		if (overShoot <= 4) {
		phiU = OVERSHOOT0;
		}else if (overShoot <= 16) {
		phiU = OVERSHOOT16_3;
		}
		else if (overShoot <= 23) {
		phiU = OVERSHOOT23_3;
		}
		
	}
	
	
	//Erstellt die Omegaachse in abhängigkeit von Ts und dessen Länge
	private double[] createOmegaAxis(double[] Ts){
		//TODO: Verifizieren ob so richtig!!!
		
				double stop;
				switch (Ts.length) {
				case 4:
					stop = 1/Ts[2];
					break;
				case 5:
					stop = 1/Ts[3];
					break;
				case 6:
					stop = 1/Ts[5];
					break;
				case 7:
					stop = 1/Ts[6];
					break;
				case 8:
					stop = 1/Ts[7];
					break;
				default:
					stop = 1/Ts[1];
					break;
				}
				//int pointNumber = (int) (Math.pow(10, 6));
				//double[] omega = Calc.logspace(-5, 2, pointNumber);
				//return Calc.logspace(-5, stop, (int) (Math.pow(10,6)*stop/2));
				return Calc.logspace(-5, stop, (int) (20000*stop));
	}
}