package ch.fhnw.eit.pro2.gruppe4.model;
/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import java.util.Observable;

public class Model extends Observable {
	private String data = "ggg";

	public void setData(String data) {
		this.data = data.toUpperCase();
		notifyObservers(); 
	}

	public String getData() {
		return data;
	}

	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}
}