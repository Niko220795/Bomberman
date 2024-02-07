package model;

import java.io.Serializable;
import javax.swing.JLabel;

/**
 * Classe che raccoglie e memorizza tutti i dati dell'utente relativi all'username, al livello giocato, ai punti conquistati etc.
 */
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public String username;
	public JLabel propic;
	public int score;
	public int level;
	public int user_number;
	public int user_id;
	
	public User(String s, int score, int level, JLabel propic, int user_id) {
		this.username = s;
		this.score = score;
		this.level = level;
		this.propic = propic;
		this.user_id = user_id;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public void resetUser() {
		this.score = 0;
		this.level = 1;
	}
	

}
