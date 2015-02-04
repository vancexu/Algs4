import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.NullPointerException;
import java.lang.UnsupportedOperationException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int N; // size of deque
    
    private class Node {
        Item item;
        Node next;
        Node prev;
        
        public Node(Item item) {
            this.item = item;
            next = null;
            prev = null;
        }
    }
    
    public Deque() {
        first = null;
        last = null;
        N = 0;
    }
    
    public boolean isEmpty() {
        return N == 0;
    }
    
    public int size() {
        return N;
    }
    
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        
        if (isEmpty()) {
            first = new Node(item);
            last = first;
        } else {
            Node node = new Node(item);
            node.next = first;
            first.prev = node;
            first = node;
        }
        N++;
    }
    
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        
        if (isEmpty()) {
            last = new Node(item);
            first = last;
        } else {
            Node node = new Node(item);
            node.prev = last;
            last.next = node;
            last = node;
        }
        N++;
    }
    
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }
        N--;
        return item;
    }
    
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        } else {
            first = null; // Solved: loitering detected on 1 of 100 removeLast() operations
        }
        N--;
        return item;
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item>{
        private Node current = first;
        
        public boolean hasNext() {
            return current != null;
        }
        
        public void remove() { 
            throw new UnsupportedOperationException();  
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
    
}