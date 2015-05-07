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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.UIManager;


public class ControllerChooserPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private GUIController guiController;
	public JToggleButton btPI, btPID;
	private ButtonGroup bGroup;

	
	public ControllerChooserPanel(GUIController guiController) {
		super(new GridBagLayout());
		this.guiController = guiController;
		setBorder(MyBorderFactory.createMyBorder(" PI / PID-T1 Regler "));
		
		btPI = new JToggleButton("PI");
		btPID = new JToggleButton("PID-T1", true);
				
		bGroup = new ButtonGroup();
		bGroup.add(btPI);
		bGroup.add(btPID);
		
		add(btPI, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.PAGE_START, GridBagConstraints.NONE, new Insets(
						10, 10, 10, 10), 0, 0));

		add(btPID, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.PAGE_START, GridBagConstraints.NONE, new Insets(
						10, 10, 10, 10), 0, 0));
	}
	public void update(Observable obs, Object obj) {

	}
}
