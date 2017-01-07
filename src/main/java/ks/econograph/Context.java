package ks.econograph;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
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

    //See use: http://stackoverflow.com/questions/12166786/multiple-fxml-with-controllers-share-object
    private final static Context context = new Context(); //singleton meaning only 1 context is ever created

    private static Parent library;
    private static Parent options;
    private static Parent graphMaker;
    private static Parent saveMenu;

    private List<Graph> graphsLL = new LinkedList<>();
    private List<Curve> curvesLL = new LinkedList<>();
    private Graph currentEditingGraph = new Graph();
    private ToggleGroup toggleGroup = new ToggleGroup();
    //WritableImage tempScreenShot = new WritableImage(library.snapshot(new SnapshotParameters(), null));
            //Context.getInstance().getStaticGraphMakerWorkspaceP().snapshot(new SnapshotParameters(), null); //contains negligible image at start


    private Pane staticGraphMakerWorkspaceP = new Pane();
    private FlowPane staticGraphMakerRadioButtonsFP = new FlowPane();

    int demandCount = 0;
    int supplyCount = 0;
    int newClassicalCount = 0;
    int keynesianCount = 0;
    int curveCount = demandCount + supplyCount + newClassicalCount + keynesianCount;

    int selectedCurveIndex = -1;
    int selectedCurveType = -1; // 0 = demand, 1 = supply, 2 = newClassical, 3 = keynesian


    boolean callInsertDemand = false;

    public void initialize() throws Exception {
        library = FXMLLoader.load(getClass().getClassLoader().getResource("Library.fxml"));
        options = FXMLLoader.load(getClass().getClassLoader().getResource("Options.fxml"));
        graphMaker = FXMLLoader.load(getClass().getClassLoader().getResource("GraphMaker.fxml"));
        saveMenu = FXMLLoader.load(getClass().getClassLoader().getResource("SaveMenu.fxml"));

        options.setVisible(false);
        graphMaker.setVisible(false);
        saveMenu.setVisible(false);
    }

    public static Context getInstance() {
        return context;
    }

    public static Parent getLibrary() {
        return library;
    }

    public static void setLibrary(Parent library) {
        Context.library = library;
    }

    public static Parent getOptions() {
        return options;
    }

    public static void setOptions(Parent options) {
        Context.options = options;
    }

    public static Parent getGraphMaker() {
        return graphMaker;
    }

    public static void setGraphMaker(Parent graphMaker) {
        Context.graphMaker = graphMaker;
    }

    public static Parent getSaveMenu() {
        return saveMenu;
    }

    public static void setSaveMenu(Parent saveMenu) {
        Context.saveMenu = saveMenu;
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

/*    public WritableImage getTempScreenShot() {
        return tempScreenShot;
    }

    public void setTempScreenShot(WritableImage tempScreenShot) {
        this.tempScreenShot = tempScreenShot;
    }*/

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
