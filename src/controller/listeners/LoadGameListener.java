package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.MenuSetup;
import model.User;
import view.GameWindow;

public class LoadGameListener implements ActionListener{

	private MenuSetup menu;
	private User current_user;
	
	public LoadGameListener(MenuSetup menu) {
		this.menu = menu;
		this.current_user = menu.current_user;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		GameWindow game_window = new GameWindow(menu);
		game_window.getFrame().setVisible(true);
	}
}
