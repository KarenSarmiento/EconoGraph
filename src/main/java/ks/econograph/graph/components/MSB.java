package ks.econograph.graph.components;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 * Created by KSarm on 26/01/2017.
 */
public class MSB extends StraightCurve{

    public MSB(Pane pane, int curveIndex) {
        setLine(new Line(200, 50, 550, 400));
        getLine().setId("line" + curveIndex);
        if (curveIndex == 0)
            setName("MSB");
        else
            setName("MSB" + curveIndex);
        setCurveType("MSB"); //never changes
        setCentreX(375);
        setElasticityGap(175);
        Label label = new Label(getName());
        label.setTranslateX(getCentreX() + getElasticityGap() + 15);
        label.setTranslateY(400);
        setLabel(label);
        setColour("Black");
        setThickness(3);
        getLine().setStrokeWidth(3);
        setDotted(false);
        setStrokeLineCap(StrokeLineCap.ROUND);
        calculateAndSetGradientAndYIntercept();
        pane.getChildren().addAll(getLine(), label);
    }

    public MSB(Pane pane, int index, String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        setLine(new Line(centreX - elasticityGap, 50, centreX + elasticityGap, 400));
        getLine().setId("line" + index);
        setName(name);
        setCurveType("MSB"); //never changes
        setCentreX(centreX);
        setElasticityGap(elasticityGap);
        Label label = new Label(getName());
        label.setTranslateX(getCentreX() + getElasticityGap() + 15);
        label.setTranslateY(400);
        setLabel(label);
        setColour(colour);
        getLine().setStroke(Paint.valueOf(colour));
        setThickness(thickness);
        getLine().setStrokeWidth(thickness);
        setDotted(dotted);
        setStrokeLineCap(StrokeLineCap.ROUND);
        calculateAndSetGradientAndYIntercept();
        pane.getChildren().addAll(getLine(), label);
    }

}
