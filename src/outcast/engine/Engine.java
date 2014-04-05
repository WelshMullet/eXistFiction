package outcast.engine;



import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Engine {
	private static Engine instance = null;
	private static ExistManager manager = null;
	private static Resource location = null;
	private static Document loc = null;
	
	protected Engine(){
		//defeats instantiation
	}

	public static Engine getInstance(){
		if (instance == null) {
			instance = new Engine();
			manager = ExistManager.getInstance();
			setLocation(manager.getResource("db/locations", "start.xml"));
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = null;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				setLoc(dBuilder.parse(new InputSource(new ByteArrayInputStream(location.getContent().toString().getBytes("utf-8")))));
			} catch (SAXException | IOException | XMLDBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			loc.getDocumentElement().normalize();
			
			//manager.getResource("xmldb:exist:///db/player/", "data");
			@SuppressWarnings("unused")
			GUI gui = new GUI();
		}
		return instance;
	}
	
	public void moveTo(String locName){
		setLocation(manager.getResource("db/locations/", locName));
	}

	public void parseInput(String string) {
		String[] in = string.split(" ");
		
		switch(in[0].toLowerCase()){
		case "examine" :
			//Switch on in[0] vs possible choices
			break;
		case "go" :
			//Switch on in[0] vs possible choices
			break;
		case "pick":
			if (in[1] != "up"){
				break;
			}
		case "take":
			//Switch on in[0] vs possible choices
			break;
			
		
		}
	}

	public static Resource getLocation() {
		return location;
	}

	public static void setLocation(Resource location) {
		Engine.location = location;
	}

	public static Document getLoc() {
		return loc;
	}

	public static void setLoc(Document loc) {
		Engine.loc = loc;
	}
	
	
	
}
