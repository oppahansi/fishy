import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Fisher {

    private ImageView bobberImage;
    private ImageView bitingImage;

    private Stage stage;
    private Robot robot;
    private Random rng;

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

    private boolean fishing;
    private boolean biteFound;
    private int waitingDuration;
    private int fishingTimer;
    private int buffTimer;
    private int bobberCheckTimer;
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

        fishing = false;
        biteFound = false;
        waitingDuration = searchDelay;
        fishingTimer = 0;
        buffTimer = 0;
        bobberCheckTimer = 0;
        bobberX = 0;
        bobberY = 0;
        bitingAreaRange = 200;

        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.exit(-1);
        }

        rng = new Random();
    }

    public void update(double delta) {
        if (fishingDuration != 0 && fishingDuration <= delta)
            end();
        else
            fishingDuration -= delta;

        if (waitingDuration != 0) {
            waitingDuration -= delta;

            if (waitingDuration <= delta)
                waitingDuration = 0;

            return;
        }

        if (useBuff) {
            if (buffCount > 0 && buffTimer == 0) {
                pressBuffHotkey();
                waitingDuration = buffingDelay;
                buffTimer = buffDuration - 5000;
                buffCount--;

                return;
            }

            if (buffTimer > 0) {
                buffTimer -= delta;

                if (buffTimer <= delta) {
                    buffTimer = 0;
                    fishing = false;
                    return;
                }
            }
        }


        if (!fishing) {
            mouseGlide(Utils.getMouseScreenX(), Utils.getMouseScreenY(), (int)stage.getX() + rng.nextInt((int)stage.getWidth()) + 1, (int)stage.getY() + rng.nextInt((int)stage.getHeight()) + 1, Utils.getRandomMoveTime(), 50);

            pressFishingHotkey();
            waitingDuration = searchDelay;
            fishingTimer = fishingCycle;
            bobberCheckTimer = 0;
            fishing = true;

            return;
        }

        if (fishingTimer <= delta)
            fishing = false;
        else
            fishingTimer -= delta;

        if (bobberCheckTimer <= delta) {
            findBobber();
            bobberCheckTimer = 5000;
        } else
            bobberCheckTimer -= delta;

        if (!biteFound) findBite();

        if (biteFound) {
            loot();

            waitingDuration = 3000;

            fishing = false;
            biteFound = false;
        }
    }

    public void end() {
        System.exit(1);
    }

    private void pressFishingHotkey() {
        robot.keyPress(49);
        robot.delay(75);
        robot.keyRelease(49);
    }

    private void pressBuffHotkey() {
        robot.keyPress(50);
        robot.delay(75);
        robot.keyRelease(50);
    }

    private void findBobber() {
        BufferedImage fishingArea = getFishingArea();

        for (int x = 0; x < fishingArea.getWidth(); x++) {
            for (int y = 0; y < fishingArea.getHeight(); y++) {
                int currentColor = fishingArea.getRGB(x, y);
                double diff = (double)Math.abs(Math.abs(bobberColor) - Math.abs(currentColor)) / (double)Math.abs(bobberColor) * 100.0;

                if (diff <= bobberSensitivity) {
                    bobberX = x;
                    bobberY = y;
                    BufferedImage bobberArea = getBobberArea();
                    bobberImage.setImage(SwingFXUtils.toFXImage(bobberArea, null));
                    return;
                }
            }
        }

        fishing = false;
    }

    private void findBite() {
        BufferedImage fishingArea = getFishingArea();
        BufferedImage bobberArea = getBobberArea();
        bitingImage.setImage(SwingFXUtils.toFXImage(bobberArea, null));

        for (int x = 0; x < fishingArea.getWidth(); x++) {
            for (int y = 0; y < fishingArea.getHeight(); y++) {
                int currentColor = fishingArea.getRGB(x, y);
                double diff = (double)Math.abs(Math.abs(bitingColor) - Math.abs(currentColor)) / (double)Math.abs(bitingColor) * 100.0;

                if (diff <= bitingSensitivity) {
                    biteFound = true;
                    mouseGlide(Utils.getMouseScreenX(), Utils.getMouseScreenY(), (int)stage.getX() + x, (int)stage.getY() + y, Utils.getRandomMoveTime(), 50);
                    waitingDuration = 500;
                    return;
                }
            }
        }
    }

    private void loot() {
        if (shiftLoot) robot.keyPress(16);
        robot.delay(75);
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.delay(75);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        robot.delay(75);
        if (shiftLoot) robot.keyRelease(16);
    }

    public void mouseGlide(int x1, int y1, int x2, int y2, int t, int n) {
        try {
            Robot r = new Robot();
            double dx = (x2 - x1) / ((double) n);
            double dy = (y2 - y1) / ((double) n);
            double dt = t / ((double) n);
            for (int step = 1; step <= n; step++) {
                Thread.sleep((int) dt);
                r.mouseMove((int) (x1 + dx * step), (int) (y1 + dy * step));
            }
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage getFishingArea() {
        Rectangle captureRect = new Rectangle((int)stage.getX(), (int)stage.getY(), (int)stage.getWidth(), (int)stage.getHeight());
        return robot.createScreenCapture(captureRect);
    }

    private BufferedImage getBobberArea() {
        Rectangle captureRect = new Rectangle((int)stage.getX() + bobberX - bitingAreaRange / 2, (int)stage.getY() + bobberY - bitingAreaRange / 2, bitingAreaRange, bitingAreaRange);
        return robot.createScreenCapture(captureRect);
    }
}
