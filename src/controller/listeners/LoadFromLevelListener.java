package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.MenuSetup;
import view.GameWindow;

public class LoadFromLevelListener implements ActionListener{

	int selected_level = 1;
	MenuSetup menu;
	public LoadFromLevelListener(int i, MenuSetup menu) {
		this.selected_level = i+1;
		this.menu = menu;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (menu.current_user != null) {
			menu.current_user.level = this.selected_level;
			GameWindow game_window = new GameWindow(menu);
			game_window.getFrame().setVisible(true);
		}
		
	}

}
