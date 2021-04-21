package pacman.logic.ghost.behavior;

import pacman.logic.entity.Ghost;

/**
 * A sz�rnyek Scatter viselked�s�t megval�s�t� oszt�ly.
 * 
 * @author V�radi Dominik
 *
 */
public class BehaviorScatter implements GhostBehavior {
	/**
	 * Egy szellemet �gy mozgat, hogy a p�lya egyik (szellemben meghat�rozott) sarka fel� l�pteti.
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
