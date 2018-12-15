abstract class Character {
    String name;
    String description;
    Room room, prev;

    public Character(String name, String description){
	this.name = name;
	this.description = description;
	this.room = this.prev = null;
    }
    
    public void changeRoom(Room room){//Updates Room object
	this.prev = this.room;
	this.room = room;
	this.prev.removeCharacter(this);
	this.room.addCharacter(this);
    }
    public void setRoom(Room room){//intended to initialize the Room object
	this.room = room;
    }
    public void reaction(Item item, Item.ItemAction action){
    }
    public String getName(){
	return name;
    }
    @Override
    public String toString(){
	return(name + ", " + description);
    }
}
