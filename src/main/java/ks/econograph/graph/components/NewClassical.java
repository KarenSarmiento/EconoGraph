package ks.econograph.graph.components;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class NewClassical extends StraightCurve{
    public NewClassical(Pane pane, int curveIndex){
        setLine(new Line(375,50,375,400));
        getLine().setId("line" + curveIndex);
        if (curveIndex == 0)
            setName("LRAS");
        else
            setName("LRAS" + curveIndex);
        setCurveType("NewClassical"); //never changes
        setCentreX(375);
        setElasticityGap(0);
        Label label = new Label(getName());
        label.setTranslateX(getCentreX() + 15);
        label.setTranslateY(50);
        setLabel(label);
        setColour("Black");
        setThickness(3);    getLine().setStrokeWidth(3);
        setDotted("bold");
        setStrokeLineCap(StrokeLineCap.ROUND);
        calculateAndSetGradientAndYIntercept();
        pane.getChildren().addAll(getLine(), label);
    }

    public NewClassical(Pane pane, int index, String name, int centreX, int elasticityGap, String colour, int thickness, String dotted) {
        setLine(new Line(centreX,50, centreX,400));
        getLine().setId("line" + index);
        setName(name);
        setCurveType("NewClassical"); //never changes
        setCentreX(centreX);
        setElasticityGap(0);
        Label label = new Label(getName());
        label.setTranslateX(getCentreX() + 15);
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
