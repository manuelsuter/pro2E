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

import javax.swing.JLabel;
import javax.swing.JPanel;


public class RulesOfThumbPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	
	
	public RulesOfThumbPanel(Controller controller) {
		super(new GridBagLayout());
		this.controller = controller;
		setBorder(MyBorderFactory.createMyBorder(" Faustformeln "));
		
		makeRulesOfThumbLine("", "<html><i>K<sub>r</sub></html></i>", "<html><i>T<sub>n</sub></i></html>", "<html><i>T<sub>v</sub></html></i>", 0);
		makeRulesOfThumbLine("Rosenberg", "8.49", "5.72", "1.25", 1);
		makeRulesOfThumbLine("Oppelt", "8.49", "5.72", "1.25", 2);
		makeRulesOfThumbLine("Ziegler/Nichols (normal)", "8.49", "5.72", "1.25", 3);
		makeRulesOfThumbLine("Ziegler/Nochols (Schwingversuch)", "8.49", "5.72", "1.25", 4);
		makeRulesOfThumbLine("Chien/Hrones/Reswick (20% Ü.)", "8.49", "5.72", "1.25", 5);
		makeRulesOfThumbLine("Chien/Hrones/Reswick (aperiod.)", "8.49", "5.72", "1.25", 6);
		//makeRulesOfThumbLine("T-Summen-Regel (normal)", "8.49", "5.72", "1.25", 7);
		//makeRulesOfThumbLine("T-Summen-Regel (schnell)", "8.49", "5.72", "1.25", 8);
		
	}
	
	
	
	public void makeRulesOfThumbLine(String lbRule, String lbKr, String lbTn, String lbTv, int y){
		
		add(new JLabel(lbRule), new GridBagConstraints(0, y, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
						5, 10, 5, 10), 0, 0));
		
		add(new JLabel(lbKr), new GridBagConstraints(1, y, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
						5, 10, 5, 10), 0, 0));
		
		add(new JLabel(lbTn), new GridBagConstraints(2, y, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
						5, 10, 5, 10), 0, 0));
		
		add(new JLabel(lbTv), new GridBagConstraints(3, y, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
						5, 10, 5, 10), 0, 0));
	}
	
	public void update(Observable obs, Object obj) {

	}
	
}
