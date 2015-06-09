/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * Authors: Benjamin Müller
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */
package ch.fhnw.eit.pro2.gruppe4.model;

import org.apache.commons.math3.complex.Complex;

import ch.fhnw.eit.pro2.gruppe4.utilities.Calc;

public class PhaseResponseMethod extends Controller {

	public static final double OVERSHOOT0 = -1.8099064,
			OVERSHOOT4_6 = -2.0001473, OVERSHOOT16_3 = -2.2427481,
			OVERSHOOT23_3 = -2.3561945;
	private double phiU = OVERSHOOT0;
	// Wenn der Phasenrand-Winkel in Abhängigkeit der Regelstrecken-Ordnung
	// geändert werden soll, kann hier der Ordnung entsprechend der
	// Maximal-Winkel für die Berechnung des logspace gewählt werden.
	private static final double[] MINIMUMANGLEPI = new double[] { -3.1, -3.1,
			-3.1, -3.1, -3.1, -3.1, -3.1, -3.1 };
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

	/**
	 * Setzt Calculationtyp.
	 */
	public PhaseResponseMethod() {
		CALCULATIONTYP = 0;
	}

	/**
	 * Setzt die Input-Wert für die Berechnung. Löst calculatePhaseMargin() und
	 * calculate() aus.
	 * 
	 * @param controllerTyp
	 * @param path
	 * @throws ControllerException
	 */
	public void setData(int controllerTyp, Path path)
			throws ControllerException {
		this.controllerTyp = controllerTyp;
		this.path = path;
		Tp = 0.0;
		calculatePhaseMargin();
		calculate();
	}

	/**
	 * Setzt die Input-Wert für die Berechnung. Löst calculatePhaseMargin() und
	 * calculate() aus.
	 * 
	 * @param controllerTyp
	 * @param path
	 * @param Tp
	 * @throws ControllerException
	 */
	public void setData(int controllerTyp, Path path, double Tp)
			throws ControllerException {
		this.controllerTyp = controllerTyp;
		this.path = path;
		this.Tp = Tp;
		calculatePhaseMargin();
		calculate();
	}

	/**
	 * Setzt die Input-Wert für die Berechnung. Löst calculatePhaseMargin() und
	 * calculate() aus.
	 * 
	 * @param controllerTyp
	 * @param path
	 * @param Tp
	 * @throws ControllerException
	 */
	public void setData(int controllerTyp, Path path, double Tp,
			double overShoot, double phaseMargin) throws ControllerException {
		this.controllerTyp = controllerTyp;
		this.path = path;
		this.Tp = Tp;
		this.phaseMargin = phaseMargin;
		this.overShoot = overShoot;
		calculateOverShoot();
		calculate();
	}

	/**
	 * Setzt das Überschwingen.
	 */
	public void setOverShoot(double overShoot) {
		this.overShoot = overShoot;
		calculateOverShoot();
		calculateKrk();
	}

	/**
	 * Setzt den Phasenrand..
	 */
	public void setPhaseMargin(double phaseMargin) throws ControllerException {
		this.phaseMargin = phaseMargin;

		calculateTnkTvk();
	}

	/**
	 * Gibt den Phasenrand zurück.
	 */
	public double getPhaseMargin() {
		return phaseMargin;
	}

	/**
	 * Setzt Krk.
	 * 
	 * @param Krk
	 */
	public void setKrk(double Krk) {
		this.Krk = Krk;
		calculateControllerConf();
		setUTF();
	}

	/**
	 * Setzt den Input-Wert für Tp. Löst calculate() aus.
	 * 
	 * @param path
	 */
	public void setTp(double Tp) {
		this.Tp = Tp;
		calculateControllerConf();
		setUTF();
	}

	/**
	 * Löst createOmegaAxis() aus und berechnet Hs der Regelstrecke. Löst
	 * calculateTnk() aus.
	 */
	protected void calculate() throws ControllerException {
		// UTF Strecke aus Strecke holen
		double Ks = path.getUTFZahPoly()[0];
		double[] Ts = path.getT();

		// Omega-Achse erstellen
		omega = createOmegaAxis(Ts);
		pointNumber = omega.length;

		// Hs und phiS berechnen
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

	/**
	 * Berechnet Tnk und Tvk anhand der Phasengang-Methode. Löst calculateKrk()
	 * aus.
	 * 
	 * @throws ControllerException
	 */
	private void calculateTnkTvk() throws ControllerException {
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
			throw new ControllerException();
		}
		phiO = Calc.ComplexAngleUnwraped(Ho);

		if (Tp == 0.0) {
			Tp = Tvk / 10;
		}

		// Berechnung Krk
		calculateKrk();

	}

	/**
	 * Berechnet Krk anhand der Phasengang-Methode. Löst
	 * calculateControllerConf() und setUTF() aus.
	 */
	private void calculateKrk() {
		// Krk berechnen
		// Phasengang des offenen Regelkreises berechnen
		// Bestimmung omegaD für die gewünschte Überschwingung
		int omegaDIndex;
		if (phiU == -2.3561945 & controllerTyp == Controller.PI) {
			omegaDIndex = omegaControllerIndex;
		} else {
			omegaDIndex = Calc.diskFind(phiO, phiU);
		}

		// Berechnung von Krk
		Krk = 1 / Ho[omegaDIndex].abs();

		// Umrechnung Reglerkonform
		calculateControllerConf();
		// UTF setzen
		setUTF();
	}

	/**
	 * Setzt die Übertragungsfunktion des Reglers.
	 */
	private void setUTF() {
		utf.setUTFPoly(Calc.utfController(controllerTyp, Krk, Tnk, Tvk, Tp));
	}

	/**
	 * Berechnet die reglerkonformen Attribute.
	 */
	private void calculateControllerConf() {
		double[] controllerConf = Calc.controllerConform(Krk, Tnk, Tvk, Tp,
				controllerTyp);
		Kr = controllerConf[0];
		Tn = controllerConf[1];
		Tv = controllerConf[2];
	}

	/**
	 * Setzt den Phasenrand entsprechend des Reglertyps.
	 * 
	 * @throws ControllerException
	 */
	private void calculatePhaseMargin() throws ControllerException {

		// alpha/Phasenrand bestimmen je nach Reglertyp
		switch (controllerTyp) {
		case 2:
			phaseMargin = PHASEMARGINPI;
			break;
		case 3:
			phaseMargin = PHASEMARGINPID;
			break;
		default:
			throw new ControllerException();
		}
	}

	/**
	 * Setzt piU entsprechend dem gewählten Überschwingen.
	 */
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

	/**
	 * Erstellt die Omega-Achse in Abhängigkeit vom Phasengang und des
	 * benötigten Winkelbereichs
	 * 
	 * @param Ts
	 * @return
	 */
	private double[] createOmegaAxis(double[] Ts) {

		int borderMin = 12;
		double minimumAngle;
		double startAngle = -1.3;

		if (controllerTyp == Controller.PI) {
			minimumAngle = MINIMUMANGLEPI[Ts.length - 1];
			startAngle = -0.05;
		} else if (controllerTyp == Controller.PID) {
			minimumAngle = MINIMUMANGLEPID[Ts.length - 1];
			startAngle = -0.75;
		} else {
			minimumAngle = -3.1;
			startAngle = -1.3;
		}

		double phi = -100.0;
		while (phi < startAngle) {
			phi = 0.0;
			borderMin--;
			for (int n = 0; n < Ts.length; n++) {
				phi -= new Complex(1, Math.pow(10, borderMin) * Ts[n])
						.getArgument();
			}
		}

		int borderMax = borderMin;
		while (phi > minimumAngle) {
			phi = 0.0;
			borderMax++;
			for (int n = 0; n < Ts.length; n++) {
				phi -= new Complex(1, Math.pow(10, borderMax) * Ts[n])
						.getArgument();
			}
		}

		return Calc.logspace(borderMin, borderMax,
				(borderMax - borderMin) * 5000);

	}
}