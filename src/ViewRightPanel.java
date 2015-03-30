import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class ViewRightPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private ViewUpperPlotPanel upperPlotPanel;
	private ViewStepResponsePanel stepResponsePanel;

	
	
	public ViewRightPanel(Controller controller) {
		super(new GridBagLayout());
		this.controller = controller;
		
		// Construct the upperPlotPanel and the stepResponsePanel
		upperPlotPanel = new ViewUpperPlotPanel(controller);
		add(upperPlotPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.3,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(
						2, 2, 2, 2), 0, 0));
		
		stepResponsePanel = new ViewStepResponsePanel(controller);
		add(stepResponsePanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.7,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(
						2, 2, 2, 2), 0, 0));
	
	}	
	
	public void update(Observable obs, Object obj) {
		upperPlotPanel.update(obs, obj);
		stepResponsePanel.update(obs, obj);
	}
}