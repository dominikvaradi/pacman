package pacman.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import pacman.logic.entity.Entity;

/**
 * Egy mezõt megvalósító osztály.
 * 
 * @author Váradi Dominik
 *
 */
public class Field {
	/** A labirintus, amiben a mezõ tartózkodik. */
	private Maze maze;
	
	/** A mezõn tartózkodó entitások. */
	private List<Entity> entities;
	
	/** Egy mezõ szomszédjai irányonként. */
	private Map<Direction, Field> neighbors;
	
	/**
	 * Konstruktor, létrehozza a tárolókat, beállítja a mezõ szomszédait alapértelmezettel null-ra.
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
	 * Visszaadja a mezõn tartózkodó entitásokat.
	 * 
	 * @return A mezõn tartózkodó entitások
	 */
	public synchronized List<Entity> getEntities() {
		return entities;
	}
	
	/**
	 * Visszaadja a mezõ szomszédjait.
	 * 
	 * @return A mezõ szomszédai
	 */
	public synchronized Map<Direction, Field> getNeighbors() {
		return neighbors;
	}
	
	/**
	 * Visszaadja, hogy a mezõ melyik labirintusban tartózkodik.
	 * 
	 * @return A labirintus, amelyikben tartózkodik a mezõ.
	 */
	public synchronized Maze getMaze() {
		return maze;
	}

	/**
	 * Beállítja a mezõ labirintusának értékét a paraméterben érkezettre.
	 * 
	 * @param m - A labirintus
	 */
	public synchronized void setMaze( Maze m ) {
		maze = m;
	}
	
	/**
	 * Beállítja a mezõnek a paraméterben kapott irányban lévõ szomszédját egy paraméterben kapott mezõre.
	 * 
	 * @param d - Az irány
	 * @param f - A mezõ
	 */
	public synchronized void setNeighbor( Direction d, Field f ) {
		if ( d == null ) return;
		
		neighbors.put( d, f );
	}
	
	/**
	 * Visszaadja a mezõ egy paraméterben kapott irányhoz tartozó szomszédját.
	 * 
	 * @param d - Az irány
	 * @return A mezõ paraméterben kapott irányhoz tartozó szomszédja
	 */
	public synchronized Field getNeighbor( Direction d ) {
		if ( d == null ) return null;
		
		return neighbors.get( d );
	}

	/**
	 * Egy paraméterben kapott entitást ráléptet a mezõre.
	 * 
	 * @param e - Az entitás
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
	 * Eltávolít egy entitást a mezõrõl.
	 * 
	 * @param e - Az entitás
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
