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

import ch.fhnw.eit.pro2.gruppe4.model.ClosedLoop;
import ch.fhnw.eit.pro2.gruppe4.model.Controller;
import ch.fhnw.eit.pro2.gruppe4.model.Model;

public class RulesOfThumbPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JUnitLabel[] lbMethod;
	private JUnitLabel[] lbKr;
	private JUnitLabel[] lbTn;
	private JUnitLabel[] lbTv;
	public static final String[] unitName = { "a", "f", "p", "n", "u", "m", "", "k", "M", "G", "T", "P", "E" };

	public RulesOfThumbPanel(GUIController controller) {
		super(new GridBagLayout());
		setBorder(MyBorderFactory.createMyBorder(" Faustformeln "));

		lbMethod = new JUnitLabel[Controller.calculationTypName.length];
		lbKr = new JUnitLabel[lbMethod.length];
		lbTn = new JUnitLabel[lbMethod.length];
		lbTv = new JUnitLabel[lbMethod.length];

		for (int i = 0; i < lbMethod.length; i++) {
			lbMethod[i] = new JUnitLabel();
			add(lbMethod[i], new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
					GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));

			lbKr[i] = new JUnitLabel();
			add(lbKr[i], new GridBagConstraints(1, i, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
					GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));

			lbTn[i] = new JUnitLabel();
			add(lbTn[i], new GridBagConstraints(2, i, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
					GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));

			lbTv[i] = new JUnitLabel();
			add(lbTv[i], new GridBagConstraints(3, i, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
					GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));
		}

		add(new JUnitLabel(), new GridBagConstraints(4, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		for (int i = 0; i < StepResponsePanel.plotColor.length - 3; i++) {
			lbMethod[i + 1].setForeground(StepResponsePanel.plotColor[i + 3]);
			lbKr[i + 1].setForeground(StepResponsePanel.plotColor[i + 3]);
			lbTn[i + 1].setForeground(StepResponsePanel.plotColor[i + 3]);
			lbTv[i + 1].setForeground(StepResponsePanel.plotColor[i + 3]);
		}

		// Erzeugt die erste Zeile f�r Kr, Tn, Tv.
		lbKr[0].setText("<html><i>K<sub>r</sub></html></i>");
		lbTn[0].setText("<html><i>T<sub>n</sub></html></i>");
		lbTv[0].setText("<html><i>T<sub>v</sub></html></i>");

		setInitialValues();
	}

	public void setInitialValues() {
		// Initialisierungswerte der Reglerausgabe.
		lbMethod[0].setText("                                                                     ");

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
			double[] controllerValues = closedLoop[i + 3].getController().getControllerValues();
			lbMethod[i + 1].setText("" + Controller.calculationTypName[(int) controllerValues[5]]);
			
			lbKr[i + 1].setText(controllerValues[Controller.KrPOS]);
			lbTn[i + 1].setText(controllerValues[Controller.TnPOS],"s");
			lbTv[i + 1].setText(controllerValues[Controller.TvPOS],"s");
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
