package controller.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Bomberman;

public class RemoteBombListener implements MouseListener {
	
	Bomberman bomberman = Bomberman.getInstance();
	private boolean placed = false;
	private boolean exploded = false;

	public boolean isPlaced() {
		return placed;
	}
	public boolean isExploded() {
		return exploded;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		var mouse_event = e.getButton();
		switch(mouse_event) {
		case MouseEvent.BUTTON1:
			this.placed = true;
			System.out.println("placed remote bomb");
			break;
		case MouseEvent.BUTTON3:
			this.exploded = true;
			System.out.println("exploded remote bomb");

			break;
		default:
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		var mouse_event = e.getButton();
		switch(mouse_event) {
		case MouseEvent.BUTTON1:
			this.placed = false;
			break;
		case MouseEvent.BUTTON3:
			this.exploded = false;

			break;
		default:
			
		}	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
