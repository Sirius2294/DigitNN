
public class BackProp {
	
	private static double[] gradient;
	
	public static void reset() {
		gradient = null;
	}
	
	public static void addToGradient(double[] vector) {
		if(gradient == null) {
			gradient = vector;
		}
		else {
			for(int x = 0; x < gradient.length; x++) {
				gradient[x] = (gradient[x] + vector[x]) / 2.0;
			}
		}
	}
	
	public static double[] getGradient() {
		return gradient;
	}
	
	public static double[] getInverseGradient() {
		double[] inverse = new double[gradient.length];
		
		for(int x = 0; x < inverse.length; x++) {
			inverse[x] = -1 * gradient[x];
		}
		
		return inverse;
	}
}
