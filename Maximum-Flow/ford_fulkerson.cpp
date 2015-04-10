#include <stdio.h>
#include <climits>
#include <algorithm>
using namespace std;

/*  Given a graph with N (2 ≤ N ≤ 5,000) vertices numbered 1 to N and M (1 ≤ M ≤ 30,000) undirected, weighted edges,
    compute the maximum flow / minimum cut from vertex 1 to vertex N.

    Input:      Output:
    4 6         5
    1 2 3
    2 3 4
    3 1 2
    2 2 5
    3 4 3
    4 3 3 */

struct flow_graph {
	int MAX_V, E, s, t, head, tail;
	int *cap, *to, *next, *last, *dist, *q, *now;

	flow_graph(int V = 0, int MAX_E = 0) {
		MAX_V = V;
		E = 0;
		cap = new int[2 * MAX_E];
		to = new int[2 * MAX_E];
		next = new int[2 * MAX_E];
		last = new int[MAX_V];
		q = new int[MAX_V];
		dist = new int[MAX_V];
		now = new int[MAX_V];
		fill(last, last + MAX_V, -1);
	}

	void add_edge(int u, int v, int uv, int vu = 0) {
		/* Attention: this is undirected graph! */
		to[E] = v, cap[E] = uv, next[E] = last[u];
		last[u] = E++;

		to[E] = u, cap[E] = vu, next[E] = last[v];
		last[v] = E++;
	}

	bool bfs() {
		fill(dist, dist + MAX_V, -1);
		head = tail = 0;
		q[tail] = t;
		++tail;
		dist[t] = 0;

		while (head < tail) {
			int v = q[head];
			++head;
			for (int e = last[v]; e != -1; e = next[e]) {
				if (cap[e ^ 1] > 0 && dist[to[e]] == -1) {
					q[tail] = to[e];
					++tail;
					dist[to[e]] = dist[v] + 1;
				}
			}
		}
		return dist[s] != -1;
	}

	int dfs(int v, int f) {
		if (v == t) return f;

		for (int &e = now[v]; e != -1; e = next[e]) {
			if (cap[e] > 0 && dist[to[e]] == dist[v] - 1) {
				int ret = dfs(to[e], min(f, cap[e]));

				if (ret > 0) {
					cap[e] -= ret;
					cap[e ^ 1] += ret;
					return ret;
				}
			}
		}

		return 0;
	}

	long long compute_max_flow(int source, int sink) {
		s = source;
		t = sink;
		long long f = 0;
		int x;

		while (bfs()) {
			for (int i = 0; i < MAX_V; ++i) now[i] = last[i];

			while (true) {
				x = dfs(s, INT_MAX);
				if(x == 0) break;
				f += x;
			}
		}
		return f;
	}
} graph;

int main() {
	int V, E, u, v, c;

	scanf("%d %d", &V, &E);
	graph = flow_graph(V, E);

	for (int i = 0; i < E; ++i) {
        scanf("%d %d %d", &u, &v, &c);
		graph.add_edge(u - 1, v - 1, c, c);
	}

	printf("%lld\n", graph.compute_max_flow(0, V - 1));
	return 0;
}
