package core.structures;

/**
 * A simple struct to store an immutable RGB value and pointers to its pixelNodes.
 */
public final class PixelNode {
    private final int rgbValue;
    private PixelNode upperLeft;
    private PixelNode upper;
    private PixelNode upperRight;
    private PixelNode left;
    private PixelNode right;
    private PixelNode lowerLeft;
    private PixelNode lower;
    private PixelNode lowerRight;

    public PixelNode(final int rgbValue) {
        this.rgbValue = rgbValue;
    }

    public int getRGBValue() {
        return rgbValue;
    }

    public PixelNode getUpperLeft() {
        return upperLeft;
    }

    public void setUpperLeft(final PixelNode pixelNode) {
        upperLeft = pixelNode;
    }

    public PixelNode getUpper() {
        return upper;
    }

    public void setUpper(final PixelNode pixelNode) {
        upper = pixelNode;
    }

    public PixelNode getUpperRight() {
        return upperRight;
    }

    public void setUpperRight(final PixelNode pixelNode) {
        upperRight = pixelNode;
    }

    public PixelNode getLeft() {
        return left;
    }

    public void setLeft(final PixelNode pixelNode) {
        left = pixelNode;
    }

    public PixelNode getRight() {
        return right;
    }

    public void setRight(final PixelNode pixelNode) {
        right = pixelNode;
    }

    public PixelNode getLowerLeft() {
        return lowerLeft;
    }

    public void setLowerLeft(final PixelNode pixelNode) {
        lowerLeft = pixelNode;
    }

    public PixelNode getLower() {
        return lower;
    }

    public void setLower(final PixelNode pixelNode) {
        lower = pixelNode;
    }

    public PixelNode getLowerRight() {
        return lowerRight;
    }

    public void setLowerRight(final PixelNode pixelNode) {
        lowerRight = pixelNode;
    }

    public static Builder newBuilderWithRGBValue(final int rgbValue) {
        return new Builder(rgbValue);
    }

    public static class Builder {
        private final int rgbValue;
        private PixelNode upperLeft;
        private PixelNode upper;
        private PixelNode upperRight;
        private PixelNode left;
        private PixelNode right;
        private PixelNode lowerLeft;
        private PixelNode lower;
        private PixelNode lowerRight;

        private Builder(final int rgbValue) {
            this.rgbValue = rgbValue;
        }

        public Builder setUpperLeft(final PixelNode pixelNode) {
            upperLeft = pixelNode;
            return this;
        }

        public Builder setUpper(final PixelNode pixelNode) {
            upper = pixelNode;
            return this;
        }

        public Builder setUpperRight(final PixelNode pixelNode) {
            upperRight = pixelNode;
            return this;
        }

        public Builder setLeft(final PixelNode pixelNode) {
            left = pixelNode;
            return this;
        }

        public Builder setRight(final PixelNode pixelNode) {
            right = pixelNode;
            return this;
        }

        public Builder setLowerLeft(final PixelNode pixelNode) {
            lowerLeft = pixelNode;
            return this;
        }

        public Builder setLower(final PixelNode pixelNode) {
            lower = pixelNode;
            return this;
        }

        public Builder setLowerRight(final PixelNode pixelNode) {
            lowerRight = pixelNode;
            return this;
        }

        public PixelNode build() {
            return new PixelNode(
                    rgbValue
            );
        }
    }
}
