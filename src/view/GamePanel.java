package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import controller.ControlsHandler;
import controller.GameSetup;
import controller.Renderer;
import controller.StateUpdater;
import model.Bomberman;
import model.MapModel;
import model.TileModel;

public class GamePanel extends JPanel implements Runnable{

	public static final int X_TILES = 16;
	public static final int Y_TILES = 12;
	public static final int TILE_SIZE = 16;
	public static final int SCALING_CONST = 3;
	public static final int FINAL_TILE_SIZE = TILE_SIZE*SCALING_CONST;
	
	GameSetup game_setup;
	Renderer renderer;
	StateUpdater state_updater;
	
	public GamePanel() {
		System.out.println("lol");
		this.setPreferredSize(new Dimension((X_TILES*FINAL_TILE_SIZE),(Y_TILES*FINAL_TILE_SIZE)));
//		this.setBackground(new Color(107, 106, 104));
		this.setBackground(Color.blue);
		this.setDoubleBuffered(true);
//		this.setLayout(null);
//		this.fdg = new FinestraDiGioco();
		this.game_setup = new GameSetup("green_village");
		this.state_updater = new StateUpdater(this.game_setup, this.game_setup.getMap_entities());
		this.renderer = new Renderer(game_setup, state_updater, this.game_setup.getMap_entities());
		this.addKeyListener(this.game_setup.getControls());
		this.setFocusable(true);
//		Thread t = new Thread(this);
//		t.start();
		Thread game_loop = new Thread(this);
		game_loop.start();
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.game_setup.isGame_over()) {
			g.drawImage(this.game_setup.getMenu().game_over, 0, 0, 768, 576, null);
		}
		else {
			
			this.renderer.drawTile(g, this.game_setup.getMap_structure());
			this.renderer.drawCharacters(g);
//		g.drawImage(this.game_setup.getBomberman_view().getSprite(),
//				Bomberman.getInstance().getPos_x(), Bomberman.getInstance().getPos_y(),
//				this.game_setup.getBomberman_view().getSpriteWidth()*2, this.game_setup.getBomberman_view().getSpriteHeight()*2,	 null);
			this.renderer.drawBombs(g);
			this.renderer.drawLaser(g);
			this.renderer.drawProjectiles(g);
			this.renderer.drawTraps(g);
			this.renderer.drawBossProjectiles(g);
			this.renderer.drawPowerUps(g);
		}
	}
	
	
	
	@Override
	public void run() {
		while(true) {
			if (this.game_setup.getControls().isPause()) {
				System.out.println("pausing");
			}
			else if(this.game_setup.isGame_over()) {
				System.out.println("game over");
				repaint();

			}
			else {
				this.state_updater.game_over();
				this.state_updater.manageCharacters();
				Bomberman.getInstance().placeBomb(game_setup);
				this.state_updater.updateBombTimer();
				this.state_updater.manageTiles();
				this.state_updater.manageLasers();
				this.state_updater.manageProjectiles();
				this.state_updater.manageTraps();
				this.state_updater.manageBossProjectiles();
				this.state_updater.managePowerUps();
				Bomberman.getInstance().kickBombs(game_setup);
				this.state_updater.slideBombs();
				repaint();
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
//			Bomberman.getInstance().move(FINAL_TILE_SIZE, this.game_setup.getMap_structure(), this.game_setup.getControls());
	}
	
	public static int getPanelWidth() {
		return X_TILES*FINAL_TILE_SIZE;
	}
	
	public static int getPanelHeight() {
		return Y_TILES*FINAL_TILE_SIZE;
	}

}
