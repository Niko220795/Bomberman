package model;

import java.util.ArrayList;

import controller.Coordinates;
import controller.listeners.ControlsHandler;
import view.GamePanel;

public class FatBoss extends Walker {
	int attack_timer = 1500;
	int state = 0;
	int moving_timer = 0;
	
	@Override
	public boolean checkCollision(Coordinates[] hit_box, Direction dir, TileModel[][] map_structure, int tile_size) {
		boolean canPass1 = false;
		boolean canPass2 = false;
		boolean canPass3 = false;
		switch(dir) {
		case UP:
			canPass1 = !map_structure[(hit_box[0].j-this.getMoveSpeed())/tile_size][hit_box[0].i/tile_size].isBorder();
			canPass2 = !map_structure[(hit_box[1].j-this.getMoveSpeed())/tile_size][hit_box[1].i/tile_size].isBorder();
			break;
		case RIGHT:
			canPass1 = !map_structure[hit_box[1].j/tile_size][(hit_box[1].i+this.getMoveSpeed())/tile_size].isBorder();
			canPass2 = !map_structure[hit_box[2].j/tile_size][(hit_box[2].i+this.getMoveSpeed())/tile_size].isBorder();
			break;
		case DOWN:
			canPass1 = !map_structure[(hit_box[2].j+this.getMoveSpeed())/tile_size][hit_box[2].i/tile_size].isBorder();
			canPass2 = !map_structure[(hit_box[3].j+this.getMoveSpeed())/tile_size][hit_box[3].i/tile_size].isBorder();
			break;
		case LEFT:
			canPass1 = !map_structure[hit_box[3].j/tile_size][(hit_box[3].i-this.getMoveSpeed())/tile_size].isBorder();
			canPass2 = !map_structure[hit_box[0].j/tile_size][(hit_box[0].i-this.getMoveSpeed())/tile_size].isBorder();
			break;
		default:
		}
		if (canPass1 && canPass2) {
			return false;
		}
		else {
			return true;
		}
	}

//	// 0 base
//	// 1 windup
//	// 2 attack
	public FatBoss(int x, int y, int health) {
		super(x,y,health);
		// TODO Auto-generated constructor stub
	}

	public FatBoss(int x, int y) {
		super(x, y);
		this.move_speed = 2;
		this.health = 2;
	}

	@Override
	public void move(int tile_size, TileModel[][] map_structure, ControlsHandler controls) {
		if (!isDead()) {
			if (this.invulnerability > 0) {
				this.invulnerability-=1;					
			}
		}
		if (this.attack_timer < 500) {
			
			this.attack_timer-=10;
		}
		else {
			
			this.attack_timer-= 5;
		}
		if (this.attack_timer< 0) {
			this.attack_timer = 1499;
		}
		this.state = 2 - this.attack_timer/500;
		if (!this.isDead() && this.attack_timer > 500) {
			Coordinates[] hit_box = this.collisionHitBox(tile_size);
			if (moving_timer == 100) {
				changeDir();
				moving_timer = 0;
			}
			else {
				moving_timer++;
			}
			
	
			
			if (this.dir == Direction.UP && 	getPos_y()-getMoveSpeed() >= 0) {
				
				boolean canMove = !checkCollision(hit_box, Direction.UP, map_structure, tile_size);
				if (canMove) {
					move();
					this.setChanged();
				}
				else {
					changeDir();
				}
			}
			else if (this.dir == Direction.DOWN && getPos_y()+7*tile_size+Bomberman.getInstance().getMoveSpeed() <= 
					GamePanel.getPanelHeight()) {
				boolean canMove = !checkCollision(hit_box, Direction.DOWN, map_structure, tile_size);		
				if (canMove) {	

					move();
					this.setChanged();
				}
				else {
					changeDir();
				}
			}
			else if (this.dir == Direction.LEFT && getPos_x()-Bomberman.getInstance().getMoveSpeed() >= 0) {
				boolean canMove = !checkCollision(hit_box, Direction.LEFT, map_structure, tile_size);
				if (canMove) {

					move();
					this.setChanged();
				}
				else {
					changeDir();
				}
				
			}
			else if (this.dir == Direction.RIGHT && getPos_x()+3*tile_size+Bomberman.getInstance().getMoveSpeed() <=GamePanel.getPanelWidth())  {
				boolean canMove = !checkCollision(hit_box, Direction.RIGHT, map_structure, tile_size);
				if (canMove) {

					move();
					this.setChanged();
				}
				else {
					changeDir();
				}
				
			}
			else {
				changeDir();
			}
			
		}
		
		Coordinates[] hit_box = this.bossDamageHitBox(tile_size);
		Coordinates[] hammer_hit_box = this.hammerDamageHitBox(tile_size);
		
		boolean damage = false;
		if (this.state == 0 || this.state == 1) {
			damage = this.hasDamagedBomberman(tile_size, hit_box);
		}
		else {
			damage = this.hasDamagedBomberman(tile_size, hammer_hit_box) || this.hasDamagedBomberman(tile_size, hit_box);
		}
		if (damage) {
			Bomberman.getInstance().damage();
		}
		boolean damaged = this.checkDamage(map_structure);
		if (damaged) {
			System.out.println("damaged boss");
			this.damage();
		}
		this.setChanged();
		this.notifyObservers(this.attack_timer);		
	}
	
	@Override
	public boolean hasDamagedBomberman(int tile_size, Coordinates[] hit_box) {
		Bomberman b = Bomberman.getInstance();
		Coordinates[] b_hit_box = b.damageHitBox(tile_size);
		for (Coordinates c : b_hit_box) {
			if (c.i > hit_box[0].i && c.i < hit_box[1].i && c.j > hit_box[0].j && c.j < hit_box[2].j) {
				return true;
			}
		}
		return false;
	}
	


	@Override
	public Coordinates[] damageHitBox(int tile_size) {
		int x1 = getPos_x();
		int x2 = getPos_x() + tile_size;
		int x3 = getPos_x() + 2*tile_size;
		int x4 = getPos_x() + 3*tile_size - 1;
		int y1 = getPos_y();
		int y2 = getPos_y() + tile_size;
		int y3 = getPos_y() + 2*tile_size;
		int y4 = getPos_y() + 3*tile_size;
		int y5 = getPos_y() + 4*tile_size;

		Coordinates[] hit_box = new Coordinates[12];

		hit_box[0] = new Coordinates(x1,y2);
		hit_box[1] = new Coordinates(x2,y2);
		hit_box[2] = new Coordinates(x3,y2);
		hit_box[3] = new Coordinates(x4,y2);
		hit_box[4] = new Coordinates(x1,y3);
		hit_box[5] = new Coordinates(x4,y3);
		hit_box[6] = new Coordinates(x1,y4);
		hit_box[7] = new Coordinates(x4,y4);
		hit_box[8] = new Coordinates(x1,y5);
		hit_box[9] = new Coordinates(x2,y5);
		hit_box[10] = new Coordinates(x3,y5);
		hit_box[11] = new Coordinates(x4,y5);
		return hit_box;

		
	}
	
	
	@Override
	public Coordinates[] collisionHitBox(int tile_size) {

		Coordinates[] hit_box = new Coordinates[4];


		int hitBoxUpperLeft_x = getPos_x();
		int hitBoxUpperLeft_y = getPos_y();
		int hitBoxUpperRight_x = getPos_x() + 3*tile_size - 1;
		int hitBoxUpperRight_y = getPos_y();
		int hitBoxBottomLeft_x = getPos_x();
		int hitBoxBottomLeft_y = getPos_y()+4*tile_size-1;
		int hitBoxBottomRight_x = getPos_x() + 3*tile_size - 1;
		int hitBoxBottomRight_y = getPos_y()+ 4*tile_size-1;
		hit_box[0] = new Coordinates(hitBoxUpperLeft_x, hitBoxUpperLeft_y);
		hit_box[1] = new Coordinates(hitBoxUpperRight_x, hitBoxUpperRight_y);
		hit_box[2] = new Coordinates(hitBoxBottomRight_x, hitBoxBottomRight_y);
		hit_box[3] = new Coordinates(hitBoxBottomLeft_x, hitBoxBottomLeft_y);

		return hit_box;
	}
	
	public Coordinates[] hammerDamageHitBox(int tile_size) {
		int hammerUpperLeft_x = getPos_x() + tile_size;
		int hammerUpperLeft_y = getPos_y() + tile_size*4;
		int hammerUpperRight_x = getPos_x() + tile_size*2;
		int hammerUpperRight_y = getPos_y() +tile_size*4;
		int hammerBottomLeft_x = getPos_x() + tile_size;
		int hammerBottomLeft_y = getPos_y() + tile_size*6;
		int hammerBottomRight_x = getPos_x() + tile_size*2;
		int hammerBottomRight_y = getPos_y() + tile_size*6;
		Coordinates hammer_top_left = new Coordinates(hammerUpperLeft_x,hammerUpperLeft_y);
		Coordinates hammer_top_right = new Coordinates(hammerUpperRight_x,hammerUpperRight_y);
		Coordinates hammer_bottom_left = new Coordinates(hammerBottomLeft_x,hammerBottomLeft_y);
		Coordinates hammer_bottom_right = new Coordinates(hammerBottomRight_x,hammerBottomRight_y);
		Coordinates[] hit_box = new Coordinates[4];
		hit_box[0] = hammer_top_left;
		hit_box[1] = hammer_top_right;
		hit_box[2] = hammer_bottom_right;
		hit_box[3] = hammer_bottom_left;
		return hit_box;
	}
	public Coordinates[] bossDamageHitBox(int tile_size) {
		int hitBoxUpperLeft_x = getPos_x();
		int hitBoxUpperLeft_y = getPos_y()+tile_size;
		int hitBoxUpperRight_x = getPos_x() + 3*tile_size - 1;
		int hitBoxUpperRight_y = getPos_y()+tile_size;
		int hitBoxBottomLeft_x = getPos_x();
		int hitBoxBottomLeft_y = getPos_y()+4*tile_size-1;
		int hitBoxBottomRight_x = getPos_x() + 3*tile_size - 1;
		int hitBoxBottomRight_y = getPos_y()+ 4*tile_size-1;
		
		Coordinates top_left = new Coordinates(hitBoxUpperLeft_x,hitBoxUpperLeft_y);
		Coordinates top_right = new Coordinates(hitBoxUpperRight_x,hitBoxUpperRight_y);
		Coordinates bottom_left = new Coordinates(hitBoxBottomLeft_x,hitBoxBottomLeft_y);
		Coordinates bottom_right = new Coordinates(hitBoxBottomRight_x,hitBoxBottomRight_y);
		
		Coordinates[] hit_box = new Coordinates[4];

		hit_box[0] = top_left;
		hit_box[1] = top_right;
		hit_box[2] = bottom_right;
		hit_box[3] = bottom_left;
		
		return hit_box;

	}
	

}


