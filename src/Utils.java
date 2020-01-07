import javafx.stage.Stage;

import java.awt.*;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
            int randomLimitedIntUC = leftLimitUC + (int) (random.nextFloat() * (rightLimitUC - leftLimitUC + 1));
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));

            if (random.nextBoolean())
                buffer.append((char) randomLimitedInt);
            else
                buffer.append((char) randomLimitedIntUC);
        }

        return buffer.toString();
    }

    static int getMouseScreenX() {
        return (int) MouseInfo.getPointerInfo().getLocation().getX();
    }

    static int getMouseScreenY() {
        return (int) MouseInfo.getPointerInfo().getLocation().getY();
    }

    static int getRandomMoveTime() {
        return ThreadLocalRandom.current().nextInt(500, 800 + 1);
    }

    static int getRandomStageX(Stage stage) {
        Random rng = new Random();
        return (int) stage.getX() + rng.nextInt((int) stage.getWidth()) + 1;
    }

    static int getRandomStageY(Stage stage) {
        Random rng = new Random();
        return (int) stage.getY() + rng.nextInt((int) stage.getHeight()) + 1;
    }

    static OSType getOperatingSystemType() {
        OSType detectedOS;
        String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

        if ((OS.contains("mac")) || (OS.contains("darwin"))) detectedOS = OSType.MacOS;
        else if (OS.contains("win")) detectedOS = OSType.Windows;
        else if (OS.contains("nux")) detectedOS = OSType.Linux;
        else detectedOS = OSType.Other;

        return detectedOS;
    }

    public enum OSType {
        Windows, MacOS, Linux, Other
    }
}
