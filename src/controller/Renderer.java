package controller;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import model.PowerUpModel;
import model.BossProjectile;
import model.BombModel;
import model.Bomberman;
import model.Character;
import model.FatBoss;
import model.FreezeBoss;
import model.Projectile;
import model.TileModel;
import model.TrapModel;
import view.BombView;
import view.EntityView;
import view.FreezeBossView;
import view.GamePanel;
import view.ShooterView;
import view.TrapperView;

public class Renderer {
	GameSetup game_setup;
	StateUpdater state_updater;
	MapEntities map_entities;

	/**
	 * Costruttore che inizializza il renderer.
	 */
	public Renderer(GameSetup game_setup, StateUpdater state_updater, MapEntities map_entities) {
		this.game_setup = game_setup;
		this.state_updater = state_updater;
		this.map_entities = map_entities;
	}


	/**
	 * Disegna i laser del nemico 'Laserer'
	 * @param g viene passato come argomento dal paintComponent del gamePanel.
	 */
	public void drawLaser(Graphics g) {
	var laser_tiles = this.map_entities.getLaser_tiles();
	for (TileModel t : laser_tiles.keySet()) {
		int x = t.getMatrix_pos_col()*GamePanel.FINAL_TILE_SIZE;
		int y = t.getMatrix_pos_row()*GamePanel.FINAL_TILE_SIZE;
		BufferedImage laser;
		switch(laser_tiles.get(t).dir) {
		case UP, DOWN:		
			laser = this.game_setup.getLaser_view().getV_sprite();
			break;
		case LEFT, RIGHT:
			laser = this.game_setup.getLaser_view().getH_sprite();
			break;
		default:
			laser = this.game_setup.getLaser_view().getH_sprite();
		}
		g.drawImage(laser, x, y, GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE, null);
		
	}
}

	/**
	 * Disegna i proiettili del freeze Boss
	 */
	public void drawBossProjectiles(Graphics g) {
	for (Iterator<BossProjectile> iterator = this.map_entities.getBoss_projectiles().iterator(); iterator.hasNext();) {
		BossProjectile t = iterator.next();

		g.drawImage(FreezeBossView.getProjectileSprite(), t.getPos_x(), t.getPos_y(), 
				GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE, null);
	}
}

	/**
	 * Disegna i proiettili del nemico Shooter
	 */
	public void drawProjectiles(Graphics g) {
	var projectile = ShooterView.getProjectileSprite();
	var projectiles = this.map_entities.getProjectiles();
	for (Projectile p : projectiles) {
		g.drawImage(projectile, p.getPos_x(), p.getPos_y(), GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE, null);
	}
}

	/**
	 * Disegna le trapole del nemico Trapper skrrt skrrt
	 */
	public void drawTraps(Graphics g) {
		var trap = TrapperView.getTrapSprite();
		var traps = this.map_entities.getTraps();
		for (TrapModel t : traps) {
			g.drawImage(trap, t.getCol()*GamePanel.FINAL_TILE_SIZE, t.getRow()*GamePanel.FINAL_TILE_SIZE,
			GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE, null);

		}
	}

	/**
	 * Disegna tutti i personaggi sulla mappa, compreso Bomberman e tutti i nemici.
	 */
	public void drawCharacters(Graphics g) {
	for (Character c : this.game_setup.getCharacterModelsView().keySet()) {
		EntityView view = this.game_setup.getCharacterModelsView().get(c);
		if (c.isDead()) {
			BufferedImage sprite = view.getDeadSprite(c.getDeath_animation_counter());

			if (c instanceof FatBoss || c instanceof FreezeBoss) {
				g.drawImage(sprite, c.getPos_x(), c.getPos_y(), view.getSpriteWidth()*2, view.getSpriteHeight()*2, null);

			}
			else {
				g.drawImage(sprite, c.getPos_x()+view.getSpriteWidth()/2, c.getPos_y(), view.getSpriteWidth()*2, view.getSpriteHeight()*2, null);				
			}

		}
		else {
			BufferedImage sprite = view.getSprite();
			if (c instanceof Bomberman) {
				if (c.invulnerability%2 == 0) {
					g.drawImage(sprite, c.getPos_x()+view.getSpriteWidth()/2, c.getPos_y(), view.getSpriteWidth()*2, view.getSpriteHeight()*2, null);				
				}
			}
			else if (c instanceof FatBoss || c instanceof FreezeBoss) {
				if (c.invulnerability%2 == 0) {
					g.drawImage(sprite, c.getPos_x(), c.getPos_y(), view.getSpriteWidth()*2, view.getSpriteHeight()*2, null);													
				}

			}
			else if (c instanceof FreezeBoss) {
				g.drawImage(sprite, c.getPos_x(), c.getPos_y(), view.getSpriteWidth()*2, view.getSpriteHeight()*2, null);								
			}
			else {
				g.drawImage(sprite, c.getPos_x()+view.getSpriteWidth()/2, c.getPos_y(), view.getSpriteWidth()*2, view.getSpriteHeight()*2, null);								
			}

		}
	}
}

	/**
	 * Disegna tutti i tiles della mappa, inoltre se un tile viene colpito dalla bomba, disegna l'animazione dell'esplosione. Se il tile è invece di tipo exit, disegnerà il tile di uscita.
	 * @param mapStructure file di configurazione della mappa.
	 */
	public void drawTile(Graphics g, TileModel[][] mapStructure) {

			int h_tiles_num = GamePanel.X_TILES;
			int v_tiles_num = GamePanel.Y_TILES;
			int tile_width = GamePanel.FINAL_TILE_SIZE;
			for (int j = 0; j < h_tiles_num; j++) {
				for (int k = 0; k < v_tiles_num; k++) {
					TileModel tile = mapStructure[k][j];
					int tile_num = tile.getModel_num();
					if (tile.isDisappearing()) {
						g.drawImage(game_setup.getTile_view().getExploding_block()[5-(tile.destruction_counter/10)%6], j*tile_width, k*tile_width, tile_width, tile_width, null);
					}
					else {
						if(tile.isExit()) {
							g.drawImage(game_setup.getTile_view().getExit_tile(), j*tile_width, k*tile_width, tile_width, tile_width, null);
						}
						else {
							g.drawImage(game_setup.getTile_view().getTileSamples()[tile_num-1], j*tile_width, k*tile_width, tile_width, tile_width, null);
						}
					}
				}
			}
		}

	/**
	 * Questa funzione disegna l'esplosione della bomba.
	 */
	public void drawBombExplosion(Graphics g, BombModel b, HashMap<BombModel, HashSet<TileModel>> placed_bombs) {
			TileModel[][] map_structure = this.game_setup.getMap_structure();
			ArrayList<TileModel> tiles_to_update = this.state_updater.getTiles_to_update();
			BombView bombView = this.game_setup.getBomb_view();
			int b_tile_col = b.getPos_x()/GamePanel.FINAL_TILE_SIZE;
			int b_tile_row = b.getPos_y()/GamePanel.FINAL_TILE_SIZE;
			//segnala che il tile colpito dalla bomba sta esplodendo, se sì, lo setta come setExploding
			map_structure[b_tile_row][b_tile_col].setExploding(true);
			placed_bombs.get(b).add(map_structure[b_tile_row][b_tile_col]);
			if (!b.processed_explosion) {
				b.setExplosionLimit(Bomberman.getInstance());
				game_setup.getMap_structure()[b_tile_row][b_tile_col].setPlacedBomb(null);
			}
			g.drawImage(game_setup.getBomb_view().cExplosionAnimations[(b.explosionAnimationCounter/5)%3], b.getPos_x(), b.getPos_y(), GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE,null);
			for (int j = 0; j < b.up_explosion_limit; j++) {
				//si controllano i bounds della mappa per l'esplosione
				if (b_tile_row-(j+1) >= 0 && b_tile_row-(j+1) < GamePanel.Y_TILES && b_tile_col >= 0 && b_tile_col < GamePanel.X_TILES) {
					//se viene incontrato dalla fiamma un tile con collisione attiva si interrompe il disegno della fiamma e si aggiunge il tile
					//ai tile da modificare (tiles_to_update) se il tile è distruttibile.
					if (map_structure[b_tile_row-(j+1)][b_tile_col].getCollision() && !b.processed_explosion) {
						b.up_explosion_limit = j;
						if (map_structure[b_tile_row-(j+1)][b_tile_col].getDestructible() == true) {
							map_structure[b_tile_row-(j+1)][b_tile_col].setDisappearing(true);
							tiles_to_update.add(map_structure[b_tile_row-(j+1)][b_tile_col]);
						}
						break;
					}
					else {
						//se l'esplosione può continuare ad espandersi si aggiunge il tile della fiamma al set di tiles di tutte le fiamme associate ad ogni bomba
						//nota: questa cosa va fatta in un'altra funzione!
						if (!b.processed_explosion) {
							placed_bombs.get(b).add(map_structure[b_tile_row-(j+1)][b_tile_col]);
							map_structure[b_tile_row-(j+1)][b_tile_col].setExploding(true);
						}
						//disegna la fiamma in quel tile prima di procedere al prossimo ed espanderla ulteriormente
						if (j == Bomberman.getInstance().getExplosion_limit()-1) {
							g.drawImage(bombView.explosionMatrix[0][1][(b.explosionAnimationCounter/5)%3],
									b.getPos_x(), b.getPos_y()-(j+1)*GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE,null);

						}

						else {

							g.drawImage(bombView.explosionMatrix[0][0][(b.explosionAnimationCounter/5)%3],
									b.getPos_x(), b.getPos_y()-(j+1)*GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE, null);
						}
					}
				}
			}
			for (int j = 0; j < b.right_explosion_limit; j++) {
				if (b_tile_row >= 0 && b_tile_row < GamePanel.Y_TILES && b_tile_col+j+1 >= 0 && b_tile_col+j+1 < GamePanel.X_TILES) {
					if (map_structure[b_tile_row][b_tile_col+j+1].getCollision() && !b.processed_explosion) {
						b.right_explosion_limit = j;

						if (map_structure[b_tile_row][b_tile_col+j+1].getDestructible() == true) {
							map_structure[b_tile_row][b_tile_col+j+1].setDisappearing(true);
							tiles_to_update.add(map_structure[b_tile_row][b_tile_col+j+1]);
						}
						break;
					}
					else {
						if (!b.processed_explosion) {
							placed_bombs.get(b).add(map_structure[b_tile_row][b_tile_col+j+1]);
							map_structure[b_tile_row][b_tile_col+j+1].setExploding(true);
						}
						if (j == Bomberman.getInstance().getExplosion_limit()-1) {
							g.drawImage(bombView.explosionMatrix[1][1][(b.explosionAnimationCounter/5)%3],
									b.getPos_x()+(j+1)*GamePanel.FINAL_TILE_SIZE, b.getPos_y(), GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE,null);

						}

						else {
							g.drawImage(bombView.explosionMatrix[1][0][(b.explosionAnimationCounter/5)%3],
									b.getPos_x()+(j+1)*GamePanel.FINAL_TILE_SIZE, b.getPos_y(), GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE,null);

						}
					}
				}
			}
			for (int j = 0; j < b.down_explosion_limit; j++) {
				if (b_tile_row+j+1 >= 0 && b_tile_row+j+1 < GamePanel.Y_TILES && b_tile_col >= 0 && b_tile_col < GamePanel.X_TILES) {

					if (map_structure[b_tile_row+j+1][b_tile_col].getCollision() && !b.processed_explosion) {
						b.down_explosion_limit = j;
						if (map_structure[b_tile_row+j+1][b_tile_col].getDestructible() == true) {
							map_structure[b_tile_row+j+1][b_tile_col].setDisappearing(true);
							tiles_to_update.add(map_structure[b_tile_row+j+1][b_tile_col]);
						}
						break;

					}
					else {
						if (!b.processed_explosion) {
							placed_bombs.get(b).add(map_structure[b_tile_row+j+1][b_tile_col]);
							map_structure[b_tile_row+j+1][b_tile_col].setExploding(true);
						}

						if (j == Bomberman.getInstance().getExplosion_limit()-1) {
							g.drawImage(bombView.explosionMatrix[2][1][(b.explosionAnimationCounter/5)%3],
									b.getPos_x(), b.getPos_y()+(j+1)*GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE,null);

						}

						else {
							g.drawImage(bombView.explosionMatrix[2][0][(b.explosionAnimationCounter/5)%3],
									b.getPos_x(), b.getPos_y()+(j+1)*GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE,null);

						}
					}
				}
			}
			for (int j = 0; j < b.left_explosion_limit; j++) {
				if (b_tile_row >= 0 && b_tile_row < GamePanel.Y_TILES && b_tile_col-(j+1) >= 0 && b_tile_col-(j+1) < GamePanel.X_TILES) {
					if (map_structure[b_tile_row][b_tile_col-(j+1)].getCollision() && !b.processed_explosion) {
						b.left_explosion_limit = j;
						if (map_structure[b_tile_row][b_tile_col-(j+1)].getDestructible() == true) {
							map_structure[b_tile_row][b_tile_col-(j+1)].setDisappearing(true);
							tiles_to_update.add(map_structure[b_tile_row][b_tile_col-(j+1)]);
						}
						break;
					}
					else {
						if (!b.processed_explosion) {
							placed_bombs.get(b).add(map_structure[b_tile_row][b_tile_col-(j+1)]);
							map_structure[b_tile_row][b_tile_col-(j+1)].setExploding(true);
						}
						if (j == Bomberman.getInstance().getExplosion_limit()-1) {
							g.drawImage(bombView.explosionMatrix[3][1][(b.explosionAnimationCounter/5)%3],
									b.getPos_x()-(j+1)*GamePanel.FINAL_TILE_SIZE, b.getPos_y(), GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE,null);

						}

						else {
							g.drawImage(bombView.explosionMatrix[3][0][(b.explosionAnimationCounter/5)%3],
									b.getPos_x()-(j+1)*GamePanel.FINAL_TILE_SIZE, b.getPos_y(), GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE,null);

						}
					}
				}

			}
			b.processed_explosion = true;

		}

	/**
	 * Disegna le bombe esplose e inesplose.
	 */
	public void drawBombs(Graphics g) {
		Bomberman bomberman = Bomberman.getInstance();
		for (BombModel b : game_setup.getMap_entities().getPlaced_bombs().keySet()) {

			int b_tile_col = b.getPos_x()/GamePanel.FINAL_TILE_SIZE;
			int b_tile_row = b.getPos_y()/GamePanel.FINAL_TILE_SIZE;
			
			//Disegna l'esplosione di ogni bomba, disegnando prima tutta la parte superiore, poi a destra, giù e infine a sinistra. Il disegno dell'esplosione
			//si interrompe non appena si incontra un tile con collision attiva.
			if (b.hasExploded()) {
				this.drawBombExplosion(g, b, this.game_setup.getMap_entities().getPlaced_bombs());
			}
			else {
				BufferedImage bombSprite = game_setup.getBomb_view().bombAnimations[(b.animationCounter/2)%3];
				g.drawImage(bombSprite, b.getPos_x(), b.getPos_y(), GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE, null);
			}
			b.explosionAnimationCounter++;
		}
		for (BombModel b : game_setup.getMap_entities().getRemote_controlled_bomb().keySet()) {

			int b_tile_col = b.getPos_x()/GamePanel.FINAL_TILE_SIZE;
			int b_tile_row = b.getPos_y()/GamePanel.FINAL_TILE_SIZE;
			
			//Disegna l'esplosione di ogni bomba, disegnando prima tutta la parte superiore, poi a destra, giù e infine a sinistra. Il disegno dell'esplosione
			//si interrompe non appena si incontra un tile con collision attiva.
			if (b.hasExploded()) {
				this.drawBombExplosion(g, b, this.game_setup.getMap_entities().getRemote_controlled_bomb());
			}
			else {
				BufferedImage bombSprite = game_setup.getBomb_view().bombAnimations[(b.animationCounter/2)%3];
				g.drawImage(bombSprite, b.getPos_x(), b.getPos_y(), GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE, null);
			}
			b.explosionAnimationCounter++;
		}
	}

	/**
	 * Disegna i tiles dove sono presenti dei power ups.
	 */
	public void drawPowerUps(Graphics g) {
		for (PowerUpModel p : this.map_entities.getPower_ups()) {
			g.drawImage(this.game_setup.getPower_up_view().getPowerUpSprite(p.getId()),
					p.getCol()*GamePanel.FINAL_TILE_SIZE, p.getRow()*GamePanel.FINAL_TILE_SIZE, 
					GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE, null);
		}
	}
}
