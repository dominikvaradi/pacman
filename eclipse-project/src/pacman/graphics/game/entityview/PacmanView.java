package pacman.graphics.game.entityview;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import pacman.logic.BoardCoordinate;
import pacman.logic.Direction;
import pacman.logic.Field;
import pacman.logic.entity.Pacman;

@SuppressWarnings( "serial" )
public class PacmanView extends JLabel implements EntityView {
	private List<BufferedImage> bodyImages;
	
	private Pacman pacman;
	
	private double fieldSize;
	
	private int counter;
	
	private boolean countUp;
	
	private BigDecimal deltaTimer;
	
	private Direction currentHeading;
	
	private BoardCoordinate southEastCorner;
	
	public PacmanView( Pacman p, double fs, BoardCoordinate sec ) {
		pacman = p;
		fieldSize = fs;
		southEastCorner = sec;
		
		bodyImages = new ArrayList<BufferedImage>();
		try {
			for ( int i = 0; i <= 5; ++i ) {
				bodyImages.add( ImageIO.read( getClass().getClassLoader().getResource( "pacman-" + i + ".png" ) ) );
			}
		} catch( IOException | NullPointerException e ) {
			System.out.println( "Hiba történt a pacman képeinek beolvasása közben, hiba: " + e.getMessage() );
		}
		
		counter = 0;
		countUp = true;
		currentHeading = pacman.getHeading();
		deltaTimer = BigDecimal.ZERO;
	}
	
	public synchronized void draw( Graphics2D g2d ) {
		synchronized( pacman ) {
			Direction heading = pacman.getHeading();
			BoardCoordinate currentFieldCoords = pacman.getBoardCoordinate();
			Field nextField = pacman.getField().getNeighbor( heading );
			BoardCoordinate nextFieldCoords;
			
			if ( nextField == null ) {
				nextFieldCoords = currentFieldCoords;
				deltaTimer = BigDecimal.ZERO;
			} else {
				nextFieldCoords = nextField.getCoordinate();
			}
			
			if ( currentFieldCoords.getX() == southEastCorner.getX() && nextFieldCoords.getX() == 0 && currentFieldCoords.getY() == nextFieldCoords.getY()
					|| currentFieldCoords.getX() == 0 && nextFieldCoords.getX() == southEastCorner.getX() && currentFieldCoords.getY() == nextFieldCoords.getY()
					|| currentFieldCoords.getY() == southEastCorner.getY() && nextFieldCoords.getY() == 0 && currentFieldCoords.getX() == nextFieldCoords.getX()
					|| currentFieldCoords.getY() == 0 && nextFieldCoords.getY() == southEastCorner.getY() && currentFieldCoords.getX() == nextFieldCoords.getX() )
			{
				nextFieldCoords = currentFieldCoords.add( heading.toBoardCoordinate() );
			}
			
			double screenX = ( currentFieldCoords.getX() * ( BigDecimal.ONE.subtract( deltaTimer ).doubleValue() ) + nextFieldCoords.getX() * ( deltaTimer.doubleValue() ) ) * fieldSize + ( fieldSize / 2 );
			double screenY = ( currentFieldCoords.getY() * ( BigDecimal.ONE.subtract( deltaTimer ).doubleValue() ) + nextFieldCoords.getY() * ( deltaTimer.doubleValue() ) ) * fieldSize + ( fieldSize / 2 );
			
			BufferedImage image = bodyImages.get( counter );
			if ( countUp ) counter++;
			else counter--;
			if ( counter == bodyImages.size() - 1 || counter  == 0 ) countUp = !countUp;
			
			if ( heading == Direction.LEFT ) {
				image = rotateImage( mirrorImage( image ), 180.0 );
			} else if ( heading == Direction.UP ) {
				image = rotateImage( image, -90.0 );
			} else if ( heading == Direction.DOWN ) {
				image = mirrorImage( rotateImage( image, -90.0 ) );
			}
			g2d.drawImage( image, (int)Math.round( screenX - ( image.getWidth() / 2 ) ), (int)Math.round( screenY - ( image.getHeight() / 2 ) ), this );
		}
	}
	
	public synchronized void step() {
		synchronized( pacman ) {
			if ( currentHeading == pacman.getHeading().getOppositeDirection() ) {
				deltaTimer = deltaTimer.negate();
			} else if ( currentHeading != pacman.getHeading() ) {
				deltaTimer = BigDecimal.ZERO;
			}
			currentHeading = pacman.getHeading();
			
			deltaTimer = deltaTimer.add( new BigDecimal( "0.2" ) );
			
			if ( deltaTimer.compareTo( BigDecimal.ONE ) == 0 ) {
				pacman.move( pacman.getHeading() );
				deltaTimer = BigDecimal.ZERO;
			}
		}
	}
	
	private BufferedImage rotateImage( BufferedImage image, double angle ) {
		int w = image.getWidth();    
	    int h = image.getHeight();

	    BufferedImage newImage = new BufferedImage( w, h, image.getType() );  
	    Graphics2D graphic = newImage.createGraphics();
	    graphic.rotate(Math.toRadians( angle ), w / 2, h / 2 );
	    graphic.drawImage( image, null, 0, 0 );
	    graphic.dispose();
	    
	    return newImage;
	}
	
	private BufferedImage mirrorImage( BufferedImage image ) {
        AffineTransform at = new AffineTransform();
        at.concatenate( AffineTransform.getScaleInstance( 1, -1 ) );
        at.concatenate( AffineTransform.getTranslateInstance( 0, -image.getHeight() ) );
        
        BufferedImage newImage = new BufferedImage( image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        
        g.transform( at );
        g.drawImage( image, 0, 0, null );
        g.dispose();
        
        return newImage;
    }
}
