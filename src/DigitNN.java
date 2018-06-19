import java.io.*;

public class DigitNN {

	public static void main(String[] args) throws IOException {
		int[] labelList = Interpreter.readTrainingLabels();
		int[][] imagePixelList = Interpreter.readTrainingImages();
		
		DigitImage[] imageList = new DigitImage[labelList.length];
		
		for(int x = 0; x < imageList.length; x++) {
			imageList[x] = new DigitImage(imagePixelList[x], labelList[x]);
		}
		
		shuffle(imageList);
		
		Network network = new Network();
		
		long startTime;
		
		for(int x = 0; x < 10000; x++) {
			startTime = System.currentTimeMillis();
			network.input(imageList[x].getPixelValues(), imageList[x].getTruth());
			BackProp.addToGradient(network.getGradientVector());
			System.out.println("Training (Trial "+x+") ("+(System.currentTimeMillis()-startTime)+" ms)");
			
			if(x % 1000 == 0 && x != 0) {
				System.out.println("Implementing Gradient "+(x/1000));
				network.implementGradient(BackProp.getInverseGradient());
				BackProp.reset();
			}
		}
		
		NetworkSaving.save(network);
	}
	
	public static <T> void shuffle(T[] arr) {
		for(int x = 0; x < arr.length; x++) {
			swap(arr, x, (int) (Math.random() * arr.length));
		}
	} 
	
	public static <T> void swap(T[] arr, int source, int destination) {
		T temp = arr[source];
		arr[source] = arr[destination];
		arr[destination] = temp;
	}

}
