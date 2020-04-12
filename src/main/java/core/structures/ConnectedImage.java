package core.structures;

import core.Images;

/**
 * A ConnectedImage is a patchwork of linked PixelNodes (each pixel has 8 pointers to its neighbors). This is useful for
 * being able to create seam paths through an image or remove seams from an image.
 * <p>
 * Note: A ConnectedImage is an immutable object. We can remove or add one or multiple seams at once, but this operation
 * creates a deep copy of the underlying pixel nodes.
 */
public final class ConnectedImage {
    public final PixelNode[] pixelNodes;
    private final int height;
    private final int width;

    public ConnectedImage(final int height, final int width, final int[] imageRGBPixels) {
        this.height = height;
        this.width = width;
        final PixelNode[] pixelNodes = new PixelNode[height * width];

        // First set the pixel nodes array
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int imageIndex = Images.getIndex(x, y, width);
                pixelNodes[imageIndex] = new PixelNode(imageRGBPixels[imageIndex]);
            }
        }

        // Now connect the neighbors
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final PixelNode node = pixelNodes[Images.getIndex(x, y, width)];

                if (x > 0 && y > 0) {
                    node.setUpperLeft(getPixelNode(pixelNodes, x - 1, y - 1, width));
                }

                if (y > 0) {
                    node.setUpper(getPixelNode(pixelNodes, x, y - 1, width));
                }

                if (x < width - 1 && y > 0) {
                    node.setUpperRight(getPixelNode(pixelNodes, x + 1, y - 1, width));
                }

                if (x > 0) {
                    node.setLeft(getPixelNode(pixelNodes, x - 1, y, width));
                }

                if (x < width - 1) {
                    node.setRight(getPixelNode(pixelNodes, x + 1, y, width));
                }

                if (x > 0 && y < height - 1) {
                    node.setLowerLeft(getPixelNode(pixelNodes, x - 1, y + 1, width));
                }

                if (y < height - 1) {
                    node.setLower(getPixelNode(pixelNodes, x, y + 1, width));
                }

                if (x < width - 1 && y < height - 1) {
                    node.setLowerRight(getPixelNode(pixelNodes, x + 1, y + 1, width));
                }
            }
        }

        this.pixelNodes = pixelNodes;
    }

    private static PixelNode getPixelNode(final PixelNode[] pixelNodes, final int x, final int y, final int width) {
        return pixelNodes[Images.getIndex(x, y, width)];
    }

    /**
     * Create an entirely new ConnectedImage by removing a vertical seam.
     */
    public static ConnectedImage removeVerticalSeam(final VerticalSeam verticalSeam) {
        return null;
    }
}

