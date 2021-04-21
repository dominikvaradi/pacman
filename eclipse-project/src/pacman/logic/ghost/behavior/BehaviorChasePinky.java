package pacman.logic.ghost.behavior;

import pacman.logic.BoardCoordinate;
import pacman.logic.entity.Ghost;

/**
 * Pinky Chase viselked�s�t megval�s�t� oszt�ly.
 * 
 * @author V�radi Dominik
 *
 */
public class BehaviorChasePinky implements GhostBehavior {
	/**
	 * �gy mozgatja a szellemet, hogy Pacmant szemb�l beker�tse.
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
