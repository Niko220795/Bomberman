package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import controller.listeners.SaveSlotGameListener;
import model.User;

public class PlayButton {

	User user;
	JButton button;
	JButton profile_pic;
	
	public PlayButton() {
		button = new JButton();
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setIcon(new ImageIcon("src/resources/menu/btn-play.png"));
	}
	
	public JButton getPlayButton() {
		return this.button;
	}
}
