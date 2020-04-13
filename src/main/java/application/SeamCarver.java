package application;

import core.Images;
import core.structures.ConnectedImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public final class SeamCarver {
    public static void main(final String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Provide a file path!");
            System.exit(1);
        }

        final String filePath = args[0];
        final BufferedImage image = ImageIO.read(new File(filePath));
        final String newFile = "new_altered.png";
        ConnectedImage connectedImage = new ConnectedImage(image.getHeight(), image.getWidth(), image);
        final Random random = new Random(0);
        final int numVerticalSeams = 300;
        for (int i = 0; i < numVerticalSeams; i++) {
            final int[] verticalSeam = new int[connectedImage.getHeight()];
            int walkIndex = 250;
            for (int y = 0; y < connectedImage.getHeight(); y++) {
                verticalSeam[y] = walkIndex;
                walkIndex += random.nextInt(3) - 1;
            }

            connectedImage = connectedImage.removeVerticalSeam(verticalSeam);
        }

        final BufferedImage newImage = new BufferedImage(
                connectedImage.getWidth(),
                connectedImage.getHeight(),
                image.getType()
        );

        for (int y = 0; y < newImage.getHeight(); y++) {
            for (int x = 0; x < newImage.getWidth(); x++) {
                newImage.setRGB(x, y, connectedImage.pixelNodes[
                        Images.getIndex(x, y, newImage.getWidth())].getRGBValue());
            }
        }

        ImageIO.write(newImage, "png", new File(newFile));
    }
}
