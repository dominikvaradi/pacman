package pacman.logic.ghost.behavior;

import pacman.logic.BoardCoordinate;
import pacman.logic.entity.Ghost;

/**
 * Blinky Chase viselked�s�t megval�s�t� oszt�ly.
 * 
 * @author V�radi Dominik
 *
 */
public class BehaviorChaseBlinky implements GhostBehavior {
	/**
	 * �gy mozgatja a szellemet, hogy Pacmant a legr�videbb �ton �ld�zze,
	 * 
	 * @param g - A szellem
	 */
	@Override
	public synchronized void moveGhost( Ghost g ) {
		if ( g == null ) return;
		
		synchronized( g ) {
			try {
				BoardCoordinate pacmansCoordinate = g.getPacmansCoordinateFromMaze();
				
				if ( g.getGateFields().contains( g.getField() ) || g.getGhostHouseField().contains( g.getField() ) ) {
					g.setHeading( g.getNextDirectionToTarget( pacmansCoordinate, null ) );
				} else {
					g.setHeading( g.getNextDirectionToTarget( pacmansCoordinate, g.getGateFields() ) );
				}
			} catch( NullPointerException e ) {
				return;
			}
		}
	}
	
	public String toString() {
		return "ChaseBlinky";
	}
}
