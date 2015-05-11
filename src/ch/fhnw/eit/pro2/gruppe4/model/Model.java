package ch.fhnw.eit.pro2.gruppe4.model;
/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import java.util.Observable;

public class Model extends Observable {
	private ClosedLoop[] closedLoop = new ClosedLoop[8];
	private Path path = new Path();

	
	
	public Model(){
		for (int i = 0; i < closedLoop.length-5; i++) {
			closedLoop[i] = new ClosedLoop(0);
		}
		for (int i = closedLoop.length-5; i < closedLoop.length; i++) {
			closedLoop[i] = new ClosedLoop(i-(closedLoop.length-6));
		}	
	}

	
	public void setData(double Ks, double Tu, double Tg, int controllerTyp, double Tp, double overShoot, double phaseMarginOffset) throws SaniException, ControllerException{
			path.setData(Ks, Tu, Tg);
		double phaseMargin, phaseMarginPos, phaseMarginNeg;

		switch (controllerTyp) {
		case Controller.PI:
			phaseMargin = PhaseResponseMethod.PHASEMARGINPI;
			phaseMarginPos = phaseMargin + (phaseMarginOffset/180*2*Math.PI);
			phaseMarginNeg = phaseMargin - (phaseMarginOffset/180*2*Math.PI);
			break;
		case Controller.PID:
			phaseMargin = PhaseResponseMethod.PHASEMARGINPID;
			phaseMarginPos = phaseMargin + (phaseMarginOffset/180*2*Math.PI);
			phaseMarginNeg = phaseMargin - (phaseMarginOffset/180*2*Math.PI);
			break;

		default:
			throw new ControllerException("Regler-Typ ist nicht Implementiert.");
		}		
		
		
		closedLoop[0].setData(controllerTyp, path, Tp, overShoot, phaseMarginPos);
		closedLoop[1].setData(controllerTyp, path, Tp, overShoot, phaseMargin);
		closedLoop[2].setData(controllerTyp, path, Tp, overShoot, phaseMarginNeg);
		
		
//		for (int i = 0; i < closedLoop.length-5; i++) {
//			closedLoop[i].setData(controllerTyp, path, Tp, phaseMarginOffset);
//		}
		for (int i = closedLoop.length-5; i < closedLoop.length; i++) {
			closedLoop[i].setData(controllerTyp, path, Tp);
		}
		notifyObservers();
	}

	public void setOverShoot(double overShootValue){
		for (int j = 0; j < closedLoop.length-5; j++) {
			closedLoop[j].setOverShoot(overShootValue);
		}
		notifyObservers();
	}
	
	public void setTp(double Tp){
		for (int j = 0; j < closedLoop.length-5; j++) {
			closedLoop[j].setTp(Tp);
		}	
		notifyObservers();
	}
	
	public void setPhaseMargin(double phaseMarginOffset){
		switch (closedLoop[0].getController().getControllerTyp()) {
		case Controller.PI:
			closedLoop[0].setPhaseMargin(PhaseResponseMethod.PHASEMARGINPI + (phaseMarginOffset/180*2*Math.PI));
			closedLoop[2].setPhaseMargin(PhaseResponseMethod.PHASEMARGINPI - (phaseMarginOffset/180*2*Math.PI));
			break;
		case Controller.PID:
			closedLoop[0].setPhaseMargin(PhaseResponseMethod.PHASEMARGINPID + (phaseMarginOffset/180*2*Math.PI));
			closedLoop[2].setPhaseMargin(PhaseResponseMethod.PHASEMARGINPID - (phaseMarginOffset/180*2*Math.PI));
			break;

		default:
			break;
		}
		notifyObservers();
	}
	
	public ClosedLoop[] getClosedLoop(){
		
		return closedLoop;
	}

	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}
}