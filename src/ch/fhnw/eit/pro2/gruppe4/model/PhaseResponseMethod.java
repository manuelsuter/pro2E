package ch.fhnw.eit.pro2.gruppe4.model;

import org.apache.commons.math3.complex.Complex;
import ch.fhnw.eit.pro2.gruppe4.utilities.Calc;

public class PhaseResponseMethod extends Controller {
	public static final int OVERSHOOT0 = 0, OVERSHOOT4_6 = 1,
			OVERSHOOT16_3 = 2, OVERSHOOT23_3 = 3;
	public static final int CALCULATIONTYP = 0;


	private int overShoot = 3;

	private double alpha;

	/**
	 * 
	 * Setzt die Input-Wert für die Berechnung.
	 * 
	 * Löst calculate() aus.
	 * 
	 * @param path
	 * @throws ControllerException 
	 */

	public void setData(int controllerTyp, Path path){

		this.controllerTyp = controllerTyp;

		this.path = path;

		setPhaseMargin();

		calculate();

	}

	public void setOverShoot(int overShoot) {

		this.overShoot = overShoot;

		calculate();

	}

	private void setPhaseMargin() {
		// alpha bestimmen je nach Reglertyp
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

	public void setPhaseMargin(double phiR) {

		this.alpha = phiR;

		calculate();

	}

	public void setKrk(double Krk) {

		this.Krk = Krk;

		Calc.controllerConform(Krk, Tnk, Tvk, Tp, controllerTyp);
		setUTF();

	}

	@Override
	protected void calculate() {

		// UTF Strecke aus Strecke holen

		double Ks = path.getUTFZahPoly()[0];

		double[] Ts = path.getUTFNenPoly();

		// X-Achse erstellen

		int pointNumber = (int) (Math.pow(10, 6));

		double[] omega = Calc.logspace(-5, 2, pointNumber);

		// Hs berechnen

		Complex[] Hs = new Complex[pointNumber];
		double[] phiS = new double[pointNumber]; 
		for (int i = 0; i < Hs.length; i++) {
			Hs[i] = new Complex(0);
		}
		for (int i = 0; i < Hs.length; i++) {
			
			for (int n = 0; n < Ts.length; n++) {
				
				Hs[i] = Hs[i].add(new Complex(1, Ts[n] * omega[i]).pow(-1));
				
			}
			phiS[i] = Hs[i].getArgument();
		}

		// Amplitudengang berechnen

		// double[] As = new double[pointNumber];

		// for (int i = 0; i < As.length; i++) {

		// As[i] = Ks;

		// }

		// for (int n = 0; n < Ts.length; n++) {

		// for (int i = 0; i < As.length; i++) {

		// As[i] = As[i] *(1/Math.sqrt(1 + Math.pow(omega[i],2) *
		// Math.pow(Ts[n],2)));

		// }

		// }

		// Phasengang berechnen

		// double[] phiS = new double[pointNumber];

		// for (int i = 0; i < phiS.length; i++) {

		// phiS[i] = 0;

		// }

		// for (int n = 0; n < Ts.length; n++) {

		// for (int i = 0; i < As.length; i++) {

		// phiS[i] = phiS[i] - new Complex(1, omega[i] * Ts[n]).getArgument();

		// phiS[i] = phiS[i] - Math.atan(omega[i] * Ts[n]);

		// }

		// }

		// Überschwingungsdefinition

		double phiU;

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

			phiU = -2.356194; // 23.3%

			break;

		}

		// Bestimmung der Frequenz im Punkt Phasengang = alpha
		int omegaControllerIndex = Calc.diskFind(phiS, alpha);

		double omega_controller = omega[omegaControllerIndex];

		// Reglerspezifische Berechnungen

		Complex[] Hr = new Complex[pointNumber];

		Complex[] Ho = new Complex[pointNumber];

		double[] phiR = new double[pointNumber];

		double[] phiO = new double[pointNumber];

		// double[] Ar = new double[pointNumber];

		// double[] Ao = new double[pointNumber];

		switch (controllerTyp) {

		case 2:

			Tnk = 1 / omega_controller;

			Tvk = 0;

			Tp = 0;

			for (int i = 0; i < pointNumber; i++) {

				Hr[i] = new Complex(0, omega[i] * Tnk).pow(-1).add(1);

				Ho[i] = Hs[i].multiply(Hr[i]);

			}

			// for (int i = 0; i < pointNumber; i++) {

			// phiR[i] = Math.atan(omega[i] * Tnk) - Math.PI/2;

			// phiO[i] = phiS[i] + phiR[i];

			// Ar[i] = 1 * Math.sqrt(1 + 1 /( Math.pow(omega[i], 2) *
			// Math.pow(Tnk, 2) ));

			// Ao[i] = Ar[i] * As[i];

			// }

			break;

		case 3:

			// TODO: Optimierbar nur benötigte auslesen.
			//TODO: for Schleife entfernen.
			/**for (int i = 0; i < pointNumber; i++) {

				phiS[i] = Hs[i].getArgument();

			}
			*/

			// Steigung Strecke

			double dPhiS = Calc.diskDiff(omega, phiS, omegaControllerIndex);

			double beta_u = Math.pow(10, -12);

			double beta_o = 1;

			double beta;

			double dPhiR,
			dPhiO;

			for (int it = 0; it < 20; it++) {

				beta = (beta_o - beta_u) / 2 + beta_u;

				Tvk = 1 / (omega_controller / beta);

				Tnk = 1 / (omega_controller * beta);

				Tp = 0;

				// TODO: Optimierbar nur benötigte berechnen.

				for (int i = 0; i < pointNumber; i++) {

					Hr[i] = new Complex(1, omega[i] * Tnk).multiply(
							new Complex(1, omega[1] * Tvk))

					.divide(new Complex(0, omega[i] * Tvk));

					// Ho[i] = Hs[i].multiply(Hr[i]);

					phiR[i] = Hr[i].getArgument();

				}

				// for (int k = omegaControllerIndex - 1; k < omega_controller +
				// 2; i++) {

				// phiR[k] = Math.atan(omega[k] * Tnk) + Math.atan(omega[k] *
				// Tvk) - Math.PI/2;

				// }

				dPhiR = Calc.diskDiff(omega, phiR, omegaControllerIndex);

				dPhiO = dPhiS + dPhiR;

				double wDPhiO = dPhiO * omega_controller;

				if (wDPhiO < -0.5) {

					beta_u = beta;

				}

				else {

					beta_o = beta;

				}

			}

			// for (int i = 0; i < pointNumber; i++) {

			// Ar[i] = Math.sqrt(1+ Math.pow(omega[i]*Tvk, 2))+Math.sqrt(1 +
			// Math.pow(omega[i], Tnk)) / Math.sqrt(1 + Math.pow(omega[i],
			// Tnk));

			// }

			break;

		default:

			// TODO: Controller Exception erstellen

			break;
		}

		// Bestimmung omega_d anhand Überschwingungsdefinition

		int omegaDIndex;

		if (phiU == -2.3561945) {

			omegaDIndex = omegaControllerIndex;

		}

		else {

			for (int i = 0; i < pointNumber; i++) {

			}

			omegaDIndex = Calc.diskFind(phiO, phiU);

		}

		// Berechnung von Krk

		Krk = 1 / (Hr[omegaDIndex].abs() * Hs[omegaDIndex].abs());

		// Umrechnung Reglerkonform

		Calc.controllerConform(Krk, Tnk, Tvk, Tp, controllerTyp);
		setUTF();
	}
	private void setUTF(){
		utf.setUTFPoly(new double[] {Tvk, Tnk}, new double[] {Tnk});
	}
}
