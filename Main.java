import org.xml.sax.SAXException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
	Scanner sc = new Scanner(System.in);
	BST<Room> bst = new BST<>();
	ArrayList<Room> rooms = new ArrayList<>();
	Player p = null;
	String input;
	Boolean check = null;
	Room out = null;
	int count = 0;
	System.out.print("Input File: ");//gets input file from user
	input = sc.nextLine();
	SAXParserFactory spf = SAXParserFactory.newInstance();
	try {//runs SAX Parser
	    InputStream xmlInput = new FileInputStream(input);
	    SAXParser saxParser = spf.newSAXParser();
	    ProjectXMLParser pxp = new ProjectXMLParser();
	    saxParser.parse(xmlInput, pxp);
	    p = pxp.getPlayer();
	    count = pxp.getCount();
	    bst = pxp.getTree();
	    rooms = pxp.getRooms();
	}
	catch(SAXException|ParserConfigurationException|IOException e){
	    e.printStackTrace();
	}
	if (p == null){//checks if there was a player object in the input file
	    System.out.println("Incorrect input file");
	}else{
	    NPC.setCount(count);//number of NPCs to set win condition
	    GameTimer.initTimer(60);
	    p.setTree(bst);
	    p.setRooms(rooms);
	    p.play(sc);//runs the game
	}
    }
	
}
