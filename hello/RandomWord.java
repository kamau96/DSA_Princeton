package hello;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String word = null;
        int count = 0;

        while (!StdIn.isEmpty()) {
            String current = StdIn.readString(); 
            count++;
            if (StdRandom.bernoulli(1.0 / count)) {
                word = current;  
            }
        }

        StdOut.println(word);

    }
}
// This program reads words from standard input and randomly selects one word
// with a uniform probability. The more words read, the lower the chance of 
