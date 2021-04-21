package pacman.logic.scoreboard;

import java.util.List;

public interface IScoreboardSource {
	public void addElement( ScoreboardElement e );
	
	public List<ScoreboardElement> getTopTenElement();
}
