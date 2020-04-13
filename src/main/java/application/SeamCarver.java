package application;

import core.Images;
import core.costs.EnergyCostFunction;
import core.seams.SeamFinder;
import core.structures.ConnectedImage;
import org.apache.commons.cli.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class SeamCarver {
    public static void main(final String[] args) throws IOException, ParseException {
        final Options options = new Options();

        final Option imageFilePathOpt = Option.builder()
                .hasArg()
                .longOpt("imageFilePath")
                .desc("Image file path")
                .type(String.class)
                .required()
                .build();
        final Option numVerticalSeamsOpt = Option.builder()
                .hasArg()
                .longOpt("numVerticalSeams")
                .desc("Number of vertical seams to remove from image")
                .type(Number.class)
                .build();
        final Option numHorizontalSeamsOpt = Option.builder()
                .hasArg()
                .longOpt("numHorizontalSeams")
                .desc("Number of vertical seams to remove from image")
                .type(Number.class)
                .build();

        options.addOption(imageFilePathOpt);
        options.addOption(numVerticalSeamsOpt);
        options.addOption(numHorizontalSeamsOpt);

        final CommandLineParser parser = new DefaultParser();
        final CommandLine cmd = parser.parse(options, args);
        final String filePath = cmd.getOptionValue(imageFilePathOpt.getLongOpt());

        final BufferedImage image = ImageIO.read(new File(filePath));
        final String outputFilePath = filePath.split("\\.")[0] + "_altered.png";

        final Object parsedNumVerticalSeams = cmd.getParsedOptionValue("numVerticalSeams");
        final long numVerticalSeams = parsedNumVerticalSeams == null ? 0 : (long) parsedNumVerticalSeams;

        final Object parsedNumHorizontalSeams = cmd.getParsedOptionValue("numHorizontalSeams");
        final long numHorizontalSeams = parsedNumHorizontalSeams == null ? 0 : (long) parsedNumHorizontalSeams;

        ConnectedImage connectedImage = new ConnectedImage(image.getHeight(), image.getWidth(), image);
        final SeamFinder seamFinder = new SeamFinder(new EnergyCostFunction());

        for (int i = 0; i < numVerticalSeams; i++) {
            final int[] verticalSeamToRemove = seamFinder.findMinimumVerticalSeam(connectedImage);
            connectedImage = connectedImage.removeVerticalSeam(verticalSeamToRemove);
        }

        for (int i = 0; i < numHorizontalSeams; i++) {
            if (i == 0) {
                connectedImage = connectedImage.rotateRight();
            }

            final int[] verticalSeamToRemove = seamFinder.findMinimumVerticalSeam(connectedImage);
            connectedImage = connectedImage.removeVerticalSeam(verticalSeamToRemove);

            if (i == numHorizontalSeams - 1) {
                connectedImage = connectedImage.rotateLeft();
            }
        }

        final BufferedImage newImage = Images.toBufferedImage(connectedImage, image.getType());
        ImageIO.write(newImage, "png", new File(outputFilePath));
    }
}
