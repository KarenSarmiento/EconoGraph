package ks.econograph.graph.components;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import ks.econograph.Context;

import static jdk.nashorn.internal.objects.Global.Infinity;

/**
 * Created by KSarm on 22/01/2017.
 */
public class Intersection {
    private Line verticalLine;
    private Line horizontalLine;
    private Label xAxisLabel;
    private Label yAxisLabel;
    private double x;
    private double y;
    private String id;
    private boolean visible;

    public Intersection(StraightCurve curve1, StraightCurve curve2, Pane workspace) {
        findIntersectionCoordinates(curve1, curve2);
        if (x >= 87 && x <= 700 && y <= 425 && y >= 40) {
            System.out.println("x = " + x + ", y = " + y);
            id = generateIntersectionId();
            verticalLine = generateIntersectionLine(x, y, true);
            horizontalLine = generateIntersectionLine(x, y, false);
            xAxisLabel = generateIntersectionLabel(x, y, "Q" + id, true);
            yAxisLabel = generateIntersectionLabel(x, y, "P" + id, false);
            System.out.println(toString());
            workspace.getChildren().addAll(verticalLine, horizontalLine, yAxisLabel, xAxisLabel);
        }
    }
    private Line generateIntersectionLine(double x, double y, boolean vertical) {
        Line line;
        if (vertical) {
            line = new Line(x, y, x, 425);
        }
        else {
            line = new Line(x, y, 87, y);
        }
        line.getStrokeDashArray().addAll(10d, 5d);
        return line;
    }

    private Label generateIntersectionLabel(double x, double y, String labelText, boolean vertical) {
        Label label = new Label(labelText);
        if (vertical) {
            label.setTranslateX(x);
            label.setTranslateY(435);
        }
        else {
            label.setTranslateX(60);
            label.setTranslateY(y);
        }
        return label;
    }

    private String generateIntersectionId() {
        String id;
        if (Context.getInstance().getIntersectionLL().size() == 0)
            id = "";
        else
            id = "" + Context.getInstance().getIntersectionLL().size();

        return id;
    }

    private void findIntersectionCoordinates(StraightCurve curve1, StraightCurve curve2) {
        double newGradient = curve1.getGradient();
        double newYIntercept = curve1.getyIntercept();
        double comparedGradient = curve2.getGradient();
        double comparedYIntercept = curve2.getyIntercept();

        x = 0;
        y = 0;

        if (Math.abs(newGradient) == Infinity) {
            x = curve1.getCentreX();
            y = comparedGradient * x + comparedYIntercept;
        }
        else if (Math.abs(comparedGradient) == Infinity){
            x = curve2.getCentreX();
            y = newGradient * x + newYIntercept;
        }
        else if (newGradient != comparedGradient && newGradient != Infinity && comparedGradient != Infinity) {
            x = (comparedYIntercept - newYIntercept) / (newGradient - comparedGradient);
            y = newGradient * x + newYIntercept;
        }
    }

    public boolean isNull() {
        if (verticalLine == null && horizontalLine == null && xAxisLabel == null && yAxisLabel == null)
            return true;
        else
            return false;
    }

    public Line getVerticalLine() {
        return verticalLine;
    }

    public void setVerticalLine(Line verticalLine) {
        this.verticalLine = verticalLine;
    }

    public Line getHorizontalLine() {
        return horizontalLine;
    }

    public void setHorizontalLine(Line horizontalLine) {
        this.horizontalLine = horizontalLine;
    }

    public Label getxAxisLabel() {
        return xAxisLabel;
    }

    public void setxAxisLabel(Label xAxisLabel) {
        this.xAxisLabel = xAxisLabel;
    }

    public Label getyAxisLabel() {
        return yAxisLabel;
    }

    public void setyAxisLabel(Label yAxisLabel) {
        this.yAxisLabel = yAxisLabel;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        if (visible) {
            setVisibleIfNotNull(verticalLine, true);
            setVisibleIfNotNull(horizontalLine, true);
            setVisibleIfNotNull(xAxisLabel, true);
            setVisibleIfNotNull(yAxisLabel, true);
        }
        else {
            setVisibleIfNotNull(verticalLine, false);
            setVisibleIfNotNull(horizontalLine, false);
            setVisibleIfNotNull(xAxisLabel, false);
            setVisibleIfNotNull(yAxisLabel, false);
        }
    }

    private void setVisibleIfNotNull(Node node, boolean visible) {
        if (node != null)
            node.setVisible(visible);
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "verticalLine=" + verticalLine +
                ", horizontalLine=" + horizontalLine +
                ", xAxisLabel=" + xAxisLabel +
                ", yAxisLabel=" + yAxisLabel +
                ", x=" + x +
                ", y=" + y +
                ", id='" + id + '\'' +
                ", visible=" + visible +
                '}';
    }
}
