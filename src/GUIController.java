/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import java.awt.Dimension;

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
