package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

public class FreezeBossView extends EntityView implements Observer{
	
	public BufferedImage[] attackAnim = new BufferedImage[2];
	public BufferedImage[] deadSprite = new BufferedImage[2];
	protected int ANIMATION_SPEED = 4;
	
	public FreezeBossView() {
		createAnimationArr();
		this.sprite = attackAnim[0];
	}
	
	public static BufferedImage getProjectileSprite() {
		BufferedImage projectile = null;
		try {
			projectile = ImageIO.read(new File("src/resources/enemy/projectile.png"));
		}
		catch (IOException e) {
			
		}
		return projectile;
		
	}
	
	
	 public void createAnimationArr() {
			try {
				attackAnim[0] = ImageIO.read(new File("src/resources/boss2/base.png"));
				attackAnim[1] = ImageIO.read(new File("src/resources/boss2/wind_up.png"));
				this.deadSprite[0] = ImageIO.read(new File("src/resources/boss2/attacking.png"));
				this.deadSprite[1] = ImageIO.read(new File("src/resources/boss2/base.png"));
				
			} catch (IOException e) {
				e.printStackTrace(); //Pos nel prograqmma in cui è avvenuto l'errore
			}
	    	
	    }

	 @Override
	 public void update(Observable o, Object arg) {
		int i = (Integer)arg;
		if(i/500 == 1) {
			this.sprite = this.attackAnim[0];
		}
		else if(i/500 == 0) {
			this.sprite = this.attackAnim[1];
		}
	}


	@Override
	public BufferedImage getDeadSprite(int animationCounter) {
		return this.deadSprite[0];
//		return this.deadSprite[(animationCounter/10)%2];
	}


//	@Override
//	protected BufferedImage getHurtSprite(int hurtCounter) {
//		return this.deadSprite[(hurtCounter/10)%2];
//	}
//
//
//	@Override
//	protected BufferedImage getHurtSprite() {
//		// TODO Auto-generated method stub
//		return null;
//	}


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

