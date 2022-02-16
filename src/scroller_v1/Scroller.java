package scroller_v1;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


public class Scroller extends Application {



		// initial size of screen; game still works after resize
		long lastTime = 0;
		static final int SCREEN_WIDTH = 1200;
		static final int SCREEN_HEIGHT = 900;
		// Constants for speed and size of objects in the game
		final int BALL_SIZE = 5;
		final int TANK_HEIGHT = 10;
		final int TANK_WIDTH = 10;
		//all of the movment variables
		int tank1XSart = SCREEN_WIDTH/10;
		int tank1YSart = 0;
		double dX1Tank = 0;
		double dY1Tank = 0;
		double GRAVITY = 0.2;
		int jumptoken = 1;
		//array for the starting potitions for the tanks
		int[] startXSpots = new int[10];
		int[] startYSpots = new int[10];
		long lastframetime = System.currentTimeMillis();
	
		List<Platforms> sceen1 = new ArrayList<Platforms>();
		
		int botWidth = 5;
		int botHeight = 5;
		int platformWidth = 60;
		int platformHeight = 20;
		int godown;
		int shotCounter = 0;
		int offscreen = -500;
		int shotSize = 2;
		int shotXSpeed = 5;
		int BaseX=0;
		// shapes for the game
		List<bot> bots = new ArrayList<bot>();
		
		Rectangle player1;
		
	
		
		
		Rectangle point1;
		
		//array for all of the walls
		Rectangle[] verticalWalls = new Rectangle[0];
		Rectangle[] horisontalWalls = new Rectangle[0];
		
//		Rectangle[][][] platforms = new Rectangle[1][9][4];
		List<Platforms> platformcheck = new ArrayList<Platforms>();
		
		
 
		Circle[] PlayerShots = new Circle[10];
		int[] dxPlayerShots = new int[10];
		int[] dyPlayerShots = new int[10];
		
		Circle[][] BotShots = new Circle[3][10];
		int[][] dxBotShots = new int[3][10];
		int[][] dyBotShots = new int[3][10];
		
		
		// controls the animation
		AnimationTimer timer;
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
			inisializeShots();
			
			
	
			

			//ilitialy drawing everything
			Group root = new Group(player1);
			//drawing all of the walls (thank u for this code miss)
			
			

			// animation timer to update and render graphics
			timer = new AnimationTimer() {
				public void handle(long now) {
					root.getChildren().clear();
					root.getChildren().add(player1);
					for(Platforms p:sceen1) {
						root.getChildren().add(p.getRect());
					}
					for(bot B: bots) {
						root.getChildren().add(B.getRect());
					}
					
					checkPlatforms();
					wallsColition();
					platformCheck(player1);
					player1.setY(player1.getY() + dY1Tank);
					player1.setX(player1.getX() + dX1Tank);
					long dif = System.currentTimeMillis()-lastframetime;
					for(bot A: bots) {
						A.move(dif, sceen1,player1);
						
					}
					
					for(int i=0; i<PlayerShots.length; i++) {
						PlayerShots[i].setCenterX(PlayerShots[i].getCenterX()+dxPlayerShots[i]);
						PlayerShots[i].setCenterY(PlayerShots[i].getCenterY()+dyPlayerShots[i]);
					}
					
					if(player1.getX() >= SCREEN_WIDTH*0.75) {
						for(Platforms p:sceen1) {
							
							p.x-=3;
								
							}
						BaseX-=3;
							player1.setX(player1.getX()-3);
						
						}
					if(player1.getX() <= SCREEN_WIDTH*0.1) {
						if(findFistX()<0) {
						for(Platforms p:sceen1) {
							
							p.x+=3;
								
							}
						BaseX+=3;
							player1.setX(player1.getX()+3);
						
						}}
					
					
					if (player1.getY()+ 10 < scene.getHeight()) {
		            	dY1Tank += GRAVITY;
		            }
					
					
		            

					
					lastframetime = System.currentTimeMillis();
					
				}
			};
			
			timer.start();

					for (Platforms rectangle : sceen1) {
						root.getChildren().add(rectangle.getRect());
					}
				
			
			for (Circle Circle : PlayerShots) {
			    root.getChildren().add(Circle);
			}
//			for(int i=0; i <BotShots.length; i++) {
//				for (Circle Circle : BotShots[i]) {
//					root.getChildren().add(Circle);
//				}
//				}
				
			
			
			// Create the scene and set it to respond to user events
			scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
			scene.setOnKeyPressed(event -> handleKeyPressed(event));
			scene.setOnKeyReleased(event -> handleKeyReleased(event));
			
			// Set up the stage
			myStage.setTitle("Tanks_v2");
			myStage.setScene(scene);

			myStage.show();

		}
		private void checkPlatforms() {
				for(int j = 0; j < sceen1.size(); j++) {
						
					if(1==checkOnscreen(sceen1.get(j).getRect())) {
						platformcheck.add(sceen1.get(j));
					}
						
					}
				}
			
		
		private int checkOnscreen(Rectangle platform) {
			int check;
			check = (int) (platform.getX()*2+platformWidth);
			if (check> SCREEN_WIDTH || check < 0) {
				return 1;
			}else
				return 0;
		}
		private int findFistX() {
			int x = Integer.MAX_VALUE;
			for(int i=0;i<sceen1.size(); i++) {
				if(sceen1.get(i).x<x) {
					x= sceen1.get(i).x;
				}
			}
			return x;
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
			
			if (code == KeyCode.Q) {
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
            	player1.setY(0);
            	player1.setX(SCREEN_WIDTH/10);
            	dY1Tank = 0; //reverse direction
            	jumptoken = 1;
            }
            
            
		}
		private void inisializePlatforms() throws IOException {
			
		
			for(int i = 0;i<500; i++) {
				Platforms platform = new Platforms(i*150,500,1);
				sceen1.add(platform);
			}
		}
		
		private void placingplatforms() {
			
			
			
			
			
			
			
		}
		private void inisializeShots() {
			PlayerShots[0] = new Circle(offscreen,offscreen,shotSize);
			PlayerShots[1] = new Circle(offscreen,offscreen,shotSize);
			PlayerShots[2] = new Circle(offscreen,offscreen,shotSize);
			PlayerShots[3] = new Circle(offscreen,offscreen,shotSize);
			PlayerShots[4] = new Circle(offscreen,offscreen,shotSize);
			PlayerShots[5] = new Circle(offscreen,offscreen,shotSize);
			PlayerShots[6] = new Circle(offscreen,offscreen,shotSize);
			PlayerShots[7] = new Circle(offscreen,offscreen,shotSize);
			PlayerShots[8] = new Circle(offscreen,offscreen,shotSize);
			PlayerShots[9] = new Circle(offscreen,offscreen,shotSize);
			
		}
		private void shoot() {
			if(shotCounter >=9)
				shotCounter =0;
			shotCounter +=1;
			dxPlayerShots[shotCounter] = shotXSpeed;
			PlayerShots[shotCounter].setCenterX(player1.getX()+TANK_WIDTH/2);
			PlayerShots[shotCounter].setCenterY(player1.getY()+TANK_HEIGHT/2);
			
			Console.print(shotCounter);
			
		}
		private void platformCheck(Rectangle rectangle){
			Bounds tank = rectangle.getBoundsInLocal();
				for(int j = 0; j < sceen1.size();j++) {
					Platforms checker = sceen1.get(j);
					Bounds wall = checker.getRect().getBoundsInLocal();
						if(godown == 0) {
							if (tank.intersects(wall)) {
								dY1Tank = 0;
								player1.setY(player1.getY() - dY1Tank);
								jumptoken = 1;
							}		
							if(checker.y+1<=player1.getY()+TANK_HEIGHT && 
							   checker.y+platformHeight+1>=player1.getY() &&
							   checker.x<=player1.getX()+TANK_WIDTH && 
							   checker.x+platformWidth>=player1.getX()) {
									player1.setY(player1.getY()-1);
									
							}
						}
					}
				}
			}


