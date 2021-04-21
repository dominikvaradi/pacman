package pacman.graphics.menu;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pacman.logic.scoreboard.IScoreboardSource;

@SuppressWarnings( "serial" )
public class MenuFrame extends JFrame {
	private JPanel menuPanel;
	
	private IScoreboardSource scoreboardSource;
	
	public MenuFrame( IScoreboardSource iscbs ) {
		scoreboardSource = iscbs;
		
		setTitle( "Pac-Man Menu" );
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		setResizable( false );
		
		HeaderLabel headerLabel = new HeaderLabel();
		add( headerLabel, BorderLayout.NORTH );
		
		setMenuPanel( new MainPanel( this, scoreboardSource ) );
		
		pack();
		setLocationRelativeTo( null );
	}
	
	public void setMenuPanel( JPanel jp ) {
		if ( menuPanel != null ) {
			remove( menuPanel );
		}
		menuPanel = jp;
		add( menuPanel, BorderLayout.SOUTH );
		
		pack();
		setLocationRelativeTo( null );
	}
}
