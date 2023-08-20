package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trials;
    private final double[] thresholds;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                    "Grid order and amount of trials must be greater than 0");
        }

        this.trials = trials;
        thresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(1, n + 1),
                                 StdRandom.uniform(1, n + 1));
            }

            thresholds[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return mean() - 1.96 * (stddev() / Math.sqrt(trials));
    }

    public double confidenceHi() {
        return mean() + 1.96 * (stddev() / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please enter two values: grid size and amount of trials");
        }
        else {
            int n, trials;
            try {
                n = Integer.parseInt(args[0]);
                trials = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input for grid size or amount of trials. "
                                           + "Enter integer values");
                return;
            }

            PercolationStats percolationStats = new PercolationStats(n, trials);

            final String mean = "mean";
            final String stddev = "stddev";
            final String confInterval = "95% confidence interval";
            final String equalsSign = " = ";
            int maxLength = StdStats.max(new int[] {
                    mean.length(),
                    stddev.length(),
                    confInterval.length(),
                    });

            System.out.println(String.format("%-" + maxLength + 's', mean) +
                                       equalsSign + percolationStats.mean());
            System.out.println(String.format("%-" + maxLength + 's', stddev) +
                                       equalsSign + percolationStats.stddev());
            System.out.println(String.format("%-" + maxLength + 's', confInterval) +
                                       equalsSign + '[' +
                                       percolationStats.confidenceLo() + ", " +
                                       percolationStats.confidenceHi() + ']');
        }
    }
}
