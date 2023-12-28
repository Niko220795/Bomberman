package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainMenu extends JFrame {

	final String[] name = {"Aziz", "masum", "sakib", "shaon"};
	private JPanel contentPane;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public MainMenu() {
		JComboBox comboBox=new JComboBox(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 768, 576);
//		getContentPane().setLayout(null);
		JPanel pane = new JPanel();
		pane.setLayout(null);
		UserList users = new UserList();
		JComboBox box = users.list;
		JButton but = new JButton();
		but.setBounds(0,0,50,50);
		box.setBounds(0, 0, 50, 50);
		pane.add(box);
		pane.setVisible(true);
		getContentPane().add(pane);
		setVisible(true);

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

	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}

}