package core.structures;

import core.Images;

import java.awt.image.BufferedImage;

/**
 * A ConnectedImage is a patchwork of linked PixelNodes (each pixel has 8 pointers to its neighbors). This is useful for
 * being able to create seam paths through an image or remove seams from an image.
 * <p>
 * Note: A ConnectedImage is *not* an immutable object. We can remove or add seams, but thesee operation mutate the
 * underlying pixel nodes.
 */
public final class ConnectedImage {
    public final PixelNode[] pixelNodes;
    private final int height;
    private final int width;

    private ConnectedImage(final int height,
                           final int width,
                           final PixelNode[] pixelNodes) {
        this.height = height;
        this.width = width;
        this.pixelNodes = pixelNodes;
    }

    public ConnectedImage(final int height, final int width, final BufferedImage image) {
        this(height, width, createPixelNodesFromBufferedImage(height, width, image));
    }

    private static PixelNode[] createPixelNodesFromBufferedImage(final int height,
                                                                 final int width,
                                                                 final BufferedImage image) {
        final PixelNode[] pixelNodes = new PixelNode[height * width];

        // First set the pixel nodes array
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final int imageIndex = Images.getIndex(x, y, width);
                pixelNodes[imageIndex] = new PixelNode(image.getRGB(x, y));
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

        return pixelNodes;
    }

    private static PixelNode getPixelNode(final PixelNode[] pixelNodes, final int x, final int y, final int width) {
        return pixelNodes[Images.getIndex(x, y, width)];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private PixelNode getPixelNodeAt(final int x, final int y) {
        return pixelNodes[Images.getIndex(x, y, width)];
    }

    private PixelNode getPixelNodeOrNullAt(final int x, final int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return getPixelNodeAt(x, y);
        }

        return null;
    }

    public int getRgbPixelAt(final int x, final int y) {
        return getPixelNodeAt(x, y).getRGBValue();
    }

    /**
     * Create an entirely new ConnectedImage by removing a vertical seam. This mutates the underlying ConnectedImage.
     */
    public ConnectedImage removeVerticalSeam(final int[] verticalSeam) {
        final int height = this.getHeight();
        final int newWidth = this.getWidth() - 1;
        if (newWidth <= 0) {
            throw new IllegalArgumentException("New image after seam removal will have 0 width!");
        }

        final PixelNode[] newImagePixelNodes = new PixelNode[height * newWidth];

        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                if (verticalSeam[y] == x) {
                    final int previousVerticalSeamX = y > 0 ? verticalSeam[y - 1] : Integer.MAX_VALUE;
                    restitchRightShiftingPixelNode(x, y, previousVerticalSeamX);
                    continue;
                }

                newImagePixelNodes[i++] = getPixelNodeAt(x, y);
            }
        }

        return new ConnectedImage(height, newWidth, newImagePixelNodes);
    }

    /**
     * This method restiches the underlying pixel node framework to remove a pixel node located at x, y. It does this
     * by restiching the pixels on the right and left of the pixel to the nodes above it properly. This assumes that
     * we are removing pixels in a vertical seam from top to bottom (which we are by definition of a vertical seam).
     */
    private void restitchRightShiftingPixelNode(final int x, final int y, final int previousX) {
        final PixelNode left = getPixelNodeOrNullAt(x - 1, y);
        final PixelNode right = getPixelNodeOrNullAt(x + 1, y);

        if (left != null) {
            final int offsetX = previousX >= x ? 1 : 0;

            left.setRight(right);
            left.setUpper(getPixelNodeOrNullAt(x + offsetX, y - 1));
            left.setUpperRight(getPixelNodeOrNullAt(x + offsetX + 1, y - 1));
            // We don't need to reset the upper left, because this pixel is on the left of the pixel to remove.
        }

        if (right != null) {
            final int offsetX = previousX >= x ? -1 : 0;

            right.setLeft(left);
            right.setUpper(getPixelNodeOrNullAt(x + offsetX, y - 1));
            right.setUpperLeft(getPixelNodeOrNullAt(x + offsetX + 1, y - 1));
            // We don't need to reset the upper right, because this pixel is on the right of the pixel to remove.
        }
    }
}

