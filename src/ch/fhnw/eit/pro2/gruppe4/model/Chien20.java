/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * Authors: Anita Rosenberger
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

package ch.fhnw.eit.pro2.gruppe4.model;

public class Chien20 extends Controller {

	/**
	 * Setzt Calculationtyp.
	 */
	public Chien20() {
		CALCULATIONTYP = 3;
	}

	/**
	 * Berechnet Reglerwerte mittels Faustformel Chien/Hrones/Reswick 20% und
	 * löst setUTFControllerConf() aus.
	 * 
	 * @throws ControllerException
	 */
	protected void calculate() throws ControllerException {
		double[] inputValues = path.getInputValues();
		double Ks = inputValues[Path.KsPOS];
		double Tg = inputValues[Path.TgPOS];
		double Tu = inputValues[Path.TuPOS];

		switch (controllerTyp) {
		// PI Regler:
		case Controller.PI:
			Kr = 0.6 * Tg / (Ks * Tu);
			Tn = Tg;
			Tv = 0;
			break;
		// PID Regler:
		case Controller.PID:
			Kr = 0.95 * Tg / (Ks * Tu);
			Tn = 1.35 * Tg;
			Tv = 0.47 * Tu;
			break;
		default:
			throw new ControllerException();
		}

		setUTFcontrollerConf();
	}
}
