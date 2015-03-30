/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Müller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;


public class View extends JPanel implements Observer {
	
	private static final long serialVersionUID = 1L;
	private ViewLeftPanel leftPanel;
	private ViewRightPanel rightPanel;


	public View(Controller controller) {
		super(new GridBagLayout());

		
		// Construct the main left and right Panel
		leftPanel = new ViewLeftPanel(controller);
		add(leftPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.PAGE_START, GridBagConstraints.NONE, new Insets(
						10, 10, 10, 10), 0, 0));
		
		rightPanel = new ViewRightPanel(controller);
		add(rightPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets(
						10, 10, 10, 10), 0, 0));
		

	}
	
	public void setVisibility(boolean flag){
		rightPanel.setVisible(flag);
		
	}

	public void update(Observable obs, Object obj) {
		leftPanel.update(obs, obj);
		rightPanel.update(obs, obj);
	}
}


