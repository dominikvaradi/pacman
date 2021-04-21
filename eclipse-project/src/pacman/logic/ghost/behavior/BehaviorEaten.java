package pacman.logic.ghost.behavior;

import pacman.logic.entity.Ghost;

/**
 * A szellemek Eaten viselked�s�t megval�s�t� oszt�ly.
 * 
 * @author V�radi Dominik
 *
 */
public class BehaviorEaten implements GhostBehavior {
	/**
	 * �gy mozgatja a szellemet, hogy az vissza menjen a sz�rny-h�zba.
	 * 
	 * @param g - A szellem
	 */
	@Override
	public synchronized void moveGhost( Ghost g ) { // TODO csak a szeme l�tsz�djon, gyorsan vigye oda stb.. !! GRAFIKA !!
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
