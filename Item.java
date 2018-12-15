public class Item{
    private String name, description;
    public enum ItemAction {POSSESS, SHAKE, THROW}
    private ItemAction actionList[] = new ItemAction[3];
    private boolean broken = false;
    
    public Item(String name, String description){
	this.name = name;
	this.description = description;
    }
    public void addAction(ItemAction action){
	for (int i = 0; i < actionList.length; i++){
	    if (actionList[i] == null){
		actionList[i] = action;
		return;
	    }
	}
    }
    public void broken(){//sets broken to true
	this.broken = true;
    }
    public void fix(){//sets broken to false
	this.broken = false;
    }
    public boolean checkBroken(){
	if (broken){
	    return true;
	}else{
	    return false;
	}
    }
    public boolean checkAction(ItemAction action) {//checks if action is suppported for item
	for (int i = 0; i < actionList.length; i++){
	    if (actionList[i].equals(action)){
		return true;
	    }
	}
	return false;
    }
    public static boolean checkEnumAction(String action){
	if (action.equalsIgnoreCase("throw") || action.equalsIgnoreCase("possess") || action.equalsIgnoreCase("shake"))
	    return true;
	else
	    return false;
		
    }
    public String getName(){
	return this.name;
    }
    
    @Override
    public String toString(){//returns Item name, description as well as supported actions and if it is broken
	String actions = "";
	String r = "";
	String ret;
	if (broken){
	    r = "broken";
	}else{
	    r = "not broken";
	}
	for (int i = 0; i < actionList.length; i++){
	    if (!(actionList[i] == null)){
		actions = actionList[i] + " " + actions;
	    }
	}
	ret = String.format(name + ", " + description + ", " + r + "\n\t" + "\tActions: " + actions);
	return ret;
    }
}
