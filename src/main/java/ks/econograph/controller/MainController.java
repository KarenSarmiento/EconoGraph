package ks.econograph.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ks.econograph.Context;
import ks.econograph.controller.screens.GraphMakerController;
import ks.econograph.controller.screens.LibraryController;
import ks.econograph.controller.screens.OptionsController;
import ks.econograph.controller.screens.SaveMenuController;
import ks.econograph.graph.components.Demand;
import ks.econograph.graph.components.Supply;

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
        System.out.println("setting visibilities");
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

    public void resetGraphMaker() {
        Context.getInstance().setDemandCount(0);
        Context.getInstance().setSupplyCount(0);
        Context.getInstance().setNewClassicalCount(0);
        Context.getInstance().setKeynesianCount(0);
        Context.getInstance().setCurveCount(0);

        if (Context.getInstance().getStaticGraphMakerWorkspaceP() != null && Context.getInstance().getCurvesLL().size() > 0) {
            Context.getInstance().getStaticGraphMakerWorkspaceP().getChildren().remove(1, Context.getInstance().getStaticGraphMakerWorkspaceP().getChildren().size());
            Context.getInstance().setCurvesLL(new LinkedList<>());
        }

        if (Context.getInstance().getStaticGraphMakerRadioButtonsFP() != null && Context.getInstance().getStaticGraphMakerRadioButtonsFP().getChildren().size() > 0) {
            Context.getInstance().getStaticGraphMakerRadioButtonsFP().getChildren().remove(1, Context.getInstance().getStaticGraphMakerRadioButtonsFP().getChildren().size());
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
