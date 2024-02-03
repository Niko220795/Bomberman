package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import controller.GameSetup;

public class SaveGameListener implements ActionListener{

	private GameSetup game_setup;
	
	public SaveGameListener(GameSetup game_setup) {
		this.game_setup = game_setup;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		int user_id = game_setup.getSelected_user().user_id;
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream("src/resources/menu/user"+user_id+".txt");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(game_setup.getSelected_user());
			objectOutputStream.flush();
			objectOutputStream.close();				
			System.out.println(user_id);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
	}

}
