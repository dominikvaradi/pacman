package pacman.graphics.game.entityview;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import pacman.logic.BoardCoordinate;
import pacman.logic.entity.Fruit;

@SuppressWarnings( "serial" )
public class FruitView extends JLabel implements EntityView {
	private Fruit fruit;

	private BufferedImage image;
	
	private double fieldSize;
	
	private List<EntityView> entityViews;
	
	public FruitView( Fruit f, double fs, List<EntityView> ev ) {
		fruit = f;
		fieldSize = fs;
		entityViews = ev;
		
		try {
			image = ImageIO.read( getClass().getClassLoader().getResource( "fruit.png" ) );
		} catch( IOException | NullPointerException e ) {
			System.out.println( "Hiba történt a gyümölcs képének beolvasása közben, hiba: " + e.getMessage() );
		}
	}
	
	@Override
	public synchronized void draw( Graphics2D g2d ) {
		BoardCoordinate boardCoords = fruit.getBoardCoordinate();
		
		if ( boardCoords == null ) return;
		
		double screenX = boardCoords.getX() * fieldSize + ( fieldSize / 2 );
		double screenY = boardCoords.getY() * fieldSize + ( fieldSize / 2 );
		g2d.drawImage( image, (int)Math.round( screenX - ( image.getWidth() / 2 ) ), (int)Math.round( screenY - ( image.getHeight() / 2 ) ), this );
	}

	@Override
	public synchronized void step() {
		if ( fruit.getField() == null || fruit.getMaze() == null ) {
			entityViews.remove( this );
		}
	}
}
