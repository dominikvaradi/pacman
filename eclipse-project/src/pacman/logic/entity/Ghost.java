package pacman.logic.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import pacman.logic.BoardCoordinate;
import pacman.logic.Direction;
import pacman.logic.Field;
import pacman.logic.ghost.GhostName;
import pacman.logic.ghost.GhostState;
import pacman.logic.ghost.behavior.BehaviorChaseBlinky;
import pacman.logic.ghost.behavior.BehaviorChaseClyde;
import pacman.logic.ghost.behavior.BehaviorChaseInky;
import pacman.logic.ghost.behavior.BehaviorChasePinky;
import pacman.logic.ghost.behavior.BehaviorEaten;
import pacman.logic.ghost.behavior.BehaviorFrightened;
import pacman.logic.ghost.behavior.BehaviorInGhostHouse;
import pacman.logic.ghost.behavior.BehaviorScatter;
import pacman.logic.ghost.behavior.GhostBehavior;

/**
 * Egy szellemet megvalósító osztály.
 * 
 * @author Váradi Dominik
 *
 */
public class Ghost extends Entity {
	
	/** A szellem viselkedése. */
	private GhostBehavior currentBehavior;
	
	/** Az irány, amibe aktuálisan mozog a szellem. */
	private Direction heading;
	
	/** A mezõ, amire a szellem Scatter mód során mozog. */
	private BoardCoordinate scatterCoordinate;
	
	/** A szellem neve. */
	private GhostName ghostName;
	
	private GhostState currentState;
	
	private GhostState systemState;
	
	/**
	 * Konstruktor, ami paraméterben meg kapja a mezõt, amire a szellemet rárakja, és egy mezõt, amire Scatter állapot során mozog.
	 * @param f - A mezõ, amire rárakja a szellemet.
	 * @param c - A mezõ, amire a szellem Scatter mód során mozog.
	 * @param
	 */
	public Ghost( Field f, GhostName gn ) {
		super( f );
		ghostName = gn;
		scatterCoordinate = getMaze().getScatterCoordinate( ghostName );
		heading = Direction.DOWN;
		setCurrentState( GhostState.INHOUSE );
		setSystemState( GhostState.SCATTER );
	}
	
	// KELL
	public synchronized GhostName getGhostName() {
		return ghostName;
	}
	
	/**
	 * Beállítja a szellem viselkedését.
	 * Ilyenkor megfordul a szellem.
	 * 
	 * @param gb - A viselkedés
	 */
	public synchronized void setCurrentBehavior( GhostBehavior gb ) {
		heading = heading.getOppositeDirection();
		currentBehavior = gb;
		
		//System.out.println( ghostName + " viselkedésváltás: " + currentBehavior.toString() ) TODO törlés
	}
	
	/**
	 * Visszaadja a szellem aktuális viselkedését.
	 * 
	 * @return A szellem aktuális viselkedése
	 */
	public synchronized GhostBehavior getCurrentBehavior() {
		return currentBehavior;
	}
	
	public synchronized void setCurrentState( GhostState gs ) {
		if ( gs == null || currentState == gs ) return;
		
		currentState = gs;
		
		if ( currentState == GhostState.CHASE ) {
			switch( ghostName ) {
				case CLYDE:
					setCurrentBehavior( new BehaviorChaseClyde() );
					break;
				case INKY:
					setCurrentBehavior( new BehaviorChaseInky() );
					break;
				case PINKY:
					setCurrentBehavior( new BehaviorChasePinky() );
					break;
				default:
					setCurrentBehavior( new BehaviorChaseBlinky() );
					break;
			}
		} else if ( currentState == GhostState.SCATTER ) {
			setCurrentBehavior( new BehaviorScatter() );
		} else if ( currentState == GhostState.FRIGHTENED ) {
			setCurrentBehavior( new BehaviorFrightened() );
		} else if ( currentState == GhostState.EATEN ) {
			setCurrentBehavior( new BehaviorEaten() );
		} else if ( currentState == GhostState.INHOUSE ) {
			setCurrentBehavior( new BehaviorInGhostHouse() );
		}
	}
	
	public synchronized GhostState getCurrentState() {
		return currentState;
	}
	
	public synchronized void setSystemState( GhostState gs ) {
		if ( gs == null ) return;
		
		systemState = gs;
		
		if ( currentState == GhostState.EATEN || currentState == GhostState.FRIGHTENED || currentState == GhostState.INHOUSE || currentState == systemState )
			return;
		
		setCurrentState( systemState );
	}
	
	public synchronized GhostState getSystemState() {
		return systemState;
	}
	
	/**
	 * Beállítja, hogy a szellem melyik irányba mozog aktuálisan.
	 * 
	 * @param h - Az irány
	 */
	public synchronized void setHeading( Direction h ) {
		if ( h == null ) return;
		heading = h;
	}
	
	/**
	 * Visszaadja, hogy a szellem aktuálisan melyik irányba mozog.
	 * 
	 * @return A szellem aktuális mozgási-iránya.
	 */
	public synchronized Direction getHeading() {
		return heading;
	}
	
	/**
	 * Beállítja, hogy a szellem melyik mezõre mozogjon Scatter viselkedés alatt.
	 * 
	 * @param c - A mezõ.
	 */
	public synchronized void setScatterCoordinate( BoardCoordinate c ) {
		scatterCoordinate = c;
	}
	
	/**
	 * Visszaadja, hogy a szellem Scatter viselkedés alatt melyik mezõre mozog.
	 * 
	 * @return Az a mezõ, amelyikre Scatter viselkedés alatt mozog a szellem.
	 */
	public synchronized BoardCoordinate getScatterCoordinate() {
		return scatterCoordinate;
	}
	
	/**
	 * Ütközteti a szellemet egy paraméterben kapott másik entitással.
	 * 
	 * @param e - A másik entitás
	 */
	@Override
	public synchronized void collideWith( Entity e ) {
		if ( e == null ) return;
		
		e.hitBy( this );
	}
	
	/**
	 * Egy metódus, amit Pacman hív, ha ütközik a szellemmel.
	 * Ha a szellem sebezhetõ, akkor Pacman megeszi õt, egyébként meg Pacman meghal.
	 * 
	 * @param g - A szellem
	 */
	@Override
	public synchronized void hitBy( Pacman p ) {
		if ( p == null || currentState == GhostState.EATEN ) return;
		
		if ( currentState == GhostState.FRIGHTENED ) {
			eatenBy( p );
		} else {
			p.die();
		}
	}
	
	/**
	 * Lépteti az aktuális viselkedése szerint a szellemet.
	 */
	public synchronized void step() {
		if ( currentBehavior == null ) return;
		
		currentBehavior.moveGhost( this );
	}
	
	/**
	 * Mozgatja egy paraméterben kapott irányba a szellemet.
	 * @param d - Az irány
	 */
	public synchronized void move( Direction d ) {
		Field nextField = field.getNeighbor( d );
		
		if ( nextField == null ) return;
		
		field.remove( this );
		nextField.accept( this );
		
		heading = d;
	}
	
	/**
	 * Egy metódus, ami akkor hívódik, ha a pacman megeszi a szellemet.
	 * @param p - Pacman
	 */
	public synchronized void eatenBy( Pacman p ) {
		p.addGhostCount();
		p.addPoints( p.getGhostCount() * 200 );
		die();
	}
	
	/**
	 * Megöli a szellemet, azaz eaten viselkedésbe átrakja az aktuális viselkedését.
	 */
	public synchronized void die() {
		setCurrentState( GhostState.EATEN );
	}
	
	/**
	 * Visszaadja BFS-Algoritmus segítségével, hogy melyik irányba kell mennie a
	 * szellemnek ahhoz, hogy legrövidebben elérje a paraméterben megadott célkoordinátát.
	 * 
	 * @param target - A koordináta, ahová tart
	 * @return Az irány, ahova lépnie kell a szellemnek
	 */
	public synchronized Direction getNextDirectionToTarget( BoardCoordinate target, List<Field> exceptions ) {
		if ( target == null ) return null;
		
		Direction[][] directions = new Direction[getMaze().getRows()][getMaze().getColumns()];
		
		for ( int y = 0; y < getMaze().getRows(); ++y ) {
			for ( int x = 0; x < getMaze().getColumns(); ++x ) {
				directions[y][x] = null;
			}
		}
		
		BoardCoordinate closestField = getMaze().getBoardCoordinateOfField( field );
		double closestDistance = closestField.getDistanceOf( target );
		
		Queue<BoardCoordinate> q = new LinkedList<BoardCoordinate>();
		for ( Direction d: Direction.values() ) {
			Field neighbor = field.getNeighbor(d);
			BoardCoordinate neighborCoords = getMaze().getBoardCoordinateOfField( neighbor );
			
			if ( neighborCoords == null || d == heading.getOppositeDirection() || ( exceptions != null && exceptions.contains( neighbor ) ) )
				continue;
			
			q.add( neighborCoords );
			directions[neighborCoords.getY()][neighborCoords.getX()] = d;
		}
		
		while ( !q.isEmpty() ) {
			BoardCoordinate current = q.poll();
			
			double distance = current.getDistanceOf( target );
			
			if ( distance < closestDistance ) {
				closestField = current;
				closestDistance = distance;
			}
			
			for ( Direction d: Direction.values() ) {
				Field neighbor = getMaze().getFieldByBoardCoordinate( current ).getNeighbor(d);
				BoardCoordinate neighborCoords = getMaze().getBoardCoordinateOfField( neighbor );
				
				if ( neighborCoords == null || directions[neighborCoords.getY()][neighborCoords.getX()] != null || ( exceptions != null && exceptions.contains( neighbor ) ) )
					continue;
				
				q.add( neighborCoords );
				directions[neighborCoords.getY()][neighborCoords.getX()] = directions[current.getY()][current.getX()];
			}
		}
		
		return directions[closestField.getY()][closestField.getX()];
	}
	
	// KELL
	public synchronized BoardCoordinate getPacmansCoordinateFromMaze() {
		return getMaze().getPacmansCoordinate();
	}
	
	// KELL
	public synchronized Direction getPacmansHeadingFromMaze() {
		return getMaze().getPacmansHeading();
	}
	
	public synchronized List<Field> getGhostHouseField() {
		return getMaze().getGhostHouseFields();
	}
	
	public synchronized List<Field> getGateFields() {
		return getMaze().getGateFields();
	}
}
