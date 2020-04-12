package core;

import java.awt.image.BufferedImage;

public final class Images {
    private Images() {
        // Exists to defeat instantiation
    }

    public static int getIndex(final int x, final int y, final int width) {
        return y * width + x;
    }

    public static int[] getRGBPixelsFromBuferedImage(final BufferedImage image) {
        final int height = image.getHeight();
        final int width = image.getWidth();
        final int[] allPixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                allPixels[getIndex(x, y, width)] = image.getRGB(x, y);
            }
        }

        return allPixels;
    }
}
