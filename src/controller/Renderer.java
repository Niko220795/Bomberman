package controller;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import controller.Coordinates;
import model.BombModel;
import model.Bomberman;
import model.Character;
import model.TileModel;
import view.BombView;
//import model.PowerUpModel;
import view.GamePanel;

public class Renderer {
	GameSetup game_setup;
	StateUpdater state_updater;
	MapEntities map_entities;
	public Renderer(GameSetup game_setup, StateUpdater state_updater, MapEntities map_entities) {
		this.game_setup = game_setup;
		this.state_updater = state_updater;
		this.map_entities = map_entities;
	}
	
	
	public void drawBombExplosion(Graphics g, BombModel b) {
		TileModel[][] map_structure = this.game_setup.getMap_structure();
		ArrayList<TileModel> tiles_to_update = this.state_updater.getTiles_to_update();
		BombView bombView = this.game_setup.getBomb_view();
		HashMap<BombModel, HashSet<TileModel>> placed_bombs = this.map_entities.getPlaced_bombs();
		int b_tile_col = b.getPos_x()/GamePanel.FINAL_TILE_SIZE;
		int b_tile_row = b.getPos_y()/GamePanel.FINAL_TILE_SIZE;
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
	
	public void drawBombs(Graphics g) {
		Bomberman bomberman = Bomberman.getInstance();
		for (BombModel b : game_setup.getMap_entities().getPlaced_bombs().keySet()) {
			
			
			
			int b_tile_col = b.getPos_x()/GamePanel.FINAL_TILE_SIZE;
			int b_tile_row = b.getPos_y()/GamePanel.FINAL_TILE_SIZE;
			
			
			
			//Disegna l'esplosione di ogni bomba, disegnando prima tutta la parte superiore, poi a destra, giù e infine a sinistra. Il disegno dell'esplosione
			//si interrompe non appena si incontra un tile con collision attiva.
			if (b.hasExploded()) {
				this.drawBombExplosion(g, b);
			}

//				for (Character c : this.moveableCharacters) {
//					var flames = placedBombs.get(b);
//					int c_tile_col = c.getPos_x()/FINAL_TILE_SIZE;
//					int c_tile_row = c.getPos_y()/FINAL_TILE_SIZE;
//					int HitBoxUpperLeft_x = (c.getPos_x()+10) / FINAL_TILE_SIZE;
//					int HitBoxUpperLeft_y = (c.getPos_y()+10) / FINAL_TILE_SIZE;
//					int HitBoxUpperRight_x = (c.getPos_x() + GamePanel.FINAL_TILE_SIZE-10)/ FINAL_TILE_SIZE;
//					int HitBoxUpperRight_y = (c.getPos_y()+10)/ FINAL_TILE_SIZE;
//					int HitBoxBottomLeft_x = (c.getPos_x()+10)/ FINAL_TILE_SIZE;
//					int HitBoxBottomLeft_y = (c.getPos_y() + GamePanel.FINAL_TILE_SIZE-10)/ FINAL_TILE_SIZE;
//					int HitBoxBottomRight_x = (c.getPos_x() + GamePanel.FINAL_TILE_SIZE-10)/ FINAL_TILE_SIZE;
//					int HitBoxBottomRight_y = (c.getPos_y() + GamePanel.FINAL_TILE_SIZE-10)/ FINAL_TILE_SIZE;
//					int row_pos = c.getPos_y()/FINAL_TILE_SIZE;
//					int col_pos = c.getPos_x()/FINAL_TILE_SIZE;
//					if (	flames.contains(this.map_structure[HitBoxUpperLeft_y][HitBoxUpperLeft_x]) || 
//							flames.contains(this.map_structure[HitBoxUpperRight_y][HitBoxUpperRight_x]) ||
//							flames.contains(this.map_structure[HitBoxBottomLeft_y][HitBoxBottomLeft_x]) ||
//							flames.contains(this.map_structure[HitBoxBottomRight_y][HitBoxBottomRight_x])){
//						if (!b.hasDamaged(c)) {
//							c.damage();
//							b.damaged(c);
//							
//						}
//						
////					TileModel characterTile = this.map_structure[c_tile_row][c_tile_col];
////					if(placedBombs.get(b).contains(characterTile) && !b.processed_explosion) {
////						c.damage();
////						System.out.println("damaged");
////					}
//					}
//				}
//				b.processed_explosion = true;
//				
//			}
			else {
				BufferedImage bombSprite = game_setup.getBomb_view().bombAnimations[(b.animationCounter/2)%3];
				g.drawImage(bombSprite, b.getPos_x(), b.getPos_y(), GamePanel.FINAL_TILE_SIZE, GamePanel.FINAL_TILE_SIZE, null);

			}

			b.explosionAnimationCounter++;
		}
	}
}
