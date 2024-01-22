package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.User;
//import Model.ButtonAdder;
//import Model.User;
//import Model.UserButton;
//import Model.UserSetter;
import view.*;
import java.awt.Color;
import java.awt.Font;

public class Main{
	
	boolean game_started = false;
	JButton play;
//	User[] users = new User[3];
	JButton[] user_buttons = new JButton[3];
	JButton new_game;
	JButton load;
	public User playing_user;
	public JFrame menu;
	
	public Main() {
//		users[0] = new User("nl", 1000, 1);
//		users[1] = new User("nl", 1000, 1);
//		users[2] = new User("nl", 1000, 1);
//		this.instantiate_buttons(panel);
//		this.loadUser();
//		this.showMenu();
//		FinestraDiGioco finestra_principale = new FinestraDiGioco();
//		JButton btnNewButton_1 = new JButton("New button");
//		btnNewButton_1.setBounds(272, 213, 150, 50);
//		btnNewButton_1.setIcon(new ImageIcon(menu.class.getResource("/resources/menu/btn-play.png")));
//		JPanel menu = new menu();
//		menu.add(btnNewButton_1);
//		btnNewButton_1.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				finestra_principale.add(new GamePanel());
//				finestra_principale.remove(menu);
//			}
//		});
//		finestra_principale.add(menu);
//		finestra_principale.pack();
//		finestra_principale.setVisible(true);
		
	}
	
//	public void loadUsers() {
//		users[0] = new User("nl", 1000, 0);
//		users[1] = new User("nl", 1000, 1);
//		users[2] = new User("nl", 1000, 1);
//	}

	public static void main(String[] args) {
//		MainMenu m = new MainMenu();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setResizable(false);
		frame.setTitle("test");
		GamePanel gp = new GamePanel();
		frame.getContentPane().add(gp);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
//		m.loadUser();
//		m.setVisible(true);;
//		Main main_controller = new Main();
	}
	
	public void showMenu() {
		menu = new JFrame("Menu");
		
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setBounds(100, 100, 768, 576);
		menu.getContentPane().setLayout(null);
		
		JPanel panel = new Menu();
		panel.setLayout(null);
		panel.setForeground(new Color(0, 0, 255));
		panel.setBounds(0, 0, 768, 576);
		menu.getContentPane().add(panel);
		this.instantiate_buttons(panel);
//		JButton play = new JButton("New button");
//		play.setOpaque(false);
//		play.setContentAreaFilled(false);
//		play.setBorderPainted(false);
//		play.setIcon(new ImageIcon(Test.class.getResource("/resources/menu/btn-play.png")));
//		play.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				for (int i = 0; i < 3; i++) {
//					panel.
//				}
//			}
//		});
//		play.addActionListener(new ActionListener() { 
//  public void actionPerformed(ActionEvent e) { 
//   System.out.println("lol");
//  } 
//} );
//		panel.add(play);
//		play.setBounds(100, 400, 150, 50);
		menu.setVisible(true);
		menu.repaint();
	}
	
//	public void changeUser(User u) {
//		this.active_user = u;
//	}
	
	public void instantiate_buttons(JPanel panel) {
		this.play = new JButton("New button");
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		play.setIcon(new ImageIcon("src/resources/menu/btn-play.png"));
		panel.add(play);
		this.new_game = new JButton("New button");
		new_game.setOpaque(false);
		new_game.setContentAreaFilled(false);
		new_game.setBorderPainted(false);
		new_game.setIcon(new ImageIcon("src/resources/menu/btn-newgame.png"));
		panel.add(new_game);
		new_game.setBounds(450, 350, 250, 50);
		panel.add(play);
		play.setBounds(500, 450, 150, 50);
		for (int i = 0; i<3; i++) {
//			JButton user = new UserButton(this, this.users[i]);
//			JButton user = new JButton("new button");
			JButton user = new JButton("SAVE SLOT " + i);
			user.setForeground(Color.ORANGE);
			user.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 40));
			user.setOpaque(false);
			user.setContentAreaFilled(false);
			user.setBorderPainted(false);
			user.setBounds(50, 350+i*50, 350, 50);
//			user.addActionListener(new UserSetter(this, this.users[i]));
//			this.user_buttons[i] = new UserButton(this, this.users[i]);
			panel.add(user);
			user.setVisible(true);
//			play.add(user);
		}
//		play.addActionListener( new GameLauncher(this, 0));
//		new_game.addActionListener( new GameLauncher(this, 1));
//		for (int i = 0; i<3; i++) {
//			JButton userx = new JButton("User " + i+1);
//			userx.setOpaque(false);
//			userx.setContentAreaFilled(false);
//			userx.setBorderPainted(true);
//			play.setIcon(new ImageIcon(Test.class.getResource("/resources/menu/btn-play.png")));
//			this.users[i] = userx;
//		}
	}
	
//	public void loadUser(){
//		
////	    	FileOutputStream fileOutputStream = new FileOutputStream("src/resources/menu/yourfile.txt");
////	    	ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
////			objectOutputStream.writeObject(person);
////			objectOutputStream.flush();
////			objectOutputStream.close();
//	    try {
//	    	for (int i = 0; i <3; i++) {
//	    		try {	    			
//	    			FileInputStream fileInputStream = new FileInputStream("src/resources/menu/user" + i + ".txt");
//	    			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//	    			this.users[i] = (User) objectInputStream.readObject();
//	    			System.out.println("load user"+this.users[i].level);
//	    			objectInputStream.close(); 
//	    		} catch (Exception e) {
//	    			// TODO Auto-generated catch block
//	    			e.printStackTrace();
//	    		}
//	    	}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    
//
//	}
}