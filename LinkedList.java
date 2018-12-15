import java.util.Iterator;

public class LinkedList<T> implements Iterable<T> {

    private class Node<T> {
	T data;
	Node<T> next;

	public Node(T data){
	    this.data = data;
	}

	public Node(T data, Node<T> next){
	    this(data);
	    this.next = next;
	}
	@Override
	public String toString(){
	    return "" + data;
	}
    }

    public class LLIterator<U> implements Iterator<U> {
	LinkedList<U> ll;
	LinkedList<U>.Node<U> curr;

	public LLIterator(LinkedList<U> ll){
	    this.ll = ll;
	    curr = null;
	}
	@Override
	public boolean hasNext(){
	    if (curr == ll.tail && ll.tail != null)
		return false;
	    return true;
	}
	@Override
	public U next(){
	    if (curr == null)
		curr = ll.head;
	    else curr = curr.next;
	    return curr.data;
	}
    }

    private Node<T> head, tail;
    private int count;

    public LinkedList(){
	count = 0;
    }

    public void prepend (T i){
	if (count == 0){
	    head = tail = new Node<T>(i);
	}else {
	    head = new Node<T>(i, head);
	}
	count++;
    }

    public void append(T i){
	if (count == 0)
	    head = tail = new Node<T>(i);
	else
	    tail = tail.next = new Node<T>(i);

	count++;
    }

    public void insert(T data, int index){
	int counter = 1;
	Node<T> input;
	
	if (index < 0 || index > count){
	    throw new IndexOutOfBoundsException("Index out of bounds");
	}
	if (index == 0){
	    prepend(data);
	    return;
	}
	
	if (index == count){
	    append(data);
	    return;
	}
	
	else{
	    Node<T> temp = head;
	    while(counter <= index){
		if (counter == index){
		    temp.next = input = new Node<T>(data, temp.next);
		    count++;
		    return;
		}
		else {
		    counter++;
		}
		temp = temp.next;
	    }
	    return;
	}
    }

    public boolean exists(T data){
	for (Node<T> temp = head; temp != null; temp = temp.next){
	    if (temp.data.equals(data))
		return true;
	}
	return false;
    }

    public boolean remove(T data){
	Node<T> prev = null;
	for (Node<T> temp = head; temp != null; temp = temp.next){
	    if (temp.data.equals(data)){
		if(temp.equals(head) && temp.equals(tail)){
		    head = tail = null;
		    count--;
		    return true;
		}
		if(temp.equals(head)){
		    head = head.next;
		    temp.next = null;
		    count--;
		    return true;
		}
		if(temp.equals(tail)){
		    tail = prev;
		    tail.next = null;
		    count--;
		    return true;
		}
		else{
		    prev.next = temp.next;
		    count--;
		    return true;
		}
	    }
	    prev = temp;
	}
	return false;
    }

    public T get(int index){
	int counter = 0;
	if (index < 0 || index > count){
	    throw new IndexOutOfBoundsException("Index out of bounds");
	}
	
	for (Node<T> temp = head; counter <= index && counter <= count; temp = temp.next){
	    if(counter == index && temp != null)
		return temp.data;
	    counter++;
	}
	return null;
    }

    public int size(){
	return count;
    }

    public boolean isEmpty(){
	return head == null;
    }
    @Override
    public String toString() {
	String retVal = "";
	for (Node<T> temp = head; temp != null; temp = temp.next)
	    retVal += "\n\t" + temp;

	return retVal;
    }
    @Override
    public Iterator<T> iterator(){
	return new LLIterator<T>(this);
    }

    public void getHead(){
	System.out.println("Head: " + head.toString());
    }

    public void getTail(){
	System.out.println("Tail: " + tail.toString());
    }
    
    public static void main (String[] args){
	/*LinkedList<Integer> ll = new LinkedList<>();
	Iterator it = ll.iterator();
	ll.append(1);
	System.out.println(ll.toString());
	//ll.getHead();
	//ll.getTail();
        ll.append(2);
	System.out.println(ll.toString());
	//ll.getHead();
	//ll.getTail();
	ll.remove(1);
	ll.prepend(1);
	System.out.println(ll.toString());
	ll.getHead();
	ll.getTail();
	System.out.println(ll.head.next);
	System.out.println(ll.tail.next);
	for (int i=0; i < ll.size(); i++)
	    System.out.println(ll.get(i));
	for (int i : ll)
	  System.out.println(i);
	
	
	//ll.remove(1);
	//System.out.println(ll.toString());
	/*System.out.println(ll.exists(3));
	ll.insert(3, 2);
	System.out.println(ll.toString());
	ll.insert(6, 5);
	System.out.println(ll.toString());
	System.out.println(ll.get(3).toString());
	System.out.println(ll.exists(3));
	System.out.println(ll.remove(3));
	System.out.println(ll.toString());
	System.out.println(ll.remove(3));
	ll.insert(6, 7);
	*/
	}
	
}
