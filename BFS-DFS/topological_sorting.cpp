#include <bits/stdc++.h>
using namespace std;

/*  Topological sorting for Directed Acyclic Graph (DAG) is a linear ordering of vertices
    such that for every directed edge uv, vertex u comes before v in the ordering.
    Topological Sorting for a graph is not possible if the graph is not a DAG.*/

/* This class represents a directed graph using adjacency list representation. */
class Graph {
    int V;              /* Number of vertices */
    list<int> *adj;     /* Pointer to array containing adjacency lists */
    void topologicalSortUtil(int v, bool visited[], stack<int> &Stack);

    public:
        Graph(int V);               /* Constructor */
        void addEdge(int v, int w); /* Function to add an edge to graph */
        void topologicalSort();     /* Prints a Topological Sort of the complete graph */
};

Graph::Graph(int V) {
    this->V = V;
    adj = new list<int>[V];
}

void Graph::addEdge(int v, int w) {
    adj[v].push_back(w); /* Add w to v list. */
}

/* A recursive function used by topologicalSort */
void Graph::topologicalSortUtil(int v, bool visited[], stack<int> &Stack) {
    /* Mark the current node as visited. */
    visited[v] = true;

    /* Recur for all the vertices adjacent to this vertex */
    list<int>::iterator i;
    for(i = adj[v].begin(); i != adj[v].end(); ++i)
        if (!visited[*i]) topologicalSortUtil(*i, visited, Stack);

    /* Push current vertex to stack which stores result */
    Stack.push(v);
}

/* The function to do Topological Sort. It uses recursive topologicalSortUtil() */
void Graph::topologicalSort() {
    stack<int> Stack;

    /* Mark all the vertices as not visited */
    bool *visited = new bool[V];
    for(int i = 0; i < V; i++) visited[i] = false;

    /* Call the recursive helper function to store Topological Sort,
       starting from all vertices one by one. */
    for(int i = 0; i < V; i++)
      if(visited[i] == false) topologicalSortUtil(i, visited, Stack);

    /* Print contents of stack */
    while(!Stack.empty()) {
        printf("%d ", Stack.top());
        Stack.pop();
    }
}

int main() {
    Graph g(6);
    g.addEdge(5, 2);
    g.addEdge(5, 0);
    g.addEdge(4, 0);
    g.addEdge(4, 1);
    g.addEdge(2, 3);
    g.addEdge(3, 1);

    printf("Topological sort: ");
    g.topologicalSort();

    return 0;
}
