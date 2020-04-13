package core.structures;

import core.Images;
import core.Pixels;

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
     * Create an entirely new ConnectedImage by removing a vertical seam.
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

    public ConnectedImage colorVerticalSeam(final int[] verticalSeam) {
        return colorVerticalSeam(verticalSeam, Pixels.RED);
    }

    /**
     * Create an entirely new ConnectedImage by coloring a vertical seam.
     */
    public ConnectedImage colorVerticalSeam(final int[] verticalSeam, final int color) {
        final int height = this.getHeight();
        final int newWidth = this.getWidth();
        if (newWidth <= 0) {
            throw new IllegalArgumentException("New image after seam removal will have 0 width!");
        }

        final int[] newImagePixelValues = new int[height * newWidth];

        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                final int newPixelColor = verticalSeam[y] == x ? color : getPixelRGBValueAt(x, y);
                newImagePixelValues[i++] = newPixelColor;
            }
        }

        return new ConnectedImage(height, newWidth, newImagePixelValues);
    }

    /**
     * Rotates an image to clockwise by 90 degrees. This allows us to use the vertical seam algorithm in the same
     * way to remove horizontal seams.
     */
    public ConnectedImage rotateRight() {
        final int newHeight = this.getWidth();
        final int newWidth = this.getHeight();

        final int[] newImagePixelValues = new int[newHeight * newWidth];

        int i = 0;
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                final int pixelColor = this.getPixelRGBValueAt(y, (this.getHeight() - 1) - x);
                newImagePixelValues[i++] = pixelColor;
            }
        }

        return new ConnectedImage(newHeight, newWidth, newImagePixelValues);
    }

    /**
     * Rotates an image to counterclockwise by 90 degrees. This lets us rotate an image back to the original orientation
     * after horizontal seam removal.
     */
    public ConnectedImage rotateLeft() {
        final int newHeight = this.getWidth();
        final int newWidth = this.getHeight();

        final int[] newImagePixelValues = new int[newHeight * newWidth];

        int i = 0;
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                final int pixelColor = this.getPixelRGBValueAt((this.getWidth() - 1) - y, x);
                newImagePixelValues[i++] = pixelColor;
            }
        }

        return new ConnectedImage(newHeight, newWidth, newImagePixelValues);
    }
}

