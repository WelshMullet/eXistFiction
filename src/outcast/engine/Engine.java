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


		}
		return instance;
	}
	
	public void moveTo(String locName){
		setLocation(manager.getResource("db/locations", locName));
		
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
		
	}

	public String parseInput(String string) {
		String[] in = string.split(" ");
		String output = "I do not understand" + string;
		
		switch(in[0].toLowerCase()){
		case "examine" :
			//Switch on in[0] vs possible choices
			break;
		case "go" :
			//Switch on in[0] vs possible choices
			if(in.length != 1){
				NodeList list =getLoc().getElementsByTagName("links");
				Node node;
				//NodeList links = list.item(0).getChildNodes();

				Node location;
				switch(in[1].toLowerCase()){
				case "north" : {
					node = getLoc().getElementsByTagName("north").item(0);
					if(node != null){
						location = node.getFirstChild();
						if(location != null){
							moveTo(location.getNodeValue());
							list =getLoc().getElementsByTagName("description");
							output = (list.item(0).getFirstChild().getNodeValue() + "\n");
						}
						else{
							output = "You cannot go north \n";
						}
					}
					break;
				}

				case "south" : {
					node = getLoc().getElementsByTagName("south").item(0);
					if(node != null){
						location = node.getFirstChild();
						if(location != null){
							moveTo(location.getNodeValue());
							list =getLoc().getElementsByTagName("description");
							output = (list.item(0).getFirstChild().getNodeValue() + "\n");
						}
						else{
							output = "You cannot go south \n";
						}
					}
					break;
				}
				
				case "east" : {
					node = getLoc().getElementsByTagName("east").item(0);
					if(node != null){
						location = node.getFirstChild();
						if(location != null){
							moveTo(location.getNodeValue());
							list =getLoc().getElementsByTagName("description");
							output = (list.item(0).getFirstChild().getNodeValue() + "\n");
						}
						else{
							output = "You cannot go east \n";
						}
					}
					break;
				}
					
				case "west" : {
					node = getLoc().getElementsByTagName("west").item(0);
					if(node != null){
						location = node.getFirstChild();
						if(location != null){
							moveTo(location.getNodeValue());
							list =getLoc().getElementsByTagName("description");
							output = (list.item(0).getFirstChild().getNodeValue() + "\n");
						}
						else{
							output = "You cannot go west \n";
						}
					}
					break;
				}
					
				case "up" : {
					node = getLoc().getElementsByTagName("up").item(0);
					if(node != null){
						location = node.getFirstChild();
						if(location != null){
							moveTo(location.getNodeValue());
							list =getLoc().getElementsByTagName("description");
							output = (list.item(0).getFirstChild().getNodeValue() + "\n");
						}
						else{
							output = "You cannot go up \n";
						}
					}
					break;
				}
					
				case "down" : {
					node = getLoc().getElementsByTagName("down").item(0);
					if(node != null){
						location = node.getFirstChild();
						if(location != null){
							moveTo(location.getNodeValue());
							list =getLoc().getElementsByTagName("description");
							output = (list.item(0).getFirstChild().getNodeValue() + "\n");
						}
						else{
							output = "You cannot go down \n";
						}
					}
					break;
				}
					
				}
				
			}
			else{
				output = "Go where?";
			}

			break;
		case "pick":
			if (in[1] != "up"){
				break;
			}
		case "take":
			//Switch on in[0] vs possible choices
			break;
		default :
			return ("I do not understand " + string);
		}
		return (output);
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
