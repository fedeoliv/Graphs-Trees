package analg.graphs;

public class PrimAdjList {
	private static final class Node {
		int data;
		int weight;
		Node link;
	}
	
	private static class MinHeapNode {
		int v;
		int key;
	}
	
	private static class MinHeap {
		int size;      // Number of heap nodes present currently
	    int pos[];     // This is needed for decreaseKey()
	    MinHeapNode[] array;
	}
	
	Node[] graph;
	int v;
	
	PrimAdjList(int vertice) {
		v = vertice;
		graph = new Node[v];
	}
	
	// A utilit function to create a Min Heap
	private MinHeap createMinHeap() {
		MinHeap m = new MinHeap();
		m.size = 0;
		m.pos = new int[v];
		m.array = new MinHeapNode[v];

		return m;
	}
	
	// A utility function to check if the given minHeap is ampty or not
	private boolean isEmpty(MinHeap minHeap) {
	    return minHeap.size == 0;
	}
	
	// A utility function to create a new Min Heap Node
	private MinHeapNode newMinHeapNode(int v, int key) {
	    MinHeapNode m = new MinHeapNode();
	    m.v = v;
	    m.key = key;
	    
	    return m;
	}
	
	// Standard function to extract minimum node from heap
	private MinHeapNode extractMin(MinHeap minHeap) {
		if (isEmpty(minHeap)) return null;

		// Store the root node
		MinHeapNode root = minHeap.array[0];

		// Replace root node with last node
		MinHeapNode lastNode = minHeap.array[minHeap.size - 1];
		minHeap.array[0] = lastNode;

		// Update position of last node
		minHeap.pos[root.v] = minHeap.size - 1;
		minHeap.pos[lastNode.v] = 0;

		// Reduce heap size and heapify root
		--minHeap.size;
		minHeapify(minHeap, 0);

		return root;
	}
	
	// A standard function to heapify at given idx
	// This function also updates position of nodes when they are swapped.
	// Position is needed for decreaseKey()
	private void minHeapify(MinHeap minHeap, int idx) {
		int smallest, left, right;
		smallest = idx;
		left = 2 * idx + 1;
		right = 2 * idx + 2;

		if (left < minHeap.size
				&& minHeap.array[left].key < minHeap.array[smallest].key)
			smallest = left;

		if (right < minHeap.size
				&& minHeap.array[right].key < minHeap.array[smallest].key)
			smallest = right;

		if (smallest != idx) {
			// The nodes to be swapped in min heap
			MinHeapNode smallestNode = minHeap.array[smallest];
			MinHeapNode idxNode = minHeap.array[idx];

			// Swap positions
			minHeap.pos[smallestNode.v] = idx;
			minHeap.pos[idxNode.v] = smallest;

			// Swap nodes
			// swapMinHeapNode(minHeap.array[smallest], minHeap.array[idx]);
			MinHeapNode t = minHeap.array[smallest];
			minHeap.array[smallest] = minHeap.array[idx];
			minHeap.array[idx] = t;

			minHeapify(minHeap, smallest);
		}
	}
	
	// Function to decreasy key value of a given vertex v. This function
	// uses pos[] of min heap to get the current index of node in min heap
	private void decreaseKey(MinHeap minHeap, int v, int key) {
		// Get the index of v in heap array
		int i = minHeap.pos[v];

		// Get the node and update its key value
		minHeap.array[i].key = key;

		// Travel up while the complete tree is not hepified.
		// This is a O(Logn) loop
		while (i != 0 && minHeap.array[i].key < minHeap.array[(i - 1) / 2].key) {
			// Swap this node with its parent
			minHeap.pos[minHeap.array[i].v] = (i - 1) / 2;
			minHeap.pos[minHeap.array[(i - 1) / 2].v] = i;

			// Swap Nodes
			// swapMinHeapNode(&minHeap.array[i], &minHeap.array[(i - 1) / 2]);
			MinHeapNode t = minHeap.array[i];
			minHeap.array[i] = minHeap.array[(i - 1) / 2];
			minHeap.array[(i - 1) / 2] = t;

			// move to parent index
			i = (i - 1) / 2;
		}
	}
	
	// A utility function to check if a given vertex
	// 'v' is in min heap or not
	private boolean isInMinHeap(MinHeap minHeap, int v) {
	   return minHeap.pos[v] < minHeap.size;
	}
	
	public void addEdge(int src, int dest, int w) {
		addEdge(graph, src, dest, w);
		addEdge(graph, dest, src, w);
	}
	
	private void addEdge(Node[] graph, int src, int dest, int weight) {
	    Node temp = new Node();
		temp.data = dest;
		temp.weight = weight;
		temp.link = graph[src];
		graph[src] = temp;
	}
	
	public void primMST() {
		primMST(graph);
	}

	private void primMST(Node[] graph) {
		int[] parent = new int[v]; 	// Array to store constructed MST
		int[] key = new int[v]; 	// Key values used to pick minimum weight

		// minHeap represents set E
		MinHeap minHeap = createMinHeap();

		// Initialize min heap with all vertices. Key value of
		// all vertices (except 0th vertex) is initially infinite
		for (int i = 1; i < v; ++i) {
			parent[i] = -1;
			key[i] = Integer.MAX_VALUE;
			minHeap.array[i] = newMinHeapNode(i, key[i]);
			minHeap.pos[i] = i;
		}

		// Make key value of 0th vertex as 0 so that it
		// is extracted first
		key[0] = 0;
		minHeap.array[0] = newMinHeapNode(0, key[0]);
		minHeap.pos[0] = 0;

		// Initially size of min heap is equal to V
		minHeap.size = v;

		// In the followin loop, min heap contains all nodes
		// not yet added to MST.
		while (!isEmpty(minHeap)) {
			// Extract the vertex with minimum key value
			MinHeapNode minHeapNode = extractMin(minHeap);
			int u = minHeapNode.v; // Store the extracted vertex number

			// Traverse through all adjacent vertices of u (the extracted
			// vertex) and update their key values
			Node pCrawl = graph[u];

			while (pCrawl != null) {
				int v = pCrawl.data;

				// If v is not yet included in MST and weight of u-v is
				// less than key value of v, then update key value and
				// parent of v
				if (isInMinHeap(minHeap, v) && pCrawl.weight < key[v]) {
					key[v] = pCrawl.weight;
					parent[v] = u;
					decreaseKey(minHeap, v, key[v]);
				}
				pCrawl = pCrawl.link;
			}
		}
		// print edges of MST
		printArr(parent, key);
	}
	
	private void printArr(int[] arr, int[] key) {
		System.out.println("Aresta   Peso");

		for (int i = 1; i < v; i++)
			System.out.println(arr[i] + " - " + i + "    " + key[i]);
	}
	
	public static void main(String[] args) {
		int v = 5;
	    PrimAdjList g = new PrimAdjList(v);
	    
		g.addEdge(0, 1, 2);
		g.addEdge(0, 3, 6);
		g.addEdge(1, 2, 3);
		g.addEdge(1, 3, 8);
		g.addEdge(1, 4, 5);
		g.addEdge(2, 4, 7);
		g.addEdge(3, 4, 9);
		g.primMST();
	}
}
