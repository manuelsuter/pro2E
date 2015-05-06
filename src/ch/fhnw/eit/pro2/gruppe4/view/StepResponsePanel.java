package ch.fhnw.eit.pro2.gruppe4.view;
/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.fhnw.eit.pro2.gruppe4.model.ClosedLoop;
import ch.fhnw.eit.pro2.gruppe4.model.Controller;
import ch.fhnw.eit.pro2.gruppe4.model.Model;
import ch.fhnw.eit.pro2.gruppe4.utilities.Calc;

import org.apache.commons.math3.complex.Complex;
import org.jfree.data.xy.XYDataset;



public class StepResponsePanel extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 1L;
	private GUIController guiController;
	private GraphPlotter stepResponse;
	private double xValues[] = {1.0,2.0,3.0,4.0,5.0};
	private double yValues[] = {-4.5, -3,-2, 2.0, 4,0};
	private JCheckBox checkPhase, checkRosenberg, checkOppelt, checkZN, checkCHR20, checkCHR0;
	private JPanel checkBoxPanel;	
	private PhasenPlot phasenPlot = new PhasenPlot();
	private double[] y, t, x, z;
	
	public StepResponsePanel(GUIController controller) {
		super(new BorderLayout());
		this.guiController = controller;
		setBorder(MyBorderFactory.createMyBorder(" Schrittantworten "));
				
//		x = Calc.linspace(0, 4*Math.PI, 1000);
//		y = new double[x.length];
//		z = new double[x.length];
//		
//		for (int i = 0; i < x.length; i++) {
//			y[i] = Math.sin(x[i]);
//			z[i] = Math.cos(x[i]);
//		}
	
		add(phasenPlot);

			
				
		/**
		stepResponse = new GraphPlotter("x","y",true);
		
		stepResponse.allowNegativeXValues(false);
		stepResponse.allowNegativeYValues(true);
		stepResponse.setRange(0.0, 10.0, -2.0, 2.0);
		stepResponse.addXMaxScaleField();
		stepResponse.setData(x, y, 2, Color.BLUE, 2, "sin");
		stepResponse.addData(x, z, 2, Color.RED, 2, "cos");
		stepResponse.addData(xValues, yValues, 2, Color.YELLOW, 2, "test");
		
		add(stepResponse, BorderLayout.CENTER);
	
		*/
		//includes the CheckBoxes below the plot on the checkBoxPanel
		checkBoxPanel = new JPanel(new GridLayout(2,4));
		
		checkPhase = new JCheckBox("Phasengang-Methode", true);
		checkPhase.setForeground(Color.RED);
		checkBoxPanel.add(checkPhase);
		checkPhase.addChangeListener(this);

		
		checkRosenberg = new JCheckBox("Rosenberg");
		checkRosenberg.setForeground(Color.GREEN);
		checkBoxPanel.add(checkRosenberg);
		checkRosenberg.addChangeListener(this);
		
		checkCHR0 = new JCheckBox("Chien/Hrones/Reswick (0% Ü.)");
		checkCHR0.setForeground(Color.MAGENTA);
		checkBoxPanel.add(checkCHR0);
		checkCHR0.addChangeListener(this);
		
		checkZN = new JCheckBox("Ziegler/Nichols");
		checkZN.setForeground(Color.ORANGE);
		checkBoxPanel.add(checkZN);
		checkZN.addChangeListener(this);
		
		checkOppelt = new JCheckBox("Oppelt");
		checkBoxPanel.add(checkOppelt);
		checkOppelt.addChangeListener(this);
		
		checkCHR20 = new JCheckBox("Chien/Hrones/Reswick (20% Ü.)");
		checkCHR20.setForeground(Color.BLUE);
		checkBoxPanel.add(checkCHR20);
		checkCHR20.addChangeListener(this);
		
		add(checkBoxPanel, BorderLayout.SOUTH);	
	}

	
	private void plotStepResponse(){
		XYDataset dataset1 = phasenPlot.createDataset(t, y);
//		XYDataset dataset2 = phasenPlot.createDataset(x, z);
		
		phasenPlot.addData(0, dataset1);
//		phasenPlot.addData(1, dataset2);
		phasenPlot.repaint();

	}	

	@Override
	public void stateChanged(ChangeEvent arg0) {
		
		
	}
	public void update(Observable obs, Object obj) {
	
		Model model = (Model)obs;
		
		double[][] yt = model.getClosedLoop()[0].getStepResponse();
		
		y = new double[yt[0].length];
		t = new double[yt[1].length];
		
		
		for (int i = 0; i < y.length; i++) {
			y[i] = yt[0][i];
		}
		for (int i = 0; i < t.length; i++) {
			t[i] = yt[1][i];
//			System.out.println(y[i]+"ti");
		}
//		System.out.println(yt[0].length+"array l�nge");
		plotStepResponse();
	}
}
