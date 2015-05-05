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

import ch.fhnw.eit.pro2.gruppe4.model.ClosedLoop;
import ch.fhnw.eit.pro2.gruppe4.model.Model;


public class RulesOfThumbPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private GUIController controller;
	
	private String[] methodDesignation = {"", "calculationTypName", "mittel", "stark"};
	private JLabel[] lbMethod = new JLabel[methodDesignation.length];
	private JLabel[] lbKr = new JLabel[lbMethod.length];
	private JLabel[] lbTn = new JLabel[lbMethod.length];
	private JLabel[] lbTv = new JLabel[lbMethod.length];
	
	
	public RulesOfThumbPanel(GUIController controller) {
		super(new GridBagLayout());
		this.controller = controller;
		setBorder(MyBorderFactory.createMyBorder(" Faustformeln "));
		
		
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
		
		lbKr[0].setText("<html><i>K<sub>r</sub></html></i>");
		lbTn[0].setText("<html><i>T<sub>n</sub></html></i>");
		lbTv[0].setText("<html><i>T<sub>v</sub></html></i>");
		
		/**
		makeRulesOfThumbLine("", "<html><i>K<sub>r</sub></html></i>", "<html><i>T<sub>n</sub></i></html>", "<html><i>T<sub>v</sub></h</html></i>tml></i>", 0);
		makeRulesOfThumbLine("Rosenberg", "8.49", "5.72", "1.25", 1);
		makeRulesOfThumbLine("Oppelt", "8.49", "5.72", "1.25", 2);
		makeRulesOfThumbLine("Ziegler/Nichols", "8.49", "5.72", "1.25", 3);
		makeRulesOfThumbLine("Chien/Hrones/Reswick (20% Ü.)", "8.49", "5.72", "1.25", 4);
		makeRulesOfThumbLine("Chien/Hrones/Reswick (aperiod.)", "8.49", "5.72", "1.25", 5);
*/
		
	}
	
	
	
	public void makeRulesOfThumbLine(String lbRule, String lbKr, String lbTn, String lbTv, int y){
		
		add(new JLabel(lbRule), new GridBagConstraints(0, y, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
						5, 10, 5, 10), 0, 0));
		
		add(new JLabel(lbKr), new GridBagConstraints(1, y, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
						5, 10, 5, 10), 0, 0));
		
		add(new JLabel(lbTn), new GridBagConstraints(2, y, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
						5, 10, 5, 10), 0, 0));
		
		add(new JLabel(lbTv), new GridBagConstraints(3, y, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
						5, 10, 5, 10), 0, 0));
	}
	
	
	
	public void update(Observable obs, Object obj) {
		Model model = (Model)obs;
		
		ClosedLoop[] closedLoop = model.getClosedLoop();
		//TODO: Gut fragen ob Label nicht direkt double verarbeiten kann
		for (int i = 1; i < methodDesignation.length; i++) {
			double[] controllerValues = closedLoop[i].getController().getControllerValues();
			lbKr[i].setText(""+controllerValues[0]);
			lbTn[i].setText(""+controllerValues[1]);
			lbTv[i].setText(""+controllerValues[2]);
		}

	}
	
}
