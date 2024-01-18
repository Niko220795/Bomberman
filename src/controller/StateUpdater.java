package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import model.Projectile;
import controller.Coordinates;
//import model.PowerUpModel;
import model.BombModel;
import model.TileModel;
import model.TrapModel;
import model.Character;
import view.GamePanel;
import model.LaserUtil;

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


	public void explodeBlocks() {
		Iterator<TileModel> iterator = this.tiles_to_update.iterator();
	    while (iterator.hasNext()) {
	        TileModel tile = iterator.next();
	        if (tile.destruction_counter == 0){
				tile.setModel_num(1);
				boolean has_power_up = tile.hasPowerUp();
//				if (has_power_up) {	
//					int i = this.random_gen.nextInt(7);
//					PowerUpModel power_up = new PowerUpModel(i, tile.getMatrix_pos_row(), tile.getMatrix_pos_col());
//					this.powerUpList.add(power_up);
//					tile.setPower_up(power_up);
//				}
				tile.setDisappearing(false);
				tile.setCollision(false);	
				iterator.remove();
			}
			else {
				tile.destruction_counter--;
			}
	    }
	}

	public void updateBombTimer() {
        // Aggiorna il timer di ogni bomba attiva
    	 //placeholder, deve essere quello di ogni singola bomba preso dal bombmodel
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

	
	public void manageCharacters() {
		for (Iterator<Character> iterator = this.game_setup.getCharacterModelsView().keySet().iterator(); iterator.hasNext();) {
			Character c = iterator.next();
			if (c.isDead()) {
				boolean can_disappear = c.decreaseDeathAnimationCounter();			
				/*
				 * se il death_animation_counter dura di più della fiamma il personaggio non sparisce, 
				 * bisogna creare can_disappear come campo della classe e fare questo controllo in un'altra funzione
				 * a parte, e non bombdamage
				 */
				if (can_disappear) {
					c.setReallyDead();
					iterator.remove();
				}
			}
			else {
				c.move(GamePanel.FINAL_TILE_SIZE, this.game_setup.getMap_structure(), this.game_setup.getControls());

			}
		}
//		for (Character c : this.game_setup.getMoveableCharacter()) {
//			c.move(GamePanel.FINAL_TILE_SIZE, this.game_setup.getMap_structure(), this.game_setup.getControls());
//		}
	}

}
