package scroller_v1;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.transform.Rotate;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import simpleIO.*;
public class Scroller_v1 extends Application {
	
	
	static final double SCREEN_WIDTH = 800;
    static final double SCREEN_HEIGHT = 600;

    static final double MAX_SPEED = 5.0;
    static final double BALL_SIZE = 40.0;

    double dX = MAX_SPEED;
    double dY = MAX_SPEED;

    Rectangle square;

    @Override
    public void start(Stage myStage) throws Exception {

        square = new Rectangle(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, BALL_SIZE,BALL_SIZE);
        Group root = new Group(square);

        BounceTimer timer = new BounceTimer();
        timer.start();

        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

        myStage.setTitle("Bouncing Ball!");
        myStage.setScene(scene);
        myStage.show();
    }

    class BounceTimer extends AnimationTimer {

        @Override
        public void handle(long now) {
            // check boundary collision
            Bounds box = square.getBoundsInLocal();
            
            //check edges of screen
            if (box.getMinX() < 0 || box.getMaxX() > SCREEN_WIDTH)
                dX *= -1;
            if (box.getMinY() < 0 || box.getMaxY() > SCREEN_HEIGHT)
                dY *= -1;

            //reposition ball for the next frame
            square.setX(square.getX() + dX);
            square.setY(square.getY() + dY);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}