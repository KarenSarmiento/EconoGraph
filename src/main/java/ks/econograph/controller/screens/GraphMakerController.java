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
import ks.econograph.graph.components.*;

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
    FlowPane graphMakerCurveRadioFP = new FlowPane();
    @FXML
    FlowPane graphMakerShadedRegionRadioFP = new FlowPane();
    @FXML
    Label graphMakerSelectAShadedRegionL = new Label("Select a Shaded Region: ");
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

    //TODO: ENSURE THAT INTERSECTION LABELS GENERATED ARE ASSOCIATED WITH LAST INSERTED CURVE!

    public void resetAndUpdateShadedRegions() {
        for (int i = 0; i < Context.getInstance().getShadedRegionsLL().size(); i++) {
            Context.getInstance().getShadedRegionsLL().get(i).setVisible(false);
        }
        Context.getInstance().getShadedRegionsLL().clear();
        graphMakerShadedRegionRadioFP.getChildren().clear();
        graphMakerShadedRegionRadioFP.getChildren().add(graphMakerSelectAShadedRegionL);

        for (int i = 0; i < Context.getInstance().getShadedRegionFieldsLL().size(); i++) {
            String[] field = Context.getInstance().getShadedRegionFieldsLL().get(i).split(",");
            ShadedRegion shadedRegion = new ShadedRegion(field[0], field[1], field[2], field[3], field[4], field[5], graphMakerWorkspaceP, graphMakerShadedRegionRadioFP);
            Context.getInstance().getShadedRegionsLL().add(shadedRegion);
        }
    }

    public void initializeShadedRegionTest() {
        main.initializeShadedRegionScreen();
    }

    public void insertIntersections() {
        resetIntersections();

        for (int i = 0; i < Context.getInstance().getCurvesLL().size() -1; i++) {
            for (int j = Context.getInstance().getCurvesLL().size() -1; j > i; j--) {
                System.out.println(Context.getInstance().getIntersectionLL());
                Intersection intersection = new Intersection((StraightCurve) Context.getInstance().getCurvesLL().get(i),
                        (StraightCurve) Context.getInstance().getCurvesLL().get(j), graphMakerWorkspaceP);
                if (!intersection.isNull())
                    Context.getInstance().getIntersectionLL().add(intersection);

            }
        }
    }

    private void resetIntersections() {
        for (int i = 0; i < Context.getInstance().getIntersectionLL().size(); i++) {
            Context.getInstance().getIntersectionLL().get(i).setVisible(false);
        }
        Context.getInstance().getIntersectionLL().clear();
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
        StraightCurve straightCurve = (StraightCurve) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex());
        straightCurve.setCentreX(375 + (int) graphMakerShiftSlider.getValue());
        straightCurve.getLine().setStartX(straightCurve.getCentreX() - straightCurve.getElasticityGap());
        straightCurve.getLine().setEndX(straightCurve.getCentreX() + straightCurve.getElasticityGap());
        straightCurve.getLabel().setTranslateX(straightCurve.getCentreX() + straightCurve.getElasticityGap() + 15);
        straightCurve.calculateAndSetGradientAndYIntercept();
        updateIntersectionsShiftArrowsAndShadedRegions();
    }

    public void updateElasticityForCurrentCurve() {
        StraightCurve straightCurve = (StraightCurve) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex());
        updateIntersectionsShiftArrowsAndShadedRegions();
        straightCurve.setElasticityGap((int) graphMakerElasticitySlider.getValue());
        straightCurve.getLine().setStartX(straightCurve.getCentreX() - straightCurve.getElasticityGap());
        straightCurve.getLine().setEndX(straightCurve.getCentreX() + straightCurve.getElasticityGap());
        straightCurve.getLabel().setTranslateX(straightCurve.getCentreX() + straightCurve.getElasticityGap() + 15);
        straightCurve.calculateAndSetGradientAndYIntercept();
    }

    public void updateCurveColour() {
        StraightCurve straightCurve = (StraightCurve) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex());
        straightCurve.getLine().setStroke(graphMakerColourPicker.getValue());
        straightCurve.setColour(graphMakerColourPicker.getValue().toString());
    }

    public void updateThickness() {
        StraightCurve straightCurve = (StraightCurve) Context.getInstance().getCurvesLL().get(Context.getInstance().getSelectedCurveIndex());
        straightCurve.getLine().setStrokeWidth((int) graphMakerThicknessSlider.getValue());
        straightCurve.setThickness((int) graphMakerThicknessSlider.getValue());
        System.out.println(graphMakerThicknessSlider.getValue());
    }

    public void insertDemand() {
        Demand demand = new Demand(graphMakerWorkspaceP, Context.getInstance().getDemandCount()); //200,50,550,400
        setUpDemand(demand);
    }

    public void insertDemand(String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        Demand demand = new Demand(graphMakerWorkspaceP, Context.getInstance().getDemandCount(), name, centreX, elasticityGap, colour, thickness, dotted); //200,50,550,400
        setUpDemand(demand);
    }

    private void setUpDemand(Demand demand) {
        createRadioButton("Demand " + Context.getInstance().getDemandCount(), Context.getInstance().getCurveCount(), 0);
        Context.getInstance().setDemandCount(Context.getInstance().getDemandCount() +1);
        Context.getInstance().getDemandCurves().add(demand);
        setUpCurve(demand);
    }

    public void insertAggregateDemand() {
        AggregateDemand aggregateDemand = new AggregateDemand(graphMakerWorkspaceP, Context.getInstance().getAggregateDemandCount());
        setUpAggregateDemand(aggregateDemand);
    }

    public void insertAggregateDemand(String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        AggregateDemand aggregateDemand = new AggregateDemand(graphMakerWorkspaceP, Context.getInstance().getAggregateDemandCount(), name, centreX, elasticityGap, colour, thickness, dotted);
        setUpAggregateDemand(aggregateDemand);
    }

    private void setUpAggregateDemand(AggregateDemand aggregateDemand) {
        createRadioButton("Aggregate Demand " + Context.getInstance().getAggregateDemandCount(), Context.getInstance().getCurveCount(), 3);
        Context.getInstance().setAggregateDemandCount(Context.getInstance().getAggregateDemandCount() +1);
        Context.getInstance().getAggregateDemandCurves().add(aggregateDemand);
        setUpCurve(aggregateDemand);
    }

    public void insertSupply() {
        Supply supply = new Supply(graphMakerWorkspaceP, Context.getInstance().getSupplyCount()); //200,50,550,400
        setUpSupply(supply);
    }

    public void insertSupply(String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        Supply supply = new Supply(graphMakerWorkspaceP, Context.getInstance().getSupplyCount(), name, centreX, elasticityGap, colour, thickness, dotted); //200,50,550,400
        setUpSupply(supply);
    }

    private void setUpSupply(Supply supply) {
        createRadioButton("Supply " + Context.getInstance().getSupplyCount(), Context.getInstance().getCurveCount(), 1);
        Context.getInstance().setSupplyCount(Context.getInstance().getSupplyCount() + 1);
        Context.getInstance().getSupplyCurves().add(supply);
        setUpCurve(supply);
    }

    public void insertAggregateSupply() {
        AggregateSupply aggregateSupply = new AggregateSupply(graphMakerWorkspaceP, Context.getInstance().getAggregateSupplyCount());
        setUpAggregateSupply(aggregateSupply);
    }

    public void insertAggregateSupply(String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        AggregateSupply aggregateSupply = new AggregateSupply(graphMakerWorkspaceP, Context.getInstance().getAggregateSupplyCount(), name, centreX, elasticityGap, colour, thickness, dotted);
        setUpAggregateSupply(aggregateSupply);
    }

    private void setUpAggregateSupply(AggregateSupply aggregateSupply) {
        createRadioButton("Aggregate Supply " + Context.getInstance().getAggregateSupplyCount(), Context.getInstance().getCurveCount(), 4);
        Context.getInstance().setAggregateSupplyCount(Context.getInstance().getAggregateSupplyCount() +1);
        Context.getInstance().getAggregateSupplyCurves().add(aggregateSupply);
        setUpCurve(aggregateSupply);
    }

    private void setUpCurve(Curve curve) {
        Context.getInstance().setCurveCount(Context.getInstance().getCurveCount() + 1);
        Context.getInstance().getCurvesLL().add(curve);
        insertIntersections();
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

        for (int i = 1; i < Context.getInstance().getSupplyCurves().size(); i++) {
            insertTopAndBottomShiftArrowsForStraightCurves(Context.getInstance().getSupplyCurves().get(i), Context.getInstance().getSupplyCurves().get(i-1));
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
        graphMakerCurveRadioFP.getChildren().add(radioButton);
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

    private void updateIntersectionsShiftArrowsAndShadedRegions() {
        calculateAndGenerateShiftArrows();
        insertIntersections();
        resetAndUpdateShadedRegions();
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

    public FlowPane getGraphMakerCurveRadioFP() {
        return graphMakerCurveRadioFP;
    }

    public void setGraphMakerCurveRadioFP(FlowPane graphMakerCurveRadioFP) {
        this.graphMakerCurveRadioFP = graphMakerCurveRadioFP;
    }

    public Slider getGraphMakerShiftSlider() {
        return graphMakerShiftSlider;
    }

    public void setGraphMakerShiftSlider(Slider graphMakerShiftSlider) {
        this.graphMakerShiftSlider = graphMakerShiftSlider;
    }

    public Slider getGraphMakerElasticitySlider() {
        return graphMakerElasticitySlider;
    }

    public void setGraphMakerElasticitySlider(Slider graphMakerElasticitySlider) {
        this.graphMakerElasticitySlider = graphMakerElasticitySlider;
    }

    public FlowPane getGraphMakerShadedRegionRadioFP() {
        return graphMakerShadedRegionRadioFP;
    }

    public void setGraphMakerShadedRegionRadioFP(FlowPane graphMakerShadedRegionRadioFP) {
        this.graphMakerShadedRegionRadioFP = graphMakerShadedRegionRadioFP;
    }

    public Label getGraphMakerSelectAShadedRegionL() {
        return graphMakerSelectAShadedRegionL;
    }

    public void setGraphMakerSelectAShadedRegionL(Label graphMakerSelectAShadedRegionL) {
        this.graphMakerSelectAShadedRegionL = graphMakerSelectAShadedRegionL;
    }

    public ColorPicker getGraphMakerColourPicker() {
        return graphMakerColourPicker;
    }

    public void setGraphMakerColourPicker(ColorPicker graphMakerColourPicker) {
        this.graphMakerColourPicker = graphMakerColourPicker;
    }

    public Slider getGraphMakerThicknessSlider() {
        return graphMakerThicknessSlider;
    }

    public void setGraphMakerThicknessSlider(Slider graphMakerThicknessSlider) {
        this.graphMakerThicknessSlider = graphMakerThicknessSlider;
    }

    public Label getGraphMakerXAxisL() {
        return graphMakerXAxisL;
    }

    public void setGraphMakerXAxisL(Label graphMakerXAxisL) {
        this.graphMakerXAxisL = graphMakerXAxisL;
    }

    public Label getGraphMakerYAxisL() {
        return graphMakerYAxisL;
    }

    public void setGraphMakerYAxisL(Label graphMakerYAxisL) {
        this.graphMakerYAxisL = graphMakerYAxisL;
    }

    public void init(MainController mainController) {
        main = mainController;
    }
}
