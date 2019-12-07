import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

class Utils {
    static String getRandomString() {
        int leftLimitUC = 65;
        int rightLimitUC = 90;
        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();

        int targetStringLength = random.nextInt(11) + 5;
        StringBuilder buffer = new StringBuilder(targetStringLength);

        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedIntUC = leftLimitUC + (int)(random.nextFloat() * (rightLimitUC - leftLimitUC + 1));
            int randomLimitedInt = leftLimit + (int)(random.nextFloat() * (rightLimit - leftLimit + 1));

            if (random.nextBoolean())
                buffer.append((char) randomLimitedInt);
            else
                buffer.append((char) randomLimitedIntUC);
        }

        return buffer.toString();
    }

    static Color getAverageColorFromImage(BufferedImage bitingArea) {
        int pixelAmount = bitingArea.getWidth() * bitingArea.getHeight();
        long sumr = 0, sumg = 0, sumb = 0;

        for (int x = 0; x < bitingArea.getWidth(); x++) {
            for (int y = 0; y < bitingArea.getHeight(); y++) {
                Color pixel = new Color(bitingArea.getRGB(x, y));
                sumr += pixel.getRed();
                sumg += pixel.getGreen();
                sumb += pixel.getBlue();
            }
        }

        sumr /= pixelAmount;
        sumg /= pixelAmount;
        sumb /= pixelAmount;

        return new Color((int)sumr, (int)sumg, (int)sumb);
    }
}
