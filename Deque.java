import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node prev;

        public Node(Item item) {
            if (item == null)
                throw new IllegalArgumentException("Item cannot be null");
            this.item = item;
            next = null;
            prev = null;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        public DequeIterator() {
            current = head;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No next element");

            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public Deque() {
        size = 0;
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        Node oldHead = head;
        head = new Node(item);
        head.next = oldHead;
        size++;
        if (oldHead == null)
            tail = head;
        else
            oldHead.prev = head;
    }

    public void addLast(Item item) {
        Node oldTail = tail;
        tail = new Node(item);
        tail.prev = oldTail;
        size++;
        if (oldTail == null)
            head = tail;
        else
            oldTail.next = tail;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Deque is empty");

        Item item = head.item;
        head = head.next;
        size--;
        if (head == null)
            tail = null;
        else
            head.prev = null;

        return item;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Deque is empty");

        Item item = tail.item;
        tail = tail.prev;
        size--;
        if (tail == null)
            head = null;
        else
            tail.next = null;

        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(3);
        deque.addFirst(5);
        deque.addLast(0);

        for (int item : deque)
            System.out.print(item + " ");
        System.out.println('\n');

        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println();
        System.out.println(deque.size());
    }
}
