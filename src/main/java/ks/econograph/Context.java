package ks.econograph;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import ks.econograph.graph.components.Curve;
import ks.econograph.graph.components.Graph;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by KSarm on 01/01/2017.
 */
public class Context {

      private final static Context context = new Context(); //singleton - only 1 context is ever created

    private Parent mainRoot;

    private List<Graph> graphsLL = new LinkedList<>();
    private List<Curve> curvesLL = new LinkedList<>();
    private Graph currentEditingGraph = new Graph();
    private ToggleGroup toggleGroup = new ToggleGroup();
    WritableImage tempScreenShot;

    private Pane staticGraphMakerWorkspaceP = new Pane();
    private FlowPane staticGraphMakerRadioButtonsFP = new FlowPane();

    int demandCount = 0;
    int supplyCount = 0;
    int newClassicalCount = 0;
    int keynesianCount = 0;
    int curveCount = demandCount + supplyCount + newClassicalCount + keynesianCount;

    int selectedCurveIndex = -1;
    int selectedCurveType = -1; // 0 = demand, 1 = supply, 2 = newClassical, 3 = keynesian

    public void initializeMainRoot() throws Exception {
        mainRoot = FXMLLoader.load(getClass().getClassLoader().getResource("Main.fxml"));
    }

    public Parent getMainRoot() {
        return mainRoot;
    }

    public void setMainRoot(Parent mainRoot) {
        this.mainRoot = mainRoot;
    }

    public static Context getInstance() {
        return context;
    }

    public List<Graph> getGraphsLL() {
        return graphsLL;
    }

    public void setGraphsLL(List<Graph> graphsLL) {
        this.graphsLL = graphsLL;
    }

    public List<Curve> getCurvesLL() {
        return curvesLL;
    }

    public void setCurvesLL(List<Curve> curvesLL) {
        Context.getInstance().curvesLL = curvesLL;
    }

    public Graph getCurrentEditingGraph() {
        return currentEditingGraph;
    }

    public void setCurrentEditingGraph(Graph currentEditingGraph) {
        this.currentEditingGraph = currentEditingGraph;
    }

    public ToggleGroup getToggleGroup() {
        return toggleGroup;
    }

    public void setToggleGroup(ToggleGroup toggleGroup) {
        this.toggleGroup = toggleGroup;
    }

    public WritableImage getTempScreenShot() {
        return tempScreenShot;
    }

    public void setTempScreenShot(WritableImage tempScreenShot) {
        this.tempScreenShot = tempScreenShot;
    }

    public Pane getStaticGraphMakerWorkspaceP() {
        return staticGraphMakerWorkspaceP;
    }

    public void setStaticGraphMakerWorkspaceP(Pane staticGraphMakerWorkspaceP) {
        Context.getInstance().staticGraphMakerWorkspaceP = staticGraphMakerWorkspaceP;
    }

    public FlowPane getStaticGraphMakerRadioButtonsFP() {
        return staticGraphMakerRadioButtonsFP;
    }

    public void setStaticGraphMakerRadioButtonsFP(FlowPane staticGraphMakerRadioButtonsFP) {
        Context.getInstance().staticGraphMakerRadioButtonsFP = staticGraphMakerRadioButtonsFP;
    }

    public int getDemandCount() {
        return demandCount;
    }

    public void setDemandCount(int demandCount) {
        this.demandCount = demandCount;
    }

    public int getSupplyCount() {
        return supplyCount;
    }

    public void setSupplyCount(int supplyCount) {
        this.supplyCount = supplyCount;
    }

    public int getNewClassicalCount() {
        return newClassicalCount;
    }

    public void setNewClassicalCount(int newClassicalCount) {
        this.newClassicalCount = newClassicalCount;
    }

    public int getKeynesianCount() {
        return keynesianCount;
    }

    public void setKeynesianCount(int keynesianCount) {
        this.keynesianCount = keynesianCount;
    }

    public int getCurveCount() {
        return curveCount;
    }

    public void setCurveCount(int curveCount) {
        this.curveCount = curveCount;
    }

    public int getSelectedCurveIndex() {
        return selectedCurveIndex;
    }

    public void setSelectedCurveIndex(int selectedCurveIndex) {
        this.selectedCurveIndex = selectedCurveIndex;
    }

    public int getSelectedCurveType() {
        return selectedCurveType;
    }

    public void setSelectedCurveType(int selectedCurveType) {
        this.selectedCurveType = selectedCurveType;
    }
}
