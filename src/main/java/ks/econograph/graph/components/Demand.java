package ks.econograph.graph.components;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 * Created by KSarm on 03/11/2016.
 */
public class Demand extends StraightCurve {

    public Demand(Pane pane, int curveIndex){
        setLine(new Line(200,50,550,400));
        getLine().setId("line" + curveIndex);
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
        setThickness(3);    getLine().setStrokeWidth(3);
        setDotted("Bold");
        setStrokeLineCap(StrokeLineCap.ROUND);
        calculateAndSetGradientAndYIntercept();
        pane.getChildren().addAll(getLine(), label);
    }

    public Demand(Pane pane, int index, String name, int centreX, int elasticityGap, String colour, int thickness, String dotted) {
        setLine(new Line(centreX - elasticityGap,50,centreX + elasticityGap,400));
        getLine().setId("line" + index);
        setName(name);
        setCurveType("Demand"); //never changes
        setCentreX(centreX);
        setElasticityGap(elasticityGap);
        Label label = new Label(getName());
        label.setTranslateX(getCentreX() + getElasticityGap() + 15);
        label.setTranslateY(400);
        setLabel(label);
        setColour(colour);      getLine().setStroke(Paint.valueOf(colour));
        setThickness(thickness);    getLine().setStrokeWidth(thickness);
        setDotted(dotted);
        setLineDottedSettings();
        setStrokeLineCap(StrokeLineCap.ROUND);
        calculateAndSetGradientAndYIntercept();
        pane.getChildren().addAll(getLine(), label);
    }

}
