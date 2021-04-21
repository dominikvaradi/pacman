package pacman.logic.scoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scoreboard {
	private List<ScoreboardElement> elements;
	
	public Scoreboard() {
		elements = new ArrayList<ScoreboardElement>();
	}
	
	public void addElement( ScoreboardElement e ) {
		elements.add( e );
	}
	
	public List<ScoreboardElement> getElements() {
		return elements;
	}
	
	public List<ScoreboardElement> getTopTenElement() {
		List<ScoreboardElement> sorted = new ArrayList<ScoreboardElement>( elements );
		sorted.sort( ( ScoreboardElement e1, ScoreboardElement e2 ) -> (int)( e1.getPoints() - e2.getPoints() ) );
		Collections.reverse( sorted );
		
		List<ScoreboardElement> topTen = new ArrayList<ScoreboardElement>();
		for( int i = 0; ( i < 10 ) && ( i < sorted.size() ); ++i ) {
			topTen.add( sorted.get( i ) );
		}
		
		return topTen;
	}
}
