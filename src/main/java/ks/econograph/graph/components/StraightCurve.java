package ks.econograph.graph.components;

import javafx.scene.shape.Line;

/**
 * Created by KSarm on 17/01/2017.
 */
public class StraightCurve extends Curve {
    private Line line;
    private double gradient;
    private double yIntercept;

    public void calculateAndSetGradientAndYIntercept() {
        gradient = (line.getStartY() - line.getEndY())/(line.getStartX() - line.getEndX());
        yIntercept = line.getStartY() - gradient * line.getStartX();
    }

    public void setLineDottedSettings() {
        switch (getDotted()) {
            case "Bold": {
                line.getStrokeDashArray().clear();
                break;
            }
            case "Dotted": {
                line.getStrokeDashArray().clear();
                line.getStrokeDashArray().addAll(5d);
                break;
            }
            case "Dashed": {
                line.getStrokeDashArray().clear();
                line.getStrokeDashArray().addAll(20d, 10d);
                break;
            }
        }
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public double getGradient() {
        return gradient;
    }

    public void setGradient(double gradient) {
        this.gradient = gradient;
    }

    public double getyIntercept() {
        return yIntercept;
    }

    public void setyIntercept(double yIntercept) {
        this.yIntercept = yIntercept;
    }

    @Override
    public String toString() {
        return "StraightCurve{" +
                "line=" + line +
                ", gradient=" + gradient +
                ", yIntercept=" + yIntercept +
                '}';
    }
}
