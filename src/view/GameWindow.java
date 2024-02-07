package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import controller.GameSetup;
import controller.MenuSetup;
import controller.listeners.SaveGameListener;
import model.User;

/**
 * JPanel che contiene il JPanel con il gioco in corso e un JLabel che mostra username, avatar e punteggio.
 */
public class GameWindow{

	JFrame frame;
	GamePanel game_panel;
	ScoreBoardView scoreboard;
	User current_user;
	GameSetup game_setup;
	SaveButton save;
	public GameWindow(MenuSetup menu) {

		this.current_user = menu.current_user;
		this.game_setup = new GameSetup(current_user);
		this.frame = new JFrame();
		this.game_panel = new GamePanel(game_setup);
		this.scoreboard = new ScoreBoardView(menu);
		this.game_setup.setScoreboard(scoreboard);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("test");
		frame.add(game_panel, BorderLayout.SOUTH);
		frame.add(scoreboard.getScoreboard(), BorderLayout.NORTH);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	public void initializeSaveButton() {
		this.save = new SaveButton(this.game_setup.getSelected_user());
		this.save.getButton().setFocusable(false);
		this.getFrame().add(save.getButton(), BorderLayout.EAST);
		save.getButton().addActionListener(new SaveGameListener(this.game_setup));
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
}
