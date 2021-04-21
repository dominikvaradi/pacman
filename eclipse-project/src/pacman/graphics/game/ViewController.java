package pacman.graphics.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import pacman.graphics.game.entityview.EntityView;

public class ViewController implements ActionListener {
	private GameFrame gameFrame;
	
	private List<EntityView> entityViews;
	
	private StatusLabel statusLabel;
	
	public ViewController( GameFrame gf, List<EntityView> ev, StatusLabel sl ) {
		gameFrame = gf;
		entityViews = ev;
		statusLabel = sl;
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		for ( EntityView entityView : entityViews ) {
			entityView.step();
		}
		
		statusLabel.update();
		
		gameFrame.validate();
		gameFrame.repaint();
	}
}
