package pacman.graphics.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import pacman.graphics.game.entityview.EntityView;
import pacman.graphics.game.entityview.FruitView;
import pacman.graphics.game.entityview.GhostView;
import pacman.graphics.game.entityview.PacmanView;
import pacman.graphics.game.entityview.PelletView;
import pacman.logic.BoardCoordinate;
import pacman.logic.IUpdatableView;
import pacman.logic.Maze;
import pacman.logic.entity.Fruit;
import pacman.logic.entity.Ghost;
import pacman.logic.entity.Pellet;

@SuppressWarnings( "serial" )
public class MazeView extends JLabel implements IUpdatableView {
	private Maze maze;
	
	private IMapImageSource mapSource;
	
	private double fieldSize;
	
	private List<EntityView> entityViews;
	
	public MazeView( Maze m, IMapImageSource ims ) {
		maze = m;
		mapSource = ims;
		
		maze.setViewUpdater( this );
		
		BufferedImage mapImage = mapSource.getMapImage();
		setIcon( new ImageIcon( mapImage ) );
		fieldSize = mapImage.getHeight() / (double)maze.getRows();
		
		entityViews = new CopyOnWriteArrayList<EntityView>();
		
		for( Pellet pellet : maze.getPellets() ) {
			entityViews.add( new PelletView( pellet, fieldSize, entityViews ) );
		}
		
		BoardCoordinate southEastCoordinate = new BoardCoordinate( maze.getColumns() - 1, maze.getRows() - 1 );
		
		entityViews.add( new PacmanView( maze.getPacman(), fieldSize, southEastCoordinate ) );
		
		for ( Ghost ghost : maze.getGhosts() ) {
			entityViews.add( new GhostView( ghost, fieldSize, southEastCoordinate ) );
		}
	}
	
	public void paintComponent( Graphics g ) {
		super.paintComponent( g );
		draw( (Graphics2D)g );
	}
	
	public void draw( Graphics2D g2d ) {
		for ( EntityView entityView : entityViews ) {
			entityView.draw( g2d );
		}
	}
	
	public List<EntityView> getEntityViews() {
		return entityViews;
	}
	
	public synchronized void addFruit() {
		for( Fruit fruit : maze.getFruits() ) {
			entityViews.add( 0, new FruitView( fruit, fieldSize, entityViews ) );
		}
	}
}
