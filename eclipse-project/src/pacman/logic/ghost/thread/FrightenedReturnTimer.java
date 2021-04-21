package pacman.logic.ghost.thread;

import pacman.logic.entity.Ghost;
import pacman.logic.ghost.GhostState;

public class FrightenedReturnTimer extends Thread {
	private Ghost ghost;

	public FrightenedReturnTimer( Ghost g ) {
		ghost = g;
	}
	
	@Override
	public void run() {
		try {
			sleep( 6000 );
		} catch ( InterruptedException e ) {
			return;
		}
		
		synchronized( ghost ) {
			if ( ghost.getCurrentState() == GhostState.EATEN ) return;
			
			ghost.setCurrentState( ghost.getSystemState() );
		}
	}
}
