package core;

public final class Maths {
    private Maths() {
        // Exists to defeat instantiation
    }

    public static double min(final double... values) {
        double minValue = Double.POSITIVE_INFINITY;
        for (final double value : values) {
            if (value < minValue) {
                minValue = value;
            }
        }
        return minValue;
    }

    public static double max(final double... values) {
        double maxValue = Double.NEGATIVE_INFINITY;
        for (final double value : values) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }
}
