import java.io.*;

public class NetworkSaving {
	
	public static void save(Network network) throws IOException {
		FileOutputStream fos = new FileOutputStream("resources\\network\\structure.zip");
		DataOutputStream dos = new DataOutputStream(fos);
		
		dos.writeInt(network.getNumOfLayers());
		for(int x = 0; x < network.getNumOfLayers(); x++) {
			dos.writeInt(network.getLayer(x).getSize());
		}
		
		dos.close();
		
		for(int x = 1; x < network.getNumOfLayers(); x++) {
			fos = new FileOutputStream("resources\\network\\layer"+x+".zip");
			dos = new DataOutputStream(fos);
			Layer layer = network.getLayer(x);
			
			dos.writeDouble(layer.getBias());
			
			double[][] weights = layer.getWeightArray();
			
			for(int i = 0; i < weights.length; i++) {
				for(int z = 0; z < weights[i].length; z++) {
					dos.writeDouble(weights[i][z]);
				}
			}
			
			dos.close();
		}
	}

}
