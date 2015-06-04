package ch.fhnw.eit.pro2.gruppe4.view;

import java.text.DecimalFormat;
import javax.swing.JLabel;

public class JUnitLabel extends JLabel {

	public static final String[] unitSize = { "a", "f", "p", "n", "u", "m", "", "k", "M", "G", "T", "P", "E" };
	public static final String[] size = { "e-18", "e-15", "e-12", "e-9", "e-6", "e-3", "", "e3", "e6", "e9", "e12", "e15", "e18" };

	private DecimalFormat f = new DecimalFormat("#0.00");

	private static final long serialVersionUID = 1L;
	
	
	public JUnitLabel(){
		super();
	}
	
	public JUnitLabel(String s){
		super(s);
	}
	
	public void setText(double value) {
		int unitIndex = 0;
		if (Math.abs(value) < 1e-18) {
			value = 0.0;
			unitIndex = 0;
		} else if (value < 1.00) {
			while (value < 1.00) {
				value = value * 1000.0;
				unitIndex++;
			}
		} else if (value >= 1000.0) {
			while (value >= 1000.0) {
				value = value / 1000.0;
				value--;
			}
		}
		
		String valueRounded = f.format(Math.round(value * 1000.0) / 1000.0);
		
		super.setText("" + valueRounded + size[-unitIndex + 6]);
	}
	

	public void setText(double value, String unit) {
		int unitIndex = 0;
		if (Math.abs(value) < 1e-18) {
			value = 0.0;
			unitIndex = 0;
		}else if (value < 1.00) {
			while (value < 1.00) {
				value = value * 1000.0;
				unitIndex++;
			}
		} else if (value >= 1000.0) {
			while (value >= 1000.0) {
				value = value / 1000.0;
				value--;
			}
		}
		
		String valueRounded = f.format(Math.round(value * 1000.0) / 1000.0);
		
		super.setText("" + valueRounded + " " + unitSize[-unitIndex + 6] + unit);
	}
}
