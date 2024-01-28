package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.MenuSetup;
import controller.listeners.ShowProfileIconListener;
import model.User;

public class NewGameButton {
	
	JButton new_game;
	MenuSetup menu;
	public NewGameButton(MenuSetup menu) {
		this.menu = menu;
		new_game = new JButton();
		new_game.setIcon((new ImageIcon("src/resources/menu/btn-newgame.png")));
		new_game.setContentAreaFilled(false);
		new_game.setBorderPainted(false);
	}
	
	public JButton getNewGameButton() {
		return this.new_game;
	}
}
