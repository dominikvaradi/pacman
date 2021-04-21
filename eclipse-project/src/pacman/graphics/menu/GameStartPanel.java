package pacman.graphics.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pacman.graphics.menu.buttonListener.ButtonReturnMainMenuListener;
import pacman.graphics.menu.buttonListener.ButtonStartGameListener;
import pacman.logic.scoreboard.IScoreboardSource;

@SuppressWarnings( "serial" )
public class GameStartPanel extends JPanel {
	public GameStartPanel( MenuFrame menuFrame, IScoreboardSource scoreboardSource ) {
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		setAlignmentX( CENTER_ALIGNMENT );
		setBackground( Color.BLACK );
		
		JLabel labelNickname = new JLabel( "Nickname" );
		labelNickname.setForeground( Color.WHITE );
		labelNickname.setFont( new Font( "sans-serif", Font.BOLD, 12 ) );
		labelNickname.setAlignmentX( getAlignmentX() );
		add( labelNickname );
		add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
		
		JTextField textFieldNickname = new JTextField( "New Player" );
		textFieldNickname.setMaximumSize( new Dimension( 150, 40 ) );
		textFieldNickname.setHorizontalAlignment( JTextField.CENTER );
		textFieldNickname.setAlignmentX( getAlignmentX() );
		add( textFieldNickname );
		
		add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
		JButton buttonStartGame = new JButton( "Start game" );
		buttonStartGame.setAlignmentX( getAlignmentX() );
		buttonStartGame.addActionListener( new ButtonStartGameListener( menuFrame, scoreboardSource, textFieldNickname ) );
		add( buttonStartGame );
		
		add( Box.createRigidArea( new Dimension( 0, 20 ) ) );
		JButton buttonReturnMainMenu = new JButton( "Return to Main menu" );
		buttonReturnMainMenu.setAlignmentX( getAlignmentX() );
		buttonReturnMainMenu.addActionListener( new ButtonReturnMainMenuListener( menuFrame, scoreboardSource ) );
		add( buttonReturnMainMenu );
		add( Box.createRigidArea( new Dimension( 0, 10 ) ) );
	}
}
