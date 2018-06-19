
public class SigmoidLayer extends Layer {
	
	// Initializes a layer of SigmoidNeurons with a specified numOfNeurons
	// The size of the weight array is based of both numOfNeurons and sizeOfPreviousLayer
	public SigmoidLayer(int numOfNeurons, int sizeOfPreviousLayer) {
		neurons = new Neuron[numOfNeurons];
		
		for(int x = 0; x < numOfNeurons; x++) {
			neurons[x] = new SigmoidNeuron();
		}
		
		weights = new double[numOfNeurons][sizeOfPreviousLayer];
		
		for(int n = 0; n < numOfNeurons; n++) {
			for(int w = 0; w < sizeOfPreviousLayer; w++) {
				weights[n][w] = (Math.random() * 2) - 1; // This range of random numbers is somewhat arbitrary
			}
		}
		
		bias = (Math.random() * 2) - 1;// This range of random numbers is somewhat arbitrary
	}
	
	@Override
	public void input(double[] inputs) {
		
		for(int x = 0; x < neurons.length; x++) {
			neurons[x].input(inputs, weights[x], bias);
		}
	}
	
}
