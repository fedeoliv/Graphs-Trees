#include <stdio.h>
#include <limits.h>
#include <string.h>
#include <queue>
#define MAXV 6
using namespace std;

/*  Returns true if there is a path from source 's' to sink 't' in
    residual graph. Also fills parent[] to store the path */
int bfs(int rGraph[MAXV][MAXV], int s, int t, int parent[]) {
    // Create a visited array and mark all vertices as not visited
    bool visited[MAXV];
    memset(visited, 0, sizeof(visited));

    // Create a queue, enqueue source vertex and mark source vertex as visited
    queue <int> q;
    q.push(s);
    visited[s] = true;
    parent[s] = -1;

    // Standard BFS Loop
    while(!q.empty()) {
        int u = q.front();
        q.pop();

        for(int v = 0; v < MAXV; v++) {
            if(visited[v] == false && rGraph[u][v] > 0) {
                q.push(v);
                parent[v] = u;
                visited[v] = true;
            }
        }
    }

    // If we reached sink in BFS starting from source, then return true, else false
    return (visited[t] == true);
}

/*  A DFS based function to find all reachable vertices from s.
    The function marks visited[i] as true if i is reachable from s. */
void dfs(int rGraph[MAXV][MAXV], int s, bool visited[]) {
    visited[s] = true;
    for (int i = 0; i < MAXV; i++)
       if(rGraph[s][i] && !visited[i])
           dfs(rGraph, i, visited);
}

// Returns tne maximum flow from s to t in the given graph
int fordFulkerson(int graph[MAXV][MAXV], int s, int t) {
    int u, v;
    int rGraph[MAXV][MAXV];     // Residual graph

    for(u = 0; u < MAXV; u++)
        for(v = 0; v < MAXV; v++)
             rGraph[u][v] = graph[u][v];

    int parent[MAXV];  // This array is filled by BFS and to store path
    int max_flow = 0;  // There is no flow initially

    // Augment the flow while tere is path from source to sink
    while(bfs(rGraph, s, t, parent)) {
        /*  Find minimum residual capacity of the edhes along the path filled by BFS.
            Or we can say find the maximum flow through the path found. */
        int path_flow = INT_MAX;

        for(v = t; v != s; v = parent[v]) {
            u = parent[v];
            path_flow = min(path_flow, rGraph[u][v]);
        }

        // Update residual capacities of the edges and reverse edges along the path
        for(v = t; v != s; v = parent[v]) {
            u = parent[v];
            rGraph[u][v] -= path_flow;
            rGraph[v][u] += path_flow;
        }

        // Add path flow to overall flow
        max_flow += path_flow;
    }

    printf("Minimum cut:\n");
    // Flow is maximum now, find vertices reachable from s
    bool visited[MAXV];
    memset(visited, false, sizeof(visited));
    dfs(rGraph, s, visited);

    // Print all edges that are from a reachable vertex to
    // non-reachable vertex in the original graph
    for (int i = 0; i < MAXV; i++)
      for (int j = 0; j < MAXV; j++)
         if (visited[i] && !visited[j] && graph[i][j])
              printf("%d - %d\n", i, j);
              //cout << i << " - " << j << endl;

    // Return the overall flow
    return max_flow;
}

int main() {
    // Let us create a graph shown in the above example
    int graph[MAXV][MAXV] = { {0, 16, 13, 0, 0, 0},
                        {0, 0, 10, 12, 0, 0},
                        {0, 4, 0, 0, 14, 0},
                        {0, 0, 9, 0, 0, 20},
                        {0, 0, 0, 7, 0, 4},
                        {0, 0, 0, 0, 0, 0}
                      };
    printf("Maximum flow: %d\n", fordFulkerson(graph, 0, 5));
    return 0;
}
