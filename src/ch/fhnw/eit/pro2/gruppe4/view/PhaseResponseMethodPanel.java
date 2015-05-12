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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.fhnw.eit.pro2.gruppe4.model.ClosedLoop;
import ch.fhnw.eit.pro2.gruppe4.model.Controller;
import ch.fhnw.eit.pro2.gruppe4.model.Model;
import ch.fhnw.eit.pro2.gruppe4.model.PhaseResponseMethod;


public class PhaseResponseMethodPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private GUIController guiController;
	public JDoubleTextField tfTp;
	private DecimalFormat f = new DecimalFormat("#0.000");
	private DecimalFormat f2 = new DecimalFormat("#0");

	private String[] methodDesignation = {"Phasenrand                                        ", "wenig", "mittel", "stark"};
	private JLabel[] lbMethod = new JLabel[methodDesignation.length];
	private JLabel[] lbKr = new JLabel[lbMethod.length];
	private JLabel[] lbTn = new JLabel[lbMethod.length];
	private JLabel[] lbTv = new JLabel[lbMethod.length];
	private JLabel lbTp;

	
	public PhaseResponseMethodPanel(GUIController controller) {
	
		super(new GridBagLayout());
		this.guiController = controller;
		setBorder(MyBorderFactory.createMyBorder(" Phasengang-Methode "));
		
		for (int i = 0; i < lbMethod.length; i++) {
			lbMethod[i] = new JLabel(methodDesignation[i]);
			add(lbMethod[i], new GridBagConstraints(0, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
							5, 10, 5, 10), 0, 0));
			
			lbKr[i] = new JLabel();
			add(lbKr[i], new GridBagConstraints(1, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
							5, 10, 5, 10), 0, 0));
			
			lbTn[i] = new JLabel();
			add(lbTn[i], new GridBagConstraints(2, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
							5, 10, 5, 10), 0, 0));
			
			lbTv[i] = new JLabel();
			add(lbTv[i], new GridBagConstraints(3, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
							5, 10, 5, 10), 0, 0));
		}
		lbKr[0].setText("<html><i>K<sub>r</sub></html></i>");
		lbTn[0].setText("<html><i>T<sub>n</sub></html></i>");
		lbTv[0].setText("<html><i>T<sub>v</sub></html></i>");
		
		
		add(lbTp = new JLabel("<html><i>T<sub>p</sub></i></html>"), new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
						5, 10, 5, 10), 0, 0));
		
		tfTp = new JDoubleTextField("",170,false);
		add(tfTp, new GridBagConstraints(1, 4, 3, 1, 1.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						5, 10, 5, 10), 0, 0));
		tfTp.addActionListener(this);
		
		// Initialisierungswerte der Reglerausgabe.
		setInitialValues();
	}
	
	public void setInitialValues(){
		for (int i = 0; i < methodDesignation.length-1; i++) {			
			lbMethod[i+1].setText(""+f2.format(Math.round(PhaseResponseMethod.PHASEMARGINPID/(2*Math.PI)*180*10.0/10.0))+"�");
			lbKr[i+1].setText("0.000");
			lbTn[i+1].setText("0.000");
			lbTv[i+1].setText("0.000");
		}
		tfTp.setText("0.000");
		
		for (int i = 0; i < methodDesignation.length-1; i++) {
			lbMethod[i+1].setForeground(StepResponsePanel.plotColor[i]);
			lbKr[i+1].setForeground(StepResponsePanel.plotColor[i]);
			lbTn[i+1].setForeground(StepResponsePanel.plotColor[i]);
			lbTv[i+1].setForeground(StepResponsePanel.plotColor[i]);
		}
	}
	

	public void update(Observable obs, Object obj) {
		Model model = (Model)obs;
		
			ClosedLoop[] closedLoop = model.getClosedLoop();
			//TODO: Gut fragen ob Label nicht direkt double verarbeiten kann
			for (int i = 0; i < methodDesignation.length-1; i++) {
				double[] controllerValues = closedLoop[i].getController().getControllerValues();
				lbKr[i+1].setText(""+f.format(Math.round((controllerValues[0])*1000.0)/1000.0));
				lbTn[i+1].setText(""+f.format(Math.round((controllerValues[1])*1000.0)/1000.0));
				lbTv[i+1].setText(""+f.format(Math.round((controllerValues[2])*1000.0)/1000.0));

				tfTp.setText(""+Math.round((controllerValues[3])*1000.0)/1000.0);
			}	
			
			lbMethod[1].setText(""+f2.format(Math.round(model.getClosedLoop()[0].getController().getPhaseMargin()/(2*Math.PI)*180*1000.0/1000.0))+"�");
			lbMethod[3].setText(""+f2.format(Math.round(model.getClosedLoop()[2].getController().getPhaseMargin()/(2*Math.PI)*180*1000.0/1000.0))+"�");

			
			if (model.getClosedLoop()[0].getController().getControllerTyp() != Controller.PID) {
				for (int i = 0; i < lbTv.length; i++) {
					lbTv[i].setText("                 ");
				}
				tfTp.setVisible(false);
				lbTp.setForeground(getBackground());
				lbMethod[2].setText(""+f2.format(Math.round(PhaseResponseMethod.PHASEMARGINPI/(2*Math.PI)*180*10.0/10.0))+"�");

			}
			else{
				lbTv[0].setText("<html><i>T<sub>v</sub></html></i>");
				tfTp.setVisible(true);
				lbTp.setForeground(Color.BLACK);
				lbMethod[2].setText(""+f2.format(Math.round(PhaseResponseMethod.PHASEMARGINPID/(2*Math.PI)*180*10.0/10.0))+"�");

			}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == tfTp)
		guiController.setTp();
	}
}
