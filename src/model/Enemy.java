package model;



import java.util.Random;

import controller.Coordinates;
import view.GamePanel;


public abstract class Enemy extends Character {
	
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
				System.out.println("hit");
				return true;
			}
		}
		return false;
	}
	
		
		
	
	/*
	 * Funzione utilitaria per associare un valore intero ad ogni direzione
	 */
	




	

}
