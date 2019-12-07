import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainController {
    // Color Settings
    public Pane colorSettingsPane;
    public ImageView bobberColorImage;
    public ImageView bitingColorImage;
    public Slider bobberSensSlider;
    public Slider bitingSensSlider;
    public TextField bobberSensitivity;
    public TextField bitingSensitivity;
    public ImageView sampleImage;

    // General Settings
    public Pane generalSettingsPane;
    public TextField fishingHotkey;
    public TextField buffHotkey;
    public TextField sampleHotkey;
    public TextField bobberColorHotkey;
    public TextField biteColorHotkey;
    public TextField fishingDurationField;
    public TextField searchDelayField;
    public CheckBox useBuffCheckBox;
    public CheckBox shiftLootCheckBox;
    public TextField buffDurationField;
    public TextField buffCountField;
    public TextField fishingCycleField;
    public Button startButton;
    public Button closeButton;

    // Misc
    public Label warnErrorMessage;
    public AnchorPane mainStage;
    public ImageView bobberImage;
    public ImageView bitingImage;
    public ImageView bobberColorAverageImage;
    public ImageView bitingColorAverageImage;

    // Search
    private Stage searchStage;
    private AnimationTimer animationTimer;

    // Misc
    private int bobberColor;
    private int bitingColor;

    @FXML
    public void initialize() {
        if (bobberSensitivity != null && bobberSensSlider != null)
            bobberSensitivity.setText(String.valueOf((int)bobberSensSlider.getValue()));

        if (bitingSensitivity != null && bitingSensSlider != null)
            bitingSensitivity.setText(String.valueOf((int)bitingSensSlider.getValue()));

        if (bobberSensSlider != null)
            bobberSensSlider.valueProperty().addListener((observable, oldValue, newValue) -> bobberSensitivity.setText(String.valueOf(newValue.intValue())));

        if (bitingSensSlider != null)
            bitingSensSlider.valueProperty().addListener((observable, oldValue, newValue) -> bitingSensitivity.setText(String.valueOf(newValue.intValue())));
    }

    @FXML
    public void onFishyButtonPressed(ActionEvent actionEvent) {
        if (startButton.getText().compareTo("Stop") == 0)
            startButton.setText("Fishy");
        else {
            makeSearchStage(actionEvent);
            startButton.setText("Stop");
        }
    }

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
            generateSampleImage();
        } else if (keyCode == KeyCode.D) {
            generateColorImage(bobberColorImage);
            bobberColor = bobberColorImage.getImage().getPixelReader().getArgb(0, 0);
        } else if (keyCode == KeyCode.F) {
            generateColorImage(bitingColorImage);
            bitingColor = bitingColorImage.getImage().getPixelReader().getArgb(0, 0);
        }
    }

    private void generateSampleImage() {
        try {
            Robot robot = new Robot();
            Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            int width = 380;
            int height = 150;
            Rectangle captureRect = new Rectangle((int)mouseLocation.getX() - width/4, (int)mouseLocation.getY() - height / 2, width, height);
            BufferedImage sampleImageCaptured = robot.createScreenCapture(captureRect);
            sampleImage.setImage(SwingFXUtils.toFXImage(sampleImageCaptured, null));
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void generateColorImage(ImageView imageView) {
        try {
            BufferedImage image = new BufferedImage(190, 150, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = image.createGraphics();
            Robot robot = new Robot();
            Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            Color pixelColor = robot.getPixelColor((int)mouseLocation.getX(), (int)mouseLocation.getY());
            graphics.setColor(pixelColor);
            graphics.fillRect ( 0, 0, image.getWidth(), image.getHeight());
            imageView.setImage(SwingFXUtils.toFXImage(image, null));
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void makeSearchStage(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("SearchArea.fxml"));

            searchStage = new Stage();
            searchStage.initModality(Modality.NONE);
            searchStage.setTitle(Utils.getRandomString());

            Scene scene = new Scene(root);
            scene.setFill(javafx.scene.paint.Color.gray(0.25, 0.25));
            scene.setOnKeyPressed(keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    stopFishing();
                    searchStage.close();
                    ((Stage)((Button)actionEvent.getSource()).getScene().getWindow()).setIconified(false);
                }
            });

            scene.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        searchStage.toBack();
                        startFishing();
                    }
                }
            });

            searchStage.initStyle(StageStyle.TRANSPARENT);
            searchStage.setScene(scene);

            ((Stage)((Button)actionEvent.getSource()).getScene().getWindow()).setIconified(true);
            searchStage.show();

            ResizeHelper.addResizeListener(searchStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exit() {
        if (searchStage != null) stopFishing();
        System.exit(1);
    }

    private void startFishing() {
        try {
            animationTimer = new AnimationTimer()
            {
                Robot robot = new Robot();
                Fisher fisher = new Fisher(
                        bobberImage,
                        bitingImage,
                        searchStage,
                        bobberColor,
                        bitingColor,
                        Integer.parseInt(bobberSensitivity.getText()),
                        Integer.parseInt(bitingSensitivity.getText()),
                        Integer.parseInt(fishingCycleField.getText()),
                        Integer.parseInt(fishingDurationField.getText()),
                        Integer.parseInt(searchDelayField.getText()),
                        Integer.parseInt(buffDurationField.getText()),
                        Integer.parseInt(buffCountField.getText()),
                        shiftLootCheckBox.isSelected(),
                        useBuffCheckBox.isSelected());

                long lastUpdate = System.nanoTime();
                int fpsTime = 100;

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
        if (searchStage != null) searchStage.close();
        if (animationTimer != null) animationTimer.stop();

        startButton.setText("Fishy");
    }
}
