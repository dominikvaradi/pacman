package pacman.logic.entity;

import pacman.logic.Direction;
import pacman.logic.Field;

/**
 * Pacman-t megvalósító osztály.
 * 
 * @author Váradi Dominik
 *
 */
public class Pacman extends Entity {
	/** Pacman pontjainak száma. */
	private long points;
	
	/** Pacman életeinek száma. */
	private short health;
	
	/** Pacman által megevett szellemek száma az aktuális PowerPellet hatása alatt. */
	private short ghostCount;
	
	/** Pacman által megevett szellemek száma a játék során. */
	private int ghostCountTotal;
	
	/** Az az irány, amelyikbe Pacman aktuálisan néz. */
	private Direction heading;

	/**
	 * Konstruktor, ami kap egy mezõt, amire rárakja, és Pacman alapértelmezett életeinek számát.
	 * 
	 * @param f - A mezõ
	 * @param defHealth - Alapértelmezett életek száma
	 */
	public Pacman( Field f ) {
		super( f );
		points = 0;
		health = 4;
		ghostCount = 0;
		ghostCountTotal = 0;
	}
	
	/**
	 * Visszaadja Pacman pontjainak számát.
	 * 
	 * @return Pacman pontjai
	 */
	public synchronized long getPoints() {
		return points;
	}
	
	/**
	 * Visszaadja Pacman életét.
	 * 
	 * @return Pacman élete
	 */
	public synchronized short getHealth() {
		return health;
	}
	
	/**
	 * Visszaadja Pacman által megölt szellemek számát egy powerpellet hatása alatt.
	 * 
	 * @return Pacman által megölt szellemek számát egy powerpellet hatása alatt
	 */
	public synchronized short getGhostCount() {
		return ghostCount;
	}
	
	/**
	 * Visszaadja Pacman által eddig megölt összes szellemek számát.
	 * 
	 * @return Pacman által eddig megölt összes szellemek számát.
	 */
	public synchronized int getGhostCountTotal() {
		return ghostCountTotal;
	}

	/**
	 * Hozzáad Pacman pontjaihoz annyit, amennyi a paraméterben érkezik.
	 * 
	 * @param p - Hozzáadandó pontok
	 */
	public synchronized void addPoints( long p ) {
		points += p;
	}
	
	/**
	 * Növeli eggyel Pacman életét.
	 */
	public synchronized void addHealth() {
		if ( health < 5 ) {
			++health;
		}
	}
	
	/**
	 * Növeli eggyel a Pacman által megölt szellemek számát egy adott powerpellet hatása alatt.
	 */
	public synchronized void addGhostCount() {
		++ghostCount;
		addGhostCountTotal();
	}
	
	/**
	 * Növeli eggyel a Pacman által összesen megölt szellemek számát.
	 * Minden 16. megölt szellem után kap egy életet Pacman.
	 */
	public synchronized void addGhostCountTotal() {
		if ( ++ghostCountTotal % 16 == 0 ) {
			addHealth();
		}
	}
	
	/**
	 * Beállítja, hogy Pacman melyik irányba nézzen.
	 * 
	 * @param d - Az irány.
	 */
	public synchronized void setHeading( Direction d ) {
		if ( d == null || getField().getNeighbor( d ) == null || getMaze().getGateFields().contains( getField().getNeighbor( d ) ) ) return; // NULL irányba nem nézhet.

		heading = d;
	}
	
	/**
	 * Visszaadja, hogy Pacman melyik irányba néz aktuálisan.
	 * 
	 * @return - Az irány, amelyikbe Pacman aktuálisan néz.
	 */
	public synchronized Direction getHeading() {
		return heading;
	}

	/**
	 * Ütközteti Pacman-t egy paraméterben kapott másik entitással.
	 * 
	 * @param e - A másik entitás
	 */
	@Override
	public synchronized void collideWith( Entity e ) {
		if ( e == null ) return;
		
		e.hitBy( this );
	}
	
	/**
	 * Egy metódus, amit egy szellem hív, ha ütközik Pacman-nel.
	 * Ha a szellem sebezhetõ, akkor Pacman megeszi õt, egyébként meg Pacman meghal.
	 * 
	 * @param g - A szellem
	 */
	@Override
	public synchronized void hitBy( Ghost g ) {
		if ( g == null ) return;
		
		g.hitBy( this );
	}
	
	/**
	 * Egy paraméterben kapott irányba mozgatja Pacman-t.
	 * 
	 * @param d - Az irány
	 * @throws FieldDoesntExistException 
	 */
	public synchronized void move( Direction d ) {
		if ( d == null ) return;
		
		Field nextField = field.getNeighbor( d );
		
		if ( nextField == null ) return;
		
		field.remove( this );
		nextField.accept( this );
		
		heading = d;
	}
	
	public synchronized void die() {
		if ( --health == 0 ) {
			getMaze().endGame();
		} else {
			getMaze().reset();
		}
	}
}
