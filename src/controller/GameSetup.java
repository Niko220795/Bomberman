package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import controller.AudioManager;
import controller.listeners.ControlsHandler;
import controller.listeners.RemoteBombListener;
import view.EntityView;
import model.Bomberman;
import model.Character;
import model.Direction;
import model.Laserer;
import model.MapModel;
import model.Shooter;
import model.TileModel;
import model.Trapper;
import model.User;
import model.Walker;
import view.BombView;
import view.BombermanView;
import view.CharacterView;
import view.EnemyView;
import view.FinestraDiGioco;
import view.FreezeBossView;
import view.GameWindow;
import view.TileView;
import view.TrapperView;
import view.WalkerView;
import view.ImmobileView;
import view.LaserView;
import view.Menu;
import view.PowerUpView;
import view.ScoreBoardView;
import view.ShooterView;
import view.CharacterView;
import view.FatBossView;
import model.FatBoss;
import model.FreezeBoss;
import view.FreezeBossView;

public class GameSetup {
	
	GameWindow game_window;
	User selected_user;
	ControlsHandler controls;
	RemoteBombListener remote_bomb_listener;
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
	HashMap<Integer, HashSet<Integer>> terrain_map = new HashMap<Integer, HashSet<Integer>>() ;
	HashMap<Integer, HashSet<Integer>> destructible_map = new HashMap<Integer, HashSet<Integer>>();
	HashMap<Integer, HashSet<Integer>> border_map = new HashMap<Integer, HashSet<Integer>>();
	HashMap<Integer, String> level_to_maps;
	ScoreBoardView scoreboard;
	public boolean endgame_sound_played = false;
//	AudioManager audio_samples = new AudioManager();

//	public AudioManager getAudio_samples() {
//		return audio_samples;
//	}

	public void setScoreboard(ScoreBoardView scoreboard) {
		this.scoreboard = scoreboard;
	}

	Menu menu = new Menu();
	HashMap<String, Coordinates> exit_tiles;
	boolean level_ended = false;
	boolean game_over = false;
	public static Random random_generator = new Random();
	int level;
	

	
	public GameSetup(User selected_user) {
		this.initializeLevelToMaps();
		this.initializeExitTiles();
		this.selected_user = selected_user;
		this.laser_view = new LaserView();
		this.bomb_view = new BombView();
		this.power_up_view = new PowerUpView();
		this.controls = new ControlsHandler();
		this.remote_bomb_listener = new RemoteBombListener();
//		this.tile_view = new TileView(map_name, 24, ".png");
//		bomberman_view = new BombermanView();
//		bomberman.addObserver(bomberman_view);
		this.map_entities = new MapEntities();
		level = selected_user.level;
		this.resetGame();
//		MapModel map = new MapModel("src/resources/map.txt", arr,arr2,arr2);
//		this.map_structure = map.getMapStructure();
		
	}
	
	public RemoteBombListener getRemote_bomb_listener() {
		return remote_bomb_listener;
	}

	public void nextLevel() {
		
		this.selected_user.level++;
		this.level++;
		
	}
	
	
	
	public void initializeLevelToMaps() {
		this.level_to_maps = new HashMap<Integer, String>();
		level_to_maps.put(1, "green_village");
		level_to_maps.put(2, "blue_castle");
	}
	public void initializeExitTiles() {
		this.exit_tiles = new HashMap<String, Coordinates>();
		this.exit_tiles.put("green_village", new Coordinates(2,1));
		this.exit_tiles.put("blue_castle", new Coordinates(4,3));

	}
	
	public Coordinates getExit_tiles() {
		return exit_tiles.get(this.level_to_maps.get(this.level));
	}

	public void resetGame() {
		System.out.println("reset game");
		if (this.level_ended == true) {
			System.out.println("old level = "+ this.level);
			this.nextLevel();
			this.level_ended = false;
			System.out.println("new level = "+ this.level);

		}
		String map_config = "src/resources/map" + this.level +".txt";
		String enemies = "src/resources/enemies" + this.level +".txt";

		this.initializeGame(enemies, map_config, level);
		Bomberman.getInstance().reset();
		this.game_over = false;
		this.map_entities.getPlaced_bombs().clear();
		this.map_entities.getPower_ups().clear();
		Bomberman.getInstance().setPos_x(96);
		Bomberman.getInstance().setPos_y(96);
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
	
	public void initializeGame(String enemies, String map_config, int level) {
		this.initializeCharacterView(enemies);
		this.initializeMap(level);
		this.initializeTileMaps(level);
		Bomberman.getInstance().reset();
		MapModel map = new MapModel(map_config, this.terrain_map.get(level),this.destructible_map.get(level),this.border_map.get(level));
		this.map_structure = map.getMapStructure();
		
	}

	public void initializeMap(int map_id) {
		switch(map_id) {
		case 1:
			this.tile_view = new TileView("green_village", 24, ".png");
			break;
		case 2:
			this.tile_view = new TileView("blue_castle", 17, ".jpg");
			break;
		default:

		}
	}
	
	public void initializeTileMaps(int map_id) {
		switch(map_id) {
		case 1:
			this.terrain_map.put(1, new HashSet<Integer>(Arrays.asList(1)));
			this.destructible_map.put(1, new HashSet<Integer>(Arrays.asList(20)));
			this.border_map.put(1, new HashSet<Integer>(Arrays.asList(10)));
			break;
		case 2:
			this.terrain_map.put(2, new HashSet<Integer>(Arrays.asList(1,8)));
			this.destructible_map.put(2, new HashSet<Integer>(Arrays.asList(14)));
			this.border_map.put(2, new HashSet<Integer>(Arrays.asList(10)));
			break;
		}
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

	public boolean isLevel_ended() {
		return level_ended;
	}

	public void setLevel_ended(boolean level_ended) {
		this.level_ended = level_ended;
	}

	public User getSelected_user() {
		return selected_user;
	}

	public boolean isGame_over() {
		return game_over;
	}

	public void setGame_over(boolean game_over) {
		this.game_over = game_over;
	}

	public Menu getMenu() {
		return menu;
	}

}
