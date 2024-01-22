package model;

public class PowerUpModel {
	private int id;
	private int row;
	private int col;
	private boolean picked_up = false;
	
	public boolean isPicked_up() {
		return picked_up;
	}
	public void setPicked_up(boolean picked_up) {
		this.picked_up = picked_up;
	}
	public int getId() {
		return id;
	}
	public PowerUpModel(int id, int row, int col) {
		this.id = id;
		this.row = row;
		this.col = col;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}

}
