package percolation;
public class UnionFind {

    public int[][] parent;
    public int[][] size;
    
    public UnionFind(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        parent = new int[n][n];
        size = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                parent[i][j] = i * n + j; // Flattening the 2D grid to 1D
                size[i][j] = 1; // Each site is its own component initially
            }
        }
    }
    
    public int find(int i, int j) {
        if (i < 0 || i >= parent.length || j < 0 || j >= parent[0].length) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        int index = i * parent.length + j; // Flattening the 2D grid to 1D
        if (parent[i][j] == index) {
            return index; // If it's the root, return it
        }
        if (parent[i][j] != index) {
            parent[i][j] = find(parent[i][j] / parent.length, parent[i][j] % parent.length); // Path compression
        }   
        return parent[i][j];
    }

    public boolean connected(int i_p, int j_p, int i_q, int j_q) {
        if (i_p < 0 || i_p >= parent.length || j_p < 0 || j_p >= parent[0].length ||
            i_q < 0 || i_q >= parent.length || j_q < 0 || j_q >= parent[0].length) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return find(i_p, j_p) == find(i_q, j_q);
    }

    public void union(int i_p, int j_p, int i_q, int j_q) {
        if (i_p < 0 || i_p >= parent.length || j_p < 0 || j_p >= parent[0].length ||
            i_q < 0 || i_q >= parent.length || j_q < 0 || j_q >= parent[0].length) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        int rootP = find(i_p, j_p);
        int rootQ = find(i_q, j_q);
        
        if (rootP == rootQ) return; // Already connected
        
        // Union by size
        if (size[i_p][j_p] < size[i_q][j_q]) {
            parent[i_p][j_p] = rootQ;
            size[i_q][j_q] += size[i_p][j_p];
        } else {
            parent[i_q][j_q] = rootP;
            size[i_p][j_p] += size[i_q][j_q];
        }
    }

    public int componentSize(int i, int j) {
        if (i < 0 || i >= parent.length || j < 0 || j >= parent[0].length) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        int root = find(i, j);
        return size[root / parent.length][root % parent.length]; // Return the size of the component
    }
}