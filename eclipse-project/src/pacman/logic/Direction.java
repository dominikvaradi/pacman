package pacman.logic;

/**
 * Az ir�nyokat reprezent�l� enumer�ci�.
 * Egys�gvektork�nt is felfoghat�ak.
 * 
 * @author V�radi Dominik
 *
 */
public enum Direction {
	UP( new BoardCoordinate( 0, -1 ) ),
	LEFT( new BoardCoordinate( -1, 0 ) ),
	DOWN( new BoardCoordinate( 0, 1 ) ),
	RIGHT( new BoardCoordinate( 1, 0 ) );
	
	/** Egy adott ir�nyhoz tartoz� ellent�tes ir�ny. */
	private Direction opposite;
	
	/** Az ir�nyok (egys�gvektorok) koordin�t�i. */
	private BoardCoordinate coordinate;
	
	/**
	 * Konstruktor, ami koordin�t�t kap param�ter�l (x,y).
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
     * Visszaadja egy ir�nynak az ellentettj�t.
     * 
     * @return Ellent�tes ir�ny
     */
    public synchronized Direction getOppositeDirection() {
        return opposite;
    }
    
    /**
     * Visszaadja az ir�ny (egys�gvektor) koordin�t�j�t.
     * @return Az ir�ny (egys�gvektor) koordin�t�ja
     */
    public synchronized BoardCoordinate toBoardCoordinate() {
    	return coordinate;
    }
}
