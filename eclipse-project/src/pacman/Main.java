package pacman;

import pacman.filehandling.scoreboard.ScoreboardSource;
import pacman.graphics.menu.MenuFrame;

public class Main {
	public static void main( String[] args ) {
		ScoreboardSource scoreboardSource = new ScoreboardSource( "scoreboard.txt" );
		MenuFrame menuFrame = new MenuFrame( scoreboardSource );
		menuFrame.setVisible( true );
	}
}
