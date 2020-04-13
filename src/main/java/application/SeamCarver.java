package application;

import core.Images;
import core.costs.EnergyCostFunction;
import core.seams.VerticalSeamFinder;
import core.structures.ConnectedImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class SeamCarver {
    public static void main(final String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Provide a file path!");
            System.exit(1);
        }

        final String filePath = args[0];
        final BufferedImage image = ImageIO.read(new File(filePath));
        final String newFile = filePath.split("\\.")[0] + "_altered.png";
        ConnectedImage connectedImage = new ConnectedImage(image.getHeight(), image.getWidth(), image);
        final int numVerticalSeams = 300;
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

        ImageIO.write(newImage, "png", new File(newFile));
    }
}
