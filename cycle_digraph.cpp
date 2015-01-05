#include <bits/stdc++.h>

using namespace std;

/* Given a directed graph, check whether the graph contains a cycle or not. */

/* This class represents a directed graph using adjacency list representation. */
class Graph {
    int V;              /* Number of vertices */
    list<int> *adj;     /* Pointer to array containing adjacency lists */
    bool isCyclicUtil(int v, bool visited[], bool *rs);  /* Used by isCyclic() */

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

bool Graph::isCyclicUtil(int v, bool visited[], bool *recStack) {
    if(visited[v] == false) {
        /* Mark the current node as visited and part of recursion stack */
        visited[v] = true;
        recStack[v] = true;

        /* Recur for all the vertices adjacent to this vertex */
        list<int>::iterator i;

        for(i = adj[v].begin(); i != adj[v].end(); ++i) {
            if(!visited[*i] && isCyclicUtil(*i, visited, recStack)) return true;
            else if (recStack[*i]) return true;
        }
    }

    recStack[v] = false;  /* Remove the vertex from recursion stack */
    return false;
}

/* This function is a variation of DFS. */
bool Graph::isCyclic() {
    /* Mark all the vertices as not visited and not part of recursion stack */
    bool *visited = new bool[V];
    bool *recStack = new bool[V];

    for(int i = 0; i < V; i++) {
        visited[i] = false;
        recStack[i] = false;
    }

    /* Call the recursive helper function to detect cycle in different DFS trees */
    for(int i = 0; i < V; i++)
        if(isCyclicUtil(i, visited, recStack)) return true;

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

    if(g.isCyclic()) printf("Graph contains cycle\n");
    else printf("Graph doesn't contain cycle");

    return 0;
}
