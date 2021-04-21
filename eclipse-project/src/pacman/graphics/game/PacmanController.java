package pacman.graphics.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import pacman.logic.Direction;
import pacman.logic.entity.Pacman;

public class PacmanController implements KeyListener {
	private Pacman pacman;
	
	public PacmanController( Pacman p ) {
		pacman = p;
	}
	
	@Override
	public void keyPressed( KeyEvent e ) {
		synchronized( pacman ) {
			if ( e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W ) {
				pacman.setHeading( Direction.UP );
			} else if ( e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S ) {
				pacman.setHeading( Direction.DOWN );
			} else if ( e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A ) {
				pacman.setHeading( Direction.LEFT );
			} else if ( e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D ) {
				pacman.setHeading( Direction.RIGHT );
			}
		}
	}

	@Override
	public void keyReleased( KeyEvent e ) {}

	@Override
	public void keyTyped( KeyEvent e ) {}
}
