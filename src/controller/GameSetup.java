package controller;

import model.Bomberman;
import model.MapModel;
import model.TileModel;
import view.BombView;
import view.BombermanView;
import view.FinestraDiGioco;
import view.TileView;

public class GameSetup {

	ControlsHandler controls;
	FinestraDiGioco fdg;
	TileModel[][] map_structure;
	BombermanView bomberman_view;
	Bomberman bomberman = Bomberman.getInstance();
	TileView map;
	MapEntities map_entities;
	BombView bomb_view;
	
	public GameSetup(String map_name) {
		this.bomb_view = new BombView();
		this.controls = new ControlsHandler();
		this.map = new TileView(map_name, 24, ".png");
		bomberman_view = new BombermanView();
		bomberman.addObserver(bomberman_view);
		this.map_entities = new MapEntities();
		int[] arr = new int[] {1};
		MapModel map = new MapModel("src/resources/map.txt", arr,arr,arr);
		this.map_structure = map.getMapStructure();
		
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

	public TileView getMap() {
		return map;
	}
}
