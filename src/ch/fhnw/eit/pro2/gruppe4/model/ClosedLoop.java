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

	/**
	 * Erzeugt einen Controller mit der entsprechenden Berechnungsart.
	 * 
	 * @param calculationTyp
	 */
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

	/**
	 * Nimmt die InputValues entgegen und gibt sie den Unterklassen weiter.
	 * 
	 * @param controllerTyp
	 * @param path
	 * @throws ControllerException
	 */
	public void setData(int controllerTyp, Path path)
			throws ControllerException {
		this.path = path;
		controller.setData(controllerTyp, path);
		fsNotGiven = true;
		calculate();
	}

	/**
	 * Nimmt die InputValues entgegen und gibt sie den Unterklassen weiter.
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
	 * Nimmt die InputValues entgegen und gibt sie den Unterklassen weiter.
	 * 
	 * @param controllerTyp
	 * @param path
	 * @param Tp
	 * @param overShoot
	 * @param phaseMargin
	 * @throws ControllerException
	 */
	public void setData(int controllerTyp, Path path, double Tp,
			double overShoot, double phaseMargin) throws ControllerException {
		this.path = path;
		controller.setData(controllerTyp, path, Tp, overShoot, phaseMargin);
		fsNotGiven = true;
		calculate();
	}

	/**
	 * Nimmt die InputValues entgegen und gibt sie den Unterklassen weiter.
	 * 
	 * @param controllerTyp
	 * @param path
	 * @param Tp
	 * @param overShoot
	 * @param phaseMargin
	 * @param fsN
	 * @throws ControllerException
	 */
	public void setData(int controllerTyp, Path path, double Tp,
			double overShoot, double phaseMargin, double[] fsN)
			throws ControllerException {
		this.fs = fsN[0];
		this.pointnumber = (int) fsN[1];
		this.path = path;
		controller.setData(controllerTyp, path, Tp, overShoot, phaseMargin);
		calculate();
	}

	/**
	 * Nimmt overShootValue entgegen und gibt es der Unterklasse weiter.
	 * 
	 * @param overShootValue
	 */
	public void setOverShoot(double overShootValue) {
		controller.setOverShoot(overShootValue);
		fsNotGiven = true;
		calculate();
	}

	/**
	 * Nimmt Tp entgegen und gibt es der Unterklasse weiter.
	 * 
	 * @param Tp
	 */
	public void setTp(double Tp) {
		controller.setTp(Tp);
		calculate();
	}

	/**
	 * Nimmt phasemargin entgegen und gibt es der Unterklasse weiter.
	 * 
	 * @param phaseMargin
	 * @throws ControllerException
	 */
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
	 * @return yt
	 */
	public double[][] getStepResponse() {
		return yt;
	}

	/**
	 * Gibt UTF des geschlossenen Regelkreises zurück.
	 * 
	 * @return utf
	 */
	public UTF getUTF() {
		return utf;
	}

	/**
	 * Gibt fsN zurück.
	 * 
	 * @return fsN
	 */
	public double[] getFsN() {
		return new double[] { fs, pointnumber };
	}

	/**
	 * Löst calculateStepResponse() aus und optimiert je nach
	 * Controller-Calculationtyp das Überschwingen mithilfe von
	 * overShootOptimization.
	 */
	private void calculate() {
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
	private void calculateStepResponse() {
		System.out.println("calculate Step Response");
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
			double proportion = path.getInputValues()[Path.TuPOS]
					/ path.getInputValues()[Path.TgPOS];
			if (controller.controllerTyp == Controller.PID)
				if (proportion < 0.015)
					pointnumber /= 16;
				else if (proportion < 0.02)
					pointnumber /= 8;
				else if (proportion < 0.05)
					pointnumber /= 4;
				else if (proportion <= 0.103)
					pointnumber /= 2;
				else
					pointnumber *= 2;
			else if (controller.controllerTyp == Controller.PI)
				if (proportion < 0.05)
					proportion /= 2;
				else
					pointnumber *= 4;
			fsNotGiven = false;
		}
		yt = Calc.schrittIfft(zah, nen, fs, pointnumber);
		
		System.out.println("pointnumber"+pointnumber);

		System.out.println("Ende calculate Step Response");

	}

	/**
	 * Optimiert das Überschwingen anhand des gewählten Überschwingens.
	 */
	private void overShootOptimization() {
		PhaseResponseMethod phaseResponseMethod = (PhaseResponseMethod) controller;
		double max = yt[0][Calc.max(yt[0])];

		// TODO weg
		// Zeitmessung
		double zstVorher = System.currentTimeMillis();

		// Grobskalierung
		int order = path.getT().length;
		double maxSoll = controller.overShoot / 100 + 1.0;
		double KrkNew;
		int count = 0;
		while (Math.abs(maxSoll - max) > 0.08 & count < 5) {
			count++;
			double Krk = phaseResponseMethod.getControllerValues()[PhaseResponseMethod.KrkPOS];
			if (max > maxSoll) {
				KrkNew = Krk * maxSoll / max * (order / 8.0);

			} else {
				KrkNew = Krk * maxSoll / max * (8.0 / order);
			}
			phaseResponseMethod.setKrk(KrkNew);
			calculateStepResponse();
			max = yt[0][Calc.max(yt[0])];
		}

		// Feinskalierung mit dem Faktor 1.05
		double Krk = phaseResponseMethod.getControllerValues()[PhaseResponseMethod.KrkPOS];
		if (max > maxSoll) {
			while (max > maxSoll & Krk > 1e-19) {
				phaseResponseMethod.setKrk(Krk / 1.05);
				calculateStepResponse();
				max = yt[0][Calc.max(yt[0])];
				Krk = phaseResponseMethod.getControllerValues()[PhaseResponseMethod.KrkPOS];
			}
		} else {
			while (max < maxSoll & Krk < 1e16) {
				phaseResponseMethod.setKrk(Krk * 1.05);
				calculateStepResponse();
				max = yt[0][Calc.max(yt[0])];
				Krk = phaseResponseMethod.getControllerValues()[PhaseResponseMethod.KrkPOS];
			}
		}
		// TODO weg
		// Zeitmessung
		double zstNachher = System.currentTimeMillis();
		System.out.println("Time" + (zstNachher - zstVorher));
	}
}
