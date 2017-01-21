package ks.econograph.controller.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import ks.econograph.Context;
import ks.econograph.controller.MainController;

/**
 * Created by KSarm on 20/01/2017.
 */
public class ShadedRegionOptionsController {

    private MainController main;

    @FXML
    ComboBox shadedRegionFromCB = new ComboBox();
    @FXML
    ComboBox shadedRegionToCB = new ComboBox();
    @FXML
    ComboBox shadedRegionBetween1CB = new ComboBox();
    @FXML
    ComboBox shadedRegionBetween2CB = new ComboBox();
    @FXML
    TextField shadedRegionLabelTextTF = new TextField();
    @FXML
    ColorPicker shadedRegionColourPicker = new ColorPicker();

    public void addComboBoxValues() {
        ObservableList<String> comboBoxValues = FXCollections.observableArrayList(
                "Sort",
                "Filter" );
        for (int i = 0; i < Context.getInstance().getIntersectionLL().size(); i++) {
            try {
                //Line line = (Line) Context.getInstance().getIntersectionLL().get(i);
                //comboBoxValues.add(line.getId());
                //double xOrdinate = label.getTranslateX();
            }
            catch (Exception e) {
                System.out.println("node is not a label");
            }
        }
        shadedRegionFromCB.setItems(comboBoxValues);
        shadedRegionFromCB.setValue(comboBoxValues.get(0));

    }

    public void initializeShadedRegionScreen() {
        try {
            Parent root1 = FXMLLoader.load(getClass().getClassLoader().getResource("ShadedRegionOptions.fxml"));
            Scene shadedRegionScene = new Scene(root1);
            Stage shadedRegionStage = new Stage();
            shadedRegionStage.setScene(shadedRegionScene);
            shadedRegionStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init(MainController mainController) {
        main = mainController;
    }

    public ComboBox getShadedRegionFromCB() {
        return shadedRegionFromCB;
    }

    public void setShadedRegionFromCB(ComboBox shadedRegionFromCB) {
        this.shadedRegionFromCB = shadedRegionFromCB;
    }

    public ComboBox getShadedRegionToCB() {
        return shadedRegionToCB;
    }

    public void setShadedRegionToCB(ComboBox shadedRegionToCB) {
        this.shadedRegionToCB = shadedRegionToCB;
    }

    public ComboBox getShadedRegionBetween1CB() {
        return shadedRegionBetween1CB;
    }

    public void setShadedRegionBetween1CB(ComboBox shadedRegionBetween1CB) {
        this.shadedRegionBetween1CB = shadedRegionBetween1CB;
    }

    public ComboBox getShadedRegionBetween2CB() {
        return shadedRegionBetween2CB;
    }

    public void setShadedRegionBetween2CB(ComboBox shadedRegionBetween2CB) {
        this.shadedRegionBetween2CB = shadedRegionBetween2CB;
    }

    public TextField getShadedRegionLabelTextTF() {
        return shadedRegionLabelTextTF;
    }

    public void setShadedRegionLabelTextTF(TextField shadedRegionLabelTextTF) {
        this.shadedRegionLabelTextTF = shadedRegionLabelTextTF;
    }

    public ColorPicker getShadedRegionColourPicker() {
        return shadedRegionColourPicker;
    }

    public void setShadedRegionColourPicker(ColorPicker shadedRegionColourPicker) {
        this.shadedRegionColourPicker = shadedRegionColourPicker;
    }
}
