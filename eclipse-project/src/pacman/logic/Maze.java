package pacman.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import pacman.logic.entity.Fruit;
import pacman.logic.entity.Ghost;
import pacman.logic.entity.Pacman;
import pacman.logic.entity.Pellet;
import pacman.logic.ghost.GhostName;
import pacman.logic.ghost.GhostState;
import pacman.logic.ghost.thread.GhostStartThread;

/**
 * Egy labirintust megval�s�t� oszt�ly.
 * 
 * @author Dominik
 *
 */
public class Maze {
	/** A labirintus p�ly�ja, benne a mez�kkel. */
	private IMapSource mapSource;
	
	/** A p�ly�n l�v� pelletek. */
	private List<Pellet> pellets;
	
	/** A p�ly�n l�v� gy�m�lcs�k. */
	private List<Fruit> fruits;
	
	/** A p�ly�n l�v� pacman. */
	private Pacman pacman;
	
	/** A labirintushoz tartoz� j�t�kmenet. */
	private Game game;
	
	private List<Ghost> ghosts;
	
	private List<Thread> runningThreads;
	
	private int pelletCount;
	
	private IUpdatableView viewUpdater;
	
	/**
	 * Default konstruktor, l�trehozza a list�kat.
	 */
	public Maze( IMapSource ms, Game g, Pacman p ) {
		mapSource = ms;
		game = g;
		
		fruits = new LinkedList<Fruit>();
		pellets = mapSource.getPellets();
		pelletCount = pellets.size();
		
		for ( List<Field> column : mapSource.getFields() ) {
			for ( Field f : column ) {
				if ( f == null ) continue;
				
				f.setMaze( this );
			}
		}
		
		pacman = p;
		mapSource.getPacmanSpawn().accept( pacman );
		pacman.setHeading( Direction.RIGHT );
		
		ghosts = new ArrayList<Ghost>();
		
		Ghost clyde = new Ghost( getRandomGhostHouseField(), GhostName.CLYDE );
		ghosts.add( clyde );
		
		Ghost inky = new Ghost( getRandomGhostHouseField(), GhostName.INKY );
		ghosts.add( inky );
		
		Ghost pinky = new Ghost( getRandomGhostHouseField(), GhostName.PINKY );
		ghosts.add( pinky );
		
		Ghost blinky = new Ghost( getRandomGhostHouseField(), GhostName.BLINKY );
		ghosts.add( blinky );
		
		runningThreads = new CopyOnWriteArrayList<Thread>();
		Thread ghostStartThread = new GhostStartThread( ghosts, runningThreads );
		runningThreads.add( ghostStartThread );
		ghostStartThread.start();
	}
	
	/**
	 * Hozz�ad egy Pelletet a p�ly�hoz.
	 * 
	 * @param p - A Pellet
	 */
	public synchronized void addPellet( Pellet p ) {
		if ( p == null ) return;
		
		pellets.add( p );
	}
	
	/**
	 * Elt�vol�t egy Pelletet a p�ly�r�l.
	 * 
	 * @param p - A pellet
	 */
	public synchronized void removePellet( Pellet p ) {
		if ( p == null ) return;
		
		pellets.remove( p );
		
		if ( pelletCount - 70 == pellets.size() || pelletCount - 170 == pellets.size() ) {
			addFruit( new Fruit( getRandomEmptyField() ) );
		}
		
		if ( pellets.size() == 0 ) {
			for( Thread thread : runningThreads ) {
				thread.interrupt();
			}
			runningThreads.clear();
			
			game.startNewLevel();
		}
	}
	
	/**
	 * Hozz�ad egy gy�m�lcs�t a p�ly�hoz.
	 * 
	 * @param f - A gy�m�lcst
	 */
	public synchronized void addFruit( Fruit f ) {
		if ( f == null ) return;
		
		fruits.add( f );
		
		viewUpdater.addFruit();
	}
	
	/**
	 * Elt�vol�t egy gy�m�lcs�t a p�ly�r�l.
	 * 
	 * @param f - A gy�m�lcst
	 */
	public synchronized void removeFruit( Fruit f ) {
		if ( f == null ) return;
		
		fruits.remove( f );
	}
	
	/**
	 * Visszaadja a p�ly�n l�v� pelletek list�j�t.
	 * 
	 * @return - A p�ly�n l�v� pelletek list�ja
	 */
	public synchronized List<Pellet> getPellets() {
		return pellets;
	}
	
	/**
	 * Visszaadja a p�ly�n l�v� gy�m�lcs�k list�j�t.
	 * 
	 * @return - A p�ly�n l�v� gy�m�lcs�k list�ja
	 */
	public synchronized List<Fruit> getFruits() {
		return fruits;
	}
	
	/**
	 * Visszaadja Pacman koordin�t�j�t.
	 * 
	 * @return Pacman koordin�t�ja
	 */
	// KELL
	public synchronized BoardCoordinate getPacmansCoordinate() {
		return mapSource.getBoardCoordinateOfField( pacman.getField() );
	}
	
	// KELL
	public synchronized Direction getPacmansHeading() {
		return pacman.getHeading();
	}
	
	// KELL
	public synchronized BoardCoordinate getBoardCoordinateOfField( Field f ) {
		return mapSource.getBoardCoordinateOfField( f );
	}
	
	// KELL
	public synchronized int getRows() {
		return mapSource.getRows();
	}
	
	// KELL
	public synchronized int getColumns() {
		return mapSource.getColumns();
	}
	
	// KELL
	public synchronized Field getFieldByBoardCoordinate( BoardCoordinate c ) {
		return mapSource.getFieldByBoardCoordinate( c );
	}
	
	public synchronized List<Ghost> getGhosts() {
		return ghosts;
	}
	
	// KELL
	public synchronized BoardCoordinate getScatterCoordinate( GhostName gn ) {
		return mapSource.getScatterCoordinates().get( gn );
	}
	
	// KELL
	public synchronized List<Field> getGhostHouseFields() {
		return mapSource.getGhostHouseFields();
	}
	
	// KELL
	public synchronized Field getRandomGhostHouseField() {
		List<Field> ghostHouse = mapSource.getGhostHouseFields();
		
		if ( ghostHouse == null || ghostHouse.isEmpty() ) return null;
		
		Random rand = new Random();
		
		return ghostHouse.get( rand.nextInt( ghostHouse.size() ) );
	}
	
	// KELL
	public synchronized void reset() {
		for( Thread thread : runningThreads ) {
			thread.interrupt();
		}
		runningThreads.clear();
		
		for( Ghost ghost : ghosts ) {
			ghost.getField().remove( ghost );
			getRandomGhostHouseField().accept( ghost );
			ghost.setCurrentState( GhostState.INHOUSE );
			ghost.setSystemState( GhostState.SCATTER );
		}
		
		pacman.getField().remove( pacman );
		mapSource.getPacmanSpawn().accept( pacman );
		pacman.setHeading( Direction.RIGHT );
		
		Thread ghostStartThread = new GhostStartThread( ghosts, runningThreads );
		runningThreads.add( ghostStartThread );
		ghostStartThread.start();
	}
	
	public synchronized void addThread( Thread t ) {
		runningThreads.add( t );
	}
	
	public synchronized Field getRandomEmptyField() {
		Random rand = new Random();
		
		List<List<Field>> fields = mapSource.getFields();
		List<Field> randomRow;
		Field randomField;
		
		do {
			randomRow = fields.get( rand.nextInt( fields.size() ) );
			randomField = randomRow.get( rand.nextInt( randomRow.size() ) );
		} while( randomField == null || mapSource.getGateFields().contains( randomField ) || mapSource.getGhostHouseFields().contains( randomField ) || randomField.getEntities().size() != 0 );
		
		return randomField;
	}
	
	// KELL
	public synchronized void endGame() {
		for( Thread thread : runningThreads ) {
			thread.interrupt();
		}
		runningThreads.clear();
		
		game.endGame();
	}
	
	// KELL
	public synchronized Pacman getPacman() {
		return pacman;
	}
	
	// KELL
	public synchronized List<Field> getGateFields() {
		return mapSource.getGateFields();
	}
	
	public synchronized void setViewUpdater( IUpdatableView iuv ) {
		viewUpdater = iuv;
	}
}
