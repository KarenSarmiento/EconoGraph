package ks.econograph;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.WritableImage;

import javafx.scene.shape.Line;
import ks.econograph.graph.components.Curve;
import ks.econograph.graph.components.Graph;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by KSarm on 01/01/2017.
 */
public class Context {

    private final static Context context = new Context(); //singleton - only 1 context is ever created

    private String fileLocationForSavedImages = "C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\FileWriting\\";
    private String filePathForSaveTextFile = "C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\FileWriting\\test.txt";
    private Parent mainRoot;

    private List<Graph> graphsLL = new LinkedList<>();
    private List<Curve> curvesLL = new LinkedList<>();
    private List<Node> intersectionLL = new LinkedList<>();

    private ToggleGroup graphMakerInsertedCurvesTG = new ToggleGroup();
    private ToggleGroup optionsTemplateTG = new ToggleGroup();
    private WritableImage tempScreenShot;

    private int demandCount = 0;
    private int supplyCount = 0;
    private int newClassicalCount = 0;
    private int keynesianCount = 0;
    private int curveCount = demandCount + supplyCount + newClassicalCount + keynesianCount;

    private Graph currentEditingGraph = new Graph();
    private int selectedCurveIndex = -1;
    private int selectedCurveType = -1; // 0 = demand, 1 = supply, 2 = newClassical, 3 = keynesian

    private String filterSearch = null;
    private boolean filterFavourite = false;
    private String filterTopic = null;

    public void initializeMainRoot() throws Exception {
        mainRoot = FXMLLoader.load(getClass().getClassLoader().getResource("Main.fxml"));
    }

    public String getFileLocationForSavedImages() {
        return fileLocationForSavedImages;
    }

    public void setFileLocationForSavedImages(String fileLocationForSavedImages) {
        this.fileLocationForSavedImages = fileLocationForSavedImages;
    }

    public String getFilePathForSavedGraphs() {
        return filePathForSaveTextFile;
    }

    public void setFilePathForSavedGraphs(String filePathForSavedGraphs) {
        this.filePathForSaveTextFile = filePathForSavedGraphs;
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

    public List<Node> getIntersectionLL() {
        return intersectionLL;
    }

    public void setIntersectionLL(List<Node> intersectionLL) {
        this.intersectionLL = intersectionLL;
    }

    public Graph getCurrentEditingGraph() {
        return currentEditingGraph;
    }

    public void setCurrentEditingGraph(Graph currentEditingGraph) {
        this.currentEditingGraph = currentEditingGraph;
    }

    public ToggleGroup getGraphMakerInsertedCurvesTG() {
        return graphMakerInsertedCurvesTG;
    }

    public ToggleGroup getOptionsTemplateTG() {
        return optionsTemplateTG;
    }

    public void setGraphMakerInsertedCurvesTG(ToggleGroup graphMakerInsertedCurvesTG) {
        this.graphMakerInsertedCurvesTG = graphMakerInsertedCurvesTG;
    }

    public WritableImage getTempScreenShot() {
        return tempScreenShot;
    }

    public void setTempScreenShot(WritableImage tempScreenShot) {
        this.tempScreenShot = tempScreenShot;
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

    public String getFilePathForSaveTextFile() {
        return filePathForSaveTextFile;
    }

    public void setFilePathForSaveTextFile(String filePathForSaveTextFile) {
        this.filePathForSaveTextFile = filePathForSaveTextFile;
    }

    public String getFilterSearch() {
        return filterSearch;
    }

    public void setFilterSearch(String filterSearch) {
        this.filterSearch = filterSearch;
    }

    public boolean isFilterFavourite() {
        return filterFavourite;
    }

    public void setFilterFavourite(boolean filterFavourite) {
        this.filterFavourite = filterFavourite;
    }

    public String getFilterTopic() {
        return filterTopic;
    }

    public void setFilterTopic(String filterTopic) {
        this.filterTopic = filterTopic;
    }
}
