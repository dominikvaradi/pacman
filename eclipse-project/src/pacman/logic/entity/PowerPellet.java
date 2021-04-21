package pacman.logic.entity;

import pacman.logic.Field;
import pacman.logic.ghost.GhostState;
import pacman.logic.ghost.thread.FrightenedReturnTimer;

/**
 * Egy PowerPelletet megvalósító osztály.
 * 
 * @author Váradi Dominik
 *
 */
public class PowerPellet extends Pellet {
	/**
	 * Konstruktor, ami egy mezõt kap paraméterben, és rárakja a pelletet a mezõre.
	 * 
	 * @param f - A mezõ
	 */
	public PowerPellet( Field f ) {
		super( f );
	}

	/**
	 * Ütközteti a PowerPelletet a Pacmannel.
	 * Ad 10 pontot Pacmannek, kitörli a mezõrõl magát, majd utána sebezhetõve teszi a szörnyeket, beállítja a viselkedésüket frightenedre.
	 * Elindít egy idõzítõt, ami vissza állítja 7 másodperc múlva a szörnyeket, és visszaállítja pacman által megölt szörnyek számát 0-ra, majd kitörli a pelletet.
	 * 
	 * @param p - Pacman
	 */
	@Override
	public synchronized void hitBy( Pacman p ) {
		if ( p == null ) return;
		
		p.addPoints( 10 );
		
		for ( Ghost ghost : getMaze().getGhosts() ) {
			ghost.setCurrentState( GhostState.FRIGHTENED );
			Thread frightenedTimer = new FrightenedReturnTimer( ghost );
			getMaze().addThread( frightenedTimer );
			frightenedTimer.start();
		}
		
		getMaze().removePellet( this );
		field.remove( this );
	}
}
