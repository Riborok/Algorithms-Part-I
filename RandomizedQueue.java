import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int index;
        private Item[] randomItems;

        public RandomizedQueueIterator() {
            index = 0;
            randomItems = (Item[]) new Object[size];
            for (int i = 0; i < size; i++)
                randomItems[i] = items[i];
            StdRandom.shuffle(randomItems);
        }

        public boolean hasNext() {
            return index < size;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No next element");
            return randomItems[index++];
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }
    }

    private static final int MIN_LENGTH = 2;
    private Item[] items;
    private int size;

    public RandomizedQueue() {
        items = (Item[]) new Object[MIN_LENGTH];
        size = 0;
    }

    private void resize(int length) {
        Item[] oldItems = items;
        items = (Item[]) new Object[length];
        for (int i = 0; i < size; i++)
            items[i] = oldItems[i];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item cannot be null");

        if (size == items.length)
            resize(items.length << 1);

        items[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Deque is empty");

        int randomIndex = StdRandom.uniform(size);
        Item item = items[randomIndex];
        items[randomIndex] = items[--size];
        items[size] = null;
        if (items.length >= MIN_LENGTH << 1 && size <= items.length >> 2)
            resize(items.length >> 1);
        return item;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Deque is empty");
        return items[StdRandom.uniform(size)];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();

        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(5);
        randomizedQueue.enqueue(0);

        for (int item : randomizedQueue)
            System.out.print(item + " ");
        System.out.println('\n');

        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println();
        System.out.println(randomizedQueue.size());
    }
}
