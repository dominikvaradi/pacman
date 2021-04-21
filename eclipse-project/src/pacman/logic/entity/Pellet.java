package pacman.logic.entity;

import pacman.logic.Field;

/**
 * Egy pelletet (goly�csk�t) megval�s�t� oszt�ly.
 * 
 * @author V�radi Dominik
 *
 */
public class Pellet extends Entity {
	/**
	 * Konstruktor, ami egy mez�t kap param�terben, �s r�rakja a Pelletet a mez�re.
	 * 
	 * @param f - A mez�
	 */
	public Pellet( Field f ) {
		super( f );
	}

	/**
	 * �tk�zteti a Pelletet a Pacmannel.
	 * Ad 10 pontot Pacmannek, ut�na kit�rli mag�t a p�ly�r�l.
	 * 
	 * @param p - Pacman
	 */
	@Override
	public synchronized void hitBy( Pacman p ) {
		if ( p == null ) return;
		
		p.addPoints( 10 );
		getMaze().removePellet( this );
		field.remove( this );
	}
}
