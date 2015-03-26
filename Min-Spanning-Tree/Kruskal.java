package analg.graphs;

// Kruskal's MST is good for disconnected graphs,using the concept of Union-Find structure.
public class Kruskal {
  	private static final class Node {
  		  int src;
  		  int dest;
  		  int weight;
  	}
  	
  	// Class to represent a subset of Union Find algorithm.
  	private static class Subset {
  	    int parent;
  	    int rank;
  	};
  	
  	Node[] graph;
  	int v;	// Number of vertices
  	int e; 	// Number of edges
  	int idx;
  	
  	Kruskal(int vertices, int arestas) {
  		  v = vertices;
  		  e = arestas;
  		  idx = 0;
  		  graph = new Node[v+1];
  	}
  	
  	private void addEdge(int src, int dest, int weight) {
  	  	addEdge(graph, src, dest, weight);
  	}
  	
  	private void addEdge(Node[] graph, int src, int dest, int weight) {
  		  Node temp = new Node();
  		  temp.src = src;
  		  temp.dest = dest;
  		  temp.weight = weight;
  		  graph[idx++] = temp;
  	}
  
  	private static Node[] quickSort(Node[] arr, int low, int high) {
    		if (arr == null || arr.length == 0) return arr;
    		if (low >= high) return arr;
     
    		//pick the pivot
    		int middle = low + (high - low) / 2;
    		int pivot = arr[middle].weight;
     
    		//make left < pivot and right > pivot
    		int i = low, j = high;
    		while (i <= j) {
      			while (arr[i].weight < pivot) i++;
      			while (arr[j].weight > pivot) j--;
       
      			if (i <= j) {
      				int tempW = arr[i].weight;
      				int tempSrc = arr[i].src;
      				int tempDest = arr[i].dest;
      				
      				arr[i].weight = arr[j].weight;
      				arr[i].src = arr[j].src;
      				arr[i].dest = arr[j].dest;
      				
      				arr[j].weight = tempW;
      				arr[j].src = tempSrc;
      				arr[j].dest = tempDest;
      				
      				i++;
      				j--;
      			}
  		  }
   
    		//recursively sort two sub parts
    		if (low < j) arr = quickSort(arr, low, j);
    		if (high > i) arr = quickSort(arr, i, high);
    		
    		return arr;
  	}
   
  	// A utility function to find set of an element i
  	// (uses path compression technique)
  	private int find(Subset[] subsets, int i) {
  	    // find root and make root as parent of i (path compression)
  	    if (subsets[i].parent != i)
  	        subsets[i].parent = find(subsets, subsets[i].parent);
  	 
  	    return subsets[i].parent;
  	}
  	
  	// A function that does union of two sets of x and y
  	// (uses union by rank)
  	private void union(Subset[] subsets, int x, int y) {
    		int xroot = find(subsets, x);
    		int yroot = find(subsets, y);
    
    		// Attach smaller rank tree under root of high rank tree
    		// (Union by Rank)
    		if (subsets[xroot].rank < subsets[yroot].rank)
    			  subsets[xroot].parent = yroot;
    		else if (subsets[xroot].rank > subsets[yroot].rank)
    			  subsets[yroot].parent = xroot;
    
    		// If ranks are same, then make one as root and increment
    		// its rank by one
    		else {
      			subsets[yroot].parent = xroot;
      			subsets[xroot].rank++;
    		}
  	}
  	
  	public void kruskalMST() {
  		  kruskalMST(graph);
  	}
  	
  	// The main function to construct MST using Kruskal's algorithm
  	private void kruskalMST(Node[] graph) {
  	    //struct Edge result[V];  // Tnis will store the resultant MST
  	    int e = 0;  // An index variable, used for result[]
  	    int i = 0;  // An index variable, used for sorted edges
  	    
  	    Node[] result = new Node[v];  // This will store the resultant MST
  	 
  	    // Step 1:  Sort all the edges in non-decreasing order of their weight
  	    // If we are not allowed to change the given graph, we can create a copy of
  	    // array of edges
  	    //qsort(graph->edge, graph->E, sizeof(graph->edge[0]), myComp);
  	    graph = quickSort(graph, 0, v);
  	    
  	    // Allocate memory for creating V ssubsets
  	    Subset[] subsets = new Subset[v];
  	 
  	    // Create V subsets with single elements
  	    for (int j = 0; j < v; ++j) {
    	    	Subset temp = new Subset();
    			  temp.parent = j;
    			  temp.rank = 0;
    			  subsets[j] = temp;
  	    }
  	 
  	    // Number of edges to be taken is equal to V-1
  	    while (e < v-1) {
  	        // Step 2: Pick the smallest edge. And increment the index
  	        // for next iteration
  	        Node next_edge = graph[i++];
  	      	//struct Edge next_edge = graph->edge[i++];
  	 
  	        int x = find(subsets, next_edge.src);
  	        int y = find(subsets, next_edge.dest);
  	 
  	        // If including this edge does't cause cycle, include it
  	        // in result and increment the index of result for next edge
  	        if (x != y) {
  	            result[e++] = next_edge;
  	            union(subsets, x, y);
  	        }
  	        // Else discard the next_edge
  	    }
  	 
  	    System.out.println("Aresta   Peso");
  	    
  	    for (i = 0; i < e; ++i)
  	        System.out.println(result[i].src + " - " + result[i].dest + "    " + result[i].weight);
  	}
  	
  	public static void main(String[] args) {
    		int v = 4;
    		int e = 5;
  	    Kruskal g = new Kruskal(v, e);
  	    
    		g.addEdge(0, 1, 10);
    		g.addEdge(0, 2, 6);
    		g.addEdge(0, 3, 5);
    		g.addEdge(1, 3, 15);
    		g.addEdge(2, 3, 4);
    		
    		g.kruskalMST();
    }
}
