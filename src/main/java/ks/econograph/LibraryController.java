package ks.econograph;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import ks.econograph.graph.components.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by KSarm on 01/01/2017.
 */
public class LibraryController {

    @FXML
    GridPane libraryGraphGP = new GridPane();
    @FXML
    CheckBox libraryFavouritesCB = new CheckBox();
    @FXML
    Button editGraph = new Button();

    public void oldGraphToGraphMaker() {
        setScreenToGraphMaker();
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
//                                insertDemand(splittedLine[2], Integer.parseInt(splittedLine[3]),
//                                        Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), true);
//                                insertDemand();
                                System.out.println("demand insert now (temp not showing)");
                            }
                            else {
                                System.out.println("reached Demand in the making");
//                                insertDemand(splittedLine[2], Integer.parseInt(splittedLine[3]),
//                                        Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), false);
//                                insertDemand();
                                System.out.println("demand insert now (temp not showing)");
                                System.out.println("demand: curvesLL" + Context.getInstance().getCurvesLL());
                            }
                            break;
                        }
                        case "Supply" : {
                            if (splittedLine[7].equals("true")) {
//                                insertSupply(splittedLine[2], Integer.parseInt(splittedLine[3]),
//                                        Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), true);
                            }
                            else {
//                                insertSupply(splittedLine[2], Integer.parseInt(splittedLine[3]),
//                                        Integer.parseInt(splittedLine[4]), splittedLine[5], Integer.parseInt(splittedLine[6]), false);
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
        Context.getInstance().getLibrary().setVisible(false);
        Context.getInstance().getOptions().setVisible(true);
        Context.getInstance().getGraphMaker().setVisible(false);
        Context.getInstance().getSaveMenu().setVisible(false);
    }
    public void setScreenToGraphMaker() {
        Context.getInstance().getLibrary().setVisible(false);
        Context.getInstance().getOptions().setVisible(false);
        Context.getInstance().getGraphMaker().setVisible(true);
        Context.getInstance().getSaveMenu().setVisible(false);
    }
}
