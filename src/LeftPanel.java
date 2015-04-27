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

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;


public class LeftPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private GUIController controller;
	private InputPanel inputPanel;
	private ControllerChooserPanel regulatorChooserPanel;
	private ControllerValuePanel regulatorValuePanel;

	
	
	public LeftPanel(GUIController controller) {
		super(new GridBagLayout());	
		this.controller = controller;
		
		UIManager.put ("ToggleButton.select", Color.ORANGE); 
	
		// Construct the inputPanel, regulatorChooserPanel and regulatorValuePanel
		inputPanel = new InputPanel(controller);
		add(inputPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						2, 2, 2, 2), 0, 0));
		
		regulatorChooserPanel = new ControllerChooserPanel(controller);
		add(regulatorChooserPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						2, 2, 2, 2), 0, 0));
		
		regulatorValuePanel = new ControllerValuePanel(controller);
		add(regulatorValuePanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						2, 2, 2, 2), 0, 0));
	}	
	
	public void update(Observable obs, Object obj) {
		inputPanel.update(obs, obj);
		regulatorChooserPanel.update(obs, obj);
		regulatorValuePanel.update(obs, obj);
	}
}