package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.swing.SwingUtilities;
import model.BossProjectile;
import model.Projectile;
import model.BombModel;
import model.Bomberman;
import model.TileModel;
import model.TrapModel;
import model.Character;
import model.FatBoss;
import model.FreezeBoss;
import view.GamePanel;
import model.LaserUtil;
import model.PowerUpModel;

public class StateUpdater {
	
	private GameSetup game_setup;
	private ArrayList<TileModel> tiles_to_update;
	private MapEntities map_entities;


	public StateUpdater(GameSetup game_setup, MapEntities map_entities) {
		this.game_setup = game_setup;
		this.tiles_to_update = new ArrayList<TileModel>();
		this.map_entities = map_entities;
	}

	public ArrayList<TileModel> getTiles_to_update() {
		return tiles_to_update;
	}

	/**
	 * Gestisce la posizione dei proiettili sparati dal freeze Boss.
	 */
	public void manageBossProjectiles() {
		var boss_projectiles = this.map_entities.getBoss_projectiles();
		for (Iterator<BossProjectile> iterator = boss_projectiles.iterator(); iterator.hasNext();) {
			BossProjectile p = iterator.next();
			if (p.isDisappearing()) {
				iterator.remove();
			}
			else {
				p.move(GamePanel.getPanelWidth(), GamePanel.getPanelHeight(), GamePanel.FINAL_TILE_SIZE);
			}
		}
	}

	/**
	 * Gestisce la posizione dei proiettoli sparati dal nemico Shooting.
	 */
	public void manageProjectiles() {
		var projectiles = this.map_entities.getProjectiles();
		for (Iterator<Projectile> iterator = projectiles.iterator(); iterator.hasNext();) {
			Projectile p = iterator.next();
			if (p.is_expired()) {
				iterator.remove();
			}
			else {
				p.move(GamePanel.FINAL_TILE_SIZE, this.game_setup.getMap_structure(), this.game_setup.getControls());;
			}
		}
	}

	/**
	 * Gestisce la posizione delle trappole piazzate dal nemico Trapper skrrt skrrt.
	 */
	public void manageTraps() {
		var traps = this.map_entities.getTraps();
		for (Iterator<TrapModel> iterator = traps.iterator(); iterator.hasNext();) {
			TrapModel trap = iterator.next();
			if (trap.has_expired()) {
				this.game_setup.map_structure[trap.getRow()][trap.getCol()].resetTrap();
				iterator.remove();
			}
			else if(trap.isSteppedOn()) {
				this.game_setup.map_structure[trap.getRow()][trap.getCol()].resetTrap();
				iterator.remove();
			}
			else {
				trap.decreaseDuration();
			}
		}
	}

	/**
	 * Gestisce la posizione del laser sparato dal nemico Laserer.
	 */
	public void manageLasers() {
		var laser_tiles = this.map_entities.getLaser_tiles();
		for (Iterator<Map.Entry<TileModel, LaserUtil>> iterator = laser_tiles.entrySet().iterator(); iterator.hasNext();) {
	            TileModel t = iterator.next().getKey();
	            if ( laser_tiles.get(t).i <= 0) {  
	            	iterator.remove();
	            }
	           	else {
	            	laser_tiles.put(t, new LaserUtil(laser_tiles.get(t).i - 1,laser_tiles.get(t).dir ));
	           	} 
	    	}
	}

	/**
	 * Metodo che serve per il funzionamento del power-up che permette di calciare le bombe.
	 */
	public void slideBombs() {
		for (BombModel b : this.map_entities.getPlaced_bombs().keySet()) {
			b.slide(b.getSlide_dir(), this.game_setup.getMap_structure());
		}
	}

	/**
	 * Gestisce i tiles che vengono distrutti, facendo apparire, se necessario, power-ups e tiles di uscita.
	 */
	public void manageTiles() {
		Iterator<TileModel> iterator = this.tiles_to_update.iterator();
	    while (iterator.hasNext()) {
	        TileModel tile = iterator.next();
	        if (tile.destruction_counter == 0){
	        	boolean has_power_up = tile.containsPowerUp();
	        	int tile_row = tile.getMatrix_pos_row();
	        	int tile_col = tile.getMatrix_pos_col();
	        	Coordinates exit_tile = this.game_setup.getExit_tiles();
	        	if (tile_row == exit_tile.j && tile_col == exit_tile.i) {
	        		has_power_up = false;
	        		tile.setExit(true);
	        	}
	        	if (this.game_setup.level_to_maps.get(this.game_setup.level) == "castle") {
	        		tile.setModel_num(8);
	        	}
	        	else if (this.game_setup.level_to_maps.get(this.game_setup.level) == "red_castle") {
	        		tile.setModel_num(14);
	        	}
	        	else if (this.game_setup.level_to_maps.get(this.game_setup.level) == "yellow_castle") {
	        		tile.setModel_num(10);
	        	}
	        	else {
	        		tile.setModel_num(1);	        		
	        	}
				this.game_setup.selected_user.score += 200;
				this.game_setup.scoreboard.update();
				if (has_power_up) {
					int i = this.game_setup.random_generator.nextInt(1,9);
					PowerUpModel power_up = new PowerUpModel(i, tile.getMatrix_pos_row(), tile.getMatrix_pos_col());
					this.map_entities.getPower_ups().add(power_up);
					tile.setPower_up(power_up);
				}
				tile.setDisappearing(false);
				tile.setCollision(false);	
				iterator.remove();					
			}
			else {
				tile.destruction_counter--;
			}
	    }
	}

	/**
	 * Gestisce la sparizione dei power-ups dopo che sono stati raccolti.
	 */
	public void managePowerUps() {
		Iterator<PowerUpModel> iterator = this.map_entities.getPower_ups().iterator();
		while(iterator.hasNext()) {
			PowerUpModel power_up = iterator.next();
			if (power_up.isPicked_up()) {
				iterator.remove();
			}
		}
	}


	public void checkBonusScore() {
		if (Bomberman.getInstance().hasBonusScore()) {
			SwingUtilities.invokeLater(new Runnable() {
			    public void run() {
			    	game_setup.selected_user.score+=3000;
			    	game_setup.scoreboard.update();
			    }
			  });
			Bomberman.getInstance().resetBonusScore();
		}
	}

	/**
	 * Aggiorna il timer di esplosione di ogni bomba attiva
	 */
	public void updateBombTimer() {
        for (Iterator<BombModel> iterator = game_setup.getMap_entities().getPlaced_bombs().keySet().iterator(); iterator.hasNext();) {
            BombModel bomba = iterator.next();
            bomba.fireFuse(GamePanel.FINAL_TILE_SIZE);
            if (bomba.hasExpired()) {  
            	for (TileModel t : this.map_entities.getPlaced_bombs().get(bomba)) {
            		t.setExploding(false);
            	}
                iterator.remove();
            }
        }
    }

	/**
	 * Gestisce la detonazione controllata della bomba (power-up).
	 */
	public void manageRemoteBomb() {
        for (Iterator<BombModel> iterator = game_setup.getMap_entities().getRemote_controlled_bomb().keySet().iterator(); iterator.hasNext();) {
        	BombModel bomba = iterator.next();
        	if (!bomba.hasExploded() && this.game_setup.getRemote_bomb_listener().isExploded()) {
        		bomba.explode();
        	}
        	else if (bomba.hasExploded()) {
        		bomba.decreaseRemoteExplosionDuration();
        	}
        	if (bomba.hasExpired()) {
        		for (TileModel t : this.map_entities.getRemote_controlled_bomb().get(bomba)) {
        			t.setExploding(false);
        		}
        		iterator.remove();
        	}	
        }
	}

	/**
	 * Segnala che il livello è finito e riproduce l'audio.
	 */
	public void next_level() {
		if (Bomberman.getInstance().checkExit(this.game_setup.getMap_structure())) {
			this.game_setup.setLevel_ended(true);
			if (!game_setup.endgame_sound_played) {
				AudioManager.getInstance().play(8);
			}
		}
	}

	/**
	 * Segnala che il livello è finito a causa della morte di Bomberman.
	 */
	public void game_over() {
		if (Bomberman.getInstance().isReallyDead()) {
			this.game_setup.setGame_over(true);
		}
	}


	/**
	 * Gestisce tutti i characters attualmente in game.
	 */
	public void manageCharacters() {
		for (Iterator<Character> iterator = this.game_setup.getCharacterModelsView().keySet().iterator(); iterator.hasNext();) {
			Character c = iterator.next();
			if (c.isDead()) {
				
				boolean can_disappear = c.decreaseDeathAnimationCounter();			

				if (can_disappear) {
					c.setReallyDead();
					if (c instanceof Bomberman) {
						SwingUtilities.invokeLater(new Runnable() {
						    public void run() {
						    	if (game_setup.selected_user.score >= 2000) {
						    		game_setup.selected_user.score-=2000;						    		
						    	}
						    	else {
						    		game_setup.selected_user.score = 0;
						    	}
						    	game_setup.scoreboard.update();
						    }
						  });
					}
					else if (c instanceof FatBoss || c instanceof FreezeBoss) {
						this.game_setup.setLevel_ended(true);
						if (!game_setup.endgame_sound_played) {
							AudioManager.getInstance().play(8);
						}
					}
					else {
						SwingUtilities.invokeLater(new Runnable() {
						    public void run() {
						    	game_setup.selected_user.score+=500;
						    	game_setup.scoreboard.update();
						    }
						  });
					}
					
					iterator.remove();
				}
			}
			else {
				c.move(GamePanel.FINAL_TILE_SIZE, this.game_setup.getMap_structure(), this.game_setup.getControls());

			}
		}

	}

}
