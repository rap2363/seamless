package application;

import core.Images;
import core.costs.EnergyCostFunction;
import core.seams.VerticalSeamFinder;
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

        options.addOption(imageFilePathOpt);
        options.addOption(numVerticalSeamsOpt);

        final CommandLineParser parser = new DefaultParser();
        final CommandLine cmd = parser.parse(options, args);
        final String filePath = cmd.getOptionValue(imageFilePathOpt.getLongOpt());

        final BufferedImage image = ImageIO.read(new File(filePath));
        final String outputFilePath = filePath.split("\\.")[0] + "_altered.png";
        final Object parsedValue = cmd.getParsedOptionValue("numVerticalSeams");
        final long numVerticalSeams = parsedValue == null ? 0 : (long) parsedValue;

        ConnectedImage connectedImage = new ConnectedImage(image.getHeight(), image.getWidth(), image);
        for (int i = 0; i < numVerticalSeams; i++) {
            final int[] verticalSeamToRemove
                    = new VerticalSeamFinder(new EnergyCostFunction()).findMinimumVerticalSeam(connectedImage);
            connectedImage = connectedImage.removeVerticalSeam(verticalSeamToRemove);
        }

        final BufferedImage newImage = new BufferedImage(
                connectedImage.getWidth(),
                connectedImage.getHeight(),
                image.getType()
        );

        for (int y = 0; y < newImage.getHeight(); y++) {
            for (int x = 0; x < newImage.getWidth(); x++) {
                newImage.setRGB(x, y, connectedImage.pixelValues[Images.getIndex(x, y, newImage.getWidth())]);
            }
        }

        ImageIO.write(newImage, "png", new File(outputFilePath));
    }
}
