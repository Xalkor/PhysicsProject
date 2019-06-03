package sample;

public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector() {
        this(0,0);
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector copy() {
        return new Vector(x,y);
    }

    public static Vector fromAngle(double theta, double radius) {
        return new Vector(radius * Math.cos(theta),radius * Math.sin(theta));
    }

    public static Vector random() {
        return Vector.fromAngle(Math.random()*2*Math.PI, 1);
    }

    public static Vector add(Vector v1, Vector v2) {
        return new Vector(v1.x + v2.x, v1.y + v2.y);
    }

    public static Vector sub(Vector v1, Vector v2) {
        return new Vector(v1.x - v2.x, v1.y - v2.y);
    }

    public static Vector mult(Vector v, double n) {
        return new Vector(v.x * n, v.y * n);
    }

    public static Vector div(Vector v, double n) {
        return new Vector(v.x / n, v.y / n);
    }

    public static Vector periodicSub(Vector v1, Vector v2, double w, double h){
        Vector dv = Vector.sub(v1, v2);

        dv.x = dv.x - Math.round(dv.x / w) * w;
        dv.y = dv.y - Math.round(dv.y / h) * h;

        return dv;
    }

    public void add(Vector other) {
        this.set(x+other.x, y+other.y);
    }

    public void sub(Vector other) {
        this.set(x-other.x, y-other.y);
    }

    public void mult(double n) {
        this.set(x*n, y*n);
    }

    public void div(double n) {
        this.set(x/n, y/n);
    }

    public double heading() {
        return Math.atan2(y, x);
    }

    public double mag() {
        return Math.sqrt(magSq());
    }

    public double magSq() {
        return (x*x) + (y*y);
    }

    public void normalize() {
        this.set(Vector.fromAngle(heading(), 1));
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

    @Override
    public String toString() {
        return "<"+x+","+y+">";
    }

    public void limit(int n) {
        if(magSq() > n*n) {
            setMag(n);
        }
    }

    private void setMag(int n) {
        this.set(Vector.fromAngle(this.heading(), n));
    }

    public static Vector normalize(Vector vel) {
        return Vector.fromAngle(vel.heading(), 1);
    }



}
