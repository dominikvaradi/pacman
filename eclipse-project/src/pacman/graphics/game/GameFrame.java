package pacman.graphics.game;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.Timer;

import pacman.graphics.menu.MenuFrame;
import pacman.logic.Game;
import pacman.logic.IUpdatableFrame;

@SuppressWarnings( "serial" )
public class GameFrame extends JFrame implements IUpdatableFrame {
	private Game game;
	
	private Timer mapRenderer;
	
	private PacmanController pacmanController;
	
	private MazeView mazeView;
	
	private StatusLabel statusLabel;
	
	public GameFrame( Game g ) {
		game = g;
		
		setTitle( "Pac-Man" );
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		setResizable( false );
		
		game.setFrameUpdater( this );
	}
	
	public synchronized void startNewLevel() {
		if ( mapRenderer != null && mapRenderer.isRunning() ) {
			mapRenderer.stop();
		}
		
		if ( mazeView != null ) {
			mazeView.removeAll();
			remove( mazeView );
		}
		
		if ( pacmanController != null ) {
			removeKeyListener( pacmanController );
		}
		
		if ( statusLabel != null ) {
			remove( statusLabel );
		}
		
		mazeView = new MazeView( game.getCurrentLevel(), game.getCurrentMapImageSource() );
		add( mazeView, BorderLayout.NORTH );
		
		statusLabel = new StatusLabel( game.getPlayer() );
		add( statusLabel, BorderLayout.SOUTH );
		
		mapRenderer = new Timer( 50, new ViewController( this, mazeView.getEntityViews(), statusLabel ) );
		mapRenderer.setRepeats( true );
		mapRenderer.start();
		
		pacmanController = new PacmanController( game.getPlayer() );
		addKeyListener( pacmanController );
		
		pack();
		setLocationRelativeTo( null );
	}

	@Override
	public synchronized void endGame() {
		if ( mapRenderer != null && mapRenderer.isRunning() ) {
			mapRenderer.stop();
		}
		
		if ( mazeView != null ) {
			mazeView.removeAll();
			remove( mazeView );
		}
		
		if ( pacmanController != null ) {
			removeKeyListener( pacmanController );
		}
		
		if ( statusLabel != null ) {
			remove( statusLabel );
		}
		
		dispose();
		MenuFrame menuFrame = new MenuFrame( game.getScoreboardSource() );
		menuFrame.setVisible( true );
	}
}
