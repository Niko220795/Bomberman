package view;

import java.awt.image.BufferedImage;

public class GreenVillageView {

	private BufferedImage[] tileSamples;
	private BufferedImage[] exploding_block = new BufferedImage[6];
	
	public BufferedImage[] getTileSamples() {
		return tileSamples;
	}

	public BufferedImage[] getExploding_block() {
		return exploding_block;
	}
	
	public BufferedImage getTileSamples(int i) {
		return tileSamples[i];
	}
	

}
