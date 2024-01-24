package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Menu extends JPanel {

	private static final long serialVersionUID = 1L;
	
	BufferedImage sfondo;
	BufferedImage game_over;
	BufferedImage next_level;

	public Menu() {
		this.setPreferredSize(new Dimension(768,576));
		try {
			sfondo = ImageIO.read(new File("src/resources/menu/bg-menu.png"));
			game_over = ImageIO.read(new File("src/resources/menu/game_over.png"));
//			next_level = ImageIO.read(new File("src/resources/menu/next_level.png"));
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(sfondo, 0, 0, sfondo.getWidth(), sfondo.getHeight(), null);
	}

}
