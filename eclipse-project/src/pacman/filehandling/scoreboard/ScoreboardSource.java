package pacman.filehandling.scoreboard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import pacman.logic.scoreboard.IScoreboardSource;
import pacman.logic.scoreboard.Scoreboard;
import pacman.logic.scoreboard.ScoreboardElement;

public class ScoreboardSource implements IScoreboardSource {
	private Scoreboard scoreboard;
	
	private File file;
	
	public ScoreboardSource( String fileName ) {
		scoreboard = new Scoreboard();
		
		file = new File( fileName );
		
		try {
			file.createNewFile();
		} catch ( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		load();
	}
	
	private void load() {
		try {
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build( file );
			Element rootRow = document.getRootElement();
			
			List<Element> scoreboardElementList = rootRow.getChildren( "element" );
			
			for ( Element scoreboardElement : scoreboardElementList ) {
				Element nickname = scoreboardElement.getChild( "nickname" );
				Element points = scoreboardElement.getChild( "points" );
				
				scoreboard.addElement( new ScoreboardElement( nickname.getValue(), Long.parseLong( points.getValue() ) ) );
			}
		} catch ( JDOMException | IOException e ) {
			return;
		}
	}
	
	private void save() {
		Document document = new Document();
		document.setRootElement( new Element( "scoreboard" ) );
        
        for( ScoreboardElement scbElement : scoreboard.getElements() ){
            Element element = new Element( "element" );
            element.addContent( new Element( "nickname" ).setText( scbElement.getNickname() ) );
            element.addContent( new Element( "points" ).setText( "" + scbElement.getPoints() ) );
            
            document.getRootElement().addContent( element );
        }

		try {
			XMLOutputter xmlOutputter = new XMLOutputter( Format.getPrettyFormat() );
	        FileOutputStream fos = new FileOutputStream( file );
			xmlOutputter.output( document, fos );
		} catch ( IOException e ) {
			return;
		}
	}
	
	@Override
	public void addElement( ScoreboardElement e ) {
		scoreboard.addElement( e );
		save();
	}

	@Override
	public List<ScoreboardElement> getTopTenElement() {
		return scoreboard.getTopTenElement();
	}
}
