package ch.fhnw.eit.pro2.gruppe4.model;

import org.apache.commons.math3.complex.Complex;

import ch.fhnw.eit.pro2.gruppe4.utilities.Calc;

public class PhaseResponseMethod extends Controller {

	public static final double OVERSHOOT0 = -1.8099064,
			OVERSHOOT4_6 = -2.0001473, OVERSHOOT16_3 = -2.2427481,
			OVERSHOOT23_3 = -2.3561945;
	private double phiU = OVERSHOOT0;
	// TODO:Winkel richtig bestimmen PI letzter stimmt schon
	private static final double[] MINIMUMANGLEPI = new double[] { -3.1, -3.1,
			-3.1, -3.1, -3.1, -3.1, -3.1, -5.1 };
	private static final double[] MINIMUMANGLEPID = new double[] { -3.1, -3.1,
			-3.1, -3.1, -3.1, -3.1, -3.1, -3.1 };
	public static final double PHASEMARGINPI = -1.5707963,
			PHASEMARGINPID = -2.3561802;
	private double phaseMargin;

	private int omegaControllerIndex;
	private double[] phiO, phiS, phiR;
	private Complex[] Ho, Hs, Hr;
	int pointNumber;
	double[] omega;

	public PhaseResponseMethod() {
		CALCULATIONTYP = 0;
	}

	/**
	 * Setzt die Input-Wert f�r die Berechnung. L�st calculate() aus.
	 * 
	 * @param path
	 * @throws ControllerException
	 */
	public void setData(int controllerTyp, Path path) {
		this.controllerTyp = controllerTyp;
		this.path = path;
		Tp = 0.0;
		calculatePhaseMargin();
		calculate();
	}

	/**
	 * Setzt die Input-Wert f�r die Berechnung. L�st calculate() aus.
	 * 
	 * @param path
	 * @throws ControllerException
	 */
	public void setData(int controllerTyp, Path path, double Tp) {
		this.controllerTyp = controllerTyp;
		this.path = path;
		this.Tp = Tp;
		calculatePhaseMargin();
		calculate();
	}

	public void setData(int controllerTyp, Path path, double Tp,
			double overShoot, double phaseMargin) {
		this.controllerTyp = controllerTyp;
		this.path = path;
		this.Tp = Tp;
		this.phaseMargin = phaseMargin;
		this.overShoot = overShoot;
		calculateOverShoot();
		calculate();
	}

	public void setOverShoot(double overShoot) {
		this.overShoot = overShoot;
		calculateOverShoot();
		calculateKrk();
	}

	// Phasenrand manuell �ndern
	public void setPhaseMargin(double phaseMargin) {
		this.phaseMargin = phaseMargin;
		// Berechnung Tnk und Tvk
		calculateTnkTvk();
	}

	public double getPhaseMargin() {
		return phaseMargin;
	}

	public void setKrk(double Krk) {
		this.Krk = Krk;
		calculatecontrollerConf();
		setUTF();
	}

	/**
	 * Setzt den Input-Wert f�r Tp. L�st calculate() aus.
	 * 
	 * @param path
	 */
	public void setTp(double Tp) {
		this.Tp = Tp;
		calculatecontrollerConf();
		setUTF();
	}

	protected void calculate() {
		// UTF Strecke aus Strecke holen
		double Ks = path.getUTFZahPoly()[0];
		double[] Ts = path.getT();

		// Omega-Achse erstellen
		omega = createOmegaAxis(Ts);
		pointNumber = omega.length;

		// Hs und phiS berechnen
		// TODO: SInnvoller Hs aus UTF von Path berechnen??
		Hs = new Complex[pointNumber];
		for (int i = 0; i < pointNumber; i++) {
			Hs[i] = new Complex(Ks);
		}
		for (int i = 0; i < Hs.length; i++) {
			for (int n = 0; n < Ts.length; n++) {
				Hs[i] = Hs[i].multiply(new Complex(1).divide(new Complex(1,
						Ts[n] * omega[i])));
			}
		}
		phiS = Calc.ComplexAngleUnwraped(Hs);

		// Berechnung Tnk und Tvk
		calculateTnkTvk();
	}

	private void calculateTnkTvk() {
		// Bestimmung der Frequenz im Phasenrand alpha
		omegaControllerIndex = Calc.diskFind(phiS, phaseMargin);
		double omegaController = omega[omegaControllerIndex];

		// Reglerspezifische Berechnungen
		Hr = new Complex[pointNumber];
		Ho = new Complex[pointNumber];

		switch (controllerTyp) {
		case 2:
			Tnk = 1 / omegaController;
			Tvk = 0;
			Tp = 0;

			for (int i = 0; i < pointNumber; i++) {
				Hr[i] = new Complex(0, omega[i] * Tnk).pow(-1).add(1);
				Ho[i] = Hs[i].multiply(Hr[i]);
			}
			phiR = Calc.ComplexAngleUnwraped(Hr);
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
				}
				phiR = Calc.ComplexAngleUnwraped(Hr);

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
		phiO = Calc.ComplexAngleUnwraped(Ho);

		if (Tp == 0.0) {
			Tp = Tvk / 10;
		}

		// Berechnung Krk
		calculateKrk();

	}

	private void calculateKrk() {
		// Krk berechnen
		// Phasengang des offenen Regelkreises berechnen
		// Bestimmung omegaD f�r die gew�nschte �berschwingung
		int omegaDIndex;
		if (phiU == -2.3561945 & controllerTyp == Controller.PI) {
			omegaDIndex = omegaControllerIndex;
		} else {
			omegaDIndex = Calc.diskFind(phiO, phiU);
		}

		// Berechnung von Krk
		Krk = 1 / Ho[omegaDIndex].abs();

		// Umrechnung Reglerkonform
		calculatecontrollerConf();
		// UTF setzen
		setUTF();
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

	private void calculateOverShoot() {
		if (overShoot <= 1)
			phiU = OVERSHOOT0;
		else if (overShoot <= 4.6)
			phiU = OVERSHOOT4_6;
		else if (overShoot <= 16)
			phiU = OVERSHOOT16_3;
		else if (overShoot <= 23)
			phiU = OVERSHOOT23_3;
	}

	// Erstellt die Omega-Achse in Abh�ngigkeit vom Phasengang und des
	// ben�tigten Winkelbereichs
	private double[] createOmegaAxis(double[] Ts) {

		int borderMax = -5;
		int borderMin = -6;
		double minimumAngle;

		if (controllerTyp == Controller.PI)
			minimumAngle = MINIMUMANGLEPI[Ts.length - 1];
		else if (controllerTyp == Controller.PID)
			minimumAngle = MINIMUMANGLEPID[Ts.length - 1];
		else
			minimumAngle = -3.1;

		double phi = 0;
		do {
			for (int n = 0; n < Ts.length; n++) {
				phi -= new Complex(1, Math.pow(10, borderMax) * Ts[n])
						.getArgument();
			}
			borderMax++;

		} while (phi > minimumAngle);

		// TODO: eventuell minimumAngel durch border Max ersetzen
		return Calc
				.logspace(borderMin, borderMax, (int) (-3226 * minimumAngle));

	}
}