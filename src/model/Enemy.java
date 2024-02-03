package model;



import java.util.Random;

import controller.ControlsHandler;
import controller.Coordinates;
import view.GamePanel;


public abstract class Enemy extends Character {
	
	
	private Direction prev_dir;
	private Direction curr_dir;
	protected int damage_timer = 0;
	Random r = new Random();

	
	/*
	 * Funzione per muovere un nemico. La prima parte introduce una componente random che potrebbe far effettuare un cambio di direzione ogni qual volta il 
	 * modello del nemico si trova sovrapposto perfettamente ad un tile (si verifica banalmente controllando l'angolo in alto a sinistra del modello)
	 * In seguito si controlla se il movimento sia possibile (controllando tramite hitObstacle che il modello non vada incontro ad un tile con collisione), nel quale
	 * caso si cambia direzione, altrimenti si effettua il movimento vero e proprio verso la direzione dir
	 */
	
	

	public Enemy(int x, int y) {
		this.setPos_x(x);
		this.setPos_y(y);
		this.health = 1;
	}

	@Override
	public boolean checkDamage(TileModel[][] map_structure) {
		Coordinates[] hitbox = damageHitBox(GamePanel.FINAL_TILE_SIZE);
		for (Coordinates c : hitbox) {
			int row = c.j/GamePanel.FINAL_TILE_SIZE;
			int col = c.i/GamePanel.FINAL_TILE_SIZE;
			if (map_structure[row][col].isExploding()) {
				return true;
			}
		}
		return false;
	}
	

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
	
	public Direction dirFromInt(int i) {
		Direction dir = Direction.UP;
		switch(i){
		case 0:
			dir = Direction.UP;
			break;
		case 1:
			dir = Direction.DOWN;
			break;
		case 2:
			dir = Direction.LEFT;
			break;
		case 3:
			dir = Direction.RIGHT;
			break;
		default:
		}
		return dir;
	}
	
	public void setDir(Direction dir) {
		this.dir = dir;
	}
	
	
	public void changeDir() {
	
		int i = r.nextInt(4);
		Direction dir = dirFromInt(i);
		int counter = 0;
		
		// Se la direzione ottenuta è l'opposto di quella da cui si viene, si fanno
		// cinque tentativi per ottenere una direzione differente.
		while (counter <= 5 && oppositeDir(dir, this.prev_dir) ) {
			while (dir == this.prev_dir) {
				i = r.nextInt(4);
				dir = dirFromInt(i);
			}
			counter++;
		}
		this.prev_dir = this.dir;
		this.dir = dir;
		
	}

	
	private int dir_number(char dir) {
		switch(dir) {
		case 'u':
			return 0;
		case 'd':
			return 2;
		case 'l':
			return 1;
		case 'r':
			return 3;
		default:
			return -1;
		}
	}
	
	/*
	 * Funzione utilitaria per calcolare che due direzioni non siano una l'opposto dell'altra. Usata per diminuire la componente casuale e fare
	 * in modo che il modello non torni troppo spesso dalla direzione da cui è venuto
	 */
	private boolean oppositeDir(Direction dir1, Direction dir2) {
		if (dir2 == null) {
			return false;
		}
		if (dir1 == Direction.UP && dir2 == Direction.DOWN) {
			return true;
		}
		if (dir1 == Direction.DOWN && dir2 == Direction.UP) {
			return true;
		}
		if (dir1 == Direction.LEFT && dir2 == Direction.RIGHT) {
			return true;
		}
		if (dir1 == Direction.RIGHT && dir2 == Direction.LEFT) {
			return true;
		}
		return false;
	}
	
	/*
	 * Funzione che cambia direzione in modo (quasi) casuale.
	 */
	


	
	public void move() {
//		if(this.getPos_x()%GamePanel.FINAL_TILE_SIZE == 0 && this.getPos_y()%GamePanel.FINAL_TILE_SIZE == 0) {
//			int i = r.nextInt(5);
//
//			if (i == 0) {
//				changeDir();
//			}
//		}
		switch(dir) {
		case UP:
			up();
			break;
		case DOWN:
			down();
			break;
		case LEFT:
			left();
			break;
		case RIGHT:
			right();
			break;
		}

	}
	
	@Override
	public void move(int tile_size, TileModel[][] map_structure, ControlsHandler controls) {
		
	
		
		if (!this.isDead()) {
			Coordinates[] hit_box = this.collisionHitBox(tile_size);
		
			if(this.getPos_x()%GamePanel.FINAL_TILE_SIZE == 0 && this.getPos_y()%GamePanel.FINAL_TILE_SIZE == 0) {
				int i = r.nextInt(5);
				
				if (i == 0) {
					changeDir();
				}
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
			else if (this.dir == Direction.DOWN && getPos_y()+tile_size+Bomberman.getInstance().getMoveSpeed() <= 
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
			else if (this.dir == Direction.RIGHT && getPos_x()+tile_size+Bomberman.getInstance().getMoveSpeed() <=GamePanel.getPanelWidth())  {
				boolean canMove = !checkCollision(hit_box, Direction.RIGHT, map_structure, tile_size);
				if (canMove) {
			
					move();
					this.setChanged();
				}
				else {
					changeDir();
				}
				
			}
			this.notifyObservers(this.getDir());
			this.clearChanged();
			boolean damaged = this.checkDamage(map_structure);
			if (damaged) {
				this.damage();
			}
			boolean has_damaged = this.hasDamagedBomberman(tile_size, hit_box);
			if (has_damaged) {
				Bomberman.getInstance().damage();					
			}
			
		}
		
	}
		
		
	
	/*
	 * Funzione utilitaria per associare un valore intero ad ogni direzione
	 */
	




	

}
