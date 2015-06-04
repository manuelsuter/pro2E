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

public class ControllerValuePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public RulesOfThumbPanel rulesOfThumbPanel;
	public PhaseResponseMethodPanel phaseResponsePanel;

	/** 
	 * Setzt das GridBagLayout.
	 * Besitzt das phaseResponseMethodPanel sowie das rulesOfThumbPanel.
	 * @param guiController
	 */
	public ControllerValuePanel(GUIController guiController) {
		super(new GridBagLayout());
		setBorder(MyBorderFactory.createMyBorder(" Reglerwerte "));

		phaseResponsePanel = new PhaseResponseMethodPanel(guiController);
		add(phaseResponsePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));

		rulesOfThumbPanel = new RulesOfThumbPanel(guiController);
		add(rulesOfThumbPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));

	}	
	
	/**
	 * Übergibt obs und obj an das phaseResponsePanel und rulesOfThumbPanel.
	 */
	public void update(Observable obs, Object obj) {
		phaseResponsePanel.update(obs, obj);
		rulesOfThumbPanel.update(obs, obj);
	}
}
