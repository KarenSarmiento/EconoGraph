package ks.econograph;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import ks.econograph.graph.components.Curve;
import ks.econograph.graph.components.Demand;
import ks.econograph.graph.components.Graph;
import ks.econograph.graph.components.Supply;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Controller {
    static Parent library;
    static Parent options;
    static Parent graphMaker;
    static Parent saveMenu;

    static int demandCount = 0;
    static int supplyCount = 0;
    static int newClassicalCount = 0;
    static int keynesianCount = 0;

    int selectedCurveIndex;
    int selectedCurveType; // 0 = demand, 1 = supply, 2 = newClassical, 3 = keynesian

    @FXML
    Button editGraph = new Button();//temporary
    @FXML
    Pane graphMakerWorkspaceP = new Pane();
    @FXML
    FlowPane graphMakerRadioButtonsFP = new FlowPane();
    @FXML
    GridPane libraryGraphGP = new GridPane();
    @FXML
    TextField optionsTitleTF = new TextField();

    @FXML
    TextField saveMenuTitleTF = new TextField();

    @FXML
    Slider graphMakerShiftSlider = new Slider();
    @FXML
    Slider graphMakerElasticitySlider = new Slider();
    @FXML
    ColorPicker graphMakerColourPicker = new ColorPicker();
    @FXML
    Slider graphMakerThicknessSlider = new Slider();
    @FXML
    CheckBox libraryFavouritesCB = new CheckBox();
    @FXML
    ComboBox saveMenuTopicCB = new ComboBox();

    Graph currentEditingGraph = new Graph();
    ToggleGroup group = new ToggleGroup();

    //TODO: Remove static; a new controller is being created.
    static List<Curve> curvesLL = new LinkedList<>();
    static Pane staticGraphMakerWorkspaceP;
    static FlowPane staticGraphMakerRadioButtonsFP;

    LinkedList<Graph> graphsLL = new LinkedList<Graph>();
    WritableImage tempScreenShot = graphMakerWorkspaceP.snapshot(new SnapshotParameters(), null); //contains negligible image at start


    public void captureShotWorkspace() {
        tempScreenShot = graphMakerWorkspaceP.snapshot(new SnapshotParameters(), null);
        File imageFile = new File("C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\FileWriting\\tempWorkspace.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(tempScreenShot, null), "png", imageFile);
        } catch (IOException e) {
            System.out.println("ioe");
        }
    }

    public void captureAndSetToSave() {
        captureShotWorkspace(); //sets tempScreenShot
        setScreenToSaveMenu();
        //TODO: on click of done set save of file loca
    }

    public void tempAddGraphsToLL() {
        for(int i = 0; i < 10; i++) {
            Graph graph = new Graph();
            graph.setTitle(Character.toString((char)(i+65)));
            System.out.println(graph.getTitle());
            graphsLL.add(graph);
        }
    }

    public void updateLibrary() {
        tempAddGraphsToLL();
        System.out.println(graphsLL);
        //GridPane.setConstraints(label, 3, 1); // column=3 row=1

        for (int i = 0; i < graphsLL.size(); i++) {
            Label title = new Label(graphsLL.get(i).getTitle());
            libraryGraphGP.setConstraints(title, 0, i+1);
            libraryGraphGP.getChildren().add(title);
        }
    }
    public void setScreenToLibrary() {
        library.setVisible(true);
        options.setVisible(false);
        graphMaker.setVisible(false);
        saveMenu.setVisible(false);
    }
    public void setScreenToOptions() {
        library.setVisible(false);
        options.setVisible(true);
        graphMaker.setVisible(false);
        saveMenu.setVisible(false);
    }
    public void setScreenToGraphMaker() {
        System.out.println("Changing screen to graphmaker");
        library.setVisible(false);
        options.setVisible(false);
        graphMaker.setVisible(true);
        saveMenu.setVisible(false);
        System.out.println("graphmaker now showing");
    }

    public void setScreenToSaveMenu() {
        library.setVisible(false);
        options.setVisible(false);
        graphMaker.setVisible(false);
        saveMenu.setVisible(true);
    }

    public void newGraphToGraphMaker() {
        setScreenToGraphMaker();
        Graph actual = new Graph();
        currentEditingGraph = actual;
        System.out.println(currentEditingGraph.getTopic());
        System.out.println(currentEditingGraph.getTitle());
        System.out.println(currentEditingGraph.getDate());
        System.out.println(currentEditingGraph.isFavourite());
    }

    public void oldGraphToGraphMaker() {
        setScreenToGraphMaker();
        loadGraph();
    }

    public void createRadioButton(String name, int index, int type) {
        if (staticGraphMakerRadioButtonsFP == null) {
            staticGraphMakerRadioButtonsFP = graphMakerRadioButtonsFP;
        }

        RadioButton radioButton = new RadioButton(name);
        radioButton.setId("demandRadio" + index);
        radioButton.setToggleGroup(group);
        System.out.println("Created radio button: " + radioButton);
        //TODO: there is some problem with this -> radioButton.setSelected(true);
        radioButton.setOnAction(e -> {
            selectedCurveIndex = index;
            selectedCurveType = type;
            //need to set to previously set elasticity or shift
            graphMakerElasticitySlider.setValue(curvesLL.get(selectedCurveIndex).getElasticityGap());
            graphMakerShiftSlider.setValue(0);
            graphMakerThicknessSlider.setValue(5);
        });
        if (demandCount + supplyCount + newClassicalCount + keynesianCount -1 == 0) {
            radioButton.setSelected(true);
        }
        staticGraphMakerRadioButtonsFP.getChildren().add(radioButton);
    }

    public void insertDemand() {
        if (staticGraphMakerWorkspaceP == null) {
            staticGraphMakerWorkspaceP = graphMakerWorkspaceP;
        }
        demandCount++;
        int index = demandCount + supplyCount + newClassicalCount + keynesianCount - 1;
        createRadioButton("Demand " + demandCount, index, 0);
        Demand demand = new Demand(staticGraphMakerWorkspaceP, index); //200,50,550,400
        curvesLL.add(demand);
        System.out.println(curvesLL.toString());
    }

    public void resetGraphMaker() {
        demandCount = 0;
        supplyCount = 0;
        newClassicalCount = 0;
        keynesianCount = 0;

        if (staticGraphMakerWorkspaceP != null && curvesLL.size() > 0) {
            staticGraphMakerWorkspaceP.getChildren().remove(1, staticGraphMakerWorkspaceP.getChildren().size());
            curvesLL = new LinkedList<>();
        }

        if (staticGraphMakerRadioButtonsFP != null && staticGraphMakerRadioButtonsFP.getChildren().size() > 0) {
            staticGraphMakerRadioButtonsFP.getChildren().remove(1, staticGraphMakerRadioButtonsFP.getChildren().size());
        }
    }

    public void insertSupply() {
        supplyCount++;
        createRadioButton("Supply " + supplyCount, demandCount + supplyCount + newClassicalCount + keynesianCount -1, 1);
        Supply supply = new Supply(graphMakerWorkspaceP); //200,400,550,50
        curvesLL.add(supply);
        System.out.println(curvesLL.toString());
    }

    public void insertNewClassical() {
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
    }

    public void loadGraph(){ //loading graph named "TestGraph"
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\FileWriting\\test.txt"));
            String line = "";
            String[] splittedLine = line.split(",");
            while ((line = br.readLine()) != null) {
                splittedLine = line.split(",");
                if (splittedLine[0].equals("Test Graph")) {
                    break;
                }
            }
            if (line == null) {
                System.out.println("graph not found");
                //TODO: Validate graph not found.
            }
            Graph loadOldGraph = new Graph();
            loadOldGraph.setTitle(splittedLine[0]);
            System.out.println(loadOldGraph.getTitle());
            loadOldGraph.setTopic(splittedLine[1]);
            loadOldGraph.setDate(Long.parseLong(splittedLine[2]));
            if (splittedLine[3].equals("true"))
                loadOldGraph.setFavourite(true);
            else
                loadOldGraph.setFavourite(false);

            currentEditingGraph = loadOldGraph;

            while ((line = br.readLine()) != null) {
                splittedLine = line.split(",");
                if (splittedLine[0].equals("newComp")) {
                    switch (splittedLine[1]) {
                        case "Demand" : {
                            if (splittedLine[7].equals("true")) {
                                insertDemand(splittedLine[2], Integer.parseInt(splittedLine[3]),
                                        Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), true);
                                insertDemand();
                            }
                            else {
                                System.out.println("reached Demand in the making");
                                insertDemand(splittedLine[2], Integer.parseInt(splittedLine[3]),
                                        Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), false);
                                insertDemand();
                                System.out.println("demand: curvesLL" + curvesLL);
                            }
                            break;
                        }
                        case "Supply" : {
                            if (splittedLine[7].equals("true")) {
                                insertSupply(splittedLine[2], Integer.parseInt(splittedLine[3]),
                                        Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), true);
                            }
                            else {
                                insertSupply(splittedLine[2], Integer.parseInt(splittedLine[3]),
                                        Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), false);
                            }
                            break;
                        }
                    }
                }
                else {
                    break;
                }
            }
            br.close();
        }
        catch(IOException ioe) {
            System.out.println("IOE");
        }
    }

    public void saveGraph() { //working well :-)
        try {
            //check for title uniqueness
            boolean uniqueTitle = true;
            for (int i = 0; i < graphsLL.size(); i++) {
                if (optionsTitleTF.getText().equals(graphsLL.get(i).getTitle())) {
                    System.out.println("title already exists");
                    uniqueTitle = false;
                    break;
                }
            }

            //TODO: edit these console warnings to GUI
            if (uniqueTitle == false) {
                System.out.println("Title already exists");
            }
            else if (saveMenuTopicCB.getValue() == null) {
                System.out.println("Topic not selected");
            }
            else {
                currentEditingGraph.setTitle(saveMenuTitleTF.getText());
                System.out.println(currentEditingGraph.getTitle());
                Date date = new Date();
                long dateOfCreation = date.getTime();

                //TODO: Fix number format exception. Find cause since the date does seem to contain no unacceptable characters.
                currentEditingGraph.setDate(dateOfCreation);
                //TODO: set type
                currentEditingGraph.setFavourite(libraryFavouritesCB.isSelected());

                BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\FileWriting\\test.txt"));
                //prevents losing all data since printwriter overwrites file
                LinkedList<String> fileLines = new LinkedList<String>();
                String line;
                while ((line = br.readLine()) != null) {
                    fileLines.add(line);
                }

                PrintWriter pw = new PrintWriter(new FileWriter("C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\FileWriting\\test.txt"));
                for (int i = 0; i < fileLines.size(); i++) {
                    pw.println(fileLines.get(i));
                }
                String newGraphDetail = currentEditingGraph.getTitle() + "," + currentEditingGraph.getTopic() + "," + currentEditingGraph.getDate() + "," + currentEditingGraph.isFavourite();
                pw.println(newGraphDetail);
                String newCurveLine = "";
                for (int i = 0; i < curvesLL.size(); i++) {
                    newCurveLine = "newComp," + curvesLL.get(i).getType() + "," + curvesLL.get(i).getName() + "," + curvesLL.get(i).getCentreX()
                            + "," + curvesLL.get(i).getElasticityGap() + "," + curvesLL.get(i).getColour() + "," + curvesLL.get(i).getThickness()
                            + "," + curvesLL.get(i).isDotted();
                    pw.println(newCurveLine);
                }
                pw.close();
                br.close();
                setScreenToLibrary();
            }

            resetGraphMaker();
        }
        catch (IOException ioe2) {
            System.out.println("IOError");
        }
    }

    public void shiftSelectedCurve() {
        switch (selectedCurveType) {
            case 0: {
                System.out.println(selectedCurveIndex);
                Demand demand = (Demand) curvesLL.get(selectedCurveIndex);
                demand.setCentreX(375 + (int) graphMakerShiftSlider.getValue());
                demand.getLine().setStartX(demand.getCentreX() - demand.getElasticityGap());
                demand.getLine().setEndX(demand.getCentreX() + demand.getElasticityGap());
                break;
            }
            case 1: {
                Supply supply = (Supply) curvesLL.get(selectedCurveIndex);
                supply.setCentreX(375 + (int) graphMakerShiftSlider.getValue());
                supply.getLine().setStartX(supply.getCentreX() + supply.getElasticityGap());
                supply.getLine().setEndX(supply.getCentreX() - supply.getElasticityGap());

                break;
            }
        }
    }

    public void updateElasticityForCurrentCurve() {
        System.out.println(selectedCurveType + " " + selectedCurveIndex);
        switch (selectedCurveType) {
            case 0: {
                Demand demand = (Demand) curvesLL.get(selectedCurveIndex);
                demand.setElasticityGap((int) graphMakerElasticitySlider.getValue());
                demand.getLine().setStartX(demand.getCentreX() - demand.getElasticityGap());
                demand.getLine().setEndX(demand.getCentreX() + demand.getElasticityGap());
                break;
            }
            case 1: {
                Supply supply = (Supply) curvesLL.get(selectedCurveIndex);
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
        switch(selectedCurveType) {
            case 0: {
                Demand demand = (Demand) curvesLL.get(selectedCurveIndex);
                demand.getLine().setStroke(graphMakerColourPicker.getValue());
                demand.setColour(graphMakerColourPicker.getValue().toString());
                break;
            }
            case 1: {
                System.out.println("supply detected");
                //TODO: error here.
                Supply supply = (Supply) curvesLL.get(selectedCurveIndex);
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
        switch(selectedCurveType) {
            case 0: {
                Demand demand = (Demand) curvesLL.get(selectedCurveIndex);
                demand.getLine().setStrokeWidth((int) graphMakerThicknessSlider.getValue());
                demand.setThickness((int) graphMakerThicknessSlider.getValue());
                System.out.println(graphMakerThicknessSlider.getValue());
                break;
            }
            case 1: {
                Supply supply = (Supply) curvesLL.get(selectedCurveIndex);
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

    public void updateDotted() {
        switch(selectedCurveType) {
            case 0: {
                break;
            }
            case 1: {
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

    public void test() {
        switch(selectedCurveType) {
            case 0: {
                break;
            }
            case 1: {
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
}