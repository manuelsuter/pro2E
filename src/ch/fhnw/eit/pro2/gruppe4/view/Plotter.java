/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * Authors: Manuel Suter
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */

package ch.fhnw.eit.pro2.gruppe4.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Plotter extends JPanel {
	private static final long serialVersionUID = 2848069135069767765L;
	private JFreeChart chart = ChartFactory.createXYLineChart("", "Zeit t/s", "y(t)", null, PlotOrientation.VERTICAL,
			false, false, false);;
	public XYLineAndShapeRenderer[] rendererArray = new XYLineAndShapeRenderer[8];
	private XYPlot xyplot;
	
	/**
	 * Setzt BorderLayout.
	 * Definiert die Plot-Voreinstellungen.
	 */
	public Plotter() {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(300, 200));

		for (int i = 0; i < rendererArray.length; i++) {
			rendererArray[i] = new XYLineAndShapeRenderer(true, false);
		}

		// Farben und Settings
		chart.setBackgroundPaint(Color.white);
		xyplot = chart.getXYPlot();
		xyplot.setBackgroundPaint(Color.WHITE);
		xyplot.setRangeGridlinePaint(Color.black);
		xyplot.setDomainGridlinePaint(Color.black);

		for (int i = 0; i < rendererArray.length; i++) {
			xyplot.getRendererForDataset(xyplot.getDataset(i)).setSeriesPaint(0, Color.green);
		}

		for (int i = 0; i < rendererArray.length; i++) {
			xyplot.setRenderer(i, rendererArray[i]);
		}

		ValueAxis axis = xyplot.getDomainAxis();
		axis.setRange(0, 20);
		axis.setAutoRange(false);
		axis.setLabelPaint(Color.black);
		axis.setTickLabelPaint(Color.black);
		axis = xyplot.getRangeAxis();
		axis.setRange(-5, 5);
		axis.setAutoRange(true);
		axis.setLabelPaint(Color.black);
		axis.setTickLabelPaint(Color.black);

		ChartPanel panel = new ChartPanel(chart);
		add(panel);
	}
	
	/**
	 * Erzeugt series aus x- und y-Werten und fügt sie dem dataset hinzu.
	 * @param x
	 * @param y
	 * @return dataset
	 */
	public XYDataset createDataset(double[] x, double[] y) {
		XYSeriesCollection dataset = new XYSeriesCollection();

		XYSeries series = new XYSeries("Object");
		for (int i = 0; i < y.length; i++) {
			series.add(x[i], y[i]);
		}
		dataset.addSeries(series);

		return dataset;
	}

	/**
	 * Fügt dem Plot das erzeugte dataset hinzu.
	 * @param index
	 * @param dataset
	 */
	public void addData(int index, XYDataset dataset) {
		XYPlot xyplot = chart.getXYPlot();
		xyplot.setDataset(index, dataset);
	}

	/**
	 * Setzt die Farbe des datasets auf dem Plot.
	 * @param index
	 */
	public void setColor(int index) {
		xyplot.getRendererForDataset(xyplot.getDataset(index)).setSeriesPaint(0, StepResponsePanel.plotColor[index]);
	}
	
	/**
	 * Setzt die x-Achse des Plots.
	 * @param xAxis
	 */
	public void setxAxis(double xAxis){		
		ValueAxis axis = xyplot.getDomainAxis();
		axis.setRange(0, xAxis);
	}

}
