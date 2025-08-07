import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int N;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
        N = 0;
    }

    // resize the array
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add null item");
        if (N == s.length)
            resize(2 * s.length);
        s[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty");
        int randomIndex = StdRandom.uniformInt(N);
        Item item = s[randomIndex];
        s[randomIndex] = s[--N];
        s[N] = null;
        if (N > 0 && N == s.length / 4)
            resize(s.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty");
        int randomSample = StdRandom.uniformInt(N);
        return s[randomSample];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] iterArray;
        private int current;

        public RandomizedQueueIterator() {
            iterArray = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                iterArray[i] = s[i];
            }
            StdRandom.shuffle(iterArray);
            current = 0;
        }

        public boolean hasNext() {
            return current < iterArray.length;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("End of array.");
            return iterArray[current++];
        }

        public void remove() {
            throw new UnsupportedOperationException("Operation not supported.");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        System.out.println("=== RandomizedQueue Unit Test ===");

        // 1. Test constructor and isEmpty()
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        System.out.println("Initially empty? " + rq.isEmpty()); // true
        System.out.println("Initial size: " + rq.size()); // 0

        // 2. Test enqueue() with normal values
        System.out.println("\n-- Enqueueing values 1 to 5 --");
        for (int i = 1; i <= 5; i++) {
            rq.enqueue(i);
            System.out.println("Enqueued: " + i + " | Size now: " + rq.size());
        }

        // 3. Test enqueue() with null (should throw IllegalArgumentException)
        try {
            System.out.println("\n-- Testing enqueue(null) --");
            rq.enqueue(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        // 4. Test sample() several times (should not remove items)
        System.out.println("\n-- Sampling random elements --");
        for (int i = 0; i < 3; i++) {
            System.out.println("Sample: " + rq.sample() + " | Size: " + rq.size());
        }

        // 5. Test sample() on empty queue
        try {
            System.out.println("\n-- Testing sample() on empty queue --");
            RandomizedQueue<String> emptyRQ = new RandomizedQueue<>();
            emptyRQ.sample();
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        // 6. Test dequeue()
        System.out.println("\n-- Dequeuing all elements --");
        while (!rq.isEmpty()) {
            System.out.println("Dequeued: " + rq.dequeue() + " | Size: " + rq.size());
        }

        // 7. Test dequeue() on empty queue
        try {
            System.out.println("\n-- Testing dequeue() on empty queue --");
            rq.dequeue();
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        // 8. Test iterator random order & independence
        System.out.println("\n-- Testing iterator independence --");
        for (int i = 1; i <= 5; i++)
            rq.enqueue(i); // refill
        Iterator<Integer> it1 = rq.iterator();
        Iterator<Integer> it2 = rq.iterator();

        System.out.print("Iterator 1: ");
        while (it1.hasNext()) {
            System.out.print(it1.next() + " ");
        }
        System.out.println();

        System.out.print("Iterator 2: ");
        while (it2.hasNext()) {
            System.out.print(it2.next() + " ");
        }
        System.out.println();

        // 9. Test iterator next() when empty
        try {
            System.out.println("\n-- Testing iterator next() on empty iterator --");
            Iterator<Integer> emptyIt = new RandomizedQueue<Integer>().iterator();
            emptyIt.next();
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        // 10. Test iterator remove()
        try {
            System.out.println("\n-- Testing iterator remove() --");
            rq.iterator().remove();
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        System.out.println("\n=== Unit Test Complete ===");
    }

}
