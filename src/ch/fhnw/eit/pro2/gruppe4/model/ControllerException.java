package ch.fhnw.eit.pro2.gruppe4.model;

public class ControllerException extends Exception {

	private static final long serialVersionUID = 1L;

	public ControllerException(String string) {
		// Aufruf des übergeordneten Konstruktors mit dem zu
		// erscheinenden Fehlertext
		super(string);
	}

}
