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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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



public class StepResponsePanel extends JPanel implements ItemListener {

	private static final long serialVersionUID = 1L;
	private GUIController guiController;
	private GraphPlotter stepResponse;
	private double xValues[] = {1.0,2.0,3.0,4.0,5.0};
	private double yValues[] = {-4.5, -3,-2, 2.0, 4,0};
	private JCheckBox check0, check3, check4, check5, check6, check7;
	private JPanel checkBoxPanel;	
	private PhasenPlot phasenPlot = new PhasenPlot();
	private double[] y, t;
	private double[][][] ytValues;
	public static final Color[] plotColor = {Color.RED, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.BLACK, Color.BLUE, Color.DARK_GRAY, Color.PINK};

	
	public StepResponsePanel(GUIController controller) {
		super(new BorderLayout());
		this.guiController = controller;
		setBorder(MyBorderFactory.createMyBorder(" Schrittantworten "));
				
		phasenPlot.rendererArray[0].setSeriesVisible(true);
		phasenPlot.rendererArray[1].setSeriesVisible(true);
		phasenPlot.rendererArray[2].setSeriesVisible(true);
		
		
		for (int i = 3; i < phasenPlot.rendererArray.length; i++) {
			phasenPlot.rendererArray[i].setSeriesVisible(false);
		}
		
		add(phasenPlot);
		
		//includes the CheckBoxes below the plot on the checkBoxPanel
		checkBoxPanel = new JPanel(new GridLayout(2,4));
		
		check0 = new JCheckBox(Controller.calculationTypName[0], true);
		check0.setForeground(plotColor[0]);
		checkBoxPanel.add(check0);
		check0.addItemListener(this);

		
		check3 = new JCheckBox(Controller.calculationTypName[1]);
		check3.setForeground(plotColor[3]);
		checkBoxPanel.add(check3);
		check3.addItemListener(this);
		
		check7 = new JCheckBox(Controller.calculationTypName[5]);
		check7.setForeground(plotColor[7]);
		checkBoxPanel.add(check7);
		check7.addItemListener(this);
		
		check5 = new JCheckBox(Controller.calculationTypName[3]);
		check5.setForeground(plotColor[5]);
		checkBoxPanel.add(check5);
		check5.addItemListener(this);
		
		check4 = new JCheckBox(Controller.calculationTypName[2]);
		check4.setForeground(plotColor[4]);
		checkBoxPanel.add(check4);
		check4.addItemListener(this);
		
		check6 = new JCheckBox(Controller.calculationTypName[4]);
		check6.setForeground(plotColor[6]);
		checkBoxPanel.add(check6);
		check6.addItemListener(this);
		
		add(checkBoxPanel, BorderLayout.SOUTH);	
	}

	
	private void plotStepResponse(){
		
		XYDataset[] datasetArray = new XYDataset[8];
		
		for (int i = 0; i < datasetArray.length; i++) {
			datasetArray[i] = phasenPlot.createDataset(ytValues[i][1], ytValues[i][0]);	
			phasenPlot.addData(i, datasetArray[i]);
		}
	
		
		phasenPlot.repaint();
	}	
	
	
	public void itemStateChanged(ItemEvent e) {
		
		if (check0.isSelected()==false) {
			phasenPlot.rendererArray[0].setSeriesVisible(false);
			phasenPlot.rendererArray[1].setSeriesVisible(false);
			phasenPlot.rendererArray[2].setSeriesVisible(false);
		}
		else{
			phasenPlot.rendererArray[0].setSeriesVisible(true);
			phasenPlot.rendererArray[1].setSeriesVisible(true);
			phasenPlot.rendererArray[2].setSeriesVisible(true);
		}
		
		if (check3.isSelected()==false) {
			phasenPlot.rendererArray[3].setSeriesVisible(false);
		}
		else{
			phasenPlot.rendererArray[3].setSeriesVisible(true);
		}
		
		if (check4.isSelected()==false) {
			phasenPlot.rendererArray[4].setSeriesVisible(false);
		}
		else{
			phasenPlot.rendererArray[4].setSeriesVisible(true);
		}
		
		if (check5.isSelected()==false) {
			phasenPlot.rendererArray[5].setSeriesVisible(false);
		}
		else{
			phasenPlot.rendererArray[5].setSeriesVisible(true);
		}
		
		if (check6.isSelected()==false) {
			phasenPlot.rendererArray[6].setSeriesVisible(false);
		}
		else{
			phasenPlot.rendererArray[6].setSeriesVisible(true);
		}
		
		if (check7.isSelected()==false) {
			phasenPlot.rendererArray[7].setSeriesVisible(false);
		}
		else{
			phasenPlot.rendererArray[7].setSeriesVisible(true);
		}
		
	}

	
	
	
	public void update(Observable obs, Object obj) {
	
		Model model = (Model)obs;
		
		//double[][] yt = model.getClosedLoop()[0].getStepResponse();
		
		ytValues = new double[8][][];
		//ytValues = new double[8][yt[0].length][yt[1].length];
		//y = new double[yt[0].length];
		//t = new double[yt[1].length];
		
		for (int i = 0; i < ytValues.length; i++) {
				ytValues[i] = model.getClosedLoop()[i].getStepResponse();
		}
		
		

//		for (int i = 0; i < y.length; i++) {
//				y[i] = ytValues[0][0][i];
//				t[i] = ytValues[0][1][i];
//		}
		
		
//		for (int i = 0; i < y.length; i++) {
//			y[i] = yt[0][i];
//			t[i] = yt[1][i];
//		}
//		
		
		
		
		
		
		
//		for (int i = 0; i < y.length; i++) {
//			y[i] = yt[0][i];
//		}
//		for (int i = 0; i < t.length; i++) {
//			t[i] = yt[1][i];
//		}
		
		plotStepResponse();
	}
	
}
