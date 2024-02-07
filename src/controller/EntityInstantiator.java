package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Stream;
import model.Character;
import model.Direction;
import model.FatBoss;
import model.FreezeBoss;
import model.Laserer;
import model.Shooter;
import model.Trapper;
import model.Walker;
import view.FatBossView;
import view.FreezeBossView;
import view.ImmobileView;
import view.ShooterView;
import view.TrapperView;
import view.WalkerView;

public class EntityInstantiator {
	
	public ArrayList<Character> chars = new ArrayList<Character>(); 
	GameSetup game_setup;

	/**
	 * Costruttore che inizializza i nemici in gioco a partire da un file di configurazione.
	 * @param path stringa che identifica il path del file di configurazione.
	 */
	public EntityInstantiator(String path, GameSetup game_setup) {
		this.game_setup = game_setup;
		Stream<String> mapText;
		
		
		try {
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));
			mapText = br.lines();
			String[] values = mapText.toArray(String[]::new);
			for (String s : values) {
				charCreation(s);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Metodo che effettua il parsing del file di configurazione e crea i nemici seguendo i dati estrapolati.
	 */
	public void charCreation(String s) {
		String[] values = s.split(" ");
		switch(values[0]) {
		case "0":
			Character c = new Walker(Integer.parseInt(values[1]), Integer.parseInt(values[2]));
			game_setup.getCharacterModelsView().put(c, new WalkerView());
			break;
		case "1":
			Character c1 = new Shooter(Integer.parseInt(values[1]), Integer.parseInt(values[2]), game_setup.getMap_entities().getProjectiles());
			game_setup.getCharacterModelsView().put(c1, new ShooterView());
			break;

		case "2":
			Character c2 = new Trapper(Integer.parseInt(values[1]), Integer.parseInt(values[2]), game_setup.getMap_entities().getTraps(), game_setup);
			game_setup.getCharacterModelsView().put(c2, new TrapperView());
			break;
		case "3":
			Character c3; 
			switch(values[3]) {
			case "0":
				c3 = new Laserer(Integer.parseInt(values[1]), Integer.parseInt(values[2]), Direction.UP, game_setup.getMap_entities().getLaser_tiles());
				game_setup.getCharacterModelsView().put(c3, new ImmobileView());
				break;
			case "1":
				c3 = new Laserer(Integer.parseInt(values[1]), Integer.parseInt(values[2]), Direction.RIGHT,  game_setup.getMap_entities().getLaser_tiles());
				game_setup.getCharacterModelsView().put(c3, new ImmobileView());
				break;
				
			case "2":
				c3 = new Laserer(Integer.parseInt(values[1]), Integer.parseInt(values[2]), Direction.DOWN,  game_setup.getMap_entities().getLaser_tiles());
				game_setup.getCharacterModelsView().put(c3, new ImmobileView());
				break;
				
			case "3":
				c3 = new Laserer(Integer.parseInt(values[1]), Integer.parseInt(values[2]), Direction.LEFT,  game_setup.getMap_entities().getLaser_tiles());
				game_setup.getCharacterModelsView().put(c3, new ImmobileView());
				break;
				
			}
			break;
		case "4":
			Character c4 = new FatBoss(Integer.parseInt(values[1]), Integer.parseInt(values[2]));
			game_setup.getCharacterModelsView().put(c4, new FatBossView());
			break;
			
		case "5":
			Character c5 = new FreezeBoss(Integer.parseInt(values[1]), Integer.parseInt(values[2]), game_setup.getMap_entities().getBoss_projectiles());
			game_setup.getCharacterModelsView().put(c5, new FreezeBossView());
			break;
		default:
			game_setup.getCharacterModelsView().put( new Walker(Integer.parseInt(values[1]), Integer.parseInt(values[2]))  , new WalkerView());
			
		}
	}
	

}
