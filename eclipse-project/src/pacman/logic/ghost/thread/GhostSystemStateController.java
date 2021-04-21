package pacman.logic.ghost.thread;

import java.util.List;

import pacman.logic.entity.Ghost;
import pacman.logic.ghost.GhostState;

public class GhostSystemStateController extends Thread {
	private volatile int counter;
	
	private List<Ghost> ghostList;
	
	public GhostSystemStateController( List<Ghost> gl ) {
		counter = 0;
		ghostList = gl;
	}
	
	@Override
	public void run() {
		while ( true ) {
			counter++;
			
			if ( counter == 7 ) { // SCATTER-->CHASE
				for( Ghost ghost : ghostList ) {
					ghost.setSystemState( GhostState.CHASE );
				}
			} else if ( counter == 27 ) { // CHASE-->SCATTER
				for( Ghost ghost : ghostList ) {
					ghost.setSystemState( GhostState.SCATTER );
				}
				counter = 0;
			}
			
			try {
				sleep( 1000 );
			} catch ( InterruptedException e ) {
				return;
			}
		}
	}
}
