package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.MenuSetup;

public class ScoreBoardView extends JPanel {
//	JPanel scoreboard;
	MenuSetup menu;
	JLabel score_label;
	BufferedImage background;
	
	public ScoreBoardView(MenuSetup menu) {
		try {
			background = ImageIO.read(new File("src/resources/menu/topBar.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.menu = menu;
//		scoreboard = new JPanel();
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(GamePanel.X_TILES*GamePanel.FINAL_TILE_SIZE, 70));
		this.setDoubleBuffered(true);
		this.add(menu.current_user.propic, BorderLayout.WEST);
		JLabel username = new JLabel(menu.current_user.username);
		username.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 30));
		score_label = scoreLabel();
		this.add(score_label, BorderLayout.EAST);
		this.add(username,BorderLayout.CENTER);
	}
	public JPanel getScoreboard() {
		return this;
	}
	
	public JLabel scoreLabel() {
		JLabel score_label = new JLabel();
		score_label.setText("Score: " + Integer.toString(menu.current_user.score));
		score_label.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 30));
		return score_label;
	}
	public JLabel getScore_label() {
		return score_label;
	}
	
	public void update() {
		score_label.setText("Score: " +Integer.toString(menu.current_user.score));
	}
	@Override 
	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);
	}
	
	
}
