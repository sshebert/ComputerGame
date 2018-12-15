import java.util.Iterator;

public class Room implements Comparable<Room>{
    private LinkedList<Character> occupants;
    private Item items[] = new Item[5];
    private String name;
    private String description;
    private Room north,south,east,west;
    private Iterator<Character> it;
    
    public Room (String name, String description) {
	this.name = name;
	this.description = description;
	this.occupants = new LinkedList<Character>();
    }
    
    public void setPosition(Room north, Room south, Room east, Room west){
	this.north = north;
	this.south = south;
	this.east = east;
	this.west = west;
    }
    
    public void addCharacter(Character character) {
	occupants.append(character);
    }
    
    public void removeCharacter(Character character) {
	occupants.remove(character);
    }
    
    public void addItem(Item item) {
	for (int i = 0; i < items.length; i++){
	    if (items[i] == null){
		items[i] = item;
		return;
	    }
	}
    }
    
    public boolean checkItem(String name) {//parameter string name of the item, checks all names of item objects in the room and if the item is found returns true
	for (int i=0; i < items.length; i++){
	    if (items[i] != null && items[i].getName().equalsIgnoreCase(name)){
		return true;
	    }
	}
	return false;
    }
    
    public boolean checkBroken() {//checks if a broken item is in the room, if there is a broken item returns true
	for (int i=0; i < items.length; i++){
	    if (items[i] != null && items[i].checkBroken()){
		return true;
	    }
	}
	return false;
    }
    
    public Item getItem(String item) {//parameter string name of the item, returns that item object. Should be used in combination with checkItem, so this method should never return null
	for (int i=0; i < items.length; i++){
	    if (items[i] != null && items[i].getName().equalsIgnoreCase(item)){
		return items[i];
	    }
	}
	return null;
    }
    
    public Item getBroken(){//returns a broken item in the room, should be used with checkBroken, and it should never return null
	for (int i=0; i < items.length; i++){
	    if (items[i] != null && items[i].checkBroken()){
	    return items[i];
	    }
	}
	return null;
    }
    
    public Room getNorth(){
	return north;
    }
    
    public Room getSouth(){
	return south;
    }
    
    public Room getEast(){
	return east;
    }
    
    public Room getWest(){
	return west;
    }
    
    public void hauntRoom(Item item, Item.ItemAction action){
	for(int i = 0; i < occupants.size(); i++){
	    occupants.get(i).reaction(item, action);
	}
    }
    
    public String getName() {
	return(this.name);
    }
    @Override
    public int compareTo(Room r){
	if (this.name.compareTo(r.getName()) > 0) return 1;
	if (this.name.compareTo(r.getName()) < 0) return -1;
	else return 0;
    }
    
    @Override
    public String toString() {//outputs Room name, description, and neighbors as well as all Characters and Items in the room and the Character and Item toString information
	String room = (this.name + ", " + this.description);
	String n, s, e, w;
	String neighbors = "\nNeighbors:";
	if (!(this.north == null)){
	    n = north.getName();
	    neighbors = neighbors + "\n\tnorth: " + n;
	}
	if (!(this.south == null)){
	    s = south.getName();
	    neighbors = neighbors + "\n\tsouth: " + s;
	}
	if (!(this.east == null)){
	    e = east.getName();
	    neighbors = neighbors + "\n\teast: " + e;
	}
	if (!(this.west == null)){
	    w = west.getName();
	    neighbors = neighbors + "\n\twest: " + w;
	}
	String title1 = ("Characters:");
	String title2 = ("Items:");
	String character = ("");
	String item = ("");
	character = "\t" + occupants.toString();
	for(int i=0; i < items.length; i++){
	    if(items[i] != null){
		item = item + "\t" + items[i].toString() + "\n";               }
	}
	return room + "\n" + neighbors + "\n" + title1 + character + "\n" + title2 + "\n" + item;
    }
}
