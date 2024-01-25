package model;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public String username;
	public BufferedImage propic;
	public int score;
	public int level;
	public int user_number;
	
	public User(String s, int i, int level, int number) {
		this.username = s;
		this.score = i;
		this.level = level;
		this.user_number = number;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	

}
