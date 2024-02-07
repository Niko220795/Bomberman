package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import controller.MenuSetup;
import model.User;
import view.GameWindow;
import view.SaveSlotButton;

public class SaveSlotGameListener implements ActionListener{

	private MenuSetup menu;
	private SaveSlotButton save_slot;
	
	public SaveSlotGameListener(MenuSetup menu, SaveSlotButton save_slot) {
		this.save_slot = save_slot;
		this.menu = menu;
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		menu.current_user = save_slot.user;
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	save_slot.getButton().setBorderPainted(true);
				for (SaveSlotButton slot : menu.save_slots) {
					if (slot.getButton() != save_slot.getButton()) {
						slot.getButton().setBorderPainted(false);
					}
				}
		    }
		  });
		
	}
}
