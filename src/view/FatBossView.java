package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import model.Direction;

public class FatBossView extends EntityView implements Observer{

	public BufferedImage[] attackAnim = new BufferedImage[3];
	protected int ANIMATION_SPEED = 4;
	public FatBossView() {
		createAnimationArr();
		this.sprite = attackAnim[0];
		System.out.println(this.getSpriteWidth());
	}
	
	
	 public void createAnimationArr() {
	    	
			try {
				attackAnim[0] = ImageIO.read(new File("src/resources/boss1/base.png"));
				attackAnim[1] = ImageIO.read(new File("src/resources/boss1/wind_up.png"));
				attackAnim[2] = ImageIO.read(new File("src/resources/boss1/attacking.png"));
				
			} catch (IOException e) {
				e.printStackTrace(); //Pos nel prograqmma in cui è avvenuto l'errore
			}
	    	
	    }

	 @Override
		public void update(Observable o, Object arg) {
			int i = (Integer)arg;
			if (i/500 == 2) {
				this.sprite = this.attackAnim[0];
			}
			else if(i/500 == 1) {
				this.sprite = this.attackAnim[1];
			}
			else if(i/500 == 0) {
				this.sprite = this.attackAnim[2];
			}
		}


	@Override
	public BufferedImage getDeadSprite(int animationCounter) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	void setNextRight() {
		// TODO Auto-generated method stub
		
	}


	@Override
	void setNextLeft() {
		// TODO Auto-generated method stub
		
	}


	@Override
	void setNextUp() {
		// TODO Auto-generated method stub
		
	}


	@Override
	void setNextDown() {
		// TODO Auto-generated method stub
		
	}
}
