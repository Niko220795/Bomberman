package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class LevelSelection {

	JButton[] buttons = new JButton[5];
	
	public LevelSelection() {
		for (int i =0; i<5; i++) {
			JButton level = new JButton();
			level.setSize(80,80);
			level.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 40));
			level.setForeground(Color.ORANGE);
			level.setText(String.valueOf(i+1));
			buttons[i] = level;
		}
	}
	
	public JButton getButton(int i) {
		return buttons[i];
	}
}
