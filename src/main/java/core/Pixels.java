package core;

public final class Pixels {
    public static final int RED = Pixels.toRGB(255, 255, 0, 0);
    public static final int GREEN = Pixels.toRGB(255, 0, 255, 0);
    public static final int BLUE = Pixels.toRGB(255, 0, 0, 255);

    private Pixels() {
        // Exists to defeat instantiation
    }

    public static int getAlpha(final int rgbPixel) {
        return (rgbPixel >> 24) & 0xff;
    }

    public static int getRed(final int rgbPixel) {
        return (rgbPixel >> 16) & 0xff;
    }

    public static int getGreen(final int rgbPixel) {
        return (rgbPixel >> 8) & 0xff;
    }

    public static int getBlue(final int rgbPixel) {
        return rgbPixel & 0xff;
    }

    public static int toRGB(final int alpha, final int red, final int green, final int blue) {
        return ((alpha & 0xff) << 24) + ((red & 0xff) << 16) + ((green & 0xff) << 8) + (blue & 0xff);
    }
}
