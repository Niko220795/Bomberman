package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import controller.MenuSetup;

public class ShowProfileIconListener implements ActionListener {

	private MenuSetup menu;
	public ShowProfileIconListener(MenuSetup menu) {
		this.menu = menu;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	if (menu.current_user != null) {
		    		menu.profileIconMenu();		    		
		    	}
		    }
		  });
		
		
	}

}
