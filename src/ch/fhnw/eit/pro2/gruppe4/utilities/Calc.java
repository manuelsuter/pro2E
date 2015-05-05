package ch.fhnw.eit.pro2.gruppe4.utilities;

/*
 * Copyright (c) 2015: Anita Rosenberger, Raphael Frey, Benjamin Mueller, Florian Alber, Manuel Suter
 * 
 * Authors: Manuel Suter
 * 
 * */

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.TransformType;
import java.lang.Enum;
import org.apache.commons.math3.complex.Complex;

public class Calc {

	/**
	 * Berechnet linspace mittels startValue, endValue und length. Gibt ein
	 * Array mit den entsprechenden Werten zurück.
	 * 
	 * @param startValue
	 * @param endValue
	 * @param count
	 * @return
	 */

	public static double[] linspace(double startValue, double endValue,
			int count) {
		double delta = (endValue - startValue) / (count - 1);
		double[] array = new double[count];

		for (int i = 0; i < array.length; i++) {

			array[i] = startValue + delta * i;
		}
		return array;
	}

	/**
	 * Berechnet logspace mittels startValue, endValue und count. Generiert ein
	 * Array mit "count" Punkten logarithmisch zwischen 10^startValue und
	 * 10^endValue aufgespannt. Gibt ein Array mit den entsprechenden Werten
	 * zurück.
	 * 
	 * @param startValue
	 * @param endValue
	 * @param length
	 * @return
	 */
	public static double[] logspace(double startValue, double endValue,
			int count) {

		double logarithmicBase = 10;
		double delta = (endValue - startValue) / (count);
		double accDelta = 0.0;
		double[] array = new double[count + 1];
		for (int i = 0; i < array.length; i++) {
			array[i] = Math.pow(logarithmicBase, startValue + accDelta);
			accDelta += delta;
		}
		return array;
	}

	/**
	 * Berechnet den Frequenzgang aufgrund von Zähler- und Nennerpolynom b resp.
	 * a sowie der Frequenzachse f.
	 * 
	 * @param b
	 *            Zählerpolynom
	 * @param a
	 *            Nennerpolynom
	 * @param f
	 *            Frequenzachse
	 * @return Komplexwertiger Frequenzgang.
	 */
	public static final Complex[] freqs(double[] b, double[] a, double[] f) {
		Complex[] res = new Complex[f.length];

		for (int k = 0; k < res.length; k++) {
			Complex jw = new Complex(0, 2.0 * Math.PI * f[k]);

			Complex zaehler = new Complex(0, 0);
			for (int i = 0; i < b.length; i++) {
				zaehler = zaehler.add(jw.pow(b.length - i - 1).multiply(b[i]));
			}

			Complex nenner = new Complex(0, 0);
			for (int i = 0; i < a.length; i++) {
				nenner = nenner.add(jw.pow(a.length - i - 1).multiply(a[i]));
			}
			res[k] = zaehler.divide(nenner);
		}
		return res;
	}

	/**
	 * Sucht den Höchstwert von x und gibt dessen Index zurück. Kommt die der
	 * Höchstwert mehrfach vor, wird der Index des ersten Wertes ausgegeben.
	 * 
	 * @param array
	 * @return
	 */

	public static int diskMax(double[] array) {
		double largest = array[0];
		int index = 0;
		for (int i = 1; i < array.length; i++) {
			if (array[i] > largest) {
				largest = array[i];
				index = i;
			}
		}
		return index;
	}

	/**
	 * Setzt drei Arrays zu einem zusammen. Double-Version
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static double[] concat(double x[], double y[], double z[]) {
		double[] res = new double[x.length + y.length + z.length];
		for (int i = 0; i < x.length; i++) {
			res[i] = x[i];
		}
		for (int i = 0; i < y.length; i++) {
			res[i + x.length] = y[i];
		}
		for (int i = 0; i < z.length; i++) {
			res[i + x.length + y.length] = z[i];
		}
		return res;
	}

	/**
	 * Setzt drei Arrays zu einem zusammen. Complex-Version
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static Complex[] concat(Complex[] x, Complex[] y, Complex[] z) {
		Complex[] res = new Complex[x.length + y.length + z.length];
		for (int i = 0; i < x.length; i++) {
			res[i] = x[i];
		}
		for (int i = 0; i < y.length; i++) {
			res[i + x.length] = y[i];
		}
		for (int i = 0; i < z.length; i++) {
			res[i + x.length + y.length] = z[i];
		}
		return res;
	}

	/**
	 * Gibt die Werte des Array x in gewünschter Reihenfolge zurück (z.B.
	 * invertiert). Double-Version
	 * 
	 * @param x
	 * @param start
	 * @param stepsize
	 * @param stop
	 * @return
	 */

	public static double[] colonColon(double[] x, int start, int stepsize,	int stop) {
		double[] y = new double[(int)((stop - start) / stepsize)+1];
		// TODO: Vergleich so richtitg oder +1 und dann <
		int k = start;
		for (int i = 0; i < y.length; i++) {
			y[i] = x[k];
			k = k + stepsize;
		}
		return y;
	}

	/**
	 * Gibt die Werte des Array x in gewünschter Reihenfolge zurück (z.B.
	 * invertiert). Complex-Version!
	 * 
	 * @param x
	 * @param start
	 * @param stepsize
	 * @param stop
	 * @return
	 */
	public static Complex[] colonColon(Complex[] x, int start, int stepsize, int stop) {
		Complex[] y = new Complex[(int)((stop - start) / stepsize)+1];
		// TODO: Vergleich so richtitg oder +1 und dann <
		int k = start;
		for (int i = 0; i < y.length; i++) {
			y[i] = x[k];
			k = k + stepsize;
		}
		return y;
	}

	/**
	 * Gibt werte Reglerkonform als Array zurück.
	 * 
	 * @return
	 */
	public static double[] controllerConform(double Krk, double Tnk,
			double Tvk, double Tp, int controllerTyp) {
		double[] res = new double[3];
		if (controllerTyp == 2) {
			res[0] = Krk;
			res[1] = Tnk;
			res[2] = 0;

		}
		if (controllerTyp == 3) {

			res[0] = Krk * (1 + Tvk / Tnk); // Kr
			res[1] = Tnk + Tvk - Tp; // Tn
			res[2] = ((Tnk * Tvk) / (Tnk + Tvk - Tp)) - Tp; // Tv
		}
		return res;
	}

	/**
	 * Gibt Werte Bodekonform zurück.
	 * 
	 * @return
	 */
	public static double[] bodeConform(double Kr, double Tn, double Tv,
			double Tp, int controllerTyp) {
		double[] res = new double[3];
		if (controllerTyp == 2) { // PI-Regler
			res[0] = Kr;
			res[1] = Tn;
			res[2] = 0;
		}

		if (controllerTyp == 3) { // PID-Regler
			double e = Math.sqrt(1 - ((4 * Tn * (Tv - Tp)) / (Math.pow(
					(Tn + Tp), 2))));
			res[1] = 0.5 * (Tn + Tp) * (1 + e); // Tnk
			res[0] = 0.5 * Kr * (1 + (Tp / res[1]) * (1 + e)); // Krk
			res[2] = 0.5 * (Tn + Tp) * (1 - e); // Tvk
		}
		return res;
	}

	/**
	 * Berechnet die Inverse step fast furious transformation
	 * 
	 * @param zah
	 *            = Zähler
	 * @param nen
	 *            = Nenner
	 * @param fs
	 *            = Frequenz
	 * @param n
	 *            = Länge
	 * @return c = die Faltung
	 */
	public static double[][] schrittIfft(double[] zah, double[] nen, double fs,
			int n) {
		System.out.println(n+"n");
		double T = (1 / fs);// Periode
		double[] w = new double[(int) fs];// Kreisfrequenz
		Complex[] H;

		// Frequenzachse berechnen
		//TODO: evtl. 2*Pi wegen Umrechnung Kreisfrequenz.
		w = linspace(0.0, fs * Math.PI, n / 2);

		// Frequenzgang berechnen
		H = freqs(zah, nen, w);
		// Symmetrischen Vektor für Ifft erstellen:
		Complex[] tmp = new Complex[H.length];
		tmp = colonColon(H, (n / 2) - 1, -1, 1);
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = tmp[i].conjugate();
		}
		Complex x = new Complex(0);
		H = concat(colonColon(H, 0, 1, (n / 2) - 1), new Complex[]{x}, tmp);
		// Impulsantwort berechen
		Complex[] h = new Complex[H.length]; // welche Länge
		
		FastFourierTransformer f = new FastFourierTransformer(
				DftNormalization.STANDARD);
		h = f.transform(H, TransformType.INVERSE);

		// Schrittantwort berechnen
		double[] zwres = new double[2];
		zwres[0] = 1.0;
		zwres[1] = n + 1.0;
		double[] y = new double[h.length];
		double[] hReal = new double[h.length];
		for (int i = 0; i < h.length; i++) {
			hReal[i] = h[i].getReal();
		}
		y = diskConv(hReal, zwres);

		// Resultate ausschneiden
		y = colonColon(y, 0, 1, (int) ((y.length / 2) - 1));

		double[] t;
		t = linspace(0.0, (y.length - 1) * T, y.length);

		// für Output zusammmensetzen:
		double[][] res = new double[2][t.length + y.length];
		for (int i = 0; i < res.length; i++) {
			res[0][i] = t[i];
		}
		for (int j = 0; j < res.length; j++) {
			res[1][j] = y[j];
		}
		return res;
	}

	/**
	 * Ausmultiplizieren von Polynomen der Form: (1+x_1)*(1+x_2)*...
	 * 
	 * @param x
	 *            double[]
	 * @return res
	 */
	public static double[] poly(double[] x) {
		double[] res = new double[x.length];
		for (int i = 1; i < res.length; i++) {
			res = diskConv(x, res);
		}
		return res;
	}

	/**
	 * Berechnet die diskrete Faltung
	 * 
	 * @param b
	 *            = Nenner
	 * @param a
	 *            = Zähler
	 * @return c = die Faltung
	 */
	public static double[] diskConv(double[] b, double[] a) {
		double[] c = new double[a.length + b.length - 1];

		for (int n = 0; n < c.length; n++) {
			for (int i = Math.max(0, n - b.length + 1); i <= Math.min(
					a.length - 1, n); i++) {
				c[i] += a[i] * b[n - i];
			}
		}
		return c;
	}

	// TODO: classe final setzen um Überschreiben zu verhindern.

	/**
	 * Berechnet die Steigung der Funktionswerte y am Punkt x (x ist ein
	 * ArrayIndex)
	 * 
	 * @param ordinate
	 * @param abszisse
	 * @param index
	 * @return gradient
	 */

	public static double diskDiff(double[] ordinate, double[] abszisse,
			int index) {
		System.out.println(index);
		double gradient;
		double slope1 = (ordinate[index] - ordinate[index - 1])
				/ (abszisse[index] - abszisse[index - 1]);
		double slope2 = (ordinate[index + 1] - ordinate[index])
				/ (abszisse[index + 1] - abszisse[index]);
		gradient = (slope1 + slope2) / 2;

		return gradient;
	}

	/**
	 * Sucht den Wert im Array x der am nächsten bei xx liegt und gibt der
	 * ArrayIndex zurück. Es kann davon ausgegangen werden, dass x monoton
	 * steigend bzw. fallend ist.
	 * 
	 * @param array
	 * @param referenceValue
	 * @return previous Index
	 */
	public static int diskFind(double[] array, double referenceValue) {
		int length = array.length;
		//System.out.println(length + "length");
		//System.out.println(referenceValue + "ref");
		int index = (int) (Math.floor((double) (length / 2)));

		int lower_index = 0;
		int upper_index = length;

		int iterations = (int) (length / (Math.log((double) (length)) / Math
				.log((double) (2))));
		iterations = 10;
		if (array[0] > array[length - 1]) {
			// Monoton fallend

			for (int i = 0; i < iterations; i++) {
				index = (int) (Math.ceil((upper_index - lower_index) / 2
						+ lower_index));
				//System.out.println(index);

				double value = array[index];
				if (value > referenceValue) {
					lower_index = index;
				} else if (value < referenceValue) {
					upper_index = index;
				}

			}
		} else {
			// Monoton steigend

			for (int i = 0; i < iterations; i++) {
				index = (int) (Math
						.ceil((double) ((upper_index - lower_index) / 2 + lower_index)));
				// System.out.println(index);

				double value = array[index];
				if (value < referenceValue) {
					lower_index = index;
				} else if (value > referenceValue) {
					upper_index = index;
				}
			}
		}// TODO: Was ist denn hier los??
		return index; // Default return value, if program gets to here something
						// has gone wrong.
	}

	/**
	 * Berechnte Zähler und Nennerpolynom eines Reglers
	 * 
	 * @param controllerTyp
	 * @param Krk
	 * @param Tnk
	 * @param Tvk
	 * @param Tp
	 * @return koeffizienten
	 */

	public static double[][] UTFController(int controllerTyp, double Krk,
			double Tnk, double Tvk, double Tp) {
		double[][] koeffizienten;

		switch (controllerTyp) {
		// PI-Regler
		case 2:
			koeffizienten = new double[2][2];
			koeffizienten[0][0] = Krk * Tnk; // Koeffizient fuer s^1 im Zaehler
			koeffizienten[0][1] = Krk; // Koeffizient fuer s^0 im Zaehler
			koeffizienten[1][0] = Tnk; // Koeffizient fuer s^1 im Nenner
			koeffizienten[1][1] = 0; // Koeffizient fuer s^0 im Nenner
			break;

		// PID-Regler
		case 3:
			koeffizienten = new double[2][3];
			koeffizienten[0][0] = Krk * Tnk * Tvk; // Koeffizient fuer s^2 im
													// Zaehler
			koeffizienten[0][1] = Krk * (Tnk + Tvk); // Koeffizient fuer s^1 im
														// Zaehler
			koeffizienten[0][2] = Krk; // Koeffizient fuer s^0 im Zaehler
			koeffizienten[1][0] = 0; // Koeffizient fuer s^2 im Nenner
			koeffizienten[1][1] = Tnk; // Koeffizient fuer s^1 im Nenner
			koeffizienten[1][2] = 0; // Koeffizient fuer s^0 im Nenner
			break;
		default:
			koeffizienten = new double[0][0];
			break;
		}
		return koeffizienten;
	}

	/**
	 * Spline Interpolation mittels SplineNAK.
	 * 
	 * @param x
	 * @param y
	 * @param v
	 * @return
	 */
	public static double spline(double[] x, double[] y, double v) {
		double res;
		int t = x.length;
		double[] b = new double[t];
		double[] c = new double[t];
		double[] d = new double[t];

		SplineNAK.cubic_nak(t, x, y, b, c, d);
		res = SplineNAK.spline_eval(t, x, y, b, c, d, v);

		return res;
	}

	/**
	 * Berechnet die Sani-Methode gemäss m-File. Gibt die berechneten Werte in
	 * einem double-Array zurück.
	 * 
	 * @param Tu
	 * @param Tg
	 * @return
	 */

	public static final double[] sani(double Tu, double Tg) {
		double[][] t_Tg = {
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0.999250000046894, 0.922105092561432, 0.872737852273561,
						0.833461509613291, 0.800341962010093,
						0.771542032284433, 0.745995932100809,
						0.723018167273046, 0.702130707565877,
						0.682983266460938, 0.665313531493445,
						0.648915462929890, 0.633625357816225,
						0.619310444597175, 0.605860208611414,
						0.593183155818518, 0.581201200000870,
						0.569847829960794, 0.559066514407131,
						0.548806395297330, 0.539025594563050,
						0.529684417073429, 0.520748993039681,
						0.512190747804443, 0.503981237916122,
						0.496096670975454, 0.488515307160658,
						0.481217958107207, 0.474186371453051,
						0.467404155291639, 0.460856441372717,
						0.454530087543143, 0.448412458251188,
						0.442491949371856, 0.436757789958252,
						0.431200556164035, 0.425811330046404,
						0.420581686418264, 0.415503675452216,
						0.410569802592400, 0.405773408613285,
						0.401108785968687, 0.396569085193029,
						0.392149538874904, 0.387844720610165,
						0.383649449236732, 0.379559302643764,
						0.375570313796733, 0.371678244211578, 0.367879119353631 },
				{ 0.999250000046894, 0.922107226507570, 0.872706276845603,
						0.833361605121633, 0.800111546073877,
						0.771097724119365, 0.745242685546578,
						0.721837534132988, 0.700383821991980,
						0.680514995992060, 0.661948191603667,
						0.644461268448695, 0.627877595873421,
						0.612059132960723, 0.596897719808807,
						0.582306086183705, 0.568220562212031,
						0.554590276900457, 0.541376514985890,
						0.528550669715072, 0.516088829271881,
						0.503973004144231, 0.492188559362999,
						0.480723004733403, 0.469566751954415,
						0.458709736304676, 0.448144654234506,
						0.437862998703065, 0.427858978581321,
						0.418125181689911, 0.408654690816324,
						0.399441432101292, 0.390478995724202,
						0.381760964243981, 0.373281269660561,
						0.365033306249429, 0.357012021930694,
						0.349210130670993, 0.341622419205996,
						0.334243039579202, 0.327065703024670,
						0.320085264762181, 0.313295643246868,
						0.306691654237087, 0.300267868232360,
						0.294018709293405, 0.287939609135041,
						0.282024753911417, 0.276270103664858, 0.270670346520377 },
				{ 0.999250000046894, 0.922107383007651, 0.872706991643177,
						0.833361397855887, 0.800108253746043,
						0.771095689905440, 0.745232279015002,
						0.721815374889299, 0.700341885536653,
						0.680441606668994, 0.661827052844791,
						0.644270112315773, 0.627588325377797,
						0.611636578636921, 0.596297245136591,
						0.581477653916683, 0.567102929386445,
						0.553116686779398, 0.539469711319727,
						0.526124617395699, 0.513052230931280,
						0.500226838818050, 0.487630570437372,
						0.475246271637870, 0.463063174732648,
						0.451073467828470, 0.439271756323706,
						0.427655150468298, 0.416224008946267,
						0.404978464544689, 0.393922370556124,
						0.383058056123001, 0.372389450133720,
						0.361922388106304, 0.351659901289326,
						0.341607253543691, 0.331767527041523,
						0.322144209863999, 0.312741199913284,
						0.303559909246291, 0.294602677023430,
						0.285869897702871, 0.277362882178780,
						0.269081241188874, 0.261023593641581,
						0.253189274974545, 0.245576631237687,
						0.238182943238760, 0.231005644345429, 0.224041611282460 },
				{ 0.999250000046894, 0.922107386204618, 0.872707021166622,
						0.833361389915603, 0.800108014239022,
						0.771095670911707, 0.745231363741798,
						0.721814170206554, 0.700341271732737,
						0.680438732834134, 0.661820829342513,
						0.644260372195519, 0.627571307864492,
						0.611607849809358, 0.596250005261985,
						0.581404315486036, 0.566991462718628,
						0.552950861159532, 0.539230510275738,
						0.525788093760870, 0.512587309773262,
						0.499597377591177, 0.486792426117789,
						0.474149566008895, 0.461650524596818,
						0.449278625989688, 0.437023931579260,
						0.424875882713319, 0.412830251093512,
						0.400883151643765, 0.389035459222199,
						0.377290872455747, 0.365654937507293,
						0.354135585659649, 0.342743740810346,
						0.331489894466197, 0.320388295846054,
						0.309450823274010, 0.298692310129461,
						0.288125404908939, 0.277763026922911,
						0.267618545415953, 0.257702476131466,
						0.248024890602768, 0.238594678112852,
						0.229419888390040, 0.220505509006743,
						0.211856651017961, 0.203476168636770, 0.195366442349037 },
				{ 0.999250000046894, 0.922107386304068, 0.872707022373133,
						0.833361389448344, 0.800107994882976,
						0.771095670187503, 0.745231346645011,
						0.721814016760357, 0.700341225769634,
						0.680438949917661, 0.661821097589154,
						0.644260305589406, 0.627570553324408,
						0.611606388831749, 0.596247150797736,
						0.581397323848129, 0.566980025151684,
						0.552931155337183, 0.539199110097262,
						0.525738702318752, 0.512512210294866,
						0.499485704452879, 0.486630028385994,
						0.473917888903349, 0.461326699592958,
						0.448834507077613, 0.436423380162161,
						0.424076826210687, 0.411783700077532,
						0.399532292365415, 0.387316418761955,
						0.375131932326650, 0.362979301343037,
						0.350862912584675, 0.338788641599224,
						0.326768693821444, 0.314817167268473,
						0.302952549445796, 0.291194332406749,
						0.279566231159836, 0.268091715743216,
						0.256794606771567, 0.245699858164708,
						0.234830944607953, 0.224209780745966,
						0.213857862144830, 0.203793346515381,
						0.194031496954954, 0.184585678563632, 0.175467181065426 },
				{ 0.999250000046894, 0.922107386288222, 0.872707022439290,
						0.833361389414993, 0.800107993302535,
						0.771095670124957, 0.745231359164457,
						0.721813995220354, 0.700341219710685,
						0.680438994451325, 0.661821195050634,
						0.644260324430373, 0.627570689592192,
						0.611606173335252, 0.596246686541832,
						0.581396944869033, 0.566978072593320,
						0.552928772531950, 0.539194584755237,
						0.525730891262082, 0.512499924688483,
						0.499465464299391, 0.486597440293007,
						0.473868000316166, 0.461250632543531,
						0.448721174855950, 0.436257868942352,
						0.423841000859358, 0.411452559078191,
						0.399074833625737, 0.386694950113822,
						0.374300901474054, 0.361884593661133,
						0.349442334596058, 0.336973077598931,
						0.324482373055820, 0.311978787465898,
						0.299478296593374, 0.287001308129055,
						0.274573672360629, 0.262226061716083,
						0.249992037419453, 0.237909175406397,
						0.226015091151966, 0.214348097190746,
						0.202944998085060, 0.191841417790100,
						0.181068687349481, 0.170654592941339, 0.160622829762122 },
				{ 0.999250000046894, 0.922107386290876, 0.872707022434764,
						0.833361389408391, 0.800107993176708,
						0.771095670120400, 0.745231360700017,
						0.721813992151183, 0.700341218760999,
						0.680439002788325, 0.661821215491030,
						0.644260330362627, 0.627570727704774,
						0.611606128277022, 0.596246582576345,
						0.581396892401871, 0.566977869438002,
						0.552928761184156, 0.539194360826447,
						0.525730046755318, 0.512497602951593,
						0.499461665899058, 0.486591389276452,
						0.473857221713326, 0.461232430093355,
						0.448691908894446, 0.436212743264024,
						0.423771279955375, 0.411346367570837,
						0.398917864726327, 0.386466016891568,
						0.373975682467010, 0.361429568475193,
						0.348815409606839, 0.336125188660631,
						0.323353323961516, 0.310501401392281,
						0.297576338267395, 0.284593618929251,
						0.271575059285268, 0.258553071486739,
						0.245565559665930, 0.232658055653654,
						0.219881690268369, 0.207291574286736,
						0.194942276353630, 0.182889570650217,
						0.171184748835992, 0.159875589272547, 0.149002329926026 } };
		double[][] tu_Tg = {
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0.0157029801263305, 0.0271571529498953,
						0.0363196825836174, 0.0439252941842200,
						0.0503813327617681, 0.0559456983238202,
						0.0607966668280624, 0.0650628700158915,
						0.0688405029964393, 0.0722050777739125,
						0.0752158136096898, 0.0779207000716336,
						0.0803591595803553, 0.0825634502426004,
						0.0845610641353129, 0.0863749327707135,
						0.0880247265951525, 0.0895278176385718,
						0.0908982470294039, 0.0921497735646001,
						0.0932927532188940, 0.0943370270266599,
						0.0952923327305127, 0.0961654249296559,
						0.0969634425221808, 0.0976925747167021,
						0.0983587222759076, 0.0989665964371867,
						0.0995205956064692, 0.100024764890002,
						0.100483140124377, 0.100898957266245,
						0.101275169365446, 0.101614407521683,
						0.101919510920157, 0.102192936356091,
						0.102436814899147, 0.102652999386417,
						0.102843104364351, 0.103008902226630,
						0.103152504582243, 0.103274498030082,
						0.103376920461693, 0.103460795390657,
						0.103526980829199, 0.103576693397869,
						0.103611264693858, 0.103631458202308, 0.103638001849829 },
				{ 0, 0.0160872071342516, 0.0286067246677922,
						0.0394250299757505, 0.0492047597834787,
						0.0582917720132344, 0.0668909070863750,
						0.0751277372843415, 0.0830801677626933,
						0.0907954172253492, 0.0982978878949828,
						0.105596223733320, 0.112688122858629,
						0.119565833954140, 0.126218729640784,
						0.132633592868343, 0.138800683021220,
						0.144710712791817, 0.150356949691277,
						0.155736024102888, 0.160845445296686,
						0.165685591700014, 0.170258531528173,
						0.174567508103160, 0.178618063004463,
						0.182415096952277, 0.185966164948415,
						0.189277581361106, 0.192358603793756,
						0.195216771906142, 0.197860112716861,
						0.200297614472389, 0.202537798644273,
						0.204589035935676, 0.206459980890425,
						0.208158325979155, 0.209693713802004,
						0.211072285157959, 0.212302859950665,
						0.213393006988285, 0.214349192316317,
						0.215179303129814, 0.215889282162990,
						0.216486120912080, 0.216975961347951,
						0.217364199013857, 0.217657593435385,
						0.217860128575680, 0.217978648930902, 0.218017109421291 },
				{ 0, 0.0160950576774209, 0.0286661664881192,
						0.0396162441127304, 0.0496392546550365,
						0.0591104948706751, 0.0682559002191843,
						0.0772244322466593, 0.0861118160725877,
						0.0949806300778382, 0.103867494606055,
						0.112789721289430, 0.121750608015504,
						0.130744128533721, 0.139756469447806,
						0.148769982219454, 0.157763242283713,
						0.166715245182820, 0.175600244165446,
						0.184393125175265, 0.193068728132452,
						0.201599136088098, 0.209958792041090,
						0.218118872542195, 0.226053743230940,
						0.233738686557713, 0.241150139101732,
						0.248266605131661, 0.255070553513447,
						0.261544653450614, 0.267677750465630,
						0.273458121093045, 0.278878229075341,
						0.283935977266567, 0.288627246672896,
						0.292954331163085, 0.296918453877578,
						0.300524246601810, 0.303780026398736,
						0.306691849164696, 0.309270214028114,
						0.311523876473965, 0.313465938278298,
						0.315107796182504, 0.316460262400822,
						0.317537660810227, 0.318353469886297,
						0.318919666670065, 0.319249764579088, 0.319356722376953 },
				{ 0, 0.0160952178928490, 0.0286685927332962,
						0.0396279519849769, 0.0496747303283528,
						0.0591940891226900, 0.0684231532925582,
						0.0775246417254680, 0.0866091829670471,
						0.0957538227508478, 0.105012639813191,
						0.114421053048453, 0.123998715085140,
						0.133757425535747, 0.143700099847177,
						0.153826336111069, 0.164128775974877,
						0.174599235604527, 0.185224169149269,
						0.195987059401646, 0.206866807927387,
						0.217838161943087, 0.228871943139018,
						0.239933708862843, 0.250985979835526,
						0.261985420810400, 0.272889665304163,
						0.283649070555631, 0.294216423426261,
						0.304539896995679, 0.314570369574770,
						0.324260142476822, 0.333562627881172,
						0.342434560953168, 0.350838820372218,
						0.358739227042227, 0.366110329909029,
						0.372925803630798, 0.379171021653210,
						0.384832321311100, 0.389903178844198,
						0.394385698096024, 0.398281939617181,
						0.401600294880356, 0.404353502108427,
						0.406559662662216, 0.408234415993140,
						0.409400946147302, 0.410081406234037, 0.410302704761332 },
				{ 0, 0.0160952211636404, 0.0286686917639371,
						0.0396286687968967, 0.0496776263464281,
						0.0592026194780903, 0.0684436628146326,
						0.0775675352978332, 0.0866904067712640,
						0.0958961552602204, 0.105247058442032,
						0.114788338935829, 0.124551129063710,
						0.134560476335279, 0.144833528612753,
						0.155384558859314, 0.166225159695537,
						0.177363252890663, 0.188805060134947,
						0.200551973633267, 0.212602230259545,
						0.224948266728061, 0.237577761693111,
						0.250470673752919, 0.263602076146851,
						0.276937368927864, 0.290435865826760,
						0.304047369540712, 0.317717519626363,
						0.331379325336512, 0.344962304183805,
						0.358387775099772, 0.371574095170806,
						0.384437154799699, 0.396887019886811,
						0.408840034242122, 0.420212329549296,
						0.430928354952552, 0.440915794456741,
						0.450117177161206, 0.458481260867217,
						0.465966886923638, 0.472548284092046,
						0.478209096165816, 0.482943272250768,
						0.486760765535114, 0.489677278565338,
						0.491715200280913, 0.492907442880187, 0.493296560540650 },
				{ 0, 0.0160952212291315, 0.0286686958066412,
						0.0396287126829544, 0.0496778627561795,
						0.0592034899257405, 0.0684461787330135,
						0.0775736630838499, 0.0867036683616480,
						0.0959222999595385, 0.105294918885194,
						0.114870807962343, 0.124686589540605,
						0.134773631826841, 0.145157587185547,
						0.155862758913559, 0.166910981131401,
						0.178325641230913, 0.190126262581407,
						0.202331978060937, 0.214960327571723,
						0.228022744480295, 0.241528688222410,
						0.255481742997900, 0.269877819263446,
						0.284705288772230, 0.299944157319462,
						0.315564563465673, 0.331525043845386,
						0.347768723473007, 0.364229087828609,
						0.380822528611041, 0.397452442748389,
						0.414010156172669, 0.430371411264682,
						0.446405832734019, 0.461970832282928,
						0.476925217413433, 0.491126323023465,
						0.504437871957155, 0.516734717221401,
						0.527903473276973, 0.537853672469831,
						0.546510774141902, 0.553824593214286,
						0.559766086980210, 0.564333574589161,
						0.567541605730747, 0.569425598489057, 0.570039761437401 },
				{ 0, 0.0160952212299781, 0.0286686959710508,
						0.0396287153697719, 0.0496778820553848,
						0.0592035787470188, 0.0684464868053022,
						0.0775745384845594, 0.0867058335344022,
						0.0959271021144297, 0.105304686579670,
						0.114889322278975, 0.124719766132111,
						0.134830190415874, 0.145250193488588,
						0.156009189853831, 0.167135238849709,
						0.178660165322990, 0.190612797062030,
						0.203024119121947, 0.215925353122425,
						0.229345270420001, 0.243311734578196,
						0.257848204393897, 0.272974564173039,
						0.288704907996082, 0.305047020566389,
						0.321996704378612, 0.339539968689807,
						0.357648711337603, 0.376276716001491,
						0.395364205183415, 0.414822405307697,
						0.434543363811021, 0.454396614274416,
						0.474222695592066, 0.493842948575038,
						0.513055717921766, 0.531647596122446,
						0.549391814318611, 0.566069450406153,
						0.581463009596499, 0.595375510448405,
						0.607637521693837, 0.618115927122222,
						0.626707496187160, 0.633360498874517,
						0.638057350332667, 0.640827821446140, 0.641730145229768 }, };
		int n;

		if (Tu <= 0 | Tg <= 0) {
			throw new IllegalArgumentException("unsinnige Zeiten");
		}

		double v = Tu / Tg;

		if (v > 0.64173) {
			throw new IllegalArgumentException("Tu/Tg zu gross -> N > 8!");
		}

		if (v < 0.001) {
			throw new IllegalArgumentException("Tu/Tg zu klein -> N = 1!!");
		}

		double[] ri = Calc.linspace(0.0, 1.0, 50);

		// abhaengig von n werden vorberechnete Datenfiles von der Festplatte
		// geladen.
		// 2 <= n <= 8
		// n=1 ist trivial und fuehrt zu Abbruch .

		if (v <= 0.103638) {
			n = 2;
		} else if (v <= 0.218017) {
			n = 3;
		} else if (v <= 0.319357) {
			n = 4;
		} else if (v <= 0.410303) {
			n = 5;
		} else if (v <= 0.4933) {
			n = 6;
		} else if (v <= 0.5700) {
			n = 7;
		} else if (v <= 0.64173) {
			n = 8;
		} else {
			n = 10;
		}

		double r;
		double w;

		r = spline(tu_Tg[n - 1], ri, v);
		w = spline(ri, t_Tg[n - 1], r);

		double[] T = new double[8];
		T[n - 1] = w * Tg; // Erster Array-Wert.

		// Berechnen der weiteren Werten.
		for (int j = 0; j < n - 1; j++) {
			T[j] = T[n - 1] * Math.pow(r, n - (j + 1));
		}
		return T;
	}
}
