package pacman.graphics.game.entityview;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import pacman.logic.BoardCoordinate;
import pacman.logic.Direction;
import pacman.logic.Field;
import pacman.logic.entity.Ghost;
import pacman.logic.ghost.GhostState;

@SuppressWarnings( "serial" )
public class GhostView extends JLabel implements EntityView {
	private List<BufferedImage> bodyImages;
	
	private BufferedImage frightenedImage;
	
	private Map<Direction, BufferedImage> eyeImages;
	
	private Ghost ghost;
	
	private double fieldSize;
	
	private int counter;
	
	private boolean countUp;
	
	private BigDecimal deltaTimer;
	
	private Direction currentHeading;
	
	private BoardCoordinate southEastCorner;
	
	private GhostState currentState;
	
	public GhostView( Ghost g, double fs, BoardCoordinate sec ) {
		ghost = g;
		fieldSize = fs;
		southEastCorner = sec;
		
		bodyImages = new ArrayList<BufferedImage>();
		eyeImages = new HashMap<Direction, BufferedImage>();
		String bodyPrefix = ghost.getGhostName().toString().toLowerCase();
		try {
			for ( int i = 1; i <= 2; ++i ) {
				bodyImages.add( ImageIO.read( getClass().getClassLoader().getResource( bodyPrefix + "-" + i + ".png" ) ) );
			}
			
			for ( Direction d : Direction.values() ) {
				eyeImages.put( d, ImageIO.read( getClass().getClassLoader().getResource( "eyes-" + d.toString().toLowerCase() + ".png" ) ) );
			}
			
			frightenedImage = ImageIO.read( getClass().getClassLoader().getResource( "frightened-ghost.png" ) );
		} catch( IOException | NullPointerException e ) {
			System.out.println( "Hiba történt a szellem képeinek beolvasása közben, hiba: " + e.getMessage() );
		}
		
		counter = 0;
		countUp = true;
		deltaTimer = BigDecimal.ZERO;
		currentHeading = ghost.getHeading();
		currentState = ghost.getCurrentState();
	}
	
	public synchronized void draw( Graphics2D g2d ) {
		synchronized( ghost ) {
			Direction heading = ghost.getHeading();
			BoardCoordinate currentFieldCoords = ghost.getBoardCoordinate();
			Field nextField = ghost.getNeighborField( heading );
			BoardCoordinate nextFieldCoords;
			
			if ( nextField == null ) {
				nextFieldCoords = currentFieldCoords;
			} else {
				nextFieldCoords = nextField.getCoordinate();
			}
			
			// Alagút ellenõrzések.
			if ( currentFieldCoords.getX() == southEastCorner.getX() && nextFieldCoords.getX() == 0 && currentFieldCoords.getY() == nextFieldCoords.getY() // jobb oldali alagút bejáratba bele megy
					|| currentFieldCoords.getX() == 0 && nextFieldCoords.getX() == southEastCorner.getX() && currentFieldCoords.getY() == nextFieldCoords.getY() // bal oldali alagút bejáratba bele megy
					|| currentFieldCoords.getY() == southEastCorner.getY() && nextFieldCoords.getY() == 0 && currentFieldCoords.getX() == nextFieldCoords.getX() // felsõ alagút bejáratba bele megy
					|| currentFieldCoords.getY() == 0 && nextFieldCoords.getY() == southEastCorner.getY() && currentFieldCoords.getX() == nextFieldCoords.getX() ) // alsó alagút bejáratba bele megy
			{
				nextFieldCoords = currentFieldCoords.add( heading.toBoardCoordinate() );
			}
			
			// Képlet: Eredeti mezõ * ( 1 - deltaTimer ) + Következõ mezõ, amire lép * deltaTimer
			double screenX = ( currentFieldCoords.getX() * ( BigDecimal.ONE.subtract( deltaTimer ).doubleValue() ) + nextFieldCoords.getX() * ( deltaTimer.doubleValue() ) ) * fieldSize + ( fieldSize / 2 );
			double screenY = ( currentFieldCoords.getY() * ( BigDecimal.ONE.subtract( deltaTimer ).doubleValue() ) + nextFieldCoords.getY() * ( deltaTimer.doubleValue() ) ) * fieldSize + ( fieldSize / 2 );
			
			if ( ghost.getCurrentState() == GhostState.FRIGHTENED ) {
				g2d.drawImage( frightenedImage, (int)Math.round( screenX - ( frightenedImage.getWidth() / 2 ) ), (int)Math.round( screenY - ( frightenedImage.getHeight() / 2 ) ), this );
			} else {
				BufferedImage bodyImage = bodyImages.get( counter );
				if ( countUp ) counter++;
				else counter--;
				
				if ( counter == bodyImages.size() - 1 || counter == 0 ) countUp = !countUp;
				
				BufferedImage eyeImage = eyeImages.get( ghost.getHeading() );
				
				if ( ghost.getCurrentState() != GhostState.EATEN ) {
					g2d.drawImage( bodyImage, (int)Math.round( screenX - ( bodyImage.getWidth() / 2 ) ), (int)Math.round( screenY - ( bodyImage.getHeight() / 2 ) ), this );
				}
				g2d.drawImage( eyeImage, (int)Math.round( screenX - ( eyeImage.getWidth() / 2 ) ), (int)Math.round( screenY - ( eyeImage.getHeight() / 2 ) ), this );
			}
		}
	}
	
	public synchronized void step() {
		synchronized( ghost ) {
			if ( currentState != ghost.getCurrentState() && ghost.getCurrentState() == GhostState.EATEN ) {
				deltaTimer = BigDecimal.ZERO;
			}
			currentState = ghost.getCurrentState();
			
			if ( currentHeading == ghost.getHeading().getOppositeDirection() ) {
				deltaTimer = deltaTimer.negate();
			}
			currentHeading = ghost.getHeading();
			
			if ( ghost.getCurrentState() == GhostState.EATEN ) {
				deltaTimer = deltaTimer.add( BigDecimal.ONE );
			} else {
				deltaTimer = deltaTimer.add( new BigDecimal( "0.2" ) );
			}
			
			if ( deltaTimer.compareTo( BigDecimal.ONE ) == 0 ) {
				ghost.move( ghost.getHeading() );
				ghost.step();
				deltaTimer = BigDecimal.ZERO;
			}
		}
	}
}
