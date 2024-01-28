package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import model.User;

public class Initializer {

	public static void main(String[] args) {
		
		for (int i = 0; i<3; i++) {
			try {
				JButton button = new JButton();
				button.setIcon(new ImageIcon("src/resources/bomberman-front.png"));

				User default_user = new User("-- EMPTY SLOT --", 0, 1, button);
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
