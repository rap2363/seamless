package core;

import core.structures.ConnectedImage;

import java.awt.image.BufferedImage;

public final class Images {
    private Images() {
        // Exists to defeat instantiation
    }

    public static int getIndex(final int x, final int y, final int width) {
        return y * width + x;
    }

    public static BufferedImage toBufferedImage(final ConnectedImage connectedImage, final int imageType) {
        final BufferedImage newImage = new BufferedImage(
                connectedImage.getWidth(),
                connectedImage.getHeight(),
                imageType
        );

        for (int y = 0; y < newImage.getHeight(); y++) {
            for (int x = 0; x < newImage.getWidth(); x++) {
                newImage.setRGB(x, y, connectedImage.pixelValues[Images.getIndex(x, y, newImage.getWidth())]);
            }
        }

        return newImage;
    }
}
