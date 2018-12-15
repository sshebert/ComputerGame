import java.util.Scanner;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class Player extends Character{
    private boolean cheat;
    private BST<Room> bst;
    private ArrayList<Room> rooms;
    
    public Player(String name, String description){
	super(name, description);
	cheat = false;
    }
    public void setTree(BST<Room> bst){
	this.bst = bst;
    }
    public void setRooms(ArrayList<Room> rooms){
	this.rooms = rooms;
    }
    
    public void play(Scanner s){
	String input = "";
	int start, end;
	Room temp;
	System.out.println("Have Fun! Type 'help' to get commands");
	while(true){
	    input = s.nextLine();
	    System.out.println("Time left: " + GameTimer.getTime());
	    //if statement for each command
	    if (input.equalsIgnoreCase("help")){//help command
		System.out.println("Available commands:\n\tnorth - go north\n\tsouth - go south\n\teast - go east\n\twest - go west\n\tlook - look around the room\n\tpossess:[ItemName] - possess an item\n\tthrow:[ItemName] - throw an item\n\tshake:[ItemName] - shake an item\n\tquit - quit the game");
	    }else if (input.equalsIgnoreCase("look")){//look command
		System.out.println(room.toString());
	    }else if (input.equalsIgnoreCase("cheatmode")){
		this.cheat = true;
		System.out.println("cheatmode on");
	    }else if (input.equalsIgnoreCase("nocheatmode")){
		this.cheat = false;
		System.out.println("cheatmode off");
	    }else if (input.equalsIgnoreCase("addtime") && cheat){
		GameTimer.addTime(1000);
	    }else if (input.equalsIgnoreCase("north")){//north command
		if (room.getNorth() == null){//if no room in requested direction
		    System.out.println("No room is to the north, type 'look' for more information");
		}else{
		    changeRoom(room.getNorth());//changes player to requested room
		    System.out.println(this.name + " moved to " + room.getName());
		}
	    }else if (input.equalsIgnoreCase("east")){//east command
		if (room.getEast() == null){
		    System.out.println("No room is to the east, type 'look' for more information");
		}else{
		    changeRoom(room.getEast());
		    System.out.println(this.name + " moved to " + room.getName());
		}
	    }else if (input.equalsIgnoreCase("west")){//west command
		if (room.getWest() == null){
		    System.out.println("No room is to the west, type 'look' for more information");
		}else{
		    changeRoom(room.getWest());
		    System.out.println(this.name + " moved to " + room.getName());
		}
	    }else if (input.equalsIgnoreCase("south")){//south command
		if (room.getSouth() == null){
		    System.out.println("No room is to the south, type 'look' for more information");
		}else{
		    changeRoom(room.getSouth());
		    System.out.println(this.name + " moved to " + room.getName());
		}
	    }else if (input.equalsIgnoreCase("quit")){//quit command
		System.out.println("Thanks for playing");
		System.exit(0);
		return;
	    }else{//case for shake, possess, throw, and error messages
		String [] arr = input.split(":",0);//splits input string with intended form action:[itemname]
		if (arr.length == 2){//should only be 1 colon in the input, so if there are more than 2 strings after splitting the input command was incorrect
		    start = arr[1].indexOf("[");//index of start bracket
		    end = arr[1].indexOf("]");//index of end bracket
		    if (start > -1 && end > -1){//checks if brackets are present
			String i = arr[1].substring(start + 1, end);//arr[1] equals [itemname] or [roomname], this step removes the square brackets []
			if(Item.checkEnumAction(arr[0])){
			    if(room.checkItem(i)){//checks if the item is in the room using the string name
				Item item = room.getItem(i);//returns item object using string name
				if (!item.checkBroken()){//if item is not broken
				    String a = arr[0].toUpperCase();//arr[0] is action name, changes to all caps so in the next line it can be changed to an ItemAction
				    Item.ItemAction action = Item.ItemAction.valueOf(a);
				    if (item.checkAction(action)){//checks if the action is supported by the item
					if(action.equals(Item.ItemAction.THROW)){
					    //System.out.println("The " + item.getName() + " has been thrown");
					    item.broken();
					}
					haunt(item, action);//calls haunt method
				    }else{//if action is not supported by item
					System.out.println("This action cannot be done on this item");
				    }
				}
				else{//if item is broken
				    System.out.println("The item is broken and can not be manipulated");
				}
			    }
			    else{//if item is not in the room
				System.out.println("This item is not in this room");
			    }
			}
			else if(arr[0].equalsIgnoreCase("look") && cheat){
			    temp = null;
			    for (Room r : rooms){
				if (r.getName().equalsIgnoreCase(i)){
				    temp = r;
				    //System.out.println(temp.getName());
				    break;
				}
			    }
			    //System.out.println(temp.getName());
			    if(temp != null && bst.search(temp))
				System.out.println(temp.toString());
			    if(temp == null || !bst.search(temp))
				System.out.println("Room does not exist");
			}
			else { System.out.println("Incorrect Command"); }
			
		    }
		    else if (arr[0].equalsIgnoreCase("look") && arr[1].equalsIgnoreCase("all") && cheat){
			    bst.printTree();
		    }
		    else { System.out.println("Incorrect Command"); }
		}      	
		else{ System.out.println("Incorrect Command"); }
	    }
	}
    }
    public void haunt(Item item, Item.ItemAction action){//passes item and action to the rooms hauntRoom method
	//System.out.println(room.getName() + " has been haunted");
	room.hauntRoom(item, action);
    }
}
