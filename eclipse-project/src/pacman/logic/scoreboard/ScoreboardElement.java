package pacman.logic.scoreboard;

public class ScoreboardElement {
	private String nickname;
	
	private long points;
	
	public ScoreboardElement( String n, long p ) {
		nickname = n;
		points = p;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public long getPoints() {
		return points;
	}
}
