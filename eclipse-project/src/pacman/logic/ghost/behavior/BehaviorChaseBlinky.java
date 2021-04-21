package pacman.logic.ghost.behavior;

import pacman.logic.BoardCoordinate;
import pacman.logic.entity.Ghost;

/**
 * Blinky Chase viselkedését megvalósító osztály.
 * 
 * @author Váradi Dominik
 *
 */
public class BehaviorChaseBlinky implements GhostBehavior {
	/**
	 * Úgy mozgatja a szellemet, hogy Pacmant a legrövidebb úton üldözze,
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
