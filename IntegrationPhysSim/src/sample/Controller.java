package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import static java.lang.Math.random;
import static java.lang.Math.toRadians;
import static javafx.scene.paint.Color.*;

public class Controller {
    public static final Vector GRAVITY = new Vector(0, .08);

    public PhysicsObject ball;
    public ImageView arrowView;
    public Image arrow;
    public boolean started = false;
    public Image ballSprite;
    public Circle[] path;
    public int frameCount = 0;

    @FXML
    public AnchorPane anchorPane;
    public Pane canvas;
    public AnchorPane ground;
    public AnchorPane sky;
    public Slider angleSlider;
    public Slider speedSlider;
    public ChoiceBox integratorChoiceBox;
    public Button launchButton;
    public Slider dtSlider;
    public CheckBox dragCheckBox;
    public Slider dragSlider;

    public void initialize() {
        dragCheckBox.setOnAction(event -> {
            dragCheckBox.setText("Drag: O" + (dragCheckBox.isSelected() ? "n": "ff"));
        });

        path = new Circle[10];
        for (int i = 0; i < path.length; i++) {
            path[i] = new Circle(4, WHITE);
            path[i].setLayoutX(random() * sky.getWidth());
            sky.getChildren().add(path[i]);
        }

        integratorChoiceBox.getItems().addAll("Euler", "Backward Euler", "Improved Euler", "Velocity Verlet");
        integratorChoiceBox.setValue("Euler");

        launchButton.setOnAction(e -> {
            if (!started) {
                //sky.getChildren().add(arrowView);

                ball.remove();

                switch((String)integratorChoiceBox.getValue()){
                    case "Euler":
                        ball = new EulerBall(.5, 1, 10, 10, ballSprite, sky);
                        break;
                    case "Backward Euler":
                        ball = new BackwardEulerBall(.5, 1, 10, 10, ballSprite, sky);
                        break;
                    case "Improved Euler":
                        ball = new ImprovedEulerBall(.5, 1, 10, 10, ballSprite, sky);
                        break;
                        default:
                            System.exit(-69);
                            break;
                }
                Vector vel = velFromSlider();
                ball.setVel(vel);
                //System.out.println(.05*speedSlider.getValue()/100.0);

                started = true;
            }
        });

        arrow = new Image("arrow.png");
        PixelReader pr = arrow.getPixelReader();
        WritableImage tmp = new WritableImage((int) arrow.getWidth(), (int) arrow.getHeight());
        PixelWriter pw = tmp.getPixelWriter();
        for (int i = 0; i < arrow.getWidth(); i++) {
            for (int j = 0; j < arrow.getHeight(); j++) {
                Color c = pr.getColor(i, j);
                if (c.equals(rgb(0, 255, 0))) {
                    pw.setColor(i, j, TRANSPARENT);
                } else {
                    pw.setColor(i, j, c);
                }
            }
        }
        arrow = tmp;
        arrowView = new ImageView(arrow);
        sky.getChildren().add(arrowView);
        arrowView.setFitWidth(50);
        arrowView.setFitHeight(40);

        ballSprite = new Image("soccer.png");
        ball = new EulerBall(.5, 1, 10, 10, ballSprite, sky);
        System.out.println(ball.getY());
        System.out.println(ground.getMaxHeight());
        AnimationTimer t = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (started) {
                    System.out.println("a");
                    arrowView.setImage(null);
                    if (!ball.isMoving() && ball.getPos().getY() >= 1) {
                        started = false;
                    }
                    if(frameCount%(int)(dtSlider.getValue()*10)==0) {
                        ball.applyForce(GRAVITY);
                        if(dragCheckBox.isSelected()){
                            ball.applyForce(Vector.mult(ball.getVel(), -dragSlider.getValue())); //f = mg-bv
                        }
                        ball.update(dtSlider.getValue());
                    }
                } else {
                    arrowView.setImage(arrow);
                    ball.setPos(new Vector(.5, 1));
                    ball.updateSprite();
                    plotKinematic();
                }
                arrowView.getTransforms().clear();
                arrowView.getTransforms().add(new Translate(sky.getWidth() / 2, sky.getHeight()));
                arrowView.getTransforms().add(new Rotate(angleSlider.getValue() - 90));
                arrowView.getTransforms().add(new Translate(arrowView.getFitWidth() / 2, -arrowView.getFitHeight() / 2));
                arrowView.setFitWidth(50 * speedSlider.getValue() / 100.0 + 25);

                frameCount++;
            }
        };
        t.start();
    }

    private Vector velFromSlider() {
        return Vector.fromAngle(toRadians(angleSlider.getValue() - 90), .06 * speedSlider.getValue() / 100.0);
    }

    private void plotKinematic() {
        final double g = (GRAVITY.getY() / ball.getM());
        Vector v = velFromSlider();
        double t = -2 * v.getY() / g;
        System.out.println(t);
        for (int n = 0; n < path.length; n++) {
            double i = n / (double) (path.length - 1) * t;
            double x = v.getX() * i + ball.getPos().getX();
            double y = v.getY() * i + g / 2 * i * i + ball.getPos().getY();
            path[n].setLayoutX(x * sky.getWidth());
            path[n].setLayoutY(sky.getHeight() - ((1 - y) * sky.getWidth()));
            System.out.println(x + " " + y);
//            gc.fillOval(x-4, y-4, 8, 8);
        }
    }
}