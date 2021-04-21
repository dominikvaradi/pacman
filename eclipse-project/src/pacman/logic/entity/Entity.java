package pacman.logic.entity;

import pacman.logic.BoardCoordinate;
import pacman.logic.Direction;
import pacman.logic.Field;
import pacman.logic.Maze;

/**
 * Egy entitást megvalósító osztály.
 * 
 * @author Váradi Dominik
 *
 */
public abstract class Entity {
	/** A mezõ, amin tartózkodik az entitás. */
	protected Field field;
	
	/**
	 * Konstruktor, ami paraméterben egy mezõt kap, amire rárakja.
	 * 
	 * @param f - A mezõ
	 */
	public Entity( Field f ) {
		if ( f == null ) {
			field = null;
			return;
		}
		
		f.accept( this );
	}

	/**
	 * Rálépteti egy paraméterben megadott mezõre az entitást.
	 * 
	 * @param f - A mezõ
	 */
	public synchronized void setField( Field f ) {
		field = f;
	}
	
	/**
	 * Visszaadja az entitás aktuális mezõjét.
	 * 
	 * @return Az entitás aktuális mezõje.
	 */
	public synchronized Field getField() {
		return field;
	}
	
	/**
	 * Visszaadja az entitás aktuális labirintusát.
	 * 
	 * @return Az entitás aktuális labirintusa
	 */
	public synchronized Maze getMaze() {
		return field.getMaze();
	}
	
	/**
	 * Ütközteti az entitást egy paraméterben kapott másik entitással.
	 * 
	 * @param e - A másik entitás
	 */
	public synchronized void collideWith( Entity e ) {}
	
	/**
	 * Egy metódus, amit a Pacman hív, ha ütközik az entitással.
	 * 
	 * @param p - A Pacman
	 */
	public synchronized void hitBy( Pacman p ) {}
	
	/**
	 * Egy metódus, amit egy szellem hív, ha ütközik az entitással.
	 * 
	 * @param g - A szellem
	 */
	public synchronized void hitBy( Ghost g ) {}
	
	public synchronized BoardCoordinate getBoardCoordinate() {
		if ( field == null ) return null;
		
		return field.getCoordinate();
	}
	
	public synchronized Field getNeighborField( Direction d ) {
		return field.getNeighbor( d );
	}
}
