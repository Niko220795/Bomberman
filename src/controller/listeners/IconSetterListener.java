package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.MenuSetup;

public class IconSetterListener implements ActionListener {

	MenuSetup menu;
	int selected_icon;
	
	public IconSetterListener(MenuSetup menu, int i) {
		this.menu = menu;
		this.selected_icon = i;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.menu.selected_icon = this.selected_icon;	
	}

}
