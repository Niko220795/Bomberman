package model;
import java.util.Observable;

@SuppressWarnings("deprecation")
public abstract class Entity extends Observable {
	
	private int pos_x;
	private int pos_y;
	protected boolean dead;
	protected int health;
	protected int death_animation_counter = 60;
	protected int move_speed = 4;
	protected Direction dir = Direction.UP;
	
	public int getPos_x() {
		return pos_x;
	}
	
	public void setPos_x(int pos_x) {
		this.pos_x = pos_x;
	}
	
	public int getPos_y() {
		return pos_y;
	}
	
	public void setPos_y(int pos_y) {
		this.pos_y = pos_y;
	}
	
	public int getMoveSpeed() {
		return this.move_speed;
	}
	
	public void up() {
		setPos_y(getPos_y() - move_speed);
		this.dir = Direction.UP;
	}

	public void down() {
		setPos_y(getPos_y() + move_speed);
		this.dir = Direction.DOWN;
	}

	public void left() {
	    setPos_x(getPos_x() - move_speed);
	    this.dir = Direction.LEFT;

	}
	
	public void right() {
		setPos_x(getPos_x() + move_speed);
		this.dir = Direction.RIGHT;
		
	}
	
	public boolean isDead() {
		return dead;
	}

	public boolean decreaseDeathAnimationCounter() {
		if (death_animation_counter > 0) {
			this.death_animation_counter -= 1;			
		}
		
		if (death_animation_counter <= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public Direction getDir() {
		return dir;
	}
	
	
}
