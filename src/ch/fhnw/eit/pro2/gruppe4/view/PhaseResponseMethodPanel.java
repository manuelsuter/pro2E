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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.fhnw.eit.pro2.gruppe4.model.ClosedLoop;
import ch.fhnw.eit.pro2.gruppe4.model.Controller;
import ch.fhnw.eit.pro2.gruppe4.model.Model;

public class PhaseResponseMethodPanel extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;
	private GUIController guiController;
	private DecimalFormat f = new DecimalFormat("0.00");
	
	public JLabel[] lbMethod = new JLabel[4];
	private JLabel[] lbKr = new JLabel[lbMethod.length];
	private JLabel[] lbTn = new JLabel[lbMethod.length];
	private JLabel[] lbTv = new JLabel[lbMethod.length];
	public JDoubleTextField[] tfTp = new JDoubleTextField[3];
	private JLabel lbTp;
	public static final String[] unitName = { "a", "f", "p", "n", "u", "m", "", "k", "M", "G","T","P", "E"};


	public PhaseResponseMethodPanel(GUIController controller) {

		super(new GridBagLayout());
		this.guiController = controller;
		setBorder(MyBorderFactory.createMyBorder(" Phasengang-Methode "));

		// Baut die Ausgabe-Panel der Phasengangmethode:
		lbMethod[0] = new JLabel("Optimierung");
		lbMethod[1] = new JLabel("Positiv");
		lbMethod[2] = new JLabel("Standard");
		lbMethod[3] = new JLabel("Negativ");


		
		
		for (int i = 0; i < lbMethod.length; i++) {
			
			add(lbMethod[i], new GridBagConstraints(0, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.LINE_START, GridBagConstraints.NONE,
					new Insets(5, 10, 9, 10), 0, 0));

			lbKr[i] = new JLabel();
			add(lbKr[i], new GridBagConstraints(1, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.LINE_START, GridBagConstraints.NONE,
					new Insets(5, 10, 9, 10), 0, 0));

			lbTn[i] = new JLabel();
			add(lbTn[i], new GridBagConstraints(2, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.LINE_START, GridBagConstraints.NONE,
					new Insets(5, 10, 9, 10), 0, 0));

			lbTv[i] = new JLabel();
			add(lbTv[i], new GridBagConstraints(3, i, 1, 1, 0.0, 0.0,
					GridBagConstraints.LINE_START, GridBagConstraints.NONE,
					new Insets(5, 10, 9, 10), 0, 0));
		}
		for (int i = 0; i < tfTp.length; i++) {
			tfTp[i] = new JDoubleTextField("", 5, false);
			tfTp[i].addKeyListener(this);
			add(tfTp[i], new GridBagConstraints(4, i + 1, 3, 1, 1.0, 0.0,
					GridBagConstraints.LINE_START,
					GridBagConstraints.HORIZONTAL, new Insets(5, 10, 5, 10), 0,
					0));
		}

		lbKr[0].setText("<html><i>K<sub>r</sub></html></i>");
		lbTn[0].setText("<html><i>T<sub>n</sub></html></i>");
		lbTv[0].setText("<html><i>T<sub>v</sub></html></i>");

		add(lbTp = new JLabel("<html><i>T<sub>p</sub></i></html>"),
				new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.LINE_START, GridBagConstraints.NONE,
						new Insets(5, 10, 9, 10), 0, 0));

		add(new JLabel(), new GridBagConstraints(5, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));

		// Initialisierungswerte der Reglerausgabe.
		setInitialValues();
	}

	public void setInitialValues() {
		for (int i = 0; i < lbMethod.length - 1; i++) {
			lbKr[i + 1].setText("0.000");
			lbTn[i + 1].setText("0.000");
			lbTv[i + 1].setText("0.000");
			tfTp[i].setText("");
		}

		for (int i = 0; i < lbMethod.length - 1; i++) {
			lbMethod[i + 1].setForeground(StepResponsePanel.plotColor[i]);
			lbKr[i + 1].setForeground(StepResponsePanel.plotColor[i]);
			lbTn[i + 1].setForeground(StepResponsePanel.plotColor[i]);
			lbTv[i + 1].setForeground(StepResponsePanel.plotColor[i]);
		}
	}


	public void update(Observable obs, Object obj) {
		Model model = (Model) obs;

		ClosedLoop[] closedLoop = model.getClosedLoop();
		
		// Einheiten-Index berechnen für Tn/Tv
				double controllerValueTnTv = closedLoop[0].getController().getControllerValues()[1];
				int unitIndexTnTv = 0;
				if (controllerValueTnTv < 1.00) {
					while (controllerValueTnTv < 1.00) {
						controllerValueTnTv = controllerValueTnTv * 1000.0;
						unitIndexTnTv++;
					}
				} else if (controllerValueTnTv > 100.0) {
					while (controllerValueTnTv > 100.0) {
						controllerValueTnTv = controllerValueTnTv / 1000.0;
						unitIndexTnTv--;
					}	
				}

				// Einheiten-Index berechnen für Kr
						double controllerValueKr = closedLoop[0].getController().getControllerValues()[0];
						int unitIndexKr = 0;
						if (controllerValueKr < 1.00) {
							while (controllerValueKr < 1.00) {
								controllerValueKr = controllerValueKr * 1000.0;
								unitIndexKr++;
							}
						} else if (controllerValueKr > 100.0) {
							while (controllerValueKr > 100.0) {
								controllerValueKr = controllerValueKr / 1000.0;
								unitIndexKr--;
							}	
						}
		// Holt die jeweiligen ClosedLoops in die Methode.
		// Setzt die aktuellen Werte auf die Labels und Textfelder.
		for (int i = 0; i < lbMethod.length - 1; i++) {
			double[] controllerValues = closedLoop[i].getController()
					.getControllerValues();
			lbKr[i + 1]
					.setText(""
							+ f.format(Math
									.ceil((controllerValues[Controller.KrPOS]) * 1000.0*Math.pow(1000,unitIndexKr)) / 1000.0)+" "+unitName[-unitIndexKr+6]+"s");
			lbTn[i + 1]
					.setText(""
							+ f.format(Math
									.ceil((controllerValues[Controller.TnPOS]) * 1000.0*Math.pow(1000,unitIndexTnTv)) / 1000.0)+" "+unitName[-unitIndexTnTv+6]+"s");
			lbTv[i + 1]
					.setText(""
							+ f.format(Math
									.ceil((controllerValues[Controller.TvPOS]) * 1000.0*Math.pow(1000,unitIndexTnTv)) / 1000.0)+" "+unitName[-unitIndexTnTv+6]+"s");
		}
		for (int i = 0; i < lbMethod.length - 1; i++) {
			tfTp[i].setText(""
					+ Math.ceil((closedLoop[i].getController()
							.getControllerValues()[Controller.TpPOS]) * 1000.0)
					/ 1000.0);
		}
		

		if (model.getClosedLoop()[0].getController().getControllerTyp() != Controller.PID) {
			for (int i = 0; i < lbTv.length; i++) {
				lbTv[i].setText("                 ");
			}
			tfTp[0].setVisible(false);
			tfTp[1].setVisible(false);
			tfTp[2].setVisible(false);

			lbTp.setForeground(getBackground());
		} else {
			lbTv[0].setText("<html><i>T<sub>v</sub></html></i>");
			tfTp[0].setVisible(true);
			tfTp[1].setVisible(true);
			tfTp[2].setVisible(true);
			lbTp.setForeground(Color.BLACK);
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) {
			if (e.getSource() == tfTp[0]) {
				guiController.setTp(0);
			} else if (e.getSource() == tfTp[1]) {
				guiController.setTp(1);
			} else if (e.getSource() == tfTp[2]) {
				guiController.setTp(2);
			}
		}

	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

}
