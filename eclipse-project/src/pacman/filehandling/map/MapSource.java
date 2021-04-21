package pacman.filehandling.map;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import javax.imageio.ImageIO;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import pacman.graphics.game.IMapImageSource;
import pacman.logic.*;
import pacman.logic.entity.Pellet;
import pacman.logic.entity.PowerPellet;
import pacman.logic.ghost.GhostName;

public class MapSource implements IMapSource, IMapImageSource {
	private List<List<Field>> field2DArray;
	
	private List<Pellet> pelletArray;
	
	private Map<GhostName, BoardCoordinate> scatterCoordinate;
	
	private Field pacmanSpawn;
	
	private List<Field> ghostHouseFields;
	
	private List<Field> gateFields;
	
	private int rows;
	
	private int columns;
	
	private BufferedImage mapImage;
	
	public MapSource( String mapName ) {
		field2DArray = new ArrayList<List<Field>>();
		pelletArray = new LinkedList<Pellet>();
		scatterCoordinate = new HashMap<GhostName, BoardCoordinate>();
		ghostHouseFields = new ArrayList<Field>();
		gateFields = new ArrayList<Field>();
		
		columns = -1;
		rows = -1;
		
		loadFromXML( mapName + ".xml" );
		loadImageFile( mapName + ".png" );
	}
	
	public List<List<Field>> getFields() {
		return field2DArray;
	}
	
	public List<Pellet> getPellets() {
		return pelletArray;
	}
	
	public Map<GhostName, BoardCoordinate> getScatterCoordinates() {
		return scatterCoordinate;
	}
	
	public Field getPacmanSpawn() {
		return pacmanSpawn;
	}
	
	public List<Field> getGhostHouseFields() {
		return ghostHouseFields;
	}
	
	public List<Field> getGateFields() {
		return gateFields;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public Field getFieldByBoardCoordinate( BoardCoordinate c ) {
		if ( c.getX() < 0 || c.getX() >= columns || c.getY() < 0 || c.getY() >= rows ) return null;
		
		return field2DArray.get( c.getY() ).get( c.getX() ); 
	}
	
	public BoardCoordinate getBoardCoordinateOfField( Field f ) {
		if ( f != null ) {
			for ( int y = 0; y < rows; ++y ) {
				for ( int x = 0; x < columns; ++x ) {
					if ( field2DArray.get( y ).get( x ) == f ) {
						return new BoardCoordinate( x, y );
					}
				}
			}
		}
		
		return null;
	}

	private void loadFromXML( String fileName ) {
		try {
			InputStreamReader isr = new InputStreamReader( getClass().getClassLoader().getResourceAsStream( fileName ) );
			BufferedReader br = new BufferedReader( isr );
			
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build( br );
			Element rootRow = document.getRootElement();
			
			if ( rootRow == null || !rootRow.getName().equals( "rows" ) ) throw new InvalidFormatException( "Hibás map-formátum: Hibás gyökér-elem." );
			
			Map<Field, List<Direction>> neighborsOfFields = new HashMap<Field, List<Direction>>();
			List<Element> rowList = rootRow.getChildren();
			rows = rowList.size();
			
			for ( int y = 0; y < rowList.size(); ++y ) {
				Element row = rowList.get( y );
				
				if ( row == null || !row.getName().equals( "row" ) ) throw new InvalidFormatException( "Hibás map-formátum: Hibás sor." );
				
				List<Field> fieldsInRow = new ArrayList<Field>();
				List<Element> columnList = row.getChildren();
				
				if ( columnList == null ) throw new InvalidFormatException( "Hibás map-formátum: Nincsenek oszlopai a sornak." );
				
				if ( columns == -1 ) {
					columns = columnList.size();
				}
				
				if ( columns != columnList.size() ) throw new InvalidFormatException( "Hibás map-formátum: Nem ugyanolyan hosszú mindegyik sor." );
				
				for ( int x = 0; x < columnList.size(); ++x ) {
					Element column = columnList.get( x );
					Attribute type = column.getAttribute( "type" );
					
					if ( !column.getName().equals( "column" ) || type == null ) throw new InvalidFormatException( "Hibás map-formátum: Hibás oszlop." );

					if ( type.getValue().equals( "wall" ) || type.getValue().equals( "empty" ) || type.getValue().equals( "scatterCoordinate" ) ) {
						fieldsInRow.add( null );
						
						if ( type.getValue().equals( "scatterCoordinate" ) ) {
							Attribute ghostName = column.getAttribute( "ghost" );
							
							if ( ghostName == null ) throw new InvalidFormatException( "Hibás map-formátum: Nincsen megadva az egyik Scatter-koordinátához tartozó szellem neve." );
							
							scatterCoordinate.put( GhostName.valueOf( ghostName.getValue().toUpperCase() ), new BoardCoordinate( x, y ) );
						}
					} else if ( type.getValue().equals( "field" ) || type.getValue().equals( "ghostHouse" ) || type.getValue().equals( "pacmanSpawn" ) || type.getValue().equals( "gate" ) ) {
						Field newField = new Field( null );
						fieldsInRow.add( newField );
						Element pellet = column.getChild( "pellet" );
						List<Element> neighbors = column.getChildren( "neighbor" );
						
						if ( neighbors == null || neighbors.isEmpty() ) throw new InvalidFormatException( "Hibás map-formátum: Az egyik mezõnek nincsenek szomszédai." );
						
						if ( type.getValue().equals( "ghostHouse" ) ) {
							ghostHouseFields.add( newField );
						} else if ( type.getValue().equals( "pacmanSpawn" ) ) {
							pacmanSpawn = newField;
						} else if ( type.getValue().equals( "gate" ) ) {
							gateFields.add( newField ); 
						} else { // Csak akkor lehet rajta pellet, ha nem szellem-ház, pacman kezdõpontja vagy kapu a mezõ.
							if ( pellet != null ) {
								Attribute pelletType = pellet.getAttribute( "type" );
								
								if ( pelletType == null ) throw new InvalidFormatException( "Hibás map-formátum: Az egyik pellet típusa hibás." );
								
								if ( pelletType.getValue().equals( "normal" ) ) {
									pelletArray.add( new Pellet( newField ) );
								} else if ( pelletType.getValue().equals( "powerPellet" ) ) {
									pelletArray.add( new PowerPellet( newField ) );
								} else {
									throw new InvalidFormatException( "Hibás map-formátum: Az egyik pellet típusa hibás." );
								}
							}
						}
						
						List<Direction> neighborsOfField = new LinkedList<Direction>();
						for ( Element n : neighbors ) {
							Attribute direction = n.getAttribute( "direction" );
							
							if ( direction == null ) throw new InvalidFormatException( "Hibás map-formátum: Az egyik mezõnek nincs meg adva, hogy melyik irányban van szomszédja." );
							
							neighborsOfField.add( Direction.valueOf( direction.getValue().toUpperCase() ) );
						}
						neighborsOfFields.put( newField , neighborsOfField );
					} else {
						throw new InvalidFormatException( "Hibás map-formátum." );
					}
				}
				field2DArray.add( fieldsInRow );
			}
			
			br.close();
			
			if ( field2DArray.isEmpty() ) {
				throw new InvalidFormatException( "Hibás map-formátum: Nincsenek mezõk." );
			}
			
			if ( ghostHouseFields.size() < 4 ) {
				throw new InvalidFormatException( "Hibás map-formátum: Legalább négy mezõnek szellem-háznak kell lennie." );
			}
			
			if ( gateFields.isEmpty() ) {
				throw new InvalidFormatException( "Hibás map-formátum: Legalább egy mezõnek kapunak kell lennie a szellem-házba." );
			}
			
			for ( GhostName gn : GhostName.values() ) {
				if ( !scatterCoordinate.containsKey(gn) ) {
					throw new InvalidFormatException( "Hibás map-formátum: Az egyik szellemnek nincsen Scatter-koordinátája." );
				}
			}
			
			setFieldsNeighbors( field2DArray, neighborsOfFields );
		} catch( JDOMException | IOException | InvalidFormatException e ) {
			System.out.println( "Hiba történt a pálya beolvasása során, a játék nem indítható!" );
			System.out.println( "\t" + e.getMessage() );
		}
	}
	
	private void setFieldsNeighbors( List<List<Field>> fields, Map<Field, List<Direction>> neighborsOfFields ) {
		for ( int y = 0; y < rows; ++y ) {
			for ( int x = 0; x < columns; ++x ) {
				Field field = fields.get( y ).get( x );
				if ( field == null ) continue;
				
				List<Direction> neighborDirections = neighborsOfFields.get( field );
				
				for ( Direction d : Direction.values() ) {
					if ( !neighborDirections.contains( d ) ) continue;
					
					BoardCoordinate neighborCoords = new BoardCoordinate( x, y ).add( d.toBoardCoordinate() );
					
					// Átjárók miatt
					if ( neighborCoords.getX() == -1 ) {
						field.setNeighbor( d, fields.get( y ).get( columns - 1 ) );
					} else if ( neighborCoords.getX() == columns ) {
						field.setNeighbor( d, fields.get( y ).get( 0 ) );
					} else if ( neighborCoords.getY() == -1 ) {
						field.setNeighbor( d, fields.get( rows ).get( x ) );
					} else if ( neighborCoords.getY() == rows ) {
						field.setNeighbor( d, fields.get( 0 ).get( x ) );
					} else {
						field.setNeighbor( d, fields.get( neighborCoords.getY() ).get( neighborCoords.getX() ) );
					}
				}
			}
		}
	}
	
	public void loadImageFile( String imageFileName ) {
		try {
			mapImage = ImageIO.read( getClass().getClassLoader().getResource( imageFileName ) );
		} catch ( IOException e ) {
			System.out.println( "Hiba történt a pálya beolvasása során, a játék nem indítható!" );
			System.out.println( "\t" + e.getMessage() );
		}
	}
	
	public BufferedImage getMapImage() {
		return mapImage;
	}
}
