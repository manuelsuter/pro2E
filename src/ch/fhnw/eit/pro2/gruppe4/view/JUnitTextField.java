package ch.fhnw.eit.pro2.gruppe4.view;

import java.text.DecimalFormat;

public class JUnitTextField extends JDoubleTextField {

	public static final String[] unitSize = { "a", "f", "p", "n", "u", "m", "", "k", "M", "G", "T", "P", "E" };
	public static final String[] size = { "e-18", "e-15", "e-12", "e-9", "e-6", "e-3", "", "e3", "e6", "e9", "e12",
			"e15", "e18" };

	private DecimalFormat f = new DecimalFormat("#0.00");

	private static final long serialVersionUID = 1L;

	public JUnitTextField(String text, int columns, boolean allowNegativeValues) {
		super(text, columns, allowNegativeValues);
	}

	public void setText(double value) {
		int unitIndex = 0;
		if (Math.abs(value) < 1e-18) {
			value = 0.0;
			unitIndex = 0;
		} else if (Math.abs(value) < 1.00) {
			while (Math.abs(value) < 1.00) {
				value = value * 1000.0;
				unitIndex++;
			}
		} else if (Math.abs(value) >= 1000.0) {
			while (Math.abs(value) >= 1000.0) {
				value = value / 1000.0;
				unitIndex--;
			}
		}

		String valueRounded = f.format(Math.round(value * 100.0) / 100.0);

		super.setText(valueRounded + size[-unitIndex + 6]);
	}

	public void setText(double value, String unit) {
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
				unitIndex--;
			}
		}

		String valueRounded = f.format(Math.round(value * 100.0) / 100.0);

		super.setText(valueRounded + unitSize[-unitIndex + 6] + unit);
	}
}
