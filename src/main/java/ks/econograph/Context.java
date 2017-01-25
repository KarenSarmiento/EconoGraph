package ks.econograph;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.WritableImage;

import ks.econograph.graph.components.*;

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
    private List<Intersection> intersectionLL = new LinkedList<>();
    private List<Node> shiftArrowsLL = new LinkedList<>();
    private List<ShadedRegion> shadedRegionsLL = new LinkedList<>();
    private List<String> shadedRegionFieldsLL = new LinkedList<>();

    private ToggleGroup graphMakerInsertedCurvesTG = new ToggleGroup();
    private ToggleGroup graphMakerInsertedShadedRegionsTG = new ToggleGroup();
    private ToggleGroup optionsTemplateTG = new ToggleGroup();
    private WritableImage tempScreenShot;

    private int demandCount = 0;
    private int supplyCount = 0;
    private int aggregateDemandCount = 0;
    private int aggregateSupplyCount = 0;
    private int newClassicalCount = 0;
    private int curveCount = demandCount + supplyCount + newClassicalCount + aggregateDemandCount + aggregateSupplyCount;

    private List<Demand> demandCurves = new LinkedList<>();
    private List<Supply> supplyCurves = new LinkedList<>();
    private List<AggregateSupply> aggregateSupplyCurves = new LinkedList<>();
    private List<AggregateDemand> aggregateDemandCurves = new LinkedList<>();
    private List<NewClassical> newClassicalCurves = new LinkedList<>();

    private Graph currentEditingGraph = new Graph();
    private int selectedCurveIndex = -1;
    private int selectedShadedRegionIndex = -1;
    private int selectedCurveType = -1; //0 = demand, 1 = supply, 2 = newClassical, 3 = AD, 4 = AS

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

    public List<Demand> getDemandCurves() {
        return demandCurves;
    }

    public void setDemandCurves(List<Demand> demandCurves) {
        this.demandCurves = demandCurves;
    }

    public List<Supply> getSupplyCurves() {
        return supplyCurves;
    }

    public void setSupplyCurves(List<Supply> supplyCurves) {
        this.supplyCurves = supplyCurves;
    }

    public List<Intersection> getIntersectionLL() {
        return intersectionLL;
    }

    public void setIntersectionLL(List<Intersection> intersectionLL) {
        this.intersectionLL = intersectionLL;
    }

    public List<Node> getShiftArrowsLL() {
        return shiftArrowsLL;
    }

    public void setShiftArrowsLL(List<Node> shiftArrowsLL) {
        this.shiftArrowsLL = shiftArrowsLL;
    }

    public List<ShadedRegion> getShadedRegionsLL() {
        return shadedRegionsLL;
    }

    public void setShadedRegionsLL(List<ShadedRegion> shadedRegionsLL) {
        this.shadedRegionsLL = shadedRegionsLL;
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

    public int getAggregateDemandCount() {
        return aggregateDemandCount;
    }

    public void setAggregateDemandCount(int aggregateDemandCount) {
        this.aggregateDemandCount = aggregateDemandCount;
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

    public int getSelectedShadedRegionIndex() {
        return selectedShadedRegionIndex;
    }

    public void setSelectedShadedRegionIndex(int selectedShadedRegionIndex) {
        this.selectedShadedRegionIndex = selectedShadedRegionIndex;
    }

    public ToggleGroup getGraphMakerInsertedShadedRegionsTG() {
        return graphMakerInsertedShadedRegionsTG;
    }

    public void setGraphMakerInsertedShadedRegionsTG(ToggleGroup graphMakerInsertedShadedRegionsTG) {
        this.graphMakerInsertedShadedRegionsTG = graphMakerInsertedShadedRegionsTG;
    }

    public void setOptionsTemplateTG(ToggleGroup optionsTemplateTG) {
        this.optionsTemplateTG = optionsTemplateTG;
    }

    public List<String> getShadedRegionFieldsLL() {
        return shadedRegionFieldsLL;
    }

    public void setShadedRegionFieldsLL(List<String> shadedRegionFieldsLL) {
        this.shadedRegionFieldsLL = shadedRegionFieldsLL;
    }

    public List<AggregateSupply> getAggregateSupplyCurves() {
        return aggregateSupplyCurves;
    }

    public void setAggregateSupplyCurves(List<AggregateSupply> aggregateSupplyCurves) {
        this.aggregateSupplyCurves = aggregateSupplyCurves;
    }

    public List<AggregateDemand> getAggregateDemandCurves() {
        return aggregateDemandCurves;
    }

    public void setAggregateDemandCurves(List<AggregateDemand> aggregateDemandCurves) {
        this.aggregateDemandCurves = aggregateDemandCurves;
    }

    public List<NewClassical> getNewClassicalCurves() {
        return newClassicalCurves;
    }

    public void setNewClassicalCurves(List<NewClassical> newClassicalCurves) {
        this.newClassicalCurves = newClassicalCurves;
    }

    public int getAggregateSupplyCount() {
        return aggregateSupplyCount;
    }

    public void setAggregateSupplyCount(int aggregateSupplyCount) {
        this.aggregateSupplyCount = aggregateSupplyCount;
    }
}
