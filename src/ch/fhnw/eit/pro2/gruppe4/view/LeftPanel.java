package ch.fhnw.eit.pro2.gruppe4.view;

/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class LeftPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public InputPanel inputPanel;
	public ControllerChooserPanel controllerChooserPanel;
	public ControllerValuePanel controllerValuePanel;

	public LeftPanel(GUIController controller) {
		super(new GridBagLayout());

		UIManager.put("ToggleButton.select", Color.ORANGE);

		// Construct the inputPanel, regulatorChooserPanel and
		// regulatorValuePanel
		inputPanel = new InputPanel(controller);
		add(inputPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));

		controllerChooserPanel = new ControllerChooserPanel(controller);
		add(controllerChooserPanel, new GridBagConstraints(0, 1, 1, 1, 0.0,
				0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));

		controllerValuePanel = new ControllerValuePanel(controller);
		add(controllerValuePanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
	}

	public void update(Observable obs, Object obj) {
		inputPanel.update(obs, obj);
		controllerChooserPanel.update(obs, obj);
		controllerValuePanel.update(obs, obj);
	}
}