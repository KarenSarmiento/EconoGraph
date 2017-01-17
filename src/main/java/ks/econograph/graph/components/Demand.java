package ks.econograph.graph.components;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 * Created by KSarm on 03/11/2016.
 */
public class Demand extends Curve {

    private Line line;

    public Demand(Pane pane, int curveIndex){
        this.line = new Line(200,50,550,400);
        this.line.setId("line" + curveIndex);
        if (curveIndex == 0)
            setName("D");
        else
            setName("D" + curveIndex);
        setCurveType("Demand"); //never changes
        setCentreX(375);
        setElasticityGap(175);
        Label label = new Label(getName());
        label.setTranslateX(getCentreX() + getElasticityGap() + 15);
        label.setTranslateY(400);
        setLabel(label);
        setColour("Black");
        setThickness(3);    this.line.setStrokeWidth(3);
        setDotted(false);
        setStrokeLineCap(StrokeLineCap.ROUND);
        pane.getChildren().addAll(line, label);
    }

    public Demand(Pane pane, int index, String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        this.line = new Line(centreX - elasticityGap,50,centreX + elasticityGap,400);
        this.line.setId("line" + index);
        setName(name);
        setCurveType("Demand"); //never changes
        setCentreX(centreX);
        setElasticityGap(elasticityGap);
        Label label = new Label(getName());
        label.setTranslateX(getCentreX() + getElasticityGap() + 15);
        label.setTranslateY(400);
        setColour(colour);      this.line.setStroke(Paint.valueOf(colour));
        setThickness(thickness);    this.line.setStrokeWidth(thickness);
        setDotted(dotted);
        setStrokeLineCap(StrokeLineCap.ROUND);
        pane.getChildren().addAll(line, label);
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "Demand{" +
                "line=" + line +
                '}';
    }
}
