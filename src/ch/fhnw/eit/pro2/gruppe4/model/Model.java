package ch.fhnw.eit.pro2.gruppe4.model;

/*

 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter

 * Authors: Manuel Suter, Benjamin Müller

 * 

 * */

import java.util.Observable;

public class Model extends Observable {
	private ClosedLoop[] closedLoop = new ClosedLoop[7];
	private Path path = new Path();

	/**
	 * Instanziiert closedLoops für die Phasengang-Methode sowie die
	 * Faustormeln.
	 */
	public Model() {
		//closedLoops für Phasengang-Methode
		for (int i = 0; i < closedLoop.length - 5; i++) {
			closedLoop[i] = new ClosedLoop(0);
		}
		// closedLoops für Faustformeln
		for (int i = closedLoop.length - 5; i < closedLoop.length; i++) {
			closedLoop[i] = new ClosedLoop(i - (closedLoop.length - 5));
		}
	}

	/**
	 * Setzt alle für die Berechnung notwenidigen Werte.
	 * Löst notifyObservers() aus.	
	 * @param Ks
	 * @param Tu
	 * @param Tg
	 * @param controllerTyp
	 * @param Tp
	 * @param overShoot
	 * @param phaseMarginOffset
	 * @throws SaniException
	 * @throws ControllerException
	 */
	public void setData(double Ks, double Tu, double Tg, int controllerTyp,
			double[] Tp, double overShoot, double phaseMarginOffset)
			throws SaniException, ControllerException {
		path.setData(Ks, Tu, Tg);
		double phaseMargin, phaseMarginPos, phaseMarginNeg;

		switch (controllerTyp) {
		case Controller.PI:
			phaseMargin = PhaseResponseMethod.PHASEMARGINPI;
			phaseMarginPos = phaseMargin
					+ (phaseMarginOffset / 180 * 2 * Math.PI);
			phaseMarginNeg = phaseMargin
					- (phaseMarginOffset / 180 * 2 * Math.PI);
			break;
		case Controller.PID:
			phaseMargin = PhaseResponseMethod.PHASEMARGINPID;
			phaseMarginPos = phaseMargin
					+ (phaseMarginOffset / 180 * 2 * Math.PI);
			phaseMarginNeg = phaseMargin
					- (phaseMarginOffset / 180 * 2 * Math.PI);
			break;

		default:
			throw new ControllerException();
		}

		closedLoop[1].setData(controllerTyp, path, Tp[1], overShoot,
				phaseMargin);
		double[] fsN = closedLoop[1].getFsN();

		closedLoop[0].setData(controllerTyp, path, Tp[0], overShoot,
				phaseMarginPos, fsN);

		closedLoop[2].setData(controllerTyp, path, Tp[2], overShoot,
				phaseMarginNeg, fsN);

		for (int i = closedLoop.length - 4; i < closedLoop.length; i++) {
			closedLoop[i].setData(controllerTyp, path);
		}

		notifyObservers();
	
	}

	/**
	 * Setzt das Überschwingen.
	 * 
	 * @param overShootValue
	 */
	public void setOverShoot(double overShootValue) {
		closedLoop[1].setOverShoot(overShootValue);
		double[] fsN = closedLoop[1].getFsN();
		closedLoop[0].setOverShoot(overShootValue, fsN);
		closedLoop[2].setOverShoot(overShootValue, fsN);
		notifyObservers();
	}

	/**
	 * Setzt Tp inkl. Tp-Index.
	 * 
	 * @param tpValues
	 * @param index
	 */
	public void setTp(double[] tpValues, int index) {
		closedLoop[index].setTp(tpValues[index]);
		notifyObservers();
	}

	/**
	 * Setzt den Phasenrand. Löst notifyOberservers() aus.
	 * 
	 * @param phaseMarginOffset
	 */
	public void setPhaseMargin(double phaseMarginOffset) {
		try {
			switch (closedLoop[0].getController().getControllerTyp()) {
			case Controller.PI:
				closedLoop[0].setPhaseMargin(PhaseResponseMethod.PHASEMARGINPI + (phaseMarginOffset / 180 * 2 * Math.PI));
				closedLoop[2].setPhaseMargin(PhaseResponseMethod.PHASEMARGINPI - (phaseMarginOffset / 180 * 2 * Math.PI));
				break;
			case Controller.PID:
				closedLoop[0].setPhaseMargin(PhaseResponseMethod.PHASEMARGINPID + (phaseMarginOffset / 180 * 2 * Math.PI));
				closedLoop[2].setPhaseMargin(PhaseResponseMethod.PHASEMARGINPID - (phaseMarginOffset / 180 * 2 * Math.PI));
				break;

			default:
				break;
			}
			notifyObservers();
			
		} catch (ControllerException e) {
			// kann nicht auftreten, da nur PI- und PID- Regler gewählt werden kann.
		}
		
	}

	/**
	 * Gibt den gschlossenen Regelkreis zurück.
	 * @return
	 */
	public ClosedLoop[] getClosedLoop() {
		return closedLoop;
	}

	/**
	 * Gibt die Strecke zurück.
	 * @return
	 */
	public Path getPath() {
		return path;
	}
	
	
	/**
	 * Informiert die Observer über Änderung. Löst setChanged() aus.
	 */
	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}
}