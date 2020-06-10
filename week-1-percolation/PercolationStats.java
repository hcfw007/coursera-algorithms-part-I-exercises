import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;
    private int T;
    private int n;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <=0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be greater than 0!");
        }
        this.n = n;
        this.T = trials;
        this.results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            int count = 0;
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                while (percolation.isOpen(row, col)) {
                    row = StdRandom.uniform(1, n + 1);
                    col = StdRandom.uniform(1, n + 1);
                }
                percolation.open(row, col);
                count ++;
            }
            this.results[i] = ((float)count) / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - Math.sqrt(this.stddev()) * 1.96 / Math.sqrt(this.T);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + Math.sqrt(this.stddev()) * 1.96 / Math.sqrt(this.T);
    }

    // test client (see below)
    public static void main(String[] args) {
    {
        int n, trials;
        n = StdIn.readInt();
        trials = StdIn.readInt();
        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}