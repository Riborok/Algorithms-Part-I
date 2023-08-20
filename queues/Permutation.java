package queues;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please enter one values: amount of elements");
        }
        else {
            int k;
            try {
                k = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input for amount of elements. "
                                           + "Enter integer values");
                return;
            }

            RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
            while (!StdIn.isEmpty())
                randomizedQueue.enqueue(StdIn.readString());

            for (int i = 0; i < k; i++)
                System.out.println(randomizedQueue.dequeue());
        }
    }
}
