package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.MenuSetup;

public class ScoreBoardView {
	JPanel scoreboard;
	MenuSetup menu;
	JLabel score_label;
	public ScoreBoardView(MenuSetup menu) {
		this.menu = menu;
		scoreboard = new JPanel();
		scoreboard.setLayout(new BorderLayout());
		scoreboard.setPreferredSize(new Dimension(GamePanel.X_TILES*GamePanel.FINAL_TILE_SIZE, 70));
		scoreboard.setDoubleBuffered(true);
		scoreboard.add(menu.current_user.propic, BorderLayout.WEST);
		JLabel username = new JLabel(menu.current_user.username);
		username.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 30));
		score_label = scoreLabel();
		scoreboard.add(score_label, BorderLayout.EAST);
		scoreboard.add(username,BorderLayout.CENTER);
	}
	public JPanel getScoreboard() {
		return this.scoreboard;
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
}
