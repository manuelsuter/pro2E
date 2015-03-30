public class ModelCalc {

	public static double[] linspace(double startValue, double endValue, int lenght) {
		double delta = (endValue - startValue) / (lenght - 1);
		double[] array = new double[lenght];

		for (int i = 0; i < array.length; i++) {

			array[i] = startValue + delta * i;
		}
		return array;
	}
}
