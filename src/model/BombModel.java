package model;
import java.util.HashSet;
import controller.AudioManager;
import view.GamePanel;

public class BombModel extends Entity {
	
	public boolean processed_explosion = false;
	public int up_explosion_limit = 1;
	public int right_explosion_limit = 1;
	public int down_explosion_limit = 1;
	public int left_explosion_limit = 1;
	public boolean scoreUpdated = false;
	private int fuse = 100;
	private boolean hasExploded;
	private boolean hasExpired;
	public boolean soundPlayed = false;
	public int animationCounter = 0;
	private HashSet<Character> damagedCharacter = new HashSet<Character>();
	public int explosionAnimationCounter = 0;
	private Direction slide_dir;
	private boolean can_slide = false;
	private int remote_explosion_duration = 30;

	/**
	 * Gestisce il limite dell'esplosione per disegnare la bomba.
	 */
	public void setExplosionLimit(Bomberman b) {
		this.up_explosion_limit = b.getExplosion_limit();
		this.right_explosion_limit = b.getExplosion_limit();
		this.down_explosion_limit = b.getExplosion_limit();
		this.left_explosion_limit = b.getExplosion_limit();
	}

	/**
	 * Istanzia una bomba nella posizione in input.
	 */
	public BombModel(int x, int y) {
		setPos_x(x);
		setPos_y(y);
	}

	/**
	 * Aggiorna il counter dell'animazione.
	 */
	public void updateAnimationCounter() {
		if (animationCounter == 12) {
			animationCounter = 0;
		}
		else animationCounter++;
	}

	/**
	 * Gestisce la durata dell'esplosione. Quando il timer scade, la bomba scade.
	 */
	public void decreaseRemoteExplosionDuration() {
		if (this.remote_explosion_duration > 0){
			this.remote_explosion_duration -= 1;
		}
		else {
			this.hasExpired = true;
		}
	}
	
	public boolean remoteExplosionExpired() {
		return this.remote_explosion_duration == 0;
	}

	/**
	 * Gestisce la durata dell'animazione della bomba prima e dopo l'esplosione.
	 * @param tile_size
	 */
	public void fireFuse(int tile_size) {
        if (fuse == 30) {
            explode(); // Facciamo esplodere la bomba quando il timer raggiunge zero
            AudioManager.getInstance().play(2);
        }
        if (fuse == 0) {
        	hasExpired = true;
        }
        fuse--;
        updateAnimationCounter();
	}
	

	public void explode() { //implementare tramite Observer-Observable
		this.hasExploded = true;
	}
	
	public boolean hasExploded() {
		return hasExploded;
	}
	
	public boolean hasExpired() {
		return hasExpired;
	}

	public boolean canSlide() {
		return can_slide;
	}

	public void setSliding(boolean can_slide) {
		this.can_slide = can_slide;
	}

	/**
	 * Gestisce il movimento della bomba per il power-up che permette di calciarla.
	 */
	public void slide(Direction dir, TileModel[][] map_structure) {
		if (this.can_slide && !this.hasExploded) {
			
			int row_tile = this.getPos_y()/GamePanel.FINAL_TILE_SIZE;
			int col_tile = this.getPos_x()/GamePanel.FINAL_TILE_SIZE;
			
			switch(slide_dir) {
			case UP:
				int above_row_tile = (this.getPos_y() - 1)/GamePanel.FINAL_TILE_SIZE;
				if (!map_structure[above_row_tile][col_tile].getCollision()) {
					this.setPos_y(this.getPos_y()-6);
				}
				break;
			case RIGHT:
				int right_col_tile = (this.getPos_x()+GamePanel.FINAL_TILE_SIZE)/GamePanel.FINAL_TILE_SIZE;
				if (!map_structure[row_tile][right_col_tile].getCollision()) {
					this.setPos_x(this.getPos_x()+6);
				}
				break;
			case DOWN:
				int below_row_tile = (this.getPos_y() + GamePanel.FINAL_TILE_SIZE)/GamePanel.FINAL_TILE_SIZE;
				if (!map_structure[below_row_tile][col_tile].getCollision()) {
					this.setPos_y(this.getPos_y()+6);
				}
				break;
			case LEFT:
				int left_col_tile = (this.getPos_x()-1)/GamePanel.FINAL_TILE_SIZE;
				if (!map_structure[row_tile][left_col_tile].getCollision()) {
					this.setPos_x(this.getPos_x()-6);
				}
				break;
			default:
				
			}
		}
	}

	public Direction getSlide_dir() {
		return slide_dir;
	}

	public void setSlide_dir(Direction slide_dir) {
		this.slide_dir = slide_dir;
	}
	
	
}
