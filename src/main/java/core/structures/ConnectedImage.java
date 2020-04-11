package core.structures;

import core.Pixels;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * A ConnectedImage is a patchwork of linked PixelNodes (each pixel has 8 pointers to its neighbors). This is useful for
 * being able to create seam paths through an image or remove seams from an image.
 *
 * Note: A ConnectedImage is not an immutable object. When pixel nodes are rewired or we re-seam, we mutate the
 * underlying PixelNodes and alter the ConnectedImage in the process.
 */
public final class ConnectedImage {
    private int height;
    private int width;
    public final PixelNode[] pixelNodes;

    public ConnectedImage(final int height, final int width, final BufferedImage image) {
        this.height = height;
        this.width = width;
        final PixelNode[] pixelNodes = new PixelNode[height * width];

        // First set the pixel nodes array
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelNodes[getIndex(x, y, width)] = new PixelNode(image.getRGB(x, y));
            }
        }

        // Now connect the neighbors
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final PixelNode node = pixelNodes[getIndex(x, y, width)];

                if (x > 0 && y > 0) {
                    node.setUpperLeft(getPixelNode(pixelNodes,x - 1, y - 1, width));
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
                    node.setLowerLeft(getPixelNode(pixelNodes,x - 1, y + 1, width));
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

    private static int getIndex(final int x, final int y, final int width) {
        return y * width + x;
    }

    private static PixelNode getPixelNode(final PixelNode[] pixelNodes, final int x, final int y, final int width) {
        return pixelNodes[getIndex(x, y, width)];
    }
}

