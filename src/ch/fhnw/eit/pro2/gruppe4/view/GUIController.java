package ch.fhnw.eit.pro2.gruppe4.view;
/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import java.awt.Dimension;

import ch.fhnw.eit.pro2.gruppe4.model.Controller;
import ch.fhnw.eit.pro2.gruppe4.model.ControllerException;
import ch.fhnw.eit.pro2.gruppe4.model.Model;
import ch.fhnw.eit.pro2.gruppe4.model.PhaseResponseMethod;
import ch.fhnw.eit.pro2.gruppe4.model.SaniException;

public class GUIController {
	private Model model;
	private View view;
	private boolean controllerCalculated = false;
	

	public GUIController(Model model) {
		this.model = model;
	}
	
	/**
	 * Allows the user to resize the Frame-Size.
	 * @param flag
	 */
	public void setVisibility(boolean flag){
		if (flag == false) {
			view.setVisibility(flag, view.leftPanel.getSize());
		}
		if (flag == true) {
			Dimension dimension = new Dimension(1200, 768);
		view.setVisibility(flag, dimension);
		}
	}
	
//	public void setData(double Ks, double Tu, double Tg, int controllerTyp, double Tp){
//		model.setData(Ks, Tu, Tg, controllerTyp, Tp);
//	}
	
	public void calculate(){	
		
		view.leftPanel.inputPanel.lbMessage.setText(" ");
		
				
				try {
					double[] tpValues = new double[3];
					if ((view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[0].getText()).equals("") || (view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[1].getText()).equals("") || (view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[2].getText()).equals("")) {
						tpValues[0] = 0.0;
						tpValues[1] = 0.0;
						tpValues[2] = 0.0;
					}else {
						tpValues[0] = Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[0].getText());
						tpValues[1] = Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[1].getText());
						tpValues[2] = Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[2].getText());
					
					}
					
					double Ks = Double.parseDouble(view.leftPanel.inputPanel.tfKs.getText());
					double Tu = Double.parseDouble(view.leftPanel.inputPanel.tfTu.getText());
					double Tg = Double.parseDouble(view.leftPanel.inputPanel.tfTg.getText());
					
					
					double phaseMarginOffset = view.rightPanel.upperPlotPanel.jsPhaseMargin.getValue();
					double overShoot = view.rightPanel.upperPlotPanel.jsOverShoot.getValue();

					if ((Double.parseDouble(view.leftPanel.inputPanel.tfKs.getText()) == 0.0) || (Double.parseDouble(view.leftPanel.inputPanel.tfTu.getText()) == 0.0) || (Double.parseDouble(view.leftPanel.inputPanel.tfTg.getText()) == 0.0)) {
						view.leftPanel.inputPanel.lbMessage.setText("Werte dürfen nicht 0 sein!");
					}else {	
						int controllerTyp;
						
						if (view.leftPanel.controllerChooserPanel.btPI.isSelected() == true) {
							controllerTyp = Controller.PI;
						}else{
							controllerTyp = Controller.PID;
						}
					model.setData(Ks, Tu, Tg, controllerTyp, tpValues, overShoot, phaseMarginOffset);
					}
					
					view.leftPanel.inputPanel.lbOrder.setText("        Strecken-Ordnung: "+model.getPath().getT().length);
					
                } catch (NumberFormatException e) {
                    view.leftPanel.inputPanel.lbMessage.setText("Eigabefeld darf nicht leer sein.");
				} catch (SaniException e){
					view.leftPanel.inputPanel.lbMessage.setText(e.getLocalizedMessage());
				} catch (ControllerException e){
					view.leftPanel.inputPanel.lbMessage.setText(e.getLocalizedMessage());
				}
				
				controllerCalculated = true;	
	}	
	
	public void setTp(){
		
		view.leftPanel.inputPanel.lbMessage.setText(" ");
		
		try {
			double[] tpValues = new double[3];
			tpValues[0] = Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[0].getText());
			tpValues[1] = Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[1].getText());
			tpValues[2] = Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[2].getText());
		
		if ((Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[0].getText()) == 0.0) || (Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[0].getText()) == 0.0) || (Double.parseDouble(view.leftPanel.controllerValuePanel.phaseResponsePanel.tfTp[0].getText()) == 0.0)) {
			setExceptionLabel("Werte dürfen nicht 0 sein!");
		}else {		
		model.setTp(tpValues);
		}
		
		} catch (NumberFormatException e) {
			setExceptionLabel("Eingabefeld darf nicht leer sein.");
		}
	}
	
	
	public void clear(){		
		view.leftPanel.inputPanel.tfKs.setText("");
		view.leftPanel.inputPanel.tfTu.setText("");
		view.leftPanel.inputPanel.tfTg.setText("");
		view.leftPanel.controllerValuePanel.phaseResponsePanel.setInitialValues();
		view.leftPanel.controllerValuePanel.rulesOfThumbPanel.setInitialValues();
		view.rightPanel.stepResponsePanel.deleteDatasets();
		
		controllerCalculated = false;
	}
	
	public void setExceptionLabel(String exception){
		view.leftPanel.inputPanel.lbMessage.setText(exception);
	}


	public void setPhaseMargin(double phaseMargin){
		if (controllerCalculated == true) {
			model.setPhaseMargin(phaseMargin);
		}
		
	}

	public void setOverShoot(double overShoot){
		if (controllerCalculated == true) {
			model.setOverShoot(overShoot);
		}
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
