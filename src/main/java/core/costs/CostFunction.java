package core.costs;

import core.structures.ConnectedImage;

public interface CostFunction {
    /**
     * Calculates the cost of a pixel at a specific (x,y) pixel in the image.
     */
    double calculate(ConnectedImage image, int x, int y);
}
