/* BFS is levelOrder() and DFS is any other type of order. Print all leaf using printLeaf().*/
class BinaryTree {
	class Node {
		int data;
		Node left, right;
	}
	
	Node root;
	
	BinaryTree() {
		root = null;
	}

	void insert(int data) {
		root = insert(root, data);
	}

	Node insert(Node root, int data) {
		if(root == null){
			Node aux = new Node();
			aux.data = data;
			aux.left = null;
			aux.right = null;
			return aux;
		}
		
		if(root.data > data) {
			root.left = insert(root.left, data);
			return root;
		}	
		
		if(root.data < data) {
			root.right = insert(root.right, data);
			return root;
		}	
		
		System.out.println("Key has already been included.");
		return root;
	}
	
	void search(int data) {
		System.out.println(search(root, data));
	}
	
	int search(Node root, int data) {
		if(root == null) return -1;
		if(root.data == data) return root.data;
		
		if(root.data > data) return search(root.left, data);
		else return search(root.right, data);
	}
	
	void excluir(int data) {
		root = excluir(root, data);
	}
	
	Node excluir(Node r, int data) {
		if(search(r, data) != -1) {
			if (r.data == data) {
				if (r.left == r.right) return null;
				if (r.left == null) return r.right;
				if (r.right == null) return r.left;
				
				Node aux = r.right;
				while (aux.left != null) aux = aux.left;
				
				aux.left = r.left;
				return aux;
				
			} else if (r.data < data) r.right = excluir(r.right, data);
			else r.left = excluir(r.left, data);
		}
		
		return r;
	}
	
	void height() {
		System.out.println(height(root));
	}
	
	int height(Node r) {
		if (r == null) return 0;
	    
		int left = height(r.left);
	    int right = height(r.right);
	    
	    if(left > right) return left + 1;
	    else return right + 1;
	}
	
	void preOrder(){
		preOrder(root);
	}
	
	void preOrder(Node root){
		if(root != null) {
			System.out.print(root.data + " ");
			preOrder(root.left);
			preOrder(root.right);
		}
	} 				

	void inOrder(){
		inOrder(root);
	}

	void inOrder(Node root){
		if(root != null) {
			inOrder(root.left);
			System.out.print(root.data + " ");
			inOrder(root.right);
		}
	} 				

	void postOrder(){
		postOrder(root);
	}

	void postOrder(Node root){
		if(root != null){
			postOrder(root.left);
			postOrder(root.right);
			System.out.print(root.data + " ");
		}
	}
	
	void levelOrder(){
		levelOrder(root);
	}
	
	void levelOrder(Node root) {
		int h = height(root);
		
		for(int i = 0; i < h; i++) printLevel(root, i);
	}
	
	void printLevel(Node raiz, int level) {
		if(raiz == null) return;
		if(level == 0) System.out.print(raiz.data + " ");
		else {
			printLevel(raiz.left, level - 1);
			printLevel(raiz.right, level - 1);
		}
	}
	
	void printLeaf() {
		printLeaf(root);
	}
	
	void printLeaf(Node r) {
		if (r != null) {
			if (r.left == null && r.right == null)
				System.out.print(r.data + " ");

			printLeaf(r.left);
			printLeaf(r.right);
		}
	}
	
	public static void main(String args[]) {
		BinaryTree b = new BinaryTree();
		b.insert(20);
		b.insert(8);
		b.insert(4);
		b.insert(12);
		b.insert(10);
		b.insert(14);
		b.insert(22);
		b.insert(25);
	}	
}
