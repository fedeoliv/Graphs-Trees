public class BTree  {
    private static final int M = 4;    // max children per B-tree node = M-1

    private Node root;             // root of the B-tree
    private int HT;                // height of the B-tree
    private int N;                 // number of key-value pairs in the B-tree

    // helper B-tree node data type
    private static final class Node {
        private int m;                             // number of children
        private Entry[] children = new Entry[M];   // the array of children
        private Node(int k) { m = k; }             // create a node with k children
    }

    // internal nodes: only use key and next
    // external nodes: only use key and value
    private static class Entry {
        private String key;
        private String value;
        private Node next;     // helper field to iterate over array entries
        public Entry(String key, String value, Node next) {
            this.key   = key;
            this.value = value;
            this.next  = next;
        }
    }

    // constructor
    public BTree() { root = new Node(0); }
 
    // return number of key-value pairs in the B-tree
    public int size() { return N; }

    // return height of B-tree
    public int height() { return HT; }


    // search for given key, return associated value; return null if no such key
    public String get(String key) { return search(root, key, HT); }
    
    private String search(Node x, String key, int ht) {
        Entry[] children = x.children;

        // external node
        if (ht == 0) {
            for (int j = 0; j < x.m; j++) {
                if (key.compareTo(children[j].key) == 0) return children[j].value;
            }
        }

        // internal node
        else {
            for (int j = 0; j < x.m; j++) {
                if (j+1 == x.m || key.compareTo(children[j+1].key) < 0)
                    return search(children[j].next, key, ht-1);
            }
        }
        return null;
    }


    // insert key-value pair
    // add code to check for duplicate keys
    public void insert(String key, String value) {
        Node u = insert(root, key, value, HT); 
        N++;
        if (u == null) return;

        // need to split root
        Node t = new Node(2);
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        HT++;
    }

    private Node insert(Node h, String key, String value, int ht) {
        int j;
        Entry t = new Entry(key, value, null);

        // external node
        if (ht == 0) {
            for (j = 0; j < h.m; j++) {
                if (key.compareTo(h.children[j].key) < 0) break;
            }
        }

        // internal node
        else {
            for (j = 0; j < h.m; j++) {
                if ((j+1 == h.m) || key.compareTo(h.children[j+1].key) < 0) {
                    Node u = insert(h.children[j++].next, key, value, ht-1);
                    if (u == null) return null;
                    t.key = u.children[0].key;
                    t.next = u;
                    break;
                }
            }
        }

        for (int i = h.m; i > j; i--) h.children[i] = h.children[i-1];
        h.children[j] = t;
        h.m++;
        if (h.m < M) return null;
        else         return split(h);
    }

    // split node in half
    private Node split(Node h) {
        Node t = new Node(M/2);
        h.m = M/2;
        for (int j = 0; j < M/2; j++)
            t.children[j] = h.children[M/2+j]; 
        return t;    
    }

    // for debugging
    public String print() {
        return print(root, HT, "") + "\n";
    }
    private String print(Node h, int ht, String indent) {
        String s = "";
        Entry[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j < h.m; j++) {
                s += indent + children[j].key + " " + children[j].value + "\n";
            }
        }
        else {
            for (int j = 0; j < h.m; j++) {
                if (j > 0) s += indent + "(" + children[j].key + ")\n";
                s += print(children[j].next, ht-1, indent + "     ");
            }
        }
        return s;
    }

    public static void main(String[] args) {
        BTree st = new BTree();

        st.insert("www.cs.princeton.edu", "128.112.136.11");
        st.insert("www.princeton.edu",    "128.112.128.15");
        st.insert("www.yale.edu",         "130.132.143.21");
        st.insert("www.simpsons.com",     "209.052.165.60");
        


        System.out.println("cs.princeton.edu:  " + st.get("www.cs.princeton.edu"));
        System.out.println("hardvardsucks.com: " + st.get("www.harvardsucks.com"));
        System.out.println("simpsons.com:      " + st.get("www.simpsons.com"));

        System.out.println("\nsize:    " + st.size());
        System.out.println("height:  " + st.height() + "\n");
        System.out.println(st.print());
    }
}
