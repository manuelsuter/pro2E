package ch.fhnw.eit.pro2.gruppe4.view;
/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.fhnw.eit.pro2.gruppe4.model.PTn;


public class OptimizationPanel extends JPanel implements ChangeListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GUIController guiController;
	public JSlider jsPhaseMargin;
	public JSlider jsOverShoot;
	private JLabel lbPhaseMargin, lbOverShoot;
	private double phaseMarginOffset;
	private double overShootValue;
	
		
	public OptimizationPanel(GUIController guiController){
		super(new GridBagLayout());
		this.guiController = guiController;
		setBorder(MyBorderFactory.createMyBorder(" Optimierungen "));
		
		lbOverShoot = new JLabel("Überschwingen in %");
		add(lbOverShoot, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						5, 10, 5, 10), 0, 0));
		
		//Baut und added Slider für Überschwingen.
		jsOverShoot = new JSlider(0, 30, 0);
		jsOverShoot.setMajorTickSpacing(5);
		jsOverShoot.setPaintTicks(true);
		jsOverShoot.setPaintLabels(true);
		jsOverShoot.setSnapToTicks(true);
		add(jsOverShoot, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						5, 10, 5, 10), 0, 0));
		jsOverShoot.addChangeListener(this);

		//Baut und added Slider für Pasenrandverschiebung.
		lbPhaseMargin = new JLabel("Phasenrandverschiebung in Grad");
		add(lbPhaseMargin, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						5, 10, 5, 10), 0, 0));
		
		//TODO: Phasenverschiebung ab 50 Grad geht nicht! Wieso?
		jsPhaseMargin = new JSlider(0, 45, 5);
		jsPhaseMargin.setMajorTickSpacing(5);
		jsPhaseMargin.setPaintTicks(true);
		jsPhaseMargin.setPaintLabels(true);
		jsPhaseMargin.setSnapToTicks(true);

		
		add(jsPhaseMargin, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						5, 10, 5, 10), 0, 0));
		jsPhaseMargin.addChangeListener(this);
	}
		

	
	public void stateChanged(ChangeEvent e) {
	
		if (e.getSource() == jsOverShoot) {
			overShootValue = jsOverShoot.getValue();
			guiController.setOverShoot(overShootValue);
			System.out.println(overShootValue+"over");

		}
		if (e.getSource() == jsPhaseMargin) {
			phaseMarginOffset = jsPhaseMargin.getValue();
			guiController.setPhaseMargin(phaseMarginOffset);
			System.out.println(phaseMarginOffset+"margin");
		}		
	}
	
	
	public void update(Observable obs, Object obj) {

	}

}
