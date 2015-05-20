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
import java.text.DecimalFormat;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.fhnw.eit.pro2.gruppe4.model.ClosedLoop;
import ch.fhnw.eit.pro2.gruppe4.model.Controller;
import ch.fhnw.eit.pro2.gruppe4.model.Model;

public class RulesOfThumbPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private GUIController guiController;
	private DecimalFormat f = new DecimalFormat("#0.000");
	private JLabel[] lbMethod;
	private JLabel[] lbKr;
	private JLabel[] lbTn;
	private JLabel[] lbTv;

	public RulesOfThumbPanel(GUIController controller) {
		super(new GridBagLayout());
		this.guiController = controller;
		setBorder(MyBorderFactory.createMyBorder(" Faustformeln "));

		lbMethod = new JLabel[Controller.calculationTypName.length];
		lbKr = new JLabel[lbMethod.length];
		lbTn = new JLabel[lbMethod.length];
		lbTv = new JLabel[lbMethod.length];

		for (int i = 0; i < lbMethod.length; i++) {
			lbMethod[i] = new JLabel();
			add(lbMethod[i], new GridBagConstraints(0, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.LINE_START, GridBagConstraints.NONE,
					new Insets(5, 10, 5, 10), 0, 0));

			lbKr[i] = new JLabel();
			add(lbKr[i], new GridBagConstraints(1, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.LINE_START, GridBagConstraints.NONE,
					new Insets(5, 10, 5, 10), 0, 0));

			lbTn[i] = new JLabel();
			add(lbTn[i], new GridBagConstraints(2, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.LINE_START, GridBagConstraints.NONE,
					new Insets(5, 10, 5, 10), 0, 0));

			lbTv[i] = new JLabel();
			add(lbTv[i], new GridBagConstraints(3, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.LINE_START, GridBagConstraints.NONE,
					new Insets(5, 10, 5, 10), 0, 0));
		}

		add(new JLabel(), new GridBagConstraints(4, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));

		for (int i = 0; i < StepResponsePanel.plotColor.length - 3; i++) {
			lbMethod[i + 1].setForeground(StepResponsePanel.plotColor[i + 3]);
			lbKr[i + 1].setForeground(StepResponsePanel.plotColor[i + 3]);
			lbTn[i + 1].setForeground(StepResponsePanel.plotColor[i + 3]);
			lbTv[i + 1].setForeground(StepResponsePanel.plotColor[i + 3]);
		}

		// Erzeugt die erste Zeile für Kr, Tn, Tv.
		lbKr[0].setText("<html><i>K<sub>r</sub></html></i>");
		lbTn[0].setText("<html><i>T<sub>n</sub></html></i>");
		lbTv[0].setText("<html><i>T<sub>v</sub></html></i>");

		setInitialValues();
	}

	public void setInitialValues() {
		// Initialisierungswerte der Reglerausgabe.
		lbMethod[0]
				.setText("                                                                     ");

		for (int i = 0; i < Controller.calculationTypName.length - 1; i++) {
			lbMethod[i + 1].setText(Controller.calculationTypName[i + 1]);
			lbKr[i + 1].setText("0.000");
			lbTn[i + 1].setText("0.000");
			lbTv[i + 1].setText("0.000");
		}
	}

	public void update(Observable obs, Object obj) {
		Model model = (Model) obs;

		ClosedLoop[] closedLoop = model.getClosedLoop();

		for (int i = 0; i < closedLoop.length - 4; i++) {
			double[] controllerValues = closedLoop[i + 3].getController()
					.getControllerValues();
			lbMethod[i + 1].setText(""
					+ Controller.calculationTypName[(int) controllerValues[5]]);
			lbKr[i + 1]
					.setText(""
							+ f.format(Math
									.round((controllerValues[0]) * 1000.0) / 1000.0));
			lbTn[i + 1]
					.setText(""
							+ f.format(Math
									.round((controllerValues[1]) * 1000.0) / 1000.0));
			lbTv[i + 1]
					.setText(""
							+ f.format(Math
									.round((controllerValues[2]) * 1000.0) / 1000.0));
		}

		if (model.getClosedLoop()[0].getController().getControllerTyp() != Controller.PID) {
			for (int i = 0; i < lbTv.length; i++) {
				lbTv[i].setText("           ");
			}
		} else {
			lbTv[0].setText("<html><i>T<sub>v</sub></html></i>");

		}
	}

}
