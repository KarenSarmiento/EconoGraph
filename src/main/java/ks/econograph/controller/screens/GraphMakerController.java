package ks.econograph.controller.screens;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import ks.econograph.Context;
import ks.econograph.controller.MainController;
import ks.econograph.graph.components.Demand;
import ks.econograph.graph.components.StraightCurve;
import ks.econograph.graph.components.Supply;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static jdk.nashorn.internal.objects.Global.Infinity;

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
    @FXML
    Label graphMakerXAxisL = new Label();
    @FXML
    Label graphMakerYAxisL = new Label();

    public void initializeShadedRegionTest() {
        main.getShadedRegionOptionsController().initializeShadedRegionScreen();
    }

    public void insertShadedRegion() {

    }

    private void getShadedRegionOptionsInput() {

    }

    public void insertIntersections() {
        resetIntersectionLLAndIntersectionCounts();

        for (int i = 0; i < Context.getInstance().getCurvesLL().size() -1; i++) {
            for (int j = Context.getInstance().getCurvesLL().size() -1; j > i; j--) {
                double newGradient = ((StraightCurve)Context.getInstance().getCurvesLL().get(i)).getGradient();
                double newYIntercept = ((StraightCurve)Context.getInstance().getCurvesLL().get(i)).getyIntercept();
                double comparedGradient = ((StraightCurve)Context.getInstance().getCurvesLL().get(j)).getGradient();
                double comparedYIntercept = ((StraightCurve)Context.getInstance().getCurvesLL().get(j)).getyIntercept();
                double x = 0;
                double y = 0;
                if (newGradient != comparedGradient && newGradient != Infinity && comparedGradient != Infinity) {
                    x = (comparedYIntercept - newYIntercept) / (newGradient - comparedGradient);
                    y = newGradient * x + newYIntercept;
                }
                else if (Math.abs(newGradient) == Infinity) {
                    x = Context.getInstance().getCurvesLL().get(i).getCentreX();
                    y = comparedGradient * x + comparedYIntercept;
                }
                else if (Math.abs(comparedGradient) == Infinity){
                    x = Context.getInstance().getCurvesLL().get(j).getCentreX();
                    y = newGradient * x + newYIntercept;
                }
                if (x >= 87 && y <= 425 ) {
                    System.out.println("x = " + x + ", y = " + y);
                    generateIntersectionLines(x, y);
                    generateIntersectionLabels(x, y);
                }
            }
        }
    }

    private void resetIntersectionLLAndIntersectionCounts() {
        for (int i = 0; i < Context.getInstance().getIntersectionLL().size(); i++) {
            Context.getInstance().getIntersectionLL().get(i).setVisible(false);
        }
        Context.getInstance().getIntersectionLL().clear();
        Context.getInstance().setxIntersectionCount(0);
        Context.getInstance().setyIntersectionCount(0);
    }

    private void generateIntersectionLines(double x, double y) {
        Line intersectionLine1 = new Line(x, y, x, 425);
        Line intersectionLine2 = new Line(x, y, 87, y);
        intersectionLine1.getStrokeDashArray().addAll(10d, 5d);
        intersectionLine2.getStrokeDashArray().addAll(10d, 5d);
        Context.getInstance().getIntersectionLL().add(intersectionLine1);
        Context.getInstance().getIntersectionLL().add(intersectionLine2);
        main.getGraphMakerController().getGraphMakerWorkspaceP().getChildren().addAll(intersectionLine1, intersectionLine2);
    }

    private void generateIntersectionLabels(double x, double y) {
        Label intersectionLabel1;
        if (Context.getInstance().getxIntersectionCount() == 0)
            intersectionLabel1 = new Label("Q");
        else
            intersectionLabel1 = new Label("Q" + Context.getInstance().getxIntersectionCount());
        Label intersectionLabel2;
        if (Context.getInstance().getyIntersectionCount() == 0)
            intersectionLabel2 = new Label("P");
        else
            intersectionLabel2 = new Label("P" + Context.getInstance().getyIntersectionCount());
        intersectionLabel1.setTranslateX(x);
        intersectionLabel1.setTranslateY(435);
        intersectionLabel2.setTranslateX(60);
        intersectionLabel2.setTranslateY(y);
        Context.getInstance().setxIntersectionCount(Context.getInstance().getxIntersectionCount()+1);
        Context.getInstance().setyIntersectionCount(Context.getInstance().getyIntersectionCount()+1);
        Context.getInstance().getIntersectionLL().add(intersectionLabel1);
        Context.getInstance().getIntersectionLL().add(intersectionLabel2);
        graphMakerWorkspaceP.getChildren().addAll(intersectionLabel1, intersectionLabel2);
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
                demand.getLabel().setTranslateX(demand.getCentreX() + demand.getElasticityGap() + 15);
                demand.calculateAndSetGradientAndYIntercept();
                break;
            }
            case 1: {
                Supply supply = (Supply) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex());
                supply.setCentreX(375 + (int) graphMakerShiftSlider.getValue());
                supply.getLine().setStartX(supply.getCentreX() - supply.getElasticityGap());
                supply.getLine().setEndX(supply.getCentreX() + supply.getElasticityGap());
                supply.getLabel().setTranslateX(supply.getCentreX() + supply.getElasticityGap() + 15);
                supply.calculateAndSetGradientAndYIntercept();
                break;
            }
        }
        calculateAndGenerateShiftArrows();
        insertIntersections();
    }

    public void updateElasticityForCurrentCurve() {
        switch (Context.getInstance().getSelectedCurveType()) {
            case 0: {
                Demand demand = (Demand) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex());
                demand.setElasticityGap((int) graphMakerElasticitySlider.getValue());
                demand.getLine().setStartX(demand.getCentreX() - demand.getElasticityGap());
                demand.getLine().setEndX(demand.getCentreX() + demand.getElasticityGap());
                demand.getLabel().setTranslateX(demand.getCentreX() + demand.getElasticityGap() + 15);
                demand.calculateAndSetGradientAndYIntercept();
                break;
            }
            case 1: {
                Supply supply = (Supply) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex());
                supply.setElasticityGap((int) graphMakerElasticitySlider.getValue());
                supply.getLine().setStartX(supply.getCentreX() - supply.getElasticityGap());
                supply.getLine().setEndX(supply.getCentreX() + supply.getElasticityGap());
                supply.getLabel().setTranslateX(supply.getCentreX() + supply.getElasticityGap() + 15);
                supply.calculateAndSetGradientAndYIntercept();
                break;
            }
            case 2: {

                break;
            }
            case 3: {

                break;
            }
        }
        calculateAndGenerateShiftArrows();
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
        Demand demand = new Demand(graphMakerWorkspaceP, Context.getInstance().getDemandCount()); //200,50,550,400
        System.out.println(Context.getInstance().getCurvesLL());
        setUpDemand(demand);
        System.out.println("Demand Count : " + Context.getInstance().getDemandCount());
        System.out.println("Curve Count : " + Context.getInstance().getCurveCount());
        System.out.println("END DEMAND SIMPLE");
    }

    public void insertDemand(String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        System.out.println("START DEMAND");
        Demand demand = new Demand(graphMakerWorkspaceP, Context.getInstance().getDemandCount(), name, centreX, elasticityGap, colour, thickness, dotted); //200,50,550,400
        setUpDemand(demand);
        System.out.println(Context.getInstance().getCurvesLL());
        System.out.println("Demand Count : " + Context.getInstance().getDemandCount());
        System.out.println("Curve Count : " + Context.getInstance().getCurveCount());
        System.out.println("END DEMAND");
    }

    private void setUpDemand(Demand demand) {
        createRadioButton("Demand " + Context.getInstance().getDemandCount(), Context.getInstance().getCurveCount(), 0);
        Context.getInstance().setDemandCount(Context.getInstance().getDemandCount() + 1);
        Context.getInstance().setCurveCount(Context.getInstance().getCurveCount() + 1);
        insertIntersections();
        calculateAndGenerateShiftArrows();
        Context.getInstance().getCurvesLL().add(demand);
        Context.getInstance().getDemandCurves().add(demand);
    }

    public void insertSupply() {
        System.out.println("START SUPPLY SIMPLE");
        Supply supply = new Supply(graphMakerWorkspaceP, Context.getInstance().getSupplyCount()); //200,50,550,400
        System.out.println(Context.getInstance().getCurvesLL());
        setUpSupply(supply);
        System.out.println("Supply Count : " + Context.getInstance().getSupplyCount());
        System.out.println("Curve Count : " + Context.getInstance().getCurveCount());
        System.out.println("END SUPPLY SIMPLE");
    }

    public void insertSupply(String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        System.out.println("START SUPPLY");
        Supply supply = new Supply(graphMakerWorkspaceP, Context.getInstance().getSupplyCount(), name, centreX, elasticityGap, colour, thickness, dotted); //200,50,550,400
        System.out.println(Context.getInstance().getCurvesLL());
        setUpSupply(supply);
        System.out.println("Supply Count : " + Context.getInstance().getSupplyCount());
        System.out.println("Curve Count : " + Context.getInstance().getCurveCount());
        System.out.println("END SUPPLY");
    }

    private void setUpSupply(Supply supply) {
        createRadioButton("Supply " + Context.getInstance().getSupplyCount(), Context.getInstance().getCurveCount(), 1);
        Context.getInstance().setSupplyCount(Context.getInstance().getSupplyCount() + 1);
        Context.getInstance().setCurveCount(Context.getInstance().getCurveCount() + 1);
        insertIntersections();
        Context.getInstance().getSupplyCurve().add(supply);
        Context.getInstance().getCurvesLL().add(supply);
        calculateAndGenerateShiftArrows();
    }

    public void calculateAndGenerateShiftArrows() {
        for (int i = 0; i < Context.getInstance().getShiftArrowsLL().size(); i++) {
            Context.getInstance().getShiftArrowsLL().get(i).setVisible(false);
        }
        Context.getInstance().getShiftArrowsLL().clear();

        for (int i = 1; i < Context.getInstance().getDemandCurves().size(); i++) {
            insertTopAndBottomShiftArrowsForStraightCurves(Context.getInstance().getDemandCurves().get(i), Context.getInstance().getDemandCurves().get(i-1));
        }

        for (int i = 1; i < Context.getInstance().getSupplyCurve().size(); i++) {
            insertTopAndBottomShiftArrowsForStraightCurves(Context.getInstance().getSupplyCurve().get(i), Context.getInstance().getSupplyCurve().get(i-1));
        }
    }

    private void insertTopAndBottomShiftArrowsForStraightCurves(StraightCurve line1, StraightCurve line2) {
        insertShiftArrow(line1, line2, 108);
        insertShiftArrow(line1, line2, 319);
    }

    private void insertShiftArrow(StraightCurve line1, StraightCurve line2 , double yCoordinate) {
        double x1 = (yCoordinate - line1.getyIntercept())/line1.getGradient();
        double x2 = (yCoordinate - line2.getyIntercept())/line2.getGradient();

        double arrowStartX = ((x1+x2)/2)-Math.abs(x1 - x2)/4;
        double arrowEndX = ((x1+x2)/2)+Math.abs(x1 - x2)/4;

        Line topArrow = new Line(arrowStartX, yCoordinate, arrowEndX, yCoordinate);

        double headX = 0;
        int direction = 0;

        if (line1.getName().compareTo(line2.getName()) < 0) {
            if (x1 > x2) {
                headX = arrowStartX;
                direction = -1;
            }
            else {
                headX = arrowEndX;
                direction = 1;
            }
        }
        else {
            if (x1 > x2) {
                headX = arrowEndX;
                direction = 1;
            }
            else {
                headX = arrowStartX;
                direction = -1;
            }
        }

        Polygon topHead = new Polygon();
        topHead.getPoints().addAll(new Double[]{
                headX +1*direction, yCoordinate,
                headX -6*direction, yCoordinate -4,
                headX -6*direction, yCoordinate +4 });

        Context.getInstance().getShiftArrowsLL().add(topArrow);
        Context.getInstance().getShiftArrowsLL().add(topHead);

        graphMakerWorkspaceP.getChildren().addAll(topArrow, topHead);
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
        //TODO : set appropriate settings
        graphMakerElasticitySlider.setValue(175);
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
