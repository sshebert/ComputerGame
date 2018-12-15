import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Scanner;

public class ProjectXMLParser extends DefaultHandler {

    private ArrayList<Room> rooms;
    private HashMap<String, String> hmap;
    private String name, description, action, temp, north, south, east, west;
    private Room r, n, s, e, w;
    private Item i;
    private Character c;
    private Player p;
    private Scanner sc;
    private int count;
    private BST<Room> bst;
    
    @Override
    public void startDocument() throws SAXException {
	//Initialize arraylist, hashmap, bst
	rooms = new ArrayList<>();
	hmap = new HashMap<>();
	bst = new BST<>();
	//counts number of NPCs for win condition
	count = 0;
    }

    @Override
    public void startElement(String namespaceURI,
                             String localName,
                             String qName, 
                             Attributes atts) throws SAXException {
        //room condition
	if (qName.equals("room")){
	    //reading attributes
	    this.name = atts.getValue("name");
	    this.description = atts.getValue("description");
	    this.north = atts.getValue("north");
	    this.south = atts.getValue("south");
	    this.east = atts.getValue("east");
	    this.west = atts.getValue("west");
	    
	    this.r = new Room(name, description);//creates room object
	    rooms.add(r);//adds room to arraylist
	    bst.insert(r);//adds room to tree
	    
	    //adds name of current room + n,s,e,w for north, south, east, west as the key. Puts the name of the neighboring room as the associated value. 
	    hmap.put(name + "n", north);
	    hmap.put(name + "s", south);
	    hmap.put(name + "e", east);
	    hmap.put(name + "w", west);
	    }
	//item condition
	if (qName.equals("item")){
	    //reading attributes
	    this.name = atts.getValue("name");
	    this.description = atts.getValue("description");
	    this.action = atts.getValue("actions");
	    this.i = new Item(name, description);//creates item object
	    //scanner with comma delimiter to read actions
	    sc = new Scanner(action);
	    sc.useDelimiter(",");
	    //adding ItemActions to the item
	    while (sc.hasNext()){
		temp = sc.next();
		if (temp.equals("shake")){
		    this.i.addAction(Item.ItemAction.SHAKE);
		}else if (temp.equals("possess")){
		    this.i.addAction(Item.ItemAction.POSSESS);
		}else if (temp.equals("throw")){
		    this.i.addAction(Item.ItemAction.THROW);
		    }
		}
	    r.addItem(i);//adds item to the current room
	}
	//adult condition
	if (qName.equals("adult")){
	    //reading attributes
	    this.name = atts.getValue("name");
	    this.description = atts.getValue("description");
	    
	    this.c = new Adult(name, description);//creates adult object
	    //System.out.println(c.toString());
	    r.addCharacter(c);//adds character to the current room
	    c.setRoom(r);
	    count++;
	}
	//player condition
	if (qName.equals("player")){
	    //reading attributes
	    this.name = atts.getValue("name");
	    this.description = atts.getValue("description");
	    
	    this.p = new Player(name, description);//creates player object
	    //System.out.println(p.toString());
	    r.addCharacter(p);//adds player to the current room
	    p.setRoom(r);
	}
	//child condition
	if (qName.equals("child")){
	    //reading attributes
	    this.name = atts.getValue("name");
	    this.description = atts.getValue("description");
	    
	    this.c = new Child(name, description);//creates child object
	    //System.out.println(c.toString());
	    r.addCharacter(c);//adds child to the current room
	    c.setRoom(r);
	    count++;
	}
	
    }

    @Override
    public void endElement(String namespaceURI,
                           String localName,
                           String qName) throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
	//iteration through the room arraylist, there will be a key for the name of each room + n,s,e,w. The value will either be a string consisting of that rooms name or be null
	for (Room r : rooms){
	    this.north = hmap.get(r.getName() + "n");
	    this.south = hmap.get(r.getName() + "s");
	    this.east = hmap.get(r.getName() + "e");
	    this.west = hmap.get(r.getName() + "w");
	    
	    //temporary room objects set to null	    
	    this.n = null;
	    this.s = null;
	    this.e = null;
	    this.w = null;

	    //iteration through the room arraylist again, this time to find the room object associated with each string room name.
	    for (Room q : rooms){
		if (q.getName().equals(this.north)){
		    this.n = q;
		}
		if (q.getName().equals(this.south)){
		    this.s = q;
		}
		if (q.getName().equals(this.east)){
		    this.e = q;
		}
		if (q.getName().equals(this.west)){
		    this.w = q;
		}
	    }
	    r.setPosition(n, s, e, w);
	}
    }


    @Override
    public void characters(char ch[], int start, int length)
        throws SAXException {
	    }
    public BST<Room> getTree(){
	return bst;
    }
    public ArrayList<Room> getRooms(){
	return rooms;
    }
    public Player getPlayer(){
	return p;
    }
    public int getCount(){
	return count;
    }
}
