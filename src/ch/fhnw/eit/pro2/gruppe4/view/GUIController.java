package ch.fhnw.eit.pro2.gruppe4.view;
/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import java.awt.Dimension;

import ch.fhnw.eit.pro2.gruppe4.model.Controller;
import ch.fhnw.eit.pro2.gruppe4.model.Model;

public class GUIController {
	private Model model;
	private View view;
	

	public GUIController(Model model) {
		this.model = model;
	}
	
	/**
	 * Allows the user to resize the Frame-Size.
	 * @param flag
	 */
	public void setVisibility(Boolean flag){
		if (flag == false) {
			view.setVisibility(flag, view.leftPanel.getSize());
		}
		if (flag == true) {
			Dimension dimension = new Dimension(1200, 768);
		view.setVisibility(flag, dimension);
		}
	}
	
	public void setData(double Ks, double Tu, double Tg, int controllerTyp, double Tp){
		model.setData(Ks, Tu, Tg, controllerTyp, Tp);
	}
	
	public void calculate(){			
		
//			if (view.leftPanel.inputPanel.tfTu.getText().equals("") || view.leftPanel.inputPanel.tfTg.getText().equals("") || view.leftPanel.inputPanel.tfKs.getText().equals("")
//					|| view.leftPanel.inputPanel.tfTu.getText().equals(" ") || view.leftPanel.inputPanel.tfTg.getText().equals(" ") || view.leftPanel.inputPanel.tfKs.getText().equals(" ")) {
//				System.out.println("Felder dürfen nicht leer sein oder Leerzeichen enthalten.");
//				
//				
				try {
					double Ks = Double.parseDouble(view.leftPanel.inputPanel.tfKs.getText());
					double Tu = Double.parseDouble(view.leftPanel.inputPanel.tfTu.getText());
					double Tg = Double.parseDouble(view.leftPanel.inputPanel.tfTg.getText());
					double Tp = 0.0;
					
					if ((Double.parseDouble(view.leftPanel.inputPanel.tfKs.getText()) == 0.0) || (Double.parseDouble(view.leftPanel.inputPanel.tfTu.getText()) == 0.0) || (Double.parseDouble(view.leftPanel.inputPanel.tfTg.getText()) == 0.0)) {
						view.leftPanel.inputPanel.lbMessage.setText("Werte dürfen nicht 0 sein!");
						System.out.println("Fehler");
					}else {	
					//TODO: Tp noch verarbeiten.
					//double Tp = Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp.getText());	
					int controllerTyp;
					
					if (view.leftPanel.controllerChooserPanel.btPI.isSelected() == true) {
						controllerTyp = Controller.PI;
					}else{
						controllerTyp = Controller.PID;
					}
					model.setData(Ks, Tu, Tg, controllerTyp, Tp);
					
					}
					
					
				} catch (NumberFormatException e) {
					view.leftPanel.inputPanel.lbMessage.setText("Eigabefeld darf nicht leer sein.");
					view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp.setText("Hallo");
					System.out.println("Ungültige Eingabe");
				}
			
	}	
	
	
	public void clear(){		
		view.leftPanel.inputPanel.tfKs.setText("");
		view.leftPanel.inputPanel.tfTu.setText("");
		view.leftPanel.inputPanel.tfTg.setText("");
		view.leftPanel.controllerValuePanel.phaseResponsePanel.setInitialValues();
		view.leftPanel.controllerValuePanel.rulesOfThumbPanel.setInitialValues();
		view.rightPanel.stepResponsePanel.deleteDatasets();
	}
	

	
	public void setView(View view) {
		this.view = view;
	}
	
	/**
	 * public void setFrame(Application frame){
	 
		this.frame = frame;
		
	}
	*/

	
}
