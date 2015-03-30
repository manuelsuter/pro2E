import java.awt.event.KeyEvent;
import java.util.Observable;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class ViewUpperPlotPanel extends JTabbedPane {

	
	private Controller controller;
	private ViewPTn pTnPanel;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	
	
	public ViewUpperPlotPanel(Controller controller){
		this.controller = controller;
		

		
		
		pTnPanel = new ViewPTn(controller);
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		
		
		setTabPlacement(JTabbedPane.TOP);
		
		
		addTab("PTn-Strecke", pTnPanel);
		
		addTab("Amplitudengang", panel2);

		addTab("Phasengang", panel3);
	
		addTab("Einstell-Regler", panel4);
	
	}
	public void update(Observable obs, Object obj) {

	}
}
