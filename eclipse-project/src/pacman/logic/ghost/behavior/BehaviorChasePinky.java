package pacman.logic.ghost.behavior;

import pacman.logic.BoardCoordinate;
import pacman.logic.entity.Ghost;

/**
 * Pinky Chase viselkedését megvalósító osztály.
 * 
 * @author Váradi Dominik
 *
 */
public class BehaviorChasePinky implements GhostBehavior {
	/**
	 * Úgy mozgatja a szellemet, hogy Pacmant szembõl bekerítse.
	 * 
	 * @param g - A szellem
	 */
	@Override
	public synchronized void moveGhost( Ghost g ) {
		if ( g == null ) return;
		
		synchronized( g ) {
			BoardCoordinate pacmansCoordinate = g.getPacmansCoordinateFromMaze();
			
			try {
				if ( g.getGateFields().contains( g.getField() ) || g.getGhostHouseField().contains( g.getField() ) ) {
					g.setHeading( g.getNextDirectionToTarget( pacmansCoordinate.add( g.getPacmansHeadingFromMaze().toBoardCoordinate().multiple( 6 ) ), null ) );
				} else {
					g.setHeading( g.getNextDirectionToTarget( pacmansCoordinate.add( g.getPacmansHeadingFromMaze().toBoardCoordinate().multiple( 6 ) ), g.getGateFields() ) );
				}
			} catch( NullPointerException e ) {
				return;
			}
		}
	}
	
	public String toString() {
		return "ChasePinky";
	}
}
