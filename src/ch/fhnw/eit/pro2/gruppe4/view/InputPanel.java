/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * Authors: Manuel Suter
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */

package ch.fhnw.eit.pro2.gruppe4.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class InputPanel extends JPanel implements ActionListener, ItemListener, KeyListener, DocumentListener {

	private static final long serialVersionUID = 1L;
	private GUIController guiController;
	public JDoubleTextField tfKs, tfTu, tfTg;
	public JButton btBerechne, btClear;
	private JCheckBox checkBoxPlot;
	public JLabel lbMessage, lbOrder;

	/**
	 * Setzt GridBagLayout. Erzeugt die Textfelder der Eingabewerte sowie die
	 * Button btBerechne und btClear. Besitzt die CheckBox checkBoxPlot zum ein-
	 * und ausblenden des rightPanels.
	 * 
	 * @param controller
	 */
	public InputPanel(GUIController controller) {
		super(new GridBagLayout());
		this.guiController = controller;
		setBorder(MyBorderFactory.createMyBorder(" Schrittantwort vermessen "));

		add(new JLabel("<html><i>K<sub>s</sub></i></html>"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 10, 5, 10), 0, 0));

//		tfKs = new JDoubleTextField("2.0", 100, false);
		tfKs = new JDoubleTextField("2.0", 100, false);

		tfKs.addKeyListener(this);
		tfKs.getDocument().addDocumentListener(this);
		add(tfKs, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 5, 10), 0, 0));

		lbOrder = new JLabel("Ordnung: ");
		add(lbOrder, new GridBagConstraints(3, 0, 2, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 5, 10), 0, 0));

		add(new JLabel("<html><i>T<sub>u</sub></i></html>"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 10, 5, 10), 0, 0));

//		tfTu = new JDoubleTextField("1.1", 100, false);
		tfTu = new JDoubleTextField("1.93e-3", 100, false);
		tfTu.addKeyListener(this);
		tfTu.getDocument().addDocumentListener(this);
		add(tfTu, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 5, 10), 0, 0));

		add(new JLabel("s"), new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
				GridBagConstraints.NONE, new Insets(10, 0, 5, 10), 0, 0));

		add(lbMessage = new JLabel("      "), new GridBagConstraints(0, 3, 4, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 10, 0, 10), 0, 0));

		add(new JLabel("<html><i>T<sub>g</sub></i></html>"), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(10, 10, 5, 10), 0, 0));

//		tfTg = new JDoubleTextField("8.9", 100, false);
		tfTg = new JDoubleTextField("9.46e-3", 100, false);

		tfTg.addKeyListener(this);
		tfTg.getDocument().addDocumentListener(this);
		add(tfTg, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 5, 10), 0, 0));

		add(new JLabel("s"), new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
				GridBagConstraints.NONE, new Insets(10, 0, 5, 10), 0, 0));

		btBerechne = new JButton("Berechnen");
		add(btBerechne, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 40, 0));
		btBerechne.addActionListener(this);

		btClear = new JButton("Löschen");
		add(btClear, new GridBagConstraints(2, 4, 2, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
				GridBagConstraints.NONE, new Insets(10, 0, 10, 10), 49, 0));
		btClear.addActionListener(this);

		checkBoxPlot = new JCheckBox("erweitert", true);
		add(checkBoxPlot, new GridBagConstraints(4, 4, 2, 1, 0.0, 0.0, GridBagConstraints.LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
		checkBoxPlot.addItemListener(this);
	}

	/**
	 * Löst bei gedrücktem btBerechne calculate() und bei gedrücktem btClear
	 * clear() des guiControllers auf.
	 * @param e
	 */

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btBerechne) {
			guiController.calculate();
		}
		if (e.getSource() == btClear) {
			guiController.clear();
		}
	}

	/**
	 * Prüft ob checkBoxPlot selektiert ist. Ruft setVisibility() des
	 * guiControllers auf und übergibt Status der CheckBox.
	 * @param e
	 */
	public void itemStateChanged(ItemEvent e) {
		if (checkBoxPlot.isSelected() == false) {
			guiController.setVisibility(false);
		}

		if (checkBoxPlot.isSelected() == true) {
			guiController.setVisibility(true);
		}
	}

	/**
	 * Wenn Enter-Taste gedrückt, wird calculate() vom guiController aufgerufen.
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) {
			guiController.calculate();
		}
	}

	/**
	 * Kein Verwendung.
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * Kein Verwendung.
	 * @param e
	 */
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Erkennt geänderte in Textfelder und setzt Hinweis darüber in lbMessage.
	 * @param e
	 */
	public void changedUpdate(DocumentEvent e) {
		lbMessage.setForeground(Color.magenta);
		lbMessage.setText("Nicht berechnete Änderungen");
	}

	/**
	 * Erkennt geänderte in Textfelder und setzt Hinweis darüber in lbMessage.
	 * @param e
	 */
	public void insertUpdate(DocumentEvent e) {
		lbMessage.setForeground(Color.magenta);
		lbMessage.setText("Nicht berechnete Änderungen");
	}

	/**
	 * Erkennt geänderte in Textfelder und setzt Hinweis darüber in lbMessage.
	 * param e
	 */
	public void removeUpdate(DocumentEvent e) {
		lbMessage.setForeground(Color.magenta);
		lbMessage.setText("Nicht berechnete Änderungen");
	}

	/**
	 * Nicht in Verwendung bis dato.
	 * 
	 * @param obs
	 * @param obj
	 */
	public void update(Observable obs, Object obj) {

	}

}
