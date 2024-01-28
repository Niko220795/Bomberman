package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import controller.MenuSetup;
import view.GamePanel;
import view.GameWindow;
import model.User;

public class NewGameListener implements ActionListener{

	
	JButton propic;
	String username;
	MenuSetup menu;
	public NewGameListener(MenuSetup menu) {
		this.menu = menu;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		GameWindow game_window = new GameWindow(new User(username,0,1, propic));
		game_window.getFrame().setVisible(true);
		
	}

}
