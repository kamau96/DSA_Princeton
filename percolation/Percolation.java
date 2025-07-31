package percolation;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {

    private boolean[][] grid;
    private int openSitesCount;
    WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }
        grid = new boolean[n][n];
        openSitesCount = 0;
        uf = new WeightedQuickUnionUF(n * n); 

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length) {
            throw new IndexOutOfBoundsException("Row or column index out of bounds");
        }
        if (!grid[row][col]) {
            grid[row][col] = true;
            openSitesCount++;
            int index = row * grid.length + col;

            // Connect to adjacent open sites
            if (row > 0 && isOpen(row - 1, col)) { // Up
                uf.union(index, (row - 1) * grid.length + col);
            }
            if (row < grid.length - 1 && isOpen(row + 1, col)) { // Down
                uf.union(index, (row + 1) * grid.length + col);
            }
            if (col > 0 && isOpen(row, col - 1)) { // Left
                uf.union(index, row * grid.length + (col - 1));
            }
            if (col < grid[0].length - 1 && isOpen(row, col + 1)) { // Right
                uf.union(index, row * grid.length + (col + 1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length) {
            throw new IndexOutOfBoundsException("Row or column index out of bounds");
        }
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length) {
            throw new IndexOutOfBoundsException("Row or column index out of bounds");
        }
        int index = row * grid.length + col;
        // Check if the site is open and connected to the top row
        if (isOpen(row, col)) {
            for (int i = 0; i < grid.length; i++) {
                if (isOpen(0, i) && (uf.find(index) == uf.find(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates(){
        // Check if any site in the bottom row is connected to the top row
        for (int i = 0; i < grid.length; i++) {
            if (isOpen(grid.length - 1, i) && isFull(grid.length - 1, i)) {
                return true;
            }
        }
        return false;
    }

    // test client (optional)
    // public static void main(String[] args)
}