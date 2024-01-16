package controller;

import java.util.ArrayList;
import java.util.HashMap;

import view.EntityView;
import model.Bomberman;
import model.Character;
import model.Direction;
import model.Laserer;
import model.MapModel;
import model.Shooter;
import model.TileModel;
import model.Walker;
import view.BombView;
import view.BombermanView;
import view.CharacterView;
import view.EnemyView;
import view.FinestraDiGioco;
import view.TileView;
import view.WalkerView;
import view.ImmobileView;
import view.LaserView;
import view.ShooterView;
import view.CharacterView;

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
	HashMap<Character, CharacterView> characterModelsView ;

	
	
	public GameSetup(String map_name) {
		this.laser_view = new LaserView();
		this.bomb_view = new BombView();
		this.controls = new ControlsHandler();
		this.tile_view = new TileView(map_name, 24, ".png");
		bomberman_view = new BombermanView();
		bomberman.addObserver(bomberman_view);
		this.map_entities = new MapEntities();
		this.initializeCharacterView();
		int[] arr = new int[] {1};
		MapModel map = new MapModel("src/resources/map.txt", arr,arr,arr);
		this.map_structure = map.getMapStructure();
		
	}

	public LaserView getLaser_view() {
		return laser_view;
	}

	public HashMap<Character, CharacterView> getCharacterModelsView() {
		return characterModelsView;
	}

	public void initializeCharacterView() {
		this.characterModelsView = new HashMap<Character,CharacterView>();
		this.characterModelsView.put(Bomberman.getInstance(), new BombermanView());
//		this.characterModelsView.put(new Walker(144,252), new WalkerView());
		this.characterModelsView.put(new Shooter(144,252, this.map_entities.getProjectiles()), new ShooterView());

//		this.characterModelsView.put(new Laserer(144,252, Direction.LEFT, this.map_entities.getLaser_tiles()), new ImmobileView());

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
