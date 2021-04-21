package pacman.logic;

import java.util.List;
import java.util.Map;

import pacman.logic.entity.Pellet;
import pacman.logic.ghost.GhostName;

public interface IMapSource {
	public List<List<Field>> getFields();
	
	public List<Pellet> getPellets();
	
	public Map<GhostName, BoardCoordinate> getScatterCoordinates();
	
	public Field getPacmanSpawn();
	
	public List<Field> getGhostHouseFields();
	
	public List<Field> getGateFields();
	
	public int getRows();
	
	public int getColumns();
	
	public Field getFieldByBoardCoordinate( BoardCoordinate c );
	
	public BoardCoordinate getBoardCoordinateOfField( Field f );
}
