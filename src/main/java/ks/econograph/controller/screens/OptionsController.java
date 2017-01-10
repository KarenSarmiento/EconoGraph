package ks.econograph.controller.screens;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ks.econograph.Context;
import ks.econograph.controller.MainController;
import ks.econograph.graph.components.Graph;

import javax.swing.text.html.ImageView;

/**
 * Created by KSarm on 07/01/2017.
 */
public class OptionsController {

    private MainController main;

    @FXML
    public GridPane optionsGP;
    @FXML
    TextField optionsTitleTF = new TextField();

    public void newGraphToGraphMaker() {
        if (optionsTitleTF.getText() == null) {
            System.out.println("Please enter a title.");
        }
        else {
            Context.getInstance().setCurrentEditingGraph(new Graph(optionsTitleTF.getText()));
            System.out.println();
            setScreenToGraphMaker();
        }
    }

    public void setScreenToLibrary() {
        main.setScreenToLibrary();
    }

    public void setScreenToGraphMaker() {
        main.setScreenToGraphMaker();
    }

    public void test() {
        switch(Context.getInstance().getSelectedCurveType()) {
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

    public void init(MainController mainController) {
        main = mainController;
    }
}
