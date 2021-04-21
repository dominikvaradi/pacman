package pacman.logic.ghost.behavior;

import java.util.*;

import pacman.logic.Direction;
import pacman.logic.Field;
import pacman.logic.entity.Ghost;

/**
 * Egy szellem Frightened viselkedését megvalósító osztály.
 * 
 * @author Váradi Dominik
 *
 */
public class BehaviorFrightened implements GhostBehavior {
	/**
	 * Egy szellemet úgy mozgat, hogy az elérhetõ szomszédok közül véletlenszerûen választ egyet, és arra lépteti.
	 * Elérhetõ szomszédnak olyan mezõ számít, ami nem fal és nem a szellemm mozgásával ellentétes irányában található.
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
