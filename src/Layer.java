
public abstract class Layer {
	
	// The array holding all the Neurons of the layer
	protected Neuron[] neurons;
	// The weights connecting the Neurons of the last layer to the Neurons of this layer
	protected double[][] weights;
	// The bias added to the raw value of each neuron
	protected double bias;
	
	// Takes the values of the previous layer's Neurons and determines the value of this layer's Neurons
	public abstract void input(double[] inputs);
	
	/*
	 * Set and Modification Methods
	 */
	// Sets a specified weight to a newValue
	public void setWeight(int destinationIndex, int sourceIndex, double newValue) {
		weights[destinationIndex][sourceIndex] = newValue;
	}
	
	// Sets weight 0 0 to a newValue
	// This is used for basic tests of the network
	public void setWeight(double newValue) {
		setWeight(0, 0, newValue);
	}
	
	public void addToWeight(int destinationIndex, int sourceIndex, double amount) {
		weights[destinationIndex][sourceIndex] += amount;
	}
	
	// Sets the bias of the layer to a newValue
	public void setBias(double newValue) {
		bias = newValue;
	}
	
	public void addToBias(double amount) {
		bias += amount;
	}
	
	
	/*
	 * Get Methods
	 */
	// Returns the an array holding all the Neurons in this layer
	public Neuron[] getNeurons() {
		return neurons;
	}
	
	// Returns a specified Neuron
	public Neuron getNeuron(int index) {
		return neurons[index];
	}
	
	// Returns a double array containing the values of all the Neurons in this layer
	public double[] getNeuronValues() {
		double[] values = new double[getSize()];
		
		for(int x = 0; x < neurons.length; x++) {
			values[x] = neurons[x].getValue();
		}
		
		return values;
	}
	
	// Returns the value of a specified Neuron
	public double getNeuronValue(int index) {
		return neurons[index].getValue();
	}
	
	// Returns the value of a specified weight
	public double getWeight(int destinationIndex, int sourceIndex) {
		return weights[destinationIndex][sourceIndex];
	}
	
	// Returns the double array containing all the weights of the layer
	public double[][] getWeightArray() {
		return weights;
	}
	
	// Returns the number of weights in this layer
	public int getNumOfWeights() {
		int num = 0;
		
		for(int x = 0; x < weights.length; x++) {
			num += weights[x].length;
		}
		
		return num;
	}
	
	// Returns the bias of the layer
	public double getBias() {
		return bias;
	}
	
	// Returns the amount of Neurons in this layer
	public int getSize() {
		return neurons.length;
	}
	
}
