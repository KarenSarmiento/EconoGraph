package ks.econograph.controller.screens;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ks.econograph.controller.MainController;

/**
 * Created by KSarm on 20/01/2017.
 */
public class ShadedRegionOptionsController {

    private MainController main;

    public void initializeShadedRegionScreen() {
        try {
            Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("ShadedRegionOptions.fxml"));
            Scene shadedRegionScene = new Scene(root1);
            Stage shadedRegionStage = new Stage();
            shadedRegionStage.setScene(shadedRegionScene);
            shadedRegionStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(MainController mainController) {
        main = mainController;
    }
}
