package ch.fhnw.eit.pro2.gruppe4.model;

import ch.fhnw.eit.pro2.gruppe4.utilities.Calc;

public class ClosedLoop {

	private double[][] yt;
	private Controller controller;
	private Path path;
	private UTF utf = new UTF();
	private double fs;
	private int pointnumber;
	private boolean fsNotGiven = false;

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
			// TODO: Exception!
			break;
		}
	}

	protected void calculate() {
		calculateStepResponse();
		switch (controller.CALCULATIONTYP) {
		case 0:
			overShootOptimization();
			break;
		}
	}

	/**
	 * Berechnet den geschlossenen Regelkreis.
	 */

	protected void calculateStepResponse() {
		// TODO: Optimieren
		double[] zah_c = controller.getUTFZahPoly();
		double[] nen_c = controller.getUTFNenPoly();
		double[] zah_p = path.getUTFZahPoly();
		double[] nen_p = path.getUTFNenPoly();

		double[] nen = Calc.diskConv(nen_c, nen_p);
		double[] zah = Calc.diskConv(zah_c, zah_p);

		nen = Calc.addArrayReverse(nen, zah);

		utf.setUTFPoly(zah, nen);

		if (fsNotGiven) {
			// TODO: noch optimieren, gibt Bereich an, welcher berechnet wird:
			// double fs = 1.0/(path.getInputValues()[Path.TgPOS]*500); // Muss
			// Potenz von 2 sein.
			// n evtl. höhr / tiefer setzen.
			double x = 1e-5;
			while (Calc.polyval(zah, x) / Calc.polyval(nen, x) > 0.01) {
				x = 1.5 * x;
			}
			fs = x * 2;

			// TODO. Man müsste irgendwie schauen dass alle das selbe Fs
			// verwenden sonst floppt es in der Darstellung

			// Version gemäss Gut
			// double wsc = 1.0;
			// double[] A = new double[nen.length];
			// for (int i = 0; i < nen.length; i++) {
			// A[i] = nen[i] / Math.pow(wsc, A.length - i - 1);
			// }
			// double[] B = new double[zah.length];
			// for (int i = 0; i < B.length; i++) {
			// B[i] = zah[i] / Math.pow(wsc, B.length - i - 1);
			// }
			//
			// Complex[] rootsNen = Calc.roots(A);
			// double[] rootsNenImag = new double[rootsNen.length];
			// double[] rootsNenReal = new double[rootsNen.length];
			// for (int i = 0; i < rootsNen.length; i++) {
			// rootsNenImag[i] = rootsNen[i].getImaginary();
			// rootsNenReal[i] = rootsNen[i].getReal();
			// }
			// double fs = 500.0 *rootsNenImag[Calc.diskMax(rootsNenImag)] /
			// (2.0 * Math.PI);
			// double simgmaMax = Calc.diskMax(rootsNenReal);
			// double N = fs * Math.log(0.005) / simgmaMax;
			// N = Math.ceil(Math.log(N)/Math.log(2));
			// N = Math.pow(2, N);
			// int n = (int) N;

			// Alte Version
			// double fs = 1 / path.getInputValues()[Path.TgPOS] * 100;

			// je grösser fs desto kleiner der Bereich

			// int n = 512;
		}
		pointnumber = 512;

		yt = Calc.schrittIfft(zah, nen, fs, pointnumber);
	}

	/**
	 * Nimmt die InputValues entgegen und gibt sie den Unterklassen weiter
	 * 
	 * @param input
	 *            (int ControllerCalculationTyp, int ControllerTyp, Path path,
	 *            double Tp, double "phiR", double/int overshoot)
	 * @throws ControllerException
	 * 
	 */

	public void setData(int controllerTyp, Path path)
			throws ControllerException {
		this.path = path;
		controller.setData(controllerTyp, path);
		fsNotGiven = true;
		calculate();
		fsNotGiven = false;
	}

	/**
	 * Nimmt die InputValues entgegen und gibt sie den Unterklassen weiter
	 * 
	 * @param input
	 *            (int ControllerCalculationTyp, int ControllerTyp, Path path,
	 *            double Tp, double "phiR", double/int overshoot)
	 * @throws ControllerException
	 * 
	 */

	public void setData(int controllerTyp, Path path, double fs)
			throws ControllerException {
		this.fs = fs;
		this.path = path;
		controller.setData(controllerTyp, path);
		calculate();
	}

	/**
	 * Nimmt die InputValues entgegen und gibt sie den Unterklassen weiter
	 * 
	 * @param input
	 *            (int ControllerCalculationTyp, int ControllerTyp, Path path,
	 *            double Tp, double "phiR", double/int overshoot)
	 * @throws ControllerException
	 * 
	 */

	public void setData(int controllerTyp, Path path, double Tp,
			double overShoot, double phaseMargin) throws ControllerException {
		this.path = path;
		controller.setData(controllerTyp, path, Tp, overShoot, phaseMargin);
		fsNotGiven = true;
		calculate();
		fsNotGiven = false;
	}

	public void setData(int controllerTyp, Path path, double Tp,
			double overShoot, double phaseMargin, double fs)
			throws ControllerException {
		this.fs = fs;
		this.path = path;
		controller.setData(controllerTyp, path, Tp, overShoot, phaseMargin);
		calculate();
	}

	public void setOverShoot(double overShootValue) {
		controller.setOverShoot(overShootValue);
		calculate();
	}

	public void setTp(double Tp) {
		controller.setTp(Tp);
		calculate();
	}

	public void setPhaseMargin(double phaseMargin) {
		controller.setPhaseMargin(phaseMargin);
		calculate();
	}

	/**
	 * Gibt Controller zurück
	 * 
	 * @return controller
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 * Gibt Path zurück.
	 * 
	 * @return path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Gibt Schrittantwort des geschlossenen Regelkreises zurück.
	 * 
	 * @return
	 */
	public double[][] getStepResponse() {
		return yt;
	}

	/**
	 * Gibt UTF des geschlossenen Regelkreises zurück.
	 * 
	 * @return
	 */
	public UTF getUTF() {
		return utf;
	}

	public double getFs() {
		return fs;
	}
	
	private void overShootOptimization() {
		double max = yt[0][Calc.max(yt[0])];
		PhaseResponseMethod phaseResponseMethod = (PhaseResponseMethod) controller;
		double Krk = phaseResponseMethod.getControllerValues()[PhaseResponseMethod.KrkPOS];
		
		//Grobskalierung mit dem Faktor 1.15
		if (max - 0.1 > controller.overShoot / 100.0 + 1.0) {
			while (max > controller.overShoot / 100.0 + 1.0) {
				Krk = phaseResponseMethod.getControllerValues()[PhaseResponseMethod.KrkPOS];
				phaseResponseMethod.setKrk(Krk / 1.15);
				calculateStepResponse();
				max = yt[0][Calc.max(yt[0])];
			}
		} else {
			while (max + 0.1 < controller.overShoot / 100.0 + 1.0 & Krk < 1000) {
				Krk = phaseResponseMethod.getControllerValues()[PhaseResponseMethod.KrkPOS];
				phaseResponseMethod.setKrk(Krk * 1.15);
				calculateStepResponse();
				max = yt[0][Calc.max(yt[0])];
			}
		}

		//Feinskalierung mit dem Faktor 1.05
		if (max > controller.overShoot / 100.0 + 1.0) {
			while (max > controller.overShoot / 100.0 + 1.0) {
				Krk = phaseResponseMethod.getControllerValues()[PhaseResponseMethod.KrkPOS];
				phaseResponseMethod.setKrk(Krk / 1.05);
				calculateStepResponse();
				max = yt[0][Calc.max(yt[0])];
			}
		} else {
			while (max < controller.overShoot / 100.0 + 1.0 & Krk < 1000) {
				Krk = phaseResponseMethod.getControllerValues()[PhaseResponseMethod.KrkPOS];
				phaseResponseMethod.setKrk(Krk * 1.05);
				calculateStepResponse();
				max = yt[0][Calc.max(yt[0])];
			}
		}
	}
}
