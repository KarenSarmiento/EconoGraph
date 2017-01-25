package ks.econograph.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ks.econograph.Context;
import ks.econograph.controller.screens.*;
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

    ShadedRegionOptionsController shadedRegionOptionsController;

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

        libraryController.init(this);
        graphMakerController.init(this);
        optionsController.init(this);
        saveMenuController.init(this);

        resetGraphMaker();
        readInGraphsToGraphsLL();
        libraryController.resetAndDisplayGraphsFromGraphsLLToLibrary();
        optionsController.setUpTemplateButtons();

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

    public void initializeShadedRegionScreen() {
        System.out.println("INITIALIZING SHADED REGION SCREEN");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShadedRegionOptions.fxml"));
            Parent root = (Parent) loader.load();
            shadedRegionOptionsController = loader.getController();
            shadedRegionOptionsController.init(this);

            Scene newScene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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

        if (graphMakerController.getGraphMakerCurveRadioFP() != null && graphMakerController.getGraphMakerCurveRadioFP().getChildren().size() > 0) {
            graphMakerController.getGraphMakerCurveRadioFP().getChildren().remove(1, graphMakerController.getGraphMakerCurveRadioFP().getChildren().size());
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

    public ShadedRegionOptionsController getShadedRegionOptionsController() {
        return shadedRegionOptionsController;
    }

    public void setShadedRegionOptionsController(ShadedRegionOptionsController shadedRegionOptionsController) {
        this.shadedRegionOptionsController = shadedRegionOptionsController;
    }
}
