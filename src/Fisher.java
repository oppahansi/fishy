import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

public class Fisher {

    private ImageView bobberImage;
    private ImageView bitingImage;

    private Stage stage;
    private Robot robot;

    private int bobberColor;
    private int bitingColor;
    private int bobberSensitivity;
    private int bitingSensitivity;
    private int fishingCycle;
    private int fishingDuration;
    private int searchDelay;
    private int buffDuration;
    private int buffCount;
    private boolean shiftLoot;
    private boolean useBuff;

    private int buffingDelay;
    private int fpsTime;

    private boolean fishing;
    private boolean buffed;
    private boolean bobberFound;
    private boolean biteFound;
    private int waitingDuration;
    private int fishingTimer;
    private int buffTimer;
    private int bobberX;
    private int bobberY;
    private int bitingAreaRange;

    public Fisher(ImageView bobberImage, ImageView bitingImage, Stage stage, int bobberColor, int bitingColor, int bobberSensitivity, int bitingSensitivity, int fishingCycle, int fishingDuration, int searchDelay, int buffDuration, int buffCount, boolean shiftLoot, boolean useBuff) {
        this.bobberImage = bobberImage;
        this.bitingImage = bitingImage;
        this.stage = stage;
        this.bobberColor = bobberColor;
        this.bitingColor = bitingColor;
        this.bobberSensitivity = bobberSensitivity;
        this.bitingSensitivity = bitingSensitivity;
        this.fishingCycle = fishingCycle;
        this.fishingDuration = fishingDuration * 60 * 60 * 1000;
        this.searchDelay = searchDelay;
        this.buffDuration = buffDuration * 60 * 1000;
        this.buffCount = buffCount;
        this.shiftLoot = shiftLoot;
        this.useBuff = useBuff;

        buffingDelay = 8000;
        fpsTime = 100;

        fishing = false;
        buffed = false;
        bobberFound = false;
        biteFound = false;
        waitingDuration = 0;
        fishingTimer = 0;
        buffTimer = buffDuration;
        bobberX = 0;
        bobberY = 0;
        bitingAreaRange = 200;

        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public void update(double delta) {
        if (fishingDuration != 0 && fishingDuration <= delta) {
            end();
        } else {
            fishingDuration -= delta;
        }

        if (waitingDuration != 0) {
            waitingDuration -= delta;

            if (waitingDuration <= delta) waitingDuration = 0;

            return;
        }

        if (useBuff && buffCount > 0 && !buffed) {
            pressBuffHotkey();
            waitingDuration = buffingDelay;
            buffed = true;
            buffCount--;

            return;
        }

        if (!fishing) {
            pressFishingHotkey();
            waitingDuration = searchDelay;
            fishingTimer = fishingCycle;
            fishing = true;

            System.out.printf("Starting fishing..%nFishing cycle: %d, delta: %f%n", fishingTimer, delta);
            return;
        }

        if (useBuff && buffTimer <= delta) {
            buffed = false;
            fishing = false;
        } else {
            buffTimer -= delta;
        }

        if (fishingTimer <= delta) {
            fishing = false;
            bobberFound = false;
        } else {
            fishingTimer -= delta;
        }

        if (!bobberFound) {
            findBobber();
            return;
        }

        if (!biteFound) {
            findBite();
        } else {
            loot();

            waitingDuration = 3000;

            fishing = false;
            biteFound = false;
            bobberFound = false;
        }
    }

    private void pressFishingHotkey() {
        robot.keyPress(49);
        robot.keyRelease(49);
    }

    private void pressBuffHotkey() {
        robot.keyPress(50);
        robot.keyRelease(50);
    }

    private void findBobber() {
        System.out.printf("Finding bobber ... %n");

        BufferedImage fishingArea = getFishingArea();
        bobberImage.setImage(SwingFXUtils.toFXImage(fishingArea, null));

        for (int x = 0; x < fishingArea.getWidth(); x++) {
            for (int y = 0; y < fishingArea.getHeight(); y++) {
                int currentColor = fishingArea.getRGB(x, y);
                double diff = (double)Math.abs(Math.abs(bobberColor) - Math.abs(currentColor)) / (double)Math.abs(bobberColor) * 100.0;

                if (diff <= bobberSensitivity) {
                    bobberX = x;
                    bobberY = y;
                    bobberFound = true;

                    BufferedImage bobberArea = getBitingArea();
                    bobberImage.setImage(SwingFXUtils.toFXImage(bobberArea, null));

                    System.out.printf("Bobber found at : %d | Y: %d%n", x, y);
                    return;
                }
            }
        }

        fishing = false;
        System.out.printf("Bobber NOT found%n");
    }

    private void findBite() {
        BufferedImage bitingArea = getBitingArea();
        bitingImage.setImage(SwingFXUtils.toFXImage(bitingArea, null));

        for (int x = 0; x < bitingArea.getWidth(); x++) {
            for (int y = 0; y < bitingArea.getHeight(); y++) {
                int currentColor = bitingArea.getRGB(x, y);
                double diff = (double)Math.abs(Math.abs(bitingColor) - Math.abs(currentColor)) / (double)Math.abs(bitingColor) * 100.0;

                if (diff <= bitingSensitivity) {
                    biteFound = true;
                    robot.mouseMove((int)stage.getX() + bobberX, (int)stage.getY() + bobberY);
                    waitingDuration = 500;
                    System.out.printf("Bite found at X: %d | Y: %d%n", x, y);
                    return;
                }
            }
        }
    }

    private void loot() {
        System.out.printf("Looting...%n");

        if (shiftLoot) robot.keyPress(16);
        robot.delay(75);
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.delay(75);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        robot.delay(75);
        if (shiftLoot) robot.keyRelease(16);
    }

    private BufferedImage getFishingArea() {
        Rectangle captureRect = new Rectangle((int)stage.getX(), (int)stage.getY(), (int)stage.getWidth(), (int)stage.getHeight());
        return robot.createScreenCapture(captureRect);
    }

    private BufferedImage getBitingArea() {
        Rectangle captureRect = new Rectangle(Math.max(((int)stage.getX() + bobberX - bitingAreaRange / 2), 0), Math.max(((int)stage.getY() + bobberY - bitingAreaRange / 2), 0), bitingAreaRange, bitingAreaRange);
        return robot.createScreenCapture(captureRect);
    }

    private void end() {
        System.exit(1);
    }
}
