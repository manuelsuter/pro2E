import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;


public class ViewLeftPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private ViewInputPanel inputPanel;
	private ViewRegulatorChooserPanel regulatorChooserPanel;
	private ViewRegulatorValuePanel regulatorValuePanel;

	
	
	public ViewLeftPanel(Controller controller) {
		super(new GridBagLayout());	
		this.controller = controller;
		
		UIManager.put ("ToggleButton.select", Color.ORANGE); 
	
		// Construct the inputPanel, regulatorChooserPanel and regulatorValuePanel
		inputPanel = new ViewInputPanel(controller);
		add(inputPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						2, 2, 2, 2), 0, 0));
		
		regulatorChooserPanel = new ViewRegulatorChooserPanel(controller);
		add(regulatorChooserPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						2, 2, 2, 2), 0, 0));
		
		regulatorValuePanel = new ViewRegulatorValuePanel(controller);
		add(regulatorValuePanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						2, 2, 2, 2), 0, 0));
		
		
		
	}	
	
	public void update(Observable obs, Object obj) {
		inputPanel.update(obs, obj);
		regulatorChooserPanel.update(obs, obj);
		regulatorValuePanel.update(obs, obj);
	}
}