package pacman.logic.entity;

import pacman.logic.Field;

/**
 * Egy pelletet (golyócskát) megvalósító osztály.
 * 
 * @author Váradi Dominik
 *
 */
public class Pellet extends Entity {
	/**
	 * Konstruktor, ami egy mezõt kap paraméterben, és rárakja a Pelletet a mezõre.
	 * 
	 * @param f - A mezõ
	 */
	public Pellet( Field f ) {
		super( f );
	}

	/**
	 * Ütközteti a Pelletet a Pacmannel.
	 * Ad 10 pontot Pacmannek, utána kitörli magát a pályáról.
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
