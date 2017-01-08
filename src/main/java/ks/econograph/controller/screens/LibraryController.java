package ks.econograph.controller.screens;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import ks.econograph.Context;
import ks.econograph.controller.MainController;
import ks.econograph.graph.components.Graph;

import javax.swing.text.html.ImageView;
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

    public void oldGraphToGraphMaker() {
        main.setScreenToGraphMaker();
        loadGraph();
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

            Context.getInstance().setCurrentEditingGraph(loadOldGraph);
            while ((line = br.readLine()) != null) {
                splittedLine = line.split(",");
                if (splittedLine[0].equals("newComp")) {
                    //TODO: Make this demand work by somehow accessing method in graphmaker controller
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

    public void updateLibrary() {
        tempAddGraphsToLL();
        System.out.println(Context.getInstance().getGraphsLL());
        //GridPane.setConstraints(label, 3, 1); // column=3 row=1

        for (int i = 0; i < Context.getInstance().getGraphsLL().size(); i++) {
            Label title = new Label(Context.getInstance().getGraphsLL().get(i).getTitle());
            libraryGraphGP.setConstraints(title, 0, i+1);
            libraryGraphGP.getChildren().add(title);
        }
    }

    public void tempAddGraphsToLL() {
        for(int i = 0; i < 10; i++) {
            Graph graph = new Graph();
            graph.setTitle(Character.toString((char)(i+65)));
            System.out.println(graph.getTitle());
            Context.getInstance().getGraphsLL().add(graph);
        }
    }
    public void setScreenToOptions() {
        main.setScreenToOptions();
    }

    public void init(MainController mainController) {
        main = mainController;
    }
}
