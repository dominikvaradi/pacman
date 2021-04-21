package pacman.logic.ghost.behavior;

import pacman.logic.BoardCoordinate;
import pacman.logic.entity.Ghost;

/**
 * Inky Chase viselkedését megvalósító osztály.
 * 
 * @author Váradi Dominik
 *
 */
public class BehaviorChaseInky implements GhostBehavior {
	/**
	 * Úgy mozgatja a szellemet, hogy Pacmant hátulról bekerítse.
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
					g.setHeading( g.getNextDirectionToTarget( pacmansCoordinate.add( g.getPacmansHeadingFromMaze().getOppositeDirection().toBoardCoordinate().multiple( 6 ) ), null ) );
				} else {
					g.setHeading( g.getNextDirectionToTarget( pacmansCoordinate.add( g.getPacmansHeadingFromMaze().getOppositeDirection().toBoardCoordinate().multiple( 6 ) ), g.getGateFields() ) );
				}
			} catch( NullPointerException e ) {
				return;
			}
		}
	}
	
	public String toString() {
		return "ChaseInky";
	}
}
