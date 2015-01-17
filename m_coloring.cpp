#include<stdio.h>
#define V 4 /* Number of vertices in the graph */

 /* Given an undirected graph and a number m, determine if the graph can be colored with at most
    m colors such that no two adjacent vertices of the graph are colored with same color.

    Create following graph and test whether it is 3 colorable (1, 2 and 3)
      (3)---(2)
       |   / |
       |  /  |
       | /   |
      (0)---(1)

    Output: Solution exists. Assigned colors: 1 2 3 2
    */

/* A utility function to print solution */
void printSolution(int color[]) {
    printf("Solution exists\n");
    printf("Assigned colors: ");

    for(int i = 0; i < V; i++)
      printf("%d ", color[i]);
    printf("\n");
}

/* Function to check if the current color assignment is safe for vertex v */
bool isSafe(int v, bool graph[V][V], int color[], int c) {
    for(int i = 0; i < V; i++)
        if(graph[v][i] && c == color[i]) return false;
    return true;
}

/*  A recursive utility function to solve m coloring problem */
bool graphColoringUtil(bool graph[V][V], int m, int color[], int v) {
    /*  Base case: If all vertices are assigned a color then return true */
    if(v == V) return true;

    /*  Consider this vertex v and try different colors */
    for(int c = 1; c <= m; c++) {
        /*  Check if assignment of color c to v is fine */
        if(isSafe(v, graph, color, c)) {
           color[v] = c;

           /* Recur to assign colors to rest of the vertices */
           if(graphColoringUtil(graph, m, color, v+1) == true) return true;

           /* If assigning color c doesn't lead to a solution then remove it */
           color[v] = 0;
        }
    }

    /* If no color can be assigned to this vertex then return false */
    return false;
}

/* This function solves the m Coloring problem using Backtracking.
  It mainly uses graphColoringUtil() to solve the problem. It returns
  false if the m colors cannot be assigned, otherwise return true and
  prints assignments of colors to all vertices. Please note that there
  may be more than one solutions, this function prints one of the
  feasible solutions.*/
void graphColoring(bool graph[V][V], int m) {
    /*  Initialize all color values as 0. This initialization is needed
        to correct functioning of isSafe() */
    int *color = new int[V];
    for(int i = 0; i < V; i++) color[i] = 0;

    /*  Call graphColoringUtil() for vertex 0 */
    if(graphColoringUtil(graph, m, color, 0)) printSolution(color);
    else printf("Solution does not exist\n");
}

int main() {
    bool graph[V][V] = {{0, 1, 1, 1},
        {1, 0, 1, 0},
        {1, 1, 0, 1},
        {1, 0, 1, 0},
    };
    int m = 3; // Number of colors
    graphColoring (graph, m);
    return 0;
}
