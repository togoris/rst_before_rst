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
		static final int SCREEN_WIDTH = 841;
		static final int SCREEN_HEIGHT = 841;
		// Constants for speed and size of objects in the game
		final int BALL_SIZE = 5;
		final int TANK_HEIGHT = 20;
		final int TANK_WIDTH = 12;
		final int TANK_SPEED = 3;
		final int USER_TANK_SPEED = 5;
		final int FONT_SIZE = 20;
		//all of the movment variables
		int tank1XSart = 5;
		int tank1YSart = 0;
		double dX1Tank = 0;
		double dY1Tank = 0;
		double dX1Point = 0;
		double dY1Point = 0;
		double rotation1 = 0;
		
		//counter to pick witch ball will be shot
		int counter1 = 1;
		int counter2 = 1;
		int counter3 = 1;
		//array for the starting potitions for the tanks
		int[] startXSpots = new int[10];
		int[] startYSpots = new int[10];
		// shapes for the game
		
		Rectangle userTank1;
		
		Rectangle point1;
		
		//array for all of the walls
		Rectangle[] verticalWalls = new Rectangle[0];
		Rectangle[] horisontalWalls = new Rectangle[0];
		
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
			
			//the mesage that shows teh points
			
			// add all elements to the scene graph
			//ilitialy drawing everything
			Group root = new Group(userTank1);
			//drawing all of the walls (thank u for this code miss)
			for (Rectangle rectangle : verticalWalls) {
				root.getChildren().add(rectangle);
			}
			for (Rectangle rectangle : horisontalWalls) {
				root.getChildren().add(rectangle);
			}

			// animation timer to update and render graphics
			timer = new GameTimer();
			timer.start();

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
				
				userTank1.setY(userTank1.getY() + dY1Tank);
				
				userTank1.setX(userTank1.getX() + dX1Tank);
				
				
				double n1 = userTank1.getRotate();
				
				userTank1.setRotate(rotation1 + n1);
			}
		}
					
		private double anglethingypart1(double curentAngle) {
			//look too bottom method for coments
			curentAngle %= 360;
			double yValue = 1;
			double xValue = 0;
			if (curentAngle < 0) {
				curentAngle *= -1;
				curentAngle = 360 - curentAngle;
			}
			if (curentAngle >= 0 && curentAngle <= 180) {
				if (curentAngle <= 90) {
					yValue = -1 * ((90 - curentAngle) / 90);
				} else {
					xValue = (180 - curentAngle) / 90;
					yValue = (1 - xValue);
				}
			} else {
				curentAngle -= 180;
				if (curentAngle <= 90) {
					yValue = ((90 - curentAngle) / 90);
				} else {
					xValue = -1 * ((180 - curentAngle) / 90);
					yValue = -1 * (1 + xValue);
				}

			}

			return yValue;
		}
		private double anglethingypart2(double curentAngle) {
			curentAngle %= 360;
			double yValue = 1;
			double xValue = 0;
			if (curentAngle < 0) {
				curentAngle *= -1;
				curentAngle = 360 - curentAngle;
			} // if its in the right
			if (curentAngle >= 0 && curentAngle <= 180) {
				// if its in the top right
				if (curentAngle <= 90) {
					// y value is how close it is to vertical when 1 is vertical and 0 is hirisontal
					yValue = (90 - curentAngle) / 90;
					xValue = 1 - yValue;
				} // if its in the bottom right
				else {
					// same as the y value but 1 as horisontal and 0 as vertical
					xValue = (180 - curentAngle) / 90;

				}
				// if its in the left
			} else {
				curentAngle -= 180;

				// if it is top left
				if (curentAngle <= 90) {
					yValue = -1 * ((90 - curentAngle) / 90);
					xValue = -1 * (1 + yValue);
				} // if its in the bottom left
				else {
					xValue = -1 * ((180 - curentAngle) / 90);

				}
			}
			return xValue;
		}
		private void handleKeyPressed(KeyEvent event) {
			KeyCode code = event.getCode();

			// all of the movment code

			if (code == KeyCode.UP || code == KeyCode.KP_UP) {
				dY1Tank = TANK_SPEED * anglethingypart1(userTank1.getRotate());
				dX1Tank = TANK_SPEED * anglethingypart2(userTank1.getRotate());
			}
			if (code == KeyCode.DOWN || code == KeyCode.KP_DOWN) {
				dY1Tank = -TANK_SPEED * anglethingypart1(userTank1.getRotate());
				dX1Tank = -TANK_SPEED * anglethingypart2(userTank1.getRotate());

			}
			if (code == KeyCode.LEFT || code == KeyCode.KP_LEFT) {
				rotation1 = -5;
			}
			if (code == KeyCode.RIGHT || code == KeyCode.KP_RIGHT) {
				rotation1 = 5;
			
			}

		}
		private void handleKeyReleased(KeyEvent event) {
			KeyCode code = event.getCode();
			//all of the stoping code
			if (code == KeyCode.UP || code == KeyCode.KP_UP || code == KeyCode.DOWN || code == KeyCode.KP_DOWN) {
				dY1Tank = 0;
				dX1Tank = 0;
				dY1Point = 0;
				dX1Point = 0;
			} else if (code == KeyCode.LEFT || code == KeyCode.KP_LEFT || code == KeyCode.RIGHT
					|| code == KeyCode.KP_RIGHT) {
				rotation1 = 0;}
			
		}
		double getRandomNumber(int low, int high) {
			int range = high - low + 1;
			return Math.random() * range + low;
		}
		public static void main(String[] args) {
			launch(args);
		}
	}

