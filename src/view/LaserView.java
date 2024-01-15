package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LaserView{
	BufferedImage h_sprite;
	BufferedImage v_sprite;
	
	public LaserView() {
		try {
			h_sprite = ImageIO.read(new File("src/resources/enemy/Laserer/laser_1.png"));
			v_sprite = ImageIO.read(new File("src/resources/enemy/Laserer/laser_2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BufferedImage getH_sprite() {
		return h_sprite;
	}

	public BufferedImage getV_sprite() {
		return v_sprite;
	}
	
	
}
