package pacman.logic.entity;

import pacman.logic.Field;
import pacman.logic.ghost.GhostState;
import pacman.logic.ghost.thread.FrightenedReturnTimer;

/**
 * Egy PowerPelletet megval�s�t� oszt�ly.
 * 
 * @author V�radi Dominik
 *
 */
public class PowerPellet extends Pellet {
	/**
	 * Konstruktor, ami egy mez�t kap param�terben, �s r�rakja a pelletet a mez�re.
	 * 
	 * @param f - A mez�
	 */
	public PowerPellet( Field f ) {
		super( f );
	}

	/**
	 * �tk�zteti a PowerPelletet a Pacmannel.
	 * Ad 10 pontot Pacmannek, kit�rli a mez�r�l mag�t, majd ut�na sebezhet�ve teszi a sz�rnyeket, be�ll�tja a viselked�s�ket frightenedre.
	 * Elind�t egy id�z�t�t, ami vissza �ll�tja 7 m�sodperc m�lva a sz�rnyeket, �s vissza�ll�tja pacman �ltal meg�lt sz�rnyek sz�m�t 0-ra, majd kit�rli a pelletet.
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
