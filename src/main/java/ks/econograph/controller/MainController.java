package ks.econograph.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import ks.econograph.Context;
import ks.econograph.controller.screens.GraphMakerController;
import ks.econograph.controller.screens.LibraryController;
import ks.econograph.controller.screens.OptionsController;
import ks.econograph.controller.screens.SaveMenuController;
import ks.econograph.graph.components.Demand;
import ks.econograph.graph.components.Graph;
import ks.econograph.graph.components.Supply;

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
                    if (splitLine[3].equals("true")) {
                        graph = new Graph(splitLine[0], splitLine[1], Long.parseLong(splitLine[2]), true);
                    }
                    else {
                        graph = new Graph(splitLine[0], splitLine[1], Long.parseLong(splitLine[2]), false);
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

        if (graphMakerController.getGraphMakerRadioButtonsFP() != null && graphMakerController.getGraphMakerRadioButtonsFP().getChildren().size() > 0) {
            graphMakerController.getGraphMakerRadioButtonsFP().getChildren().remove(1, graphMakerController.getGraphMakerRadioButtonsFP().getChildren().size());
        }
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
