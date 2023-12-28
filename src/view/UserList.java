package view;

import javax.swing.JComboBox;

public class UserList{
	
	String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };
	public JComboBox list;
	public UserList() {
		this.list = new JComboBox(petStrings);
	}
	
}