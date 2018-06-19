
public class InputLayer extends Layer {
	
	// Initializes the layer with a certain amount of Neurons
	public InputLayer(int numOfNeurons) {
		neurons = new Neuron[numOfNeurons];
		
		for(int x = 0; x < numOfNeurons; x++) {
			neurons[x] = new Neuron();
		}
	}
	
	@Override
	public void input(double[] inputs) {
		for(int x = 0; x < neurons.length; x++) {
			neurons[x].input(inputs[x]);
		}
	}
	
	@Override
	public int getNumOfWeights() {
		return 0;
	}
	
}
