import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainController {
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
    public AnchorPane mainStage;

    private Stage searchStage;

    private double xOffset = 0;
    private double yOffset = 0;
    private boolean fishing;
    private boolean buffing;

    @FXML
    public void initialize() {
        //bitingSensValue.setText(((int)bitingSens.getValue()));
    }

    @FXML
    public void onFishyButtonPressed(ActionEvent actionEvent) {
        if (startButton.getText().compareTo("Stop") == 0) {

            startButton.setText("Fishy");
        } else {
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
            generateColorImage(bobberColor);
        } else if (keyCode == KeyCode.F) {
            generateColorImage(bitingColor);
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

    private void exit() {
        // do stuff before exit
        System.exit(1);
    }

    private void startFishing() {
        fishing = true;

    }

    private void stopFishing() {
        fishing = false;
        buffing = false;
    }
}
