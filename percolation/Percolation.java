public class Percolation {

    private int[][] grid; // 2D array to represent the grid

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }
        grid = new int[n][n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (!isOpen(row, col)) {
            grid[row][col] = 1; // Assuming 1 represents an open site
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return grid[row][col] == 1; // Assuming 1 represents an open site
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)

    // returns the number of open sites
    public int numberOfOpenSites()

    // does the system percolate?
    public boolean percolates()

    // test client (optional)
    public static void main(String[] args)
}