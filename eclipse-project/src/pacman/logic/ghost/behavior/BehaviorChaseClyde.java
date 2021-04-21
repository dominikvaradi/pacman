package pacman.logic.ghost.behavior;

import pacman.logic.BoardCoordinate;
import pacman.logic.entity.Ghost;

/**
 * Clyde Chase viselkedését megvalósító osztály.
 * 
 * @author Váradi Dominik
 *
 */
public class BehaviorChaseClyde implements GhostBehavior {
	/**
	 * Úgy mozgatja a szellemet, hogy Pacmant a legrövidebb úton üldözze,
	 * viszont ha 8 távolságnyira kerül hozzá, akkor a pálya egyik sarka (szellemben megadott) felé kezd mozogni.
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
