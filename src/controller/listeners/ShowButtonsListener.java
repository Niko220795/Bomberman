package controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class ShowButtonsListener implements ActionListener{

	
	ArrayList<JButton> buttons;
	
	public ShowButtonsListener(	ArrayList<JButton> buttons) {
		this.buttons = buttons;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		for (JButton button : buttons) {
			 SwingUtilities.invokeLater(new Runnable() {
				    public void run() {
				      button.setVisible(true);
				    }
				  });

		}
		
	}

}
