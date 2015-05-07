package ch.fhnw.eit.pro2.gruppe4.view;
/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class View extends JPanel implements Observer {
	
	private static final long serialVersionUID = 1L;
	public LeftPanel leftPanel;
	private RightPanel rightPanel;
	private JFrame frame;

//TODO: Wie genau müssen wir Konstruktoren spezifizieren?
	/**
	 * 
	 * @param guiController
	 */
	public View(GUIController guiController) {
		super(new GridBagLayout());

		
		// Construct the main left and main right panel
		leftPanel = new LeftPanel(guiController);
		add(leftPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(
						0, 0, 0, 0), 0, 0));
		
		rightPanel = new RightPanel(guiController);
		add(rightPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(
						10, 10, 10, 10), 0, 0));
		}
	/**
	 * Connects the upper JFrame with the local JFrame.
	 * @param frame
	 */
	public void setJFrame(JFrame frame) {
		this.frame = frame;
		
	}
	

	/**
	 * Sets the visibility of the right panel, the new size of the frame and a flag if the frame is resizable. 
	 * @param flag
	 * @param dimension
	 */
	public void setVisibility(boolean flag, Dimension dimension){
		rightPanel.setVisible(flag);
		frame.setSize(dimension);
		frame.setResizable(flag);
	}
	/**
	 * @param obs
	 * @param obj
	 */

	public void update(Observable obs, Object obj) {
		leftPanel.update(obs, obj);
		rightPanel.update(obs, obj);
	}	

}


