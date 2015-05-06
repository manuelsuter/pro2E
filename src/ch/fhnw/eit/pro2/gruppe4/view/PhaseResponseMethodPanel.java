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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.fhnw.eit.pro2.gruppe4.model.ClosedLoop;
import ch.fhnw.eit.pro2.gruppe4.model.Model;


public class PhaseResponseMethodPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private GUIController guiController;
	public JDoubleTextField tfTp;
	private String[] methodDesignation = {"Überschwingen                                 ", "wenig", "mittel", "stark"};
	private JLabel[] lbMethod = new JLabel[methodDesignation.length];
	private JLabel[] lbKr = new JLabel[lbMethod.length];
	private JLabel[] lbTn = new JLabel[lbMethod.length];
	private JLabel[] lbTv = new JLabel[lbMethod.length];

	
	public PhaseResponseMethodPanel(GUIController controller) {
	
		super(new GridBagLayout());
		this.guiController = controller;
		setBorder(MyBorderFactory.createMyBorder(" Phasengang-Methode "));
		
		for (int i = 0; i < lbMethod.length; i++) {
			lbMethod[i] = new JLabel(methodDesignation[i]);
			add(lbMethod[i], new GridBagConstraints(0, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
							5, 10, 5, 10), 0, 0));
			
			lbKr[i] = new JLabel();
			add(lbKr[i], new GridBagConstraints(1, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
							5, 10, 5, 10), 0, 0));
			
			lbTn[i] = new JLabel();
			add(lbTn[i], new GridBagConstraints(2, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
							5, 10, 5, 10), 0, 0));
			
			lbTv[i] = new JLabel();
			add(lbTv[i], new GridBagConstraints(3, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
							5, 10, 5, 10), 0, 0));
		}
		// Setzte die Werte Zeile für Kr, Tn, Tv.
		lbKr[0].setText("<html><i>K<sub>r</sub></html></i>");
		lbTn[0].setText("<html><i>T<sub>n</sub></html></i>");
		lbTv[0].setText("<html><i>T<sub>v</sub></html></i>");
		
		
		add(new JLabel("<html><i>T<sub>p</sub></i></html>"), new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
						5, 10, 5, 10), 0, 0));
		
		tfTp = new JDoubleTextField("",100,false);
		add(tfTp, new GridBagConstraints(1, 4, 3, 1, 1.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						5, 10, 5, 10), 0, 0));
	}
	

	public void update(Observable obs, Object obj) {
		Model model = (Model)obs;
		
			ClosedLoop[] closedLoop = model.getClosedLoop();
			//TODO: Gut fragen ob Label nicht direkt double verarbeiten kann
			for (int i = 0; i < methodDesignation.length-1; i++) {
				double[] controllerValues = closedLoop[i].getController().getControllerValues();
				lbKr[i+1].setText(""+Math.round((controllerValues[0])*1000.0)/1000.0);
				lbTn[i+1].setText(""+Math.round((controllerValues[1])*1000.0)/1000.0);
				lbTv[i+1].setText(""+Math.round((controllerValues[2])*1000.0)/1000.0);
				tfTp.setText(""+Math.round((controllerValues[3])*1000.0)/1000.0);
			}	
	}
}
