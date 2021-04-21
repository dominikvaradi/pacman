package pacman.graphics.game;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import pacman.logic.entity.Pacman;

@SuppressWarnings( "serial" )
public class StatusLabel extends JLabel {
	private Pacman pacman;
	
	public StatusLabel( Pacman p ) {
		pacman = p;
		setOpaque( true );
		setBackground( Color.BLACK );
		setForeground( Color.ORANGE );
		setFont( new Font( "sans-serif", Font.BOLD, 20 ) );
		setText( "Health: " + pacman.getHealth() + " Points: " + pacman.getPoints() );
		setHorizontalAlignment( CENTER );
	}
	
	public void update() {
		setText( "Health: " + pacman.getHealth() + " Points: " + pacman.getPoints() );
	}
}
