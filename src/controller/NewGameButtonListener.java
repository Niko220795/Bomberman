package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import view.MainMenu;

public class NewGameButtonListener implements ActionListener {
	
	public JButton[] profiles_icon;
	public JButton new_game_button;
	public JButton load_game_button;
	
		
	public NewGameButtonListener(MainMenu m) {
		this.profiles_icon = m.profiles_icon;
		this.new_game_button = m.new_game;
		this.load_game_button = m.play;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
//		for (int i = 0; i < profiles_icon.length; i++) {
//			profiles_icon[i].setVisible(false);
//		}
//		System.out.println("selected profile icon");
		for (int i = 0; i < profiles_icon.length; i++) {
			profiles_icon[i].setVisible(true);
			this.new_game_button.setVisible(false);
			this.load_game_button.setVisible(false);
			
		}
	System.out.println("new game");
		
	}

}
