package pacman.logic.entity;

import pacman.logic.Field;

/**
 * Egy gyümölcsöt (golyócskát) megvalósító osztály.
 * 
 * @author Váradi Dominik
 *
 */
public class Fruit extends Entity {
	/**
	 * Konstruktor, ami egy mezõt kap paraméterben, és rárakja a gyümölcsöt a mezõre.
	 * 
	 * @param f - A mezõ
	 */
	public Fruit( Field f ) {
		super( f );
	}

	/**
	 * Ütközteti a gyümölcsöt a Pacmannel.
	 * Ad 100 pontot Pacmannek, utána kitörli magát a pályáról.
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
