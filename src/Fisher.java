import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Fisher {
    private Stage stage;
    private ColorSettings colorSettings;
    private FishingPlan fishingPlan;
    private ExitPlan exitPlan;
    private Robot robot;

    private int buffingDelay;
    private boolean fishing;
    private boolean biteFound;
    private int waitingDuration;
    private int fishingTimer;
    private int buffTimer;
    private int bobberCheckTimer;
    private int hooksCounter;
    private int castsCounter;

    public Fisher(Stage stage, ColorSettings colorSettings, FishingPlan fishingPlan, ExitPlan exitPlan) {
        this.stage = stage;
        this.colorSettings = colorSettings;
        this.fishingPlan = fishingPlan;
        this.exitPlan = exitPlan;

        init();
    }

    private void init() {
        buffingDelay = 8000;
        fishing = false;
        biteFound = false;
        waitingDuration = fishingPlan.getSearchDelay() + 5000;
        fishingTimer = 0;
        buffTimer = 0;
        bobberCheckTimer = 0;
        hooksCounter = 0;
        castsCounter = 0;

        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.exit(-3);
        }
    }

    public void update(double delta) {
        if (exitPlan.shouldExit(hooksCounter, castsCounter, fishingPlan.getBuffCount())) end();
        if (shouldWait(delta)) {
            if (fishingPlan.isUseBuff())
                updateBuffTimer(delta);

            return;
        }

        if (fishingPlan.canBuff()) {
            if (buffTimer == 0) {
                pressBuffHotkey();
                waitingDuration = buffingDelay;
                buffTimer = fishingPlan.getBuffDuration() - 5000;
                fishingPlan.reduceBuffCount(1);

                return;
            }

            updateBuffTimer(delta);

            if (buffTimer == 0) {
                fishing = false;
                return;
            }
        }

        if (!fishing) {
            mouseGlide(Utils.getMouseScreenX(), Utils.getMouseScreenY(), Utils.getRandomStageX(stage), Utils.getRandomStageY(stage), Utils.getRandomMoveTime(), 50);
            pressFishingHotkey();
            startFishingCycle();
            return;
        }

        checkFishingCycle(delta);
        checkBobber(delta);

        if (!biteFound) findBite();
        if (biteFound) {
            loot();
            resetFishingCycle();
            waitingDuration = fishingPlan.getSearchDelay();
            hooksCounter++;
        }
    }

    public void end() {
        if (exitPlan.isLogoutSelected())
            pressLogoutHotkey();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.exit(-4);
            e.printStackTrace();
        }

        if (exitPlan.isShutDownPcSelected())
            shutDownPc();

        System.exit(2);
    }

    private boolean shouldWait(double delta) {
        if (waitingDuration != 0) {
            waitingDuration -= delta;

            if (waitingDuration <= delta)
                waitingDuration = 0;

            return true;
        }

        return false;
    }

    private void startFishingCycle() {
        waitingDuration = fishingPlan.getSearchDelay();
        fishingTimer = fishingPlan.getFishingCycle();
        bobberCheckTimer = 0;
        fishing = true;
        castsCounter++;
    }

    private void checkFishingCycle(double delta) {
        if (fishingTimer <= delta)
            fishing = false;
        else
            fishingTimer -= delta;
    }

    private void checkBobber(double delta) {
        if (bobberCheckTimer <= delta) {
            findBobber();
            bobberCheckTimer = 5000;
        } else
            bobberCheckTimer -= delta;
    }

    private void resetFishingCycle() {
        bobberCheckTimer = 0;
        fishing = false;
        biteFound = false;
    }

    private void updateBuffTimer(double delta) {
        if (buffTimer > 0) {
            buffTimer -= delta;

            if (buffTimer <= delta) {
                buffTimer = 0;
            }
        }
    }

    private void findBobber() {
        BufferedImage fishingArea = getFishingArea();

        for (int x = 0; x < fishingArea.getWidth(); x++) {
            for (int y = 0; y < fishingArea.getHeight(); y++) {
                int currentColor = fishingArea.getRGB(x, y);
                double diff = (double) Math.abs(Math.abs(colorSettings.getBobberColor()) - Math.abs(currentColor)) / (double) Math.abs(colorSettings.getBobberColor()) * 100.0;

                if (diff <= colorSettings.getBobberSensitivity()) {
                    return;
                }
            }
        }

        fishing = false;
    }

    private void findBite() {
        BufferedImage fishingArea = getFishingArea();

        for (int x = 0; x < fishingArea.getWidth(); x++) {
            for (int y = 0; y < fishingArea.getHeight(); y++) {
                int currentColor = fishingArea.getRGB(x, y);
                double diff = (double) Math.abs(Math.abs(colorSettings.getBitingColor()) - Math.abs(currentColor)) / (double) Math.abs(colorSettings.getBitingColor()) * 100.0;

                if (diff <= colorSettings.getBitingSensitivity()) {
                    biteFound = true;
                    mouseGlide(Utils.getMouseScreenX(), Utils.getMouseScreenY(), (int) stage.getX() + x, (int) stage.getY() + y, Utils.getRandomMoveTime(), 50);
                    waitingDuration = 500;
                    return;
                }
            }
        }
    }

    private void mouseGlide(int x1, int y1, int x2, int y2, int t, int n) {
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
        Rectangle captureRect = new Rectangle((int) stage.getX(), (int) stage.getY(), (int) stage.getWidth(), (int) stage.getHeight());
        return robot.createScreenCapture(captureRect);
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

    private void loot() {
        if (fishingPlan.isShiftLoot()) robot.keyPress(16);
        robot.delay(75);
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.delay(75);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        robot.delay(75);
        if (fishingPlan.isShiftLoot()) robot.keyRelease(16);
    }

    private void pressLogoutHotkey() {
        String text = "/logout";
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);

        robot.keyPress(10);
        robot.delay(75);
        robot.keyRelease(10);
        robot.delay(75);

        robot.keyPress(17);
        robot.delay(75);
        robot.keyPress(86);
        robot.delay(75);
        robot.keyRelease(86);
        robot.delay(75);
        robot.keyRelease(17);
        robot.delay(75);

        robot.keyPress(10);
        robot.delay(75);
        robot.keyRelease(10);
    }

    private void shutDownPc() {
        Runtime runtime = Runtime.getRuntime();

        try {
            Process proc;
            switch (Utils.getOperatingSystemType()) {
                case Windows:
                    proc = runtime.exec("shutdown -s -t 60");
                    break;
                case MacOS:
                    proc = runtime.exec("sudo poweroff");
                    break;
                case Linux:
                    proc = runtime.exec("sudo shutdown -h +1");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
