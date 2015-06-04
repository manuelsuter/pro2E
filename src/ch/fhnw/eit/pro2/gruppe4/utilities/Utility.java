package ch.fhnw.eit.pro2.gruppe4.utilities;

/*
 * Copyright (c) 2015: Richard Gut
 * Authors: Richard Gut
 * */

import java.awt.Container;
import java.awt.Image;
import java.awt.MediaTracker;

import javax.swing.ImageIcon;

public class Utility {

	private static Container p = new Container();

	public static Image loadImage(String strBild) {
		MediaTracker tracker = new MediaTracker(p);
		Image img = (new ImageIcon(strBild)).getImage();
		tracker.addImage(img, 0);
		try {
			tracker.waitForID(0);
		} catch (InterruptedException ex) {
			System.out.println("Can not load image: " + strBild);
		}
		return img;
	}

	public static Image loadResourceImage(String strBild) {
		MediaTracker tracker = new MediaTracker(p);
		Image img = (new ImageIcon(Utility.class.getResource("images/" + strBild))).getImage();
		tracker.addImage(img, 0);
		try {
			tracker.waitForID(0);
		} catch (InterruptedException ex) {
			System.out.println("Can not load image: " + strBild);
		}
		return img;
	}

	public static ImageIcon loadResourceIcon(String strBild) {
		ImageIcon icon = new ImageIcon(Utility.class.getClassLoader().getResource("images/" + strBild));
		return icon;
	}
}
