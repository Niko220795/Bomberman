package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.User;
import view.GameWindow;

public class LoadGameListener implements ActionListener{

	private User current_user;
	
	public LoadGameListener(User current_user) {
		this.current_user = current_user;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		GameWindow game_window = new GameWindow(this.current_user);
		game_window.getFrame().setVisible(true);
	}

}
