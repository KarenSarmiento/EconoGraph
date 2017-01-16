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

    public void insertIntersections() {
        for (int i = 0; i < Context.getInstance().getIntersectionLL().size(); i++) {
            Context.getInstance().getIntersectionLL().get(i).setVisible(false);
        }
        Context.getInstance().getIntersectionLL().clear();

        for (int i = 0; i < Context.getInstance().getCurvesLL().size() -1; i++) {
            for (int j = Context.getInstance().getCurvesLL().size() -1; j > i; j--) {
                System.out.println(i + " checks " + j);
                Line newLine = new Line();
                switch(Context.getInstance().getCurvesLL().get(i).getCurveType()) {
                    case "Demand": {
                        newLine = ((Demand) Context.getInstance().getCurvesLL().get(i)).getLine();
                        break;
                    }
                    case "Supply": {
                        newLine = ((Supply) Context.getInstance().getCurvesLL().get(i)).getLine();
                    }
                }
                double newGradient = (newLine.getStartY() - newLine.getEndY())/(newLine.getStartX() - newLine.getEndX());
                double newYIntercept = newLine.getStartY() - newGradient * newLine.getStartX();
                System.out.println("newGradient : " + newGradient);
                System.out.println("newYIntercept : " + newYIntercept);

                Line comparedLine = new Line();
                switch (Context.getInstance().getCurvesLL().get(j).getCurveType()) {
                    case "Demand": {
                        comparedLine = ((Demand) Context.getInstance().getCurvesLL().get(j)).getLine();
                        break;
                    }
                    case "Supply": {
                        comparedLine = ((Supply) Context.getInstance().getCurvesLL().get(j)).getLine();
                        break;
                    }
                }

                double comparedGradient = (comparedLine.getStartY() - comparedLine.getEndY()) / (comparedLine.getStartX() - comparedLine.getEndX());
                double comparedYIntercept = comparedLine.getStartY() - comparedGradient * comparedLine.getStartX();

                System.out.println("comparedGradient : " + comparedGradient);
                System.out.println("comparedYIntercept : " + comparedYIntercept);

                if (newGradient != comparedGradient) {
                    //Intersection at
                    double x = (comparedYIntercept - newYIntercept) / (newGradient - comparedGradient);
                    double y = newGradient * x + newYIntercept;
                    System.out.println("x" + x + "/ny" + y);
                    Line line = new Line(x, y, x, 425);
                    Line line2 = new Line(x, y, 87, y);
                    line.getStrokeDashArray().addAll(10d, 5d);
                    line2.getStrokeDashArray().addAll(10d, 5d);
                    Context.getInstance().getIntersectionLL().add(line);
                    Context.getInstance().getIntersectionLL().add(line2);
                    main.getGraphMakerController().getGraphMakerWorkspaceP().getChildren().addAll(line, line2);
                }
            }

        }
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
                Demand demand = (Demand) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex());
                demand.setCentreX(375 + (int) graphMakerShiftSlider.getValue());
                demand.getLine().setStartX(demand.getCentreX() - demand.getElasticityGap());
                demand.getLine().setEndX(demand.getCentreX() + demand.getElasticityGap());
                break;
            }
            case 1: {
                Supply supply = (Supply) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex());
                supply.setCentreX(375 + (int) graphMakerShiftSlider.getValue());
                supply.getLine().setStartX(supply.getCentreX() - supply.getElasticityGap());
                supply.getLine().setEndX(supply.getCentreX() + supply.getElasticityGap());
                break;
            }
        }
        insertIntersections();
    }

    public void updateElasticityForCurrentCurve() {
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
        insertIntersections();
    }

    public void updateCurveColour() {
        switch (Context.getInstance().getSelectedCurveType()) {
            case 0: {
                Demand demand = (Demand) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex());
                demand.getLine().setStroke(graphMakerColourPicker.getValue());
                demand.setColour(graphMakerColourPicker.getValue().toString());
                break;
            }
            case 1: {
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
        switch (Context.getInstance().getSelectedCurveType()) {
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
        System.out.println("START DEMAND SIMPLE");
        Demand demand = new Demand(graphMakerWorkspaceP, Context.getInstance().getCurveCount()); //200,50,550,400
        Context.getInstance().getCurvesLL().add(demand);
        System.out.println(Context.getInstance().getCurvesLL());
        setUpDemand();
        System.out.println("Demand Count : " + Context.getInstance().getDemandCount());
        System.out.println("Curve Count : " + Context.getInstance().getCurveCount());
        System.out.println("END DEMAND SIMPLE");
    }

    public void insertDemand(String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        System.out.println("START DEMAND");
        Demand demand = new Demand(graphMakerWorkspaceP, Context.getInstance().getCurveCount(), name, centreX, elasticityGap, colour, thickness, dotted); //200,50,550,400
        Context.getInstance().getCurvesLL().add(demand);
        setUpDemand();
        System.out.println(Context.getInstance().getCurvesLL());
        System.out.println("Demand Count : " + Context.getInstance().getDemandCount());
        System.out.println("Curve Count : " + Context.getInstance().getCurveCount());
        System.out.println("END DEMAND");
    }

    private void setUpDemand() {
        createRadioButton("Demand " + Context.getInstance().getDemandCount(), Context.getInstance().getCurveCount(), 0);
        Context.getInstance().setDemandCount(Context.getInstance().getDemandCount() + 1);
        Context.getInstance().setCurveCount(Context.getInstance().getCurveCount() + 1);
        insertIntersections();
    }

    public void insertSupply() {
        System.out.println("START SUPPLY SIMPLE");
        Supply supply = new Supply(graphMakerWorkspaceP, Context.getInstance().getCurveCount()); //200,50,550,400
        Context.getInstance().getCurvesLL().add(supply);
        System.out.println(Context.getInstance().getCurvesLL());
        setUpSupply();
        System.out.println("Supply Count : " + Context.getInstance().getSupplyCount());
        System.out.println("Curve Count : " + Context.getInstance().getCurveCount());
        System.out.println("END SUPPLY SIMPLE");
    }

    public void insertSupply(String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        System.out.println("START SUPPLY");
        Supply supply = new Supply(graphMakerWorkspaceP, Context.getInstance().getCurveCount(), name, centreX, elasticityGap, colour, thickness, dotted); //200,50,550,400
        Context.getInstance().getCurvesLL().add(supply);
        System.out.println(Context.getInstance().getCurvesLL());
        setUpSupply();
        System.out.println("Supply Count : " + Context.getInstance().getSupplyCount());
        System.out.println("Curve Count : " + Context.getInstance().getCurveCount());
        System.out.println("END SUPPLY");
    }

    private void setUpSupply() {
        createRadioButton("Supply " + Context.getInstance().getSupplyCount(), Context.getInstance().getCurveCount(), 1);
        Context.getInstance().setSupplyCount(Context.getInstance().getSupplyCount() + 1);
        Context.getInstance().setCurveCount(Context.getInstance().getCurveCount() + 1);
        insertIntersections();
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
//        if (Context.getInstance().getSelectedCurveIndex() == 0 || Context.getInstance().getSelectedCurveIndex() == 1) {
            graphMakerElasticitySlider.setValue(175);
//        }
//        else {
//            graphMakerElasticitySlider.setValue(Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex()).getElasticityGap());
//        }
        graphMakerShiftSlider.setValue(0);
        graphMakerThicknessSlider.setValue(5);
    }

    public void setScreenToSaveMenu() {
        main.setScreenToSaveMenu();
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

    public void init(MainController mainController) {
        main = mainController;
    }
}
