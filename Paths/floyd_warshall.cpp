#include <stdio.h>
#define MAXN 51

/*  Find shortest distances between every pair of vertices in a given edge weighted directed Graph.

    Input:
    1
    3
    0 1 1
    1 0 1
    1 9 0
    2
    2 1
    3 2

    1 test case, 3x3 table, 2 pairs. Find shortest distance between these 2 pairs.

    Output:
    Case #1: 3 */

int main() {
    /* Quantidade de distâncias, de ordem 51 x 51.*/
    int d[MAXN][MAXN];
    int T, r, n, i, j, k, x = 0;
    short c = 1;

    scanf("%d", &T);

    while(T--) {
        scanf("%d", &n);

        for(i = 1; i <= n; i++)
            for(j = 1; j <= n; j++)
                scanf("%d", &d[i][j]);

        for (k = 1; k <= n; k++)
            for (i = 1; i <= n; i++)
                for(j = 1; j <= n; j++) {
                    x = d[i][k] + d[k][j];

                    if(d[i][j] > x)
                        d[i][j] = x;
                }

        scanf("%d", &r);

        for(i = 0; i < r; i++) {
            scanf("%d %d", &j, &k);
            x += d[j][k];
        }

        printf("Case #%d: %d\n", c++, x);
    }

    return 0;
}
