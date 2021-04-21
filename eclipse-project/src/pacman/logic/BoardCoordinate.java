package pacman.logic;

/**
 * Egy koordin�t�t megval�s�t� oszt�ly.
 * 
 * @author V�radi Dominik
 *
 */
public class BoardCoordinate {
	/** X koordin�t�t reprezent�l� �rt�k */
	private int x;
	
	/** Y koordin�t�t reprezent�l� �rt�k */
	private int y;
	
	/**
	 * Konstruktor, ami param�terben kap egy X, �s Y �rt�ket.
	 * 
	 * @param x
	 * @param y
	 */
	public BoardCoordinate( int x, int y ) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Visszaadja a koordin�ta X �rt�k�t.
	 * 
	 * @return koordin�ta X �rt�ke
	 */
	public synchronized int getX() {
		return x;
	}

	/**
	 * Be�ll�tja a koordin�ta X �rt�k�t.
	 * 
	 * @param x - Koordin�ta X �rt�ke
	 */
	public synchronized void setX( int x ) {
		this.x = x;
	}

	/**
	 * Visszaadja a koordin�ta Y �rt�k�t.
	 * 
	 * @return koordin�ta Y �rt�ke
	 */
	public synchronized int getY() {
		return y;
	}

	/**
	 * Be�ll�tja a koordin�ta Y �rt�k�t.
	 * 
	 * @param y - Koordin�ta Y �rt�ke
	 */
	public synchronized void setY( int y ) {
		this.y = y;
	}
	
	/**
	 * Visszaadja egy param�terben kapott m�sik koordin�t�t�l val� t�vols�g�t a koordin�t�nak.
	 * 
	 * @param other - A m�sik koordin�ta
	 * @return param�terben kapott m�sik koordin�t�t�l val� t�vols�ga a koordin�t�nak
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
