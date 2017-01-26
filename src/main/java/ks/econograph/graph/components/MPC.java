package ks.econograph.graph.components;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 * Created by KSarm on 26/01/2017.
 */
public class MPC extends StraightCurve {
    public MPC(Pane pane, int curveIndex){
        setLine(new Line(200,400,550,50));
        getLine().setId("line" + curveIndex);
        if (curveIndex == 0)
            setName("MPC");
        else
            setName("MPC" + curveIndex);
        setCurveType("MPC"); //never changes
        setCentreX(375);
        setElasticityGap(175);
        Label label = new Label(getName());
        setLabel(label);
        label.setTranslateX(getCentreX() + getElasticityGap() + 15);
        label.setTranslateY(50);
        setColour("Black");
        setThickness(3);    getLine().setStrokeWidth(3);
        setDotted("Bold");
        setStrokeLineCap(StrokeLineCap.ROUND);
        calculateAndSetGradientAndYIntercept();
        pane.getChildren().addAll(getLine(), label);
    }

    public MPC(Pane pane, int curveIndex, String name, int centreX, int elasticityGap, String colour, int thickness, String dotted) {
        setLine(new Line(centreX - elasticityGap,400, centreX + elasticityGap,50));
        getLine().setId("line" + curveIndex);
        setName(name);
        setCurveType("MPC"); //never changes
        setCentreX(centreX);
        setElasticityGap(elasticityGap);
        Label label = new Label(getName());
        setLabel(label);
        label.setTranslateX(getCentreX() + getElasticityGap() + 15);
        label.setTranslateY(50);
        setColour(colour);      getLine().setStroke(Paint.valueOf(colour));
        setThickness(thickness);    getLine().setStrokeWidth(thickness);
        setDotted(dotted);
        setLineDottedSettings();
        setStrokeLineCap(StrokeLineCap.ROUND);
        calculateAndSetGradientAndYIntercept();
        pane.getChildren().addAll(getLine(), label);
    }
}
