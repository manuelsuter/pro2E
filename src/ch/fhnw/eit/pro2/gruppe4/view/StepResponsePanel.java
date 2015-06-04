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
import javax.swing.JPanel;

import org.jfree.data.xy.XYDataset;

import ch.fhnw.eit.pro2.gruppe4.model.Controller;
import ch.fhnw.eit.pro2.gruppe4.model.Model;

public class StepResponsePanel extends JPanel implements ItemListener {

	private static final long serialVersionUID = 1L;
	private JCheckBox[] checkBox = new JCheckBox[Controller.calculationTypName.length];
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
	 * Setzt das Layout auf ein BorderLayout.
	 * Platziert den Plot-Bereich im Zentrum.
	 * Erszeugt ein checkBoxPanel mit all den CheckBoxen f�r Plot, das im S�den platziert wird.
	 * @param controller
	 */
	public StepResponsePanel(GUIController controller) {
		super(new BorderLayout());
		setBorder(MyBorderFactory.createMyBorder(" Schrittantworten "));

		// Plot dem Panel hinzuf�gen.
		add(stepResponsePlot, BorderLayout.CENTER);

		// includes the CheckBoxes below the plot on the checkBoxPanel
		checkBoxPanel = new JPanel(new GridLayout(2, 4));

		// Check-Box der Phasengangmethode.
		checkBox[0] = new JCheckBox(Controller.calculationTypName[0], true);
		checkBox[0].setForeground(plotColor[1]);
		checkBoxPanel.add(checkBox[0]);
		checkBox[0].addItemListener(this);

		// CheckBoxen der Faustformeln.
		for (int i = 1; i < Controller.calculationTypName.length; i++) {
			checkBox[i] = new JCheckBox(Controller.calculationTypName[i], false);
			checkBox[i].setForeground(plotColor[i + 2]);
			checkBoxPanel.add(checkBox[i]);
			checkBox[i].addItemListener(this);
		}

		// F�gt das checkBoxPanel dem BorderLayout hinzu.
		add(checkBoxPanel, BorderLayout.SOUTH);
	}

	/**
	 * F�llt die Datasets des Plots mit aktuellen Werten.
	 * F�gt die Datasets dem stepResponsePlot hinzu.
	 */
	private void plotStepResponse() {
		// Datasets erstellen und schreiben.
		for (int i = 0; i < datasetArray.length; i++) {
			datasetArray[i] = stepResponsePlot.createDataset(ytValues[i][1], ytValues[i][0]);
			stepResponsePlot.addData(i, datasetArray[i]);
		}
		// Farben den Datasets hinzuf�gen.
		for (int i = 0; i < plotColor.length; i++) {
			stepResponsePlot.setColor(i);
		}

		// Sichtbarkeit der Faustformel pr�fen.
		if (checkBox[0].isSelected() == false) {
			stepResponsePlot.rendererArray[0].setSeriesLinesVisible(0, false);
			stepResponsePlot.rendererArray[1].setSeriesLinesVisible(0, false);
			stepResponsePlot.rendererArray[2].setSeriesLinesVisible(0, false);
		} else {
			stepResponsePlot.rendererArray[0].setSeriesLinesVisible(0, true);
			stepResponsePlot.rendererArray[1].setSeriesLinesVisible(0, true);
			stepResponsePlot.rendererArray[2].setSeriesLinesVisible(0, true);
		}

		// Sichtbarkeit der Faustformeln-Plots Plot pr�fen.
		for (int i = 1; i < checkBox.length; i++) {
			if (checkBox[i].isSelected() == false) {
				stepResponsePlot.rendererArray[i + 2].setSeriesLinesVisible(0, false);
			} else {
				stepResponsePlot.rendererArray[i + 2].setSeriesLinesVisible(0, true);
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
	 * �berschreibt alle datasets des stepResponsePlot mit null.
	 */
	public void deleteDatasets() {
		for (int i = 0; i < stepResponsePlot.rendererArray.length; i++) {
			stepResponsePlot.addData(i, null);
		}
	}

	public void itemStateChanged(ItemEvent e) {

		if (checkBox[0].isSelected() == false) {
			stepResponsePlot.rendererArray[0].setSeriesLinesVisible(0, false);
			stepResponsePlot.rendererArray[1].setSeriesLinesVisible(0, false);
			stepResponsePlot.rendererArray[2].setSeriesLinesVisible(0, false);
		} else {
			stepResponsePlot.rendererArray[0].setSeriesLinesVisible(0, true);
			stepResponsePlot.rendererArray[1].setSeriesLinesVisible(0, true);
			stepResponsePlot.rendererArray[2].setSeriesLinesVisible(0, true);
		}

		for (int i = 1; i < checkBox.length; i++) {
			if (checkBox[i].isSelected() == false) {
				stepResponsePlot.rendererArray[i + 2].setSeriesLinesVisible(0, false);
			} else {
				stepResponsePlot.rendererArray[i + 2].setSeriesLinesVisible(0, true);
			}
		}
	}


	/**
	 * Castet obs als Model und weisst es model zu.
	 * Erzeugt ein 3-dimensionales double-Array und speichert darin die aktuellen 
	 * @param obs
	 * @param obj
	 */
	public void update(Observable obs, Object obj) {
		Model model = (Model) obs;

		ytValues = new double[8][][];
		for (int i = 0; i < ytValues.length; i++) {
			ytValues[i] = model.getClosedLoop()[i].getStepResponse();
		}
		plotStepResponse();
	}

}
