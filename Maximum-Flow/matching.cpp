#include <stdio.h>
#include <string.h>
#define MAX_N 50000
#define MAX_M 50000
#define MAX_E 150000
using namespace std;

/*  Example: N (1 ≤ N ≤ 50,000) cows and M (1 ≤ M ≤ 50,000) bulls. Given a list of P (1 ≤ P ≤ 150,000) potential matches
    between a cow and a bull, compute the greatest number of pairs that can be matched.
    Of course, a cow can be matched to at most one bull, and vice versa.
    Print a single integer that is the maximum number of pairs that can be obtained.

    Input:  The first line contains three integers, N, M, and P. Each of the next P lines contains two
            integers A (1 ≤ A ≤ N) and B (1 ≤ B ≤ M), denoting that cow A can be matched with bull B.

    Input:      Output:
    5 4 6       3
    5 2
    1 2
    4 3
    3 1
    2 2
    4 4 */

int N, M, l[MAX_N], r[MAX_M];
int E, to[MAX_E], next[MAX_E], last[MAX_N];
bool visited[MAX_N];

void init(){
	memset(last,-1,sizeof(int)*N);
	E = 0;
}

void add_edge(int u, int v){
	to[E] = v, next[E] = last[u]; last[u] = E; ++E;
}

bool pairup(int u){
    if (visited[u])  return false;
    visited[u] = true;

    for(int e = last[u];e!=-1;e = next[e]){
        int v = to[e];

        if(r[v]==-1 || pairup(r[v])){
            l[u] = v;
            r[v] = u;
            return true;
        }
    }

    return false;
}

int max_matching() {
    bool change = true;
    memset(l,-1,sizeof(int)*N);
    memset(r,-1,sizeof(int)*M);

    while(change){
        change = false;
        memset(visited,false,sizeof(bool)*N);

        for(int i = 0;i<N;++i)
            if(l[i]==-1) change |= pairup(i);
    }

    int ret = 0;

    for(int i = 0;i<N;++i)
        if(l[i]!=-1) ++ret;

    return ret;
}

int main(){
	int P, u, v;
	scanf("%d %d %d", &N, &M, &P);

	init();

	while(P--){
		scanf("%d %d", &u, &v);
		--u; --v;
		add_edge(u,v);
	}

	printf("%d\n", max_matching());
	return 0;
}
