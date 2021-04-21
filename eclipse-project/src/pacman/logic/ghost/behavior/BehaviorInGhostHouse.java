package pacman.logic.ghost.behavior;

import pacman.logic.entity.Ghost;

public class BehaviorInGhostHouse implements GhostBehavior {
	@Override
	public synchronized void moveGhost( Ghost g ) {
		if ( g == null ) return;
		
		synchronized( g ) {
			try {
				g.setHeading( g.getNextDirectionToTarget( g.getMaze().getRandomGhostHouseField().getCoordinate(), g.getGateFields() ) );
			} catch( NullPointerException e ) {
				return;
			}
		}
	}
	
	public String toString() {
		return "InGhostHouse";
	}
}
