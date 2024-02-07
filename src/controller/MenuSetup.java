package controller;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import controller.listeners.IconSetterListener;
import controller.listeners.LoadFromLevelListener;
import controller.listeners.LoadGameListener;
import controller.listeners.SaveSlotGameListener;
import controller.listeners.NewGameListener;
import controller.listeners.ShowProfileIconListener;
import model.User;
import view.LevelSelection;
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
	public User current_user;
	public LevelSelection level_selection = new LevelSelection();
	public JLabel level_text;
	
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
		displayLevels(background);
		displayNewLoadGame(background);		
		displayPlayLoadButton(background);
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(background);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	

	
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
		username_field.setVisible(false);
	}
	
	public static void main(String[] args) {
		MenuSetup menu = new MenuSetup();
	}
	
	public void profileIconMenu() {
		int i = 0;
		for (SaveSlotButton save_slot : this.save_slots) {
			save_slot.getButton().setVisible(false);
			i+=1;
		}
		this.new_game.setVisible(false);
		this.load_button.setVisible(false);
		for(int j = 0; j<5; j++) {
			this.profile_icons.getIcon(j).setVisible(true);
		}
		this.username_field.setVisible(true);
		this.play_button.setVisible(true);
	}
	
	public void levelSelectionMenu() {
		int i = 0;
		for (SaveSlotButton save_slot : this.save_slots) {
			save_slot.getButton().setVisible(false);
			i+=1;
		}
		this.new_game.setVisible(false);
		this.load_button.setVisible(false);
		for (int j = 0; j<5; j++) {
			this.level_selection.getButton(j).setVisible(true);
		}
		this.level_text.setVisible(true);
	}
	
	public void displaySaveSlots(Menu background) {
		int i = 0;
		for (SaveSlotButton save_slot : this.save_slots) {
			save_slot.getButton().setBounds(20, 300+80*i, 300, 50);
			background.add(save_slot.getButton());
			save_slot.getButton().addActionListener(new SaveSlotGameListener(this, save_slot));
			save_slot.getButton().setVisible(true);
			i+=1;
		}
	}
	
	
	public void displayIcons(Menu background) {
		for (int i = 0; i< 5; i++) {
			JButton icon = this.profile_icons.getIcon(i);
			icon.addActionListener(new IconSetterListener(this, i));
			icon.setBounds(150+100*i, 480, 65, 65);
			background.add(icon);
			icon.setContentAreaFilled(false);
			icon.setVisible(false);
			icon.setBorderPainted(false);
		}
		background.add(this.username_field);

	}
	
	public void displayLevels(Menu background) {
		level_text = new JLabel();
		level_text.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 40));
		level_text.setForeground(Color.ORANGE);
		level_text.setText("Select a level:");
		background.add(level_text);
		level_text.setVisible(false);
		level_text.setBounds(250, 280, 400, 80);
		for (int i = 0; i<5; i++) {
			JButton level = this.level_selection.getButton(i);
			level.addActionListener(new LoadFromLevelListener(i,this));
			level.setBounds(150+100*i, 380, 80,80);
			background.add(level);
			level.setContentAreaFilled(false);
			level.setVisible(false);
			level.setBorderPainted(false);
		}
	}
	
	public void displayNewLoadGame(Menu background) {
		this.new_game.setBounds(400, 300, 300, 80);
		background.add(this.new_game);
		this.new_game.addActionListener(new ShowProfileIconListener(this));
		this.new_game.setVisible(true);
	}
	
	public void displayPlayLoadButton(Menu background) {
		this.play_button.setBounds(230, 300, 300, 80);
		background.add(this.play_button);
		this.play_button.addActionListener(new NewGameListener(this));
		this.play_button.setVisible(false);
		this.load_button.setBounds(400, 400, 300, 80);
		background.add(this.load_button);
		this.load_button.addActionListener(new LoadGameListener(this));
		this.load_button.setVisible(true);
	}
	
	public void initializeSaveSlots() {
		loadUsers();
		for (int i = 0; i<3; i++) {
			this.save_slots[i] = new SaveSlotButton(this.users[i],1);
		}
	}
	
	public void loadUsers(){

    try {
    	for (int i = 0; i <3; i++) {
    		try {	    			
    			FileInputStream fileInputStream = new FileInputStream("src/resources/menu/user" + i + ".txt");
    			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
    			User user = (User) objectInputStream.readObject();
    			this.users[i] = user;
    			objectInputStream.close();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
	} catch (Exception e) {
		e.printStackTrace();
	}
    

}
}
