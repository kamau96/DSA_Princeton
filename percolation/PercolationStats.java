package percolation;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private double[] thresholds; // Array to store the percolation thresholds for each trial
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Grid size and number of trials must be greater than 0");
        }
        // Initialize arrays to store results of trials
        thresholds = new double[trials];
        
        for (int t = 0; t < trials; t++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniformInt(n);
                int col = StdRandom.uniformInt(n);
                perc.open(row, col);
            }
            thresholds[t] = (double) perc.numberOfOpenSites() / (n * n); // Calculate threshold
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        double mean = mean();
        double stddev = stddev();
        return mean - (1.96 * stddev / Math.sqrt(thresholds.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        double mean = mean();
        double stddev = stddev();
        return mean + (1.96 * stddev / Math.sqrt(thresholds.length));
    }

   // test client (see below)
   public static void main(String[] args){
        if (args.length < 2) {
            throw new IllegalArgumentException("Please provide grid size and number of trials");
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        
        PercolationStats stats = new PercolationStats(n, trials);
        
        StdOut.printf("mean                    = %.16f\n", stats.mean());
        StdOut.printf("stddev                  = %.16f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%.16f, %.16f]\n", stats.confidenceLo(), stats.confidenceHi());
    }
   }

// Note: The above code assumes that the Percolation class is implemented correctly and has methods like open, percolates, and numberOfOpenSites.