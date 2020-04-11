package application;

import core.Pixels;

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
        for (int x = 0; x < newImage.getWidth(); x++) {
            for (int y = 0; y < newImage.getHeight(); y++) {
                newImage.setRGB(x, y, Pixels.toRGB(
                        Pixels.getAlphaAtXY(image, x, y),
                        Pixels.getRedAtXY(image, x, y),
                        Pixels.getGreenAtXY(image, x, y),
                        Pixels.getBlueAtXY(image, x, y)
                ));
            }
        }

        ImageIO.write(newImage, "png", new File(newFile));
    }
}
