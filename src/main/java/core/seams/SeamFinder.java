package core.seams;

import core.Maths;
import core.costs.CostFunction;
import core.structures.ConnectedImage;

import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * Using dynamic programming, find the shortest path from the top of the image to the bottom while minimizing the given
 * cost function.
 */
public final class SeamFinder {
    private final CostFunction costFunction;

    public SeamFinder(final CostFunction costFunction) {
        this.costFunction = costFunction;
    }

    public int[] findMinimumVerticalSeam(final ConnectedImage connectedImage) {
        final double[][] costArray = new double[connectedImage.getHeight()][connectedImage.getWidth()];
        for (int y = 0; y < costArray.length; y++) {
            for (int x = 0; x < costArray[0].length; x++) {
                double value = costFunction.calculate(connectedImage, x, y);
                if (y > 0) {
                    final double upperLeftValue = x > 0
                            ? costArray[y - 1][x - 1]
                            : Double.POSITIVE_INFINITY;
                    final double upperValue = costArray[y - 1][x];
                    final double upperRightValue = x < costArray[0].length - 1
                            ? costArray[y - 1][x + 1]
                            : Double.POSITIVE_INFINITY;
                    value += Maths.min(upperLeftValue, upperValue, upperRightValue);
                }

                costArray[y][x] = value;
            }
        }

        // From the cost array, work backwards to find the vertical seam
        // First find the endpoint (the minimum value in the costArray's final row).
        final int[] minVerticalSeam = new int[costArray.length];
        int col = IntStream.range(0, costArray[0].length)
                .boxed()
                .min(Comparator.comparing(index -> costArray[costArray.length - 1][index]))
                .orElseThrow(RuntimeException::new);

        for (int row = costArray.length - 1; row >= 0; row--) {
            minVerticalSeam[row] = col;
            if (row == 0) {
                continue;
            }

            // Calculate the next column value
            final double upperLeftValue = col > 0 ? costArray[row - 1][col - 1] : Double.POSITIVE_INFINITY;
            final double upperValue = costArray[row - 1][col];
            final double upperRightValue = col < costArray[0].length - 1 ? costArray[row - 1][col + 1] : Double.POSITIVE_INFINITY;

            if (upperLeftValue <= upperValue && upperLeftValue <= upperRightValue) {
                col--;
            } else if (upperRightValue <= upperLeftValue && upperRightValue <= upperValue) {
                col++;
            }
        }

        return minVerticalSeam;
    }
}
