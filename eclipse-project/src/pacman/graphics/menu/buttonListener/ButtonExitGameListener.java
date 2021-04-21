package pacman.graphics.menu.buttonListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pacman.graphics.menu.MenuFrame;

public class ButtonExitGameListener implements ActionListener {
	private MenuFrame menuFrame;
	
	public ButtonExitGameListener( MenuFrame mf ) {
		menuFrame = mf;
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		menuFrame.dispose();
	}
}
