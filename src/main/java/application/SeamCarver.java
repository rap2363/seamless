package application;

import core.Images;
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
        final String newFile = "new_altered.png";
        final BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        final int[] rgbPixels = Images.getRGBPixelsFromBuferedImage(image);
        final ConnectedImage connectedImage = new ConnectedImage(image.getHeight(), image.getWidth(), rgbPixels);

        for (int y = 0; y < newImage.getHeight(); y++) {
            for (int x = 0; x < newImage.getWidth(); x++) {
                newImage.setRGB(x, y, connectedImage.pixelNodes[
                        Images.getIndex(x, y, newImage.getWidth())].getRGBValue());
            }
        }

        ImageIO.write(newImage, "png", new File(newFile));
    }
}
