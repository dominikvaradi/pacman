package pacman.logic.ghost.behavior;

import pacman.logic.BoardCoordinate;
import pacman.logic.entity.Ghost;

/**
 * Clyde Chase viselked�s�t megval�s�t� oszt�ly.
 * 
 * @author V�radi Dominik
 *
 */
public class BehaviorChaseClyde implements GhostBehavior {
	/**
	 * �gy mozgatja a szellemet, hogy Pacmant a legr�videbb �ton �ld�zze,
	 * viszont ha 8 t�vols�gnyira ker�l hozz�, akkor a p�lya egyik sarka (szellemben megadott) fel� kezd mozogni.
	 * 
	 * @param g - A szellem
	 */
	@Override
	public synchronized void moveGhost( Ghost g ) {
		if ( g == null ) return;
		
	synchronized( g ) {
		BoardCoordinate pacmansCoordinate = g.getPacmansCoordinateFromMaze();
		try {
			if ( g.getBoardCoordinate().getDistanceOf( pacmansCoordinate ) < 8 ) {
				if ( g.getGateFields().contains( g.getField() ) || g.getGhostHouseField().contains( g.getField() ) ) {
						g.setHeading( g.getNextDirectionToTarget( g.getScatterCoordinate(), null ) );
					} else {
						g.setHeading( g.getNextDirectionToTarget( g.getScatterCoordinate(), g.getGateFields() ) );
					}
				} else {
					if ( g.getGateFields().contains( g.getField() ) || g.getGhostHouseField().contains( g.getField() ) ) {
						g.setHeading( g.getNextDirectionToTarget( pacmansCoordinate, null ) );
					} else {
						g.setHeading( g.getNextDirectionToTarget( pacmansCoordinate, g.getGateFields() ) );
					}
				}
			} catch( NullPointerException e ) {
				return;
			}
		}
	}
	
	public String toString() {
		return "ChaseClyde";
	}
}
