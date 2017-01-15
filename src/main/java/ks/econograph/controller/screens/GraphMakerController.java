package ks.econograph.controller.screens;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import ks.econograph.Context;
import ks.econograph.controller.MainController;
import ks.econograph.graph.components.Demand;
import ks.econograph.graph.components.Supply;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by KSarm on 01/01/2017.
 */
public class GraphMakerController {

    private MainController main;

    @FXML
    public GridPane graphMakerGP;
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

    public void findIntersectionofDemandWithOtherCurves() {
        System.out.println("findIntersectionofDemandWithOtherCurves() reached");
        Line newLine = ((Demand) Context.getInstance().getCurvesLL().get(Context.getInstance().getCurvesLL().size()-1)).getLine();
        double newGradient = (newLine.getStartY() - newLine.getEndY())/(newLine.getStartX() - newLine.getEndX());
        double newYIntercept = newLine.getStartY() - newGradient * newLine.getStartX();
        System.out.println("newGradient : " + newGradient);
        System.out.println("newYIntercept : " + newYIntercept);

        //for (int i = 0; i < Context.getInstance().getCurvesLL().size() -1; i++) {
            //TODO: curveType should be a curve field and set as a default and final value in constructor for specific curve types
            Line comparedLine = ((Supply) Context.getInstance().getCurvesLL().get(0)).getLine();
            double comparedGradient = (comparedLine.getStartY() - comparedLine.getEndY())/(comparedLine.getStartX() - comparedLine.getEndX());
            double comparedYIntercept = comparedLine.getStartY() - comparedGradient * comparedLine.getStartX();

            System.out.println("comparedGradient : " + comparedGradient);
            System.out.println("comparedYIntercept : " + comparedYIntercept);

            //Intersection at
            double x = (comparedYIntercept - newYIntercept)/(newGradient - comparedGradient);
            double y = newGradient * x + newYIntercept;
            Line line = new Line(x, y, x, y+200);
            Line line2 = new Line(x, y, x-200, y);
            main.getGraphMakerController().getGraphMakerWorkspaceP().getChildren().addAll(line, line2);
        //}
    }

    public void captureAndSetToSave() {
        captureShotWorkspace();
        main.getSaveMenuController().setImageAsCurrentEditingGraphIamge();
        setScreenToSaveMenu();
    }

    public void captureShotWorkspace() {
        System.out.println("Capturing workspace");
        Context.getInstance().setTempScreenShot(graphMakerWorkspaceP.snapshot(new SnapshotParameters(), null));
        System.out.println(Context.getInstance().getCurrentEditingGraph().toString());
        File imageFile = new File(Context.getInstance().getFileLocationForSavedImages() + Context.getInstance().getCurrentEditingGraph().getFileName());
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(Context.getInstance().getTempScreenShot(), null), "png", imageFile);
        } catch (IOException e) {
            System.out.println("ioe");
        }
    }

    public void shiftSelectedCurve() {
        switch (Context.getInstance().getSelectedCurveType()) {
            case 0: {
                Demand demand = (Demand) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex() -1);
                demand.setCentreX(375 + (int) graphMakerShiftSlider.getValue());
                demand.getLine().setStartX(demand.getCentreX() - demand.getElasticityGap());
                demand.getLine().setEndX(demand.getCentreX() + demand.getElasticityGap());
                break;
            }
            case 1: {
                Supply supply = (Supply) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex() -1);
                supply.setCentreX(375 + (int) graphMakerShiftSlider.getValue());
                supply.getLine().setStartX(supply.getCentreX() - supply.getElasticityGap());
                supply.getLine().setEndX(supply.getCentreX() + supply.getElasticityGap());
                break;
            }
        }
    }

    public void updateElasticityForCurrentCurve() {
        switch (Context.getInstance().getSelectedCurveType()) {
            case 0: {
                Demand demand = (Demand) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex() -1);
                demand.setElasticityGap((int) graphMakerElasticitySlider.getValue());
                demand.getLine().setStartX(demand.getCentreX() - demand.getElasticityGap());
                demand.getLine().setEndX(demand.getCentreX() + demand.getElasticityGap());
                break;
            }
            case 1: {
                Supply supply = (Supply) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex() -1);
                supply.setElasticityGap((int) graphMakerElasticitySlider.getValue());
                supply.getLine().setStartX(supply.getCentreX() - supply.getElasticityGap());
                supply.getLine().setEndX(supply.getCentreX() + supply.getElasticityGap());
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
        switch (Context.getInstance().getSelectedCurveType()) {
            case 0: {
                Demand demand = (Demand) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex() -1);
                demand.getLine().setStroke(graphMakerColourPicker.getValue());
                demand.setColour(graphMakerColourPicker.getValue().toString());
                break;
            }
            case 1: {
                Supply supply = (Supply) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex() -1);
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
        switch (Context.getInstance().getSelectedCurveType()) {
            case 0: {
                Demand demand = (Demand) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex() -1);
                demand.getLine().setStrokeWidth((int) graphMakerThicknessSlider.getValue());
                demand.setThickness((int) graphMakerThicknessSlider.getValue());
                System.out.println(graphMakerThicknessSlider.getValue());
                break;
            }
            case 1: {
                Supply supply = (Supply) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex() -1);
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
        System.out.println("START DEMAND SIMPLE");
        setUpDemand();
        Demand demand = new Demand(graphMakerWorkspaceP, Context.getInstance().getCurveCount()); //200,50,550,400
        Context.getInstance().getCurvesLL().add(demand);
        System.out.println(Context.getInstance().getCurvesLL());
        System.out.println("Demand Count : " + Context.getInstance().getDemandCount());
        System.out.println("Curve Count : " + Context.getInstance().getCurveCount());
        System.out.println("END DEMAND SIMPLE");
    }

    public void insertDemand(String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        System.out.println("START DEMAND");
        setUpDemand();
        Demand demand = new Demand(graphMakerWorkspaceP, Context.getInstance().getCurveCount(), name, centreX, elasticityGap, colour, thickness, dotted); //200,50,550,400
        Context.getInstance().getCurvesLL().add(demand);
        System.out.println(Context.getInstance().getCurvesLL());
        System.out.println("Demand Count : " + Context.getInstance().getDemandCount());
        System.out.println("Curve Count : " + Context.getInstance().getCurveCount());
        System.out.println("END DEMAND");
    }

    public void setUpDemand() {
        //Context.getInstance().setStaticGraphMakerWorkspaceP(graphMakerWorkspaceP);
        Context.getInstance().setDemandCount(Context.getInstance().getDemandCount() + 1);
        Context.getInstance().setCurveCount(Context.getInstance().getCurveCount() + 1);
        createRadioButton("Demand " + Context.getInstance().getDemandCount(), Context.getInstance().getCurveCount(), 0);
    }

    public void insertSupply() {
        System.out.println("START SUPPLY SIMPLE");
        setUpSupply();
        Supply supply = new Supply(graphMakerWorkspaceP, Context.getInstance().getCurveCount()); //200,50,550,400
        Context.getInstance().getCurvesLL().add(supply);
        System.out.println(Context.getInstance().getCurvesLL());
        System.out.println("Supply Count : " + Context.getInstance().getSupplyCount());
        System.out.println("Curve Count : " + Context.getInstance().getCurveCount());
        System.out.println("END SUPPLY SIMPLE");
    }

    public void insertSupply(String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        System.out.println("START SUPPLY");
        setUpSupply();
        Supply supply = new Supply(graphMakerWorkspaceP, Context.getInstance().getCurveCount(), name, centreX, elasticityGap, colour, thickness, dotted); //200,50,550,400
        Context.getInstance().getCurvesLL().add(supply);
        System.out.println(Context.getInstance().getCurvesLL());
        System.out.println("Supply Count : " + Context.getInstance().getSupplyCount());
        System.out.println("Curve Count : " + Context.getInstance().getCurveCount());
        System.out.println("END SUPPLY");
    }

    public void setUpSupply() {
        Context.getInstance().setSupplyCount(Context.getInstance().getSupplyCount() + 1);
        Context.getInstance().setCurveCount(Context.getInstance().getCurveCount() + 1);
        createRadioButton("Supply " + Context.getInstance().getSupplyCount(), Context.getInstance().getCurveCount(), 1);
    }

    public void createRadioButton(String name, int index, int type) {
        RadioButton radioButton = new RadioButton(name);
        switch (type) {
            case 0: {
                radioButton.setId("demandRadio" + index);
                break;
            }
            case 1: {
                radioButton.setId("supplyRadio" + index);
                break;
            }
            case 2: {
                radioButton.setId("newClassicalRadio" + index);
                break;
            }
            case 3: {
                radioButton.setId("keynesianRadio" + index);
                break;
            }
        }
        radioButton.setToggleGroup(Context.getInstance().getGraphMakerInsertedCurvesTG());
        radioButton.setOnAction(e -> setAppropriateCurveSettings(index, type));
        graphMakerRadioButtonsFP.getChildren().add(radioButton);
        radioButton.setSelected(true);
        setAppropriateCurveSettings(index, type);

        System.out.println("Created radio button: " + radioButton);
    }

    public void setAppropriateCurveSettings(int index, int type) {
        Context.getInstance().setSelectedCurveIndex(index);
        Context.getInstance().setSelectedCurveType(type);
        if (Context.getInstance().getSelectedCurveIndex() - 1 == 0) {
            graphMakerElasticitySlider.setValue(175);
        }
        else {
            graphMakerElasticitySlider.setValue(Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex() - 2).getElasticityGap());
        }
        graphMakerShiftSlider.setValue(0);
        graphMakerThicknessSlider.setValue(5);
    }

    public void setScreenToSaveMenu() {
        main.setScreenToSaveMenu();
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

    public void init(MainController mainController) {
        main = mainController;
    }

    public Pane getGraphMakerWorkspaceP() {
        return graphMakerWorkspaceP;
    }

    public void setGraphMakerWorkspaceP(Pane graphMakerWorkspaceP) {
        this.graphMakerWorkspaceP = graphMakerWorkspaceP;
    }

    public FlowPane getGraphMakerRadioButtonsFP() {
        return graphMakerRadioButtonsFP;
    }

    public void setGraphMakerRadioButtonsFP(FlowPane graphMakerRadioButtonsFP) {
        this.graphMakerRadioButtonsFP = graphMakerRadioButtonsFP;
    }
}
