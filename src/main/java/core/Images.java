package core;

public final class Images {
    private Images() {
        // Exists to defeat instantiation
    }

    public static int getIndex(final int x, final int y, final int width) {
        return y * width + x;
    }
}
