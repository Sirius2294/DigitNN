
public class SigmoidNeuron extends Neuron{
	
	@Override
	public void input(double[] inputs, double[] weights, double bias) {
		value = 0;
		
		for(int x = 0; x < inputs.length; x++) {
			value += inputs[x] * weights[x];
		}
		
		z = value + bias;
		
		value = sigmoid(value + bias);
	}
	
	// Returns the output of the mathematical Sigmoid (s-shaped) function
	// This is used to squash all our values between 0 and 1
	public static double sigmoid(double num) {
		return 1.0 / (1 + Math.pow(Math.E, -1 * num));
	}
	
	// Returns the derivative of the mathematical Sigmoid (s-shaped) function
	public static double getDerivative(double z) {
		return sigmoid(z) * (1 - sigmoid(z));
	}
	
}
