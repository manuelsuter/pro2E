package ch.fhnw.eit.pro2.gruppe4.model;



import org.apache.commons.math3.complex.Complex;

import ch.fhnw.eit.pro2.gruppe4.utilities.Calc;



public class PhaseResponseMethod extends Controller {
	public static final int OVERSHOOT0 = 0, OVERSHOOT4_6 = 1, OVERSHOOT16_3 = 2, OVERSHOOT23_3 = 3;
	private int overShoot = 3;
	private double alpha;
	
	public PhaseResponseMethod(){	
		CALCULATIONTYP = 0;
	}



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
		double[] Ts = path.getT();
		
		// Omega-Achse erstellen



		int pointNumber = (int) (Math.pow(10, 6));



		double[] omega = Calc.logspace(-5, 2, pointNumber);



		

		// Hs und phiS berechnen



		Complex[] Hs = new Complex[pointNumber];

		double[] phiS = new double[pointNumber]; 

		for (int i = 0; i < Hs.length; i++) {

			Hs[i] = new Complex(Ks);

			phiS[i] = 0;

		}

		

		for (int i = 0; i < Hs.length; i++) {

			

			for (int n = 0; n < Ts.length; n++) {

				

				Hs[i] = Hs[i].multiply(new Complex(1).divide(new Complex(1, Ts[n] * omega[i])));

				phiS[i] = phiS[i] - Math.atan(omega[i] * Ts[n]);

				

			}

			

			

		}



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



			phiU = -2.3561945; // 23.3%



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



		switch (controllerTyp) {



		case 2:

			

			System.out.println("Berechnung PI");



			Tnk = 1 / omega_controller;



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

			

			Tp = 0;



			for (int iteration = 0; iteration < 20; iteration++) {



				beta = (beta_o - beta_u) / 2 + beta_u;



				Tvk = 1 / (omega_controller / beta);



				Tnk = 1 / (omega_controller * beta);



				// TODO: Optimierbar nur benötigte berechnen.



				for (int i = 0; i < pointNumber; i++) {



					Hr[i] = new Complex(1, omega[i] * Tnk).multiply(

							new Complex(1, omega[i] * Tvk))

							.divide(new Complex(0, omega[i] * Tnk));

					

					

					phiR[i] = Math.atan(omega[i] * Tnk) + Math.atan(omega[i] *Tvk) - Math.PI/2;



				}



				dPhiR = Calc.diskDiff(omega, phiR, omegaControllerIndex);



				dPhiO = dPhiS + dPhiR;

				

				if (dPhiO * omega_controller < -0.5) {



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



		// Bestimmung omega_d anhand Überschwingungsdefinition



		int omegaDIndex;



		if (phiU == -2.3561945) {



			omegaDIndex = omegaControllerIndex;

			

		}



		else {

			

			for (int i = 0; i < phiO.length; i++) {

				phiO[i] = phiR[i] + phiS[i];

			}

			

			omegaDIndex = Calc.diskFind(phiO, phiU);



		}



		// Berechnung von Krk



		Krk = 1 / (Hr[omegaDIndex].abs() * Hs[omegaDIndex].abs());

		

		



		// Umrechnung Reglerkonform
		//TODO: TP!
		Tp = Tv/10;

		double[] controllerConf = Calc.controllerConform(Krk, Tnk, Tvk, Tp, controllerTyp);

		Kr = controllerConf[0];

		Tn = controllerConf[1];

		Tv = controllerConf[2];

		setUTF();
	}

	private void setUTF(){

		

		utf.setUTFPoly(Calc.utfController(controllerTyp, Krk, Tnk, Tvk, Tp));

	}

}

