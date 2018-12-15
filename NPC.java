import java.util.Random;

abstract class NPC extends Character{
    float factor, j, scare;
    Random rand = new Random();
    int scareLimit = 100;
    boolean check = true;
    static int npcCount, count;
    public NPC(String name, String description){
    super(name, description);
    }
    @Override
    public void changeRoom(Room room){
	super.changeRoom(room);//same as Character's changeRoom except if there is a broken item in the new room the NPC fix it. NPC will only fix one item.
	Item item;
	if (this.room.checkBroken()){
	    item = this.room.getBroken();
	    item.fix();
	    System.out.println(this.name + " has fixed the " + item.getName());
	}
    }
    @Override
    public void reaction(Item item, Item.ItemAction action){
	if(action.equals(Item.ItemAction.POSSESS)){//each action increments the scare by a different amount
	    j = rand.nextInt(16) + 10;//random number 10-25
	    this.scare = this.scare + factor*j;
	}
	if(action.equals(Item.ItemAction.THROW)){
	    j = rand.nextInt(21) + 20;//random number from 20-40
	    this.scare = this.scare + factor*j;
	}
	if(action.equals(Item.ItemAction.SHAKE)){
	    j = rand.nextInt(11) + 5;//random number from 5-15
	    this.scare = this.scare + factor*j;
	}
	if(scare < scareLimit)
	    System.out.println(this.name + " shrieks!");
	if ((this.scare >= this.scareLimit/2) && check && !(scare >= scareLimit)){//if scare is one half or more of scareLimit and the NPC has not already moved rooms
	    Room r = null;
	    check = false;//the NPC will not change rooms again (except when leaving the house)
	    int i;
	    while(r == null){
		i = rand.nextInt(4);//uses random number from 0-3 to determine what room the NPC should move to (0 corresponds to north, etc), if there is no room in that direction the while loop does not break and a new number is tried
		switch(i){//switch case based random number
		case 0://north case
		    r = this.room.getNorth();
		    if(r != null){//checks if there is a room in this direction
			System.out.println(this.name + " has fled to " + r.getName());
			changeRoom(r);
			break;
		    }else{//if no room r will remain null and while loop continues
			break;
		    }
		case 1://south case
		    r = this.room.getSouth();
		    if(r != null){
			System.out.println(this.name + " has fled to " + r.getName());
			changeRoom(r);
			break;
		    }else{
			break;
		    }
		case 2://east case
		    r = this.room.getEast();
		    if(r != null){
			System.out.println(this.name + " has fled to " + r.getName());
			changeRoom(r);
			break;
		    }else{
			break;
		    }
		case 3://west case
		    r = this.room.getWest();
		    if(r != null){
			System.out.println(this.name + " has fled to " + r.getName());
			changeRoom(r);
			break;
		    }else{
			break;
		    }
		}
	    }
	    this.prev.hauntRoom(item, action);//without this line the for loop in the hauntRoom action will end prematurely. This resets the for loop in order to scare the rest of the characters in the room.
	}
	if (scare >= scareLimit){//if scare reaches scareLimit
	    this.room.removeCharacter(this);
	    this.room = null;//the NPC is effectively no longer in the game
	    System.out.println(this.name + " has fled the house in terror");
	    count++;
	    if (count == npcCount){//win condition
		System.out.println("All NPCs have been scared out of the house\nCongratulations you win!");
		System.exit(0);
	    }else{
		GameTimer.addTime(30);
	    }
	}
    }
    public static void setCount(int count){
	npcCount = count;
    }
    @Override
    public String toString(){
	return super.toString() + " - scare level: " + this.scare;
    }
}
