package view;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Main;
import controller.UserSetter;
import model.User;

public class UserButton extends JButton{
	User user;
	public UserButton(MainMenu main, User user) {
		super("test");
		this.user = user;
//		this.addActionListener(new UserSetter(main, user));
		// TODO Auto-generated constructor stub
	}

}
