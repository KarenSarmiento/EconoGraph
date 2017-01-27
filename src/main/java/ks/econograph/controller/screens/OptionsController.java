package ks.econograph.controller.screens;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import ks.econograph.Context;
import ks.econograph.controller.MainController;
import ks.econograph.graph.components.Graph;
import ks.econograph.graph.components.ShadedRegion;


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
    @FXML
    TextField optionsXAxisTF = new TextField();
    @FXML
    TextField optionsYAxisTF = new TextField();
    @FXML
    ImageView optionsSampleGraphIV = new ImageView(new Image("File:C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\Images\\BlankSample.png"));
    @FXML
    TextField optionsXLabelTF = new TextField();
    @FXML
    TextField optionsYLabelTF = new TextField();

    public void blankCanvasRadioSelected() {
        optionsSampleGraphIV.setImage(new Image("File:C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\Images\\BlankSample.png"));
        optionsTitleTF.setText("");
        optionsXAxisTF.setText("");
        optionsYAxisTF.setText("");
        optionsXLabelTF.setText("");
        optionsYLabelTF.setText("");
    }

    public void microEquiblriumRadioSelected() {
        optionsSampleGraphIV.setImage(new Image("File:C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\Images\\MicroSample.png"));
        optionsTitleTF.setText("Microeconomic Equilibrium");
        optionsXAxisTF.setText("Quantity");
        optionsYAxisTF.setText("Price");
        optionsXLabelTF.setText("Q");
        optionsYLabelTF.setText("P");
    }

    public void macroEquibliriumRadioSelected() {
        optionsSampleGraphIV.setImage(new Image("File:C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\Images\\MacroSample.png"));
        optionsTitleTF.setText("Macroeconomic Equilibrium");
        optionsXAxisTF.setText("Real Output");
        optionsYAxisTF.setText("Average Price Level");
        optionsXLabelTF.setText("Y");
        optionsYLabelTF.setText("P");
    }

    public void externalityRadioSelected() {
        optionsSampleGraphIV.setImage(new Image("File:C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\Images\\ExternalitySample.png"));
        optionsTitleTF.setText("Externality");
        optionsXAxisTF.setText("Quantity");
        optionsYAxisTF.setText("Price");
        optionsXLabelTF.setText("Q");
        optionsYLabelTF.setText("P");
    }

    public void labourRadioSelected() {
        optionsSampleGraphIV.setImage(new Image("File:C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\Images\\LabourSample.png"));
        optionsTitleTF.setText("Labour Market");
        optionsXAxisTF.setText("Real Output");
        optionsYAxisTF.setText("Average Real Wage");
        optionsXLabelTF.setText("Q");
        optionsYLabelTF.setText("W");
    }

    public void subsidyRadioSelected() {
        optionsSampleGraphIV.setImage(new Image("File:C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\Images\\SubsidySample.png"));
        optionsTitleTF.setText("Subsidy");
        optionsXAxisTF.setText("Quantity");
        optionsYAxisTF.setText("Price");
        optionsXLabelTF.setText("Q");
        optionsYLabelTF.setText("P");
    }

    public void newGraphToGraphMaker() {
        if (optionsTitleTF.getText() == null) {
            System.out.println("Please enter a title.");
        }
        else {
            Context.getInstance().setCurrentEditingGraph(new Graph(optionsTitleTF.getText()));
            main.getSaveMenuController().getSaveMenuTitleTF().setText(optionsTitleTF.getText());
            main.getGraphMakerController().getGraphMakerXAxisL().setText(optionsXAxisTF.getText());
            main.getGraphMakerController().getGraphMakerYAxisL().setText(optionsYAxisTF.getText());
            setScreenToGraphMaker();
            if (((RadioButton) Context.getInstance().getOptionsTemplateTG().getSelectedToggle()).getText().equals("Blank Canvas") == false) {
                generateTemplate();
            }
        }
        Context.getInstance().setxIntersectionLabel(optionsXLabelTF.getText());
        Context.getInstance().setyIntersectionLabel(optionsYLabelTF.getText());
        if (Context.getInstance().getCurvesLL().size() != 0)
            main.getGraphMakerController().shiftSelectedCurve();
    }

    public void generateTemplate() {
        System.out.println("generate temple reached: " + ((RadioButton) Context.getInstance().getOptionsTemplateTG().getSelectedToggle()).getText());
        switch (((RadioButton) Context.getInstance().getOptionsTemplateTG().getSelectedToggle()).getId()) {
            case "Microeconomic Equilibrium": {
                generateMicroeconomicEquilibriumTemplate();
                break;
            }
            case "Macroeconomic Equilibrium": {
                generateMacroEconomicEquilibrium();
                break;
            }
            case "Externality": {
                generateExternality();
                break;
            }
            case "Labour Force": {
                generateLabourForce();
                break;
            }
            case "Subsidy": {
                generateSubsidyTemplate();
                break;
            }

        }
    }

    private  void generateExternality() {
        main.getGraphMakerController().insertMSB();
        main.getGraphMakerController().insertMSC();
        main.getGraphMakerController().insertMPC();
        main.getGraphMakerController().getGraphMakerShiftSlider().setValue(100);
        main.getGraphMakerController().shiftSelectedCurve();
        ShadedRegion shadedRegion = new ShadedRegion("Welfare Loss", "0x8ff6cbff", "MSC", "MSB", "Q", "Q1",
                main.getGraphMakerController().getGraphMakerWorkspaceP(), main.getGraphMakerController().getGraphMakerShadedRegionRadioFP());
        Context.getInstance().getShadedRegionsLL().add(shadedRegion);
        Context.getInstance().getShadedRegionFieldsLL().add("Welfare Loss,0x8ff6cbff,MSC,MSB,Q,Q1");

    }

    private void generateLabourForce() {
        main.getGraphMakerController().insertGeneralDownwardSloping("ADL");
        main.getGraphMakerController().insertGeneralUpwardSloping("ASL");
        main.getGraphMakerController().getGraphMakerElasticitySlider().setValue(80);
        main.getGraphMakerController().updateElasticityForCurrentCurve();
        main.getGraphMakerController().getGraphMakerShiftSlider().setValue(-25);
        main.getGraphMakerController().shiftSelectedCurve();
        main.getGraphMakerController().insertGeneralUpwardSloping("LF");
        main.getGraphMakerController().getGraphMakerElasticitySlider().setValue(25);
        main.getGraphMakerController().updateElasticityForCurrentCurve();
        main.getGraphMakerController().getGraphMakerShiftSlider().setValue(100);
        main.getGraphMakerController().shiftSelectedCurve();
    }

    private void generateMacroEconomicEquilibrium() {
        main.getGraphMakerController().insertAggregateDemand();
        main.getGraphMakerController().insertAggregateSupply();
        main.getGraphMakerController().insertNewClassical();
        main.getSaveMenuController().getSaveMenuTopicCB().setValue("Macro");
    }

    private void generateSubsidyTemplate() {
        main.getGraphMakerController().insertDemand();
        main.getGraphMakerController().insertSupply();
        main.getGraphMakerController().insertSupply();
        main.getGraphMakerController().getGraphMakerShiftSlider().setValue(100.0);
        main.getGraphMakerController().shiftSelectedCurve();
        main.getSaveMenuController().getSaveMenuTopicCB().setValue("Micro");
        //TODO: Add shaded regions to this
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
