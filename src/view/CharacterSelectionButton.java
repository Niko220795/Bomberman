package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CharacterSelectionButton{
	public ImageIcon[] icons = new ImageIcon[1];
	public CharacterSelectionButton() {
		icons[0] = new ImageIcon("src/resources/profiles/bomberman-front.png");
	}
	public JButton get_button(int i) {
		JButton icon = new JButton();
		icon.setOpaque(false);
		icon.setContentAreaFilled(false);
		icon.setBorderPainted(false);
		icon.setBounds(50, 350+i*50, 350, 50);
		icon.setVisible(false);
		icon.setIcon(icons[i]);
		return icon;
	}
}
