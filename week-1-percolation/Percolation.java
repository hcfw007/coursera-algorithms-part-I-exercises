public class Percolation {
    private boolean[] data;
    private int[] groups;
    private int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be an integer greater than 0");
        } else {
            this.n = n;
            data = new boolean[n * n];
            groups = new int[n * n];
            for (int i = 0; i < n * n; i ++) {
                if (i < n) {
                    groups[i] = -1;
                } else {
                    groups[i] = i;
                }
            }
        }
    }

    private int getIndex(int row, int col) {
        return (row - 1) * n + col - 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        this.data[this.getIndex(row, col)] = true;
        int finalGroup = this.groups[this.getIndex(row, col)];
        if (row > 1 && this.data[this.getIndex(row - 1, col)]) {
            if (this.finalGroup(row - 1, col) == -1) {
                finalGroup = -1;
            }
            this.groups[this.finalIndex(row - 1, col)] = finalGroup;
        }
        if (row < n && this.data[this.getIndex(row + 1, col)]) {
            if (this.finalGroup(row + 1, col) == -1) {
                finalGroup = -1;
            }
            this.groups[this.finalIndex(row + 1, col)] = finalGroup;
        }
        if (col > 1 && this.data[this.getIndex(row, col - 1)]) {
            if (this.finalGroup(row, col - 1) == -1) {
                finalGroup = -1;
            }
            this.groups[this.finalIndex(row, col - 1)] = finalGroup;
        }
        if (col < n && this.data[this.getIndex(row, col + 1)]) {
            if (this.finalGroup(row, col + 1) == -1) {
                finalGroup = -1;
            }
            this.groups[this.finalIndex(row, col + 1)] = finalGroup;
        }
        this.groups[this.getIndex(row, col)] = finalGroup;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return this.data[this.getIndex(row, col)];
    }

    private int finalIndex(int row, int col) {
        int aboveGroup = this.groups[this.getIndex(row, col)];
        int aboveIndex = this.getIndex(row, col);
        while (aboveGroup != -1 && aboveGroup != aboveIndex) {
            aboveIndex = aboveGroup;
            aboveGroup = this.groups[aboveIndex];
        }
        return aboveIndex;
    }

    private int finalGroup(int row, int col) {
        return this.groups[this.finalIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return this.isOpen(row, col) && this.finalGroup(row, col) == -1;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int total = 0;
        for (int i = 0; i < n * n; i++) if (this.data[i]) total ++;
        return total;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i < n; i ++) {
            if (this.finalGroup(n, i + 1) == -1) {
                return true;
            }
        }
        return false;
    }

    private void printGroups() {
        System.out.println("Group:");
        for (int i = 1; i <= n; i ++) {
            for (int j = 1; j <= n; j ++) {
                System.out.print(this.groups[this.getIndex(i, j)]);
                System.out.print(',');
            }
            System.out.println();
        }
        System.out.println("FinalGroup:");
        for (int i = 1; i <= n; i ++) {
            for (int j = 1; j <= n; j ++) {
                System.out.print(this.finalGroup(i, j));
                System.out.print(',');
            }
            System.out.println();
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation perc = new Percolation(10);
        perc.open(2,4);
        perc.open(3,4);
        perc.open(1,4);
        perc.printGroups();
    }
}