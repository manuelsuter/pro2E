package ch.fhnw.eit.pro2.gruppe4.view;

/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import ch.fhnw.eit.pro2.gruppe4.model.Model;
import ch.fhnw.eit.pro2.gruppe4.utilities.Utility;

public class Application extends JFrame {
	private static final long serialVersionUID = 1L;
	private Model model = new Model();
	private GUIController guiController = new GUIController(model);
	private View view = new View(guiController);
	private MenuBar menuBar = new MenuBar(guiController, this);

	/**
	 * Fügt die View dem content pane hinzu und registriert die view als Observer des Models.
	 */
	public void init() {
		view.setJFrame(this);
		guiController.setView(view);
		model.addObserver(view);
		model.addObserver(menuBar);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(view, BorderLayout.CENTER);
		setJMenuBar(menuBar);
		guiController.calculate();
	}

	/**
	 * Start-Methode der Applikation. Setzt die präferenzierte und minimale Grösse der Applikation. 
	 */
	public static void main(String[] args) {
		Application frame = new Application();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Reglerdimensionierung nach Phasengang-Methode");
		ImageIcon icon = Utility.loadResourceIcon("PID.png");
		frame.setIconImage(icon.getImage());
		frame.setResizable(true);
		// Set preferred size of the JFrame in function of the inner screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
		int innerScreenWidth = screenSize.width - screenInsets.left - screenInsets.right;
		int innerScreenHeight = screenSize.height - screenInsets.top - screenInsets.bottom;
		Dimension minimumSize = new Dimension(200, 768);

		if (innerScreenWidth < minimumSize.width) {
			minimumSize.width = innerScreenWidth;
		}
		if (innerScreenHeight < minimumSize.height) {
			minimumSize.height = innerScreenHeight;
		}
		frame.setMinimumSize(minimumSize);
		frame.setPreferredSize(new Dimension(1200, 768));

		frame.init();
		frame.setVisible(true);
		frame.pack();
		frame.validate();
	}
}
