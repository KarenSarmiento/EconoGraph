package ks.econograph.controller.screens;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ks.econograph.Context;
import ks.econograph.controller.MainController;
import ks.econograph.graph.components.Graph;

/**
 * Created by KSarm on 07/01/2017.
 */
public class OptionsController {

    private MainController main;

    @FXML
    public GridPane optionsGP;
    @FXML
    TextField optionsTitleTF = new TextField();
    @FXML
    RadioButton optionsBlankCanvasRB = new RadioButton();
    @FXML
    RadioButton optionsMicroRB = new RadioButton();
    @FXML
    RadioButton optionsMacroRB = new RadioButton();
    @FXML
    RadioButton optionsExternalityRB = new RadioButton();
    @FXML
    RadioButton optionsLabourForceRB = new RadioButton();
    @FXML
    RadioButton optionsSubsidyRB = new RadioButton();

    public void newGraphToGraphMaker() {
        if (optionsTitleTF.getText() == null) {
            System.out.println("Please enter a title.");
        }
        else {
            Context.getInstance().setCurrentEditingGraph(new Graph(optionsTitleTF.getText()));
            main.getSaveMenuController().getSaveMenuTitleTF().setText(optionsTitleTF.getText());
            setScreenToGraphMaker();
            if (((RadioButton) Context.getInstance().getOptionsTemplateTG().getSelectedToggle()).getText().equals("Blank Canvas") == false) {
                generateTemplate();
            }
        }
    }

    public void generateTemplate() {
        System.out.println("generate temple reached: " + ((RadioButton) Context.getInstance().getOptionsTemplateTG().getSelectedToggle()).getText());
        switch (((RadioButton) Context.getInstance().getOptionsTemplateTG().getSelectedToggle()).getText()) {
            case "Microeconomic Equilibrium": {
                generateMicroeconomicEquilibriumTemplate();
                break;
            }
            case "Macroeconomic Equilibrium": {
                break;
            }
            case "Externality": {
                break;
            }
            case "Labour Force": {
                break;
            }
            case "Subsidy": {
                break;
            }

        }
    }

    private void generateMicroeconomicEquilibriumTemplate() {
        main.getGraphMakerController().insertDemand();
        main.getGraphMakerController().insertSupply();
        main.getSaveMenuController().getSaveMenuTopicCB().setValue("Micro");

    }

    public void setUpTemplateButtons() {
        optionsBlankCanvasRB.setToggleGroup(Context.getInstance().getOptionsTemplateTG());
        optionsMicroRB.setToggleGroup(Context.getInstance().getOptionsTemplateTG());
        optionsMacroRB.setToggleGroup(Context.getInstance().getOptionsTemplateTG());
        optionsExternalityRB.setToggleGroup(Context.getInstance().getOptionsTemplateTG());
        optionsLabourForceRB.setToggleGroup(Context.getInstance().getOptionsTemplateTG());
        optionsSubsidyRB.setToggleGroup(Context.getInstance().getOptionsTemplateTG());

        optionsBlankCanvasRB.setSelected(true);
    }

    public void setScreenToLibrary() {
        main.setScreenToLibrary();
    }

    public void setScreenToGraphMaker() {
        main.setScreenToGraphMaker();
    }

    public void test() {
        switch(Context.getInstance().getSelectedCurveType()) {
            case 0: {
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
        }
    }

    public void init(MainController mainController) {
        main = mainController;
    }
}
