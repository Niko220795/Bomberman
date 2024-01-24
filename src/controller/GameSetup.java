package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import view.EntityView;
import model.Bomberman;
import model.Character;
import model.Direction;
import model.Laserer;
import model.MapModel;
import model.Shooter;
import model.TileModel;
import model.Trapper;
import model.Walker;
import view.BombView;
import view.BombermanView;
import view.CharacterView;
import view.EnemyView;
import view.FinestraDiGioco;
import view.FreezeBossView;
import view.TileView;
import view.TrapperView;
import view.WalkerView;
import view.ImmobileView;
import view.LaserView;
import view.Menu;
import view.PowerUpView;
import view.ShooterView;
import view.CharacterView;
import view.FatBossView;
import model.FatBoss;
import model.FreezeBoss;
import view.FreezeBossView;

public class GameSetup {

	ControlsHandler controls;
	FinestraDiGioco fdg;
	TileModel[][] map_structure;
	BombermanView bomberman_view;
	LaserView laser_view;
	Bomberman bomberman = Bomberman.getInstance();
	TileView tile_view;
	MapEntities map_entities;
	BombView bomb_view;
	PowerUpView power_up_view;
	HashMap<Character, EntityView> characterModelsView ;
	EntityInstantiator entity_instantiator;
	boolean game_over = false;
	
	public boolean isGame_over() {
		return game_over;
	}

	public void setGame_over(boolean game_over) {
		this.game_over = game_over;
	}

	Menu menu = new Menu();
	public Menu getMenu() {
		return menu;
	}

	public static Random random_generator = new Random();
	
	
	public GameSetup(String map_name) {
		this.laser_view = new LaserView();
		this.bomb_view = new BombView();
		this.power_up_view = new PowerUpView();
		this.controls = new ControlsHandler();
		this.tile_view = new TileView(map_name, 24, ".png");
//		bomberman_view = new BombermanView();
//		bomberman.addObserver(bomberman_view);
		this.map_entities = new MapEntities();
		this.initializeCharacterView("src/resources/enemies.txt");
		int[] arr = new int[] {1};
		int[] arr2 = new int[] {};

		MapModel map = new MapModel("src/resources/map.txt", arr,arr2,arr2);
		this.map_structure = map.getMapStructure();
		
	}

	public PowerUpView getPower_up_view() {
		return power_up_view;
	}

	public LaserView getLaser_view() {
		return laser_view;
	}

	public HashMap<Character, EntityView> getCharacterModelsView() {
		return characterModelsView;
	}

	public void initializeCharacterView(String path) {
		this.characterModelsView = new HashMap<Character,EntityView>();
		this.characterModelsView.put(Bomberman.getInstance(), new BombermanView());
		this.entity_instantiator = new EntityInstantiator(path,this);
//		this.characterModelsView.put(new Walker(144,252), new WalkerView());
//		this.characterModelsView.put(new Shooter(144,252, this.map_entities.getProjectiles()), new ShooterView());
//		this.characterModelsView.put(new Trapper(144,252, this.map_entities.getTraps(), this), new TrapperView());
//		this.characterModelsView.put(new Laserer(144,252, Direction.LEFT, this.map_entities.getLaser_tiles()), new ImmobileView());

//		this.characterModelsView.put(new FatBoss(300,250), new FatBossView());
//		this.characterModelsView.put(new FreezeBoss(300,300,this.map_entities.getBoss_projectiles()), new FreezeBossView());
		for (Character c : this.characterModelsView.keySet()) {
			c.addObserver(this.characterModelsView.get(c));
		}
	}
	
	public BombView getBomb_view() {
		return bomb_view;
	}

	public MapEntities getMap_entities() {
		return map_entities;
	}

	public ControlsHandler getControls() {
		return controls;
	}

	public FinestraDiGioco getFdg() {
		return fdg;
	}

	public TileModel[][] getMap_structure() {
		return map_structure;
	}

	public BombermanView getBomberman_view() {
		return bomberman_view;
	}

	public Bomberman getBomberman() {
		return bomberman;
	}

	public TileView getTile_view() {
		return tile_view;
	}
}
