package pacman.graphics.game.entityview;

import java.awt.Graphics2D;

public interface EntityView {
	public void draw( Graphics2D g2d );
	
	public void step();
}
