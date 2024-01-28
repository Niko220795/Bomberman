package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

import controller.listeners.LoadGameListener;
import model.User;

public class SaveSlotButton {

	User user;
	int skin_id;
	JButton button;
	
	public SaveSlotButton(User user, int skin_id) {
		this.user = user;
		this.skin_id = skin_id;
		this.button = new JButton(user.username);
		button.setForeground(Color.ORANGE);
		button.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 30));
		button.setFocusPainted(false);
//		user.setOpaque(false);
		button.setContentAreaFilled(false);
		button.addActionListener(new LoadGameListener(user));
		button.setBorderPainted(false);
	}
	
	public JButton getButton() {
		return this.button;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
