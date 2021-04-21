package pacman.graphics.game.entityview;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import pacman.logic.BoardCoordinate;
import pacman.logic.entity.Pellet;
import pacman.logic.entity.PowerPellet;

@SuppressWarnings( "serial" )
public class PelletView extends JLabel implements EntityView {
	private Pellet pellet;

	private BufferedImage image;
	
	private double fieldSize;
	
	private List<EntityView> entityViews;
	
	public PelletView( Pellet p, double fs, List<EntityView> ev ) {
		pellet = p;
		fieldSize = fs;
		entityViews = ev;
		
		try {
			if ( pellet instanceof PowerPellet ) {
				image = ImageIO.read( getClass().getClassLoader().getResource( "powerpellet.png" ) );
			} else {
				image = ImageIO.read( getClass().getClassLoader().getResource( "pellet.png" ) );
			}
		} catch( IOException | NullPointerException e ) {
			System.out.println( "Hiba történt a pellet képének beolvasása közben, hiba: " + e.getMessage() );
		}
	}
	
	@Override
	public synchronized void draw( Graphics2D g2d ) {
		BoardCoordinate boardCoords = pellet.getBoardCoordinate();
		
		if ( boardCoords == null ) return;
		
		double screenX = boardCoords.getX() * fieldSize + ( fieldSize / 2 );
		double screenY = boardCoords.getY() * fieldSize + ( fieldSize / 2 );
		g2d.drawImage( image, (int)Math.round( screenX - ( image.getWidth() / 2 ) ), (int)Math.round( screenY - ( image.getHeight() / 2 ) ), this );
	}

	@Override
	public synchronized void step() {
		if ( pellet.getField() == null || pellet.getMaze() == null ) {
			entityViews.remove( this );
		}
	}
}
