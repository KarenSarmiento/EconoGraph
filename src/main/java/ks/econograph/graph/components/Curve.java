package ks.econograph.graph.components;

import javafx.scene.control.Label;
import javafx.scene.shape.Line;

/**
 * Created by KSarm on 03/11/2016.
 */
public class Curve extends Line{
    private String name;
    private String curveType;
    private int centreX;
    private int elasticityGap;
    private String colour;
    private int thickness;
    private Label label;
    private String dotted;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurveType() {
        return curveType;
    }

    public void setCurveType(String curveType) {
        this.curveType = curveType;
    }

    public int getCentreX() {
        return centreX;
    }

    public void setCentreX(int centreX) {
        this.centreX = centreX;
    }

    public int getElasticityGap() {
        return elasticityGap;
    }

    public void setElasticityGap(int elasticityGap) {
        this.elasticityGap = elasticityGap;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getDotted() {
        return dotted;
    }

    public void setDotted(String dotted) {
        this.dotted = dotted;
    }
}
