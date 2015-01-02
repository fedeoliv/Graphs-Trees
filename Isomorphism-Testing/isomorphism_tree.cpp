#include <cstdio>
#include <cstring>
#include <vector>
#include <algorithm>

using namespace std;
typedef unsigned long long ULL;

/*
    Input:
    - The first line contains the number of nodes N.
    - Each of next N-1 lines contain two integers A, B, denoting that there is an edge in T1 between nodes A and B.
    - Each of next N-1 lines contain two integers A, B, denoting that there is an edge in T2 between nodes A and B.

    Input:      Output: YES
    2
    4
    4 2
    4 1
    2 3
    4 2
    2 3
    4 1
*/


const int MAX_N = 1e5;
int N, V;
vector<int> graph[2][MAX_N + 1];
int dist[MAX_N], dist2[MAX_N];
bool visited[MAX_N + 1];
ULL shaker[MAX_N];

void init(){
	scanf("%d", &N);
	for(int k=0; k<2; k++)
		for(int i=0; i<=N; i++) graph[k][i].clear();

	for(int k=0; k < 2; k++){
		for(int i=0; i<N-1; i++){
			int a, b;
			scanf("%d %d", &a, &b);
			a--; b--;
			graph[k][a].push_back(b);
			graph[k][b].push_back(a);
		}
	}
}

void dfs(int k, int v){
	visited[v] = true;
	for(int i = 0; i < (int)graph[k][v].size(); i++){
		const int u = graph[k][v][i];

		if(!visited[u]) {
			dist[u] = dist[v] + 1;
			dfs(k, u);
		}
	}
}

/* Return the index of the center, if we have two cases there is a pair, when the second one is -1. */
pair<int,int> find_center(int k){
	memset(visited, false, sizeof(visited));
	dist[0] = 0;
	dfs(k, 0);

	int e = max_element(dist, dist + N) - dist;
	memset(visited, false, sizeof(visited));
	memcpy(dist2, dist, sizeof(dist));
	dist[e] = 0;
	dfs(k, e);

	memset(visited, false, sizeof(visited));
	memcpy(dist2, dist, sizeof(dist));
	e = max_element(dist, dist + N) - dist;
	dist[e] = 0;
	dfs(k, e);

	int diameter = *max_element(dist, dist+ N);

	pair<int,int> ret(-1,-1);

	for(int i = 0; i < N; i++){
		if( (dist[i] == diameter/2 || dist2[i] == diameter/2) && dist[i] + dist2[i] == diameter){
			if(ret.first == -1) ret.first = i;
			else ret.second = i;
		}
	}

	return ret;
}

/* Calculate the hash value of the sub-tree whose vertices are there. */
ULL rec(int k, int v){
	ULL ret = 1;
	visited[v] = true;
	vector<ULL> hs;

	for(int i=0; i<(int)graph[k][v].size(); i++) {
		const int u = graph[k][v][i];
		if(!visited[u]) hs.push_back(rec(k, u));
	}

	sort(hs.begin(), hs.end());

	for(int i=0; i<(int)hs.size(); i++)
		ret += hs[i] * shaker[i];

	return ret;
}


ULL calc_hash(int k){
	pair<int,int> center = find_center(k);
	int root = center.first;

	if(center.second != -1){
		root = N;
		const int v = center.first, u = center.second;

		graph[k][root].push_back(v);
		graph[k][root].push_back(u);

		*find(graph[k][v].begin(), graph[k][v].end(), u) = root;
		*find(graph[k][u].begin(), graph[k][u].end(), v) = root;
	}

	memset(visited, false, sizeof(visited));
	return rec(k, root);
}

/* Assuming the size of the node is the same. */
bool is_isomorhic(){
	return calc_hash(0) == calc_hash(1);
}

int main(){
	for(int i = 0; i < MAX_N; i++) shaker[i] = rand();
	int T;
	scanf("%d", &T);

	for(;T--;){
		init();
		if(is_isomorhic()) printf("YES\n");
		else printf("NO\n");
	}

	return 0;
}
