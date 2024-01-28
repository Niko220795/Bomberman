package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ProfileIcons {

	JButton[] icons;
	public ProfileIcons() {
		icons = new JButton[3];
		this.setUpIcons();
	}
	
	public void setUpIcons() {
		for (int i = 1; i <= 3; i++) {
			String path = "src/resources/green_village/" + i + ".png";
			JButton icon = new JButton();
			icon.setIcon(new ImageIcon(path));
			this.icons[i-1] = icon;
		}
	}
	
	public JButton getIcon(int i) {
		return this.icons[i];
	}
}
