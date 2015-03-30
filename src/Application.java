/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Müller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class Application extends JFrame {
	private static final long serialVersionUID = 1L;
	private Model model = new Model();
	private Controller controller = new Controller(model);
	private View view = new View(controller);
	private MenuBar menuBar = new MenuBar(controller, this);
	private Application frame = new Application();


	/**
	 * Add the view to the content pane and register the view as observer of the
	 * model.
	 */
	public void init() {
		controller.setView(view);
		model.addObserver(view);
		model.notifyObservers();
		model.addObserver(menuBar);
		controller.setFrame(frame);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(view, BorderLayout.CENTER);
		setJMenuBar(menuBar);
	}

	/**
	 * Entry method of the application. Sets the preferred size and the minimum
	 * size of the JFrame.
	 */
	public static void main(String[] args) {
		Application frame = new Application();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Reglerdimensionierung nach Phasanegang-Methode");
		// To set an own logo:
		//frame.setIconImage(new ImageIcon(Application.class.getResource("resources/logo.png")).getImage());
		frame.setResizable(true);
		// Set preferred size of the JFrame in function of the inner screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
		int innerScreenWidth = screenSize.width - screenInsets.left - screenInsets.right;
		int innerScreenHeight = screenSize.height - screenInsets.top - screenInsets.bottom;
		Dimension minimumSize = new Dimension(800, 768);
		//1024x768 Pixel
		if (innerScreenWidth < minimumSize.width) {
			minimumSize.width = innerScreenWidth;
		}
		if (innerScreenHeight < minimumSize.height) {
			minimumSize.height = innerScreenHeight;
		}
		frame.setMinimumSize(minimumSize);
		frame.setPreferredSize(new Dimension((int) (0.6 * innerScreenWidth), (int) (0.6 * innerScreenHeight)));

		
		frame.init();
		frame.setVisible(true);
		frame.pack();
		frame.validate();
		
	}
	
	public void setWindowSize(int width, int height){
		setSize(new Dimension(width, height));
		setLocationRelativeTo(null);
		validate();
	}
}
