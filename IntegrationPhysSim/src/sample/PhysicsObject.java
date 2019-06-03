package sample;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public abstract class PhysicsObject {
    private Vector pos;
    private Vector vel;
    private Vector acc;
    private ImageView sprite;
    private double m;
    private double r;
    private Pane world;

    public PhysicsObject(double x, double y, double m, double r, Image sprite, Pane world){
        this.pos = new Vector(x,y);
        this.vel = new Vector(0,0);
        this.acc = new Vector(0,0);
        this.sprite = new ImageView();
        this.sprite.setImage(sprite);
        this.sprite.setFitWidth(r*2);
        this.sprite.setFitHeight(r*2);
        world.getChildren().add(this.sprite);
        this.m = m;
        this.r = r;
        this.world = world;
        updateSprite();
    }

    public abstract void update(double dt);

    public void applyForce(Vector force){
        this.acc.add(Vector.div(force, m));
    }

    public Vector getVel() {
        return vel;
    }

    public void setVel(Vector vel) {
        this.vel = vel;
    }

    public Vector getPos() {
        return pos;
    }

    public void setPos(Vector pos) {
        this.pos = pos;
    }

    public Vector getAcc() {
        return acc;
    }

    public void setAcc(Vector acc) {
        this.acc = acc;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setSprite(Image sprite) {
        this.sprite.setImage(sprite);
    }

    public Pane getWorld() {
        return world;
    }

    public double getX(){
        return pos.getX();
    }

    public double getY(){
        return pos.getY();
    }

    public boolean isMoving(){
        return vel.mag() > .00001;
    }

    protected ImageView getSprite() {
        return sprite;
    }

    public void updateSprite(){
        Vector tPos = transform(getPos());
        this.sprite.setX(tPos.getX()-r);
        this.sprite.setY(tPos.getY()-r);
    }

    public void remove() {
        world.getChildren().remove(this.getSprite());
    }

    public Vector transform(Vector v){
        return new Vector(v.getX() * world.getWidth(), world.getHeight()-((1-v.getY()) * world.getWidth()));
    }
}
