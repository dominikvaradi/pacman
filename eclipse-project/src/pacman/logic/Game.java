package pacman.logic;

import pacman.filehandling.map.MapSource;
import pacman.graphics.game.IMapImageSource;
import pacman.logic.entity.Pacman;
import pacman.logic.scoreboard.IScoreboardSource;
import pacman.logic.scoreboard.ScoreboardElement;

public class Game {
	private Maze currentLevel;
	
	private MapSource currentMapSource;
	
	private Pacman player;
	
	private IUpdatableFrame frameUpdater;
	
	private String playerNickname;
	
	private IScoreboardSource scoreboardSource;
	
	public Game( String n, IScoreboardSource isbc ) {
		player = new Pacman( null );
		currentLevel = null;
		playerNickname = n;
		scoreboardSource  = isbc;
	}
	
	public synchronized void startNewLevel() {
		MapSource mapSource = new MapSource( "map1" );
		Maze level = new Maze( mapSource, this, player );
		
		currentLevel = level;
		currentMapSource = mapSource;
		
		frameUpdater.startNewLevel();
	}
	
	public synchronized void endGame() {
		scoreboardSource.addElement( new ScoreboardElement( playerNickname, player.getPoints() ) );
		frameUpdater.endGame();
	}
	
	public synchronized Maze getCurrentLevel() {
		return currentLevel;
	}
	
	public synchronized IMapImageSource getCurrentMapImageSource() {
		return currentMapSource;
	}
	
	public synchronized Pacman getPlayer() {
		return player;
	}
	
	public synchronized IScoreboardSource getScoreboardSource() {
		return scoreboardSource;
	}
	
	public synchronized void setFrameUpdater( IUpdatableFrame iuf ) {
		frameUpdater = iuf;
	}
}
