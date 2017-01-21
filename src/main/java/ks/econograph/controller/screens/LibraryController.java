package ks.econograph.controller.screens;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ks.econograph.Context;
import ks.econograph.controller.MainController;
import ks.econograph.graph.components.Graph;

import java.io.*;
import java.util.Date;

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

    public void resetAndDisplayGraphsFromGraphsLLToLibrary() {
        libraryGraphGP.getChildren().remove(5,libraryGraphGP.getChildren().size());
        libraryGraphGP.setGridLinesVisible(true);
        for (int i = 0; i < Context.getInstance().getGraphsLL().size(); i++) {
            if (Context.getInstance().getGraphsLL().get(i).isVisible() == true) {
                String title = Context.getInstance().getGraphsLL().get(i).getTitle();
                Label titleLabel = new Label(title);
                libraryGraphGP.setConstraints(titleLabel, 0, i + 1);
                ImageView graphImage = new ImageView(new javafx.scene.image.Image("file:" + Context.getInstance().getFileLocationForSavedImages() +
                        Context.getInstance().getGraphsLL().get(i).getFileName()));
                System.out.println(Context.getInstance().getFileLocationForSavedImages() +
                        Context.getInstance().getGraphsLL().get(i).getFileName());
                graphImage.setFitWidth(375);
                graphImage.setFitHeight(225);
                VBox graphColumnContents = new VBox(3);
                graphColumnContents.getChildren().addAll(titleLabel, graphImage);
                libraryGraphGP.setConstraints(graphColumnContents, 0, i + 1);
                Label description = new Label(Context.getInstance().getGraphsLL().get(i).getDescription());
                libraryGraphGP.setConstraints(description, 1, i + 1);
                if (Context.getInstance().getGraphsLL().get(i).isFavourite()) {
                    ImageView favouriteStar = new ImageView(new javafx.scene.image.Image("file:C:\\Users\\KSarm\\Documents\\Intellij Projects\\EconoGraph2\\src\\main\\resources\\FavouritesStar.png"));
                    favouriteStar.setFitHeight(50);
                    favouriteStar.setFitWidth(50);
                    libraryGraphGP.setConstraints(favouriteStar, 2, i + 1);
                    libraryGraphGP.getChildren().add(favouriteStar);
                }
                Button editButton = new Button("Edit " + title);
                editButton.setOnAction(e -> oldGraphToGraphMaker(title));
                Button deleteButton = new Button("Delete");
                Button saveToDocuments = new Button("Save to Documents");
                int curveIndex = i;
                saveToDocuments.setOnAction(e -> savePdfToSelectedFileLocation(curveIndex));
                VBox buttonMenu = new VBox(5);
                buttonMenu.getChildren().addAll(editButton, deleteButton, saveToDocuments);
                libraryGraphGP.setConstraints(buttonMenu, 3, i + 1);
                libraryGraphGP.getChildren().addAll(graphColumnContents, description, buttonMenu);
            }
        }
    }


    private void savePdfToSelectedFileLocation(int curveIndex) {
        try {
            OutputStream file = new FileOutputStream(openFileDirectoryAndOutputDesiredFileLocation());
            Document document = new Document();
            PdfWriter.getInstance(document, file);
            document.open();
            document.add(new Paragraph(Context.getInstance().getGraphsLL().get(curveIndex).getTitle()));
            document.add(new Paragraph(new Date().toString()));
            Image graphImage = Image.getInstance(Context.getInstance().getFileLocationForSavedImages() + Context.getInstance().getGraphsLL().get(curveIndex).getFileName());
            graphImage.scaleToFit(550f, 550f);
            document.add(graphImage);
            document.add(new Paragraph(Context.getInstance().getGraphsLL().get(curveIndex).getDescription()));
            document.close();
            file.close();
        } catch (Exception e) {
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
                System.out.println("Condition met : FilterSearch " + Context.getInstance().getFilterSearch() + ", Filter Topic " + Context.getInstance().getFilterTopic() +
                        ", FilterFavourite " + Context.getInstance().isFilterFavourite());
                System.out.println(Context.getInstance().getGraphsLL().get(i).isFavourite());
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
        main.setScreenToGraphMaker();
        loadGraph(graphName);
    }

    public void loadGraph(String graphName){ //loading graph named "TestGraph"
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
                    switch (splittedLine[1]) {
                        case "Demand" : {
                            if (splittedLine[7].equals("true")) {
                                main.getGraphMakerController().insertDemand(splittedLine[2], Integer.parseInt(splittedLine[3]),
                                        Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), true);
                            }
                            else {
                                System.out.println("reached Demand in the making");
                                main.getGraphMakerController().insertDemand(splittedLine[2], Integer.parseInt(splittedLine[3]),
                                        Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), false);
                                System.out.println("demand: curvesLL" + Context.getInstance().getCurvesLL());
                            }
                            break;
                        }
                        case "Supply" : {
                            if (splittedLine[7].equals("true")) {
                                main.getGraphMakerController().insertSupply(splittedLine[2], Integer.parseInt(splittedLine[3]),
                                        Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), true);
                            }
                            else {
                                main.getGraphMakerController().insertSupply(splittedLine[2], Integer.parseInt(splittedLine[3]),
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

    public void setScreenToOptions() {
        main.setScreenToOptions();
    }

    public void init(MainController mainController) {
        main = mainController;
    }
}
