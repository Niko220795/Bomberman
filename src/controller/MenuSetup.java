package controller;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import controller.listeners.IconSetterListener;
import controller.listeners.UsernameListener;
import model.User;
import view.Menu;
import view.ProfileIcons;
import view.SaveSlotButton;
import view.NewGameButton;
import view.PlayButton;
public class MenuSetup {

	
	public User[] users;
	public SaveSlotButton[] save_slots;
	public ProfileIcons profile_icons;
	public JButton new_game;
	public JTextField username_field;
	public JButton play_button;
	public JButton load_button;
	public int selected_icon = 0;
	public String username = "";
	
	public MenuSetup() {
		this.play_button = (new PlayButton()).getPlayButton();
		this.load_button = (new PlayButton()).getPlayButton();

		this.username_field = new JTextField("Insert username");
		this.setUpUsernameField();
		this.profile_icons = new ProfileIcons();
		this.users = new User[3];
		this.save_slots = new SaveSlotButton[3];
		this.new_game = (new NewGameButton(this)).getNewGameButton();
		
		Menu background = new Menu();
		background.setLayout(null);
		initializeSaveSlots();
		displaySaveSlots(background);
		displayIcons(background);
		displayNewLoadGame(background);		
		displayPlayButton(background);
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(background);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	
	/*
	 * 
	  	JTextField username_field = new JTextField("Insert username");
		username_field.requestFocus();
		username_field.setBackground(Color.ORANGE);
		username_field.setBounds(100, 100, 100,30);
	 */
	
	public void setUpUsernameField() {
		username_field.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                if(username_field.getText().isEmpty()) {
                	username_field.setText("Insert username");
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if(username_field.getText().equals("Insert username")) {
                	username_field.setText("");
                }
            }
        });
		username_field.requestFocus();
		username_field.setBackground(Color.BLACK);
		username_field.setForeground(Color.ORANGE);
		username_field.setBorder(new LineBorder(Color.orange,3));
		username_field.setBounds(280, 400, 200,30);
		username_field.setHorizontalAlignment(JTextField.CENTER);
		username_field.addActionListener(new UsernameListener(this, username_field.getText()));
		username_field.setVisible(false);
	}
	
	public static void main(String[] args) {
		MenuSetup menu = new MenuSetup();
		System.out.println(menu.username_field.getText());
	}
	
	public void profileIconMenu() {
		int i = 0;
		for (SaveSlotButton save_slot : this.save_slots) {
			save_slot.getButton().setVisible(false);
			i+=1;
		}
		this.new_game.setVisible(false);
		for(int j = 0; j<3; j++) {
			this.profile_icons.getIcon(j).setVisible(true);
		}
		this.username_field.setVisible(true);
		this.play_button.setVisible(true);
	}
	
	public void displaySaveSlots(Menu background) {
		int i = 0;
		for (SaveSlotButton save_slot : this.save_slots) {
			save_slot.getButton().setBounds(20, 300+80*i, 300, 50);
			background.add(save_slot.getButton());
			save_slot.getButton().setVisible(true);
			i+=1;
		}
	}
	
	
	public void displayIcons(Menu background) {
		for (int i = 0; i< 3; i++) {
			JButton icon = this.profile_icons.getIcon(i);
			icon.addActionListener(new IconSetterListener(this, i));
			icon.setBounds(150+200*i, 500, 50, 50);
			background.add(icon);
			icon.setContentAreaFilled(false);
			icon.setVisible(false);
			icon.setBorderPainted(false);
		}
		background.add(this.username_field);
//		this.username_field.setVisible(false);
		
	}
	
	public void displayNewLoadGame(Menu background) {
		this.new_game.setBounds(400, 300, 300, 80);
		background.add(this.new_game);
		this.new_game.setVisible(true);
	}
	
	public void displayPlayButton(Menu background) {
		this.play_button.setBounds(230, 300, 300, 80);
		background.add(this.play_button);
		this.play_button.setVisible(false);
	}
	
	public void initializeSaveSlots() {
		loadUsers();
		for (int i = 0; i<3; i++) {
//			this.save_slots[i].setUser(this.users[i]);
			this.save_slots[i] = new SaveSlotButton(this.users[i],1);
			System.out.println("initialized user "+i);

		}
	}
	
	public void loadUsers(){
		
//    	FileOutputStream fileOutputStream = new FileOutputStream("src/resources/menu/yourfile.txt");
//    	ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//		objectOutputStream.writeObject(person);
//		objectOutputStream.flush();
//		objectOutputStream.close();
    try {
    	for (int i = 0; i <3; i++) {
    		try {	    			
    			FileInputStream fileInputStream = new FileInputStream("src/resources/menu/user" + i + ".txt");
    			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
    			this.users[i] = (User) objectInputStream.readObject();
    			System.out.println("load user"+i);
    			objectInputStream.close(); 
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    

}
}
