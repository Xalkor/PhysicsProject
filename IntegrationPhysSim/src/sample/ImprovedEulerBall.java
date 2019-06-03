package sample;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ArrayList;

import static javafx.scene.paint.Color.rgb;

public class ImprovedEulerBall extends PhysicsObject{
    private ArrayList<Line> path;

    public ImprovedEulerBall(double x, double y, double m, double r, Image sprite, Pane world) {
        super(x, y, m, r, sprite, world);
        path = new ArrayList<>();
    }

    @Override
    public void update(double dt) {
        if(getY() <= 1) {
            Vector pPos = transform(getPos());
            Vector pvel = getVel().copy();
            getVel().add(Vector.mult(getAcc(), dt));
            Vector avgVel = Vector.div(Vector.add(pvel, getVel()), 2);
            getPos().add(Vector.mult(avgVel, dt));
            updateSprite();
            setAcc(new Vector(0, 0));

            Vector tPos = transform(getPos());
            Line l = new Line(pPos.getX(), pPos.getY(), tPos.getX(), tPos.getY());
            l.setStrokeWidth(2);
            l.setStrokeDashOffset(4);
            l.setStroke(rgb(164, 71, 200));
            getWorld().getChildren().add(l);
            path.add(l);
            System.out.println(l);
        }else{
            setVel(new Vector(0,0));
        }
    }

    @Override
    public void remove(){
        super.remove();
        for (Line line : path) {
            getWorld().getChildren().remove(line);
        }
    }
}
