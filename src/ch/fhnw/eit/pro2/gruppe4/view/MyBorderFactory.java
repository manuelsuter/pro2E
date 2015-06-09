/*
 * Copyright (c) 2015: Richard Gut
 * Authors: Richard Gut
 * 
 */

package ch.fhnw.eit.pro2.gruppe4.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class MyBorderFactory {
	/**
	 * Setzt Rahmen mit Titel.
	 * 
	 * @param title
	 * @return titled
	 */
	public static Border createMyBorder(String title) {
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		TitledBorder titled = BorderFactory.createTitledBorder(loweredetched, title);
		return titled;
	}

	/**
	 * Setzt Rahmen mit Titel und Farbe.
	 * 
	 * @param title
	 * @param color
	 * @return titled
	 */
	public static Border createMyBorder(String title, Color color) {
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		TitledBorder titled = BorderFactory.createTitledBorder(loweredetched, title);
		titled.setTitleColor(color);
		return titled;
	}

	/**
	 * Setzt Rahmen.
	 * 
	 * @return loweredetched
	 */
	public static Border createMyBorder() {
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		return loweredetched;
	}
}
