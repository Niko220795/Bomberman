package view;

import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import model.Direction;

public abstract class EntityView implements Observer {
	protected BufferedImage sprite;
	public EntityView() {
		// TODO Auto-generated constructor stub
	}
	

	public BufferedImage getSprite() {
		return this.sprite;
	}
    public int getSpriteHeight() {
        return this.sprite.getHeight();
    }
    
    
    public abstract BufferedImage getDeadSprite(int animationCounter);
    
    public int getSpriteWidth() {
    	return this.sprite.getWidth();
    }
    
    abstract void setNextRight();
    abstract void setNextLeft();
    abstract void setNextUp();
    abstract void setNextDown();
    
    @Override
	public void update(Observable o, Object arg) {
		if (arg != null) {
			
			Direction dir = (Direction)arg;
			switch(dir) {
			case UP:
				this.setNextUp();
				break;
			case RIGHT:
				this.setNextRight();
				break;
			case DOWN:
				this.setNextDown();
				break;
			case LEFT:
				this.setNextLeft();
				break;
			default:
			}
			
		}
	}

}
