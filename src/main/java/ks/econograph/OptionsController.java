package ks.econograph;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ks.econograph.graph.components.Graph;

/**
 * Created by KSarm on 07/01/2017.
 */
public class OptionsController {

    @FXML
    TextField optionsTitleTF = new TextField();

    public void setScreenToLibrary() {
        Context.getInstance().getLibrary().setVisible(true);
        Context.getInstance().getOptions().setVisible(false);
        Context.getInstance().getGraphMaker().setVisible(false);
        Context.getInstance().getSaveMenu().setVisible(false);
    }

    public void newGraphToGraphMaker() {
        setScreenToGraphMaker();
        Graph actual = new Graph();
        Context.getInstance().setCurrentEditingGraph(actual);
    }

    public void setScreenToGraphMaker() {
        Context.getInstance().getLibrary().setVisible(false);
        Context.getInstance().getOptions().setVisible(false);
        Context.getInstance().getGraphMaker().setVisible(true);
        Context.getInstance().getSaveMenu().setVisible(false);
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
}
