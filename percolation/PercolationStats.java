package percolation;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private double[] thresholds;
    private double confidence95 = 1.96; // 95% confidence level

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Grid size and number of trials must be greater than 0");
        }

        thresholds = new double[trials];
        for (int t = 0; t < trials; t++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniformInt(n);
                int col = StdRandom.uniformInt(n);
                perc.open(row + 1, col + 1); // Convert to 1-based indexing
            }
            thresholds[t] = (double) perc.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double stddev = stddev();
        return mean - (confidence95 * stddev / Math.sqrt(thresholds.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double stddev = stddev();
        return mean + (confidence95 * stddev / Math.sqrt(thresholds.length));
    }

   // test client (see below)
   public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Please provide grid size and number of trials as arguments");
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.printf("mean                    = %.16f\n", stats.mean());
        StdOut.printf("stddev                  = %.16f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%.16f, %.16f]\n", stats.confidenceLo(), stats.confidenceHi());
   }

}