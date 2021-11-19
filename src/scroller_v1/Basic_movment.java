package scroller_v1;



import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import simpleIO.Console;


public class Basic_movment extends Application {



		// initial size of screen; game still works after resize
		long lastTime = 0;
		static final int SCREEN_WIDTH = 1280;
		static final int SCREEN_HEIGHT = 720;
		// Constants for speed and size of objects in the game
		final int BALL_SIZE = 5;
		final int TANK_HEIGHT = 10;
		final int TANK_WIDTH = 10;
		//all of the movment variables
		int tank1XSart = 0;
		int tank1YSart = 0;
		double dX1Tank = 0;
		double dY1Tank = 0;
		double GRAVITY = 0.2;
		int jumptoken = 1;
		//array for the starting potitions for the tanks
		int[] startXSpots = new int[10];
		int[] startYSpots = new int[10];

		int spacing = 150;
		int level2 = SCREEN_HEIGHT/2;
		int level1 = level2+spacing;
		int level3 = level2-spacing;
		int level2B = SCREEN_HEIGHT/2+75;
		int level1B = level2B+spacing;
		int level3B = level2B-(spacing*1);
		int level4B = level2B-(spacing*2);
		
		int a = 0;
		int Colom1 = a;
		int Colom2 = a+spacing;
		int Colom3 = a+spacing*2;
		int Colom4 = a+spacing*3;
		int Colom5 = a+spacing*4;
		int Colom6 = a+spacing*5;
		int Colom7 = a+spacing*6;
		int Colom8 = a+spacing*7;
		int Colom9 = a+spacing*8;
		
		
		int platformWidth = 50;
		int platformHeight = 10;
		int godown;
		int shotCounter;
		int offscreen = -500;
		int shotSize = 2;
		// shapes for the game
		
		
		Rectangle player1;
		
		Rectangle point1;
		
		//array for all of the walls
		Rectangle[] verticalWalls = new Rectangle[0];
		Rectangle[] horisontalWalls = new Rectangle[0];
		
		Rectangle[][][] platforms = new Rectangle[1][9][4];
		Rectangle[][] enemies = new Rectangle[1][1];
		Circle[] shots = new Circle[10];
		
		// controls the animation
		GameTimer timer;
		// Reference for screen
		Scene scene;
		// Play sound when ball hits paddle
		Rotate rotate = new Rotate();

		// Keep score



		public void start(Stage myStage) throws Exception {
			//where the tanks start
			lastTime = System.currentTimeMillis();
			player1 = new Rectangle(tank1XSart, tank1YSart, TANK_WIDTH, TANK_HEIGHT);
			
			player1.setFill(Paint.valueOf("Red"));
			inisializePlatforms();
			inisializeEnemies();
			inisializeShots();
			//the mesage that shows teh points
			
			// add all elements to the scene graph
			//ilitialy drawing everything
			Group root = new Group(player1);
			//drawing all of the walls (thank u for this code miss)
			
			

			// animation timer to update and render graphics
			timer = new GameTimer();
			timer.start();

			for (int i = 0; i < platforms.length; i++) {
				for(int j = 0; j < platforms[i].length; j++) {
					for (Rectangle rectangle : platforms[i][j]) {
						root.getChildren().add(rectangle);
					}
				}
			}
//			for(int i = 0; i < enemies.length; i++) {
//				for (Rectangle rectangle : enemies[i]) {
//					root.getChildren().add(rectangle);
//					}
//			}
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
				wallsColition();
				platformCheck(player1);
				player1.setY(player1.getY() + dY1Tank);
				player1.setX(player1.getX() + dX1Tank);
				
				
				
				if (player1.getY()+ 10 < scene.getHeight()) {
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
				godown=1;

			}
			if (code == KeyCode.LEFT || code == KeyCode.KP_LEFT) {
				dX1Tank = -3 ;
			}
			if (code == KeyCode.RIGHT || code == KeyCode.KP_RIGHT) {
				dX1Tank = 3 ;
			
			}
			if (code == KeyCode.R){
				player1.setY(0);
				player1.setX(0);
			}
			if(code == KeyCode.W ) {
				shoot();
			}

		}
		private void handleKeyReleased(KeyEvent event) {
			KeyCode code = event.getCode();
			//all of the stoping code
			if (code == KeyCode.UP || code == KeyCode.KP_UP || code == KeyCode.DOWN || code == KeyCode.KP_DOWN) {
				
				
				
			}
			if(code == KeyCode.DOWN || code == KeyCode.KP_DOWN) {
				godown =0;
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
				dY1Tank = -7.1;
				player1.setY(player1.getY() -1);
			}
			jumptoken = 0;
		}
		private void wallsColition() {
			if (player1.getX() < 0 || player1.getX()+10 > scene.getWidth()) {
				player1.setX(player1.getX()-dX1Tank);
				dX1Tank = 0;
			}
            if (player1.getY()+10> scene.getHeight()) {
            	player1.setY(player1.getY()-dY1Tank);
            	dY1Tank = 0; //reverse direction
            	jumptoken = 1;
            }
            
            
		}
		
		private void inisializePlatforms() {
			int startValue = 0;
			
			platforms[0][0][0] = new Rectangle(startValue+Colom1, level1, platformWidth, platformHeight);
			platforms[0][0][1] = new Rectangle(startValue+Colom1, level2, platformWidth, platformHeight);
			platforms[0][0][2] = new Rectangle(startValue+Colom1, level3, platformWidth, platformHeight);
			platforms[0][0][3] = new Rectangle(offscreen, offscreen, platformWidth, platformHeight);
				platforms[0][1][0] = new Rectangle(startValue+Colom2, level1B, platformWidth, platformHeight);
				platforms[0][1][1] = new Rectangle(startValue+Colom2, level2B, platformWidth, platformHeight);
				platforms[0][1][2] = new Rectangle(startValue+Colom2, level3B, platformWidth, platformHeight);
				platforms[0][1][3] = new Rectangle(startValue+Colom2, level4B, platformWidth, platformHeight);
			platforms[0][2][0] = new Rectangle(startValue+Colom3, level1, platformWidth, platformHeight);
			platforms[0][2][1] = new Rectangle(startValue+Colom3, level2, platformWidth, platformHeight);
			platforms[0][2][2] = new Rectangle(startValue+Colom3, level3, platformWidth, platformHeight);
			platforms[0][2][3] = new Rectangle(offscreen, offscreen, platformWidth, platformHeight);
				platforms[0][3][0] = new Rectangle(startValue+Colom4, level1B, platformWidth, platformHeight);
				platforms[0][3][1] = new Rectangle(startValue+Colom4, level2B, platformWidth, platformHeight);
				platforms[0][3][2] = new Rectangle(startValue+Colom4, level3B, platformWidth, platformHeight);
				platforms[0][3][3] = new Rectangle(startValue+Colom4, level4B, platformWidth, platformHeight);
			platforms[0][4][0] = new Rectangle(startValue+Colom5, level3, platformWidth, platformHeight);
			platforms[0][4][1] = new Rectangle(startValue+Colom5, level1, platformWidth, platformHeight);
			platforms[0][4][2] = new Rectangle(startValue+Colom5, level2, platformWidth, platformHeight);
			platforms[0][4][3] = new Rectangle(offscreen, offscreen, platformWidth, platformHeight);
				platforms[0][5][0] = new Rectangle(startValue+Colom6, level4B, platformWidth, platformHeight);
				platforms[0][5][1] = new Rectangle(startValue+Colom6, level1B, platformWidth, platformHeight);
				platforms[0][5][2] = new Rectangle(startValue+Colom6, level2B, platformWidth, platformHeight);
				platforms[0][5][3] = new Rectangle(startValue+Colom6, level3B, platformWidth, platformHeight);
			platforms[0][6][0] = new Rectangle(startValue+Colom7, level3, platformWidth, platformHeight);
			platforms[0][6][1] = new Rectangle(startValue+Colom7, level1, platformWidth, platformHeight);
			platforms[0][6][2] = new Rectangle(startValue+Colom7, level2, platformWidth, platformHeight);
			platforms[0][6][3] = new Rectangle(offscreen, offscreen, platformWidth, platformHeight);
				platforms[0][7][0] = new Rectangle(startValue+Colom8, level4B, platformWidth, platformHeight);
				platforms[0][7][1] = new Rectangle(startValue+Colom8, level1B, platformWidth, platformHeight);
				platforms[0][7][2] = new Rectangle(startValue+Colom8, level2B, platformWidth, platformHeight);
				platforms[0][7][3] = new Rectangle(startValue+Colom8, level3B, platformWidth, platformHeight);
			platforms[0][8][0] = new Rectangle(startValue+Colom9, level3, platformWidth, platformHeight);
			platforms[0][8][1] = new Rectangle(startValue+Colom9, level1, platformWidth, platformHeight);
			platforms[0][8][2] = new Rectangle(startValue+Colom9 , level2, platformWidth, platformHeight);
			platforms[0][8][3] = new Rectangle(offscreen, offscreen, platformWidth, platformHeight);
		}
		private void inisializeEnemies() {
			
		}
		private void inisializeShots() {
			shots[0] = new Circle(offscreen,offscreen,shotSize);
			shots[1] = new Circle(offscreen,offscreen,shotSize);
			shots[2] = new Circle(offscreen,offscreen,shotSize);
			shots[3] = new Circle(offscreen,offscreen,shotSize);
			shots[4] = new Circle(offscreen,offscreen,shotSize);
			shots[5] = new Circle(offscreen,offscreen,shotSize);
			shots[6] = new Circle(offscreen,offscreen,shotSize);
			shots[7] = new Circle(offscreen,offscreen,shotSize);
			shots[8] = new Circle(offscreen,offscreen,shotSize);
			shots[9] = new Circle(offscreen,offscreen,shotSize);

		}
		private void shoot() {
			shotCounter +=1;
			
		}
		private void platformCheck(Rectangle rectangle){
			Bounds tank = rectangle.getBoundsInLocal();
			for (int i = 0; i < platforms.length; i++) {
				for(int j = 0; j < platforms[i].length; j++) {
					for(int k = 0;k<platforms[i][j].length; k++) {
						Bounds wall = platforms[i][j][k].getBoundsInLocal();
				
						if(godown == 0) {
							if (tank.intersects(wall)) {
								dY1Tank = 0;
								player1.setY(player1.getY() - dY1Tank);
								jumptoken = 1;
							}		
							if(platforms[i][j][k].getY()+1<=player1.getY()+TANK_HEIGHT && platforms[i][j][k].getY()+platformHeight>=player1.getY() &&
									platforms[i][j][k].getX()<=player1.getX()+TANK_WIDTH && platforms[i][j][k].getX()+platformWidth>=player1.getX()) {
									player1.setY(player1.getY()-1);
									
							}
						}
					}
				}
			}
		}
}

