package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import model.Direction;

@SuppressWarnings("deprecation")
public abstract class CharacterView extends EntityView{
    protected BufferedImage dead_sprite;
    protected BufferedImage[] leftAnimations;
    protected BufferedImage[] rightAnimations;
    protected BufferedImage[] upAnimations;
    protected BufferedImage[] downAnimations;
    protected BufferedImage[] deathAnimations;
    
    protected int upCount = 0;
    protected int downCount = 0;
    protected int leftCount = 0;
    protected int rightCount = 0;
    protected int hitBoxWidth;
    protected int hitBoxHeight;
    
	protected final int SCALING_CONST = 3;

	
	
	public BufferedImage getDeadSprite(int animationCounter) {
		return deathAnimations[5-(animationCounter/10)%6];
	}
	
    
    abstract void createAnimationArr();
    
	
}