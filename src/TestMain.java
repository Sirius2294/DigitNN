import java.io.*;

public class TestMain {
	
	public static void main(String[] args) throws IOException {
		int[] labelList = Interpreter.readTrainingLabels();
		int[][] imageValues = Interpreter.readTrainingImages();
		
		DigitImage[] images = new DigitImage[labelList.length];
		for(int x = 0; x < images.length; x++) {
			images[x] = new DigitImage(imageValues[x], labelList[x]);
		}
		
		images[(int) (Math.random() * 60000)].display();
	}

}
