import java.io.*;
import java.util.zip.*;

public class Interpreter {
	
	public static int[] readTrainingLabels() throws IOException {
		InputStream fileStream = new FileInputStream("resources\\training\\training-labels.gz");
		InputStream gzipStream = new GZIPInputStream(fileStream);
		DataInputStream dis = new DataInputStream(gzipStream);
		
		int[] labels;
		
		dis.skipBytes(4);
		
		labels = new int[dis.readInt()];
		
		for(int x = 0; x < labels.length; x++) {
			labels[x] = dis.readUnsignedByte();
		}
		
		dis.close();
		
		return labels;
	}
	
	public static int[][] readTrainingImages() throws IOException {
		InputStream fileStream = new FileInputStream("resources\\training\\training-images.gz");
		InputStream gzipStream = new GZIPInputStream(fileStream);
		DataInputStream dis = new DataInputStream(gzipStream);
		
		int[][] pixels;
		
		dis.skipBytes(4);
		
		pixels = new int[dis.readInt()][dis.readInt() * dis.readInt()];
		
		for(int i = 0; i < pixels.length; i++) {
			for(int p = 0; p < pixels[i].length; p++) {
				pixels[i][p] = dis.readUnsignedByte();
			}
		}
		
		dis.close();
		
		return pixels;
	}
	
}
