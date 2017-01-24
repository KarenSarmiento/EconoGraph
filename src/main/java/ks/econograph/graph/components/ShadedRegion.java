package ks.econograph.graph.components;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import ks.econograph.Context;

import java.util.LinkedList;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.Infinity;

/**
 * Created by KSarm on 21/01/2017.
 */
public class ShadedRegion {
    Polygon polygon;
    Label label;

    public ShadedRegion(String labelText, Pane pane, String lineName1, String lineName2, String lineName3, String lineName4) {
        Double[] coordinates = calculateShadedRegionVertices(lineName1, lineName2, lineName3, lineName4);
        polygon = new Polygon();
        polygon.getPoints().addAll(coordinates);
        //TODO: calculate position of label text by averaging max and min x and y coordinates of vertices
        label = new Label(labelText);
        pane.getChildren().addAll(polygon, label);
    }

    private Double[] calculateShadedRegionVertices(String lineName1, String lineName2, String lineName3, String lineName4) {
        List<String> vertices = new LinkedList<>();
        String[] shadedRegionInputs = {lineName1, lineName2, lineName3, lineName4};
        List<Line> intersectionLinesLL = new LinkedList<>();
        List<StraightCurve> straightCurvesLL = new LinkedList<>();
        useNamesToFindCorrespondingIntersectionLinesAndCurves(shadedRegionInputs, intersectionLinesLL, straightCurvesLL);

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

    @Override
    public String toString() {
        return "ShadedRegion{" +
                "polygon=" + polygon +
                ", label=" + label +
                '}';
    }
}
