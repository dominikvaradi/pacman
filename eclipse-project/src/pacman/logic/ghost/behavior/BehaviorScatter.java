package pacman.logic.ghost.behavior;

import pacman.logic.entity.Ghost;

/**
 * A szörnyek Scatter viselkedését megvalósító osztály.
 * 
 * @author Váradi Dominik
 *
 */
public class BehaviorScatter implements GhostBehavior {
	/**
	 * Egy szellemet úgy mozgat, hogy a pálya egyik (szellemben meghatározott) sarka felé lépteti.
	 * 
	 * @param g - A szellem
	 */
	@Override
	public synchronized void moveGhost( Ghost g ) {
		if ( g == null ) return;
		
		synchronized( g ) {
			try {
				if ( g.getGateFields().contains( g.getField() ) || g.getGhostHouseField().contains( g.getField() ) ) {
					g.setHeading( g.getNextDirectionToTarget( g.getScatterCoordinate(), null ) );
				} else {
					g.setHeading( g.getNextDirectionToTarget( g.getScatterCoordinate(), g.getGateFields() ) );
				}
			} catch( NullPointerException e ) {
				return;
			}
		}
	}
	
	public String toString() {
		return "Scatter";
	}
}
