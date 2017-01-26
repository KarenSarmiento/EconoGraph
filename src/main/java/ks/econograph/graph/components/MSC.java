package ks.econograph.graph.components;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 * Created by KSarm on 26/01/2017.
 */
public class MSC extends StraightCurve{
    public MSC(Pane pane, int curveIndex){
        setLine(new Line(200,400,550,50));
        getLine().setId("line" + curveIndex);
        if (curveIndex == 0)
            setName("MSC");
        else
            setName("MSC" + curveIndex);
        setCurveType("MSC"); //never changes
        setCentreX(375);
        setElasticityGap(175);
        Label label = new Label(getName());
        setLabel(label);
        label.setTranslateX(getCentreX() + getElasticityGap() + 15);
        label.setTranslateY(50);
        setColour("Black");
        setThickness(3);    getLine().setStrokeWidth(3);
        setDotted(false);
        setStrokeLineCap(StrokeLineCap.ROUND);
        calculateAndSetGradientAndYIntercept();
        pane.getChildren().addAll(getLine(), label);
    }

    public MSC(Pane pane, int curveIndex, String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        setLine(new Line(centreX - elasticityGap,400, centreX + elasticityGap,50));
        getLine().setId("line" + curveIndex);
        setName(name);
        setCurveType("MSC"); //never changes
        setCentreX(centreX);
        setElasticityGap(elasticityGap);
        Label label = new Label(getName());
        setLabel(label);
        label.setTranslateX(getCentreX() + getElasticityGap() + 15);
        label.setTranslateY(50);
        setColour(colour);      getLine().setStroke(Paint.valueOf(colour));
        setThickness(thickness);    getLine().setStrokeWidth(thickness);
        setDotted(dotted);
        setStrokeLineCap(StrokeLineCap.ROUND);
        calculateAndSetGradientAndYIntercept();
        pane.getChildren().addAll(getLine(), label);
    }
}
