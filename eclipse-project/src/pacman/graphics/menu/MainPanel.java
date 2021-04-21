package pacman.graphics.menu;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import pacman.graphics.menu.buttonListener.ButtonExitGameListener;
import pacman.graphics.menu.buttonListener.ButtonNewGameListener;
import pacman.graphics.menu.buttonListener.ButtonScoreboardListener;
import pacman.logic.scoreboard.IScoreboardSource;

@SuppressWarnings( "serial" )
public class MainPanel extends JPanel {
	public MainPanel( MenuFrame menuFrame, IScoreboardSource scoreboardSource ) {
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		setAlignmentX( CENTER_ALIGNMENT );
		setBackground( Color.BLACK );
		
		JButton buttonNewGame = new JButton( "New game" );
		buttonNewGame.setAlignmentX( getAlignmentX() );
		buttonNewGame.addActionListener( new ButtonNewGameListener( menuFrame, scoreboardSource ) );
		add( buttonNewGame );
		
		add( Box.createRigidArea( new Dimension( 0, 20 ) ) );
		JButton buttonScoreboard = new JButton( "Scoreboard" );
		buttonScoreboard.setAlignmentX( getAlignmentX() );
		buttonScoreboard.addActionListener( new ButtonScoreboardListener( menuFrame, scoreboardSource ) );
		add( buttonScoreboard );
		
		add( Box.createRigidArea( new Dimension( 0, 20 ) ) );
		JButton buttonExitGame = new JButton( "Exit game" );
		buttonExitGame.setAlignmentX( getAlignmentX() );
		buttonExitGame.addActionListener( new ButtonExitGameListener( menuFrame ) );
		add( buttonExitGame );
		add( Box.createRigidArea( new Dimension( 0, 10 ) ) );
	}
}
