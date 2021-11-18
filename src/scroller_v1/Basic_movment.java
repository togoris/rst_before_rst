package scroller_v1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import simpleIO.*;

public class Basic_movment extends Application {



		// initial size of screen; game still works after resize
		static final int SCREEN_WIDTH = 1280;
		static final int SCREEN_HEIGHT = 720;
		// Constants for speed and size of objects in the game
		final int BALL_SIZE = 5;
		final int TANK_HEIGHT = 10;
		final int TANK_WIDTH = 10;
		final int TANK_SPEED = 3;
		final int USER_TANK_SPEED = 5;
		final int FONT_SIZE = 20;
		//all of the movment variables
		int tank1XSart = 5;
		int tank1YSart = 0;
		double dX1Tank = 0;
		double dY1Tank = 0;
		double GRAVITY = 0.2;
		int jumptoken = 1;
		//array for the starting potitions for the tanks
		int[] startXSpots = new int[10];
		int[] startYSpots = new int[10];
		int level2 = SCREEN_HEIGHT/2;
		int spacing = 150;
		int level1 = level2+spacing;
		int level3 = level2-spacing;
		int platformWidth = 50;
		int platformHeight = 10;
		// shapes for the game
		
		
		Rectangle userTank1;
		
		Rectangle point1;
		
		//array for all of the walls
		Rectangle[] verticalWalls = new Rectangle[0];
		Rectangle[] horisontalWalls = new Rectangle[0];
		
		Rectangle[][] platforms = new Rectangle[1][2];
		
		// controls the animation
		GameTimer timer;
		// Reference for screen
		Scene scene;
		// Play sound when ball hits paddle
		Rotate rotate = new Rotate();

		// Keep score



		public void start(Stage myStage) throws Exception {
			//where the tanks start
			userTank1 = new Rectangle(tank1XSart, tank1YSart, TANK_WIDTH, TANK_HEIGHT);
			
			userTank1.setFill(Paint.valueOf("Red"));
			inisializeplatforms();
			//the mesage that shows teh points
			
			// add all elements to the scene graph
			//ilitialy drawing everything
			Group root = new Group(userTank1);
			//drawing all of the walls (thank u for this code miss)
			
			

			// animation timer to update and render graphics
			timer = new GameTimer();
			timer.start();

			
			for (Rectangle rectangle : platforms[0]) {
				root.getChildren().add(rectangle);
			}
			// Create the scene and set it to respond to user events
			scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
			scene.setOnKeyPressed(event -> handleKeyPressed(event));
			scene.setOnKeyReleased(event -> handleKeyReleased(event));
			
			// Set up the stage
			myStage.setTitle("Tanks_v2");
			myStage.setScene(scene);

			myStage.show();

		}
		class GameTimer extends AnimationTimer {
			@Override
			public void handle(long now) {
				//making variables to use for colition detection
				
				//updating the score each second or whatever
				
				//all of the colition detection
				
				//drawing all of the objects again
				platformCheck(userTank1);
				userTank1.setY(userTank1.getY() + dY1Tank);
				
				userTank1.setX(userTank1.getX() + dX1Tank);
				
				walls();
				
				if (userTank1.getY()+ 10 < scene.getHeight()) {
	            	dY1Tank += GRAVITY;
	            }
	           
	            

				

			}
		}
					
		
		private void handleKeyPressed(KeyEvent event) {
			KeyCode code = event.getCode();

			// all of the movment code

			if (code == KeyCode.UP || code == KeyCode.KP_UP) {
				jump();
			}
			if (code == KeyCode.DOWN || code == KeyCode.KP_DOWN) {
				

			}
			if (code == KeyCode.LEFT || code == KeyCode.KP_LEFT) {
				dX1Tank = -5 ;
			}
			if (code == KeyCode.RIGHT || code == KeyCode.KP_RIGHT) {
				dX1Tank = 5 ;
			
			}

		}
		private void handleKeyReleased(KeyEvent event) {
			KeyCode code = event.getCode();
			//all of the stoping code
			if (code == KeyCode.UP || code == KeyCode.KP_UP || code == KeyCode.DOWN || code == KeyCode.KP_DOWN) {
				dY1Tank = 0;
				
				
			}
			if (code == KeyCode.LEFT || code == KeyCode.KP_LEFT || code == KeyCode.RIGHT|| code == KeyCode.KP_RIGHT) {
				dX1Tank = 0;
				
				
			}
			
		}
		double getRandomNumber(int low, int high) {
			int range = high - low + 1;
			return Math.random() * range + low;
		}
		public static void main(String[] args) {
			launch(args);
		}
		private void jump() {
			if(jumptoken == 1) {
			//maybe make accelerated jumping
				dY1Tank -= 10;
				userTank1.setY(userTank1.getY() -1);
			}
			jumptoken = 0;
		}
		private void walls() {
			if (userTank1.getX() < 0 || userTank1.getX()+10 > scene.getWidth()) {
				userTank1.setX(userTank1.getX()-dX1Tank);
				dX1Tank = 0;
			}
            if (userTank1.getY()+10> scene.getHeight()) {
            	userTank1.setY(userTank1.getY()-dY1Tank);
            	dY1Tank = 0; //reverse direction
            	jumptoken = 1;
            }
            
            
		}
		private void inisializeplatforms() {
			
			platforms[0][0] = new Rectangle(0, level2, platformWidth, platformHeight);
			platforms[0][1] = new Rectangle(0, level1, platformWidth, platformHeight);
		}
		private void platformCheck(Rectangle rectangle){
			Bounds tank = rectangle.getBoundsInLocal();
			for (int i = 0; i < platforms.length; i++) {
				for(int j = 0; j < platforms[i].length; j++) {
				Bounds wall = platforms[i][j].getBoundsInLocal();
				
				if (tank.intersects(wall)) {
					
					dY1Tank = 0;
					userTank1.setY(userTank1.getY() - dY1Tank);
					jumptoken = 1;
				}if(platforms[i][j].getY()+1<userTank1.getY()+TANK_HEIGHT && platforms[i][j].getY()+platformHeight>userTank1.getY() &&
						platforms[i][j].getX()<userTank1.getX() && platforms[i][j].getX()+platformWidth>userTank1.getX()+TANK_WIDTH) {
					userTank1.setY(userTank1.getY()-1);
				}
				}
			}
		}
}

