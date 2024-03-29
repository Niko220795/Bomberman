package model;
import java.util.Random;

import controller.GameSetup;

/**
 * Classe con getter e setter per gestire il model del tile.
 */
public class TileModel {
	

	private boolean is_exit = false;
	private boolean exploding = false;
	private Random r = new Random();
	private int matrix_pos_row;
	private int matrix_pos_col;
	private int model_num;
	private PowerUpModel power_up;
	private boolean collision = true;
	private boolean is_destructible = true;
	public int destruction_counter = 40;
	public BombModel placedBomb = null;
	private boolean disappearing = false;
	private boolean has_trap = false;
	private TrapModel placed_trap = null;
	private boolean border = false;
	public TileModel(int i) {
		this.model_num = i;
		this.power_up = null;
	}
	
	public void removePowerUp() {
		this.power_up = null;
	}
	
	public boolean hasTrap() {
		return this.placed_trap != null;
	}

	public void setTrap(TrapModel trap) {
		this.placed_trap = trap;
	}
	
	public void resetTrap() {
		this.placed_trap = null;
	}

	public TrapModel getPlaced_trap() {
		return placed_trap;
	}

	public BombModel getPlacedBomb() {
		return placedBomb;
	}
	
	public boolean isDisappearing() {
		return this.disappearing;
	}
	public void setDisappearing(boolean b) {
		this.disappearing = b;
	}
	

	public void setPlacedBomb(BombModel placedBomb) {
		this.placedBomb = placedBomb;
	}

	public boolean isBorder() {
		return this.border;
	}
	
	public void setBorder() {
		this.border = true;
	}
	public boolean getCollision() {
		return collision;
	}
	
	public boolean containsPowerUp() {
		int k = GameSetup.random_generator.nextInt(5);
		this.power_up = null;
		if (k == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isExploding() {
		return exploding;
	}
	
	
	public PowerUpModel getPowerUp() {
		return this.power_up;
	}
	
	public void setPower_up(PowerUpModel power_up) {
		this.power_up = power_up;
	}

	public boolean isExit() {
		return is_exit;
	}

	public void setExit(boolean is_exit) {
		this.is_exit = is_exit;
	}

	public void setExploding(boolean exploding) {
		this.exploding = exploding;
	}
	
	public void setCollision(boolean setCollision) {
		this.collision = setCollision;
	}
	
	public int getMatrix_pos_row() {
		return matrix_pos_row;
	}
	
	
	public int getMatrix_pos_col() {
		return matrix_pos_col;
	}
	
	public void setDestructible(boolean setDestructible) {
		this.is_destructible = setDestructible;
	}
	
	public boolean getDestructible() {
		return this.is_destructible;
	}
	
	
	public int getModel_num() {
		return model_num;
	}
	
	public void setModel_num(int i) {
		this.model_num = i;
	}


	public void setColCoord(int col) {
		this.matrix_pos_col = col;
	}


	public void setRowCoord(int row) {
		this.matrix_pos_row = row;
		
	}

	
	
}
