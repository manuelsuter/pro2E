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


public class ControllerValuePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private GUIController guiController;
	private RulesOfThumbPanel rulesOfThumbPanel;
	public PhaseResponseMethodPanel phaseResponsePanel;
	private JLabel lbRosenberg, lbOppelt, lbZNn, lbZNs, lbCHR2, lbCHRa, lbTSUMn, lbTSUMs, lbKR, lbTN, lbTV;
	
	
	public ControllerValuePanel(GUIController controller) {
		super(new GridBagLayout());
		this.guiController = controller;
		setBorder(MyBorderFactory.createMyBorder(" Reglerwerte "));
	
		
		phaseResponsePanel = new PhaseResponseMethodPanel(controller);
		add(phaseResponsePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						2, 2, 2, 2), 0, 0));
		
			
		rulesOfThumbPanel = new RulesOfThumbPanel(controller);
		add(rulesOfThumbPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						2, 2, 2, 2), 0, 0));
	
	}
	
	public void update(Observable obs, Object obj) {
		phaseResponsePanel.update(obs, obj);
		rulesOfThumbPanel.update(obs, obj);
	}
}


