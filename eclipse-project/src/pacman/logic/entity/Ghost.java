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
 * Egy szellemet megval�s�t� oszt�ly.
 * 
 * @author V�radi Dominik
 *
 */
public class Ghost extends Entity {
	
	/** A szellem viselked�se. */
	private GhostBehavior currentBehavior;
	
	/** Az ir�ny, amibe aktu�lisan mozog a szellem. */
	private Direction heading;
	
	/** A mez�, amire a szellem Scatter m�d sor�n mozog. */
	private BoardCoordinate scatterCoordinate;
	
	/** A szellem neve. */
	private GhostName ghostName;
	
	private GhostState currentState;
	
	private GhostState systemState;
	
	/**
	 * Konstruktor, ami param�terben meg kapja a mez�t, amire a szellemet r�rakja, �s egy mez�t, amire Scatter �llapot sor�n mozog.
	 * @param f - A mez�, amire r�rakja a szellemet.
	 * @param c - A mez�, amire a szellem Scatter m�d sor�n mozog.
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
	 * Be�ll�tja a szellem viselked�s�t.
	 * Ilyenkor megfordul a szellem.
	 * 
	 * @param gb - A viselked�s
	 */
	public synchronized void setCurrentBehavior( GhostBehavior gb ) {
		heading = heading.getOppositeDirection();
		currentBehavior = gb;
		
		//System.out.println( ghostName + " viselked�sv�lt�s: " + currentBehavior.toString() ) TODO t�rl�s
	}
	
	/**
	 * Visszaadja a szellem aktu�lis viselked�s�t.
	 * 
	 * @return A szellem aktu�lis viselked�se
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
	 * Be�ll�tja, hogy a szellem melyik ir�nyba mozog aktu�lisan.
	 * 
	 * @param h - Az ir�ny
	 */
	public synchronized void setHeading( Direction h ) {
		if ( h == null ) return;
		heading = h;
	}
	
	/**
	 * Visszaadja, hogy a szellem aktu�lisan melyik ir�nyba mozog.
	 * 
	 * @return A szellem aktu�lis mozg�si-ir�nya.
	 */
	public synchronized Direction getHeading() {
		return heading;
	}
	
	/**
	 * Be�ll�tja, hogy a szellem melyik mez�re mozogjon Scatter viselked�s alatt.
	 * 
	 * @param c - A mez�.
	 */
	public synchronized void setScatterCoordinate( BoardCoordinate c ) {
		scatterCoordinate = c;
	}
	
	/**
	 * Visszaadja, hogy a szellem Scatter viselked�s alatt melyik mez�re mozog.
	 * 
	 * @return Az a mez�, amelyikre Scatter viselked�s alatt mozog a szellem.
	 */
	public synchronized BoardCoordinate getScatterCoordinate() {
		return scatterCoordinate;
	}
	
	/**
	 * �tk�zteti a szellemet egy param�terben kapott m�sik entit�ssal.
	 * 
	 * @param e - A m�sik entit�s
	 */
	@Override
	public synchronized void collideWith( Entity e ) {
		if ( e == null ) return;
		
		e.hitBy( this );
	}
	
	/**
	 * Egy met�dus, amit Pacman h�v, ha �tk�zik a szellemmel.
	 * Ha a szellem sebezhet�, akkor Pacman megeszi �t, egy�bk�nt meg Pacman meghal.
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
	 * L�pteti az aktu�lis viselked�se szerint a szellemet.
	 */
	public synchronized void step() {
		if ( currentBehavior == null ) return;
		
		currentBehavior.moveGhost( this );
	}
	
	/**
	 * Mozgatja egy param�terben kapott ir�nyba a szellemet.
	 * @param d - Az ir�ny
	 */
	public synchronized void move( Direction d ) {
		Field nextField = field.getNeighbor( d );
		
		if ( nextField == null ) return;
		
		field.remove( this );
		nextField.accept( this );
		
		heading = d;
	}
	
	/**
	 * Egy met�dus, ami akkor h�v�dik, ha a pacman megeszi a szellemet.
	 * @param p - Pacman
	 */
	public synchronized void eatenBy( Pacman p ) {
		p.addGhostCount();
		p.addPoints( p.getGhostCount() * 200 );
		die();
	}
	
	/**
	 * Meg�li a szellemet, azaz eaten viselked�sbe �trakja az aktu�lis viselked�s�t.
	 */
	public synchronized void die() {
		setCurrentState( GhostState.EATEN );
	}
	
	/**
	 * Visszaadja BFS-Algoritmus seg�ts�g�vel, hogy melyik ir�nyba kell mennie a
	 * szellemnek ahhoz, hogy legr�videbben el�rje a param�terben megadott c�lkoordin�t�t.
	 * 
	 * @param target - A koordin�ta, ahov� tart
	 * @return Az ir�ny, ahova l�pnie kell a szellemnek
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
