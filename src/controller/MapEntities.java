package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import model.LaserUtil;
import model.PowerUpModel;
import model.Projectile;
import model.BombModel;
import model.BossProjectile;
import model.TileModel;
import model.TrapModel;

public class MapEntities {
	private HashMap<BombModel, HashSet<TileModel>> placed_bombs;
	private HashMap<BombModel, HashSet<TileModel>> remote_controlled_bomb;

	private HashMap<TileModel, LaserUtil> laser_tiles;
	private ArrayList<Projectile> projectiles;
	private ArrayList<TrapModel> traps;
	private ArrayList<BossProjectile> boss_projectiles;
	private ArrayList<PowerUpModel> power_ups;

	
	public ArrayList<TrapModel> getTraps() {
		return traps;
	}
	public HashMap<TileModel, LaserUtil> getLaser_tiles() {
		return laser_tiles;
	}
	public MapEntities() {
		this.placed_bombs = new HashMap<BombModel, HashSet<TileModel>>();
		this.remote_controlled_bomb = new HashMap<BombModel, HashSet<TileModel>>();
		this.laser_tiles = new HashMap<TileModel, LaserUtil>();
		this.projectiles = new ArrayList<Projectile>();
		this.traps = new ArrayList<TrapModel>();
		this.boss_projectiles = new ArrayList<BossProjectile>();
		this.power_ups = new ArrayList<PowerUpModel>();
	}
	
	public ArrayList<PowerUpModel> getPower_ups() {
		return power_ups;
	}
	public ArrayList<BossProjectile> getBoss_projectiles() {
		return boss_projectiles;
	}
	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}
	public HashMap<BombModel, HashSet<TileModel>> getPlaced_bombs() {
		return placed_bombs;
	}
	public HashMap<BombModel, HashSet<TileModel>> getRemote_controlled_bomb() {
		return remote_controlled_bomb;
	}
	
	public void addBomb(BombModel bomb) {
		this.placed_bombs.put(bomb, new HashSet<TileModel>());
	}
	public void addRemoteBomb(BombModel bomb) {
		System.out.println("addRemoteBomb");
		this.remote_controlled_bomb.put(bomb, new HashSet<TileModel>());
	}
}
