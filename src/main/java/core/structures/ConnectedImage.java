package core.structures;

import core.Images;

import java.awt.image.BufferedImage;

/**
 * A ConnectedImage is an immutable, flat buffer of RGB values.
 */
public final class ConnectedImage {
    public final int[] pixelValues;
    private final int height;
    private final int width;

    private ConnectedImage(final int height,
                           final int width,
                           final int[] pixelValues) {
        this.height = height;
        this.width = width;
        this.pixelValues = pixelValues;
    }

    public ConnectedImage(final int height, final int width, final BufferedImage image) {
        this(height, width, createPixelNodesFromBufferedImage(height, width, image));
    }

    private static int[] createPixelNodesFromBufferedImage(final int height,
                                                           final int width,
                                                           final BufferedImage image) {
        final int[] pixelValues = new int[height * width];

        // First set the pixel nodes array
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelValues[Images.getIndex(x, y, width)] = image.getRGB(x, y);
            }
        }

        return pixelValues;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean outOfBounds(final int x, final int y) {
        return !(x >= 0 && x < width && y >= 0 && y < height);
    }

    public int getPixelRGBValueAt(final int x, final int y) {
        return pixelValues[Images.getIndex(x, y, width)];
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

        final int[] newImagePixelValues = new int[height * newWidth];

        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                if (verticalSeam[y] == x) {
                    continue;
                }

                newImagePixelValues[i++] = getPixelRGBValueAt(x, y);
            }
        }

        return new ConnectedImage(height, newWidth, newImagePixelValues);
    }
}

