package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import controller.Coordinates;
//import model.PowerUpModel;
import model.BombModel;
import model.TileModel;
import view.GamePanel;

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

}
