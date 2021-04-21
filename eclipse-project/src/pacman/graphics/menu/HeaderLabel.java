package pacman.graphics.menu;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings( "serial" )
public class HeaderLabel extends JLabel {
	private BufferedImage image;
	
	public HeaderLabel() {
		try {
			image = ImageIO.read( getClass().getClassLoader().getResource( "menu-header.png" ) );
		} catch( IOException | NullPointerException e ) {
			System.out.println( "Hiba t�rt�nt a men� fejl�c k�p�nek beolvas�sa k�zben, hiba: " + e.getMessage() );
		}
		
		setIcon( new ImageIcon( image ) );
	}
}
