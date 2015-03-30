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
import javax.swing.JTextField;


public class PhaseResponseMethodPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JTextField tfTP;
	
	
	
	public PhaseResponseMethodPanel(Controller controller) {
		super(new GridBagLayout());
		this.controller = controller;
		setBorder(MyBorderFactory.createMyBorder(" Phasengang-Methode "));
		
		makePhaseResponseMethodLine("Überschwingen                                   ", "<html><i>K<sub>r</sub></i></html>", "<html><i>T<sub>n</sub></i></html>", "<html><i>T<sub>v</sub></i></html>", 0);
		makePhaseResponseMethodLine("wenig", "8.49", "5.72", "1.25", 1);
		makePhaseResponseMethodLine("mittel", "8.49", "5.72", "1.25", 2);
		makePhaseResponseMethodLine("stark", "8.49", "5.72", "1.25", 3);
		
		add(new JLabel("<html><i>T<sub>p</sub></i></html>"), new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(
						5, 10, 5, 10), 0, 0));
		
		tfTP = new JTextField();
		add(tfTP, new GridBagConstraints(1, 4, 3, 1, 1.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						5, 10, 5, 10), 0, 0));
	}
	
	/**
	 * <pre>
	 * - Creates Output-Lines.
	 * </pre>
	 * **/

public void makePhaseResponseMethodLine(String lbMethod, String lbKr, String lbTn, String lbTv, int y){
		
	add(new JLabel(lbMethod), new GridBagConstraints(0, y, 1, 1, 0.0, 0.0,
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
	Model model = (Model)obs;
	tfTP.setText(model.getData());
}

}
