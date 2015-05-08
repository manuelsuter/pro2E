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


public class UpperPlotPanel extends JPanel implements ChangeListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GUIController guiController;
	private JSlider jsPhaseMargin;
	private JSlider jsOverShoot;
	private JLabel lbPhaseMargin, lbOverShoot;
	int phaseMarginValue;
	int overShootValue;
	
		
	public UpperPlotPanel(GUIController guiController){
		super(new GridBagLayout());
		this.guiController = guiController;
		setBorder(MyBorderFactory.createMyBorder(" Optimierungen "));
		
		lbOverShoot = new JLabel("Überschwingen");
		add(lbOverShoot, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						5, 10, 5, 10), 0, 0));
		
		
		jsOverShoot = new JSlider(0, 30, 0);
		jsOverShoot.setMajorTickSpacing(5);
		jsOverShoot.setPaintTicks(true);
		jsOverShoot.setPaintLabels(true);
		jsOverShoot.setSnapToTicks(true);
		add(jsOverShoot, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						5, 10, 5, 10), 0, 0));
		jsOverShoot.addChangeListener(this);

				
		lbPhaseMargin = new JLabel("Phasenrand");
		add(lbPhaseMargin, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						5, 10, 5, 10), 0, 0));
		
		jsPhaseMargin = new JSlider(0, 50, 0);
		jsPhaseMargin.setMajorTickSpacing(5);
		jsPhaseMargin.setPaintTicks(true);
		jsPhaseMargin.setPaintLabels(true);
		jsPhaseMargin.setSnapToTicks(true);

		
		add(jsPhaseMargin, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						5, 10, 5, 10), 0, 0));
		jsPhaseMargin.addChangeListener(this);
		
		
		
		
	}
	
	
	

	@Override
	public void stateChanged(ChangeEvent e) {
	
		if (e.getSource() == jsOverShoot) {
			overShootValue = jsOverShoot.getValue();
			guiController.setOverShoot(overShootValue);
			System.out.println(overShootValue+"over");

		}
		if (e.getSource() == jsPhaseMargin) {
			phaseMarginValue = jsPhaseMargin.getValue();
			guiController.setPhaseMargin(phaseMarginValue);
			System.out.println(phaseMarginValue+"margin");
		}		
	}
	
	
	public void update(Observable obs, Object obj) {

	}

}
