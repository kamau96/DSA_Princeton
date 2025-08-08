import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {

    private boolean[][] grid;
    private int openSitesCount;
    private WeightedQuickUnionUF uf;
    private int virtualTop; // Virtual top site for union-find
    private int virtualBottom; // Virtual bottom site for union-find

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }
        grid = new boolean[n][n];
        openSitesCount = 0;
        uf = new WeightedQuickUnionUF(n * n + 2); 
        // Virtual top site index
        virtualTop = n * n; 
        // Virtual bottom site index
        virtualBottom = n * n + 1; 

    }
    private boolean helper(int row, int col) {
        // System.out.println("Checking if row and col are within bounds: " + row + ", " + col);
        // System.out.println("Grid dimensions: " + grid.length + "x" + grid[0].length);
        // System.out.println(row > 0 && col > 0 && row <= grid.length && col <= grid[0].length);
        return row > 0 && col > 0 && row <= grid.length && col <= grid[0].length;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!helper(row, col)) {
            throw new IllegalArgumentException("Row or column index out of bounds");
        }
        row--; // Adjust for 0-based indexing
        col--; // Adjust for 0-based indexing
        if (!grid[row][col]) {
            grid[row][col] = true;
            openSitesCount++;
            int index = row * grid.length + col;

            // Connect to adjacent open sites
            // Connect to virtual top site
            if (row == 0) {
                uf.union(index, virtualTop); 
            }
            // Connect to virtual bottom site
            if (row == grid.length - 1) {   
                uf.union(index, virtualBottom);
            }
            // Up
            if (row > 0 && isOpen(row, col + 1)) {
                uf.union(index, (row - 1) * grid.length + col);
            }
            // Down
            if (row < grid.length - 1 && isOpen(row + 2, col + 1)) { 
                uf.union(index, (row + 1) * grid.length + col);
            }
            // Left
            if (col > 0 && isOpen(row + 1, col)) {
                uf.union(index, row * grid.length + (col - 1));
            }
            // Right
            if (col < grid[0].length - 1 && isOpen(row + 1, col + 2)) {
                uf.union(index, row * grid.length + (col + 1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!helper(row, col)) {
            throw new IllegalArgumentException("Row or column index out of bounds");
        }
        row--; // Adjust for 0-based indexing
        col--; // Adjust for 0-based indexing
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!helper(row, col)) {
            throw new IllegalArgumentException("Row or column index out of bounds");
        }
        row--; // Adjust for 0-based indexing
        col--; // Adjust for 0-based indexing
        int index = row * grid.length + col;
        // Check if the site is open and connected to the top row
        if (isOpen(row + 1, col + 1)) {
            return uf.find(index) == uf.find(virtualTop);
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        // Check if any site in the bottom row is connected to the top row
        return uf.find(virtualTop) == uf.find(virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        while (!StdIn.isEmpty()) {
            int n = StdIn.readInt();
            Percolation perc = new Percolation(n);
            while (!StdIn.isEmpty()) {
                int row = StdIn.readInt();
                int col = StdIn.readInt();
                perc.open(row, col);
            }
            System.out.println("Number of open sites: " + perc.numberOfOpenSites());
            System.out.println("Does the system percolate? " + perc.percolates());
        }
    }
}