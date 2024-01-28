package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.MenuSetup;

public class UsernameListener implements ActionListener {
	
	MenuSetup menu;
	String username;
	public UsernameListener(MenuSetup menu) {
		this.menu = menu;
		this.username = menu.username_field.getText();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		menu.username = this.username;
		
	}

}
