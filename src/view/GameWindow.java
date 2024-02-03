package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import controller.GameSetup;
import controller.MenuSetup;
import model.User;

public class GameWindow{

	JFrame frame;
	GamePanel game_panel;
	ScoreBoardView scoreboard;
	User current_user;
	GameSetup game_setup;
	public GameWindow(MenuSetup menu) {
		/*
		 * need to initialize user
		 */
		this.current_user = menu.current_user;
		this.game_setup = new GameSetup(current_user);
		this.frame = new JFrame();
		this.game_panel = new GamePanel(game_setup);
		this.scoreboard = new ScoreBoardView(menu);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("test");
		frame.add(game_panel, BorderLayout.SOUTH);
		frame.add(scoreboard.getScoreboard(), BorderLayout.NORTH);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
}
