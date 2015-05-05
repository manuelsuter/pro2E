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
		
			double Ks = Double.parseDouble(view.leftPanel.inputPanel.tfKs.getText());
			double Tu = Double.parseDouble(view.leftPanel.inputPanel.tfTu.getText());
			double Tg = Double.parseDouble(view.leftPanel.inputPanel.tfTg.getText());
			double Tp = 0.0;
			//TODO: Tp noch verarbeiten.
			//double Tp = Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp.getText());	
			int controllerTyp;
			
			if (view.leftPanel.controllerChooserPanel.btPI.isSelected() == true) {
				controllerTyp = Controller.PI;
			}
			else{
				controllerTyp = Controller.PID;
			}
			model.setData(Ks, Tu, Tg, controllerTyp, Tp);
			
			//TODO: SYSO löschen.
			System.out.println("GUI-Controller Methode calculate() ausgelöst!!");
		
	}
	
	
	public void clear(){		
		view.leftPanel.inputPanel.tfKs.setText("");
		view.leftPanel.inputPanel.tfTu.setText("");
		view.leftPanel.inputPanel.tfTg.setText("");	
	}
	

	
	public void setView(View view) {
		this.view = view;
	}
	
	/**
	 * public void setFrame(Application frame){
	 
		this.frame = frame;
		
	}
	*/

	//public void btAction(String stInfo) {
	//	model.setData(stInfo);
		// Klasse.Methode(Übergabewert)

	//}
}
