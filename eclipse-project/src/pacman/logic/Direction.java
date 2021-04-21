package pacman.logic;

/**
 * Az irányokat reprezentáló enumeráció.
 * Egységvektorként is felfoghatóak.
 * 
 * @author Váradi Dominik
 *
 */
public enum Direction {
	UP( new BoardCoordinate( 0, -1 ) ),
	LEFT( new BoardCoordinate( -1, 0 ) ),
	DOWN( new BoardCoordinate( 0, 1 ) ),
	RIGHT( new BoardCoordinate( 1, 0 ) );
	
	/** Egy adott irányhoz tartozó ellentétes irány. */
	private Direction opposite;
	
	/** Az irányok (egységvektorok) koordinátái. */
	private BoardCoordinate coordinate;
	
	/**
	 * Konstruktor, ami koordinátát kap paraméterül (x,y).
	 * @param x
	 * @param y
	 */
	private Direction( BoardCoordinate c ) {
		coordinate = c;
	}
	
    static {
    	UP.opposite = DOWN;
    	LEFT.opposite = RIGHT;
    	DOWN.opposite = UP;
    	RIGHT.opposite = LEFT;
    }

    /**
     * Visszaadja egy iránynak az ellentettjét.
     * 
     * @return Ellentétes irány
     */
    public synchronized Direction getOppositeDirection() {
        return opposite;
    }
    
    /**
     * Visszaadja az irány (egységvektor) koordinátáját.
     * @return Az irány (egységvektor) koordinátája
     */
    public synchronized BoardCoordinate toBoardCoordinate() {
    	return coordinate;
    }
}
