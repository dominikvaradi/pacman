package pacman.logic.entity;

import pacman.logic.Field;

/**
 * Egy gy�m�lcs�t (goly�csk�t) megval�s�t� oszt�ly.
 * 
 * @author V�radi Dominik
 *
 */
public class Fruit extends Entity {
	/**
	 * Konstruktor, ami egy mez�t kap param�terben, �s r�rakja a gy�m�lcs�t a mez�re.
	 * 
	 * @param f - A mez�
	 */
	public Fruit( Field f ) {
		super( f );
	}

	/**
	 * �tk�zteti a gy�m�lcs�t a Pacmannel.
	 * Ad 100 pontot Pacmannek, ut�na kit�rli mag�t a p�ly�r�l.
	 * 
	 * @param p - Pacman
	 */
	@Override
	public synchronized void hitBy( Pacman p ) {
		if ( p == null ) return;
		
		p.addPoints( 100 );
		getMaze().removeFruit( this );
		field.remove( this );
	}
}
