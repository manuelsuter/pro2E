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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import ch.fhnw.eit.pro2.gruppe4.utilities.Utility;

public class ControllerChooserPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private GUIController guiController;
	public JToggleButton btPI, btPID;
	private ButtonGroup bGroup;

	/**
	 * Setzt GridBagLayout.
	 * Baut eine ButtonGroup mit den Button zur Wahl von PI und PID.
	 * @param guiController
	 */
	public ControllerChooserPanel(GUIController guiController) {
		super(new GridBagLayout());
		this.guiController = guiController;
		setBorder(MyBorderFactory.createMyBorder(" PI / PID-T1 Regler "));

		ImageIcon iconPI = new ImageIcon(Utility.loadResourceIcon("PI.png").getImage()
				.getScaledInstance(50, 25, java.awt.Image.SCALE_SMOOTH));
		btPI = new JToggleButton("PI", iconPI);
		btPI.setHorizontalTextPosition(AbstractButton.CENTER);
		btPI.setVerticalTextPosition(AbstractButton.TOP);
		btPI.addActionListener(this);

		ImageIcon iconPID = new ImageIcon(Utility.loadResourceIcon("PID.png").getImage()
				.getScaledInstance(50, 25, java.awt.Image.SCALE_SMOOTH));
		btPID = new JToggleButton("PID-T1", iconPID, true);
		btPID.addActionListener(this);
		btPID.setHorizontalTextPosition(AbstractButton.CENTER);
		btPID.setVerticalTextPosition(AbstractButton.TOP);

		bGroup = new ButtonGroup();
		bGroup.add(btPI);
		bGroup.add(btPID);

		add(btPI, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 51, 0));

		add(btPID, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 0, 10, 10), 51, 0));

		add(new JLabel(), new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
	}

	public void update(Observable obs, Object obj) {

	}

	/**
	 * Löst calculate() des guiControllers auf.
	 */
	public void actionPerformed(ActionEvent e) {
		guiController.calculate();
	}
}
