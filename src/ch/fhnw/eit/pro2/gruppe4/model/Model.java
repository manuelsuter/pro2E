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
			closedLoop[i] = new ClosedLoop(i-5);
		}	
	}

	
	public void setData(double Ks, double Tu, double Tg, int controllerTyp, double Tp) {
		path.setData(Ks, Tu, Tg);
		for (int i = 0; i < closedLoop.length; i++) {
			closedLoop[i].setData(controllerTyp, path, Tp);
		}
	}



	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}
}