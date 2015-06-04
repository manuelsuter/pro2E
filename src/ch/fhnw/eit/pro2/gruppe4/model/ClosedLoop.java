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
			controller = new Chien20();
			break;

		case 4:
			controller = new ChienApper();
			break;

		default:
			controller = new PhaseResponseMethod();
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
		double[] zah_c = controller.getUTFZahPoly();
		double[] nen_c = controller.getUTFNenPoly();
		double[] zah_p = path.getUTFZahPoly();
		double[] nen_p = path.getUTFNenPoly();

		double[] nen = Calc.diskConv(nen_c, nen_p);
		double[] zah = Calc.diskConv(zah_c, zah_p);
		nen = Calc.addArrayReverse(nen, zah);

		if (fsNotGiven) {
			double[] fsN = Calc.calculateFsN(nen);
			fs = fsN[0];
			pointnumber = (int) fsN[1];
			if (controller.controllerTyp == Controller.PID)
				pointnumber *= 2;
			else if (controller.controllerTyp == Controller.PI)
				pointnumber *= 4;
		}
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

	public void setData(int controllerTyp, Path path) throws ControllerException {
		this.path = path;
		controller.setData(controllerTyp, path);
		fsNotGiven = true;
		calculate();
		fsNotGiven = false;
	}

	/**
	 * Nimmt die InputValues entgegen und gibt sie den Unterklassen weiter
	 * 
	 * @param controllerTyp
	 * @param path
	 * @param fsN
	 * @throws ControllerException
	 */
	public void setData(int controllerTyp, Path path, double[] fsN)
			throws ControllerException {
		this.fs = fsN[0];
		this.pointnumber = (int) fsN[1];
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

	public void setData(int controllerTyp, Path path, double Tp, double overShoot, double phaseMargin)
			throws ControllerException {
		this.path = path;
		controller.setData(controllerTyp, path, Tp, overShoot, phaseMargin);
		fsNotGiven = true;
		calculate();
		fsNotGiven = false;
	}

	public void setData(int controllerTyp, Path path, double Tp,
			double overShoot, double phaseMargin, double[] fsN)
			throws ControllerException {
		this.fs = fsN[0];
		this.pointnumber = (int) fsN[1];
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

	public void setPhaseMargin(double phaseMargin) throws ControllerException {
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

	public double[] getFsN() {
		return new double[] { fs, pointnumber };
	}

	private void overShootOptimization() {
		double max = yt[0][Calc.max(yt[0])];
		PhaseResponseMethod phaseResponseMethod = (PhaseResponseMethod) controller;
		double Krk = phaseResponseMethod.getControllerValues()[PhaseResponseMethod.KrkPOS];

		// Grobskalierung mit dem Faktor 1.15
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

		// Feinskalierung mit dem Faktor 1.05
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
