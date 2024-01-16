package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import controller.ControlsHandler;
import controller.Coordinates;

public class Laserer extends Enemy {
	private int shooting_cd = 0;
	private int laser_damage_cd = 0;
	private Direction shooting_dir;
	private HashMap<TileModel, LaserUtil> laser_tiles;
	
	
	public Laserer(int x, int y, Direction dir, HashMap<TileModel, LaserUtil> laser_tiles) {
		super(x,y);
		this.shooting_dir = dir;
		this.laser_tiles = laser_tiles;
	}
	
	public void laserDamage(int tile_size, TileModel[][] map_structure) {
		Bomberman b = Bomberman.getInstance();
		Coordinates[] hit_box = b.damageHitBox(tile_size);
		for (Coordinates c : hit_box) {
			int row = c.j/tile_size;
			int col = c.i/tile_size;
			if (this.laser_tiles.containsKey(map_structure[row][col])){
				if (this.laser_damage_cd <= 0) {
					b.damage();			
					this.laser_damage_cd = 100;
				}
			}
		}
		this.laser_damage_cd-=1;
	}

	@Override
	public void move(int tile_size, TileModel[][] map_structure, ControlsHandler control) {
		
		if (shooting_cd == 0) {
			this.shootLaser(tile_size, map_structure, this.laser_tiles);
			
			shooting_cd = 200;
		}
		else {
			shooting_cd -= 1;
		}
		this.laserDamage(tile_size, map_structure);
		
		Coordinates[] hit_box = this.collisionHitBox(tile_size);
		boolean has_damaged = this.hasDamagedBomberman(tile_size, hit_box);
		if (has_damaged) {
			Bomberman.getInstance().damage();					
		}
		boolean damaged = this.checkDamage(map_structure);
		if (damaged) {
			this.damage();
		}
		this.setChanged();
		this.notifyObservers(null);

	}

	@Override
	public boolean checkCollision(Coordinates[] hit_box, Direction dir, TileModel[][] map_structure, int tile_size) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void shootLaser(int tile_size, TileModel[][] map_structure, HashMap<TileModel, LaserUtil> laser_tiles) {
		int pos_row = this.getPos_y()/tile_size;
		int pos_col = this.getPos_x()/tile_size;
		System.out.println("laserer + " + pos_row);
		System.out.println("laserer + " + pos_col);
		int i = 1;
		switch(this.shooting_dir) {
		case UP:
			while(!map_structure[pos_row - i][pos_col].getCollision()) {
				this.laser_tiles.put(map_structure[pos_row - i][pos_col], new LaserUtil(50, this.shooting_dir));
				i+=1;
			}
			break;
		case RIGHT:
			while(!map_structure[pos_row][pos_col+i].getCollision()) {
				this.laser_tiles.put(map_structure[pos_row][pos_col+i], new LaserUtil(50, this.shooting_dir));
				i+=1;
			}
			break;
		case DOWN:
			while(!map_structure[pos_row + i][pos_col].getCollision()) {
				this.laser_tiles.put(map_structure[pos_row + i][pos_col], new LaserUtil(50, this.shooting_dir));
				i+=1;
			}
			break;
		case LEFT:
			while(!map_structure[pos_row][pos_col-i].getCollision()) {
				this.laser_tiles.put(map_structure[pos_row][pos_col -i], new LaserUtil(50, this.shooting_dir));
				i+=1;
			}
			break;
		default:
			
		}
		
//		Bomberman b = Bomberman.getInstance();
//		Coordinates[] hit_box = b.collisionHitBox(tile_size);
//		for (Coordinates c : hit_box) {
//			int row = c.j/tile_size;
//			int col = c.i/tile_size;
//			if (this.laser_tiles.containsKey(map_structure[row][col])){
//				b.damage();
//			}
//		}
		
	}

	@Override
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

	@Override
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

}
