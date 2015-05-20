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
	private JFreeChart chart = ChartFactory.createXYLineChart("", "Zeit t/s",
			"y(t)", null, PlotOrientation.VERTICAL, false, false, false);;
	public XYLineAndShapeRenderer[] rendererArray = new XYLineAndShapeRenderer[8];
	private XYPlot xyplot;

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
			xyplot.getRendererForDataset(xyplot.getDataset(i)).setSeriesPaint(
					0, Color.green);
		}

		for (int i = 0; i < rendererArray.length; i++) {
			xyplot.setRenderer(i, rendererArray[i]);
		}

		ValueAxis axis = xyplot.getDomainAxis();
		axis.setRange(0, 10);
		axis.setAutoRange(true);
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

	public XYDataset createDataset(double[] x, double[] y) {
		XYSeriesCollection dataset = new XYSeriesCollection();

		XYSeries series = new XYSeries("Object");
		for (int i = 0; i < y.length; i++) {
			series.add(x[i], y[i]);
		}
		dataset.addSeries(series);

		return dataset;
	}

	public void addData(int index, XYDataset dataset) {
		XYPlot xyplot = chart.getXYPlot();
		xyplot.setDataset(index, dataset);
	}

	public void setColor(int index) {
		xyplot.getRendererForDataset(xyplot.getDataset(index)).setSeriesPaint(
				0, StepResponsePanel.plotColor[index]);
	}

}
