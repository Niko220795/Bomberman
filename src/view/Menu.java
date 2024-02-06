package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
			next_level = ImageIO.read(new File("src/resources/menu/bg-menu.png"));
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		var g2d = next_level.createGraphics();
		g2d.setPaint(Color.ORANGE);
	    g2d.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 40));
	    String s = "LEVEL COMPLETED";
	    String s1 = "Press ENTER to continue...";
	    g2d.drawString(s, 180, 500);
	    g2d.drawString(s1, 130, 560);

		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(sfondo, 0, 0, sfondo.getWidth(), sfondo.getHeight(), null);
		
	}

}
