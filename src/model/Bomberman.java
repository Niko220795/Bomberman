package model;


import view.GamePanel;

import java.util.ArrayList;
import java.util.HashSet;

import model.BombModel;
import model.Bomberman;
//import model.PowerUpModel;
import model.TileModel;
import controller.AudioManager;
import controller.Coordinates;
import controller.GameSetup;
import controller.MapEntities;
import controller.listeners.ControlsHandler;

public class Bomberman extends Character{
	//Inizio Singleton
	private static Bomberman bomberman; //Istanza di se stesso
	private PowerUpModel power_up = null;
	private boolean shield = false;
	private int ghosting_timer = 0;
	private int explosion_limit = 2;
	private int bomb_timer = 0;
	private int multiple_bombs_power_up_timer = 0;
	private int remote_bomb_power_up_timer = 0;
	private GameSetup game_setup;
	private int shield_invulnerability = 0;
	private int move_speed_buff_timer = 0;
	private boolean bonus_score = false;

	/**
	 * Resetta tutti i dati di Bomberman dopo una morte o un cambio di livello.
	 */
	public void reset() {
		this.death_animation_counter = 60;
		this.invulnerability = 0;
		this.health = 2;
		this.dead = false;
		this.is_actually_dead = false;
		power_up = null;
		shield = false;
		ghosting_timer = 0;
		explosion_limit = 2;
		bomb_timer = 0;
		multiple_bombs_power_up_timer = 0;
		shield_invulnerability = 0;
		move_speed_buff_timer = 0;
	}
	
	public int getExplosion_limit() {
		return explosion_limit;
	}


	public void setPower_up(PowerUpModel power_up) {
		this.power_up = power_up;
	}

	public int getMoveSpeed() {
		return this.move_speed;
	}
	
	public void setMoveSpeed(int i) {
		this.move_speed = i;
	}

	private Bomberman() {
		this.setHealth(2);
		
	}
	
	public static Bomberman getInstance() {
		if (bomberman == null) {
			bomberman = new Bomberman();
		}
		return bomberman;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}

	public PowerUpModel getPower_up() {
		return power_up;
	}
	
	public boolean canKickBombs() {
		return power_up != null && power_up.getId() == 2;
	}

	/**
	 * Gestisce il power-up per calciare le bombe.
	 */
	public void kickBombs(GameSetup game_setup) {
		if (this.canKickBombs()) {			
			var controls = game_setup.getControls();
			var map_structure = game_setup.getMap_structure();
			int bomberman_row = 0;
			int bomberman_col = 0;
			Coordinates[] hit_box = this.collisionHitBox(GamePanel.FINAL_TILE_SIZE);
			ArrayList<BombModel> bombs = new ArrayList<BombModel>();
			for (Coordinates c : hit_box) {
				bomberman_row = c.j/GamePanel.FINAL_TILE_SIZE;
				bomberman_col = c.i/GamePanel.FINAL_TILE_SIZE;
				BombModel bomb = map_structure[bomberman_row][bomberman_col].getPlacedBomb();
				if ( bomb != null) {
					bombs.add(bomb);
				}
			}
			for (BombModel bomb : bombs) {
				if (getPower_up() != null && !bomb.canSlide() &&  controls.canKickBomb()) {
					bomb.setSlide_dir(this.getDir());
					bomb.setSliding(true);
				}
			}
		}
	}

	public void resetPowerUps() {
		this.shield = false;
		this.move_speed = 4;
		this.ghosting_timer = 0;
	}

	public int getGhosting_timer() {
		return ghosting_timer;
	}

	public void setGhosting_timer(int ghosting_timer) {
		this.ghosting_timer = ghosting_timer;
	}
	
	public void decreaseGhosting_timer() {
		this.ghosting_timer -= 1;
	}

	public void setShield() {
		this.shield = true;
	}
	public void removeShield() {
		this.shield = false;
	}

	/**
	 * Gestisce l'aumento del range di esplosione delle bombe (power-up)
	 */
	public void increaseExplosionRange() {
		if (this.explosion_limit < 10) {
			this.explosion_limit+=1;
		}
	}

	/**
	 * Gestisce il danno subito
	 */
	@Override
	public void damage() {
		if (this.shield) {
			removeShield();
			this.shield_invulnerability  = 100;
		}
		else {
			if (this.shield_invulnerability == 0) {
				super.damage();				
				if (this.dead) {
					AudioManager.getInstance().play(5);
				}
			}
		}
	}

	/**
	 * Gestisce il movimento di Bomberman e il ghosting, utilizzando Observer-Observable
	 * @param tile_size
	 * @param map_structure
	 * @param controls tasti premuti
	 */
	@Override
	public void move(int tile_size, TileModel[][] map_structure, ControlsHandler controls) {
		if (!isDead()) {
				if (this.invulnerability > 0) {
					this.invulnerability-=1;					
				}
				if (this.shield_invulnerability > 0) {
					this.shield_invulnerability -= 1;
				}
				if (this.move_speed_buff_timer > 0) {
					this.move_speed = 7;
					this.move_speed_buff_timer -=1;
				}
				else {
					this.move_speed = 4;
				}
				if (getGhosting_timer() > 0) {
					decreaseGhosting_timer();
				
				}
				Coordinates[] hit_box = this.collisionHitBox(tile_size);
				boolean needs_to_ghost = false;
				for (Coordinates c : hit_box) {
					int c_row = c.j/GamePanel.FINAL_TILE_SIZE;
					int c_col = c.i/GamePanel.FINAL_TILE_SIZE;
					if (map_structure[c_row][c_col].getCollision()) {
						needs_to_ghost = true;
						break;
					}
				}
				boolean ghosting = false;
				if (power_up != null) {
					ghosting = power_up.getId() == 3 && this.ghosting_timer > 0;
				}

				if (controls.isUp() == true && 	getPos_y()-Bomberman.getInstance().getMoveSpeed() >= 0) {
					boolean canMove = !checkCollision(hit_box, Direction.UP, map_structure, tile_size);
					if (canMove || ghosting || needs_to_ghost) {
						up();
						this.setChanged(); //observer
					}
				}
				else if (controls.isDown() == true && getPos_y()+tile_size+Bomberman.getInstance().getMoveSpeed() <= 
						GamePanel.getPanelHeight()) {
					boolean canMove = !checkCollision(hit_box, Direction.DOWN, map_structure, tile_size);		
					if (canMove || ghosting || needs_to_ghost) {				
						down();
						this.setChanged();
					}
				}
				else if (controls.isLeft() == true && getPos_x()-Bomberman.getInstance().getMoveSpeed() >= 0) {
					boolean canMove = !checkCollision(hit_box, Direction.LEFT, map_structure, tile_size);
					if (canMove || ghosting || needs_to_ghost) {
						left();
						this.setChanged();
					}
					
				}
				else if (controls.isRight() == true && getPos_x()+tile_size+Bomberman.getInstance().getMoveSpeed() <=GamePanel.getPanelWidth())  {
					boolean canMove = !checkCollision(hit_box, Direction.RIGHT, map_structure, tile_size);
					if (canMove || ghosting || needs_to_ghost) {
						right();
						this.setChanged();
					}
					
				}
				this.checkPowerUp(map_structure);
				boolean damaged = this.checkDamage(map_structure);
				if (damaged) {
					this.damage();
				}
				this.notifyObservers(this.getDir()); //observer
				this.clearChanged();
			}
			
		}

	/**
	 * Controlla se si incontrano dei power-up, serve alla funzione move. Gestisce i power-up incontrati in base al loro id.
	 */
	public void checkPowerUp(TileModel[][] map_structure) {
		Coordinates[] hitbox = this.damageHitBox(GamePanel.FINAL_TILE_SIZE);
		for (Coordinates c : hitbox) {
			int row = c.j/GamePanel.FINAL_TILE_SIZE;
			int col = c.i/GamePanel.FINAL_TILE_SIZE;
			TileModel tile = map_structure[row][col];
			if (tile.getPowerUp() != null) {
				AudioManager.getInstance().play(7);
				PowerUpModel power_up = tile.getPowerUp();
				this.setPower_up(power_up);
				tile.removePowerUp();
				power_up.setPicked_up(true);
				switch(power_up.getId()) {
				case 1:
					this.multiple_bombs_power_up_timer = 300;
					break;
				case 3:
					this.ghosting_timer = 300;
					break;
				case 4:
					this.setShield();
					break;
				case 5:
					this.bonus_score = true;
					break;
				case 6:
					this.explosion_limit+=1;
					break;
				case 7:
					this.move_speed_buff_timer = 300;
					break;
				case 8:
					this.remote_bomb_power_up_timer = 300;
					break;
				default:
				}
			}
		}
	}
	
	public boolean hasBonusScore() {
		return bonus_score;
	}
	
	public void resetBonusScore() {
		this.bonus_score = false;
	}

	/**
	 * Gestisce il piazzamento della bomba con detonazione controllata.
	 */
	public void placeRemoteBomb(GameSetup game_setup) {
		if (game_setup.getMap_entities().getRemote_controlled_bomb().size() == 0) {
			int b_center_x = this.getPos_x() + GamePanel.FINAL_TILE_SIZE/2;
			int b_center_y = this.getPos_y() + GamePanel.FINAL_TILE_SIZE/2;
			int bomb_aligned_x = b_center_x - b_center_x%GamePanel.FINAL_TILE_SIZE;
			int bomb_aligned_y = b_center_y - b_center_y%GamePanel.FINAL_TILE_SIZE;
			int bomb_tile_col = bomb_aligned_x/GamePanel.FINAL_TILE_SIZE;
			int bomb_tile_row = bomb_aligned_y/GamePanel.FINAL_TILE_SIZE;
			if (game_setup.getRemote_bomb_listener().isPlaced()) {
				if ((power_up != null && power_up.getId() == 8) && this.remote_bomb_power_up_timer > 0){
					BombModel placedBomb = new BombModel(bomb_aligned_x, bomb_aligned_y);
					game_setup.getMap_entities().addRemoteBomb(placedBomb);
					game_setup.getMap_structure()[bomb_tile_row][bomb_tile_col].setPlacedBomb(placedBomb);
				}
			}
		}
		this.manageRemoteBombPowerUp();
	}

	/**
	 * Gestisce il piazzamento della bomba standard.
	 */
	public void placeBomb(GameSetup game_setup) {
		Bomberman b = Bomberman.getInstance();
		if (bomb_timer > 0) {
			bomb_timer -= 1;
		}
		if (game_setup.getControls().isSpace()) {
			//Valori calcolati per fare in modo che la bomba venga disegnata allineata con un tile
			
			int b_center_x = b.getPos_x() + GamePanel.FINAL_TILE_SIZE/2;
			int b_center_y = b.getPos_y() + GamePanel.FINAL_TILE_SIZE/2;
			int bomb_aligned_x = b_center_x - b_center_x%GamePanel.FINAL_TILE_SIZE;
			int bomb_aligned_y = b_center_y - b_center_y%GamePanel.FINAL_TILE_SIZE;
			int bomb_tile_col = bomb_aligned_x/GamePanel.FINAL_TILE_SIZE;
			int bomb_tile_row = bomb_aligned_y/GamePanel.FINAL_TILE_SIZE;
			
			//Si utilizza un timer per evitare di piazzare troppe bombe in un determinato istante di tempo. La bomba viene piazzata solo se il timer è giunto allo zero
			if (game_setup.getMap_entities().getPlaced_bombs().isEmpty() ||
					( (power_up != null && power_up.getId() ==1) && this.multiple_bombs_power_up_timer > 0 ) ) {
				if (bomb_timer == 0) {
					AudioManager.getInstance().play(1);
					BombModel placedBomb = new BombModel(bomb_aligned_x, bomb_aligned_y);
					//ogni volta che viene piazzata una bomba, essa viene inserita in placedBombs e gli viene associato il set di tutti i tiles che saranno affetti
					//dalla sua fiamma. Inizialmente questo set è vuoto e viene costruito in modo adeguato da drawBombs, ma è sbagliato farlo in quella funzione
					//perché fa già troppe cose. Si può costruire una funzione utilitaria che calcola i tile dove saranno le fiamme e chiamarla direttamente qui dentro
					game_setup.getMap_entities().addBomb(placedBomb);

					game_setup.getMap_structure()[bomb_tile_row][bomb_tile_col].setPlacedBomb(placedBomb);
					//Si riavvia il timer dopo il piazzamento
					bomb_timer = 17;	
				}
			}
		}
		this.manageMultipleBombsPowerUp();
	}

	/**
	 * Gestisce il timer del power-up
	 */
	public void manageMultipleBombsPowerUp() {
		if (power_up != null) {
			if (power_up.getId() == 1 && multiple_bombs_power_up_timer > 0) {
				multiple_bombs_power_up_timer -= 1;
			}
		}
	}

	/**
	 * Gestisce il timer del power-up
	 */
	public void manageRemoteBombPowerUp() {
		if (power_up != null) {
			if (power_up.getId() == 8 && remote_bomb_power_up_timer > 0) {
				remote_bomb_power_up_timer -= 1;
			}
		}
	}


	/**
	 * Effettua il controllo sulle collisioni.
	 * @return
	 */
	@Override
	public boolean checkCollision(Coordinates[] hit_box, Direction dir, TileModel[][] map_structure, int tile_size) {
		boolean canPass1 = false;
		boolean canPass2 = false;
		switch(dir) {
		case UP:
			canPass1 = !map_structure[(hit_box[0].j-this.getMoveSpeed())/tile_size][hit_box[0].i/tile_size].getCollision();
			canPass2 = !map_structure[(hit_box[1].j-this.getMoveSpeed())/tile_size][hit_box[1].i/tile_size].getCollision();
			break;
		case RIGHT:
			canPass1 = !map_structure[hit_box[1].j/tile_size][(hit_box[1].i+this.getMoveSpeed())/tile_size].getCollision();
			canPass2 = !map_structure[hit_box[2].j/tile_size][(hit_box[2].i+this.getMoveSpeed())/tile_size].getCollision();
			break;
		case DOWN:
			canPass1 = !map_structure[(hit_box[2].j+this.getMoveSpeed())/tile_size][hit_box[2].i/tile_size].getCollision();
			canPass2 = !map_structure[(hit_box[3].j+this.getMoveSpeed())/tile_size][hit_box[3].i/tile_size].getCollision();
			break;
		case LEFT:
			canPass1 = !map_structure[hit_box[3].j/tile_size][(hit_box[3].i-this.getMoveSpeed())/tile_size].getCollision();
			canPass2 = !map_structure[hit_box[0].j/tile_size][(hit_box[0].i-this.getMoveSpeed())/tile_size].getCollision();
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


	/**
	 *  Ritorna le coordinate dell'hitbox di Bomberman
	 */
	@Override
	public Coordinates[] collisionHitBox(int tile_size) {
		int hitBoxUpperLeft_x = getPos_x()+tile_size/5;
		int hitBoxUpperLeft_y = getPos_y()+tile_size*2/5;
		int hitBoxUpperRight_x = getPos_x() + tile_size - tile_size/5;
		int hitBoxUpperRight_y = getPos_y()+tile_size*2/5;
		int hitBoxBottomLeft_x = getPos_x()+tile_size/5;
		int hitBoxBottomLeft_y = getPos_y()+tile_size-1;
		int hitBoxBottomRight_x = getPos_x() + tile_size - tile_size/5;
		int hitBoxBottomRight_y = getPos_y()+ tile_size-1;
		Coordinates[] hit_box = new Coordinates[4];
		hit_box[0] = new Coordinates(hitBoxUpperLeft_x, hitBoxUpperLeft_y);
		hit_box[1] = new Coordinates(hitBoxUpperRight_x, hitBoxUpperRight_y);
		hit_box[2] = new Coordinates(hitBoxBottomRight_x, hitBoxBottomRight_y);
		hit_box[3] = new Coordinates(hitBoxBottomLeft_x, hitBoxBottomLeft_y);
		return hit_box;
		
	}

	/**
	 * Ritorna le coordinate dell'hitbox di Bomberman relativo al danno
	 */
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

	/**
	 * Controlla se Bomberman si trova su un tile di uscita.
	 */
	public boolean checkExit(TileModel[][] map_structure) {
		Coordinates[] hitbox = this.collisionHitBox(GamePanel.FINAL_TILE_SIZE);

		for (Coordinates c : hitbox) {
			int row = c.j/GamePanel.FINAL_TILE_SIZE;
			int col = c.i/GamePanel.FINAL_TILE_SIZE;
			if (map_structure[row][col].isExit()) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Controlla se Bomberman sta su un tile con un'entity che provoca danno.
	 */
	@Override
	public boolean checkDamage(TileModel[][] map_structure) {
		Coordinates[] hitbox = this.damageHitBox(GamePanel.FINAL_TILE_SIZE);
		for (Coordinates c : hitbox) {
			int row = c.j/GamePanel.FINAL_TILE_SIZE;
			int col = c.i/GamePanel.FINAL_TILE_SIZE;
			if (map_structure[row][col].isExploding()) {
				return true;
			}
		}
		int row = (Bomberman.getInstance().getPos_y()+GamePanel.FINAL_TILE_SIZE/2)/GamePanel.FINAL_TILE_SIZE;
		int col = (Bomberman.getInstance().getPos_x()+GamePanel.FINAL_TILE_SIZE/2)/GamePanel.FINAL_TILE_SIZE;
		if ( map_structure[row][col].hasTrap()) {
			map_structure[row][col].getPlaced_trap().step_on();
			map_structure[row][col].resetTrap();
			return true;
		}
		return false;
	}

}
