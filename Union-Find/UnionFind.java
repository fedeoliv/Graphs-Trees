import java.util.Scanner;

/* Input:	10	Output: 4 3
		4 3		3 8
		3 8		6 5
		6 5		9 4
		9 4		2 1
		2 1		5 0
		8 9		7 2
		5 0		6 1
		7 2
		6 1		2 components
		1 0
		6 7 */

public class UnionFind {
    private int[] parent;   // parent[i] = parent of i
    private int[] size;     // size[i] = number of objects in subtree rooted at i
    private int count;      // number of components

    public UnionFind(int N) {
        count = N;
        parent = new int[N];
        size = new int[N];
        
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    // Returns number of components
    public int count() {
        return count;
    }

    // Find the other component associated to p.
    public int find(int p) {
        validate(p);
        while (p != parent[p]) p = parent[p];
        return p;
    }

    // Validate that p is a valid index
    private void validate(int p) {
        int N = parent.length;
        if (p < 0 || p >= N)
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + N);
    }

    // Are p and q in the same component?
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

  
    // Adding connection between p and q
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }

    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        UnionFind uf = new UnionFind(N);
        
        for(int i = 0; i < N; i++) {
            int p = sc.nextInt();
            int q = sc.nextInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
            System.out.println(p + " " + q);
        }
        
        System.out.println(uf.count() + " components");
    }
}
