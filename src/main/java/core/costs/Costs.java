package core.costs;

import core.Maths;
import core.Pixels;

public final class Costs {
    private Costs() {
        // Exists to defeat instantiation
    }

    public static double difference(final int pixelValue1, final int pixelValue2) {
        final int deltaAlpha = Pixels.getAlpha(pixelValue1) - Pixels.getAlpha(pixelValue2);
        final int deltaRed = Pixels.getRed(pixelValue1) - Pixels.getRed(pixelValue2);
        final int deltaGreen = Pixels.getGreen(pixelValue1) - Pixels.getGreen(pixelValue2);
        final int deltaBlue = Pixels.getBlue(pixelValue1) - Pixels.getBlue(pixelValue2);

        return deltaAlpha + deltaRed + deltaGreen + deltaBlue;
    }

    /**
     * Returns (alpha1 - alpha2)**2 + (red1 - red2)**2 + (green1 - green2)**2 + (blue1 - blue2)**2.
     */
    public static double squaredDifference(final int pixelValue1, final int pixelValue2) {
        final int deltaAlpha = Pixels.getAlpha(pixelValue1) - Pixels.getAlpha(pixelValue2);
        final int deltaRed = Pixels.getRed(pixelValue1) - Pixels.getRed(pixelValue2);
        final int deltaGreen = Pixels.getGreen(pixelValue1) - Pixels.getGreen(pixelValue2);
        final int deltaBlue = Pixels.getBlue(pixelValue1) - Pixels.getBlue(pixelValue2);

        return deltaAlpha * deltaAlpha + deltaRed * deltaRed + deltaGreen * deltaGreen + deltaBlue * deltaBlue;
    }

    /**
     * Returns |alpha1 - alpha2| + |red1 - red2| + |green1 - green2| + |blue1 - blue2|.
     */
    public static double absoluteDifference(final int pixelValue1, final int pixelValue2) {
        final int absoluteDeltaAlpha = Math.abs(Pixels.getAlpha(pixelValue1) - Pixels.getAlpha(pixelValue2));
        final int absoluteDeltaRed = Math.abs(Pixels.getRed(pixelValue1) - Pixels.getRed(pixelValue2));
        final int absoluteDeltaGreen = Math.abs(Pixels.getGreen(pixelValue1) - Pixels.getGreen(pixelValue2));
        final int absoluteDeltaBlue = Math.abs(Pixels.getBlue(pixelValue1) - Pixels.getBlue(pixelValue2));

        return absoluteDeltaAlpha + absoluteDeltaRed + absoluteDeltaGreen + absoluteDeltaBlue;
    }

    /**
     * Returns max(alpha1 - alpha2, red1 - red2, green1 - green2, blue1 - blue2).
     */
    public static double maxDifference(final int pixelValue1, final int pixelValue2) {
        final double absoluteDeltaAlpha = Math.abs(Pixels.getAlpha(pixelValue1) - Pixels.getAlpha(pixelValue2));
        final double absoluteDeltaRed = Math.abs(Pixels.getRed(pixelValue1) - Pixels.getRed(pixelValue2));
        final double absoluteDeltaGreen = Math.abs(Pixels.getGreen(pixelValue1) - Pixels.getGreen(pixelValue2));
        final double absoluteDeltaBlue = Math.abs(Pixels.getBlue(pixelValue1) - Pixels.getBlue(pixelValue2));

        return Maths.max(absoluteDeltaAlpha, absoluteDeltaRed, absoluteDeltaGreen, absoluteDeltaBlue);
    }
}
