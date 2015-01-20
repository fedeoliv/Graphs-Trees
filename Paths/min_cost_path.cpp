#include<stdio.h>
#include<limits.h>
#define MAXM 3
#define MAXN 3

/*  Given a cost matrix cost[][] and a position (m, n) in cost[][], write a function that returns cost
    of minimum path to reach (m, n) from (0, 0). Each cell of the matrix represents a cost to traverse
    through that cell. Total cost of a path to reach (m, n) is sum of all the costs on that path
    (including both source and destination).

    cost[] = {{1, 2, 3}, {4, 8, 2}, {1, 5, 3}}
    target = (2, 2)
    output: 8 */
int min(int x, int y, int z) {
   if(x < y) return (x < z)? x : z;
   else return (y < z)? y : z;
}

int minCost(int cost[MAXM][MAXN], int m, int n) {
     int i, j;

     /* Instead of following line, we can use int tc[m+1][n+1] or
        dynamically allocate memoery to save space. The following line is
        used to keep te program simple and make it working on all compilers. */
     int tc[MAXM][MAXN];

     tc[0][0] = cost[0][0];

     /* Initialize first column of total cost(tc) array */
     for(i = 1; i <= m; i++)
        tc[i][0] = tc[i-1][0] + cost[i][0];

     /* Initialize first row of tc array */
     for(j = 1; j <= n; j++)
        tc[0][j] = tc[0][j-1] + cost[0][j];

     /* Construct rest of the tc array */
     for(i = 1; i <= m; i++)
        for(j = 1; j <= n; j++)
            tc[i][j] = min(tc[i-1][j-1], tc[i-1][j], tc[i][j-1]) + cost[i][j];

     return tc[m][n];
}

int main() {
   int cost[MAXM][MAXN] = {{1, 2, 3}, {4, 8, 2}, {1, 5, 3}};

   printf("%d\n", minCost(cost, 2, 2));
   return 0;
}
