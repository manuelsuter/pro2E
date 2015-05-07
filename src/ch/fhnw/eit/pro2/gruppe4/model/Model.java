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
	/**
	 * 
	 * @param Ks
	 * @param Tu
	 * @param Tg
	 * @param controllerTyp
	 * @param Tp
	 */
	
	public void setData(double Ks, double Tu, double Tg, int controllerTyp, double Tp) {
		path.setData(Ks, Tu, Tg);
		for (int i = 0; i < closedLoop.length; i++) {
			closedLoop[i].setData(controllerTyp, path, Tp);
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