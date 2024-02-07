package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import controller.AudioManager;
import controller.GameSetup;
import controller.Renderer;
import controller.StateUpdater;
import controller.listeners.SaveGameListener;
import model.Bomberman;


public class GamePanel extends JPanel implements Runnable{

	public static final int X_TILES = 16;
	public static final int Y_TILES = 12;
	public static final int TILE_SIZE = 16;
	public static final int SCALING_CONST = 3;
	public static final int FINAL_TILE_SIZE = TILE_SIZE*SCALING_CONST;
	
	GameSetup game_setup;
	Renderer renderer;
	StateUpdater state_updater;
	SaveButton save;


	/** Questo costruttore inizializza la finestra di gioco.
	 * @param game_setup contiene tutte le info necessarie per inizializzare la partita.
	 * Inoltre avvia il ciclo di gioco attraverso la variabile game_loop di tipo Thread.
	 * */
	public GamePanel(GameSetup game_setup) {
		this.setPreferredSize(new Dimension((X_TILES*FINAL_TILE_SIZE),(Y_TILES*FINAL_TILE_SIZE)));
		this.setBackground(Color.blue);
		this.setDoubleBuffered(true);
		this.game_setup = game_setup;
		this.initializeSaveButton();
		this.state_updater = new StateUpdater(this.game_setup, this.game_setup.getMap_entities());
		this.renderer = new Renderer(game_setup, state_updater, this.game_setup.getMap_entities());
		this.addKeyListener(this.game_setup.getControls());
		this.addMouseListener(this.game_setup.getRemote_bomb_listener());
		this.setFocusable(true);
		Thread game_loop = new Thread(this);
		game_loop.start();
		
	}

	/** Questo metodo è un override del paintComponent del JPanel, serve a disegnare tutti gli elementi necessari allo svolgimento del gioco.
	 * */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		var clip = AudioManager.getInstance().clips.get(8);
		if (this.game_setup.isGame_over()) {
			
			g.drawImage(this.game_setup.getMenu().game_over, 0, 0, 768, 576, null);				
			
		}
		else if (this.game_setup.isLevel_ended() && !clip.isRunning()) {
			

			g.drawImage(this.game_setup.getMenu().next_level, 0, 0, 768, 576, null);

			
		}
		else {
			
			this.renderer.drawTile(g, this.game_setup.getMap_structure());
			this.renderer.drawCharacters(g);
			this.renderer.drawBombs(g);
			this.renderer.drawLaser(g);
			this.renderer.drawProjectiles(g);
			this.renderer.drawTraps(g);
			this.renderer.drawBossProjectiles(g);
			this.renderer.drawPowerUps(g);
		}
	}


	/** Ciclo di gioco effettivo. Aggiorna tutti gli stati di gioco relativi a mappa ed entities.
	 *
	 */
	@Override
	public void run() {
		while(true) {
			if (this.game_setup.getControls().isPause()) {
				SwingUtilities.invokeLater(new Runnable() {
				    public void run() {
				    	if (save.getButton().isVisible() == false) {
				    		save.getButton().setVisible(true);
				    	}
				    }
				  });
			}
			else if(this.game_setup.isGame_over()) {

				if (this.game_setup.getControls().isEnter()) {
					this.game_setup.resetGame();
					}
				
				

			}
			else if(this.game_setup.isLevel_ended()) {
				int i = 0;
				i+=1;
				var clip = AudioManager.getInstance().clips.get(8);
				while(clip.isRunning()) {
					repaint();
				}
				if (this.game_setup.getControls().isEnter() && !clip.isRunning()) {
					this.game_setup.resetGame();
					game_setup.endgame_sound_played = false;
				}
				

			}
			else {
				SwingUtilities.invokeLater(new Runnable() {
				    public void run() {
				    	if (save.getButton().isVisible() == true) {
				    		save.getButton().setVisible(false);
				    	}
				    }
				  });
				this.state_updater.game_over();
				this.state_updater.next_level();
				this.state_updater.manageCharacters();
				Bomberman.getInstance().placeBomb(game_setup);
				Bomberman.getInstance().placeRemoteBomb(game_setup);
				this.state_updater.updateBombTimer();
				this.state_updater.manageRemoteBomb();
				this.state_updater.manageTiles();
				this.state_updater.manageLasers();
				this.state_updater.manageProjectiles();
				this.state_updater.manageTraps();
				this.state_updater.manageBossProjectiles();
				this.state_updater.managePowerUps();
				Bomberman.getInstance().kickBombs(game_setup);
				this.state_updater.slideBombs();
				this.state_updater.checkBonusScore();
				repaint();
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			}
	}

	/** Questo metodo inizializza ed aggiunge il pulsante di salvataggio inizialmente invisibile.
	 *
	 */
	public void initializeSaveButton() {
		this.save = new SaveButton(this.game_setup.getSelected_user());
		this.save.getButton().setFocusable(false);
		this.add(save.getButton());
		save.getButton().setVisible(false);
		save.getButton().addActionListener(new SaveGameListener(this.game_setup));
	}

	public static int getPanelWidth() {
		return X_TILES*FINAL_TILE_SIZE;
	}
	
	public static int getPanelHeight() {
		return Y_TILES*FINAL_TILE_SIZE;
	}

}
