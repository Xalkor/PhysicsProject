package sample;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ArrayList;

import static javafx.scene.paint.Color.rgb;

public class BackwardEulerBall extends PhysicsObject{
    private ArrayList<Line> path;

    public BackwardEulerBall(double x, double y, double m, double r, Image sprite, Pane world) {
        super(x, y, m, r, sprite, world);
        path = new ArrayList<>();
    }

    @Override
    public void update(double dt) {
        if(getY() <= 1) {
            Vector pPos = transform(getPos());
            getVel().add(Vector.mult(getAcc(), dt));
            getPos().add(Vector.mult(getVel(), dt));
            updateSprite();
            setAcc(new Vector(0, 0));

            Vector tPos = transform(getPos());
            Line l = new Line(pPos.getX(), pPos.getY(), tPos.getX(), tPos.getY());
            l.setStrokeWidth(2);
            l.setStrokeDashOffset(4);
            l.setStroke(rgb(200,169, 69));
            getWorld().getChildren().add(l);
            path.add(l);
            //System.out.println(l);
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
