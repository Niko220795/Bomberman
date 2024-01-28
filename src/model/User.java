package model;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.JButton;

public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public String username;
	public JButton propic;
	public int score;
	public int level;
	public int user_number;
	
	public User(String s, int score, int level, JButton propic) {
		this.username = s;
		this.score = score;
		this.level = level;
		this.propic = propic;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	

}
