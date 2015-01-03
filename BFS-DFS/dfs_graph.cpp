#include <bits/stdc++.h>
using namespace std;

/*
    Possible applications:
    -   For an unweighted graph, DFS traversal of the graph produces the minimum spanning tree
        and all pair shortest path tree.
    -   Detecting cycle in a graph. A graph has cycle if and only if we see a back edge during DFS.
    -   Path Finding. We can specialize the DFS algorithm to find a path between two given vertices u and z.

*/


/* This class represents a directed graph using adjacency list representation. */
class Graph {
    int V;                                               /* Number of vertices */
    list<int> *adj;                                      /* Pointer to array containing adjacency lists */
    void DFSUtil(int v, bool visited[]);                 /* A function used by DFS */
    bool isCyclicUtil(int v, bool visited[], bool *rs);  /* Used by is_cyclic() */

    public:
        Graph(int V);               /* Constructor */
        void addEdge(int v, int w); /* Function to add an edge to graph */
        void DFS(int v);            /* DFS traversal of the vertices reachable from v */
        bool is_cyclic();            /* Returns true if there is a cycle in this graph */
};

Graph::Graph(int V) {
    this->V = V;
    adj = new list<int>[V];
}

void Graph::addEdge(int v, int w) {
    adj[v].push_back(w); /* Add w to v’s list. */
}

void Graph::DFSUtil(int v, bool visited[]) {
    /* Mark the current node as visited and print it */
    visited[v] = true;
    printf("%d ", v);

    /* Recur for all the vertices adjacent to this vertex */
    list<int>::iterator i;
    for(i = adj[v].begin(); i != adj[v].end(); ++i)
        if(!visited[*i]) DFSUtil(*i, visited);
}

/* DFS traversal of the vertices reachable from v. It uses recursive DFSUtil() */
void Graph::DFS(int v) {
    /* Mark all the vertices as not visited */
    bool *visited = new bool[V];
    for(int i = 0; i < V; i++) visited[i] = false;

    /* Call the recursive helper function to print DFS traversal */
    DFSUtil(v, visited);
}

/* This function is a variation of DFSUytil() */
bool Graph::isCyclicUtil(int v, bool visited[], bool *recStack) {
    if(visited[v] == false) {
        /* Mark the current node as visited and part of recursion stack */
        visited[v] = true;
        recStack[v] = true;

        /* Recur for all the vertices adjacent to this vertex */
        list<int>::iterator i;
        for(i = adj[v].begin(); i != adj[v].end(); ++i) {
            if (!visited[*i] && isCyclicUtil(*i, visited, recStack)) return true;
            else if(recStack[*i]) return true;
        }
    }

    recStack[v] = false;  /* Remove the vertex from recursion stack */
    return false;
}

/* Returns true if the graph contains a cycle, else false. */
bool Graph::is_cyclic() {
    /* Mark all the vertices as not visited and not part of recursion stack */
    bool *visited = new bool[V];
    bool *recStack = new bool[V];

    for(int i = 0; i < V; i++) {
        visited[i] = false;
        recStack[i] = false;
    }

    /* Call the recursive helper function to detect cycle in different DFS trees */
    for(int i = 0; i < V; i++)
        if (isCyclicUtil(i, visited, recStack)) return true;

    return false;
}

int main() {
    Graph g(4);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 2);
    g.addEdge(2, 0);
    g.addEdge(2, 3);
    g.addEdge(3, 3);

    for(int i = 0; i < 4; i++) {
        printf("Starting from vertex %d: ", i);
        g.DFS(i);
        printf("\n");
    }

    if(g.is_cyclic()) printf("Graph cointains cycle.\n");
    else printf("Graph doesn't cointain cycle.\n");

    return 0;
}
