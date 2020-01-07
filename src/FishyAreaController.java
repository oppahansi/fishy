import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class FishyAreaController {
    public AnchorPane fishyArea;

    private FishyConfiguration fishyConfiguration = FishyConfiguration.getInstance();
    private ColorSettings colorSettings;
    private FishingPlan fishingPlan;
    private ExitPlan exitPlan;
    private AnimationTimer animationTimer;

    @FXML
    public void initialize() {
        setConfigurations();

        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true);
        keyboardHook.addKeyListener(new GlobalKeyAdapter() {
            @Override
            public void keyReleased(GlobalKeyEvent keyEvent) {
                if (keyEvent == null)
                    return;

                int keyCode = keyEvent.getVirtualKeyCode();

                if (keyCode == GlobalKeyEvent.VK_ESCAPE) {
                    if (animationTimer != null) stopFishing();
                    else exit();
                }
            }
        });
    }

    @FXML
    public void onMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                ((Stage) fishyArea.getScene().getWindow()).toBack();
                startFishing();
            }
        }
    }

    private void setConfigurations() {
        colorSettings = new ColorSettings(
                fishyConfiguration.getBobberColor(), fishyConfiguration.getBobberColorSens(),
                fishyConfiguration.getBitingColor(), fishyConfiguration.getBitingColorSens());
        fishingPlan = new FishingPlan(
                fishyConfiguration.getFishingCycle(), fishyConfiguration.getSearchDelay(),
                fishyConfiguration.isUseShiftLoot(), fishyConfiguration.isUseBuff(),
                fishyConfiguration.getBuffDuration(), fishyConfiguration.getBuffCount());
        exitPlan = new ExitPlan(
                fishyConfiguration.isEndTimeSelected(), fishyConfiguration.getEndTime(),
                fishyConfiguration.isHooksSelected(), fishyConfiguration.getHooks(),
                fishyConfiguration.isCastsSelected(), fishyConfiguration.getCasts(),
                fishyConfiguration.isHoursSelected(), fishyConfiguration.isNeverSelected(),
                fishyConfiguration.isBuffsUsedUpSelected(), fishyConfiguration.isLogout(),
                fishyConfiguration.isShutDownPc()
        );
    }

    private void startFishing() {
        try {
            animationTimer = new AnimationTimer() {
                Fisher fisher = new Fisher((Stage) fishyArea.getScene().getWindow(), colorSettings, fishingPlan, exitPlan);
                long lastUpdate = System.nanoTime();

                @Override
                public void handle(long now) {
                    double delta = (now - lastUpdate) / 1000000.0;
                    fisher.update(delta);
                    lastUpdate = now;
                }
            };

            animationTimer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopFishing() {
        animationTimer.stop();
        animationTimer = null;
    }

    private void exit() {
        System.exit(1);
    }
}
