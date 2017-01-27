package ks.econograph.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ks.econograph.Context;
import ks.econograph.controller.screens.*;
import ks.econograph.graph.components.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

        resetForNewGraph();
        resetAndReadInGraphsToGraphsLL();
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
            newScene.getStylesheets().add(getClass().getResource("/sample.css").toExternalForm());
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(newScene);
            newStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetAndReadInGraphsToGraphsLL() {
        Context.getInstance().getGraphsLL().clear();
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

    public void resetForNewGraph() {
        resetContextFields();
        graphMakerController.getGraphMakerWorkspaceP().getChildren().remove(3, graphMakerController.getGraphMakerWorkspaceP().getChildren().size());
        graphMakerController.getGraphMakerCurveRadioFP().getChildren().remove(1, graphMakerController.getGraphMakerCurveRadioFP().getChildren().size());
        graphMakerController.getGraphMakerShadedRegionRadioFP().getChildren().remove(1, graphMakerController.getGraphMakerShadedRegionRadioFP().getChildren().size());
        graphMakerController.getGraphMakerDownwardSlopingTF().setText("");
        graphMakerController.getGraphMakerUpwardSlopingTF().setText("");
        libraryController.resetSearchAndFilterOptions();
        saveMenuController.getSaveMenuDescriptionTA().setText("");
    }

    private void resetContextFields() {
        Context context = Context.getInstance();

        context.getIntersectionLL().clear();
        context.getShiftArrowsLL().clear();
        context.getShadedRegionsLL().clear();
        context.getShadedRegionFieldsLL().clear();
        context.getGraphMakerInsertedCurvesTG().getToggles().clear();
        context.getGraphMakerInsertedShadedRegionsTG().getToggles().clear();

        context.setDemandCount(0);
        context.setSupplyCount(0);
        context.setNewClassicalCount(0);
        context.setAggregateDemandCount(0);
        context.setAggregateSupplyCount(0);
        context.setMPBcount(0);
        context.setMPCcount(0);
        context.setMSBcount(0);
        context.setMSCcount(0);
        context.setCurveCount(0);

        context.getCurvesLL().clear();
        context.getDemandCurves().clear();
        context.getSupplyCurves().clear();
        context.getNewClassicalCurves().clear();
        context.getAggregateDemandCurves().clear();
        context.getAggregateSupplyCurves().clear();
        context.getMSBCurves().clear();
        context.getMSCCurves().clear();
        context.getMPBCurves().clear();
        context.getMPCCurves().clear();

        context.setSelectedCurveIndex(-1);
        context.setSelectedShadedRegionIndex(-1);
        context.setSelectedCurveType(-1);

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
