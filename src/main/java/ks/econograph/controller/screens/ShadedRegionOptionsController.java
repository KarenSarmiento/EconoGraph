package ks.econograph.controller.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import ks.econograph.Context;
import ks.econograph.controller.MainController;
import ks.econograph.graph.components.StraightCurve;

import java.util.LinkedList;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.Infinity;

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

    public Double[] calculateShadedRegionVertices() {
        List<String> vertices = new LinkedList<>();
        //ASSUME TWO VERTICAL (Q) INTERSECTION AND TWO CURVES
        String a = "Q";
        String b = "Q1";
        String c = "D";
        String d = "S";
        String[] shadedRegionInputs = {a,b,c,d};
        List<Line> intersectionLinesLL = new LinkedList<>();
        List<StraightCurve> straightCurvesLL = new LinkedList<>();
        useNamesToFindCorrespondingIntersectionLinesAndCurves(shadedRegionInputs, intersectionLinesLL, straightCurvesLL);

        //ASSUME FIRST TWO ARE LINE INTERSECTION AND LAST TWO ARE STRAIGHT CURVES
        //Now, x-value is that of intersection line and y-value is this x value subbed into (x*gradient + intercept) This gives 2 intersection points for each curve
        //Third intersection is between both curves
        System.out.println(intersectionLinesLL.toString());
        System.out.println(straightCurvesLL.toString());

        //COMPARE STRAIGHT CURVES
        for (int i = 0; i < straightCurvesLL.size() -1; i++) {
            for (int j = straightCurvesLL.size() -1; j > i; j--) {
                String coordinates = findIntersectionCoordinates(straightCurvesLL.get(i), straightCurvesLL.get(j));
                if (coordinates != null) {
                    vertices.add(coordinates);
                }
            }
        }
        for (int i = 0; i < intersectionLinesLL.size() -1; i++) {
            for (int j = intersectionLinesLL.size() -1; j > i; j--) {
                String coordinates = findIntersectionCoordinates(intersectionLinesLL.get(i), intersectionLinesLL.get(j));
                if (coordinates != null) {
                    vertices.add(coordinates);
                }
            }
        }

        for (int i = 0; i < intersectionLinesLL.size(); i++) {
            for (int j = 0; j < straightCurvesLL.size(); j++) {
                String coordinates = findIntersectionCoordinates(intersectionLinesLL.get(i), straightCurvesLL.get(j));
                vertices.add(coordinates);
            }
        }
        System.out.println(vertices.toString());

        String[] splitCoordinatesInString;
        List<Double> splitCoordinatesLL = new LinkedList<>();
        for (int i = 0; i < vertices.size(); i++) {
            splitCoordinatesInString = vertices.get(i).split(";");
            splitCoordinatesLL.add(Double.parseDouble(splitCoordinatesInString[0]));
            splitCoordinatesLL.add(Double.parseDouble(splitCoordinatesInString[1]));
        }

        Double[] splitCoordinates = new Double[splitCoordinatesLL.size()];
        for (int i = 0; i < splitCoordinatesLL.size(); i++) {
            splitCoordinates[i] = splitCoordinatesLL.get(i);
        }
        return splitCoordinates;
    }

    private String findIntersectionCoordinates(Line line, StraightCurve straightCurve) {
        if (line.getStartX() == line.getEndX()) {
            //then vertical;
            double y = straightCurve.getGradient()*line.getStartX() + straightCurve.getyIntercept();
            return line.getStartX() + ";" + y;
        }
        else {
            double x = (line.getStartY()-straightCurve.getyIntercept())/straightCurve.getGradient();
            return x  + ";" + line.getStartY();
        }

    }

    private String findIntersectionCoordinates(Line line1, Line line2) {
        if (line1.getStartX() == line1.getEndX() && line2.getStartY() == line2.getEndY()) {
            return line1.getStartX() + ";" + line2.getStartY();
        }
        else if (line1.getStartY() == line1.getEndY() && line2.getStartX() == line2.getEndX()) {
            return line2.getStartX() + ";" + line1.getStartY();
        }
        else {
            return null;
        }
    }

    private String findIntersectionCoordinates(StraightCurve curve1, StraightCurve curve2) {
        double newGradient = curve1.getGradient();
        double newYIntercept = curve1.getyIntercept();
        double comparedGradient = curve2.getGradient();
        double comparedYIntercept = curve2.getyIntercept();

        double x = 0;
        double y = 0;

        if (newGradient != comparedGradient && newGradient != Infinity && comparedGradient != Infinity) {
            x = (comparedYIntercept - newYIntercept) / (newGradient - comparedGradient);
            y = newGradient * x + newYIntercept;
        }
        else if (Math.abs(newGradient) == Infinity) {
            x = curve1.getCentreX();
            y = comparedGradient * x + comparedYIntercept;
        }
        else if (Math.abs(comparedGradient) == Infinity){
            x = curve2.getCentreX();
            y = newGradient * x + newYIntercept;
        }

        if (newGradient == comparedGradient) {
            return null;
        }
        else {
            return x + ";" + y;
        }
    }

    //TODO: Make prettier :)
    private void useNamesToFindCorrespondingIntersectionLinesAndCurves(String[] shadedRegionInputs, List<Line> intersectionsFoundLL, List<StraightCurve> straightCurvesFoundLL) {
        List<Line> linesFound = new LinkedList<>();
        for (int i = 0; i < shadedRegionInputs.length; i++) {
            System.out.println(shadedRegionInputs[i]);
            String labelType = "";
            if (shadedRegionInputs[i].contains("Q")) {
                labelType = "Q";
                for (int j = 0; j < Context.getInstance().getIntersectionLL().size(); j++) {
                    if ((labelType + Context.getInstance().getIntersectionLL().get(j).getId()).equals(shadedRegionInputs[i])) {
                        //this means curve had been found
                        linesFound.add(Context.getInstance().getIntersectionLL().get(j).getVerticalLine());
                    }
                }
            }
            else if(shadedRegionInputs[i].contains("P")) {
                labelType = "P";
                for (int j = 0; j < Context.getInstance().getIntersectionLL().size(); j++) {
                    if ((labelType + Context.getInstance().getIntersectionLL().get(j).getId()).equals(shadedRegionInputs[i])) {
                        //this means curve had been found
                        linesFound.add(Context.getInstance().getIntersectionLL().get(j).getHorizontalLine());
                    }
                }
            }
            else if (shadedRegionInputs[i].contains("D")) {
                labelType = "D";
                System.out.println("labelType = \"D\";");
            }
            else if (shadedRegionInputs[i].contains("S")) {
                labelType = "S";
                System.out.println("labelType = \"S\";");
            }
            if (labelType.equals("D") || labelType.equals("S")) {
                System.out.println(Context.getInstance().getCurvesLL().toString());
                for (int j = 0; j < Context.getInstance().getCurvesLL().size(); j++) {
                    System.out.println("name : " + Context.getInstance().getCurvesLL().get(j).getName() + " vs. Input : " + shadedRegionInputs[i]);
                    if (Context.getInstance().getCurvesLL().get(j).getName().equals(shadedRegionInputs[i])) {
                        //this means curve had been found
                        linesFound.add(Context.getInstance().getCurvesLL().get(j));
                    }
                }
            }

        }
        for (int i = 0 ; i < linesFound.size(); i++) {
            try {
                StraightCurve straightCurve = (StraightCurve) linesFound.get(i);
                straightCurvesFoundLL.add(straightCurve);
            }
            catch (ClassCastException e) {
                //therefore it is an intersection
                intersectionsFoundLL.add(linesFound.get(i));
            }
        }
    }

    //TODO: Validate combobox so that only vertical lines are picked vice versa
    public void addAllComboBoxValues() {
        addComboBoxValue(shadedRegionFromCB, Context.getInstance().getIntersectionLL().size(), false);
        addComboBoxValue(shadedRegionToCB, Context.getInstance().getIntersectionLL().size(), false);
        addComboBoxValue(shadedRegionBetween1CB, Context.getInstance().getCurvesLL().size(), true);
        addComboBoxValue(shadedRegionBetween2CB, Context.getInstance().getCurvesLL().size(), true);
    }

    public void addComboBoxValue(ComboBox comboBox, int listSize, boolean curve) {
        List<String> rawList = new LinkedList<>();
        for (int i = 0; i < listSize; i++) {
            if (curve) {
                rawList.add(Context.getInstance().getCurvesLL().get(i).getLabel().getText());
            }
            else {
                rawList.add("P" + Context.getInstance().getIntersectionLL().get(i).getId());
                rawList.add("Q" + Context.getInstance().getIntersectionLL().get(i).getId());
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
        addAllComboBoxValues();
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
