import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.awt.event.ActionListener;

public class Controller {
    public Pane colorSettingsPane;
    public ImageView bobberColor;
    public ImageView bitingColor;
    public Slider bobberSens;
    public Slider bitingSens;
    public TextField bobberSensValue;
    public TextField bitingSensValue;
    public ImageView sampleImage;

    public Pane generalSettingsPane;
    public TextField fishingHotkey;
    public TextField buffHotkey;
    public TextField sampleHotkey;
    public TextField bobberColorHotkey;
    public TextField biteColorHotkey;
    public TextField fishingDuration;
    public TextField searchDelay;
    public CheckBox useBuff;
    public CheckBox shiftLoot;
    public TextField buffDuration;
    public TextField buffCount;
    public Button startButton;
    public Button closeButton;

    public Label warnErrorMessage;

    @FXML
    public void onCloseButtonPressed(ActionEvent actionEvent) {
        exit();
    }

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent == null || keyEvent.getCode() == null)
            return;

        KeyCode keyCode = keyEvent.getCode();

        if (keyCode == KeyCode.ESCAPE) {
            exit();
        } else if (keyCode == KeyCode.S) {

        }
    }

    private void exit() {
        // do stuff before exit
        System.exit(1);
    }
}
