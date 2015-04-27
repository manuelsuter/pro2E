/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class InputPanel extends JPanel implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	private GUIController controller;
	private JDoubleTextField tfKS, tfTU, tfTG;
	private JButton btBerechne, btClear;
	private JCheckBox checkBoxPlot;

	/**
	 * Constructor for the InputPanel. Includes the Textfields for InputValues and the Buttons.
	 * 
	 * @param controller
	 */
	public InputPanel(GUIController controller) {
		super(new GridBagLayout());
		this.controller = controller;
		setBorder(MyBorderFactory.createMyBorder(" Schrittantwort vermessen "));

		/*
		 * x, y, x-span, y-span, x-weight, y-weight, anchor, fill, insets(int
		 * top, int left, int bottom, int right), internal padding x, internal
		 * padding y.
		 */

		// Construct the components of inputPanel
		add(new JLabel("<html><i>K<sub>s</sub></i></html>"),
				new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.LINE_START, GridBagConstraints.NONE,
						new Insets(10, 10, 5, 10), 0, 0)); // .setText("K"+"<html><center><font face=\"Arial, Verdana, Sans Serif\" color=\"#FFFFFF\" size=\"-2\">Please insert</font></center></html>");

		tfKS = new JDoubleTextField("",100,false);
		add(tfKS, new GridBagConstraints(1, 0, 2, 1, 1.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
				new Insets(10, 10, 5, 10), 0, 0));

		add(new JLabel("<html><i>T<sub>u</sub></i></html>"),
				new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.LINE_START, GridBagConstraints.NONE,
						new Insets(10, 10, 5, 10), 0, 0));

		tfTU = new JDoubleTextField("",100,false);
		add(tfTU, new GridBagConstraints(1, 1, 2, 1, 1.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
				new Insets(10, 10, 5, 10), 0, 0));

		add(new JLabel("s"), new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 0, 5, 10), 0, 0));

		add(new JLabel("<html><i>T<sub>g</sub></i></html>"),
				new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.LINE_START, GridBagConstraints.NONE,
						new Insets(10, 10, 5, 10), 0, 0));

		tfTG = new JDoubleTextField("",100,false);
		add(tfTG, new GridBagConstraints(1, 2, 2, 1, 1.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
				new Insets(10, 10, 5, 10), 0, 0));

		add(new JLabel("s"), new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 0, 5, 10), 0, 0));

		btBerechne = new JButton("Berechnen");
		add(btBerechne, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		btBerechne.addActionListener(this);

		btClear = new JButton("Löschen");
		add(btClear, new GridBagConstraints(2, 3, 2, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		btClear.addActionListener(this);

		checkBoxPlot = new JCheckBox("Zeige Plots", true);
		add(checkBoxPlot, new GridBagConstraints(4, 3, 2, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 10, 10, 10), 0, 0));
		checkBoxPlot.addItemListener(this);
	}
	public void actionPerformed(ActionEvent e) {

	}
//TODO: Grösse aus LeftPanel Lesen; bei true grösser setzen.
//TODO: Hinweis auf Geäderte Werte in Textfelder falls Neuberechnenn noch nicht ausgelöst???
	
	
	public void itemStateChanged(ItemEvent e) {
		if (checkBoxPlot.isSelected() == false) {
			controller.setVisibility(false);
		}
		
		if (checkBoxPlot.isSelected() == true) {
			controller.setVisibility(true);
		}
	}

	public void update(Observable obs, Object obj) {

	}

}
