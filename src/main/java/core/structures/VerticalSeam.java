package core.structures;

/**
 * A VerticalSeam runs from the top of the picture to the bottom, and represents an ordered set of pixels in the image.
 */
public final class VerticalSeam {
    private final int[] columnPixels;

    public VerticalSeam(int[] columnPixels) {
        this.columnPixels = columnPixels;
    }
}
