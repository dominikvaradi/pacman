package pacman.graphics.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pacman.graphics.menu.buttonListener.ButtonReturnMainMenuListener;
import pacman.logic.scoreboard.IScoreboardSource;
import pacman.logic.scoreboard.ScoreboardElement;

@SuppressWarnings( "serial" )
public class ScoreboardPanel extends JPanel {
	public ScoreboardPanel( MenuFrame menuFrame, IScoreboardSource scoreboardSource ) {
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		setAlignmentX( CENTER_ALIGNMENT );
		setBackground( Color.BLACK );
		
		List<ScoreboardElement> topTen = scoreboardSource.getTopTenElement();
		for ( int i = 0; i < topTen.size(); ++i ) {
			ScoreboardElement scbElement = topTen.get( i );
			
			JLabel labelElement = new JLabel( i + 1 + ". " + scbElement.getNickname() + ": " + scbElement.getPoints() );
			labelElement.setForeground( Color.ORANGE );
			labelElement.setFont( new Font( "sans-serif", Font.BOLD, 14 ) );
			labelElement.setAlignmentX( getAlignmentX() );
			add( labelElement );
			add( Box.createRigidArea( new Dimension( 0, 10 ) ) );
		}
		
		add( Box.createRigidArea( new Dimension( 0, 20 ) ) );
		JButton buttonReturnMainMenu = new JButton( "Return to Main menu" );
		buttonReturnMainMenu.setAlignmentX( getAlignmentX() );
		buttonReturnMainMenu.addActionListener( new ButtonReturnMainMenuListener( menuFrame, scoreboardSource ) );
		add( buttonReturnMainMenu );
		add( Box.createRigidArea( new Dimension( 0, 10 ) ) );
	}
}