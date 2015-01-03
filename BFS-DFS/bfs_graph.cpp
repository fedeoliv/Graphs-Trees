#include <bits/stdc++.h>

using namespace std;

/*
    Breadth-first search is appropriate if we don't care which order we visit
    the vertices and edges of the graph, so any order is appropriate.
    Also, we can use it if we are interested in shortest paths on unweighted graphs.
    Unlike trees, graphs may contain cycles, so we may come to the same node again.
*/

/* This class represents a directed graph using adjacency list representation. */
class Graph {
    int V;              /* Number of vertices */
    list<int> *adj;     /* Pointer to array containing adjacency lists */

    public:
        Graph(int V);               /* Constructor */
        void addEdge(int v, int w); /* Function to add an edge to graph */
        void BFS(int s);            /* Prints BFS traversal from a given source s */
};

Graph::Graph(int V) {
    this->V = V;
    adj = new list<int>[V];
}

void Graph::addEdge(int v, int w) {
    adj[v].push_back(w); /* Add w to v list. */
}

void Graph::BFS(int s) {
    /* Mark all the vertices as not visited */
    bool *visited = new bool[V];
    for(int i = 0; i < V; i++) visited[i] = false;

    list<int> queue; /* Create a queue for BFS */

    /* Mark the current node as visited and enqueue it */
    visited[s] = true;
    queue.push_back(s);

    list<int>::iterator i; /* Will be used to get all adjacent vertices of a vertex */

    while(!queue.empty()) {
        /* Dequeue a vertex from queue and print it */
        s = queue.front();
        printf("%d ", s);
        queue.pop_front();

        /*  Get all adjacent vertices of the dequeued vertex s.
            If an adjacent has not been visited, then mark it visited and enqueue it. */
        for(i = adj[s].begin(); i != adj[s].end(); ++i) {
            if(!visited[*i]) {
                visited[*i] = true;
                queue.push_back(*i);
            }
        }
    }
}

int main() {
    Graph g(4);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 2);
    g.addEdge(2, 0);
    g.addEdge(2, 3);
    g.addEdge(3, 3);
    //g.BFS(2);

    for(int i = 0; i < 4; i++) {
        printf("Starting from vertex %d: ", i);
        g.BFS(i);
        printf("\n");
    }

    return 0;
}
