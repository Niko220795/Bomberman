package view;

import javax.swing.JFrame;

import controller.GameSetup;
import controller.MenuSetup;
import model.User;

public class GameWindow{

	JFrame frame;
	GamePanel game_panel;
	User current_user;
	public GameWindow(MenuSetup menu) {
		/*
		 * need to initialize user
		 */
		this.current_user = menu.current_user;
		this.frame = new JFrame();
		this.game_panel = new GamePanel(new GameSetup(current_user));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("test");
		frame.getContentPane().add(game_panel);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
}
