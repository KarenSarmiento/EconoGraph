package ks.econograph.graph.components;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;

/**
 * Created by KSarm on 21/01/2017.
 */
public class ShadedRegion {
    Polygon polygon;
    Label label;

    ShadedRegion(Double[] coordinates, String labelText, Pane pane) {
        polygon = new Polygon();
        polygon.getPoints().addAll(coordinates);
        //TODO: calculate position of label text by averaging max and min x and y coordinates of vertices
        label = new Label(labelText);
        pane.getChildren().addAll(polygon, label);
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
