import edu.princeton.cs.algs4.StdIn;

public class Permutation {
  public static void main(String[] args) {
    int k = Integer.parseInt(args[0]);
    RandomizedQueue<String> rq = new RandomizedQueue<>();

    while (!StdIn.isEmpty()) {
      String s = StdIn.readString();
      rq.enqueue(s);
      if (k == 0) {
        rq.dequeue();
      } else {
        k--;
      }
    }

    for (String s : rq) {
      System.out.println(s);
    }

  }
}