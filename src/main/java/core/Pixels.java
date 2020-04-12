package core;

import java.awt.image.BufferedImage;

public final class Pixels {
    private Pixels() {
        // Exists to defeat instantiation
    }

    public static int getAlpha(final int rgbPixel) {
        return (rgbPixel >> 24) & 0xff;
    }

    public static int getAlphaAtXY(final BufferedImage image, final int x, final int y) {
        return getAlpha(image.getRGB(x, y));
    }

    public static int getRed(final int rgbPixel) {
        return (rgbPixel >> 16) & 0xff;
    }

    public static int getRedAtXY(final BufferedImage image, final int x, final int y) {
        return getRed(image.getRGB(x, y));
    }

    public static int getGreen(final int rgbPixel) {
        return (rgbPixel >> 8) & 0xff;
    }

    public static int getGreenAtXY(final BufferedImage image, final int x, final int y) {
        return getGreen(image.getRGB(x, y));
    }

    public static int getBlue(final int rgbPixel) {
        return rgbPixel & 0xff;
    }

    public static int getBlueAtXY(final BufferedImage image, final int x, final int y) {
        return getBlue(image.getRGB(x, y));
    }

    public static int toRGB(final int alpha, final int red, final int green, final int blue) {
        return ((alpha & 0xff) << 24) + ((red & 0xff) << 16) + ((green & 0xff) << 8) + (blue & 0xff);
    }
}
