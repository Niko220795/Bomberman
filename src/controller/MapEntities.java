package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import model.LaserUtil;
import model.Projectile;
import model.BombModel;
import model.TileModel;

public class MapEntities {
	private HashMap<BombModel, HashSet<TileModel>> placed_bombs;
	private HashMap<TileModel, LaserUtil> laser_tiles;
	private ArrayList<Projectile> projectiles;

	
	public HashMap<TileModel, LaserUtil> getLaser_tiles() {
		return laser_tiles;
	}
	public MapEntities() {
		this.placed_bombs = new HashMap<BombModel, HashSet<TileModel>>();
		this.laser_tiles = new HashMap<TileModel, LaserUtil>();
		this.projectiles = new ArrayList<Projectile>();
	}
	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}
	public HashMap<BombModel, HashSet<TileModel>> getPlaced_bombs() {
		return placed_bombs;
	}
	
	public void addBomb(BombModel bomb) {
		this.placed_bombs.put(bomb, new HashSet<TileModel>());
	}
}
