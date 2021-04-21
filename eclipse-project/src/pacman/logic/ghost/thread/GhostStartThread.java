package pacman.logic.ghost.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pacman.logic.entity.Ghost;
import pacman.logic.ghost.GhostState;

public class GhostStartThread extends Thread {
	private volatile List<Thread> threadList;
	
	private List<Ghost> ghostList;
	
	public GhostStartThread( List<Ghost> gl, List<Thread> tl ) {
		threadList = tl;
		ghostList = gl;
	}
	
	@Override
	public void run() {
		List<Ghost> remaining = new ArrayList<Ghost>();
		for ( Ghost ghost : ghostList ) {
			remaining.add( ghost );
		}
		
		Random rand = new Random();
		
		while ( !remaining.isEmpty() ) {
			Ghost randomGhost = remaining.get( rand.nextInt( remaining.size() ) );
			
			synchronized( randomGhost ) {
				randomGhost.setCurrentState( GhostState.SCATTER );
				remaining.remove( randomGhost );
			}
			
			try {
				sleep( 2000 );
			} catch ( InterruptedException e ) {
				return;
			}
		}
		
		Thread ghostSystemStateController = new GhostSystemStateController( ghostList );
		threadList.add( ghostSystemStateController );
		ghostSystemStateController.start();
		threadList.remove( this );
	}
}
