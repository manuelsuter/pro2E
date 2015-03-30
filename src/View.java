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
import java.util.Observer;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class View extends JPanel implements Observer {
	
	private static final long serialVersionUID = 1L;
	public LeftPanel leftPanel;
	private RightPanel rightPanel;
	private JFrame frame;


	public View(Controller controller) {
		super(new GridBagLayout());

		
		// Construct the main left and right Panel
		leftPanel = new LeftPanel(controller);
		add(leftPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.PAGE_START, GridBagConstraints.NONE, new Insets(
						10, 10, 10, 10), 0, 0));
		
		rightPanel = new RightPanel(controller);
		add(rightPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.PAGE_START, GridBagConstraints.BOTH, new Insets(
						10, 10, 10, 10), 0, 0));
		}
	
	public void setJFrame(JFrame frame) {
		this.frame = frame;
		
	}

	public void setVisibility(boolean flag, Dimension dimension){
		rightPanel.setVisible(flag);
		frame.setSize(dimension);
		frame.setResizable(flag);
	}

	public void update(Observable obs, Object obj) {
		leftPanel.update(obs, obj);
		rightPanel.update(obs, obj);
	}
}


