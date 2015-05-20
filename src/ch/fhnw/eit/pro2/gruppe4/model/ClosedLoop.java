package ch.fhnw.eit.pro2.gruppe4.model;

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
			// TODO: Exception!
			break;
		}
	}

	protected void calculate() {
		switch (controller.CALCULATIONTYP) {
		case 0:
			calculateStepResponse();
			overShootOptimazation();
			break;

		default:
			calculateStepResponse();
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

		// TODO: noch optimieren, gibt Bereich an, welcher berechnet wird:
		// double fs = 1.0/(path.getInputValues()[Path.TgPOS]*500); // Muss
		// Potenz von 2 sein.
		// n evtl. höhr / tiefer setzen.

		double fs = 1 / path.getInputValues()[Path.TgPOS] * 100;
		//
		// double summeT = 0;
		// for (int i = 0; i < path.getT().length; i++) {
		// summeT += path.getT()[i];
		// }
		//
		// fs = summeT * path.getT().length * 0.04;
		// fs = summeT * 1/path.getT().length * 10;

		// fs = (path.getInputValues()[Path.TgPOS] +
		// path.getInputValues()[Path.TuPOS]);
		// je grösser fs desto kleiner der Bereich

		int n = 512;

		yt = Calc.schrittIfft(zah, nen, fs, n);
	}

	/**
	 * Addiert zwei Arrays zusammen.
	 * 
	 * @param b
	 * @param a
	 * @return
	 */
	// TODO: In Calc verschieben??
	protected double[] add(double[] b, double[] a) {
		double[] res = new double[a.length + b.length];
		// TODO: Vereinfachen mit Referenzen
		if (b.length > a.length) {
			res = b;
			for (int i = 0; i > (res.length - a.length); i++) {
				res[i] = b[b.length - 1 - i] + a[i];
			}
		} else if (b.length < a.length) {
			res = a;
			for (int i = 0; i > (res.length - b.length); i++) {
				res[i] = b[b.length - 1 - i] + a[i];
			}
		}
		return res;
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

	public void setData(int controllerTyp, Path path)
			throws ControllerException {
		this.path = path;
		controller.setData(controllerTyp, path);
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

	// TODO: Sicherstellen dass Endwert erreicht wird!!!!!!
	// TODO: eventuel schneller machen mit berücksichtigung des faktors
	private void overShootOptimazation() {
		double max = yt[0][Calc.diskMax(yt[0])];
		PhaseResponseMethod phaseResponseMethod = (PhaseResponseMethod) controller;
		double Krk = phaseResponseMethod.getControllerValues()[PhaseResponseMethod.KrkPOS];
		if (max > controller.overShoot / 100.0 + 1.0) {
			while (max > controller.overShoot / 100.0 + 1.0) {
				Krk = phaseResponseMethod.getControllerValues()[PhaseResponseMethod.KrkPOS];
				phaseResponseMethod.setKrk(Krk / 1.05);
				calculateStepResponse();
				max = yt[0][Calc.diskMax(yt[0])];
			}
		} else {
			while (max < controller.overShoot / 100.0 + 1.0 & Krk < 1000) {
				Krk = phaseResponseMethod.getControllerValues()[PhaseResponseMethod.KrkPOS];
				phaseResponseMethod.setKrk(Krk * 1.05);
				calculateStepResponse();
				max = yt[0][Calc.diskMax(yt[0])];
			}
		}
	}
}
