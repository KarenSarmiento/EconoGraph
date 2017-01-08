package ks.econograph;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EconoGraphApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*Context.getInstance().initialize();

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(Context.getInstance().getLibrary(), Context.getInstance().getOptions(),
                Context.getInstance().getGraphMaker(), Context.getInstance().getSaveMenu());
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(stackPane, 1200, 800);
        scene.getStylesheets().add("sample.css");
        //scene.getStylesheets().add(EconoGraph2.class.getResource("/sample/sample.css"))/
        primaryStage.setScene(scene);

        primaryStage.show();*/

        Context.getInstance().initializeMainRoot();
        Scene scene = new Scene(Context.getInstance().getMainRoot());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
