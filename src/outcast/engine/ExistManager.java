package outcast.engine;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;

import org.exist.backup.*;
import org.exist.backup.restore.listener.AbstractRestoreListener;
import org.exist.backup.restore.listener.DefaultRestoreListener;
import org.exist.backup.restore.listener.RestoreListener;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.exist.storage.serializers.EXistOutputKeys;
import org.exist.xmldb.DatabaseInstanceManager;
import org.exist.xmldb.XmldbURI;


public class ExistManager {
	private static ExistManager instance = null; //classic singleton setup, we only want one db manager
	private boolean isRunning = false;
	protected ExistManager(){
		//defeats instantiation
	}
	
	protected static String driver = "org.exist.xmldb.DatabaseImpl";

	protected static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";

	public static ExistManager getInstance(){
		if (instance == null) {
			instance = new ExistManager();
		}
		return instance;
	}
	
	public int start() {

		// initialize database drivers
		Class<?> cl;
		try {
			cl = Class.forName(driver);
			Database database = (Database) cl.newInstance();
	        
	        // Set to TRUE to connectect over HTTPS-uri 
	        // like 'xmldb:exist://localhost:8443/exist/xmlrpc' (port changed 8080->8443)
	        database.setProperty("ssl-enable", "false");
	        
			DatabaseManager.registerDatabase(database);
			setRunning(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}
	
	public int save(){
		JFileChooser fc = new JFileChooser();
		
		if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

		Backup backup = new Backup("admin", "", file.getAbsolutePath() + ".zip", XmldbURI.create(URI+"/db"));
		try {
			backup.backup(false, null);
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return 0;
	}
	
	public int load(){
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter(null, "zip"));
		
		
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            Restore restore = new Restore();
            RestoreListener listener = new DefaultRestoreListener();
            try {
				restore.restore(listener, "admin", "", "", file, URI);
			} catch (XMLDBException | IOException
					| SAXException | ParserConfigurationException
					| URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return 0;
	}
    
    public Resource getResource(String collection, String resource){
    	if(collection == null){
    		collection = ""; //default path
    	}
    	//XMLResource res = null;
    	Resource res = null;
    	
    	try{
    	Collection col = DatabaseManager.getCollection(URI + collection);
		col.setProperty(OutputKeys.INDENT, "yes");
		col.setProperty(EXistOutputKeys.EXPAND_XINCLUDES, "no");
        col.setProperty(EXistOutputKeys.PROCESS_XSL_PI, "yes");
		//res = (XMLResource)col.getResource(resource);
		res = col.getResource(resource);
    	}
    	catch(Exception e){
    		
    	}
    	return res;

    }
    
    public int storeResource(String collection, Resource resource){
    	try{
    	Collection col = DatabaseManager.getCollection(URI + collection);
		col.setProperty(OutputKeys.INDENT, "yes");
		col.setProperty(EXistOutputKeys.EXPAND_XINCLUDES, "no");
        col.setProperty(EXistOutputKeys.PROCESS_XSL_PI, "yes");
		col.storeResource(resource);
    	}
    	catch(Exception e){
    		//collection not found or resource not stored
    		return 1;
    	}
    	return 0;
    }
    
    public int shutdown() {
    // shut down the database
    Collection col;
	try {
		col = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/", "admin", "");
	    DatabaseInstanceManager manager = (DatabaseInstanceManager) 
	    col.getService("DatabaseInstanceManager", "1.0");
	    manager.shutdown();
	    setRunning(false);
	    return 0;
	} catch (XMLDBException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return 1;
	}

    }

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}

