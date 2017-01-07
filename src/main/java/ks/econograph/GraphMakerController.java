package ks.econograph;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import ks.econograph.graph.components.Demand;
import ks.econograph.graph.components.Supply;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by KSarm on 01/01/2017.
 */
public class GraphMakerController {

    @FXML
    Pane graphMakerWorkspaceP = new Pane();
    @FXML
    FlowPane graphMakerRadioButtonsFP = new FlowPane();
    @FXML
    Slider graphMakerShiftSlider = new Slider();
    @FXML
    Slider graphMakerElasticitySlider = new Slider();
    @FXML
    ColorPicker graphMakerColourPicker = new ColorPicker();
    @FXML
    Slider graphMakerThicknessSlider = new Slider();

    public void captureShotWorkspace() {
        //TODO: Fix writable image
       /* Context.getInstance().setTempScreenShot(graphMakerWorkspaceP.snapshot(new SnapshotParameters(), null));
        File imageFile = new File("C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\FileWriting\\tempWorkspace.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(Context.getInstance().getTempScreenShot(), null), "png", imageFile);
        } catch (IOException e) {
            System.out.println("ioe");
        }*/
    }

    public void captureAndSetToSave() {
        captureShotWorkspace(); //sets tempScreenShot
        setScreenToSaveMenu();
        //TODO: on click of done set save of file loca
    }

    public void shiftSelectedCurve() {
        switch (Context.getInstance().getSelectedCurveType()) {
            case 0: {
                Demand demand = (Demand) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveType());
                demand.setCentreX(375 + (int) graphMakerShiftSlider.getValue());
                demand.getLine().setStartX(demand.getCentreX() - demand.getElasticityGap());
                demand.getLine().setEndX(demand.getCentreX() + demand.getElasticityGap());
                break;
            }
            case 1: {
                Supply supply = (Supply) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex());
                supply.setCentreX(375 + (int) graphMakerShiftSlider.getValue());
                supply.getLine().setStartX(supply.getCentreX() + supply.getElasticityGap());
                supply.getLine().setEndX(supply.getCentreX() - supply.getElasticityGap());
                break;
            }
        }
    }

    public void updateElasticityForCurrentCurve() {
        System.out.println(Context.getInstance().getSelectedCurveType() + " " + Context.getInstance().getSelectedCurveIndex());
        switch (Context.getInstance().getSelectedCurveType()) {
            case 0: {
                Demand demand = (Demand) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex());
                demand.setElasticityGap((int) graphMakerElasticitySlider.getValue());
                demand.getLine().setStartX(demand.getCentreX() - demand.getElasticityGap());
                demand.getLine().setEndX(demand.getCentreX() + demand.getElasticityGap());
                break;
            }
            case 1: {
                Supply supply = (Supply) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex());
                supply.setElasticityGap((int) graphMakerElasticitySlider.getValue());
                supply.getLine().setStartX(supply.getCentreX() + supply.getElasticityGap());
                supply.getLine().setEndX(supply.getCentreX() - supply.getElasticityGap());
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

    public void updateCurveColour() {
        switch(Context.getInstance().getSelectedCurveType()) {
            case 0: {
                Demand demand = (Demand) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex());
                demand.getLine().setStroke(graphMakerColourPicker.getValue());
                demand.setColour(graphMakerColourPicker.getValue().toString());
                break;
            }
            case 1: {
                System.out.println("supply detected");
                //TODO: supply curve colour not working
                Supply supply = (Supply) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex());
                supply.getLine().setStroke(graphMakerColourPicker.getValue());
                supply.setColour(graphMakerColourPicker.getValue().toString());
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

    public void updateThickness() {
        switch(Context.getInstance().getSelectedCurveType()) {
            case 0: {
                Demand demand = (Demand) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex());
                demand.getLine().setStrokeWidth((int) graphMakerThicknessSlider.getValue());
                demand.setThickness((int) graphMakerThicknessSlider.getValue());
                System.out.println(graphMakerThicknessSlider.getValue());
                break;
            }
            case 1: {
                Supply supply = (Supply) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex());
                supply.getLine().setStrokeWidth((int) graphMakerThicknessSlider.getValue());
                supply.setThickness((int) graphMakerThicknessSlider.getValue());
                System.out.println(graphMakerThicknessSlider.getValue());
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

    public void insertDemand() {
        System.out.println("insert demand reached");

        //if (Context.getInstance().getStaticGraphMakerWorkspaceP() == null) {
            Context.getInstance().setStaticGraphMakerWorkspaceP(graphMakerWorkspaceP);
        //}
        Context.getInstance().setDemandCount(Context.getInstance().getDemandCount() +1);
        Context.getInstance().setCurveCount(Context.getInstance().getCurveCount()+1);
        int index = Context.getInstance().getDemandCount();
        createRadioButton("Demand " + index, index, 0);
        Demand demand = new Demand(Context.getInstance().getStaticGraphMakerWorkspaceP(), index); //200,50,550,400
        Context.getInstance().getCurvesLL().add(demand);
        System.out.println(Context.getInstance().getCurvesLL().toString());
        System.out.println(Context.getInstance().getDemandCount());
        System.out.println(Context.getInstance().getCurveCount());

    }

    public void insertSupply() {
        if (Context.getInstance().getStaticGraphMakerWorkspaceP() == null) {
            Context.getInstance().setStaticGraphMakerWorkspaceP(graphMakerWorkspaceP);
        }
        Context.getInstance().setSupplyCount(Context.getInstance().getSupplyCount() +1);
        int index = Context.getInstance().getCurveCount() -1;
        createRadioButton("Supply " + Context.getInstance().getSupplyCount(), index, 0);
        Supply supply = new Supply(Context.getInstance().getStaticGraphMakerWorkspaceP(), index); //200,50,550,400
        Context.getInstance().getCurvesLL().add(supply);
        System.out.println(Context.getInstance().getCurvesLL().toString());

    }

    public void createRadioButton(String name, int index, int type) {
        //if (Context.getInstance().getStaticGraphMakerRadioButtonsFP() == null) {
            Context.getInstance().setStaticGraphMakerRadioButtonsFP(graphMakerRadioButtonsFP);
        //}

        //TODO: Make createRadioButton() compatible with other curves!
        RadioButton radioButton = new RadioButton(name);
        radioButton.setId("demandRadio" + index);
        radioButton.setToggleGroup(Context.getInstance().getToggleGroup());
        System.out.println("Created radio button: " + radioButton);
        //TODO: there is some problem with radioButton.setSelected(true);
        radioButton.setOnAction(e -> {
            Context.getInstance().setSelectedCurveIndex(index);
            Context.getInstance().setSelectedCurveType(type);
            //need to set to previously set elasticity or shift
            graphMakerElasticitySlider.setValue(Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex()).getElasticityGap());
            graphMakerShiftSlider.setValue(0);
            graphMakerThicknessSlider.setValue(5);
        });
        if (Context.getInstance().getCurveCount() -1 == 0) {
            radioButton.setSelected(true);
        }
        Context.getInstance().getStaticGraphMakerRadioButtonsFP().getChildren().add(radioButton);
    }

    public void setScreenToSaveMenu() {
        Context.getInstance().getLibrary().setVisible(false);
        Context.getInstance().getOptions().setVisible(false);
        Context.getInstance().getGraphMaker().setVisible(false);
        Context.getInstance().getSaveMenu().setVisible(true);
    }

    /*public void insertNewClassical() {
        newClassicalCount++;
        createRadioButton("New Classical " + newClassicalCount, demandCount + supplyCount + newClassicalCount + keynesianCount -1, 2);
        //curvesLL.add(supply);
    }

    public void insertKeynesian() {
        keynesianCount++;
        createRadioButton("Keynesian " + keynesianCount, demandCount + supplyCount + newClassicalCount + keynesianCount -1, 3);
        //curvesLL.add(supply);
    }

    public void insertDemand(String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        if (staticGraphMakerWorkspaceP == null) {
            staticGraphMakerWorkspaceP = graphMakerWorkspaceP;
        }
        demandCount++;
        int index = demandCount + supplyCount + newClassicalCount + keynesianCount - 1;
        createRadioButton("Demand " + demandCount, index, 0);
        Demand demand = new Demand(staticGraphMakerWorkspaceP, name, centreX, elasticityGap, colour, thickness, dotted, index); //200,50,550,400
        curvesLL.add(demand);
        System.out.println(curvesLL.toString());
    }

    public void insertSupply(String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        supplyCount++;
        createRadioButton("Supply " + supplyCount, demandCount + supplyCount + newClassicalCount + keynesianCount -1, 1);
        Supply supply = new Supply(graphMakerWorkspaceP, name, centreX, elasticityGap, colour, thickness, dotted); //200,400,550,50
        curvesLL.add(supply);
        System.out.println(curvesLL.toString());
    }

    public void insertNewClassical(String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) { //change parameters
        newClassicalCount++;
        createRadioButton("New Classical " + newClassicalCount, demandCount + supplyCount + newClassicalCount + keynesianCount -1, 2);
        //curvesLL.add(supply);
    }

    public void insertKeynesian(String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) { //change parameters
        newClassicalCount++;
        keynesianCount++;
        createRadioButton("Keynesian " + keynesianCount, demandCount + supplyCount + newClassicalCount + keynesianCount -1, 3);
        //curvesLL.add(supply);
    }*/
}
