package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import model.User;

public class SaveButton {

	public User user;
	JButton button;
	
	public SaveButton(User user) {
		this.user = user;
		this.button = new JButton("SAVE GAME");
		button.setForeground(Color.ORANGE);
		button.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 30));
		button.setFocusPainted(false);
//		user.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorder(BorderFactory.createLineBorder(Color.red, 5));
		button.setBorderPainted(false);
	}
	
	public JButton getButton() {
		return this.button;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
