import java.awt.*;
import javax.swing.*;

public class DigitImage {
	
	private double[] pixelValues;
	private double[] truth;
	private int[] rawValues;
	
	private int value;
	
	public static final int WIDTH = 28;
	public static final int HEIGHT = 28;
	
	public DigitImage(int[] rawPixelValues, int label) {
		rawValues = rawPixelValues;
		pixelValues = new double[rawPixelValues.length];
		
		for(int x = 0; x < pixelValues.length; x++) {
			pixelValues[x] = rawPixelValues[x] / 255.0;
		}
		
		truth = new double[10];
		value = label;
		
		for(int x = 0; x < truth.length; x++) {
			if(x == label)
				truth[x] = 1.0;
			else
				truth[x] = 0.0;
		}
	}
	
	public void display() {
		JFrame frame = new JFrame(Integer.toString(value));
		frame.setSize(280, 320);
		
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				for(int x = 0; x < rawValues.length; x++) {
					int value = 255 - rawValues[x];
					g.setColor(new Color(value, value, value));
					g.fillRect((x % 28) * 10, (x / 28) * 10, 10, 10);
				}
				
				
			}
		};
		
		frame.getContentPane().add(panel);
		panel.repaint();
		frame.setVisible(true);
	}
	
	public double[] getPixelValues() {
		return pixelValues;
	}
	
	public double[] getTruth() {
		return truth;
	}
	
	public int getValue() {
		return value;
	}

}
