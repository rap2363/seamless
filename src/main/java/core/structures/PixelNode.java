package core.structures;

/**
 * A simple struct to store an immutable RGB value and pointers to its pixelNodes.
 */
public final class PixelNode {
    private final int rgbValue;

    public PixelNode(final int rgbValue) {
        this.rgbValue = rgbValue;
    }
}
