package pacman.logic.entity;

import pacman.logic.Direction;
import pacman.logic.Field;

/**
 * Pacman-t megval�s�t� oszt�ly.
 * 
 * @author V�radi Dominik
 *
 */
public class Pacman extends Entity {
	/** Pacman pontjainak sz�ma. */
	private long points;
	
	/** Pacman �leteinek sz�ma. */
	private short health;
	
	/** Pacman �ltal megevett szellemek sz�ma az aktu�lis PowerPellet hat�sa alatt. */
	private short ghostCount;
	
	/** Pacman �ltal megevett szellemek sz�ma a j�t�k sor�n. */
	private int ghostCountTotal;
	
	/** Az az ir�ny, amelyikbe Pacman aktu�lisan n�z. */
	private Direction heading;

	/**
	 * Konstruktor, ami kap egy mez�t, amire r�rakja, �s Pacman alap�rtelmezett �leteinek sz�m�t.
	 * 
	 * @param f - A mez�
	 * @param defHealth - Alap�rtelmezett �letek sz�ma
	 */
	public Pacman( Field f ) {
		super( f );
		points = 0;
		health = 4;
		ghostCount = 0;
		ghostCountTotal = 0;
	}
	
	/**
	 * Visszaadja Pacman pontjainak sz�m�t.
	 * 
	 * @return Pacman pontjai
	 */
	public synchronized long getPoints() {
		return points;
	}
	
	/**
	 * Visszaadja Pacman �let�t.
	 * 
	 * @return Pacman �lete
	 */
	public synchronized short getHealth() {
		return health;
	}
	
	/**
	 * Visszaadja Pacman �ltal meg�lt szellemek sz�m�t egy powerpellet hat�sa alatt.
	 * 
	 * @return Pacman �ltal meg�lt szellemek sz�m�t egy powerpellet hat�sa alatt
	 */
	public synchronized short getGhostCount() {
		return ghostCount;
	}
	
	/**
	 * Visszaadja Pacman �ltal eddig meg�lt �sszes szellemek sz�m�t.
	 * 
	 * @return Pacman �ltal eddig meg�lt �sszes szellemek sz�m�t.
	 */
	public synchronized int getGhostCountTotal() {
		return ghostCountTotal;
	}

	/**
	 * Hozz�ad Pacman pontjaihoz annyit, amennyi a param�terben �rkezik.
	 * 
	 * @param p - Hozz�adand� pontok
	 */
	public synchronized void addPoints( long p ) {
		points += p;
	}
	
	/**
	 * N�veli eggyel Pacman �let�t.
	 */
	public synchronized void addHealth() {
		if ( health < 5 ) {
			++health;
		}
	}
	
	/**
	 * N�veli eggyel a Pacman �ltal meg�lt szellemek sz�m�t egy adott powerpellet hat�sa alatt.
	 */
	public synchronized void addGhostCount() {
		++ghostCount;
		addGhostCountTotal();
	}
	
	/**
	 * N�veli eggyel a Pacman �ltal �sszesen meg�lt szellemek sz�m�t.
	 * Minden 16. meg�lt szellem ut�n kap egy �letet Pacman.
	 */
	public synchronized void addGhostCountTotal() {
		if ( ++ghostCountTotal % 16 == 0 ) {
			addHealth();
		}
	}
	
	/**
	 * Be�ll�tja, hogy Pacman melyik ir�nyba n�zzen.
	 * 
	 * @param d - Az ir�ny.
	 */
	public synchronized void setHeading( Direction d ) {
		if ( d == null || getField().getNeighbor( d ) == null || getMaze().getGateFields().contains( getField().getNeighbor( d ) ) ) return; // NULL ir�nyba nem n�zhet.

		heading = d;
	}
	
	/**
	 * Visszaadja, hogy Pacman melyik ir�nyba n�z aktu�lisan.
	 * 
	 * @return - Az ir�ny, amelyikbe Pacman aktu�lisan n�z.
	 */
	public synchronized Direction getHeading() {
		return heading;
	}

	/**
	 * �tk�zteti Pacman-t egy param�terben kapott m�sik entit�ssal.
	 * 
	 * @param e - A m�sik entit�s
	 */
	@Override
	public synchronized void collideWith( Entity e ) {
		if ( e == null ) return;
		
		e.hitBy( this );
	}
	
	/**
	 * Egy met�dus, amit egy szellem h�v, ha �tk�zik Pacman-nel.
	 * Ha a szellem sebezhet�, akkor Pacman megeszi �t, egy�bk�nt meg Pacman meghal.
	 * 
	 * @param g - A szellem
	 */
	@Override
	public synchronized void hitBy( Ghost g ) {
		if ( g == null ) return;
		
		g.hitBy( this );
	}
	
	/**
	 * Egy param�terben kapott ir�nyba mozgatja Pacman-t.
	 * 
	 * @param d - Az ir�ny
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
