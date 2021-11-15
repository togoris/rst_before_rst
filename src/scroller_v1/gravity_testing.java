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


public class gravity_testing extends Application{
	// Constants for size & speeds of objects
    static final double SCREEN_WIDTH = 800;
    static final double SCREEN_HEIGHT = 600;

     final int BALL_SIZE = 20;
     final int INITAL_SPEED = 0;
     final int ANGLE = 65;
     final double GRAVITY = 1;

    double dX;
    double dY;

    Circle ball;
    
    Scene scene;

    @Override
    public void start(Stage myStage) throws Exception {

        ball = new Circle(BALL_SIZE, SCREEN_HEIGHT - BALL_SIZE, BALL_SIZE);
        
        //start ball moving to the right
        dX = INITAL_SPEED * Math.cos(Math.toRadians(ANGLE));
        
        //start ball moving up
        dY = - INITAL_SPEED * Math.sin(Math.toRadians(ANGLE));

        Group root = new Group(ball);

        scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        scene.setOnKeyPressed(event -> handleKeyPressed(event));
        scene.setOnKeyReleased(event -> handleKeyReleased(event));
        
        GravityTimer timer = new GravityTimer();
        timer.start();

        myStage.setTitle("Gravity");
        myStage.setScene(scene);
        myStage.show();
    }

    class GravityTimer extends AnimationTimer {

        @Override
        public void handle(long now) {

            // check boundary collision
            Bounds box = ball.getBoundsInLocal();

            // check edges of screen
            if (box.getMinX() < 0 || box.getMaxX() > scene.getWidth())
                dX *= 0;
            if (box.getMinY() < 0 || box.getMaxY() > scene.getHeight())
                dY *= 0; //reverse direction
            else
                dY += GRAVITY; //apply effect of gravity
               
            // update position
            ball.setCenterX(ball.getCenterX() + dX);
            ball.setCenterY(ball.getCenterY() + dY);

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    private void handleKeyPressed(KeyEvent event) {
		KeyCode code = event.getCode();
		if (code == KeyCode.LEFT || code == KeyCode.KP_LEFT) {
			dY = -5;}
		
    	if (code == KeyCode.RIGHT || code == KeyCode.KP_RIGHT) {
    		dY =  5;}
}  
	private void handleKeyReleased(KeyEvent event) {
		KeyCode code = event.getCode();
		if (code == KeyCode.LEFT || code == KeyCode.KP_LEFT) {
			dY = 0;
		}
		if (code == KeyCode.RIGHT || code == KeyCode.KP_RIGHT) {
			dY = 0;
		}
		}
		
    
    
    
}
