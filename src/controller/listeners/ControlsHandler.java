package controller.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Bomberman;
import view.BombermanView;

public class ControlsHandler implements KeyListener{
	Bomberman b = Bomberman.getInstance();
	BombermanView s;
	
//	private static final int WINDOW_WIDTH = GamePanel.getPanelWidth();
//	private static final int WINDOW_HEIGHT = GamePanel.getPanelHeight();
	
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean space;
	private boolean kicks_bomb;
	private volatile boolean enter;
	private boolean pausing = false;
	

	public ControlsHandler() {

	}
	
	public boolean isUp() {
		return up;
	}

	public boolean isDown() {
		return down;
	}

	public boolean isEnter() {
		return enter;
	}
	
	public boolean isLeft() {
		return left;
	}


	public boolean isRight() {
		return right;
	}

	public boolean isSpace() {
		return space;
	}
	
	public boolean isPause() {
		return pausing;
	}
	
	public boolean canKickBomb() {
		return this.kicks_bomb;
	}



	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int key_code = e.getKeyCode();
		
		switch(e.getKeyCode()) {
		
		
			case KeyEvent.VK_W:

				up = true;					
				break;
			case KeyEvent.VK_S:

				down = true;					

				
				break;
			case KeyEvent.VK_A:

				left = true;
				break;
			case KeyEvent.VK_D:

				right = true;
				break;
			case KeyEvent.VK_SPACE:

				space = true;
				break;
				
			case KeyEvent.VK_E:
				kicks_bomb = true;
				break;
			case KeyEvent.VK_ENTER:
				this.enter = true;
				System.out.println("enter");
				break;
				
			case KeyEvent.VK_P:
				pausing = !pausing;
				System.out.println("p pressed");
				System.out.println("pausing is" + pausing);

				break;
			default:
			
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key_code = e.getKeyCode();

		
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
				up = false;
				break;
			case KeyEvent.VK_S:
				down = false;
				break;
			case KeyEvent.VK_A:
				left = false;
				break;
			case KeyEvent.VK_D:
				right = false;
				break;
			case KeyEvent.VK_SPACE:
				space = false;
				break;
			case KeyEvent.VK_E:
				kicks_bomb = false;
				break;
			case KeyEvent.VK_ENTER:
				this.enter = false;
				break;
				
//			case KeyEvent.VK_P:
//				System.out.println("P release");
//				this.pausing = false;
//				break;
			default:
			
		}
	}

	
}
