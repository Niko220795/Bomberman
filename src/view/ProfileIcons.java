package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ProfileIcons {

	JButton[] icons;
	public ProfileIcons() {
		icons = new JButton[5];
		this.setUpIcons();
	}
	
	public void setUpIcons() {
		for (int i = 1; i <= 5; i++) {
			String path = "src/resources/profile_icons/" + i + ".jpg";
			JButton icon = new JButton();
			icon.setIcon(new ImageIcon(path));
			this.icons[i-1] = icon;
		}
	}
	
	public JButton getIcon(int i) {
		return this.icons[i];
	}
}
