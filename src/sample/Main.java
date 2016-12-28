package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller.library = FXMLLoader.load(getClass().getResource("Library.fxml"));
        Controller.options = FXMLLoader.load(getClass().getResource("Options.fxml"));
        Controller.graphMaker = FXMLLoader.load(getClass().getResource("GraphMaker.fxml"));
        Controller.saveMenu = FXMLLoader.load(getClass().getResource("SaveMenu.fxml"));

        Controller.options.setVisible(false);
        Controller.graphMaker.setVisible(false);
        Controller.saveMenu.setVisible(false);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(Controller.library, Controller.options, Controller.graphMaker, Controller.saveMenu);
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(stackPane, 1200, 800);
        scene.getStylesheets().add("sample.css");
        //scene.getStylesheets().add(EconoGraph2.class.getResource("/sample/sample.css"))/
        primaryStage.setScene(scene);

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
