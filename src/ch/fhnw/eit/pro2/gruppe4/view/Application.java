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



public class Application extends JFrame {
	private static final long serialVersionUID = 1L;
	private Model model = new Model();
	private GUIController guiController = new GUIController(model);
	private View view = new View(guiController);
	private MenuBar menuBar = new MenuBar(guiController, this);

	/**
	 * Add the view to the content pane and register the view as observer of the
	 * model.
	 */
	public void init() {
		view.setJFrame(this);
		guiController.setView(view);
		model.addObserver(view);
		//model.notifyObservers();
		model.addObserver(menuBar);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(view, BorderLayout.CENTER);
		setJMenuBar(menuBar);
	}

	/**
	 * Entry method of the application. Sets the preferred size and the minimum
	 * size of the JFrame.
	 */
	public static void main(String[] args) {
		Application frame = new Application();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Reglerdimensionierung nach Phasanegang-Methode");
		// To set a logo:
		//frame.setIconImage(new ImageIcon(Application.class.getResource("resources/logo.png")).getImage());
		frame.setResizable(true);
		// Set preferred size of the JFrame in function of the inner screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
		int innerScreenWidth = screenSize.width - screenInsets.left - screenInsets.right;
		int innerScreenHeight = screenSize.height - screenInsets.top - screenInsets.bottom;
		Dimension minimumSize = new Dimension(200, 768);
		//1024x768 Pixel
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
