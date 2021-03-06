import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class MyBorderFactory {
	public static Border createMyBorder(String title) {
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		TitledBorder titled = BorderFactory.createTitledBorder(loweredetched, title);
		return titled;
	}

	public static Border createMyBorder(String title, Color color) {
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		TitledBorder titled = BorderFactory.createTitledBorder(loweredetched, title);
		titled.setTitleColor(color);
		return titled;
	}

	public static Border createMyBorder() {
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		return loweredetched;
	}
}
