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
	private JCheckBox check0, check3, check4, check5, check6, check7;
	private JPanel checkBoxPanel;	
	private PhasenPlot phasenPlot = new PhasenPlot();
	private double[][][] ytValues;
	public static final Color[] plotColor = {Color.RED, Color.MAGENTA, Color.ORANGE, Color.BLACK, Color.GREEN,Color.BLUE, Color.lightGray, Color.PINK};

	
	public StepResponsePanel(GUIController controller) {
		super(new BorderLayout());
		this.guiController = controller;
		setBorder(MyBorderFactory.createMyBorder(" Schrittantworten "));
		
		phasenPlot.rendererArray[0].setSeriesLinesVisible(0, true);
		phasenPlot.rendererArray[1].setSeriesLinesVisible(0, true);
		phasenPlot.rendererArray[2].setSeriesLinesVisible(0, true);
		
		for (int i = 3; i < phasenPlot.rendererArray.length; i++) {
			phasenPlot.rendererArray[i].setSeriesLinesVisible(0, false);
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
		
		check4 = new JCheckBox(Controller.calculationTypName[2]);
		check4.setForeground(plotColor[4]);
		checkBoxPanel.add(check4);
		check4.addItemListener(this);
		
		check5 = new JCheckBox(Controller.calculationTypName[3]);
		check5.setForeground(plotColor[5]);
		checkBoxPanel.add(check5);
		check5.addItemListener(this);
		
		check6 = new JCheckBox(Controller.calculationTypName[4]);
		check6.setForeground(plotColor[6]);
		checkBoxPanel.add(check6);
		check6.addItemListener(this);
		
		check7 = new JCheckBox(Controller.calculationTypName[5]);
		check7.setForeground(plotColor[7]);
		checkBoxPanel.add(check7);
		check7.addItemListener(this);
		
		add(checkBoxPanel, BorderLayout.SOUTH);	
	}

	
	private void plotStepResponse(){
		
		XYDataset[] datasetArray = new XYDataset[8];
		
		for (int i = 0; i < datasetArray.length; i++) {
			datasetArray[i] = phasenPlot.createDataset(ytValues[i][1], ytValues[i][0]);	
			phasenPlot.addData(i, datasetArray[i]);
		}
		
		for (int i = 0; i < plotColor.length; i++) {
			phasenPlot.setColor(i);
		}		
		phasenPlot.repaint();
	}	
	
	
	public void itemStateChanged(ItemEvent e) {
		
		if (check0.isSelected()==false) {
			phasenPlot.rendererArray[0].setSeriesLinesVisible(0, false);
			phasenPlot.rendererArray[1].setSeriesLinesVisible(0, false);
			phasenPlot.rendererArray[2].setSeriesLinesVisible(0, false);
		}
		else{
			phasenPlot.rendererArray[0].setSeriesLinesVisible(0, true);
			phasenPlot.rendererArray[1].setSeriesLinesVisible(0, true);
			phasenPlot.rendererArray[2].setSeriesLinesVisible(0, true);
		}
		if (check3.isSelected()==false) {
			phasenPlot.rendererArray[3].setSeriesLinesVisible(0, false);
		}
		else{
			phasenPlot.rendererArray[3].setSeriesLinesVisible(0, true);
		}
		if (check4.isSelected()==false) {
			phasenPlot.rendererArray[4].setSeriesLinesVisible(0, false);
		}
		else{
			phasenPlot.rendererArray[4].setSeriesLinesVisible(0, true);
		}
		if (check5.isSelected()==false) {
			phasenPlot.rendererArray[5].setSeriesLinesVisible(0, false);
		}
		else{
			phasenPlot.rendererArray[5].setSeriesLinesVisible(0, true);
		}
		if (check6.isSelected()==false) {
			phasenPlot.rendererArray[6].setSeriesLinesVisible(0, false);
		}
		else{
			phasenPlot.rendererArray[6].setSeriesLinesVisible(0, true);
		}
		if (check7.isSelected()==false) {
			phasenPlot.rendererArray[7].setSeriesLinesVisible(0, false);
		}
		else{
			phasenPlot.rendererArray[7].setSeriesLinesVisible(0, true);
		}
	}

	
	public void update(Observable obs, Object obj) {
		Model model = (Model)obs;
		
		ytValues = new double[8][][];
		for (int i = 0; i < ytValues.length; i++) {
				ytValues[i] = model.getClosedLoop()[i].getStepResponse();
		}
		plotStepResponse();
	}
	
}
