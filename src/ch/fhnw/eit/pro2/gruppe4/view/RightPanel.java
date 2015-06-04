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

import javax.swing.JPanel;

public class RightPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public OptimizationPanel optimizationPanel;
	public StepResponsePanel stepResponsePanel;

	/**
	 * Setzt GridBagLayout.
	 * Erzugt und platziert optimizationPanel und stepResponsePanel.
	 * @param controller
	 */
	public RightPanel(GUIController controller) {
		super(new GridBagLayout());

		// Construct the upperPlotPanel and the stepResponsePanel
		optimizationPanel = new OptimizationPanel(controller);
		add(optimizationPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.1, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

		stepResponsePanel = new StepResponsePanel(controller);
		add(stepResponsePanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.9, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

	}
	
	/**
	 * Übergibt obs und obj an optimizationPanel und stepResponsePanel weiter.
	 * @param obs
	 * @param obj
	 */
	public void update(Observable obs, Object obj) {
		optimizationPanel.update(obs, obj);
		stepResponsePanel.update(obs, obj);
	}
}