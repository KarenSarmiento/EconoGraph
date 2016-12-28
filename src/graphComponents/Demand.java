package graphComponents;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

/**
 * Created by KSarm on 03/11/2016.
 */
public class Demand extends Curve {
    /*private String name;
    private int centreX;
    private int elasticityGap;
    private String colour;
    private int thickness;
//    private GraphLabel label;
    private boolean dotted;*/

    private Line line;

    public Demand(Pane pane){
        this.line = new Line(200,50,550,400);
        setName("D");
        setType("Demand"); //never changes
        setCentreX(375);
        setElasticityGap(175);
        setColour("Black");
        setThickness(3);    this.line.setStrokeWidth(3);
        setDotted(false);
        setStrokeLineCap(StrokeLineCap.ROUND);
        pane.getChildren().add(line);
    }

    public Demand(Pane pane, String name, int centreX, int elasticityGap, String colour, int thickness, boolean dotted) {
        this.line = new Line(centreX - elasticityGap,50,centreX + elasticityGap,400);
        setName(name);
        setType("Demand"); //never changes
        setCentreX(centreX);
        setElasticityGap(elasticityGap);
        setColour(colour);      this.line.setStroke(Paint.valueOf(colour));
        setThickness(thickness);    this.line.setStrokeWidth(thickness);
        setDotted(dotted);
        setStrokeLineCap(StrokeLineCap.ROUND);
        pane.getChildren().add(this.line);
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
