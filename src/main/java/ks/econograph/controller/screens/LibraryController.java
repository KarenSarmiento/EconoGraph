package ks.econograph.controller.screens;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ks.econograph.Context;
import ks.econograph.controller.MainController;
import ks.econograph.graph.components.Graph;
import ks.econograph.graph.components.ShadedRegion;

import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by KSarm on 01/01/2017.
 */
public class LibraryController {

    private MainController main;

    @FXML
    public GridPane libraryGraphGP;
    @FXML
    CheckBox libraryFavouritesCB = new CheckBox();
    @FXML
    ComboBox libraryFilterCB = new ComboBox();
    @FXML
    TextField librarySearchTF = new TextField();
    @FXML
    ComboBox librarySortCB = new ComboBox();

    public void deleteAllGraphs() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(Context.getInstance().getFilePathForSavedGraphs()));
            pw.println();
            main.resetAndReadInGraphsToGraphsLL();
            resetAndDisplayGraphsFromGraphsLLToLibrary();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteGraph(String title) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Context.getInstance().getFilePathForSavedGraphs()));
            String line;
            List<String> fileLines = new LinkedList<>();
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] splittedLine = line.split(",");
                if (splittedLine[0].equals(title))
                    break;
                else
                    fileLines.add(line);
            }
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] splittedLine = line.split(",");
                if (!splittedLine[0].equals("newComp")) {
                    fileLines.add(line);
                    break;
                }
            }
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                fileLines.add(line);
            }

            PrintWriter pw = new PrintWriter(new FileWriter(Context.getInstance().getFilePathForSavedGraphs()));
            for (int i = 0; i < fileLines.size(); i++) {
                pw.println(fileLines.get(i));
            }

            pw.close();
            br.close();
            main.resetAndReadInGraphsToGraphsLL();
            resetAndDisplayGraphsFromGraphsLLToLibrary();

        } catch (IOException ioe2) {
            System.out.println("IOError");
        }

    }

    public void resetAndDisplayGraphsFromGraphsLLToLibrary() {
        libraryGraphGP.getChildren().remove(5,libraryGraphGP.getChildren().size());
        libraryGraphGP.setGridLinesVisible(true);
        for (int i = 0; i < Context.getInstance().getGraphsLL().size(); i++) {
            if (Context.getInstance().getGraphsLL().get(i).isVisible() == true) {
                String title = Context.getInstance().getGraphsLL().get(i).getTitle();
                Label titleLabel = new Label(title);
                titleLabel.getStyleClass().add("description-text");
                libraryGraphGP.setConstraints(titleLabel, 0, i + 1);
                ImageView graphImage = new ImageView(new javafx.scene.image.Image("file:" + Context.getInstance().getFileLocationForSavedImages() +
                        Context.getInstance().getGraphsLL().get(i).getFileName()));
                System.out.println(Context.getInstance().getFileLocationForSavedImages() +
                        Context.getInstance().getGraphsLL().get(i).getFileName());
                graphImage.setFitWidth(375);
                graphImage.setFitHeight(225);
                VBox graphColumnContents = new VBox(3);
                graphColumnContents.getStyleClass().add("padding");
                graphColumnContents.getChildren().addAll(titleLabel, graphImage);
                libraryGraphGP.setConstraints(graphColumnContents, 0, i + 1);

                StackPane descriptionSP = new StackPane();
                Text description = new Text(Context.getInstance().getGraphsLL().get(i).getDescription());
                descriptionSP.getChildren().add(description);
                description.setWrappingWidth(300);
                description.getStyleClass().add("description-text");
                libraryGraphGP.setConstraints(descriptionSP, 1, i + 1);

                StackPane favouriteSP = new StackPane();
                libraryGraphGP.setConstraints(favouriteSP, 2, i + 1);
                if (Context.getInstance().getGraphsLL().get(i).isFavourite()) {
                    ImageView favouriteStar = new ImageView(new javafx.scene.image.Image("file:C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\Images\\favouriteStar.png"));
                    favouriteStar.setFitHeight(75);
                    favouriteStar.setFitWidth(75);
                    favouriteSP.getChildren().add(favouriteStar);
                }

                Button editButton = new Button("Edit");
                setButtonAppearance(editButton, "edit-button");
                editButton.setOnAction(e -> oldGraphToGraphMaker(title));
                Button deleteButton = new Button("Delete");
                setButtonAppearance(deleteButton, "delete-button");
                deleteButton.setOnAction(e -> deleteGraph(title));
                Button saveToDocuments = new Button("Save to Documents");
                setButtonAppearance(saveToDocuments, "save-button");
                int curveIndex = i;
                saveToDocuments.setOnAction(e -> savePdfToSelectedFileLocation(curveIndex));
                VBox buttonMenu = new VBox(5);
                buttonMenu.getStyleClass().add("padding");
                buttonMenu.getChildren().addAll(editButton, deleteButton, saveToDocuments);
                libraryGraphGP.setConstraints(buttonMenu, 3, i + 1);

                if(i%2 == 0)
                    setGridPaneBackgroundColour(graphColumnContents, descriptionSP, favouriteSP, buttonMenu, "grid-pane-background-even");
                else
                    setGridPaneBackgroundColour(graphColumnContents, descriptionSP, favouriteSP, buttonMenu, "grid-pane-background-odd");

                libraryGraphGP.getChildren().addAll(graphColumnContents, descriptionSP, favouriteSP, buttonMenu);
            }
        }
    }

    private void setGridPaneBackgroundColour(VBox graphColumnContents, StackPane descriptionSP, StackPane favouriteSP, VBox buttonMenu, String cssClass) {
        graphColumnContents.getStyleClass().add(cssClass);
        descriptionSP.getStyleClass().add(cssClass);
        favouriteSP.getStyleClass().add(cssClass);
        buttonMenu.getStyleClass().add(cssClass);
    }

    private void setButtonAppearance(Button button, String cssClass) {
        button.getStyleClass().add(cssClass);
        button.setPrefWidth(175);
    }

    public void saveAllGraphsToDocuments() {
        try {
            OutputStream file = new FileOutputStream(openFileDirectoryAndOutputDesiredFileLocation());
            Document document = new Document();
            PdfWriter.getInstance(document, file);
            document.open();
            for (int i = 0; i < Context.getInstance().getGraphsLL().size(); i++) {
                printGraphToPDF(document, i);
                document.newPage();
            }
            document.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void savePdfToSelectedFileLocation(int curveIndex) {
        try {
            OutputStream file = new FileOutputStream(openFileDirectoryAndOutputDesiredFileLocation());
            Document document = new Document();
            PdfWriter.getInstance(document, file);
            document.open();
            printGraphToPDF(document, curveIndex);
            document.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printGraphToPDF(Document document, int curveIndex) {
        try {
            document.add(new Paragraph(Context.getInstance().getGraphsLL().get(curveIndex).getTitle()));
            document.add(new Paragraph(new Date().toString()));
            Image graphImage = Image.getInstance(Context.getInstance().getFileLocationForSavedImages() + Context.getInstance().getGraphsLL().get(curveIndex).getFileName());
            graphImage.scaleToFit(550f, 550f);
            document.add(graphImage);
            document.add(new Paragraph(Context.getInstance().getGraphsLL().get(curveIndex).getDescription()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File openFileDirectoryAndOutputDesiredFileLocation() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("PDF Files", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(null);

        return selectedFile;
    }

    public void sortGraphsWithSelectionSort() {
        Graph[] graphsArray = new Graph[Context.getInstance().getGraphsLL().size()];
        for (int i = 0; i < Context.getInstance().getGraphsLL().size(); i++) {
            graphsArray[i] = Context.getInstance().getGraphsLL().get(i);
        }
        int i,j,first;
        Graph temp;
        for (i = graphsArray.length -1; i > 0; i--) {
            first = 0;
            for (j = 1; j <= i; j++) {
                switch(librarySortCB.getValue().toString()) {
                    case "A to Z": {
                        if (graphsArray[j].getTitle().compareTo(graphsArray[first].getTitle()) > 0)
                            first = j;
                        break;
                    }
                    case "Date of Creation": {
                        if (graphsArray[j].getTime() > graphsArray[first].getTime())
                            first = j;
                        break;
                    }
                    case "Topic": {
                        if (graphsArray[j].getTopic().compareTo(graphsArray[first].getTopic()) > 0)
                            first = j;
                        break;
                    }
                }
            }
            temp = graphsArray[first];
            graphsArray[first] = graphsArray[i];
            graphsArray[i] = temp;
        }
        Context.getInstance().getGraphsLL().clear();
        for(int k = 0; k < graphsArray.length; k++) {
            Context.getInstance().getGraphsLL().add(graphsArray[k]);
        }
        resetAndDisplayGraphsFromGraphsLLToLibrary();
    }

    public void resetSearchAndFilterOptions() {
        Context.getInstance().setFilterSearch(null);
        Context.getInstance().setFilterTopic(null);
        Context.getInstance().setFilterFavourite(false);
        libraryFilterCB.setValue("Show All");
        libraryFavouritesCB.setSelected(false);
        librarySearchTF.setText("");
        updateGraphsVisibilityInLibrary();
    }

    public void setFilterFromSearch() {
        Context.getInstance().setFilterSearch(librarySearchTF.getText());
        updateGraphsVisibilityInLibrary();
    }

    public void setFilterByType() {
        if (libraryFilterCB.getValue().toString().equals("Show All")) {
            Context.getInstance().setFilterTopic(null);
        }
        else {
            Context.getInstance().setFilterTopic(libraryFilterCB.getValue().toString());
        }
        updateGraphsVisibilityInLibrary();
    }

    public void setFilterFavourites() {
        if (libraryFavouritesCB.isSelected()) {
            Context.getInstance().setFilterFavourite(true);
        }
        else {
            Context.getInstance().setFilterFavourite(false);
        }
        updateGraphsVisibilityInLibrary();

    }

    private void updateGraphsVisibilityInLibrary() {
        System.out.println(Context.getInstance().getGraphsLL().toString());
        for(int i = 0; i < Context.getInstance().getGraphsLL().size(); i++){
            if ((Context.getInstance().getFilterSearch() != null && Context.getInstance().getGraphsLL().get(i).getTitle().contains(Context.getInstance().getFilterSearch()) == false) ||
                    (Context.getInstance().getFilterTopic() != null && Context.getInstance().getGraphsLL().get(i).getTopic().equals(Context.getInstance().getFilterTopic()) == false) ||
                    (Context.getInstance().isFilterFavourite() == true && Context.getInstance().getGraphsLL().get(i).isFavourite() == false)) {
                Context.getInstance().getGraphsLL().get(i).setVisible(false);
            }
            else {
                Context.getInstance().getGraphsLL().get(i).setVisible(true);
            }
        }
        System.out.println(Context.getInstance().getGraphsLL().toString());
            resetAndDisplayGraphsFromGraphsLLToLibrary();
    }

    public void oldGraphToGraphMaker(String graphName) {
        main.resetForNewGraph();
        main.setScreenToGraphMaker();
        loadGraph(graphName);
    }

    public void loadGraph(String graphName){
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\KSarm\\OneDrive\\IB\\Computer Science\\IA\\FileWriting\\test.txt"));
            String line = "";
            String[] splittedLine = line.split(",");
            while ((line = br.readLine()) != null) {
                splittedLine = line.split(",");
                if (splittedLine[0].equals(graphName)) {
                    break;
                }
            }
            if (line == null) {
                System.out.println("graph not found");
            }
            Graph loadOldGraph = new Graph();
            loadOldGraph.setTitle(splittedLine[0]);
            System.out.println(loadOldGraph.getTitle());
            loadOldGraph.setTopic(splittedLine[1]);
            loadOldGraph.setTime(Long.parseLong(splittedLine[2]));
            loadOldGraph.setDescription(splittedLine[3]);
            if (splittedLine[4].equals("true"))
                loadOldGraph.setFavourite(true);
            else
                loadOldGraph.setFavourite(false);

            Context.getInstance().setCurrentEditingGraph(loadOldGraph);
            while ((line = br.readLine()) != null) {
                splittedLine = line.split(",");
                if (splittedLine[0].equals("newComp")) {
                    insertComponent(splittedLine);
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

    private void insertComponent(String[] splittedLine) {
        switch (splittedLine[1]) {
            case "Demand" : {
                    main.getGraphMakerController().insertDemand(splittedLine[2], Integer.parseInt(splittedLine[3]),
                            Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), splittedLine[7]);
                break;
            }
            case "Supply" : {
                    main.getGraphMakerController().insertSupply(splittedLine[2], Integer.parseInt(splittedLine[3]),
                            Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), splittedLine[7]);
                break;
            }
            case "AggregateDemand": {
                    main.getGraphMakerController().insertAggregateDemand(splittedLine[2], Integer.parseInt(splittedLine[3]),
                            Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), splittedLine[7]);
                break;
            }
            case "AggregateSupply": {
                    main.getGraphMakerController().insertAggregateSupply(splittedLine[2], Integer.parseInt(splittedLine[3]),
                            Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), splittedLine[7]);
                break;
            }
            case "MSB": {
                    main.getGraphMakerController().insertMSB(splittedLine[2], Integer.parseInt(splittedLine[3]),
                            Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), splittedLine[7]);
                break;
            }
            case "MSC": {
                    main.getGraphMakerController().insertMSC(splittedLine[2], Integer.parseInt(splittedLine[3]),
                            Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), splittedLine[7]);
                break;
            }
            case "MPB": {
                    main.getGraphMakerController().insertMPB(splittedLine[2], Integer.parseInt(splittedLine[3]),
                            Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), splittedLine[7]);
                break;
            }
            case "MPC": {
                    main.getGraphMakerController().insertMPC(splittedLine[2], Integer.parseInt(splittedLine[3]),
                            Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), splittedLine[7]);
                break;
            }
            case "NewClassical": {
                    main.getGraphMakerController().insertNewClassical(splittedLine[2], Integer.parseInt(splittedLine[3]),
                            Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), splittedLine[7]);
                break;
            }
            case "GeneralDownwardSloping": {
                main.getGraphMakerController().insertGeneralDownwardSloping(splittedLine[2], Integer.parseInt(splittedLine[3]),
                        Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), splittedLine[7]);
                break;
            }
            case "GeneralUpwardSloping": {
                main.getGraphMakerController().insertGeneralUpwardSloping(splittedLine[2], Integer.parseInt(splittedLine[3]),
                        Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), splittedLine[7]);
                break;
            }
            case "ShadedRegion": {
                ShadedRegion shadedRegion = new ShadedRegion(splittedLine[2], splittedLine[3], splittedLine[4], splittedLine[5], splittedLine[6], splittedLine[7],
                        main.getGraphMakerController().getGraphMakerWorkspaceP(), main.getGraphMakerController().getGraphMakerShadedRegionRadioFP());
                Context.getInstance().getShadedRegionsLL().add(shadedRegion);
                String shadedRegionFields = splittedLine[2] + "," + splittedLine[3] + "," + splittedLine[4] + "," +
                        splittedLine[5] + "," + splittedLine[6] + "," + splittedLine[7];
                Context.getInstance().getShadedRegionFieldsLL().add(shadedRegionFields);
                break;
            }
        }
    }

    public void setScreenToOptions() {
        main.setScreenToOptions();
    }

    public void init(MainController mainController) {
        main = mainController;
    }
}
