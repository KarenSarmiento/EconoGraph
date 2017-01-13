package ks.econograph.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ks.econograph.Context;
import ks.econograph.controller.screens.GraphMakerController;
import ks.econograph.controller.screens.LibraryController;
import ks.econograph.controller.screens.OptionsController;
import ks.econograph.controller.screens.SaveMenuController;
import ks.econograph.graph.components.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by KSarm on 07/01/2017.
 */
public class MainController {
    @FXML
    LibraryController libraryController;
    @FXML
    GraphMakerController graphMakerController;
    @FXML
    OptionsController optionsController;
    @FXML
    SaveMenuController saveMenuController;

    @FXML
    AnchorPane libraryAP;
    @FXML
    AnchorPane graphMakerAP;
    @FXML
    AnchorPane optionsAP;
    @FXML
    AnchorPane saveMenuAP;

    @FXML public void initialize() {
        System.out.println("Application started");
        resetGraphMaker();

        libraryController.init(this);
        graphMakerController.init(this);
        optionsController.init(this);
        saveMenuController.init(this);

        readInGraphsToGraphsLL();
        resetAndDisplayGraphsFromGraphsLLToLibrary();
        System.out.println(Context.getInstance().getGraphsLL());
        setScreenToLibrary();
    }

    public void setScreenToLibrary() {
        libraryAP.setVisible(true);
        graphMakerAP.setVisible(false);
        optionsAP.setVisible(false);
        saveMenuAP.setVisible(false);
    }

    public void setScreenToGraphMaker() {

        libraryAP.setVisible(false);
        graphMakerAP.setVisible(true);
        optionsAP.setVisible(false);
        saveMenuAP.setVisible(false);
    }

    public void setScreenToOptions() {
        libraryAP.setVisible(false);
        graphMakerAP.setVisible(false);
        optionsAP.setVisible(true);
        saveMenuAP.setVisible(false);
    }

    public void setScreenToSaveMenu() {
        libraryAP.setVisible(false);
        graphMakerAP.setVisible(false);
        optionsAP.setVisible(false);
        saveMenuAP.setVisible(true);
    }

    public void readInGraphsToGraphsLL() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Context.getInstance().getFilePathForSavedGraphs()));
            String readLine;
            String[] splitLine;
            while ((readLine = br.readLine()) != null) {
                splitLine = readLine.split(",");
                if (!splitLine[0].equals("newComp")) {
                    Graph graph;
                    if (splitLine[4].equals("true")) {
                        graph = new Graph(splitLine[0], splitLine[1], Long.parseLong(splitLine[2]), splitLine[3], true, splitLine[5]);
                    }
                    else {
                        graph = new Graph(splitLine[0], splitLine[1], Long.parseLong(splitLine[2]), splitLine[3], false, splitLine[5]);
                    }
                    Context.getInstance().getGraphsLL().add(graph);
                }
            }
        }
        catch (IOException ioe) {
            System.out.println("Error loading graphs");
        }
    }

    public void resetAndDisplayGraphsFromGraphsLLToLibrary() {
        libraryController.libraryGraphGP.getChildren().remove(5,libraryController.libraryGraphGP.getChildren().size());
        libraryController.libraryGraphGP.setGridLinesVisible(true);
        for (int i = 0; i < Context.getInstance().getGraphsLL().size(); i++) {
            if (Context.getInstance().getGraphsLL().get(i).isVisible() == true) {
                Label title = new Label(Context.getInstance().getGraphsLL().get(i).getTitle());
                libraryController.libraryGraphGP.setConstraints(title, 0, i + 1);
                ImageView graphImage = new ImageView(new Image("file:" + Context.getInstance().getFileLocationForSavedImages() +
                        Context.getInstance().getGraphsLL().get(i).getFileName()));
                System.out.println(Context.getInstance().getFileLocationForSavedImages() +
                        Context.getInstance().getGraphsLL().get(i).getFileName());
                graphImage.setFitWidth(250);
                graphImage.setFitHeight(152);
                VBox graphColumnContents = new VBox(3);
                graphColumnContents.getChildren().addAll(title, graphImage);
                libraryController.libraryGraphGP.setConstraints(graphColumnContents, 0, i + 1);
                Label description = new Label(Context.getInstance().getGraphsLL().get(i).getDescription());
                libraryController.libraryGraphGP.setConstraints(description, 1, i + 1);
                if (Context.getInstance().getGraphsLL().get(i).isFavourite()) {
                    ImageView favouriteStar = new ImageView(new Image("file:C:\\Users\\KSarm\\Documents\\Intellij Projects\\EconoGraph2\\src\\main\\resources\\FavouritesStar.png"));
                    favouriteStar.setFitHeight(50);
                    favouriteStar.setFitWidth(50);
                    libraryController.libraryGraphGP.setConstraints(favouriteStar, 2, i + 1);
                    libraryController.libraryGraphGP.getChildren().add(favouriteStar);

                }
                libraryController.libraryGraphGP.getChildren().addAll(graphColumnContents, description);
            }
        }
    }

    public void resetGraphMaker() {
        Context.getInstance().setDemandCount(0);
        Context.getInstance().setSupplyCount(0);
        Context.getInstance().setNewClassicalCount(0);
        Context.getInstance().setKeynesianCount(0);
        Context.getInstance().setCurveCount(0);

        if (graphMakerController.getGraphMakerWorkspaceP() != null && Context.getInstance().getCurvesLL().size() > 0) {
            graphMakerController.getGraphMakerWorkspaceP().getChildren().remove(1, graphMakerController.getGraphMakerWorkspaceP().getChildren().size());
            Context.getInstance().setCurvesLL(new LinkedList<>());
        }

        if (graphMakerController.getGraphMakerRadioButtonsFP() != null && graphMakerController.getGraphMakerRadioButtonsFP().getChildren().size() > 0) {
            graphMakerController.getGraphMakerRadioButtonsFP().getChildren().remove(1, graphMakerController.getGraphMakerRadioButtonsFP().getChildren().size());
        }
        libraryController.resetSearchAndFilterOptions();
    }

    public LibraryController getLibraryController() {
        return libraryController;
    }

    public void setLibraryController(LibraryController libraryController) {
        this.libraryController = libraryController;
    }

    public GraphMakerController getGraphMakerController() {
        return graphMakerController;
    }

    public void setGraphMakerController(GraphMakerController graphMakerController) {
        this.graphMakerController = graphMakerController;
    }

    public OptionsController getOptionsController() {
        return optionsController;
    }

    public void setOptionsController(OptionsController optionsController) {
        this.optionsController = optionsController;
    }

    public SaveMenuController getSaveMenuController() {
        return saveMenuController;
    }

    public void setSaveMenuController(SaveMenuController saveMenuController) {
        this.saveMenuController = saveMenuController;
    }
}
