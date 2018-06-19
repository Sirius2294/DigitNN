import java.io.*;

public class Network {
	// The layer array that holds the neurons of the network
	private Layer[] layers;
	// The desired output values of the network
	private double[] truth;
	
	// Initializes the network in a predefined, hard-coded structure, defined in the constructor
	public Network() {
		layers = new Layer[4];
		layers[0] = new InputLayer(784);
		layers[1] = new SigmoidLayer(16, layers[0].getSize());
		layers[2] = new SigmoidLayer(16, layers[1].getSize());
		layers[3] = new SigmoidLayer(10, layers[2].getSize());
	}
	
	public Network(String sourceFolder) throws IOException {
		FileInputStream fis = new FileInputStream(sourceFolder + "structure.zip");
		DataInputStream dis = new DataInputStream(fis);
		
		layers = new Layer[dis.readInt()];
		layers[0] = new InputLayer(dis.readInt());
		for(int x = 1; x < layers.length; x++) {
			layers[x] = new SigmoidLayer(dis.readInt(), layers[x-1].getSize());
		}
		
		dis.close();
		
		for(int x = 1; x < layers.length; x++) {
			fis = new FileInputStream(sourceFolder + "layer"+x+".zip");
			dis = new DataInputStream(fis);
			
			layers[x].setBias(dis.readDouble());
			
			for(int i = 0; i < layers[x].getWeightArray().length; i++) {
				for(int z = 0; z < layers[x].getWeightArray()[i].length; z++) {
					layers[x].setWeight(i, z, dis.readDouble());
				}
			}
		}
	}
	
	// This method runs a set of values through the network and returns the output
	// No backprop takes place with this method call, and derivatives are entered into the vector
	public int input(double[] inputValues) {
		// Passes inputValues into the input layer of the network
		layers[0].input(inputValues);
		
		for(int x = 1; x < layers.length; x++) {
			// Inputs the values of each layer to the next
			layers[x].input(layers[x-1].getNeuronValues());
		}
		
		// Returns the output of the network
		return getLargestIndex(layers[layers.length-1].getNeuronValues());
	}
	
	// This method runs a set of values throgh the network, calculates the cost of the trial, and returns the cost
	// No backprop takes place with this method call, however derivatives are entered into the vector
	public double input(double[] inputValues, double[] truth) {
		this.truth = truth;
		
		// Passes inputValues into the input layer of the network
		layers[0].input(inputValues);
		
		for(int x = 1; x < layers.length; x++) {
			// Inputs the values of each layer to the next
			layers[x].input(layers[x-1].getNeuronValues());
		}
		
		double[] results = layers[layers.length-1].getNeuronValues();
		
		// Returns the cost of the trial
		return calculateCost(results, truth);
	}
	
	// This method takes the output of the network, and the known truth and calculates the cost of the trial
	public double calculateCost(double[] results, double[] truth) {
		double cost = 0;
		
		for(int x = 0; x < results.length; x++) {
			cost += Math.pow(results[x] - truth[x], 2);
		}
		
		return cost;
	}
	
	// This method returns the derivative of the cost function with respect to a specified weight
	public double getWeightDerivative(int sourceLayer, int sourceIndex, int destinationIndex) {
		double derivative = 1;
		
		//System.out.println("layers[sourceLayer].getNeuronValue(sourceIndex) = " + layers[sourceLayer].getNeuronValue(sourceIndex));
		//System.out.println("SigmoidNeuron.getDerivative(layers[sourceLayer+1].getNeuron(destinationIndex).getZ() = " + SigmoidNeuron.getDerivative(layers[sourceLayer+1].getNeuron(destinationIndex).getZ()));
		
		derivative *= layers[sourceLayer].getNeuronValue(sourceIndex);
		derivative *= SigmoidNeuron.getDerivative(layers[sourceLayer+1].getNeuron(destinationIndex).getZ());
		
		derivative *= getActivationDerivative(sourceLayer+1, destinationIndex);
		
		return derivative;
	}
	
	// This method returns the derivative of the cost function with respect to the specified bias
	public double getBiasDerivative(int layer) {
		double derivative = 0;
		double chainDerivative;
		
		for(int x = 0; x < layers[layer].getSize(); x++) {
			chainDerivative = 1;
			
			chainDerivative *= SigmoidNeuron.getDerivative(layers[layer].getNeuron(x).getZ());
			
			chainDerivative *= getActivationDerivative(layer, x);
			
			
			derivative += chainDerivative;
		}
		
		return derivative;
	}
	
	// This method returns the derivative of the cost function with respect to the specified activation
	public double getActivationDerivative(int layer, int index) {
		double derivative = 0;
		
		if(layer == layers.length-1) {
			return 2 * (layers[layer].getNeuronValue(index) - truth[index]);
		}
		else {
			for(int x = 0; x < layers[layer+1].getSize(); x++) {
				double chainDerivative = 1;
				
				chainDerivative *= layers[layer+1].getWeight(x, index);
				chainDerivative *= SigmoidNeuron.getDerivative(layers[layer+1].getNeuron(x).getZ());
				
				chainDerivative *= getActivationDerivative(layer+1, x);
				
				
				derivative += chainDerivative;
			}
			
			return derivative;
		}
	}
	
	// This method returns the gradient vector of the cost function for the latest trial for a single layer
	public double[] getGradientVector(int layer) {
		double[] derivatives = new double[layers[layer].getNumOfWeights() + 1];
		int counter = 0;
		
		derivatives[counter] = getBiasDerivative(layer);
		counter++;
		
		for(int x = 0; x < layers[layer].getSize(); x++) {
			for(int i = 0; i < layers[layer-1].getSize(); i++) {
				derivatives[counter] = getWeightDerivative(layer-1, i, x);
				counter++;
			}
		}
		
		return derivatives;
	}
	
	// This method returns the gradient vector of the cost function for the latest trial
	public double[] getGradientVector() {
		double[] derivatives = new double[getNumOfParameters()];
		double[] partialDerivatives;
		int counter = 0;
		
		for(int layer = 1; layer < layers.length; layer++) {
			partialDerivatives = getGradientVector(layer);
			
			for(int x = 0; x < partialDerivatives.length; x++) {
				derivatives[counter] = partialDerivatives[x];
				counter++;
			}
		}
		
		return derivatives;
	}
	
	// This method calculates and prints the gradient vector of the cost function for the latest trial
	public void printDerivativeVector() {
		double[] vector = getGradientVector();
		int counter = 0;
		
		for(int layer = 1; layer < layers.length; layer++) {
			System.out.println("\nLAYER " + layer);
			System.out.println("bias: " + vector[counter]);
			counter++;
			for(int destinationIndex = 0; destinationIndex < layers[layer].getSize(); destinationIndex++) {
				for(int sourceIndex = 0; sourceIndex < layers[layer-1].getSize(); sourceIndex++) {
					System.out.println("Weight (from "+sourceIndex+" to "+destinationIndex+"): " + vector[counter]);
					counter++;
				}
			}
		}
	}
	
	public void implementGradient(double[] inverseGradient) {
		int counter = 0;
		
		for(int layer = 1; layer < layers.length; layer++) {
			layers[layer].addToBias(inverseGradient[counter]);
			counter++;
			for(int destinationIndex = 0; destinationIndex < layers[layer].getSize(); destinationIndex++) {
				for(int sourceIndex = 0; sourceIndex < layers[layer-1].getSize(); sourceIndex++) {
					layers[layer].addToWeight(destinationIndex, sourceIndex, inverseGradient[counter]);
					counter++;
				}
			}
		}
	}
	
	public void implementGradient(BackProp backProp) {
		implementGradient(backProp.getInverseGradient());
	}
	
	/*
	 * Get Methods
	 */
	// Returns the total amount of parameters for the cost function of the network
	public int getNumOfParameters() {
		int size = layers.length;
		
		for(int x = 0; x < layers.length; x++) {
			size += layers[x].getNumOfWeights();
		}
		
		return size;
	}
	
	// Returns the layer object at a specified index
	public Layer getLayer(int index) {
		return layers[index];
	}
	
	// Returns the number of layers in the network
	public int getNumOfLayers() {
		return layers.length;
	}
	
	public int getLargestIndex(double[] values) {
		int largest = 0;
		for(int x = 0; x < values.length; x++) {
			if(values[x] > values[largest]) {
				largest = x;
			}
		}
		
		return largest;
	}
}
