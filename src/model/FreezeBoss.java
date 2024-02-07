package model;

import java.util.ArrayList;
import java.util.Random;
import controller.Coordinates;
import controller.listeners.ControlsHandler;
import view.GamePanel;

public class FreezeBoss extends Walker{

	int shooting_timer = 1000;
	int moving_timer = 0;

	ArrayList<BossProjectile> boss_projectiles;
	Random r = new Random();
	public FreezeBoss(int x, int y, ArrayList<BossProjectile> boss_projectiles) {
		super(x, y, 3);
		this.move_speed = 2;
		this.boss_projectiles = boss_projectiles;
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

		Coordinates[] hit_box = new Coordinates[12];
		hit_box[0] = new Coordinates(x1,y1);
		hit_box[1] = new Coordinates(x2,y1);
		hit_box[2] = new Coordinates(x3,y1);
		hit_box[3] = new Coordinates(x4,y1);
		hit_box[4] = new Coordinates(x1,y2);
		hit_box[5] = new Coordinates(x4,y2);
		hit_box[6] = new Coordinates(x1,y3);
		hit_box[7] = new Coordinates(x4,y3);
		hit_box[8] = new Coordinates(x1,y4);
		hit_box[9] = new Coordinates(x2,y4);
		hit_box[10] = new Coordinates(x3,y4);
		hit_box[11] = new Coordinates(x4,y4);
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
		int hitBoxBottomLeft_y = getPos_y()+3*tile_size-1;
		int hitBoxBottomRight_x = getPos_x() + 3*tile_size - 1;
		int hitBoxBottomRight_y = getPos_y()+ 3*tile_size-1;
		hit_box[0] = new Coordinates(hitBoxUpperLeft_x, hitBoxUpperLeft_y);
		hit_box[1] = new Coordinates(hitBoxUpperRight_x, hitBoxUpperRight_y);
		hit_box[2] = new Coordinates(hitBoxBottomRight_x, hitBoxBottomRight_y);
		hit_box[3] = new Coordinates(hitBoxBottomLeft_x, hitBoxBottomLeft_y);

		return hit_box;
	}
	
	@Override
	public boolean checkCollision(Coordinates[] hit_box, Direction dir, TileModel[][] map_structure, int tile_size) {
		boolean canPass1 = false;
		boolean canPass2 = false;
		boolean canPass3 = true;
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
		if (canPass1 && canPass2 && canPass3) {
			return false;
		}
		else {
			return true;
		}
	}
	
	@Override
	public void move(int tile_size, TileModel[][] map_structure, ControlsHandler controls){

		if (this.invulnerability > 0) {
			this.invulnerability-=1;					
		}
		this.shooting_timer-=10;
		if (this.shooting_timer<= 0) {
			shootProjectile();
			this.shooting_timer = 1000;
		}
		if (!this.isDead() && this.shooting_timer > 500) {
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
			else if (this.dir == Direction.DOWN && getPos_y()+6*tile_size+getMoveSpeed() <
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
			else if (this.dir == Direction.LEFT && getPos_x()-getMoveSpeed() >= 0) {
				boolean canMove = !checkCollision(hit_box, Direction.LEFT, map_structure, tile_size);

				if (canMove) {
					move();
					this.setChanged();
				}
				else {
					changeDir();
				}
				
			}
			else if (this.dir == Direction.RIGHT && getPos_x()+3*tile_size+getMoveSpeed() < GamePanel.getPanelWidth())  {
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
		Coordinates[] hit_box = this.collisionHitBox(tile_size);
		boolean has_damaged = this.hasDamagedBomberman(tile_size, hit_box);
		if (has_damaged) {
			Bomberman.getInstance().damage();					
		}

		boolean damaged = this.checkDamage(map_structure);
		if (damaged) {
			this.damage();

		}
		this.damage_timer -= 10;
		this.setChanged();
		this.notifyObservers(this.shooting_timer);
		this.clearChanged();
		
		
	}

	/**
	 * Genera 5 o 6 proiettili e li spara in direzioni casuali. Viene chiamata in move
	 */
	public void shootProjectile() {
		for (int i = 0; i < 6; i++) {
			int r1 = r.nextInt(-5,5);
			int r2 = r.nextInt(-5,5);
			while (r1 == 0 && r2 == 0) {
				r1 = r.nextInt(-5,5);
				r2 = r.nextInt(-5,5);
			}
			ObliqueDirection dir = new ObliqueDirection(r1,r2);
			this.boss_projectiles.add(new BossProjectile(this.getPos_x(), this.getPos_y(), dir));
		}
	}

}
