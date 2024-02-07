package model;

public class BossProjectile{

	private int pos_x;
	private int pos_y;
	private boolean disappearing = false;
	ObliqueDirection dir;

	public BossProjectile(int x, int y, ObliqueDirection dir)  {
		this.dir = dir;
		this.pos_x = x;
		this.pos_y = y;
	}
	
	public boolean isDisappearing() {
		return disappearing;
	}

	/**
	 * Muove il proiettile lungo la sua traaiettoria.
	 */
	public void move(int width_limit, int height_limit, int tile_size) {
		if (pos_x + dir.component_x >= 0 && pos_x + dir.component_x < width_limit
				&& pos_y + dir.component_y >= 0 && pos_y + dir.component_y < height_limit) {
			pos_x += dir.component_x;
			pos_y += dir.component_y;
		}
		else {
			this.disappearing = true;
		}
		

		Bomberman b = Bomberman.getInstance();
		if ((b.getPos_x() <= pos_x+tile_size/2 &&  pos_x+tile_size/2 <= b.getPos_x()+tile_size)) {
			if ((b.getPos_y() <= pos_y + tile_size/2 && pos_y + tile_size/2 <= b.getPos_y()+tile_size)) {
				b.damage();
			}
			
		}
	}

	public int getPos_x() {
		return pos_x;
	}

	public int getPos_y() {
		return pos_y;
	}

}
