package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import controller.listeners.SaveSlotGameListener;
import model.User;

/**
 * Buttons che gestiscono i save slot del menu.
 */
public class SaveSlotButton {

	public User user;
	public int skin_id;
	JButton button;
	
	public SaveSlotButton(User user, int skin_id) {
		this.user = user;
		this.skin_id = skin_id;
		this.button = new JButton(user.username);
		button.setForeground(Color.ORANGE);
		button.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 30));
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
		button.setBorderPainted(false);
	}
	
	public JButton getButton() {
		return this.button;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
