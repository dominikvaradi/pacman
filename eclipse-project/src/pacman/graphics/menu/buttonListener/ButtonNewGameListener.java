package pacman.graphics.menu.buttonListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pacman.graphics.menu.GameStartPanel;
import pacman.graphics.menu.MenuFrame;
import pacman.logic.scoreboard.IScoreboardSource;

public class ButtonNewGameListener implements ActionListener {
	private MenuFrame menuFrame;
	
	private IScoreboardSource scoreboardSource;
	
	public ButtonNewGameListener( MenuFrame mf, IScoreboardSource iscbs ) {
		menuFrame = mf;
		scoreboardSource = iscbs;
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		menuFrame.setMenuPanel( new GameStartPanel( menuFrame, scoreboardSource ) );
	}
}
