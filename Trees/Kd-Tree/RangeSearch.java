package lineintersection;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

/*************************************************************************
 * 	1-D range searching:: Range search implemented using a randomized BST.
 * 
 *  Applications: Database search, geographic information systems (GIS).
 *  
 *  Which employees are between x1 and x2 years of age, make between y1 and y2 dollars 
 *  a year, have been working between z1 and z2 months and have never been arrested?
 *  
 *  Input: 	{{aaj, 0}, {abc, 1}, {hue, 2}, {klf, 3}, (bnm, 4}}
 *  		Range searching: [ab, c]
 *  Output: abc 1
 *  		bnm 4
 *************************************************************************/

public class RangeSearch {
    private Node root;   // root of the BST

    // BST helper node data type
    private class Node {
        String key;            // key
        int val;          // associated data
        Node left, right;   // left and right subtrees
        int N;              // node count of descendents

        public Node(String key, int val) {
            this.key = key;
            this.val = val;
            this.N   = 1;
        }
    }

   /*************************************************************************
    *  BST search
    *************************************************************************/

    public boolean contains(String key) {
        return (get(key) != -1); // -1 is not found
    }

    // return value associated with the given key
    // if no such value, return null
    public int get(String key) {
        return get(root, key);
    }

    private int get(Node x, String key) {
        if (x == null) return -1;	// not found
        int cmp = key.compareTo(x.key);
        if      (cmp == 0) return x.val;
        else if (cmp  < 0) return get(x.left,  key);
        else               return get(x.right, key);
    }

   /*************************************************************************
    *  randomized insertion
    *************************************************************************/
    
    public void put(String key, int val) {
        root = put(root, key, val);
    }

    // make new node the root with uniform probability
    private Node put(Node x, String key, int val) {
        if (x == null) return new Node(key, val);
        int cmp = key.compareTo(x.key);
        if (cmp == 0) { x.val = val; return x; }
        if (bernoulli(1.0 / (size(x) + 1.0))) return putRoot(x, key, val);
        if (cmp < 0) x.left  = put(x.left,  key, val); 
        else         x.right = put(x.right, key, val); 
        fix(x);
        return x;
    }

    private Node putRoot(Node x, String key, int val) {
        if (x == null) return new Node(key, val);
        int cmp = key.compareTo(x.key);
        if      (cmp == 0) { x.val = val; return x; }
        else if (cmp  < 0) { x.left  = putRoot(x.left,  key, val); x = rotR(x); }
        else               { x.right = putRoot(x.right, key, val); x = rotL(x); }
        return x;
    }
    
    private static boolean bernoulli(double p) {
        /*if (!(p >= 0.0 && p <= 1.0))
           System.out.println("Probability must be between 0.0 and 1.0");*/
        
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        return random.nextDouble() < p;
    }

   /*************************************************************************
    *  deletion
    *************************************************************************/
    
    private Node joinLR(Node a, Node b) { 
        if (a == null) return b;
        if (b == null) return a;

        if (bernoulli((double) size(a) / (size(a) + size(b))))  {
            a.right = joinLR(a.right, b);
            fix(a);
            return a;
        }
        else {
            b.left = joinLR(a, b.left);
            fix(b);
            return b;
        }
    }

    private Node remove(Node x, String key) {
        if (x == null) return null; 
        int cmp = key.compareTo(x.key);
        if      (cmp == 0) x = joinLR(x.left, x.right);
        else if (cmp  < 0) x.left  = remove(x.left,  key);
        else               x.right = remove(x.right, key);
        fix(x);
        return x;
    }

    // remove and return value associated with given key; if no such key, return null
    public int remove(String key) {
        int val = get(key);
        root = remove(root, key);
        return val;
    }

   /*************************************************************************
    *  Range searching
    *************************************************************************/

    // return all keys in given interval
    public Iterable<String> range(String low, String high) {
    	Queue<String> list = new LinkedList<String>();
        range(root, low, high, list);
        return list;
    }
    private void range(Node x, String low, String high, Queue<String> list) {
        if (x == null) return;
        if (!less(x.key, low))  range(x.left, low, high, list);
        if (inRange(x.key, low, high))    list.add(x.key);	//list.enqueue(x.key);
        if (!less(high, x.key)) range(x.right, low, high, list);
    }

   /*************************************************************************
    *  Utility functions
    *************************************************************************/
	// is x between low and high
	public boolean inRange(String x, String low, String high) {
		return !less(x, low) && !less(high, x);
	}
    
    // return the smallest key
    public String min() {
        String key = null;
        for (Node x = root; x != null; x = x.left)
            key = x.key;
        return key;
    }
    
    // return the largest key
    public String max() {
        String key = null;
        for (Node x = root; x != null; x = x.right)
            key = x.key;
        return key;
    }


   /*************************************************************************
    *  useful binary tree functions
    *************************************************************************/

    // return number of nodes in subtree rooted at x
    public int size() { return size(root); }
    private int size(Node x) { 
        if (x == null) return 0;
        else           return x.N;
    }

    // height of tree (empty tree height = 0)
    public int height() { return height(root); }
    private int height(Node x) {
        if (x == null) return 0;
        return 1 + Math.max(height(x.left), height(x.right));
    }


   /*************************************************************************
    *  helper BST functions
    *************************************************************************/

    // fix subtree count field
    private void fix(Node x) {
        if (x == null) return;                 // check needed for remove
        x.N = 1 + size(x.left) + size(x.right);
    }

    // right rotate
    private Node rotR(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        fix(h);
        fix(x);
        return x;
    }

    // left rotate
    private Node rotL(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        fix(h);
        fix(x);
        return x;
    }

   /*************************************************************************
    *  Debugging functions that test the integrity of the tree
    *************************************************************************/

    // check integrity of subtree count fields
    public boolean check() { return checkCount() && isBST(); }

    // check integrity of count fields
    private boolean checkCount() { return checkCount(root); }
    private boolean checkCount(Node x) {
        if (x == null) return true;
        return checkCount(x.left) && checkCount(x.right) && (x.N == 1 + size(x.left) + size(x.right));
    }

    // does this tree satisfy the BST property?
    private boolean isBST() { return isBST(root, min(), max()); }

    // are all the values in the BST rooted at x between min and max, and recursively?
    private boolean isBST(Node x, String min, String max) {
        if (x == null) return true;
        if (less(x.key, min) || less(max, x.key)) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    } 

   /*************************************************************************
    *  helper comparison functions
    *************************************************************************/

    private boolean less(String k1, String k2) {
        return k1.compareTo(k2) < 0;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        RangeSearch st = new RangeSearch();
        
    	int n, value;
    	String key, low, high;
    	
    	n = sc.nextInt();
        
    	for(int i = 0; i < n; i++) {
    		key = sc.next();
    		value = i;
    		st.put(key, value);
    	}

       /* System.out.println("height:          " + st.height());
        System.out.println("size:            " + st.size());
        System.out.println("min key:         " + st.min());
        System.out.println("max key:         " + st.max());
        System.out.println("integrity check: " + st.check());
        System.out.println();*/

        low = sc.next();
        high = sc.next();
        
        Iterable<String> list = st.range(low, high);
        
        for (String s1 : list)
            System.out.println(s1 + " " + st.get(s1));
        System.out.println();
    }
}
