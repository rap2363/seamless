package core.costs;

import core.structures.ConnectedImage;

/**
 * Defined as the change in x + the change in y for neighbor pixels (left, right, up, and down). This uses the Euclidean
 * squared distance by default. We use a simple first order filter for the horizontal and vertical directions, namely:
 * [-.5, 0, 0.5] and [0.5, 0, -0.5]. We avoid taking a square root at the end to keep this operation fast.
 */
public final class EnergyCostFunction implements CostFunction {
    @Override
    public double calculate(final ConnectedImage image, final int x, final int y) {
        final double deltaX = getDifference(image, x, y, x - 1, y) + getDifference(image, x + 1, y, x, y);
        final double deltaY = getDifference(image, x, y, x, y - 1) + getDifference(image, x, y + 1, x, y);
        return (deltaX * deltaX + deltaY * deltaY) / 2.0;
    }

    private static double getDifference(final ConnectedImage image,
                                        final int x1,
                                        final int y1,
                                        final int x2,
                                        final int y2) {

        if (image.outOfBounds(x1, y1) || image.outOfBounds(x2, y2)) {
            return 0d;
        }

        return Costs.difference(image.getPixelRGBValueAt(x1, y1), image.getPixelRGBValueAt(x2, y2));
    }
}
