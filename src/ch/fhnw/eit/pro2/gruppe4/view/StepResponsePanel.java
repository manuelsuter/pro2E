package ch.fhnw.eit.pro2.gruppe4.view;

/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Observable;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.data.xy.XYDataset;

import ch.fhnw.eit.pro2.gruppe4.model.Controller;
import ch.fhnw.eit.pro2.gruppe4.model.Model;

public class StepResponsePanel extends JPanel implements ItemListener {

	private static final long serialVersionUID = 1L;
	private JCheckBox[] checkBox = new JCheckBox[7];
	private JPanel checkBoxPanel;
	private Plotter stepResponsePlot = new Plotter();
	private double[][][] ytValues;
	private static final Color firebrick = new Color(139, 26, 26);
	private static final Color tomato = new Color(240, 185, 37);
	private static final Color springgreen = new Color(102, 205, 0);
	private static final Color slategray = new Color(84, 84, 84);
	private static final Color tan = new Color(255, 52, 179);
	public static final Color[] plotColor = { firebrick, Color.RED, tomato, springgreen, Color.BLUE, slategray, tan };
	private XYDataset[] datasetArray = new XYDataset[7];

	/**
	 * Setzt Border-Layout.
	 * Platziert Plot und CheckBoxen.
	 * @param controller
	 */
	public StepResponsePanel(GUIController controller) {
		super(new BorderLayout());
		setBorder(MyBorderFactory.createMyBorder(" Schrittantworten "));

		// Plot dem Panel hinzufügen.
		add(stepResponsePlot, BorderLayout.CENTER);

		// includes the CheckBoxes below the plot on the checkBoxPanel
		checkBoxPanel = new JPanel(new GridLayout(2, 4));

		// Check-Box der Phasengangmethode.
		for (int i = 0; i < 3; i++) {
			checkBox[i] = new JCheckBox(Controller.calculationTypName[0], true);
			checkBox[i].setForeground(plotColor[i]);
			checkBoxPanel.add(checkBox[i]);
			checkBox[i].addItemListener(this);
		}
		// Bewirkt, dass alle Faustformeln auf zweiter Zeile sind.
		JLabel spaceLocker = new JLabel("");
		checkBoxPanel.add(spaceLocker);

		// CheckBoxen der Faustformeln.
		for (int i = 3; i < checkBox.length; i++) {
			checkBox[i] = new JCheckBox(Controller.calculationTypName[i-2], false);
			checkBox[i].setForeground(plotColor[i]);
			checkBoxPanel.add(checkBox[i]);
			checkBox[i].addItemListener(this);
		}

		// Fügt das checkBoxPanel dem BorderLayout hinzu.
		add(checkBoxPanel, BorderLayout.SOUTH);
	}

	/**
	 * Füllt die Datasets des Plots mit aktuellen Werten.
	 * Fügt die Datasets dem stepResponsePlot hinzu.
	 */
	private void plotStepResponse() {
		// Datasets erstellen und schreiben.
		for (int i = 0; i < datasetArray.length; i++) {
			datasetArray[i] = stepResponsePlot.createDataset(ytValues[i][1], ytValues[i][0]);
			stepResponsePlot.addData(i, datasetArray[i]);
		}
		// Farben den Datasets hinzufügen.
		for (int i = 0; i < plotColor.length; i++) {
			stepResponsePlot.setColor(i);
		}

		// Sichtbarkeit der Plots prüfen.
		for (int i = 0; i < checkBox.length; i++) {
			if (checkBox[i].isSelected() == false) {
				stepResponsePlot.rendererArray[i].setSeriesLinesVisible(0, false);
			} else {
				stepResponsePlot.rendererArray[i].setSeriesLinesVisible(0, true);
			}
		}
		stepResponsePlot.repaint();
	}
	
	/**
	 * Setzt die Plot-Sichtbarkeit auf false.
	 */
	public void setPlotInvisible() {
		for (int i = 0; i < stepResponsePlot.rendererArray.length; i++) {
			stepResponsePlot.rendererArray[i].setSeriesLinesVisible(0, false);
		}
	}
	/**
	 * Überschreibt alle datasets des stepResponsePlot mit null.
	 */
	public void deleteDatasets() {
		for (int i = 0; i < stepResponsePlot.rendererArray.length; i++) {
			stepResponsePlot.addData(i, null);
		}
	}

	/**
	 * Wird aktiviert bei Statusänderung einer CheckBox.
	 * Setzt die Sichtbarkeit der einzelnen Plots, je nach Status der jeweiligen CheckBox.
	 */
	public void itemStateChanged(ItemEvent e) {

		for (int i = 0; i < checkBox.length; i++) {
			if (checkBox[i].isSelected() == false) {
				stepResponsePlot.rendererArray[i].setSeriesLinesVisible(0, false);
			} else {
				stepResponsePlot.rendererArray[i].setSeriesLinesVisible(0, true);
			}
		}
	}


	/**
	 * Castet obs als Model und weisst es model zu.
	 * Erzeugt ein 3-dimensionales double-Array und speichert darin die aktuellen yt-Werte.
	 * @param obs
	 * @param obj
	 */
	public void update(Observable obs, Object obj) {
		Model model = (Model) obs;

		ytValues = new double[7][][];
		for (int i = 0; i < ytValues.length; i++) {
			ytValues[i] = model.getClosedLoop()[i].getStepResponse();
		}
		stepResponsePlot.setxAxis(ytValues[1][1][ytValues[1][1].length-1]);
		plotStepResponse();
	}

}
