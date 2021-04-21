package pacman.filehandling.map;

@SuppressWarnings( "serial" )
public class ImageNotFoundException extends MapLoaderException {
	public ImageNotFoundException( String error ) {
		super(error);
	}
}
