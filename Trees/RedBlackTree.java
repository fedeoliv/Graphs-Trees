package analg.trees;

public class RedBlackTree {
	private static final boolean RED = true;
	private static final boolean BLACK = false;
	private Node root;

	private class Node {
		private int key;           	// key
        	private Node left, right;  	// links to left and right subtrees
        	private boolean color;     	// color of parent link
        	private int N;             	// subtree count
		
		public Node(int key, boolean color, int N) {
            		this.key = key;
            		this.color = color;
        		 this.N = N;
		 }
	}
	
	private boolean isRed(Node x) {
        if (x == null) return false;
        return (x.color == RED);
    }

    // number of node in subtree rooted at x; 0 if x is null
    private int size(Node x) {
        if (x == null) return 0;
        return x.N;
    }
    
    public int size() { 
    	return size(root); 
    }

    // is this symbol table empty?
    public boolean isEmpty() {
        return root == null;
    }
    
    // value associated with the given key; null if no such key
    public int get(int key) { 
    	return get(root, key); 
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private int get(Node x, int key) {
        while (x != null) {
            if (key < x.key) x = x.left;
            else if (key > x.key) x = x.right;
            else  return x.key;
        }
        return -1;
    }
    
	// is there a key-value pair with the given key?
	public boolean contains(int key) {
		return get(key) != -1;
	}
	
	// insert the key-value pair; overwrite the old value with the new value
    // if the key is already present
    public void insert(int key) {
        root = insert(root, key);
        root.color = BLACK;
    }
    
	// insert the key-value pair in the subtree rooted at h
	private Node insert(Node h, int key) {
		if (h == null) return new Node(key, RED, 1);

		if (key < h.key) h.left = insert(h.left, key);
		else if (key > h.key) h.right = insert(h.right, key);
		//else h.val = val;

		// fix-up any right-leaning links
		if (isRed(h.right) && !isRed(h.left))
			h = rotateLeft(h);
		
		if (isRed(h.left) && isRed(h.left.left))
			h = rotateRight(h);
		
		if (isRed(h.left) && isRed(h.right))
			flipColors(h);
		
		h.N = size(h.left) + size(h.right) + 1;

		return h;
	}
	
	// delete the key-value pair with the minimum key
    public void deleteMin() {
        if (isEmpty()) System.out.println("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        
        if (!isEmpty()) root.color = BLACK;
    }

    // delete the key-value pair with the minimum key rooted at h
    private Node deleteMin(Node h) { 
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }
    
    // delete the key-value pair with the maximum key
    public void deleteMax() {
        if (isEmpty()) System.out.println("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    // delete the key-value pair with the maximum key rooted at h
    private Node deleteMax(Node h) { 
        if (isRed(h.left)) h = rotateRight(h);

        if (h.right == null) return null;

        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.right = deleteMax(h.right);

        return balance(h);
    }
    
    // delete the key-value pair with the given key
    public void delete(int key) { 
        if (!contains(key)) {
            System.err.println("Symbol table does not contain " + key);
            return;
        }

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        
        if (!isEmpty()) root.color = BLACK;
    }
    
    // delete the key-value pair with the given key rooted at h
    private Node delete(Node h, int key) { 
        if (key < h.key)  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else {
            if (isRed(h.left)) h = rotateRight(h);
            if (key == h.key && (h.right == null)) return null;
            
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            
            if (key == h.key) {
                Node x = min(h.right);
                h.key = x.key;
                //h.val = x.val;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }
    
	// height of tree (1-node tree has height 0)
	public int height() {
		return height(root);
	}

	private int height(Node x) {
		if (x == null) return -1;
		return 1 + Math.max(height(x.left), height(x.right));
	}
    
    // make a left-leaning link lean to the right
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }
    
	// flip the colors of a node and its two children
	private void flipColors(Node h) {
		// h must have opposite color of its two children
		h.color = !h.color;
		h.left.color = !h.left.color;
		h.right.color = !h.right.color;
	}
	
	// Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private Node moveRedLeft(Node h) {
    	flipColors(h);
        
    	if (isRed(h.right.left)) { 
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
    	
        return h;
    }
    
	// Assuming that h is red and both h.right and h.right.left
	// are black, make h.right or one of its children red.
	private Node moveRedRight(Node h) {
		flipColors(h);
		
		if (isRed(h.left.left)) {
			h = rotateRight(h);
			flipColors(h);
		}
		
		return h;
	}
	
	// restore red-black tree invariant
    private Node balance(Node h) {
    	if (isRed(h.right)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }
    
	// the smallest key; null if no such key
	public int min() {
		if (isEmpty()) return -1;
		return min(root).key;
	}

	// the smallest key in subtree rooted at x; null if no such key
	private Node min(Node x) {
		if (x.left == null) return x;
		else return min(x.left);
	}
	
	// the largest key; null if no such key
	public int max() {
		if (isEmpty()) return -1;
		return max(root).key;
	}

	// the largest key in the subtree rooted at x; null if no such key
	private Node max(Node x) {
		if (x.right == null) return x;
		else return max(x.right);
	}
	
	// the largest key less than or equal to the given key
    public int getLargestKey(int key) {
        Node x = getLargestKey(root, key);
        if (x == null) return -1;
        else return x.key;
    }    

    // the largest key in the subtree rooted at x less than or equal to the given key
    private Node getLargestKey(Node x, int key) {
        if (x == null) return null;
        
        if (key == x.key) return x;
        if (key < x.key)  return getLargestKey(x.left, key);
        
        Node t = getLargestKey(x.right, key);
        
        if (t != null) return t; 
        else return x;
    }
    
	// the smallest key greater than or equal to the given key
	public int getSmallestKey(int key) {
		Node x = getSmallestKey(root, key);
		if (x == null) return -1;
		else return x.key;
	}

	// the smallest key in the subtree rooted at x greater than or equal to the
	// given key
	private Node getSmallestKey(Node x, int key) {
		if (x == null) return null;
		if (key == x.key) return x;
		if (key > x.key) return getSmallestKey(x.right, key);
		
		Node t = getSmallestKey(x.left, key);
		
		if (t != null) return t;
		else return x;
	}
	
	public void inorder() {
		inorder(root);
	}
	
	private void inorder(Node root) {
	    if (root == null) return;
	    inorder(root.left);
	    System.out.print(root.key + " ");
	    inorder(root.right);
	}
	
	public static void main(String[] args) {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert(5);
	    rbt.insert(3);
	    rbt.insert(7);
	    rbt.insert(2);
	    rbt.insert(4);
	    rbt.insert(6);
	    rbt.insert(8);
	    rbt.insert(11);
	    rbt.inorder();
	}
}
