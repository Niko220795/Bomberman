package model;

import controller.ControlsHandler;
import controller.Coordinates;
import view.GamePanel;

public abstract class Character extends Entity{
	

	protected boolean is_actually_dead = false;
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
	
	public boolean checkCollision(Coordinates[] hit_box, Direction dir, TileModel[][] map_structure, int tile_size) {
		boolean canPass1 = false;
		boolean canPass2 = false;
		boolean canPass3 = true;
		Coordinates[] bomberman_hit_box = Bomberman.getInstance().collisionHitBox(tile_size);
		switch(dir) {
		case UP:
			canPass1 = !map_structure[(hit_box[0].j-this.getMoveSpeed())/tile_size][hit_box[0].i/tile_size].getCollision();
			canPass2 = !map_structure[(hit_box[1].j-this.getMoveSpeed())/tile_size][hit_box[1].i/tile_size].getCollision();
//			canPass3 = map_structure[(hit_box[0].j-this.getMoveSpeed())/tile_size][hit_box[0].i/tile_size].getPlacedBomb() == null;
			break;
		case RIGHT:
			canPass1 = !map_structure[hit_box[1].j/tile_size][(hit_box[1].i+this.getMoveSpeed())/tile_size].getCollision();
			canPass2 = !map_structure[hit_box[2].j/tile_size][(hit_box[2].i+this.getMoveSpeed())/tile_size].getCollision();
//			canPass3 = map_structure[hit_box[1].j/tile_size][(hit_box[1].i+this.getMoveSpeed())/tile_size].getPlacedBomb() == null;
			break;
		case DOWN:
			canPass1 = !map_structure[(hit_box[2].j+this.getMoveSpeed())/tile_size][hit_box[2].i/tile_size].getCollision();
			canPass2 = !map_structure[(hit_box[3].j+this.getMoveSpeed())/tile_size][hit_box[3].i/tile_size].getCollision();
//			canPass3 = map_structure[(hit_box[2].j+this.getMoveSpeed())/tile_size][hit_box[2].i/tile_size].getPlacedBomb() == null;
			break;
		case LEFT:
			canPass1 = !map_structure[hit_box[3].j/tile_size][(hit_box[3].i-this.getMoveSpeed())/tile_size].getCollision();
			canPass2 = !map_structure[hit_box[0].j/tile_size][(hit_box[0].i-this.getMoveSpeed())/tile_size].getCollision();
//			canPass3 = map_structure[hit_box[3].j/tile_size][(hit_box[3].i-this.getMoveSpeed())/tile_size].getPlacedBomb() == null;
			break;
		default:
		}
		if (canPass1 && canPass2 && canPass3) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public Coordinates[] damageHitBox(int tile_size) {
		int hitBoxUpperLeft_x = getPos_x()+tile_size/5;
		int hitBoxUpperLeft_y = getPos_y()+tile_size*1/5;
		int hitBoxUpperRight_x = getPos_x() + tile_size - tile_size/5;
		int hitBoxUpperRight_y = getPos_y()+tile_size*1/5;
		int hitBoxBottomLeft_x = getPos_x()+tile_size/5;
		int hitBoxBottomLeft_y = getPos_y()+tile_size-tile_size/5;
		int hitBoxBottomRight_x = getPos_x() + tile_size - tile_size/5;
		int hitBoxBottomRight_y = getPos_y()+ tile_size-tile_size/5;
		Coordinates[] hit_box = new Coordinates[4];
		hit_box[0] = new Coordinates(hitBoxUpperLeft_x, hitBoxUpperLeft_y);
		hit_box[1] = new Coordinates(hitBoxUpperRight_x, hitBoxUpperRight_y);
		hit_box[2] = new Coordinates(hitBoxBottomRight_x, hitBoxBottomRight_y);
		hit_box[3] = new Coordinates(hitBoxBottomLeft_x, hitBoxBottomLeft_y);
		return hit_box;
		
	}
	
	public Coordinates[] hitBox(int tile_size) {
		Coordinates[] hit_box = new Coordinates[4];
		hit_box[0] = new Coordinates(this.getPos_x(), this.getPos_y());
		hit_box[1] = new Coordinates(this.getPos_x()+tile_size-1, this.getPos_y());
		hit_box[2] = new Coordinates(this.getPos_x()+tile_size-1, this.getPos_y()+tile_size-1);
		hit_box[3] = new Coordinates(this.getPos_x(), this.getPos_y()+tile_size-1);
		return hit_box;
	}
	
	public Coordinates[] collisionHitBox(int tile_size) {
		int hitBoxUpperLeft_x = getPos_x();
		int hitBoxUpperLeft_y = getPos_y();
		int hitBoxUpperRight_x = getPos_x() + tile_size - 1;
		int hitBoxUpperRight_y = getPos_y();
		int hitBoxBottomLeft_x = getPos_x();
		int hitBoxBottomLeft_y = getPos_y()+tile_size-1;
		int hitBoxBottomRight_x = getPos_x() + tile_size - 1;
		int hitBoxBottomRight_y = getPos_y()+ tile_size-1;
		Coordinates[] hit_box = new Coordinates[4];
		hit_box[0] = new Coordinates(hitBoxUpperLeft_x, hitBoxUpperLeft_y);
		hit_box[1] = new Coordinates(hitBoxUpperRight_x, hitBoxUpperRight_y);
		hit_box[2] = new Coordinates(hitBoxBottomRight_x, hitBoxBottomRight_y);
		hit_box[3] = new Coordinates(hitBoxBottomLeft_x, hitBoxBottomLeft_y);
		return hit_box;
		
	}
	public abstract boolean checkDamage(TileModel[][] map_structure);

}
