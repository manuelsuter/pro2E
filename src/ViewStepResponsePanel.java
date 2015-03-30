import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ViewStepResponsePanel extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private GraphPlotter stepResponse;
	private double xValues[] = {1.0,2.0,3.0,4.0,5.0};
	private double yValues[] = {-4.5, -3,-2, 2.0, 4,0};
	private JCheckBox checkPhase, checkRosenberg, checkOppelt, checkZN, checkCHR20, checkCHR0;
	private JPanel checkBoxPanel;
		
	
	public ViewStepResponsePanel(Controller controller) {
		super(new BorderLayout());
		this.controller = controller;
		setBorder(MyBorderFactory.createMyBorder(" Schrittantworten "));
				
		double[] x = ModelCalc.linspace(0, 4*Math.PI, 1000);
		double[] y = new double[x.length];
		double[] z = new double[x.length];
		
		for (int i = 0; i < x.length; i++) {
			y[i] = Math.sin(x[i]);
			z[i] = Math.cos(x[i]);
		}
		
		//Includes the stepresponse plot.
		
		stepResponse = new GraphPlotter("x","y",true);
		
		stepResponse.allowNegativeXValues(false);
		stepResponse.allowNegativeYValues(true);
		stepResponse.setRange(0.0, 10.0, -2.0, 2.0);
		stepResponse.addXMaxScaleField();
		stepResponse.setData(x, y, 2, Color.BLUE, 2, "sin");
		stepResponse.addData(x, z, 2, Color.RED, 2, "cos");
		stepResponse.addData(xValues, yValues, 2, Color.YELLOW, 2, "test");
		
		add(stepResponse, BorderLayout.CENTER);
		
		
		//includes the CheckBoxes below the plot on the checkBoxPanel
		checkBoxPanel = new JPanel(new GridLayout(2,4));
		
		checkPhase = new JCheckBox("Phasengang-Methode");
		checkPhase.setForeground(Color.RED);
		checkBoxPanel.add(checkPhase);
		checkPhase.addChangeListener(this);

		
		checkRosenberg = new JCheckBox("Rosenberg");
		checkRosenberg.setForeground(Color.GREEN);
		checkBoxPanel.add(checkRosenberg);
		checkRosenberg.addChangeListener(this);
		
		checkCHR0 = new JCheckBox("Chien/Hrones/Reswick (0% Ü.)");
		checkCHR0.setForeground(Color.MAGENTA);
		checkBoxPanel.add(checkCHR0);
		checkCHR0.addChangeListener(this);
		
		checkZN = new JCheckBox("Ziegler/Nichols");
		checkZN.setForeground(Color.ORANGE);
		checkBoxPanel.add(checkZN);
		checkZN.addChangeListener(this);
		
		checkOppelt = new JCheckBox("Oppelt");
		checkBoxPanel.add(checkOppelt);
		checkOppelt.addChangeListener(this);
		
		checkCHR20 = new JCheckBox("Chien/Hrones/Reswick (20% Ü.)");
		checkCHR20.setForeground(Color.BLUE);
		checkBoxPanel.add(checkCHR20);
		checkCHR20.addChangeListener(this);
		
		add(checkBoxPanel, BorderLayout.SOUTH);	
	}


	@Override
	public void stateChanged(ChangeEvent arg0) {
		
		
	}
	public void update(Observable obs, Object obj) {

	}
}
