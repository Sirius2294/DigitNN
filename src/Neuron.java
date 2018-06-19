
public class Neuron {
	
	// The finalized value of the Neuron
	protected double value = 0;
	// The result of the inputs, weights, and the bias, without any transformation
	// This is used in the calculation of derivatives
	protected double z;
	
	// Combines the inputs, weights, and the bias to calculate z and value
	public void input(double[] inputs, double[] weights, double bias) {
		value = 0;
		
		for(int x = 0; x < inputs.length; x++) {
			value += inputs[x] * weights[x];
		}
		
		z = value + bias;
		
		value += bias;
	}
	
	// Sets the value of the Neuron based on a single input
	public void input(double input) {
		value = input;
	}
	
	// Returns the value of the Neuron
	public double getValue() {
		return value;
	}
	
	// Returns the z of the Neuron
	public double getZ() {
		return z;
	}
}
