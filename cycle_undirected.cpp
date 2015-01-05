#include <bits/stdc++.h>

using namespace std;

/* Given a directed graph, check whether the graph contains a cycle or not. */

/* This class represents a directed graph using adjacency list representation. */
class Graph {
    int V;              /* Number of vertices */
    list<int> *adj;     /* Pointer to array containing adjacency lists */
    bool isCyclicUtil(int v, bool visited[], int parent);  /* Used by isCyclic() */

    public:
        Graph(int V);               /* Constructor */
        void addEdge(int v, int w); /* Function to add an edge to graph */
        bool isCyclic();            /* Returns true if there is a cycle in this graph */
};

Graph::Graph(int V) {
    this->V = V;
    adj = new list<int>[V];
}

void Graph::addEdge(int v, int w) {
    adj[v].push_back(w); /* Add w to v list. */
}

/*  A recursive function that uses visited[] and parent to detect cycle
    in subgraph reachable from vertex v. */
bool Graph::isCyclicUtil(int v, bool visited[], int parent) {
    /* Mark the current node as visited */
    visited[v] = true;

    /* Recur for all the vertices adjacent to this vertex */
    list<int>::iterator i;
    for(i = adj[v].begin(); i != adj[v].end(); ++i) {
        /* If an adjacent is not visited, then recur for that adjacent */
        if(!visited[*i])
            if(isCyclicUtil(*i, visited, v)) return true;

        /*  If an adjacent is visited and not parent of current vertex,
            then there is a cycle. */
        else if(*i != parent) return true;
    }

    return false;
}

bool Graph::isCyclic() {
    /* Mark all the vertices as not visited and not part of recursion stack */
    bool *visited = new bool[V];
    for(int i = 0; i < V; i++) visited[i] = false;

    /* Call the recursive helper function to detect cycle in different DFS trees */
    for(int u = 0; u < V; u++)
        if(!visited[u]) /* Don't recur for u if it is already visited */
            if(isCyclicUtil(u, visited, -1)) return true;

    return false;
}

int main() {
    Graph g(5);
    g.addEdge(1, 0);
    g.addEdge(0, 2);
    g.addEdge(2, 0);
    g.addEdge(0, 3);
    g.addEdge(3, 4);

    if(g.isCyclic()) printf("Graph contains cycle\n");
    else printf("Graph doesn't contain cycle");

    return 0;
}
