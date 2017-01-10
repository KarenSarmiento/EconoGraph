package ks.econograph.graph.components;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import ks.econograph.Context;

/**
 * Created by KSarm on 03/11/2016.
 */
public class Supply extends Curve {

    private Line line;

    public Supply(Pane pane, int curveIndex){
        this.line = new Line(200,400,550,50);
        this.line.setId("line" + curveIndex);
        setName("S");
        setType("Supply"); //never changes
        setCentreX(375);
        setElasticityGap(175);
        setColour("Black");
        setThickness(3);    this.line.setStrokeWidth(3);
        setDotted(false);
        setStrokeLineCap(StrokeLineCap.ROUND);
        pane.getChildren().add(line);
    }

    public Supply(Pane pane, int curveIndex, String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        this.line = new Line(centreX + elasticityGap,50,centreX - elasticityGap,400);
        this.line.setId("line" + curveIndex);
        setName(name);
        setType("Supply"); //never changes
        setCentreX(centreX);
        setElasticityGap(elasticityGap);
        setColour(colour);      this.line.setStroke(Paint.valueOf(colour));
        setThickness(thickness);    this.line.setStrokeWidth(thickness);
        setDotted(dotted);
        setStrokeLineCap(StrokeLineCap.ROUND);
        pane.getChildren().add(line);
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "Supply{" +
                "line=" + line +
                '}';
    }
}
