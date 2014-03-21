package outcast.engine;

import org.xmldb.api.base.Resource;

public class Engine {
	private static Engine instance = null;
	private static ExistManager manager = null;
	private static Resource location = null;
	
	protected Engine(){
		//defeats instantiation
	}

	public static Engine getInstance(){
		if (instance == null) {
			instance = new Engine();
			manager = ExistManager.getInstance();
			setLocation(manager.getResource("db/locations", "start.xml"));
			Resource res = null;
			
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
	
	
}
