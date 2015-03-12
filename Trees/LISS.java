/* 	A subset of all tree nodes is an independent set if there is no edge 
 	between any of the two nodes of the subset.
 	
 	Input: {20, 8, 4, 12, 10, 14, 22, 25}
 	Output: 5 (20, 4, 25, 10, 14) */

class LISS {
	class Node {
		int data;
		int liss;
		Node linke,linkd;
	}
	
	Node root;
	
	LISS() {
		root = null;
	}

	void insert(int data) {
		root = insert(root, data);
	}

	Node insert(Node root, int data) {
		if(root == null){
			Node aux = new Node();
			aux.data = data;
			aux.liss = 0;
			aux.linke = null;
			aux.linkd = null;
			return aux;
		}
		
		if(root.data > data) {
			root.linke = insert(root.linke, data);
			return root;
		}	
		
		if(root.data < data) {
			root.linkd = insert(root.linkd, data);
			return root;
		}	
		
		System.out.println("Key has already been included.");
		return root;
	}
	
	public int getLISS() {
		return getLISS(root);
	}
	
	private int getLISS(Node root) {
		if(root == null) return 0;
		if(root.liss != 0) return root.liss;
		if(root.linke == null && root.linkd == null) return root.liss = 1;
		int liss_excl = getLISS(root.linke) + getLISS(root.linkd);
		int liss_incl = 1;
		if(root.linke != null)
			liss_incl += getLISS(root.linke.linke) + getLISS(root.linke.linkd);
		if(root.linkd != null)
			liss_incl += getLISS(root.linkd.linke) + getLISS(root.linkd.linkd);
		root.liss = Math.max(liss_incl, liss_excl);
		return root.liss;
	}
	
	public static void main(String args[]) {
		LISS b = new LISS();
		b.insert(20);
		b.insert(8);
		b.insert(4);
		b.insert(12);
		b.insert(10);
		b.insert(14);
		b.insert(22);
		b.insert(25);
		System.out.println(b.getLISS());
	}	
}
