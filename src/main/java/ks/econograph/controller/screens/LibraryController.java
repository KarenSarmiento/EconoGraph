package ks.econograph.controller.screens;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import ks.econograph.Context;
import ks.econograph.controller.MainController;
import ks.econograph.graph.components.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
    Button editGraph = new Button();
    @FXML
    ComboBox libraryFilterCB = new ComboBox();
    @FXML
    TextField librarySearchTF = new TextField();

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
        if (main != null) {
            main.resetAndDisplayGraphsFromGraphsLLToLibrary();
        }
    }

    public void oldGraphToGraphMaker() {
        main.setScreenToGraphMaker();
        loadGraph("Test Graph");
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
