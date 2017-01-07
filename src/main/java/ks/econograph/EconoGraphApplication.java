package ks.econograph;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EconoGraphApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*Context.getInstance().setLibrary(FXMLLoader.load(getClass().getClassLoader().getResource("Library.fxml")));
        Context.getInstance().setGraphMaker(FXMLLoader.load(getClass().getClassLoader().getResource("GraphMaker.fxml")));
        Context.getInstance().setOptions(FXMLLoader.load(getClass().getClassLoader().getResource("Options.fxml")));
        Context.getInstance().setSaveMenu(FXMLLoader.load(getClass().getClassLoader().getResource("Save Menu.fxml")));

        Context.getInstance().getOptions().setVisible(false);
        Context.getInstance().getGraphMaker().setVisible(false);
        Context.getInstance().getSaveMenu().setVisible(false);*/

        Context.getInstance().initialize();

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(Context.getInstance().getLibrary(), Context.getInstance().getOptions(),
                Context.getInstance().getGraphMaker(), Context.getInstance().getSaveMenu());
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
