package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import model.User;

public class Initializer {

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
				User default_user = new User("-- EMPTY SLOT --", 0, 2, label,i);
				System.out.println("initializer userid =" + default_user.user_id);
				FileOutputStream fileOutputStream = new FileOutputStream("src/resources/menu/user"+i+".txt");
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(default_user);
				objectOutputStream.flush();
				objectOutputStream.close();				
				System.out.println("test");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
