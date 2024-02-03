package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.MenuSetup;
import view.GameWindow;

public class LoadGameListener implements ActionListener {

	MenuSetup menu;
	public LoadGameListener(MenuSetup menu) {
		this.menu = menu;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (menu.current_user != null) {
			GameWindow game_window = new GameWindow(menu);
			game_window.getFrame().setVisible(true);
		}
		
	}

}
