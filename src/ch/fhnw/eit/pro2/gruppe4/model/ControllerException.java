package ch.fhnw.eit.pro2.gruppe4.model;

public class ControllerException extends Exception {
	
	ControllerException() {
        // Aufruf des �bergeordneten Konstruktors mit dem zu
        // erscheinenden Fehlertext
        super("Ein Regler-Fehler ist eingetreten.");
    }

}
