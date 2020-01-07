import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalTime;

public class Fishy extends Application {
    private static FishyConfiguration fishyConfiguration = FishyConfiguration.getInstance();

    public static void main(String[] args) {
        if (args.length != 21) System.exit(-1);

        initConfiguration(args);
        launch(args);
    }

    private static void initConfiguration(String[] args) {
        fishyConfiguration.setBobberColor(Integer.parseInt(args[0]));
        fishyConfiguration.setBobberColorSens(Integer.parseInt(args[1]));
        fishyConfiguration.setBitingColor(Integer.parseInt(args[2]));
        fishyConfiguration.setBitingColorSens(Integer.parseInt(args[3]));

        fishyConfiguration.setFishingCycle(Integer.parseInt(args[4]));
        fishyConfiguration.setSearchDelay(Integer.parseInt(args[5]));
        fishyConfiguration.setUseShiftLoot(args[6].compareTo("true") == 0);
        fishyConfiguration.setUseBuff(args[7].compareTo("true") == 0);
        fishyConfiguration.setBuffDuration(Integer.parseInt(args[8]));
        fishyConfiguration.setBuffCount(Integer.parseInt(args[9]));

        fishyConfiguration.setEndTimeSelected(args[10].compareTo("true") == 0);
        fishyConfiguration.setEndTime(LocalTime.parse(args[11]));
        fishyConfiguration.setCastsSelected(args[12].compareTo("true") == 0);
        fishyConfiguration.setCasts(Integer.parseInt(args[13]));
        fishyConfiguration.setHooksSelected(args[14].compareTo("true") == 0);
        fishyConfiguration.setHooks(Integer.parseInt(args[15]));
        fishyConfiguration.setHoursSelected(args[16].compareTo("true") == 0);
        fishyConfiguration.setNeverSelected(args[17].compareTo("true") == 0);
        fishyConfiguration.setBuffsUsedUpSelected(args[18].compareTo("true") == 0);
        fishyConfiguration.setLogout(args[19].compareTo("true") == 0);
        fishyConfiguration.setShutDownPc(args[20].compareTo("true") == 0);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FishyArea.fxml"));

        stage = new Stage();
        stage.initModality(Modality.NONE);
        stage.setTitle(Utils.getRandomString());

        Scene scene = new Scene(root);
        scene.setFill(javafx.scene.paint.Color.gray(0.25, 0.25));

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> System.exit(1));

        stage.show();

        ResizeHelper.addResizeListener(stage);
    }
}
