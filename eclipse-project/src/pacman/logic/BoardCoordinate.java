package pacman.logic;

/**
 * Egy koordinátát megvalósító osztály.
 * 
 * @author Váradi Dominik
 *
 */
public class BoardCoordinate {
	/** X koordinátát reprezentáló érték */
	private int x;
	
	/** Y koordinátát reprezentáló érték */
	private int y;
	
	/**
	 * Konstruktor, ami paraméterben kap egy X, és Y értéket.
	 * 
	 * @param x
	 * @param y
	 */
	public BoardCoordinate( int x, int y ) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Visszaadja a koordináta X értékét.
	 * 
	 * @return koordináta X értéke
	 */
	public synchronized int getX() {
		return x;
	}

	/**
	 * Beállítja a koordináta X értékét.
	 * 
	 * @param x - Koordináta X értéke
	 */
	public synchronized void setX( int x ) {
		this.x = x;
	}

	/**
	 * Visszaadja a koordináta Y értékét.
	 * 
	 * @return koordináta Y értéke
	 */
	public synchronized int getY() {
		return y;
	}

	/**
	 * Beállítja a koordináta Y értékét.
	 * 
	 * @param y - Koordináta Y értéke
	 */
	public synchronized void setY( int y ) {
		this.y = y;
	}
	
	/**
	 * Visszaadja egy paraméterben kapott másik koordinátától való távolságát a koordinátának.
	 * 
	 * @param other - A másik koordináta
	 * @return paraméterben kapott másik koordinátától való távolsága a koordinátának
	 */
	public synchronized double getDistanceOf( BoardCoordinate other ) {
		// TODO ide exceotion kell null-ra
		
		return Math.sqrt( Math.pow( this.x - other.x, 2 ) + Math.pow( this.y - other.y, 2 ) );
	}
	
	// KELL
	public synchronized BoardCoordinate add( BoardCoordinate other ) {
		if ( other == null ) return null;
		
		return new BoardCoordinate( this.x + other.x, this.y + other.y );
	}
	
	// KELL
	public synchronized BoardCoordinate multiple( int n ) {
		return new BoardCoordinate( x * n, y * n );
	}
	
	// KELL
	public synchronized boolean equals( BoardCoordinate other ) {
		if ( other == null ) return false;
		
		return this.x == other.x && this.y == other.y;
	}
}
