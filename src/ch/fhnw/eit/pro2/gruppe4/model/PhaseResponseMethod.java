package ch.fhnw.eit.pro2.gruppe4.model;

import org.apache.commons.math3.complex.Complex;

import ch.fhnw.eit.pro2.gruppe4.utilities.Calc;

public class PhaseResponseMethod extends Controller {

	public static final int OVERSHOOT0 = 0, OVERSHOOT4_6 = 1,
			OVERSHOOT16_3 = 2, OVERSHOOT23_3 = 3;
	private int overShoot = 3;
	private double phiU = -2.3561945;
	private double alpha;
	private int omegaControllerIndex;
	private double[] phiO;
	private Complex[] Ho;
	Complex[] Hs;
	double[] phiS;
	int pointNumber;
	double[] omega;
	double[] phiR;

	public PhaseResponseMethod() {
		CALCULATIONTYP = 0;
	}

	/**
	 * 
	 * 
	 * 
	 * Setzt die Input-Wert für die Berechnung.
	 * 
	 * 
	 * 
	 * Löst calculate() aus.
	 * 
	 * @param controllerTyp
	 * @param path
	 * @throws ControllerException
	 */

	public void setData(int controllerTyp, Path path) {
		System.out.println("setData von Phasengangmethode ausgelöst");
		this.controllerTyp = controllerTyp;
		this.path = path;
		calculatePhaseMargin();
		calculate();
	}
	/**
	 * 
	 * @param overShoot
	 */

	public void setOverShoot(int overShoot) {
		this.overShoot = overShoot;
		calculatePhiU();
		calculate();
	}

	/**
	 * 
	 * @param alpha
	 */
	// Phasenrand manuell ändern
	public void setPhaseMargin(double alpha) {
		System.out.println("set phaseMargin con Phasengang ausgelöst");
		this.alpha = alpha;
		// Berechnung Tnk und Tvk
		calculateTnkTvk();
		// Berechnung Krk
		calculateKrk();
		// Umrechnung Reglerkonform
		calculatecontrollerConf();
		// UTF setzen
		setUTF();
	}

	/**
	 * 
	 * @param Krk
	 */
	public void setKrk(double Krk) {
		this.Krk = Krk;
		calculatecontrollerConf();
		setUTF();
	}

	/**
	 * Setzt den Input-Wert für Tp. Löst calculate() aus.
	 * 
	 * @param path
	 */
	public void setTp(double Tp) {
		this.Tp = Tp;
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
				Hs[i] = Hs[i].multiply(new Complex(1).divide(new Complex(1,
						Ts[n] * omega[i])));

				phiS[i] = phiS[i] - Math.atan(omega[i] * Ts[n]);
			}
		}
		// Berechnung Tnk und Tvk
		calculateTnkTvk();
		// Berechnung Krk
		calculateKrk();
		// Umrechnung Reglerkonform
		calculatecontrollerConf();
		// UTF setzen
		setUTF();
	}

	private void calculateTnkTvk() {
		// Bestimmung der Frequenz im Phasenrand alpha
		omegaControllerIndex = Calc.diskFind(phiS, alpha);
		double omegaController = omega[omegaControllerIndex];

		// Reglerspezifische Berechnungen
		Complex[] Hr = new Complex[pointNumber];
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
				phiR[i] = Math.atan(omega[i] * Tnk) - Math.PI / 2;
			}
			break;
		case 3:

			// Steigung Strecke
			double dPhiS = Calc.diskDiff(omega, phiS, omegaControllerIndex);
			double beta_u = Math.pow(10, -12);
			double beta_o = 1;
			double beta;
			double dPhiR,
			dPhiO;

			for (int iteration = 0; iteration < 20; iteration++) {
				beta = (beta_o - beta_u) / 2 + beta_u;
				Tvk = 1 / (omegaController / beta);
				Tnk = 1 / (omegaController * beta);

				for (int i = 0; i < pointNumber; i++) {
					Hr[i] = new Complex(1, omega[i] * Tnk).multiply(
							new Complex(1, omega[i] * Tvk)).divide(
							new Complex(0, omega[i] * Tnk));
					Ho[i] = Hs[i].multiply(Hr[i]);

					phiR[i] = Math.atan(omega[i] * Tnk)
							+ Math.atan(omega[i] * Tvk) - Math.PI / 2;
				}

				dPhiR = Calc.diskDiff(omega, phiR, omegaControllerIndex);
				dPhiO = dPhiS + dPhiR;

				if (dPhiO * omegaController < -0.5) {
					beta_u = beta;
				} else {
					beta_o = beta;
				}
			}
			break;
		default:
			// TODO: Controller Exception erstellen
			break;
		}

		// TODO: TP!
		Tp = Tvk / 10;

	}

	private void calculateKrk() {
		// Krk berechnen
		// Phasengang des offenen Regelkreises berechnen
		for (int i = 0; i < phiO.length; i++) {
			phiO[i] = phiR[i] + phiS[i];
		}
		// Bestimmung omegaD für die gewünschte Überschwingung
		int omegaDIndex;
		if (phiU == -2.3561945) {
			omegaDIndex = omegaControllerIndex;
		} else {
			omegaDIndex = Calc.diskFind(phiO, phiU);
		}

		// Berechnung von Krk
		Krk = 1 / Ho[omegaDIndex].abs();
	}

	private void setUTF() {
		utf.setUTFPoly(Calc.utfController(controllerTyp, Krk, Tnk, Tvk, Tp));
	}

	private void calculatecontrollerConf() {
		double[] controllerConf = Calc.controllerConform(Krk, Tnk, Tvk, Tp,
				controllerTyp);
		Kr = controllerConf[0];
		Tn = controllerConf[1];
		Tv = controllerConf[2];
	}

	// Phasenrand bestimmen je nach Reglertyp
	private void calculatePhaseMargin() {

		// alpha/Phasenrand bestimmen je nach Reglertyp
		switch (controllerTyp) {
		case 2:
			alpha = -1.5707963;
			break;
		case 3:
			alpha = -2.3561802;
			break;
		default:
			// TODO: Controller Exception erstellen
			break;
		}
	}
/**
 * 
 * @param Ts
 * @return
 */

	// Erstellt die Omegaachse in abhängigkeit von Ts und dessen Länge
	private double[] createOmegaAxis(double[] Ts) {
		// TODO: Verifizieren ob so richtig!!!

		double stop;
		switch (Ts.length) {
		case 4:
			stop = 1 / Ts[2];
			break;
		case 5:
			stop = 1 / Ts[3];
			break;
		case 6:
			stop = 1 / Ts[5];
			break;
		case 7:
			stop = 1 / Ts[6];
			break;
		case 8:
			stop = 1 / Ts[7];
			break;
		default:
			stop = 1 / Ts[1];
			break;
		}
		// int pointNumber = (int) (Math.pow(10, 6));
		// double[] omega = Calc.logspace(-5, 2, pointNumber);
		// return Calc.logspace(-5, stop, (int) (Math.pow(10,6)*stop/2));
		return Calc.logspace(-5, stop, (int) (20000 * stop));
	}

	// phiU gemäss gewünschtem Überschwingen bestimmen
	private void calculatePhiU() {
		switch (overShoot) {
		case 0:
			phiU = -1.8099064; // 0%
			break;
		case 1:
			phiU = -2.0001473; // 4.6%
			break;
		case 2:
			phiU = -2.2427481; // 16.3%
			break;
		default:
			phiU = -2.3561945; // 23.3%
			break;
		}
	}
}