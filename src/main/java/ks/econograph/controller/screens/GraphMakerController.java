package ks.econograph.controller.screens;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
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
import ks.econograph.Context;
import ks.econograph.controller.MainController;
import ks.econograph.graph.components.Demand;
import ks.econograph.graph.components.StraightCurve;
import ks.econograph.graph.components.Supply;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.Infinity;
import static jdk.nashorn.internal.objects.Global.loadWithNewGlobal;

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
                calculateAndGenerateShiftArrows(); //TODO: move to bottom when compatible with supply
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
                calculateAndGenerateShiftArrows(); //TODO: move to bottom when compatible with supply
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
        Context.getInstance().getCurvesLL().add(demand);
        System.out.println(Context.getInstance().getCurvesLL());
        setUpDemand();
        System.out.println("Demand Count : " + Context.getInstance().getDemandCount());
        System.out.println("Curve Count : " + Context.getInstance().getCurveCount());
        System.out.println("END DEMAND SIMPLE");
    }

    public void insertDemand(String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        System.out.println("START DEMAND");
        Demand demand = new Demand(graphMakerWorkspaceP, Context.getInstance().getDemandCount(), name, centreX, elasticityGap, colour, thickness, dotted); //200,50,550,400
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
        calculateAndGenerateShiftArrows();
    }

    public void calculateAndGenerateShiftArrows() {
        //TODO: create specific curve linked lists in context so that they dont have to reloaded every time.
        for (int i = 0; i < Context.getInstance().getShiftArrowsLL().size(); i++) {
            Context.getInstance().getShiftArrowsLL().get(i).setVisible(false);
        }
        Context.getInstance().getShiftArrowsLL().clear();

        if (Context.getInstance().getDemandCount() > 0) {
            List<StraightCurve> demandCurves = new LinkedList<>();
            for (int i = 0; i < Context.getInstance().getCurvesLL().size(); i++) {
                switch (Context.getInstance().getCurvesLL().get(i).getCurveType()) {
                    case "Demand": {
                        demandCurves.add((Demand) Context.getInstance().getCurvesLL().get(i));
                    }
                }
            }
            for (int i = 1; i < demandCurves.size(); i++) {
                insertShiftArrows(demandCurves.get(i), demandCurves.get(i-1));
            }
        }
    }

    public void insertShiftArrows(StraightCurve line1, StraightCurve line2) {
        //Calculates x coordinates for each line at y=142,383
        double topX1 = (106 - line1.getyIntercept())/line1.getGradient();
        double topX2 = (106 - line2.getyIntercept())/line2.getGradient();
        double bottomX1 = (319 - line1.getyIntercept())/line1.getGradient();
        double bottomX2 = (319 - line2.getyIntercept())/line2.getGradient();

        //arrows start and finish 1/4 to 3/4 of the distance between the lines
        double topArrowStartX = ((topX1+topX2)/2)-Math.abs(topX1 - topX2)/4;
        double topArrowEndX = ((topX1+topX2)/2)+Math.abs(topX1 - topX2)/4;
        double bottomArrowStartX = (((bottomX1+bottomX2)/2)-Math.abs(bottomX1 - bottomX2)/4);
        double bottomArrowEndX = (((bottomX1+bottomX2)/2)+Math.abs(bottomX1 - bottomX2)/4);

        //generate lines at calculated values
        Line topArrow = new Line(topArrowStartX, 106, topArrowEndX, 106);
        Line bottomArrow = new Line(bottomArrowStartX, 319, bottomArrowEndX, 319);

        double topHeadX = 0;
        double bottomHeadX = 0;
        int topDirection = 0; //-1 is left +1 is right
        int bottomDirection = 0;

        if (line1.getName().compareTo(line2.getName()) < 0) { //A to Z :. D to Dinfinity
            //head points towards line2 since line1 is D and line2 is D1.
            if (topX1 > topX2) {
                //this means that line 1 is on the right of line2 :. it needs to point towards the left
                topHeadX = topArrowStartX;
                topDirection = -1;

            }
            else {
                //right
                topHeadX = topArrowEndX;
                topDirection = 1;
            }
            if (bottomX1 > bottomX2) {
                //left
                bottomHeadX = bottomArrowStartX;
                bottomDirection = -1;
            }
            else {
                //right
                bottomHeadX = bottomArrowEndX;
                bottomDirection = 1;
            }
        }
        else {
            //points towards line1
            if (topX1 > topX2) {
                //right
                topHeadX = topArrowEndX;
                topDirection = 1;
            }
            else {
                //left
                topHeadX = topArrowStartX;
                topDirection = -1;
            }
            if (bottomX1 > bottomX2) {
                //right
                bottomHeadX = bottomArrowEndX;
                bottomDirection = 1;
            }
            else {
                //left
                bottomHeadX = bottomArrowStartX;
                bottomDirection = -1;
            }
        }

        Polygon topHead = new Polygon();
        topHead.getPoints().addAll(new Double[]{
                topHeadX +1*topDirection, 106.0,
                topHeadX -6*topDirection, 102.0,
                topHeadX -6*topDirection, 110.0 });

        Polygon bottomHead = new Polygon();
        bottomHead.getPoints().addAll(new Double[]{
                bottomHeadX +1*bottomDirection, 319.0,
                bottomHeadX -6*bottomDirection, 315.0,
                bottomHeadX -6*bottomDirection, 323.0 });

        Context.getInstance().getShiftArrowsLL().add(topArrow);
        Context.getInstance().getShiftArrowsLL().add(bottomArrow);
        Context.getInstance().getShiftArrowsLL().add(topHead);
        Context.getInstance().getShiftArrowsLL().add(bottomHead);

        graphMakerWorkspaceP.getChildren().addAll(topArrow, bottomArrow, topHead, bottomHead);
    }

    public void insertSupply() {
        System.out.println("START SUPPLY SIMPLE");
        Supply supply = new Supply(graphMakerWorkspaceP, Context.getInstance().getSupplyCount()); //200,50,550,400
        Context.getInstance().getCurvesLL().add(supply);
        System.out.println(Context.getInstance().getCurvesLL());
        setUpSupply();
        System.out.println("Supply Count : " + Context.getInstance().getSupplyCount());
        System.out.println("Curve Count : " + Context.getInstance().getCurveCount());
        System.out.println("END SUPPLY SIMPLE");
    }

    public void insertSupply(String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        System.out.println("START SUPPLY");
        Supply supply = new Supply(graphMakerWorkspaceP, Context.getInstance().getSupplyCount(), name, centreX, elasticityGap, colour, thickness, dotted); //200,50,550,400
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
        //calculateAndGenerateShiftArrows(); -> not compatible yet
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
