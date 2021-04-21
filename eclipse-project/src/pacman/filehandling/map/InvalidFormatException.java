package pacman.filehandling.map;

@SuppressWarnings( "serial" )
public class InvalidFormatException extends MapLoaderException {
	public InvalidFormatException( String error ) {
		super( error );
	}

}
