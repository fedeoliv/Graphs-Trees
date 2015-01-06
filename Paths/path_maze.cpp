#include <stdio.h>
#define gc getchar_unlocked

/*  Validate whether it¡¯s a valid maze or not. A valid maze has exactly one entry point and exactly one exit point
    (exactly 2 openings in the edges) and there must be atleast one path from the entry point to exit point.

    '#' represents a wall and '.' represents a space. */

/* Max 20x20 table */
char mat[20][20];
bool traversed[20][20];

/*  Used to verify next movement. Each movement we need to verify the current position ans next 2 position.
    So, we have a 3x3 table to analyse.*/
int e[2][2];

/* Using DFS */
void traverse(int i, int j, int n, int m) {
	traversed[i][j] = true;

	if(i > 0 && mat[i-1][j] == '.') {
		if(!traversed[i-1][j]) {
			traverse(i-1, j, n, m);
			if(traversed[e[1][0]][e[1][1]]) {
                printf("%d %d\n", i, j);
                return;
			}
		}
	}

	if(j > 0 && mat[i][j-1] == '.') {
		if(!traversed[i][j-1]) {
			traverse(i, j-1, n, m);
			if(traversed[e[1][0]][e[1][1]]) {
                printf("%d %d\n", i, j);
                return;
			}
		}
	}

	if(i < (n-1) && mat[i+1][j] == '.') {
		if(!traversed[i+1][j]) {
			traverse(i+1, j, n, m);
			if(traversed[e[1][0]][e[1][1]]) {
                printf("%d %d\n", i, j);
                return;
			}
		}
	}

	if(j < (m-1) && mat[i][j+1] == '.') {
		if(!traversed[i][j+1]) {
			traverse(i, j+1, n, m);
			if(traversed[e[1][0]][e[1][1]]) {
                printf("%d %d\n", i, j);
                return;
			}
		}
	}
}

int main() {
    freopen("a.txt", "r", stdin);
	int t, m, n, invalid, points;

	for(scanf("%d", &t); t--;) {
		scanf("%d %d", &n, &m);
		invalid = 0, points = 0;

		for(int i = 0; i < n; i++) {
			gc();
			for(int j = 0; j < m; j++) {
				mat[i][j] = gc();
				traversed[i][j] = false;

				/*  A valid maze has exactly one entry point and exactly one exit point.
                    If we have more than 2 openings, is invalid.*/
				if((i == 0 || j == 0 || i == (n-1) || j == (m-1)) && (mat[i][j]=='.')) {
					if(points >= 2) invalid = 1;
					else {
                        e[points][0] = i;
                        e[points][1] = j;
                    }
					points++;
				}
			}
		}

		if(points != 2) {
			printf("invalid\n\n");
			continue;
		}

        /* Printing the initial position */
        printf("%d %d\n", e[1][0], e[1][1]);

		traverse(e[0][0], e[0][1], n, m);

		if(traversed[e[1][0]][e[1][1]]) printf("valid\n\n");
		else printf("invalid\n\n");
	}
	return 0;
}
