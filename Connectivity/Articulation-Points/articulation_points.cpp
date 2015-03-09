#include <stdio.h>
#include <list>
#define NIL -1
using namespace std;

/*  A vertex in an undirected connected graph is an articulation point (or cut vertex),
    if removing it (and edges through it) disconnects the graph.

    Input: (1,0); (0,2); (2,1); (0,3); (3,4);
    Output: 0 3 */

class Graph {
    int V;    // No. of vertices
    list<int> *adj;    // A dynamic array of adjacency lists
    void APUtil(int v, bool visited[], int disc[], int low[], int parent[], bool ap[]);
    public:
        Graph(int V);   // Constructor
        void addEdge(int v, int w);   // function to add an edge to graph
        void AP();    // prints articulation points
};

Graph::Graph(int V) {
    this->V = V;
    adj = new list<int>[V];
}

void Graph::addEdge(int v, int w) {
    adj[v].push_back(w);
    adj[w].push_back(v);  // Note: the graph is undirected
}

// A recursive function that find articulation points using DFS traversal
// u --> The vertex to be visited next
// visited[] --> keeps tract of visited vertices
// disc[] --> Stores discovery times of visited vertices
// parent[] --> Stores parent vertices in DFS tree
// ap[] --> Store articulation points
void Graph::APUtil(int u, bool visited[], int disc[], int low[], int parent[], bool ap[]) {
    static int time = 0;        // variable by passing a pointer.
    int children = 0;           // Count of children in DFS Tree
    visited[u] = true;          // Mark the current node as visited
    disc[u] = low[u] = ++time;   // Initialize discovery time and low value

    // Go through all vertices aadjacent to this
    list<int>::iterator i;
    for (i = adj[u].begin(); i != adj[u].end(); ++i) {
        int v = *i;  // v is current adjacent of u

        // If v is not visited yet, then make it a child of u in DFS tree and recur for it
        if (!visited[v]) {
            children++;
            parent[v] = u;
            APUtil(v, visited, disc, low, parent, ap);

            // Check if the subtree rooted with v has a connection to one of the ancestors of u
            low[u]  = min(low[u], low[v]);

            // u is an articulation point in following cases:

            // (1) u is root of DFS tree and has two or more chilren.
            if (parent[u] == NIL && children > 1) ap[u] = true;

            // (2) If u isn't root and low value of one of its child is more than discovery value of u.
            if (parent[u] != NIL && low[v] >= disc[u]) ap[u] = true;
        }

        // Update low value of u for parent function calls.
        else if (v != parent[u]) low[u]  = min(low[u], disc[v]);
    }
}

// The function to do DFS traversal. It uses recursive function APUtil()
void Graph::AP() {
    // Mark all the vertices as not visited
    bool *visited = new bool[V];
    int *disc = new int[V];
    int *low = new int[V];
    int *parent = new int[V];
    bool *ap = new bool[V]; // To store articulation points

    // Initialize parent and visited, and ap(articulation point) arrays
    for (int i = 0; i < V; i++) {
        parent[i] = NIL;
        visited[i] = false;
        ap[i] = false;
    }

    // Call the recursive helper function to find articulation points
    // in DFS tree rooted with vertex 'i'
    for (int i = 0; i < V; i++)
        if (visited[i] == false) APUtil(i, visited, disc, low, parent, ap);

    // Now ap[] contains articulation points, print them
    for (int i = 0; i < V; i++)
        if (ap[i] == true) printf("%d ", i);
}

int main() {
    Graph g1(5);
    g1.addEdge(1, 0);
    g1.addEdge(0, 2);
    g1.addEdge(2, 1);
    g1.addEdge(0, 3);
    g1.addEdge(3, 4);
    g1.AP();
    return 0;
}
