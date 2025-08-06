import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item can't be null.");
        }
        Node current = new Node();

        current.item = item;
        current.next = first;
        current.prev = null;

        if (first == null) {
            last = current;
        } else {
            first.prev = current;
        }

        first = current;

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item can't be null.");
        }

        Node current = new Node();

        current.item = item;
        current.next = null;
        current.prev = last;

        if (last == null) {
            first = current;
        } else {
            last.next = current;
        }

        last = current;

        size++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty.");
        }

        Node oldFirst = first;

        first = first.next;
        oldFirst.next = null;

        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }

        size--;

        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty.");
        }

        Node oldLast = last;

        last = last.prev;
        oldLast.prev = null;

        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }

        size--;

        return oldLast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No more items.");
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove not supported.");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        System.out.println("Creating an empty deque...");
        Deque<String> deque = new Deque<>();
        System.out.println("Is empty? " + deque.isEmpty()); // true
        System.out.println("Size: " + deque.size()); // 0

        System.out.println("\nAdding elements to front and back...");
        deque.addFirst("first");
        deque.addLast("last");
        deque.addFirst("zero");
        deque.addLast("end");

        System.out.println("Is empty? " + deque.isEmpty()); // false
        System.out.println("Size: " + deque.size()); // 4

        System.out.println("\nRemoving from front: " + deque.removeFirst()); // "zero"
        System.out.println("Removing from back: " + deque.removeLast()); // "end"

        System.out.println("Current size: " + deque.size()); // 2

        System.out.println("\nCurrent deque (front to back):");
        for (String item : deque) {
            System.out.println(item); // "first", "last"
        }

        System.out.println("\nTesting iterator exceptions...");
        Iterator<String> it = deque.iterator();
        while (it.hasNext()) {
            System.out.println("Next: " + it.next());
        }

        try {
            System.out.println("Calling next() when no items remain...");
            it.next(); // Should throw NoSuchElementException
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected NoSuchElementException: " + e.getMessage());
        }

        try {
            System.out.println("Calling remove() on iterator...");
            it.remove(); // Should throw UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException: " + e.getMessage());
        }

        System.out.println("\nTesting exception on addFirst(null)...");
        try {
            deque.addFirst(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException: " + e.getMessage());
        }

        System.out.println("\nTesting exception on addLast(null)...");
        try {
            deque.addLast(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException: " + e.getMessage());
        }

        System.out.println("\nClearing deque...");
        deque.removeFirst();
        deque.removeFirst();

        System.out.println("Testing exception on removeFirst() when empty...");
        try {
            deque.removeFirst();
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected NoSuchElementException: " + e.getMessage());
        }

        System.out.println("Testing exception on removeLast() when empty...");
        try {
            deque.removeLast();
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected NoSuchElementException: " + e.getMessage());
        }

        System.out.println("\nAll tests completed.");
    }

}