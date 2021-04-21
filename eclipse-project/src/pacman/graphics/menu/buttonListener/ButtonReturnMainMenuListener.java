package pacman.graphics.menu.buttonListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pacman.graphics.menu.MainPanel;
import pacman.graphics.menu.MenuFrame;
import pacman.logic.scoreboard.IScoreboardSource;

public class ButtonReturnMainMenuListener implements ActionListener {
	private MenuFrame menuFrame;
	
	private IScoreboardSource scoreboardSource;
	
	public ButtonReturnMainMenuListener( MenuFrame mf, IScoreboardSource iscbs ) {
		menuFrame = mf;
		scoreboardSource = iscbs;
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		menuFrame.setMenuPanel( new MainPanel( menuFrame, scoreboardSource ) );
	}
}
