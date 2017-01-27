package ks.econograph;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EconoGraphApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Context.getInstance().initializeMainRoot();
        Scene scene = new Scene(Context.getInstance().getMainRoot());
        scene.getStylesheets().add(getClass().getResource("/sample.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
