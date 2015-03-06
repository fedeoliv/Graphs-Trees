package analg.trees;

class BinaryTree {
	class Node {
		String nome;
		String fone;
		Node linke,linkd;
	}
	
	Node root;
	
	BinaryTree() {
		root = null;
	}

	void insert(String nm, String fn) {
		root = insert(root, nm, fn);
	}

	Node insert(Node root, String nm, String fn) {
		if(root == null){
			Node aux = new Node();
			aux.nome = nm;
			aux.fone = fn;
			aux.linke = null;
			aux.linkd = null;
			return aux;
		}
		
		if(root.nome.compareTo(nm) > 0) {
			root.linke = insert(root.linke, nm, fn);
			return root;
		}	
		
		if(root.nome.compareTo(nm) < 0) {
			root.linkd = insert(root.linkd, nm, fn);
			return root;
		}	
		
		System.out.println("Nome já existe!");
		return root;
	}
	
	void search(String nome) {
		System.out.println("\n2) Busca (" + nome + "): " + search(root, nome));
	}
	
	String search(Node root, String nome) {
		// Se a árvore estiver vazia, retorna "0".
		if(root == null) return "0";
		
		// Se o nome for encontrado, retornamos seu telefone.
		if(root.nome.equals(nome)) return root.fone;
		
		// Se o nome passado por parâmetro for menor que a root, percorremos à esquerda.
		// Senão, percorremos à direita.
		if(root.nome.compareTo(nome) > 0) 
			return search(root.linke, nome);
		else return search(root.linkd, nome);
	}
	
	void excluir(String nome) {
		root = excluir(root, nome);
		System.out.print("3) Exclusão (" + nome + "): ");
		emOrdem();
	}
	
	Node excluir(Node r, String nome) {
		// Se a função de busca retornar "0", é porque o nome não foi encontrado.
		if(!search(r, nome).equals("0")) {
			
			// compareTo = 0 significa que o nome foi encontrado. 
			// Nesse caso, faremos as 3 verificações possíveis de exclusão.
			if (r.nome.compareTo(nome) == 0) {
				// Se linke = linkd, significa que o elemento que estamos excluindo
				// é a root da árvore. Basta dizermos que o nodo é null, já que a root é 
				// o ultimo elemento da árvore.
				if (r.linke == r.linkd) return null;
				
				// Se na esquerda não houver valor, colocamos o da direita pra ocupar o seu lugar.
				if (r.linke == null) return r.linkd;
				
				// Se na direita não houver valor, colocamos o da esquerda pra ocupar o seu lugar.
				if (r.linkd == null) return r.linke;
				
				// Criando o link entre os elementos da direita com os da esquerda.
				// A partir dos elementos da direita da árvore, percorremos todos os elementos
				// da esquerda dele, até chegarmos no último.
				// Quando estamos no último valor, linkamos ele com a árvore à esquerda.
				Node aux = r.linkd;
				
				while (aux.linke != null) aux = aux.linke;
				
				aux.linke = r.linke;
				return aux;
				
			} else if (r.nome.compareTo(nome) < 0) {
				// Se o nome na árvore for menor que nome passado por parâmetro, percorre à direita.
				r.linkd = excluir(r.linkd, nome);
			} else {
				// Se o nome na árvore for maior que nome passado por parâmetro, percorre à esquerda.
				r.linke = excluir(r.linke, nome);
			}
		}
		
		return r;
	}
	
	void height() {
		System.out.println("4) Altura: " + height(root));
	}
	
	int height(Node r) {
		// Se a árvore está vazia, a altura é 0.
	    if (r == null) return 0;
	    
	    // Recursivamente, contamos a quantidade de links que temos da esquerda e da direita.
	    // Se tivermos mais links da esquerda, somamos +1 na esquerda, senão +1 para a direita.
	    // Essa verificação é para sabermos se estamos no próximo nível da árvore ou não.
	    int left = height(r.linke);
	    int right = height(r.linkd);
	    
	    if(left > right) return left + 1;
	    else return right + 1;
	}
	
	void preOrdem(){
		System.out.print("PreOrdem: [");
		preOrdemAux(root);
		System.out.println(" ]");
	}
	
	void preOrdemAux(Node root){
		if(root !=null) {
			System.out.print(" "+root.nome);
			preOrdemAux(root.linke);
			preOrdemAux(root.linkd);
		}
	} 				

	void emOrdem(){
		System.out.print("EmOrdem:  [");
		emOrdemAux(root);
		System.out.println(" ]");
	}

	void emOrdemAux(Node root){
		if(root != null) {
			emOrdemAux(root.linke);
			System.out.print(" "+root.nome);
			emOrdemAux(root.linkd);
		}
	} 				

	void posOrdem(){
		System.out.print("PosOrdem: [");
		posOrdemAux(root);
		System.out.println(" ]");
	}

	void posOrdemAux(Node root){
		if(root != null){
			posOrdemAux(root.linke);
			posOrdemAux(root.linkd);
			System.out.print(" "+root.nome);
		}
	}
	
	void listar() {
		System.out.print("\n1) Nós folha: ");
		listar(root);
	}
	
	void listar(Node r) {
		// Percorremos primeiro os da esquerda e depois os da direita.
		// Se linke e linkd forem nulos, é porque são nós folha.
		if (r != null) {
			if (r.linke == null && r.linkd == null)
				System.out.print("[" + r.nome + " - " + r.fone + "] ");

			listar(r.linke);
			listar(r.linkd);
		}
	}
	
	public static void main(String args[]) {
		BinaryTree a1 = new BinaryTree();
		a1.insert("Lidia", "011 9999999");
		a1.preOrdem();
		a1.emOrdem();
		a1.posOrdem();
		a1.insert("Marcio", "011 8888888");
		a1.preOrdem();
		a1.emOrdem();
		a1.posOrdem();
	}	
}				
