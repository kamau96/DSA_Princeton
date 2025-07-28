package percolation;
public class Percolation {

    private int[][] grid; // 2D array to represent the grid
    private int openSitesCount = 0; // Count of open sites
    UnionFind uf; // Union-Find structure to manage connectivity

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }
        grid = new int[n][n];
        uf = new UnionFind(n); // Initialize Union-Find for n*n sites
        // Initialize all sites to blocked (0)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0; // 0 represents a blocked site
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (grid[row][col] == 1) {
            return; // Site is already open
        }
        grid[row][col] = 1; // Mark the site as open
        openSitesCount++; // Increment the count of open sites

        // Connect to adjacent open sites
        if (row > 0 && isOpen(row - 1, col)) { // Up
            uf.union(row, col, row - 1, col);
        }
        if (row < grid.length - 1 && isOpen(row + 1, col)) { // Down
            uf.union(row, col, row + 1, col);
        }
        if (col > 0 && isOpen(row, col - 1)) { // Left
            uf.union(row, col, row, col - 1);
        }
        if (col < grid[0].length - 1 && isOpen(row, col + 1)) { // Right
            uf.union(row, col, row, col + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return grid[row][col] == 1; // 1 represents an open site
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        } 
        // A site is full if it is open and connected to the top row
        if (!isOpen(row, col)) {
            return false; // If the site is not open, it cannot be full
        }
        // Check if the site is connected to any open site in the top row
        for (int i = 0; i < grid[0].length; i++) {
            if (isOpen(0, i) && uf.connected(0, i, row, col)) {
                return true; // If connected to any open site in the top row
            }
        }
        return false; // If no connection to the top row
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openSitesCount; // Return the count of open sites
    }

    // does the system percolate?
    public boolean percolates(){
        // The system percolates if there is a connection from the top row to the bottom row
        for (int i = 0; i < grid[0].length; i++) {
            if (isOpen(grid.length - 1, i) && uf.connected(0, i, grid.length - 1, i)) {
                return true; // If any site in the bottom row is connected to the top row
            }
        }
        return false; // No connection found
    }

    // test client (optional)
    // public static void main(String[] args)
}