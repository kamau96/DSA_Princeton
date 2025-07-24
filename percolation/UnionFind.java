public class UnionFind {

    private int[] parent;
    private int[] size;
    
    public UnionFind(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i; // each element is its own root
            size[i] = 1;   // each component has size 1 initially
        }
    }
    
    public int find(int p) {
        if (p < 0 || p >= parent.length) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (parent[p] == p) {return p;} // p is the root
        parent[p] = find(parent[p]); // path compression
        return parent[p];
    }
    
    public boolean connected(int p, int q) {
        if (p < 0 || p >= parent.length || q < 0 || q >= parent.length) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return find(p) == find(q);
    }


}