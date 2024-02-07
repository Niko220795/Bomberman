package controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import controller.listeners.ControlsHandler;
import controller.listeners.RemoteBombListener;
import view.EntityView;
import model.Bomberman;
import model.Character;
import model.MapModel;
import model.TileModel;
import model.User;
import view.BombView;
import view.BombermanView;
import view.GameWindow;
import view.TileView;
import view.LaserView;
import view.Menu;
import view.PowerUpView;
import view.ScoreBoardView;

public class GameSetup {
	
	GameWindow game_window;
	User selected_user;
	ControlsHandler controls;
	RemoteBombListener remote_bomb_listener;
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

	public void setScoreboard(ScoreBoardView scoreboard) {
		this.scoreboard = scoreboard;
	}

	Menu menu = new Menu();
	HashMap<String, Coordinates> exit_tiles;
	boolean level_ended = false;
	boolean game_over = false;
	public static Random random_generator = new Random();
	int level;


	/**
	 * Costruttore che inizializza tutto il necessario all'inizializzazione del gioco.
	 */
	public GameSetup(User selected_user) {
		this.initializeLevelToMaps();
		this.initializeExitTiles();
		this.selected_user = selected_user;
		this.laser_view = new LaserView();
		this.bomb_view = new BombView();
		this.power_up_view = new PowerUpView();
		this.controls = new ControlsHandler();
		this.remote_bomb_listener = new RemoteBombListener();
		this.map_entities = new MapEntities();
		level = selected_user.level;
		this.resetGame();

		
	}
	
	public RemoteBombListener getRemote_bomb_listener() {
		return remote_bomb_listener;
	}


	/**
	 * Aggiorna il livello dell'user.
	 */
	public void nextLevel() {
		this.selected_user.level++;
		this.level++;
	}


	/**
	 * Questo metodo utilitario serve a ricavare i nomi delle mappe a partire dal numero del livello.
	 */
	public void initializeLevelToMaps() {
		this.level_to_maps = new HashMap<Integer, String>();
		level_to_maps.put(1, "green_village");
		level_to_maps.put(2, "blue_castle");
		level_to_maps.put(3, "castle");
		level_to_maps.put(4, "red_castle");
		level_to_maps.put(5, "yellow_castle");

	}

	/**
	 * Questo metodo utilitario serve a ricavare le coordinate dei tiles di uscita a partire dal livello.
	 */
	public void initializeExitTiles() {
		this.exit_tiles = new HashMap<String, Coordinates>();
		this.exit_tiles.put("green_village", new Coordinates(2,1));
		this.exit_tiles.put("blue_castle", new Coordinates(4,3));
		this.exit_tiles.put("castle", new Coordinates(100,100));
		this.exit_tiles.put("red_castle", new Coordinates(3,2));
		this.exit_tiles.put("yellow_castle", new Coordinates(100,100));




	}
	
	public Coordinates getExit_tiles() {
		return exit_tiles.get(this.level_to_maps.get(this.level));
	}

	/**
	 * Questo metodo serve a resettare il gioco in caso di vittoria o di morte.
	 */
	public void resetGame() {
		if (this.level_ended == true) {
			this.nextLevel();
			this.level_ended = false;
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

	/**
	 * Questo metodo inizializza il gioco a partire dai file di configurazione e dal livello.
	 * @param enemies configurazione dei nemici
	 * @param map_config configurazione della mappa
	 * @param level livello attuale.
	 */
	public void initializeGame(String enemies, String map_config, int level) {
		this.initializeCharacterView(enemies);
		this.initializeMap(level);
		this.initializeTileMaps(level);
		Bomberman.getInstance().reset();
		MapModel map = new MapModel(map_config, this.terrain_map.get(level),this.destructible_map.get(level),this.border_map.get(level));
		this.map_structure = map.getMapStructure();
		
	}

	/**
	 * Inizializza la grafica della mappa in base al livello.
	 * @param map_id id della mappa appartenente ad un determinato livello.
	 */
	public void initializeMap(int map_id) {
		switch(map_id) {
		case 1:
			this.tile_view = new TileView("green_village", 24, ".png");
			break;
		case 2:
			this.tile_view = new TileView("blue_castle", 17, ".jpg");
			break;
		case 3:
			this.tile_view = new TileView("castle", 28, ".jpg" );
			break;
		case 4:
			this.tile_view = new TileView("red_castle", 23, ".jpg");
			break;
		case 5:
			this.tile_view = new TileView("yellow_castle", 18, ".jpg" );
		default:

		}
	}

	/**
	 * Inizializza le configurazioni per i singoli tiles, distruttibili o trapassabili.
	 * @param map_id id della mappa appartenente ad un determinato livello.
	 */
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
		case 3:
			this.terrain_map.put(3, new HashSet<Integer>(Arrays.asList(8)));
			this.destructible_map.put(3, new HashSet<Integer>(Arrays.asList(25)));
			this.border_map.put(3, new HashSet<Integer>(Arrays.asList(10)));
			break;
		case 4:
			this.terrain_map.put(4, new HashSet<Integer>(Arrays.asList(14)));
			this.destructible_map.put(4, new HashSet<Integer>(Arrays.asList(22)));
			this.border_map.put(4, new HashSet<Integer>(Arrays.asList(10)));
			break;
		case 5:
			this.terrain_map.put(5, new HashSet<Integer>(Arrays.asList(10,6)));
			this.destructible_map.put(5, new HashSet<Integer>(Arrays.asList(17)));
			this.border_map.put(5, new HashSet<Integer>(Arrays.asList(10)));
			break;
		}
	}

	/**
	 * Inizializza i nemici e Bomberman a partire dal file di configurazione associandogli la view opportuna.
	 */
	public void initializeCharacterView(String path) {
		this.characterModelsView = new HashMap<Character,EntityView>();
		this.characterModelsView.put(Bomberman.getInstance(), new BombermanView());
		this.entity_instantiator = new EntityInstantiator(path,this);
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
