package ks.econograph.graph.components;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import ks.econograph.Context;

import java.util.LinkedList;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.Infinity;

public class ShadedRegion {
    private Polygon polygon;
    private Label label;
    private double labelPosX;
    private double labelPosY;
    private boolean visible;

    public ShadedRegion(String labelText, String colour, String lineName1, String lineName2, String lineName3, String lineName4, Pane pane, FlowPane flowPane) {
        System.out.println("SHADED REGION CONSTRUCTOR CALLED");
        Double[] coordinates = calculateShadedRegionVertices(lineName1, lineName2, lineName3, lineName4);
        polygon = new Polygon();
        polygon.getPoints().addAll(coordinates);
        polygon.setFill(Paint.valueOf(colour));
        calculateLabelPosition(coordinates);
        printCoordinate(coordinates);
        label = new Label(labelText);
        System.out.println("LabelX=" + labelPosX + "  LabelY=" + labelPosY);
        label.setTranslateX(labelPosX);
        label.setTranslateY(labelPosY);
        visible = true;
        pane.getChildren().addAll(polygon, label);
        label.toFront();
        createShadedRegionRadio(labelText, flowPane);
    }

    private void printCoordinate(Double[] coordinate) {
        for (int i = 0; i < coordinate.length; i+=2) {
            System.out.println("At " + i + ", coordinate = " + coordinate[i] + "," + coordinate[i+1]);
        }
    }

    private void createShadedRegionRadio(String labelText, FlowPane flowPane) {
        RadioButton radioButton = new RadioButton(labelText + " Region");
        radioButton.setToggleGroup(Context.getInstance().getGraphMakerInsertedShadedRegionsTG());
        radioButton.setOnAction(e -> Context.getInstance().setSelectedShadedRegionIndex(Context.getInstance().getShadedRegionsLL().size()));
        flowPane.getChildren().add(radioButton);
        radioButton.setSelected(true);
        Context.getInstance().setSelectedShadedRegionIndex(Context.getInstance().getShadedRegionsLL().size());
    }

    private void calculateLabelPosition(Double[] coordinates) {
        double maxX = coordinates[0];
        double maxY = coordinates[1];
        double minX = coordinates[0];
        double minY = coordinates[1];
        for (int i = 0; i < coordinates.length; i++) {
            if (i%2 == 0) {
                if (maxX < coordinates[i])
                    maxX = coordinates[i];
                if (minX > coordinates[i])
                    minX = coordinates[i];
            }
            else {
                if (maxY < coordinates[i])
                    maxY = coordinates[i];
                if (minY > coordinates[i])
                    minY = coordinates[i];
            }
        }
        labelPosX = ((maxX + minX)/2)-20;
        labelPosY = ((maxY + minY)/2)-10;
    }

    private Double[] calculateShadedRegionVertices(String lineName1, String lineName2, String lineName3, String lineName4) {
        List<String> vertices = new LinkedList<>();
        String[] shadedRegionInputs = {lineName1, lineName2, lineName3, lineName4};
        List<Line> intersectionLinesLL = new LinkedList<>();
        List<StraightCurve> straightCurvesLL = new LinkedList<>();
        useNamesToFindCorrespondingIntersectionLinesAndCurves(shadedRegionInputs, intersectionLinesLL, straightCurvesLL);
        System.out.println("line LL: " + intersectionLinesLL.toString());
        System.out.println("straightCurveLL: " + straightCurvesLL.toString());

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

        return generateDoubleArrayContainingCoordinatesFromStringLL(vertices);
    }

    private Double[] generateDoubleArrayContainingCoordinatesFromStringLL(List<String> vertices) {
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

    private void useNamesToFindCorrespondingIntersectionLinesAndCurves(String[] shadedRegionInputs, List<Line> intersectionsFoundLL, List<StraightCurve> straightCurvesFoundLL) {
        List<Line> linesFound = new LinkedList<>();
        for (int i = 0; i < shadedRegionInputs.length; i++) {
            if (shadedRegionInputs[i].contains(Context.getInstance().getxIntersectionLabel())) {
                for (int j = 0; j < Context.getInstance().getIntersectionLL().size(); j++) {
                    if ((Context.getInstance().getxIntersectionLabel() + Context.getInstance().getIntersectionLL().get(j).getId()).equals(shadedRegionInputs[i])) {
                        linesFound.add(Context.getInstance().getIntersectionLL().get(j).getVerticalLine());
                    }
                }
            }
            else if(shadedRegionInputs[i].contains(Context.getInstance().getyIntersectionLabel())) {
                for (int j = 0; j < Context.getInstance().getIntersectionLL().size(); j++) {
                    if ((Context.getInstance().getxIntersectionLabel() + Context.getInstance().getIntersectionLL().get(j).getId()).equals(shadedRegionInputs[i])) {
                        linesFound.add(Context.getInstance().getIntersectionLL().get(j).getHorizontalLine());
                    }
                }
            }
            if (shadedRegionInputs[i].contains("S") ||shadedRegionInputs[i].contains("D") ||shadedRegionInputs[i].contains("MSB") ||
                    shadedRegionInputs[i].contains("MPC") ||shadedRegionInputs[i].contains("MPB") ||shadedRegionInputs[i].contains("MSC") ||
                    shadedRegionInputs[i].contains("AS") ||shadedRegionInputs[i].contains("AD") ||shadedRegionInputs[i].contains("LRAS") ||
                    shadedRegionInputs[i].contains("LF") ||shadedRegionInputs[i].contains("ASL")){
                for (int j = 0; j < Context.getInstance().getCurvesLL().size(); j++) {
                    if (Context.getInstance().getCurvesLL().get(j).getName().equals(shadedRegionInputs[i])) {
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
                intersectionsFoundLL.add(linesFound.get(i));
            }
        }
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        if (visible) {
            polygon.setVisible(true);
            label.setVisible(true);
        }
        else {
            polygon.setVisible(false);
            label.setVisible(false);
        }
    }

    @Override
    public String toString() {
        return "ShadedRegion{" +
                "polygon=" + polygon +
                ", label=" + label +
                ", labelPosX=" + labelPosX +
                ", labelPosY=" + labelPosY +
                ", visible=" + visible +
                '}';
    }
}
