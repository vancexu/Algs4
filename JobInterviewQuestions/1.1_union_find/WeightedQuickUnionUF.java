public class WeightedQuickUnionUF {
	private int id[]; // id[i] = parents of node i
	private int sz[]; // sz[i] = size of node i
	private int count; // number of connected components

	public WeightedQuickUnionUF(int N) {
		count = N;
		id = new int[N];
		sz = new int[N];
		for (int i = 0; i < N; ++i) {
			id[i] = i;
			sz[i] = 1;
		}
	}

	public int count() {
		return count;
	}

	private int root(int p) {
		while (id[p] != p) {
			id[p] = id[id[p]]; // path compression
			p = id[p];
		}
		return p;
	}

	public void union(int p, int q) {
		int rootp = root(p);
		int rootq = root(q);
		if (rootp == rootq) return;
		if (sz[rootp] < sz[rootq]) {
			id[rootp] = rootq;
			sz[rootq] += sz[rootp];
		} else {
			id[rootq] = rootp;
			sz[rootp] += sz[rootq];
		}
		count--;
	}

	public boolean connected(int p, int q) {
		return root(p) == root(q);
	}

	public static void main(String[] args) {
		WeightedQuickUnionUF uf = new WeightedQuickUnionUF(10);
		StdOut.println(uf.count() == 10);
		uf.union(0, 1);
		StdOut.println(uf.count() == 9);
		uf.union(2, 3);
		StdOut.println(uf.count() == 8);
		uf.union(0, 2);
		StdOut.println(uf.count() == 7);
		uf.union(1, 3);
		StdOut.println(uf.count() == 7);
	}
}
