package model;

import controller.Coordinates;
import controller.listeners.ControlsHandler;
import view.GamePanel;

public class Projectile extends Enemy{

	private Direction dir;
	private int damage_timer = 0;
	private boolean projectile_expired = false;
	public Projectile(Direction dir, int pos_x, int pos_y) {
		super(pos_x,pos_y);
		this.dir = dir;
		this.move_speed = 8;
	}

	
	public boolean is_expired() {
		return this.projectile_expired;
	}
	
	@Override
	public void move(int tile_size, TileModel[][] map_structure, ControlsHandler control) {

		Coordinates[] hit_box = this.collisionHitBox(tile_size);
		boolean can_pass = !this.checkCollision(hit_box, dir, map_structure, tile_size);
		boolean has_damaged = this.hasDamagedBomberman(tile_size, hit_box);
		if (has_damaged) {
			Bomberman.getInstance().damage();					
		}
		

		switch(dir) {
		case UP:
			if (can_pass && getPos_y()-getMoveSpeed() >= 0) {
				up();
			}
			break;
		case RIGHT:
			if (can_pass && getPos_x()+tile_size+Bomberman.getInstance().getMoveSpeed() < GamePanel.getPanelWidth()) {
				right();
			}
			break;
		case DOWN:
			if (can_pass && getPos_y()+tile_size+this.move_speed < GamePanel.getPanelHeight()) {
				down();
			}
			break;
		case LEFT:
			if (can_pass && getPos_x()-this.getMoveSpeed() >= 0) {
				left();
			}
			break;
		default:
		}
		
	}

	@Override
	public boolean checkCollision(Coordinates[] hit_box, Direction dir, TileModel[][] map_structure, int tile_size) {
		boolean collision = super.checkCollision(hit_box, dir, map_structure, tile_size);
		if (collision) {
			this.projectile_expired = true;
			return true;
		}
		else {
			return false;
		}
	}





}
