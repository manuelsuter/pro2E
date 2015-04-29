package ch.fhnw.eit.pro2.gruppe4.model;
/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ch.fhnw.eit.pro2.gruppe4.utilities.MiniMatlab;
import ch.fhnw.eit.pro2.gruppe4.view.GUIController;
import ch.fhnw.eit.pro2.gruppe4.view.GraphPlotter;


public class PTn extends JPanel {
	
	
	private GUIController controller;
	private GraphPlotter pTn;
	private double xValues[] = {1.0,2.0,3.0,4.0,5.0};
	private double yValues[] = {-4.5, -3,-2, 2.0, 4,0};

	
	public PTn(GUIController controller){
		super(new GridLayout(1,1));
		this.controller = controller;
		
		double[] x = MiniMatlab.linspace(0, 4*Math.PI, 1000);
		double[] y = new double[x.length];
		double[] z = new double[x.length];
		
		for (int i = 0; i < x.length; i++) {
			y[i] = Math.sin(x[i]);
			z[i] = Math.cos(x[i]);
		}
		
		//Includes the stepresponse plot.
		
		pTn = new GraphPlotter("x","y",true);
		
		pTn.allowNegativeXValues(false);
		pTn.allowNegativeYValues(false);
		pTn.setRange(0.0, 10.0, 0.0, 5.0);
		//pTn.addXMaxScaleField();
		pTn.setData(x, y, 2, Color.BLACK, 2, "");

		
		add(pTn);

	}
}

