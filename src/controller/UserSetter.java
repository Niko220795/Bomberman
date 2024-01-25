package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.User;
//import view.FinestraDiGioco;
import view.MainMenu;
import view.UserButton;

public class UserSetter implements ActionListener {

	MainMenu main;
	User user;
	UserButton[] user_buttons;
	public UserSetter(MainMenu main, User user, UserButton[] user_buttons) {
		this.main = main;
		this.user = user;
		this.user_buttons = user_buttons;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		this.main.playing_user = this.user;
//		System.out.println(this.user.current_level);
//		this.main.new_game.setVisible(true);
//		this.main.play.setVisible(true);
//		for (int i = 0; i < user_buttons.length; i++) {
//			this.user_buttons[i].setVisible(false);
//		}
//		new FinestraDiGioco(this.user);
		
	}

}
