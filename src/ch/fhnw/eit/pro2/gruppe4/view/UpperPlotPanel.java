package ch.fhnw.eit.pro2.gruppe4.view;
/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import java.awt.event.KeyEvent;
import java.util.Observable;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ch.fhnw.eit.pro2.gruppe4.model.PTn;


public class UpperPlotPanel extends JTabbedPane {

	
	private GUIController guiController;
	private PTn pTnPanel;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	
	/**
	 * 
	 * @param guiController
	 */
	public UpperPlotPanel(GUIController guiController){
		this.guiController = guiController;
		

		
		
		pTnPanel = new PTn(guiController);
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		
		
		setTabPlacement(JTabbedPane.TOP);
		
		
		addTab("PTn-Strecke", pTnPanel);
		
		addTab("Amplitudengang", panel2);

		addTab("Phasengang", panel3);
	
		addTab("Einstell-Regler", panel4);
	
	}
	/**
	 * 
	 * @param obs
	 * @param obj
	 */
	public void update(Observable obs, Object obj) {

	}
}
