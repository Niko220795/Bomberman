package model;

import controller.ControlsHandler;
import controller.Coordinates;
import view.GamePanel;

public abstract class Character extends Entity{
	

	private boolean is_actually_dead = false;
	public int invulnerability = 0;
	
	
	public void setReallyDead() {
		this.is_actually_dead = true;
	}
	
	public boolean isReallyDead() {
		return this.is_actually_dead;
	}
	
	public void revive() {
		this.death_animation_counter = 60;
		this.dead = false;
		this.is_actually_dead = false;
		
	}
	

	
	public Character() {
		setPos_x(96);
		setPos_y(96);
	}
	


	public int getDeath_animation_counter() {
		return death_animation_counter;
	}
	
	public void damage() {
		if (this.invulnerability <= 0) {
			
			this.health -= 1;
			if (this.health == 0) {
				this.dead = true;
			}
			else {
				this.invulnerability = 100;
			}
			
		}
	}

	
	public abstract void move(int tile_size, TileModel[][] map_structure, ControlsHandler control);
	public abstract boolean checkCollision(Coordinates[] hit_box, Direction dir, TileModel[][] map_structure, int tile_size);
	public abstract Coordinates[] damageHitBox(int tile_size);
	
	public Coordinates[] hitBox(int tile_size) {
		Coordinates[] hit_box = new Coordinates[4];
		hit_box[0] = new Coordinates(this.getPos_x(), this.getPos_y());
		hit_box[1] = new Coordinates(this.getPos_x()+tile_size-1, this.getPos_y());
		hit_box[2] = new Coordinates(this.getPos_x()+tile_size-1, this.getPos_y()+tile_size-1);
		hit_box[3] = new Coordinates(this.getPos_x(), this.getPos_y()+tile_size-1);
		return hit_box;
	}
	
	public abstract Coordinates[] collisionHitBox(int tile_size);
	
	public abstract boolean checkDamage(TileModel[][] map_structure);

}
