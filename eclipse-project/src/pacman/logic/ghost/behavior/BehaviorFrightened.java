package pacman.logic.ghost.behavior;

import java.util.*;

import pacman.logic.Direction;
import pacman.logic.Field;
import pacman.logic.entity.Ghost;

/**
 * Egy szellem Frightened viselked�s�t megval�s�t� oszt�ly.
 * 
 * @author V�radi Dominik
 *
 */
public class BehaviorFrightened implements GhostBehavior {
	/**
	 * Egy szellemet �gy mozgat, hogy az el�rhet� szomsz�dok k�z�l v�letlenszer�en v�laszt egyet, �s arra l�pteti.
	 * El�rhet� szomsz�dnak olyan mez� sz�m�t, ami nem fal �s nem a szellemm mozg�s�val ellent�tes ir�ny�ban tal�lhat�.
	 * 
	 * @param g - A szellem
	 */
	@Override
	public synchronized void moveGhost( Ghost g ) {
		if ( g == null ) return;
		
		synchronized( g ) {
			try {
				Field f = g.getField();
				
				List<Direction> neighborDirections = new ArrayList<Direction>();
				
				for ( Direction d : Direction.values() ) {
					if ( f.getNeighbor( d ) == null || g.getHeading().getOppositeDirection() == d )
						continue;
					
					neighborDirections.add( d );
				}
				
				Random rand = new Random();
				
				Direction directionToMove;
				do {
					directionToMove = neighborDirections.get( rand.nextInt( neighborDirections.size() ) );
				} while ( g.getGateFields().contains(g.getField().getNeighbor( directionToMove ) ) );
				
				g.setHeading( directionToMove );
			} catch( NullPointerException e ) {
				return;
			}
		}
	}
	
	public String toString() {
		return "Frightened";
	}
}
