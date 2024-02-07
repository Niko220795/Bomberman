package model;

import java.util.ArrayList;
import controller.GameSetup;
import controller.listeners.ControlsHandler;


public class Trapper extends Walker{

	private ArrayList<TrapModel> traps;
	private int trap_timer = 1000;
	private GameSetup game_setup;
	
	public Trapper(int x, int y, ArrayList<TrapModel> traps, GameSetup game_setup) {
		super(x,y);
		this.traps = traps;
		this.game_setup = game_setup;
	}

	@Override
	public void move(int tile_size, TileModel[][] map_structure, ControlsHandler controls) {
		super.move(tile_size, map_structure, controls);
		placeTrap(tile_size,map_structure, controls, traps );
		
	}
	
	public ArrayList<TrapModel> getTraps() {
		return traps;
	}

	/**
	 * Funzione che permette al nemico di piazzare una trappola sul terreno.
	 */
	private void placeTrap(int tile_size, TileModel[][] map_structure, ControlsHandler controls, ArrayList<TrapModel> traps) {
		if (this.trap_timer <= 0) {
			int b_center_x = getPos_x() + tile_size/2;
			int b_center_y = getPos_y() + tile_size/2;
			int trap_aligned_x = b_center_x - b_center_x%tile_size;
			int trap_aligned_y = b_center_y - b_center_y%tile_size;
			int trap_tile_col = trap_aligned_x/tile_size;
			int trap_tile_row = trap_aligned_y/tile_size;
			System.out.println(trap_tile_col + " " + trap_tile_row);
			TrapModel trap = new TrapModel(trap_tile_row, trap_tile_col);
			traps.add(trap);
			this.game_setup.getMap_structure()[trap_tile_row][trap_tile_col].setTrap(trap);
			trap_timer = 1000;
		}
		else {
			trap_timer-=10;
		}
	}


}
