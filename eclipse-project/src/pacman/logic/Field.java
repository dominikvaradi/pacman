package pacman.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import pacman.logic.entity.Entity;

/**
 * Egy mez�t megval�s�t� oszt�ly.
 * 
 * @author V�radi Dominik
 *
 */
public class Field {
	/** A labirintus, amiben a mez� tart�zkodik. */
	private Maze maze;
	
	/** A mez�n tart�zkod� entit�sok. */
	private List<Entity> entities;
	
	/** Egy mez� szomsz�djai ir�nyonk�nt. */
	private Map<Direction, Field> neighbors;
	
	/**
	 * Konstruktor, l�trehozza a t�rol�kat, be�ll�tja a mez� szomsz�dait alap�rtelmezettel null-ra.
	 */
	public Field( Maze m ) {
		entities = new CopyOnWriteArrayList<Entity>();
		
		maze = m;
		
		neighbors = new HashMap<Direction, Field>();
		for ( Direction d : Direction.values() ) {
			neighbors.put( d, null );
		}
	}
	
	/**
	 * Visszaadja a mez�n tart�zkod� entit�sokat.
	 * 
	 * @return A mez�n tart�zkod� entit�sok
	 */
	public synchronized List<Entity> getEntities() {
		return entities;
	}
	
	/**
	 * Visszaadja a mez� szomsz�djait.
	 * 
	 * @return A mez� szomsz�dai
	 */
	public synchronized Map<Direction, Field> getNeighbors() {
		return neighbors;
	}
	
	/**
	 * Visszaadja, hogy a mez� melyik labirintusban tart�zkodik.
	 * 
	 * @return A labirintus, amelyikben tart�zkodik a mez�.
	 */
	public synchronized Maze getMaze() {
		return maze;
	}

	/**
	 * Be�ll�tja a mez� labirintus�nak �rt�k�t a param�terben �rkezettre.
	 * 
	 * @param m - A labirintus
	 */
	public synchronized void setMaze( Maze m ) {
		maze = m;
	}
	
	/**
	 * Be�ll�tja a mez�nek a param�terben kapott ir�nyban l�v� szomsz�dj�t egy param�terben kapott mez�re.
	 * 
	 * @param d - Az ir�ny
	 * @param f - A mez�
	 */
	public synchronized void setNeighbor( Direction d, Field f ) {
		if ( d == null ) return;
		
		neighbors.put( d, f );
	}
	
	/**
	 * Visszaadja a mez� egy param�terben kapott ir�nyhoz tartoz� szomsz�dj�t.
	 * 
	 * @param d - Az ir�ny
	 * @return A mez� param�terben kapott ir�nyhoz tartoz� szomsz�dja
	 */
	public synchronized Field getNeighbor( Direction d ) {
		if ( d == null ) return null;
		
		return neighbors.get( d );
	}

	/**
	 * Egy param�terben kapott entit�st r�l�ptet a mez�re.
	 * 
	 * @param e - Az entit�s
	 */
	public synchronized void accept( Entity e ) {
		if ( e == null ) return;
		
		entities.add( e );
		e.setField( this );
		
		for ( int i = entities.size() - 1; i >= 0; --i ) {
			if ( entities.get( i ) != e ) e.collideWith( entities.get( i ) );
		}
	}
	
	/**
	 * Elt�vol�t egy entit�st a mez�r�l.
	 * 
	 * @param e - Az entit�s
	 */
	public synchronized void remove( Entity e ) {
		if ( e == null ) return;
		
		entities.remove( e );
		e.setField( null );
	}
	
	// KELL
	public synchronized BoardCoordinate getCoordinate() {
		return maze.getBoardCoordinateOfField( this );
	}
}
