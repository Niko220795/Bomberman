package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.NewGameButtonListener;
import controller.UserSetter;
import model.User;

public class MainMenu extends JFrame {

	public User[] users = new User[3];
	public User playing_user = new User("default", 0);
	public JButton play;
	public JButton new_game;
	public UserButton[] user_buttons = new UserButton[3];
	public JButton[] profiles_icon = new JButton[1];

	public MainMenu() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 768, 576);
		this.getContentPane().setLayout(null);
		
		JPanel panel = new Menu();
		panel.setLayout(null);
		panel.setForeground(new Color(0, 0, 255));
		panel.setBounds(0, 0, 768, 576);
		this.getContentPane().add(panel);
		this.loadUsers();
		this.instantiate_buttons(panel);
//		
//		menu.setVisible(true);
//		menu.repaint();

//
//	        //transparent JPanel creation
//	        JPanel mainPanel = new JPanel(new BorderLayout()); // transparent frame to add comboBox
//	        mainPanel.add(comboBox, BorderLayout.NORTH); // comboBox added to transparent frame
//
//	        //now dealing with the creation of the JFrame to display it all
//	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	        this.setLocationRelativeTo(null);
//	        this.setLayout(new BorderLayout());
//
//	        //adding everything to the frame
//
//	        this.add(mainPanel, BorderLayout.CENTER);
//	        this.pack();
//	        this.setVisible(true);
	}

	public void instantiate_buttons(JPanel panel) {
		CharacterSelectionButton profiles = new CharacterSelectionButton();
		for (int i = 0; i < 1; i++) {
			JButton icon = profiles.get_button(i);
			panel.add(icon);
			this.profiles_icon[i] = icon; 
		}
		JButton play = new JButton("New button");
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		play.setIcon(new ImageIcon("src/resources/menu/btn-play.png"));
		play.setVisible(false);
		panel.add(play);
		this.play = play;
		JButton new_game = new JButton("New button");
		new_game.setOpaque(false);
		new_game.setContentAreaFilled(false);
		new_game.setBorderPainted(false);
		new_game.setIcon(new ImageIcon("src/resources/menu/btn-newgame.png"));
		new_game.setVisible(false);
		panel.add(new_game);
		this.new_game = new_game;
		new_game.addActionListener(new NewGameButtonListener(this));
		new_game.setBounds(450, 350, 250, 50);
		panel.add(play);
		play.setBounds(500, 450, 150, 50);
		for (int i = 0; i<3; i++) {
			JButton user = new UserButton(this, this.users[i]);
//			JButton user = new JButton("new button");
//			JButton user = new JButton("SAVE SLOT " + i);
			user.setForeground(Color.ORANGE);
			user.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 40));
			user.setOpaque(false);
			user.setContentAreaFilled(false);
			user.setBorderPainted(false);
			user.setBounds(50, 350+i*50, 350, 50);
			user.addActionListener(new UserSetter(this, this.users[i], this.user_buttons));
			this.user_buttons[i] = (UserButton)user;
			user.setVisible(true);
			panel.add(user);
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
	
	public void loadUsers() {
	users[0] = new User("nl", 0);
	users[1] = new User("nl", 1);
	users[2] = new User("nl", 1);
}

}