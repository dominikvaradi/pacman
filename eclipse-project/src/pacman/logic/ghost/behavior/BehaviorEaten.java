package pacman.logic.ghost.behavior;

import pacman.logic.entity.Ghost;

/**
 * A szellemek Eaten viselkedését megvalósító osztály.
 * 
 * @author Váradi Dominik
 *
 */
public class BehaviorEaten implements GhostBehavior {
	/**
	 * Úgy mozgatja a szellemet, hogy az vissza menjen a szörny-házba.
	 * 
	 * @param g - A szellem
	 */
	@Override
	public synchronized void moveGhost( Ghost g ) { // TODO csak a szeme látszódjon, gyorsan vigye oda stb.. !! GRAFIKA !!
		if ( g == null ) return;
		
		synchronized( g ) {
			try {
				if ( g.getMaze().getGhostHouseFields().contains( g.getField() ) ) {
					g.setCurrentState( g.getSystemState() );
				} else {
					g.setHeading( g.getNextDirectionToTarget( g.getMaze().getRandomGhostHouseField().getCoordinate(), null ) );
				}
			} catch( NullPointerException e ) {
				return;
			}
		}
	}
	
	public String toString() {
		return "Eaten";
	}
}
