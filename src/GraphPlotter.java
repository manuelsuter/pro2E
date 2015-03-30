/*
 * Copyright (c) 2014 Mueller Fabian, Duerner Daniel, Risi Julian, Walzer Ken,
 * Mijnssen Raphael, Pluess Jonas
 * 
 * Authors: Pluess Jonas
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
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GraphPlotter extends JPanel implements MouseWheelListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private String xLabel, yLabel;
	private boolean gridOn = false, allowNegativeXValues = true, allowNegativeYValues = true;
	private Color gridColor = Color.lightGray;
	
	//West and South Spaces
	private int topInset = 30, leftInset = 60, bottomInset = 50, rightInset = 30;
	private int width, height, innerWidth, innerHeight;
	private double xMin = 0.0, xMax = 1.0, yMin = 0.0, yMax = 1.0;
	private double xMinHard = -1.0 / 0.0, xMaxHard = 1.0 / 0.0, yMinHard = -1.0 / 0.0, yMaxHard = 1.0 / 0.0;
	private double[] xTicks = {}, yTicks = {};
	private double xTickDelta = 0.0, yTickDelta = 0.0;
	private Line[] lines = new Line[0];
	private JDoubleTextField tfXMin = new JDoubleTextField("", 5, false),
			tfXMiddle = new JDoubleTextField("", 5, false), tfXMax = new JDoubleTextField("", 5, false);
	private JDoubleTextField tfYMin = new JDoubleTextField("", 5, false),
			tfYMiddle = new JDoubleTextField("", 5, false), tfYMax = new JDoubleTextField("", 5, false);
	private boolean bXMin = false, bXMiddle = false, bXMax = false, bYMin = false, bYMiddle = false, bYMax = false;
	private double xMiddleValue = (xMax - xMin) / 2.0, yMiddleValue = (yMax - yMin) / 2.0;
	private double lastScrollEvent;

	public static final int LINE = 0, DOTS = 1, LineAndDots = 2;// BrokenLine =
																// 3;

	public GraphPlotter(String xLabel, String yLabel, boolean gridOn) {
		super.setLayout(null);
		setOpaque(false);
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent arg0) {
				resize();
				repaint();
			}
		});
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.gridOn = gridOn;

		tfXMiddle.setEditable(false);
		tfXMiddle.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		tfYMiddle.setEditable(false);
		tfYMiddle.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		// Add action listeners
		tfXMin.addActionListener(this);
		tfXMax.addActionListener(this);
		tfYMin.addActionListener(this);
		tfYMax.addActionListener(this);
		// Add mouse wheel listeners
		tfXMin.addMouseWheelListener(this);
		tfXMiddle.addMouseWheelListener(this);
		tfXMax.addMouseWheelListener(this);
		tfYMin.addMouseWheelListener(this);
		tfYMiddle.addMouseWheelListener(this);
		tfYMax.addMouseWheelListener(this);
	}

	/**
	 * Sets the hard range of the plot area. It's not possible to scroll out of
	 * this range or set a range bigger than the hard range.
	 * 
	 * @param hardRange
	 *            Double array with xMinHard, xMaxHard, yMinHard, yMaxHard
	 */
	public void setHardRange(double[] hardRange) {
		if (hardRange == null || hardRange.length < 4) {
			return;
		}
		setHardRange(hardRange[0], hardRange[1], hardRange[2], hardRange[3]);
	}

	/**
	 * Sets the hard range of the plot area. It's not possible to scroll out of
	 * this range or set a range bigger than the hard range.
	 * 
	 * @param xMinHard
	 *            Double xMinHard
	 * @param xMaxHard
	 *            Double xMaxHard
	 * @param yMinHard
	 *            Double yMinHard
	 * @param yMaxHard
	 *            Double yMaxHard
	 */
	public void setHardRange(double xMinHard, double xMaxHard, double yMinHard, double yMaxHard) {
		this.xMinHard = xMinHard;
		this.xMaxHard = xMaxHard;
		this.yMinHard = yMinHard;
		this.yMaxHard = yMaxHard;
		checkRange();
		resize();
	}

	/**
	 * Allows negative x values. If it is disallowed, no x-ticks will be shown.
	 * 
	 * @param b
	 */
	public void allowNegativeXValues(boolean b) {
		allowNegativeXValues = b;
	}

	/**
	 * Allows negative y values. If it is disallowed, no y-ticks will be shown.
	 * 
	 * @param b
	 */
	public void allowNegativeYValues(boolean b) {
		allowNegativeYValues = b;
	}

	/**
	 * Set the visible range of the plot. The plot between this values will be
	 * shown.
	 * 
	 * @param range
	 *            Double array with xMin, xMax, yMin, yMax
	 */
	public void setRange(double[] range) {
		if (range == null || range.length < 4) {
			return;
		}
		setRange(range[0], range[1], range[2], range[3]);
	}

	/**
	 * Set the visible range of the plot. The plot between this values will be
	 * shown.
	 * 
	 * @param xMin
	 *            Double xMin
	 * @param xMax
	 *            Double xMax
	 * @param yMin
	 *            Double yMin
	 * @param yMax
	 *            Double yMax
	 */
	public void setRange(double xMin, double xMax, double yMin, double yMax) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		checkRange();
		resize();
	}

	/**
	 * Add two textfields to change the value of xMax and xMin
	 */
	public void addXMaxAndMinScaleField() {
		removeAllXScaleFields();
		add(tfXMin);
		add(tfXMax);
		bXMin = true;
		bXMax = true;
	}

	/**
	 * Add a textfield to change the value of xMin
	 */
	public void addXMinScaleField() {
		removeAllXScaleFields();
		add(tfXMin);
		bXMin = true;
	}

	/**
	 * Add a textfield to change the value of xMax
	 */
	public void addXMaxScaleField() {
		removeAllXScaleFields();
		add(tfXMax);
		bXMax = true;
	}

	/**
	 * Add a textfield to change the value of xMin and xMax from the center
	 * (zooming)
	 * 
	 * @param atPoint
	 *            Adds the textfield with this value in the x-axis
	 */
	public void addXMiddleScaleFieldAt(double atPoint) {
		removeAllXScaleFields();
		xMiddleValue = atPoint;
		tfXMiddle.setText(formatValue(atPoint));
		add(tfXMiddle);
		bXMiddle = true;
		checkRange();
	}

	/**
	 * Add two textfields to change the value of yMax and yMin
	 */
	public void addYMaxAndMinScaleField() {
		removeAllYScaleFields();
		add(tfYMin);
		add(tfYMax);
		bYMin = true;
		bYMax = true;
	}

	/**
	 * Add a textfield to change the value of yMin
	 */
	public void addYMinScaleField() {
		removeAllYScaleFields();
		add(tfYMin);
		bYMin = true;
	}

	/**
	 * Add a textfield to change the value of yMax
	 */
	public void addYMaxScaleField() {
		removeAllYScaleFields();
		add(tfYMax);
		bYMax = true;
	}

	/**
	 * Add a textfield to change the value of yMin and yMax from the center
	 * (zooming)
	 * 
	 * @param atPoint
	 *            Adds the textfield with this value in the y-axis
	 */
	public void addYMiddleScaleFieldAt(double atPoint) {
		removeAllYScaleFields();
		yMiddleValue = atPoint;
		tfYMiddle.setText(formatValue(atPoint));
		add(tfYMiddle);
		bYMiddle = true;
		checkRange();
	}

	/**
	 * Remove all x scale fields
	 */
	public void removeAllXScaleFields() {
		if (bXMax) {
			remove(tfXMax);
			bXMax = false;
		}
		if (bXMiddle) {
			remove(tfXMiddle);
			bXMiddle = false;
		}
		if (bXMin) {
			remove(tfXMin);
			bXMin = false;
		}
	}

	/**
	 * Remove all y scale fields
	 */
	public void removeAllYScaleFields() {
		if (bYMax) {
			remove(tfYMax);
			bYMax = false;
		}
		if (bYMiddle) {
			remove(tfYMiddle);
			bYMiddle = false;
		}
		if (bYMin) {
			remove(tfYMin);
			bYMin = false;
		}
	}

	/**
	 * Set values to plot. All previous added data will be deleted.
	 * 
	 * @param xValues
	 *            Double array with the x-axis
	 * @param yValues
	 *            Double array with more than one line. All lines have the same
	 *            x-axis
	 * @param lineType
	 *            Integer of the line type (this.LINE, this.DOTS,
	 *            this.LineAndDots)
	 * @param color
	 *            Color of the line
	 * @param lineWeight
	 *            Weight in pixels of the line
	 * @param text
	 *            Line label to show
	 */
	public void setData(double[] xValues, double[][] yValues, int lineType, Color color, int lineWeight, String text) {
		removeAllLines();
		if (xValues == null || xValues.length == 0 || yValues == null || yValues.length == 0) {
			repaint();
			return;
		}
		addLines(xValues, yValues, lineType, color, lineWeight, text);
		resize();
		repaint();
	}

	/**
	 * Set values to plot. All previous added data will be deleted.
	 * 
	 * @param xValues
	 *            Double array with the x-axis
	 * @param yValues
	 *            Double array with one line
	 * @param lineType
	 *            Integer of the line type (this.LINE, this.DOTS,
	 *            this.LineAndDots)
	 * @param color
	 *            Color of the line
	 * @param lineWeight
	 *            Weight in pixels of the line
	 * @param text
	 *            Line label to show
	 */
	public void setData(double[] xValues, double[] yValues, int lineType, Color color, int lineWeight, String text) {
		removeAllLines();
		if (xValues == null || xValues.length == 0 || yValues == null || yValues.length == 0) {
			repaint();
			return;
		}
		addLines(xValues, new double[][] { yValues }, lineType, color, lineWeight, text);
		resize();
		repaint();
	}

	/**
	 * * Add values to plot. All previous added data will be plotted too.
	 * 
	 * @param xValues
	 *            Double array with the x-axis
	 * @param yValues
	 *            Double array with more than one line. All lines have the same
	 *            x-axis
	 * @param lineType
	 *            Integer of the line type (this.LINE, this.DOTS,
	 *            this.LineAndDots)
	 * @param color
	 *            Color of the line
	 * @param lineWeight
	 *            Weight in pixels of the line
	 * @param text
	 *            Line label to show
	 */
	public void addData(double[] xValues, double[][] yValues, int lineType, Color color, int lineWeight, String text) {
		if (xValues == null || xValues.length == 0 || yValues == null || yValues.length == 0) {
			repaint();
			return;
		}
		addLines(xValues, yValues, lineType, color, lineWeight, text);
		resize();
		repaint();
	}

	/**
	 * Add values to plot. All previous added data will be plotted too.
	 * 
	 * @param xValues
	 *            Double array with the x-axis
	 * @param yValues
	 *            Double array with one line
	 * @param lineType
	 *            Integer of the line type (this.LINE, this.DOTS,
	 *            this.LineAndDots)
	 * @param color
	 *            Color of the line
	 * @param lineWeight
	 *            Weight in pixels of the line
	 * @param text
	 *            Line label to show
	 */
	public void addData(double[] xValues, double[] yValues, int lineType, Color color, int lineWeight, String text) {
		if (xValues == null || xValues.length == 0 || yValues == null || yValues.length == 0) {
			repaint();
			return;
		}
		addLines(xValues, new double[][] { yValues }, lineType, color, lineWeight, text);
		resize();
		repaint();
	}

	/**
	 * Removes all lines from the plot. It does not remove the scaling text
	 * fields.
	 */
	public void removeAllData() {
		removeAllLines();
		resize();
		repaint();
	}

	// add lines to the plot
	private void addLines(double[] xValues, double[][] yValues, int lineType, Color color, int lineWeight,
			String lineText) {
		Line[] temp = lines;
		removeAllLines();
		lines = new Line[temp.length + yValues.length];
		int index = 1;
		for (int i = 0; i < lines.length; i++) {
			if (i < yValues.length) {
				lines[i] = new Line();
				lines[i].setData(xValues, yValues[i]);
				lines[i].setColor(color);
				lines[i].setLineType(lineType);
				lines[i].setLineWeight(lineWeight);
				lines[i].setLineText(lineText);
				if (!lineText.equals("")) {
					lines[i].setLabelIndex(index);
					index++;
				}
			} else {
				lines[i] = temp[i - yValues.length];
				if (!temp[i - yValues.length].getLineText().equals("")) {
					lines[i].setLabelIndex(index);
					index++;
				}
			}
			add(lines[i]);
		}
	}

	// remove all lines of the plot
	private void removeAllLines() {
		for (int i = 0; i < lines.length; i++) {
			remove(lines[i]);
		}
		lines = new Line[0];
	}

	// resizes all components
	private void resize() {
		width = getWidth();
		height = getHeight();
		innerWidth = width - leftInset - rightInset;
		innerHeight = height - topInset - bottomInset;
		for (int i = 0; i < lines.length; i++) {
			lines[i].setRange(xMin, xMax, yMin, yMax);
			lines[i].setBounds(leftInset, topInset, innerWidth, innerHeight);
			lines[i].resize();
		}

		// graduation algorithm
		// x graduation
		int xTickCount = 0;
		if (innerWidth != 0) {
			xTickCount = Math.abs(innerWidth / 50);
		}
		xTickDelta = (xMax - xMin) / (double) (xTickCount - 1.0);
		double xExponent = Math.ceil(Math.log10(xTickDelta)) - 1.0;
		xTickDelta = Math.ceil(xTickDelta / Math.pow(10, xExponent)) * Math.pow(10, xExponent);
		double xLowestTick = xTickDelta * Math.round(xMin / xTickDelta);
		xTicks = new double[xTickCount];
		for (int i = 0; i < xTicks.length; i++) {
			xTicks[i] = xLowestTick + xTickDelta * i;
		}
		// y graduation
		int yTickCount = 0;
		if (innerHeight != 0) {
			yTickCount = Math.abs(innerHeight / 40);
		}
		yTickDelta = (yMax - yMin) / (double) (yTickCount - 1.0);
		double yExponent = Math.ceil(Math.log10(yTickDelta)) - 1.0;
		yTickDelta = Math.ceil(yTickDelta / Math.pow(10, yExponent)) * Math.pow(10, yExponent);
		double yLowestTick = yTickDelta * Math.round(yMin / yTickDelta);
		yTicks = new double[yTickCount];
		for (int i = 0; i < yTicks.length; i++) {
			yTicks[i] = yLowestTick + yTickDelta * i;
		}

		// set bounds of scale fields
		tfXMin.setBounds(leftInset, topInset + innerHeight + 6, 55, 20);
		tfXMiddle.setBounds(leftInset + innerWidth / 2 - 27, topInset + innerHeight + 6, 55, 20);
		tfXMax.setBounds(width - rightInset - 55, topInset + innerHeight + 6, 55, 20);
		tfYMin.setBounds(leftInset - 55 - 4, topInset + innerHeight - 20, 55, 20);
		tfYMiddle.setBounds(leftInset - 55 - 4, topInset + innerHeight / 2 - 10, 55, 20);
		tfYMax.setBounds(leftInset - 55 - 4, topInset, 55, 20);
		// set text of scale fields
		tfXMin.setText(formatScaleFieldValue(xMin));
		tfXMax.setText(formatScaleFieldValue(xMax));
		tfYMin.setText(formatScaleFieldValue(yMin));
		tfYMax.setText(formatScaleFieldValue(yMax));
	}

	private String formatValue(double value) {
		String formattedValue = "";
		if ((Math.abs(value) < 100.0 && Math.abs(value) > 0.001) || value == 0.0) {
			DecimalFormat df = new DecimalFormat("#.###");
			formattedValue = df.format(value).replaceAll("//.000$", "");
		} else {
			DecimalFormat df = new DecimalFormat("#.##E0");
			formattedValue = df.format(value);
		}
		return formattedValue;
	}

	private String formatScaleFieldValue(double value) {
		String formattedValue = "";
		if ((Math.abs(value) < 100.0 && Math.abs(value) > 0.001) || value == 0.0) {
			DecimalFormat df = new DecimalFormat("#.##");
			formattedValue = df.format(value).replaceAll("//.00$", "");
		} else {
			DecimalFormat df = new DecimalFormat("#.#E0");
			formattedValue = df.format(value);
		}
		return formattedValue;
	}

	private void checkRange() {
		if (xMin < xMinHard) {
			xMin = xMinHard;
		}
		if (xMax > xMaxHard) {
			xMax = xMaxHard;
		}
		if (yMin < yMinHard) {
			yMin = yMinHard;
		}
		if (yMax > yMaxHard) {
			yMax = yMaxHard;
		}
		if (bXMiddle) {
			if ((xMiddleValue - xMin) <= (xMax - xMiddleValue)) {
				xMin = xMiddleValue - (xMax - xMiddleValue);
			} else {
				xMax = xMiddleValue + (xMiddleValue - xMin);
			}
		}
		if (bYMiddle) {
			if ((yMiddleValue - yMin) <= (yMax - yMiddleValue)) {
				yMin = yMiddleValue - (yMax - yMiddleValue);
			} else {
				yMax = yMiddleValue + (yMiddleValue - yMin);
			}
		}
	}

	/**
	 * Paints the axis with arrows, the grid and the graduation of the plot.
	 * Graduation is calculated in the resize() method.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);

		// Create font
		Font font = this.getFont();
		AffineTransform fontAT = new AffineTransform();
		fontAT.rotate(Math.toRadians(270));
		Font fontrotated = font.deriveFont(fontAT);

		// Painting the labels
		if (xLabel != null) {
			g.setFont(font);
			g.drawString(xLabel, leftInset + innerWidth / 2 - getFontMetrics(font).stringWidth(xLabel) / 2, height
					- bottomInset / 4);
		}
		if (yLabel != null) {
			g.setFont(fontrotated);
			g.drawString(yLabel, leftInset / 4, topInset + innerHeight / 2 + getFontMetrics(font).stringWidth(yLabel));
			g.setFont(font);
		}

		g.drawLine(leftInset - 1, height - bottomInset, leftInset - 1, topInset - 1);
		g.drawLine(leftInset - 1, height - bottomInset, width - rightInset, height - bottomInset);

		g.fillPolygon(new int[] { width - rightInset, width - rightInset + rightInset * 2 / 3, width - rightInset },
				new int[] { height - bottomInset + 4, height - bottomInset, height - bottomInset - 4 }, 3);
		g.fillPolygon(new int[] { leftInset + 4 - 1, leftInset - 1, leftInset - 4 - 1 }, new int[] { topInset,
				topInset * 1 / 3, topInset }, 3);

		// Plot the graduation
		// x graduation
		for (int i = 0; i < xTicks.length; i++) {
			double x = (xTicks[i] - xMin) / (xMax - xMin) * innerWidth;
			String strxLabel = formatValue(xTicks[i]);
			if (x <= innerWidth && x >= 0.0
					&& ((allowNegativeXValues == true) || (allowNegativeXValues == false && xTicks[i] >= 0.0))) {
				// major ticks
				g.drawLine((int) (leftInset - 1 + x), height - bottomInset, (int) (leftInset - 1 + x), height
						- bottomInset + 5);
				g.drawString(strxLabel, (int) (leftInset - 1 + x), height - bottomInset * 2 / 3);
				// x grid
				if (gridOn && x < innerWidth && (x - 1.0) > 0.0) {
					g.setColor(gridColor);
					g.drawLine((int) (leftInset - 1 + x), height - bottomInset, (int) (leftInset - 1 + x), topInset);
					g.setColor(Color.black);
				}
			}
			// minor ticks
			int minorTickCount = 5;
			double minorXTickDelta = xTickDelta * innerWidth / ((xMax - xMin) * minorTickCount);
			for (int j = 0; j < minorTickCount; j++) {
				if ((x + minorXTickDelta * j) <= innerWidth && (x + minorXTickDelta * j) >= 0.0
						&& ((allowNegativeXValues == true) || (allowNegativeXValues == false && xTicks[i] >= 0.0))) {
					g.drawLine((int) (leftInset - 1 + x + minorXTickDelta * j), height - bottomInset, (int) (leftInset
							- 1 + x + minorXTickDelta * j), height - bottomInset + 2);
				}
			}
		}

		// y graduation
		for (int i = 0; i < yTicks.length; i++) {
			double y = (yTicks[i] - yMin) / (yMax - yMin) * innerHeight;
			String stryLabel = formatValue(yTicks[i]);
			if (y <= innerHeight && y >= 0.0
					&& ((allowNegativeYValues == true) || (allowNegativeYValues == false && yTicks[i] >= 0.0))) {
				// major ticks
				g.drawLine(leftInset - 6, (int) (height - bottomInset - y), leftInset - 1,
						(int) (height - bottomInset - y));
				g.drawString(stryLabel, leftInset - getFontMetrics(font).stringWidth(stryLabel) - 10, (int) (height
						- bottomInset - y + getFont().getSize() / 2));
				// y grid
				if (gridOn && y < innerHeight && y > 0.0) {
					g.setColor(gridColor);
					g.drawLine(leftInset, (int) (height - bottomInset - y), width - rightInset, (int) (height
							- bottomInset - y));
					g.setColor(Color.black);
				}
			}
			// minor ticks
			int minorTickCount = 5;
			double minorYTickDelta = yTickDelta * innerHeight / ((yMax - yMin) * minorTickCount);
			for (int j = 0; j < minorTickCount; j++) {
				if ((y + minorYTickDelta * j) <= innerHeight && (y + minorYTickDelta * j) >= 0.0
						&& ((allowNegativeYValues == true) || (allowNegativeYValues == false && yTicks[i] >= 0.0))) {
					g.drawLine(leftInset - 3, (int) (height - bottomInset - y - minorYTickDelta * j), leftInset - 1,
							(int) (height - bottomInset - y - minorYTickDelta * j));
				}
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource().equals(tfXMin)) {
				double xMinTemp = Double.parseDouble(tfXMin.getText());
				if (xMinTemp < xMax) {
					xMin = xMinTemp;
				}
			}
			if (e.getSource().equals(tfXMax)) {
				double xMaxTemp = Double.parseDouble(tfXMax.getText());
				if (xMaxTemp > xMin) {
					xMax = xMaxTemp;
				}
			}
			if (e.getSource().equals(tfYMin)) {
				double yMinTemp = Double.parseDouble(tfYMin.getText());
				if (yMinTemp < yMax) {
					yMin = yMinTemp;
				}
			}
			if (e.getSource().equals(tfYMax)) {
				double yMaxTemp = Double.parseDouble(tfYMax.getText());
				if (yMaxTemp > yMin) {
					yMax = yMaxTemp;
				}
			}
		} catch (Exception e2) {

		}
		checkRange();
		resize();
		repaint();
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		int wheelRotation = e.getWheelRotation();
		if (e.getWhen() - lastScrollEvent < 100.0) {
			wheelRotation = (int) (Math.abs(200 / (e.getWhen() - lastScrollEvent)) * e.getWheelRotation());
			if (Math.abs(wheelRotation) > 30) {
				wheelRotation = 30 * e.getWheelRotation();
			}
		}
		lastScrollEvent = e.getWhen();
		double deltaX = wheelRotation * Math.abs(xMax - xMin) / (innerWidth);
		double deltaY = wheelRotation * Math.abs(yMax - yMin) / (innerWidth);
		if (e.getSource().equals(tfXMin)) {
			if ((xMin - deltaX) >= xMinHard && (xMin - deltaX) < xMax) {
				xMin = xMin - deltaX;
			} else if ((xMin - deltaX) < xMinHard) {
				xMin = xMinHard;
			}
		}
		if (e.getSource().equals(tfXMiddle)) {
			if ((xMin - deltaX) >= xMinHard && (xMax + deltaX) <= xMaxHard && (xMin - deltaX) < xMax
					&& (xMax + deltaX) > xMin) {
				xMin = xMin - deltaX;
				xMax = xMax + deltaX;
			}
		}
		if (e.getSource().equals(tfXMax)) {
			if ((xMax + deltaX) <= xMaxHard && (xMax + deltaX) > xMin) {
				xMax = xMax + deltaX;
			} else if ((xMax + deltaX) > xMaxHard) {
				xMax = xMaxHard;
			}
		}
		if (e.getSource().equals(tfYMin)) {
			if ((yMin - deltaY) >= yMinHard && (yMin - deltaY) < yMax) {
				yMin = yMin - deltaY;
			} else if ((yMin - deltaY) < yMinHard) {
				yMin = yMinHard;
			}
		}
		if (e.getSource().equals(tfYMiddle)) {
			if ((yMin - deltaY) >= yMinHard && (yMax + deltaY) <= yMaxHard && (yMin - deltaY) < yMax
					&& (yMax + deltaY) > yMin) {
				yMin = yMin - deltaY;
				yMax = yMax + deltaY;
			}
		}
		if (e.getSource().equals(tfYMax)) {
			if ((yMax + deltaY) <= yMaxHard && (yMax + deltaY) > yMin) {
				yMax = yMax + deltaY;
			} else if ((yMax + deltaY) > yMaxHard) {
				yMax = yMaxHard;
			}
		}
		checkRange();
		resize();
		repaint();
	}

	// Class Line
	class Line extends JLabel {
		private static final long serialVersionUID = 1L;
		private double xMin = 0.0, xMax = 0.0, yMin = 0.0, yMax = 0.0;
		private double[] xValues = {}, yValues = {};
		private int[] xValuesInt = {}, yValuesInt = {};
		private Color color = Color.black;
		private int lineType = LINE, lineWeight = 1;
		private int lineIndex = 0;
		private String lineText = "";

		public Line() {
			// Important: do not repaint in this inner class. Repainting will
			// slow down the plotting process and we repaint in the main class
			// every
			// time we set or add data to plot and every time we resize the
			// window.
			super.setLayout(null);
			setOpaque(false);
		}

		// set the plot data (xValues and yValues)
		public void setData(double[] xValues, double[] yValues) {
			if (xValues == null || yValues == null) {
				return;
			}
			this.xValues = xValues;
			this.yValues = yValues;
			resize();
		}

		// set the range of shown values
		public void setRange(double xMin, double xMax, double yMin, double yMax) {
			this.xMin = xMin;
			this.xMax = xMax;
			this.yMin = yMin;
			this.yMax = yMax;
			resize();
		}

		// set the color of the line and label
		public void setColor(Color color) {
			this.color = color;
		}

		// set the line type
		public void setLineType(int lineType) {
			this.lineType = lineType;
		}

		// set weight of the line
		public void setLineWeight(int lineWeight) {
			this.lineWeight = lineWeight;
		}

		// set the line label text
		public void setLineText(String labelText) {
			this.lineText = labelText;
		}

		// Return the line label text
		public String getLineText() {
			return lineText;
		}

		// set the index of the label
		public void setLabelIndex(int index) {
			this.lineIndex = index;
		}

		// resize the plot data if the bounds has changed
		private void resize() {
			// set delta x and delta y (plotting area / range)
			double dx = (double) (innerWidth - 1) / (xMax - xMin);
			double dy = (double) (innerHeight - 1) / (yMax - yMin);
			// check if xValueArray is longer than yvalueArray
			int arrayLength = 0;
			if (xValues.length > yValues.length) {
				arrayLength = yValues.length;
			} else {
				arrayLength = xValues.length;
			}
			// calculates an integer array for painting with it
			xValuesInt = new int[arrayLength];
			yValuesInt = new int[arrayLength];
			for (int i = 0; i < arrayLength; i++) {
				xValuesInt[i] = (int) (dx * (xValues[i] - xMin));
				yValuesInt[i] = (int) (innerHeight - (dy * (yValues[i] - yMin)));
				if (Double.isNaN(xValues[i]) || Double.isInfinite(xValues[i])) {
					xValuesInt[i] = Integer.MAX_VALUE;
				}
				if (Double.isNaN(yValues[i]) || Double.isInfinite(yValues[i])) {
					yValuesInt[i] = Integer.MAX_VALUE;
				}
			}
		}

		// paints the line and the line label
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Stroke saveStroke = ((Graphics2D) g).getStroke();
			((Graphics2D) g).setStroke(new BasicStroke(lineWeight));

			g.setColor(color);
			if (xValues == null || yValues == null) {
				return;
			}
			for (int i = 0; i < xValuesInt.length; i++) {
				switch (lineType) {
				// Plot a simple line
				case LINE:
					try {
						if (xValuesInt[i] == Integer.MAX_VALUE || yValuesInt[i] == Integer.MAX_VALUE
								|| xValuesInt[i + 1] == Integer.MAX_VALUE || yValuesInt[i + 1] == Integer.MAX_VALUE) {
							break;
						}
						g.drawLine(xValuesInt[i], yValuesInt[i], xValuesInt[i + 1], yValuesInt[i + 1]);
					} catch (Exception e) {
						// It's normal that we produce ArrayIndexOutOfBounds
						// Exceptions
					}
					break;
				// Plot dots
				case DOTS:
					if (xValuesInt[i] >= 0 && xValuesInt[i] <= innerWidth && yValuesInt[i] >= 0
							&& yValuesInt[i] <= innerHeight) {
						g.fillOval(xValuesInt[i] - lineWeight, yValuesInt[i] - lineWeight, lineWeight * 2,
								lineWeight * 2);
					}
					break;
				// Plot a line with dots on each point
				case LineAndDots:
					try {
						if (xValuesInt[i] == Integer.MAX_VALUE || yValuesInt[i] == Integer.MAX_VALUE
								|| xValuesInt[i + 1] == Integer.MAX_VALUE || yValuesInt[i + 1] == Integer.MAX_VALUE) {
							break;
						}
						g.drawLine(xValuesInt[i], yValuesInt[i], xValuesInt[i + 1], yValuesInt[i + 1]);
					} catch (Exception e) {
						// It's normal that we produce ArrayIndexOutOfBounds
						// Exceptions
					}
					if (xValuesInt[i] >= 0 && xValuesInt[i] <= innerWidth && yValuesInt[i] >= 0
							&& yValuesInt[i] <= innerHeight) {
						g.fillOval(xValuesInt[i] - lineWeight, yValuesInt[i] - lineWeight, lineWeight * 2,
								lineWeight * 2);
					}
					break;
				default:
					break;
				}
			}
			((Graphics2D) g).setStroke(saveStroke);
			// draw the line label text
			g.fillRect(10, lineIndex * 20 - getFont().getSize() / 2, getFont().getSize() / 2, getFont().getSize() / 2);
			g.drawString(lineText, 20, lineIndex * 20);
			g.setColor(Color.black);
		}
	}

}
