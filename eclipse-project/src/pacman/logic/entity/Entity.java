package pacman.logic.entity;

import pacman.logic.BoardCoordinate;
import pacman.logic.Direction;
import pacman.logic.Field;
import pacman.logic.Maze;

/**
 * Egy entit�st megval�s�t� oszt�ly.
 * 
 * @author V�radi Dominik
 *
 */
public abstract class Entity {
	/** A mez�, amin tart�zkodik az entit�s. */
	protected Field field;
	
	/**
	 * Konstruktor, ami param�terben egy mez�t kap, amire r�rakja.
	 * 
	 * @param f - A mez�
	 */
	public Entity( Field f ) {
		if ( f == null ) {
			field = null;
			return;
		}
		
		f.accept( this );
	}

	/**
	 * R�l�pteti egy param�terben megadott mez�re az entit�st.
	 * 
	 * @param f - A mez�
	 */
	public synchronized void setField( Field f ) {
		field = f;
	}
	
	/**
	 * Visszaadja az entit�s aktu�lis mez�j�t.
	 * 
	 * @return Az entit�s aktu�lis mez�je.
	 */
	public synchronized Field getField() {
		return field;
	}
	
	/**
	 * Visszaadja az entit�s aktu�lis labirintus�t.
	 * 
	 * @return Az entit�s aktu�lis labirintusa
	 */
	public synchronized Maze getMaze() {
		return field.getMaze();
	}
	
	/**
	 * �tk�zteti az entit�st egy param�terben kapott m�sik entit�ssal.
	 * 
	 * @param e - A m�sik entit�s
	 */
	public synchronized void collideWith( Entity e ) {}
	
	/**
	 * Egy met�dus, amit a Pacman h�v, ha �tk�zik az entit�ssal.
	 * 
	 * @param p - A Pacman
	 */
	public synchronized void hitBy( Pacman p ) {}
	
	/**
	 * Egy met�dus, amit egy szellem h�v, ha �tk�zik az entit�ssal.
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
