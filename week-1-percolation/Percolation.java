import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] data;
    private WeightedQuickUnionUF uf1, uf2;
    private int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be an integer greater than 0");
        } else {
            this.n = n;
            data = new boolean[n * n];
            this.uf1 = new WeightedQuickUnionUF(n * n + 2);
            this.uf2 = new WeightedQuickUnionUF(n * n + 2);
            for (int i = 0; i < n; i ++) {
                uf1.union(i, n * n);
                uf2.union(i, n * n);

                uf1.union(i + n * (n - 1), n * n + 1);
            }
        }
    }

    private void union(int i, int j) {
        this.uf1.union(i, j);
        this.uf2.union(i, j);
    }

    private int getIndex(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IndexOutOfBoundsException("row:" + row + " or col:" + col + " is invalid.");
        }
        return (row - 1) * n + col - 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        this.data[this.getIndex(row, col)] = true;

        if (row > 1 && this.data[this.getIndex(row - 1, col)]) {
            this.union(this.getIndex(row, col), this.getIndex(row - 1, col));
        }
        if (row < n && this.data[this.getIndex(row + 1, col)]) {
            this.union(this.getIndex(row, col), this.getIndex(row + 1, col));
        }
        if (col > 1 && this.data[this.getIndex(row, col - 1)]) {
            this.union(this.getIndex(row, col), this.getIndex(row, col - 1));
        }
        if (col < n && this.data[this.getIndex(row, col + 1)]) {
            this.union(this.getIndex(row, col), this.getIndex(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return this.data[this.getIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return this.data[this.getIndex(row, col)] && (uf2.find(this.getIndex(row, col)) == uf2.find(0));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int total = 0;
        for (int i = 0; i < n * n; i++) if (this.data[i]) total ++;
        return total;
    }

    // does the system percolate?
    public boolean percolates() {
        return n == 1 ? this.data[0] : uf1.connected(0, n * n + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation perc = new Percolation(10);
        perc.open(2,4);
        perc.open(3,4);
        perc.open(1,4);
    }
}