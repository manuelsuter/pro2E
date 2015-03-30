import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class ViewRegulatorValuePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private ViewRulesOfThumbPanel rulesOfThumbPanel;
	private ViewPhaseResponseMethodPanel phaseResponsePanel;
	private JLabel lbRosenberg, lbOppelt, lbZNn, lbZNs, lbCHR2, lbCHRa, lbTSUMn, lbTSUMs, lbKR, lbTN, lbTV;
	
	
	public ViewRegulatorValuePanel(Controller controller) {
		super(new GridBagLayout());
		this.controller = controller;
		setBorder(MyBorderFactory.createMyBorder(" Reglerwerte "));
	
		
		phaseResponsePanel = new ViewPhaseResponseMethodPanel(controller);
		add(phaseResponsePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						2, 2, 2, 2), 0, 0));
		
			
		rulesOfThumbPanel = new ViewRulesOfThumbPanel(controller);
		add(rulesOfThumbPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, new Insets(
						2, 2, 2, 2), 0, 0));
	
	}
	
	public void update(Observable obs, Object obj) {
		phaseResponsePanel.update(obs, obj);
		rulesOfThumbPanel.update(obs, obj);
	}
}


