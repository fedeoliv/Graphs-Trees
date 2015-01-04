#include <bits/stdc++.h>
#define MAXV 4

using namespace std;

/*  We assign RED color to the source vertex. Color all the neighbors with BLUE color,
    color all neighbor’s neighbor with RED color, and so on.
    While assigning colors, if we find a neighbor which is colored with same color
    as current vertex, then the graph is not bipartite. */

bool is_bipartite(int G[][MAXV], int src) {
    /*  Create a color array to store colors assigned to all vertices.
        Vertex number is used as index in this array. The value '-1' of colorArr[i]
        is used to indicate that no color is assigned to vertex 'i'.
        The value 1 is used to indicate first color is assigned and value 0
        indicates second color is assigned. */

    int colorArr[MAXV];
    for(int i = 0; i < MAXV; ++i) colorArr[i] = -1;

    colorArr[src] = 1;  /* Assign first color to source */

    /*  Create a queue (FIFO) of vertex numbers and enqueue source vertex for BFS traversal */
    queue <int> q;
    q.push(src);

    /* Run while there are vertices in queue (Similar to BFS) */
    while(!q.empty()) {
        /* Dequeue a vertex from queue */
        int u = q.front();
        q.pop();

        /* Find all non-colored adjacent vertices */
        for(int v = 0; v < MAXV; ++v) {
            /* An edge from u to v exists and destination v is not colored */
            if(G[u][v] && colorArr[v] == -1) {
                /* Assign alternate color to this adjacent v of u*/
                colorArr[v] = 1 - colorArr[u];
                q.push(v);
            }

            /* An edge from u to v exists and destination v is colored with same color as u */
            else if(G[u][v] && colorArr[v] == colorArr[u]) return false;
        }
    }

    /* If we reach here, then all adjacent vertices can be colored with alternate color */
    return true;
}

int main() {
/*
    int G[][MAXV] = {{0, 1, 0, 1},
        {1, 0, 1, 0},
        {0, 1, 0, 1},
        {1, 0, 1, 0}
    };
*/
    int G[MAXV][MAXV];

    for(int i = 0; i < MAXV; i++)
        for(int j = 0; j < MAXV; j++) scanf("%d", &G[i][j]);

    if(is_bipartite(G, G[0][0])) printf("Yes\n");
    else printf("No\n");

    return 0;
}
