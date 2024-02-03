package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
		System.out.println(this.menu.username_field.getText());
		if (this.menu.username_field.getText() != "" && !this.menu.username_field.getText().equals("Insert username")) {
			JLabel label = new JLabel();
			label.setIcon(menu.profile_icons.getIcon(this.menu.selected_icon).getIcon());
			this.menu.current_user.resetUser();
			this.menu.current_user.propic = label;
			this.menu.current_user.username = this.menu.username_field.getText();
//			this.menu.current_user = new User(this.menu.username_field.getText(), 0, 1, label);
			System.out.println("user id = " + menu.current_user.user_id);
			GameWindow game_window = new GameWindow(menu);
			game_window.getFrame().setVisible(true);			
		}
		
	}

}
