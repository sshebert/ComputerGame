import java.util.ArrayList;

public class BST<T extends Comparable<T>>{

    private class BSTNode<T extends Comparable<T>>{
	T value;
	BSTNode<T> left;
	BSTNode<T> right;

	public BSTNode(T value){
	    this.value = value;
	}
	@Override
	public String toString(){
	    return value.toString();
	}
    }

    private BSTNode<T> root;

    public BST(){
	this.root = null;
    }
    
    private BSTNode<T> insert(BSTNode<T> curr, T value){
	if (curr == null)
	    return new BSTNode<T>(value);
	if (value.compareTo(curr.value) > 0)
	    curr.right = insert(curr.right, value);
	else if (value.compareTo(curr.value) < 0)
	    curr.left = insert(curr.left, value);
	return curr;
    }

    public void insert(T value){
	root = insert(root, value);
    }

    private boolean search(BSTNode<T> curr, T value){
	//System.out.println("Visiting: " + (curr == null ? "null :(" : curr.value));
	if (curr == null) return false;
	if (curr.value.compareTo(value) == 0) return true;
	if (value.compareTo(curr.value) > 0)
	    return search(curr.right, value);
	return search(curr.left, value);
    }

    public boolean search(T value){
	return search(root, value);
    }

    private void printInOrder(BSTNode<T> curr){
	if (curr != null){
	    printInOrder(curr.left);
	    System.out.print(curr.value.toString());
	    printInOrder(curr.right);
	}
    }

    public void printTree(){
	printInOrder(root);
	System.out.println();
    }

    public static void main(String[] args){
	BST<Room> bst = new BST<>();
	
	Room living = new Room("living room", "the living room");
	bst.insert(living);
	Room family = new Room("family room", "the family room");
	bst.insert(family);
	Room guest = new Room("guest", "the guest room");
	bst.insert(guest);
	Room kids = new Room("kids room", "the kids room");
	bst.insert(kids);
	Room closet = new Room("closet", "the closet");
	bst.insert(closet);
	Room kitchen = new Room("kitchen", "the kitchen");
	bst.insert(kitchen);
	
	bst.printTree();
	
    }
}
