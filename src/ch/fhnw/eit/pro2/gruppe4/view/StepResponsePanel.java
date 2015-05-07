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
	private JCheckBox[] checkBox = new JCheckBox[Controller.calculationTypName.length];
	private JPanel checkBoxPanel;	
	private PhasenPlot phasenPlot = new PhasenPlot();
	private double[][][] ytValues;
	public static final Color[] plotColor = {Color.RED, Color.MAGENTA, Color.ORANGE, Color.BLACK, Color.GREEN,Color.BLUE, Color.lightGray, Color.PINK};
	private XYDataset[] datasetArray = new XYDataset[8];

	
	
	public StepResponsePanel(GUIController controller) {
		super(new BorderLayout());
		this.guiController = controller;
		setBorder(MyBorderFactory.createMyBorder(" Schrittantworten "));
		
		//Plot dem Panel hinzufügen.
		add(phasenPlot);
		
		//includes the CheckBoxes below the plot on the checkBoxPanel
		checkBoxPanel = new JPanel(new GridLayout(2,4));
		
		//Check-Box der Phasengangmethode.
		checkBox[0] = new JCheckBox(Controller.calculationTypName[0], true);
		checkBox[0].setForeground(plotColor[0]);
		checkBoxPanel.add(checkBox[0]);
		checkBox[0].addItemListener(this);
		
		// CheckBoxen der Faustformeln.
		for (int i = 1; i < Controller.calculationTypName.length ; i++) {
			checkBox[i] = new JCheckBox(Controller.calculationTypName[i], false);
			checkBox[i].setForeground(plotColor[i+2]);
			checkBoxPanel.add(checkBox[i]);
			checkBox[i].addItemListener(this);
		}
	
		// Fügt das checkBoxPanel dem BorderLayout hinzu.
		add(checkBoxPanel, BorderLayout.SOUTH);	
	}

	
	private void plotStepResponse(){
		//Datasets erstellen und schreiben.
		for (int i = 0; i < datasetArray.length; i++) {
			datasetArray[i] = phasenPlot.createDataset(ytValues[i][1], ytValues[i][0]);	
			phasenPlot.addData(i, datasetArray[i]);
		}
		//Farben den Datasets hinzufügen.
		for (int i = 0; i < plotColor.length; i++) {
			phasenPlot.setColor(i);
		}
		
		
		//Sichtbarkeit der Faustformel prüfen.
		if (checkBox[0].isSelected()==false) {
			phasenPlot.rendererArray[0].setSeriesLinesVisible(0, false);
			phasenPlot.rendererArray[1].setSeriesLinesVisible(0, false);
			phasenPlot.rendererArray[2].setSeriesLinesVisible(0, false);
		}else{
			phasenPlot.rendererArray[0].setSeriesLinesVisible(0, true);
			phasenPlot.rendererArray[1].setSeriesLinesVisible(0, true);
			phasenPlot.rendererArray[2].setSeriesLinesVisible(0, true);
		}
		
		//Sichtbarkeit der Faustformeln-Plots Plot prüfen.
		for (int i = 1; i < checkBox.length; i++) {
			if (checkBox[i].isSelected()==false) {
				phasenPlot.rendererArray[i+2].setSeriesLinesVisible(0, false);
			}else{
				phasenPlot.rendererArray[i+2].setSeriesLinesVisible(0, true);
			}
		}
		phasenPlot.repaint();
	}	
	
	
	public void itemStateChanged(ItemEvent e) {
		
		if (checkBox[0].isSelected()==false) {
			phasenPlot.rendererArray[0].setSeriesLinesVisible(0, false);
			phasenPlot.rendererArray[1].setSeriesLinesVisible(0, false);
			phasenPlot.rendererArray[2].setSeriesLinesVisible(0, false);
		}
		else{
			phasenPlot.rendererArray[0].setSeriesLinesVisible(0, true);
			phasenPlot.rendererArray[1].setSeriesLinesVisible(0, true);
			phasenPlot.rendererArray[2].setSeriesLinesVisible(0, true);
		}
		
		for (int i = 1; i < checkBox.length; i++) {
			if (checkBox[i].isSelected()==false) {
				phasenPlot.rendererArray[i+2].setSeriesLinesVisible(0, false);
			}
			else{
				phasenPlot.rendererArray[i+2].setSeriesLinesVisible(0, true);
			}
		}
	}
		
		
	
	public void setPlotInvisible(){
		for (int i = 0; i < phasenPlot.rendererArray.length; i++) {
			phasenPlot.rendererArray[i].setSeriesLinesVisible(0, false);
		}
	}
	
	public void deleteDatasets(){
		for (int i = 0; i < phasenPlot.rendererArray.length; i++) {
			phasenPlot.addData(i, null);
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
