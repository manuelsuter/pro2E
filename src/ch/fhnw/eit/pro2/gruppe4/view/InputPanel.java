package ch.fhnw.eit.pro2.gruppe4.view;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class InputPanel extends JPanel implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	private GUIController guiController;
	public JDoubleTextField tfKs, tfTu, tfTg;
	private JButton btBerechne, btClear;
	private JCheckBox checkBoxPlot;

	/**
	 * Constructor for the InputPanel. Includes the Textfields for InputValues and the Buttons.
	 * 
	 * @param controller
	 */
	public InputPanel(GUIController controller) {
		super(new GridBagLayout());
		this.guiController = controller;
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
		//TODO: Textfelder wieder löschen.
		tfKs = new JDoubleTextField("2.0",100,false);
		add(tfKs, new GridBagConstraints(1, 0, 2, 1, 1.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
				new Insets(10, 10, 5, 10), 0, 0));
		
		add(new JLabel("<html><i>T<sub>u</sub></i></html>"),
				new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.LINE_START, GridBagConstraints.NONE,
						new Insets(10, 10, 5, 10), 0, 0));

		tfTu = new JDoubleTextField("1.1",100,false);
		add(tfTu, new GridBagConstraints(1, 1, 2, 1, 1.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
				new Insets(10, 10, 5, 10), 0, 0));

		add(new JLabel("s"), new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(10, 0, 5, 10), 0, 0));

		add(new JLabel("<html><i>T<sub>g</sub></i></html>"),
				new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.LINE_START, GridBagConstraints.NONE,
						new Insets(10, 10, 5, 10), 0, 0));

		tfTg = new JDoubleTextField("8.9",100,false);
		add(tfTg, new GridBagConstraints(1, 2, 2, 1, 1.0, 0.0,
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
		if(e.getSource()==btBerechne){
			guiController.calculate();		
		}
		if(e.getSource()==btClear){
			guiController.clear();		
		}
	}
//TODO: Hinweis auf Geï¿½derte Werte in Textfelder falls Neuberechnenn noch nicht ausgelï¿½st???
	

	public void itemStateChanged(ItemEvent e) {
		if (checkBoxPlot.isSelected() == false) {
			guiController.setVisibility(false);
		}
		
		if (checkBoxPlot.isSelected() == true) {
			guiController.setVisibility(true);
		}
	}
	/**
	 * 
	 * @param obs
	 * @param obj
	 */
	public void update(Observable obs, Object obj) {

	}
	
	

}
