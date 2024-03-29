package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Stream;

public class MapModel {
	
	private TileModel[][] mapStructure;
	
	/**File di configurazione per indicare, insieme alla mappa, quali blocchi devono avere la collision attiva. */
	private HashSet<Integer> terrain_config;
	private HashSet<Integer> destructible_config;
	private HashSet<Integer> border_config;
	
	
	public MapModel(String path, HashSet<Integer> terrain_config, HashSet<Integer> destructible_config, HashSet<Integer> border_config) {
		
		this.terrain_config = terrain_config;
		this.destructible_config = destructible_config;
		this.border_config = border_config;
		Stream<String> mapText;
		
		
		try {
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));
			mapText = br.lines();
			this.mapStructure = mapText.map(MapModel::tileMapping).toArray(TileModel[][]::new); //stream
			this.setTilesCoord();
			//fa un ulteriore passaggio sulla mapStructure per settare i blocchi con la collision in base al config
			configureTiles();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	
	
	/**
	 * Funzione per impostare la collisione sulla mapStructure. Scorre su tutti i singoli TileModel della mapStructure e
	 * se il loro modelNum corrisponde ad uno dei valori nel file di config, attiva la collision su quel Tile
	 */
	public void configureTiles() {
		for (int i = 0; i < mapStructure.length; i++) {
			for (int j = 0; j < mapStructure[0].length; j++) {
				

				if (terrain_config.contains(mapStructure[i][j].getModel_num())) {
					mapStructure[i][j].setCollision(false);
				}
				else {
					mapStructure[i][j].setCollision(true);

				}
				if (destructible_config.contains(mapStructure[i][j].getModel_num())) {
					mapStructure[i][j].setDestructible(true);
				}
				else {
					mapStructure[i][j].setDestructible(false);
				}
				if (border_config.contains(mapStructure[i][j].getModel_num())) {
					mapStructure[i][j].setBorder();
				}
			}
		}
	}
	
	public void setTilesCoord() {
		for (int row = 0; row < this.mapStructure.length; row++) {
			for (int col = 0; col < this.mapStructure[0].length; col++) {
				this.mapStructure[row][col].setRowCoord(row);
				this.mapStructure[row][col].setColCoord(col);
			}
		}
		
	}
	
	public static TileModel[] tileMapping(String s) {
		String[] ss = s.split(" ");
		TileModel[] c = new TileModel[ss.length];

		
		for (int i=0; i<ss.length; i++) {
			c[i] = new TileModel(Integer.parseInt(ss[i]));
			
		}
		
		return c;
	}

	public TileModel[][] getMapStructure() {
		return mapStructure;
	}
	
}
