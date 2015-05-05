package ch.fhnw.eit.pro2.gruppe4.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.apache.commons.math3.complex.Complex;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ch.fhnw.eit.pro2.gruppe4.utilities.Calc;


public class PhasenPlot extends JPanel {
	private static final long serialVersionUID = 2848069135069767765L;
	private JFreeChart chart = ChartFactory.createXYLineChart("",
			"Frequenz [Hz]", "Phase [rad]", null, PlotOrientation.VERTICAL,
			false, false, false);;
	
	private double[] x, y;
	

	public PhasenPlot() {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(300, 200));

		// Farben und Settings
		chart.setBackgroundPaint(Color.white);
		XYPlot xyplot = chart.getXYPlot();
		xyplot.setBackgroundPaint(Color.WHITE);
		xyplot.setRangeGridlinePaint(Color.black);
		xyplot.setDomainGridlinePaint(Color.black);
		xyplot.getRenderer().setSeriesPaint(0, Color.black);

		ValueAxis axis = xyplot.getDomainAxis();
		axis.setRange(0, 10);
		axis.setAutoRange(false);
		axis.setLabelPaint(Color.black);
		axis.setTickLabelPaint(Color.black);
		axis = xyplot.getRangeAxis();
		axis.setRange(-5, 5);
		axis.setAutoRange(false);
		axis.setLabelPaint(Color.black);
		axis.setTickLabelPaint(Color.black);

		ChartPanel panel = new ChartPanel(chart);
		add(panel);
		
		double[] b = new double[] {1,2,3,4,5};
		double[] a = new double[] {4,3,1,26,5};
		double[] f = new double[] {138,120,4,5};
		
		Complex[] array = Calc.freqs(b, a, f);
		System.out.println(array[1].abs()+"array");
	}

	public XYDataset createDataset(double[] x, double[] y) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		
		XYSeries series1 = new XYSeries("Object1");
	//	XYSeries series2 = new XYSeries("Object2");
		for (int i = 0; i < y.length; i++) {
			series1.add(x[i], y[i]);
		}
		
		
		dataset.addSeries(series1);
		//dataset.addSeries(series2);
		
		return dataset;
	}
	
//	public void addData(XYDataset dataset) {
//		
//		XYPlot xyplot = chart.getXYPlot();
//		XYDataset tmpDataset = xyplot;
//	
//	}
//	
	
	public void setData(double[] x, double[] y, int serie) {
		this.x = x;
		this.y = y;
		XYSeries series = new XYSeries("Phasengang");
		for (int i = 1; i < x.length; i++) {
			series.add(x[i], y[i]);
		}
		XYPlot xyplot = chart.getXYPlot();
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		xyplot.setDataset(dataset);
	}
}
