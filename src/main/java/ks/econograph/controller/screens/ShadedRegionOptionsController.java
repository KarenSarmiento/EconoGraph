package ks.econograph.controller.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ks.econograph.Context;
import ks.econograph.controller.MainController;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by KSarm on 20/01/2017.
 */
public class ShadedRegionOptionsController {

    private MainController main;

    @FXML
    GridPane shadedRegionGP = new GridPane();
    @FXML
    ComboBox shadedRegionFromCB = new ComboBox(); //shadedRegionFromCB
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

    public void calculateShadedRegionVertices() {

    }

    //TODO: Validate combobox so that only vertical lines are picked vice versa
    public void addIntersectionComboBoxValues() {
        addComboBoxValue(shadedRegionFromCB, Context.getInstance().getIntersectionLL(), false);
        addComboBoxValue(shadedRegionToCB, Context.getInstance().getIntersectionLL(), false);
        addComboBoxValue(shadedRegionBetween1CB, Context.getInstance().getCurvesLL(), true);
        addComboBoxValue(shadedRegionBetween2CB, Context.getInstance().getCurvesLL(), true);
    }

    public void addComboBoxValue(ComboBox comboBox, List list, boolean curve) {
        List<String> rawList = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            if (curve) {
                rawList.add(Context.getInstance().getCurvesLL().get(i).getLabel().getText());
            }
            else {
                Group group = (Group) list.get(i);
                rawList.add(group.getChildren().get(0).getId());
                System.out.println(rawList);
            }
        }
        ObservableList<String> comboBoxValues = FXCollections.observableArrayList(rawList);
        System.out.println(comboBoxValues);
        comboBox.setItems(comboBoxValues);
        comboBox.setValue(comboBoxValues.get(0));
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
        addIntersectionComboBoxValues();
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
