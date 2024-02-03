package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import controller.MenuSetup;

public class ScoreBoardView {
	JPanel scoreboard;
	MenuSetup menu;
	public ScoreBoardView(MenuSetup menu) {
		scoreboard = new JPanel();
		scoreboard.setLayout(new BorderLayout());
		scoreboard.setPreferredSize(new Dimension(GamePanel.X_TILES*GamePanel.FINAL_TILE_SIZE, 70));
		scoreboard.setDoubleBuffered(true);
		scoreboard.add(menu.current_user.propic, BorderLayout.WEST);
		this.menu = menu;
	}
	public JPanel getScoreboard() {
		return this.scoreboard;
	}
}
