package pacman.graphics.menu.buttonListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pacman.graphics.menu.MenuFrame;
import pacman.graphics.menu.ScoreboardPanel;
import pacman.logic.scoreboard.IScoreboardSource;

public class ButtonScoreboardListener implements ActionListener {
	private MenuFrame menuFrame;
	
	private IScoreboardSource scoreboardSource;
	
	public ButtonScoreboardListener( MenuFrame mf, IScoreboardSource iscbs ) {
		menuFrame = mf;
		scoreboardSource = iscbs;
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		menuFrame.setMenuPanel( new ScoreboardPanel( menuFrame, scoreboardSource ) );
	}
}
