package controller;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import model.User;

public class Initializer {

	/**
	 * Questa classe serve ad inizializzare tutti i files di configurazioni contenenti gli users la prima volta.
	 */
	public static void main(String[] args) {
		
		for (int i = 0; i<3; i++) {
			try {
				JButton button = new JButton();
				button.setIcon(new ImageIcon("src/resources/bomberman-front.png"));
				button.setContentAreaFilled(false);
				button.setBorderPainted(false);
				button.setFocusPainted(false);
				JLabel label = new JLabel();
				label.setIcon(button.getIcon());
				User default_user = new User("-- EMPTY SLOT --", 0, 1, label,i);
				FileOutputStream fileOutputStream = new FileOutputStream("src/resources/menu/user"+i+".txt");
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(default_user);
				objectOutputStream.flush();
				objectOutputStream.close();				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
