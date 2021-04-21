package pacman.graphics.menu.buttonListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import pacman.graphics.game.GameFrame;
import pacman.graphics.menu.MenuFrame;
import pacman.logic.Game;
import pacman.logic.scoreboard.IScoreboardSource;

public class ButtonStartGameListener implements ActionListener {
	private JTextField textFieldNickname;
	
	private MenuFrame menuFrame;
	
	private IScoreboardSource scoreboardSource;
	
	public ButtonStartGameListener( MenuFrame mf, IScoreboardSource iscbs, JTextField jtf ) {
		textFieldNickname = jtf;
		menuFrame = mf;
		scoreboardSource = iscbs;
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		menuFrame.dispose();
		Game game = new Game( textFieldNickname.getText(), scoreboardSource );
		GameFrame gameFrame = new GameFrame( game );
		gameFrame.setVisible( true );
		game.startNewLevel();
	}
}
