package ch.fhnw.eit.pro2.gruppe4.model;
/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import java.util.Observable;

public class Model extends Observable {
	
	private Model(){
		//erstellt zu jedem Reglerberechnungstyp einen ClosedLoop und liefert die zur Berechung notwendigen Daten. Organisiert die CloedLoops in einem Array.
		
		//*Ein Objekt mit den Inhalten: String ControllerCalculationTyp, int ControllerTyp, Path path, double Tp, double "phiR", double/int overshoot
		Object[] object = new Object[7];
		ClosedLoop closedLoop = new ClosedLoop();
		for (int i = 0; i < object.length; i++) {
			objetc[i] = 
			
		}
		
	}

	public void setData(String data) {
		
	}



	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}
}