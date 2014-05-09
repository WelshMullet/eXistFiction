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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Engine {
	private static Engine instance = null;
	private static ExistManager manager = null;
	private static Resource location = null;
	private static Document loc = null;
	private static Document play = null;
	private static Resource player = null;
	
	protected Engine(){
		//defeats instantiation
	}

	public static Engine getInstance(){
		if (instance == null) {
			instance = new Engine();
			manager = ExistManager.getInstance();
			
			manager.load();
			
			setLocation(manager.getResource("db/locations", "start.xml"));
			setPlayer(manager.getResource("db/player", "data.xml"));
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
				setPlay(dBuilder.parse(new InputSource(new ByteArrayInputStream(player.getContent().toString().getBytes("utf-8")))));
			} catch (SAXException | IOException | XMLDBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			loc.getDocumentElement().normalize();
			play.getDocumentElement().normalize();
			
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
		String output = "I do not understand" + string + "\n";
		NodeList list = null;
		Node node;
		
		switch(in[0].toLowerCase()){
		case "examine" : case "oggle" : case "observe" :
			//Switch on in[0] vs possible choices
			if(in.length != 1){
				switch(in[1].toLowerCase()){
				case "room" : {
					list =getLoc().getElementsByTagName("description");
					output = (list.item(0).getFirstChild().getNodeValue() + "\n");
					
					//Output what is to the cardinal direction? Maybe add description tags to links, will require re-write of some other xml code
					//Output what items are here
					
					break;
				}
				}
			}
			
			break;
		case "go" : case "exit" : case "proceed" :
			//Switch on in[0] vs possible choices
			if(in.length != 1){
				list =getLoc().getElementsByTagName("links");
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
				output = "Go where?\n";
			}

			break;
		case "pick":
			if(in.length != 1){
				if(in[1] == "up"){
					if(in.length != 2){
						list =getLoc().getElementsByTagName("inventory");
						
					}
					else{
						output = "Pick up what?\n";
					}
				}
			}
			
				
				
				
				break;
			
		case "take":
			//Switch on in[0] vs possible choices
			
			break;
		case "drop":
			
			break;
		default :
			return ("I do not understand " + string + "\n");
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
	
	public int moveItem(String invOne, String invTwo, String item){
		//Moves item from xml file at invOne to xml file at invTwo
		//returns 0 if success, 1 if not, should not return 1 in production, used to debug xml errors
		String uri1 = "db/locations", uri2 = "db/locations";
		Resource loc1, loc2;
		Document first = null, second = null;
		
		if(invOne == "player.xml"){
			uri1 = "db/player";
		}
		if(invTwo == "player.xml"){
			uri2 = "db/player";
		}
		
		loc1 = manager.getResource(uri1, invOne);
		loc2 = manager.getResource(uri2, invTwo);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			first = dBuilder.parse(new InputSource(new ByteArrayInputStream(loc1.getContent().toString().getBytes("utf-8"))));
			second = dBuilder.parse(new InputSource(new ByteArrayInputStream(loc2.getContent().toString().getBytes("utf-8"))));
		} catch (SAXException | IOException | XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		first.getDocumentElement().normalize();
		second.getDocumentElement().normalize();
		
		NodeList items = first.getElementsByTagName("inventory");
		
		
		
		
		
		return 0;
	}
	
	public String getHelp(){
		Resource res = manager.getResource("db", "help.xml");
		Document doc = null;
		String result = null;
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(res.getContent().toString().getBytes("utf-8"))));
		} catch (SAXException | IOException | XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		doc.getDocumentElement().normalize();
		
		NodeList nodes = doc.getElementsByTagName("help");
		
		try{
			result = nodes.item(0).getFirstChild().getNodeValue();
		}
		catch(Exception e){
			
		}
		
		return result;
	}

	public static Resource getPlayer() {
		return player;
	}

	public static void setPlayer(Resource player) {
		Engine.player = player;
	}

	public static Document getPlay() {
		return play;
	}

	public static void setPlay(Document play) {
		Engine.play = play;
	}
	
	public static String[] getInventory(){
		String[] list = null;
		
		NodeList nodes = getPlay().getElementsByTagName("*");
		
		int i = 0;
		while( i  < nodes.getLength()){
			list[i] = nodes.item(i).getFirstChild().getNodeValue();
		}
		
		return list;
	}
}
