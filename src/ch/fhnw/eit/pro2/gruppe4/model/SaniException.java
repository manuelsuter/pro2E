package ch.fhnw.eit.pro2.gruppe4.model;

/*

 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter

 * Authors: Manuel Suter

 * 

 * */

public class SaniException extends Exception {

	private static final long serialVersionUID = 1L;

	public SaniException(String string) {
		// Aufruf des übergeordneten Konstruktors mit dem zu
		// erscheinenden Fehlertext
		super(string);
	}
}
